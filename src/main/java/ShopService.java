import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.util.*;

@RequiredArgsConstructor
public class ShopService {
    private final ProductRepo productRepo;
    private final OrderRepo orderRepo;

    public Order addOrder(List<String> productIds) {
        List<Product> products = new ArrayList<>();
        for (String productId : productIds) {
            Optional<Product> productToOrder = productRepo.getProductById(productId);
            if (productToOrder.isEmpty()) {
                throw new NoSuchElementException("Product not found, Id: " + productId);
            }
            products.add(productToOrder.get());
        }

        Order newOrder = new Order(UUID.randomUUID().toString(), products, OrderStatus.PROCESSING, Instant.now());

        return orderRepo.addOrder(newOrder);
    }

    public List<Order> getOrdersByOrderStatus(OrderStatus orderStatus) {
        List<Order> orders = new ArrayList<>(orderRepo.getOrders());
        return orders.stream()
                .filter(order -> order.orderStatus() == orderStatus)
                .toList();

    }

    public void updateOrder(String orderId, OrderStatus newOrderStatus) {
        Order order = orderRepo.getOrderById(orderId);
        orderRepo.removeOrder(orderId);
        order = order.withOrderStatus(newOrderStatus);
        orderRepo.addOrder(order);

    }
}
