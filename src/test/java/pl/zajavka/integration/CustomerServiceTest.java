package pl.zajavka.integration;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import pl.zajavka.business.*;
import pl.zajavka.domain.*;
import pl.zajavka.infrastructure.configuration.ApplicationConfiguration;

import java.time.LocalDate;
import java.util.List;

@SpringJUnitConfig(classes = ApplicationConfiguration.class)
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class CustomerServiceTest {

    private ReloadDataService reloadDataService;
    private CustomerService customerService;
    private PurchaseService purchaseService;
    private OpinionService opinionService;
    private ProducerService producerService;
    private ProductService productService;

//    @Autowired
//    public CustomerServiceTest(
//            CustomerService customerService,
//            PurchaseService purchaseService,
//            OpinionService opinionService
//    ) {
//        this.customerService = customerService;
//        this.purchaseService = purchaseService;
//        this.opinionService = opinionService;
//    }

    @BeforeEach
    public void setUp() {
        Assertions.assertNotNull(reloadDataService);
        Assertions.assertNotNull(customerService);
        Assertions.assertNotNull(purchaseService);
        Assertions.assertNotNull(opinionService);
        Assertions.assertNotNull(producerService);
        Assertions.assertNotNull(productService);

        reloadDataService.loadRandomData();
    }

    @Test
    @DisplayName("Polecenie 4 cz.1")
    void thatCustomerIsRemovedCorrectly() {
        //given
        final Customer customer = customerService.create(StoreFixtures.someCustomer());
        final Producer producer = producerService.create(StoreFixtures.someProducer());
        final Product product1 = productService.create(StoreFixtures.someProduct1(producer));
        final Product product2 = productService.create(StoreFixtures.someProduct2(producer));
        purchaseService.create(StoreFixtures.somePurchase(customer, product1).withQuantity(1));
        purchaseService.create(StoreFixtures.somePurchase(customer, product2).withQuantity(3));
        opinionService.create(StoreFixtures.someOpinion(customer, product1));

        Assertions.assertEquals(customer, customerService.find(customer.getEmail()));

        //when
        customerService.remove(customer.getEmail());

        //then
        RuntimeException exception = Assertions
                .assertThrows(RuntimeException.class,
                        () -> customerService.find(customer.getEmail()));
        Assertions.assertEquals
                ("Customer with email: [%s] is missing".
                        formatted(customer.getEmail()), exception.getMessage());

        Assertions.assertTrue(purchaseService.findAll(customer.getEmail()).isEmpty());
        Assertions.assertTrue(opinionService.findAll(customer.getEmail()).isEmpty());


    }

    @Test
    @DisplayName("Polecenie 4 cz.2")
    void thatPurchaseAndOpinionIsNotRemovedWhenCustomerRemovingFails() {
        //given
        final Customer customer = customerService.create(StoreFixtures.someCustomer()
                .withDateOfBirth(LocalDate.of(1950,10,4)));
        final Producer producer = producerService.create(StoreFixtures.someProducer());
        final Product product1 = productService.create(StoreFixtures.someProduct1(producer));
        final Product product2 = productService.create(StoreFixtures.someProduct2(producer));
        Purchase purchase1 = purchaseService.create(StoreFixtures.somePurchase(customer, product1).withQuantity(1));
        Purchase purchase2 = purchaseService.create(StoreFixtures.somePurchase(customer, product2).withQuantity(3));
        Opinion opinion1 = opinionService.create(StoreFixtures.someOpinion(customer, product1));

        Assertions.assertEquals(customer, customerService.find(customer.getEmail()));

        //when
        RuntimeException exception = Assertions
                .assertThrows(RuntimeException.class,
                        () -> customerService.remove(customer.getEmail()));
        Assertions.assertEquals
                ("Could not remove customer because he/she is older then 40, email: [%s]"
                        .formatted(customer.getEmail()), exception.getMessage());

        Assertions.assertEquals(customer, customerService.find(customer.getEmail()));
        Assertions.assertEquals(
                purchaseService.findAll(customer.getEmail()),
                List.of(

                )
                );
        Assertions.assertEquals(
                opinionService.findAll(customer.getEmail()),

                );

    }

}
