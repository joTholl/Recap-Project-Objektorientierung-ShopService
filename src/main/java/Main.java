import java.util.List;

public class Main {
    public static void main(String[] args) {
        OrderRepo orderRepo = new OrderMapRepo();
        ProductRepo productRepo = new ProductRepo();
        ShopService shopService = new ShopService(productRepo, orderRepo);

        productRepo.addProduct(new Product("2", "Himbeere"));
        productRepo.addProduct(new Product("3", "Erdbeere"));
        Order o1 = shopService.addOrder(List.of("1", "3"));
        Order o2 = shopService.addOrder(List.of("2", "3"));
        Order o3 = shopService.addOrder(List.of("1", "3", "2"));
    }
}
