import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class ShopServiceTest {
    ShopService shopService = new ShopService();

    @Test
    void addOrderTest() {
        //GIVEN

        List<String> productsIds = List.of("1");

        //WHEN
        Order actual = shopService.addOrder(productsIds);

        //THEN
        Order expected = new Order("-1", List.of(new Product("1", "Apfel")), OrderStatus.PROCESSING);
        assertEquals(expected.products(), actual.products());
        assertNotNull(expected.id());
    }

    @Test
    void addOrderTest_whenInvalidProductId_expectNull() {
        //GIVEN

        List<String> productsIds = List.of("1", "2");

        //THEN
        assertThrows(NoSuchElementException.class, () -> shopService.addOrder(productsIds));
    }

    @Test
    void getOrdersByOrderStatusTest() {
        Order order = shopService.addOrder(List.of("1"));
        assertEquals(List.of(order), shopService.getOrdersByOrderStatus(OrderStatus.PROCESSING));
        assertEquals(new ArrayList<Order>(), shopService.getOrdersByOrderStatus(OrderStatus.COMPLETED));
    }

    @Test
    void updateOrderTest() {
        Order order = shopService.addOrder(List.of("1"));
        String id = order.id();
        shopService.updateOrder(id, OrderStatus.IN_DELIVERY);
        order = order.withOrderStatus(OrderStatus.IN_DELIVERY);
        assertEquals(List.of(order), shopService.getOrdersByOrderStatus(OrderStatus.IN_DELIVERY));
    }

}
