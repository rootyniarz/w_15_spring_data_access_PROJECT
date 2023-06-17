package pl.zajavka.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public class StoreFixtures {
    public static Customer someCustomer() {
        return Customer.builder()
                .userName("zajavkowyzajavkowicz")
                .email("zajavka@gmail.com")
                .name("zajavkowicz")
                .surname("zajavkowy")
                .dateOfBirth(LocalDate.of(1984, 10, 2))
                .telephoneNumber("+001234567890")
                .build();
    }

    public static Producer someProducer() {
        return Producer.builder()
                .producerName("someProducer")
                .address("someAddress")
                .build();
    }

    public static Product someProduct1(Producer producer) {
        return Product.builder()
                .productCode("productCode1")
                .productName("productName1")
                .productPrice(BigDecimal.valueOf(3454.22))
                .adultsOnly(false)
                .description("someDescription")
                .producer(producer)
                .build();
    }
    public static Product someProduct2(Producer producer) {
        return Product.builder()
                .productCode("productCode2")
                .productName("productName2")
                .productPrice(BigDecimal.valueOf(3454.22))
                .adultsOnly(false)
                .description("someDescription")
                .producer(producer)
                .build();
    }


    public static Purchase somePurchase(Customer customer, Product product) {
        return Purchase.builder()
                .customer(customer)
                .product(product)
                .quantity(2)
                .dateTime(OffsetDateTime.of(2020, 1, 1, 10, 9, 10, 1, ZoneOffset.ofHours(4)))
                .build();
    }

    public static Opinion someOpinion(Customer customer, Product product) {
        return Opinion.builder()
                .customer(customer)
                .product(product)
                .stars((byte) 4)
                .comment("My comment")
                .dateTime(OffsetDateTime.of(2020, 1, 1, 12, 9, 10, 1, ZoneOffset.ofHours(4)))
                .build();
    }
}
