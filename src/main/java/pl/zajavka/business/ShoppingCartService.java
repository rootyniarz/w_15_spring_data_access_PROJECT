package pl.zajavka.business;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.zajavka.domain.Customer;
import pl.zajavka.domain.Product;
import pl.zajavka.domain.Purchase;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Slf4j
@Service
@AllArgsConstructor
public class ShoppingCartService {

    private final CustomerService customerService;
    private final ProductService productService;
    private final PurchaseService purchaseService;

    @Transactional
    public void makeAPurchase(String email, String productCode, int quantity) {
        Customer customer = customerService.find(email);
        Product product = productService.find(productCode);
        Purchase purchase = purchaseService.create(Purchase.builder()
                .customer(customer)
                .product(product)
                .quantity(quantity)
                .dateTime(OffsetDateTime.of(2020, 1, 1, 10, 9, 10, 1, ZoneOffset.ofHours(4)))
                .build());
        log.info("Customer: [{}] made a purchase for product: [{}], quantity: [{}]"
                , email, product, quantity);
        log.debug("Customer: [{}] made a purchase for product: [{}], quantity: [{}], purchase: [{}]"
                , email, product, quantity, purchase);
    }
}
