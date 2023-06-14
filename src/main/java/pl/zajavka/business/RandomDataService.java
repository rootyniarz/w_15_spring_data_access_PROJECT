package pl.zajavka.business;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.zajavka.domain.*;

@Slf4j
@Service
@AllArgsConstructor
public class RandomDataService {

    private final RandomDataPreparationService randomDataPreparationService;
    private final CustomerRepository customerRepository;
    private final OpinionRepository opinionRepository;
    private final ProducerRepository producerRepository;
    private final ProductRepository productRepository;
    private final PurchaseRepository purchaseRepository;

    void create(){
        Customer customer = customerRepository.create(randomDataPreparationService.createCustomer());
        Opinion opinion = opinionRepository.create(randomDataPreparationService.createOpinion());
        Producer producer = producerRepository.create(randomDataPreparationService.createProducer());
        Product product = productRepository.create(randomDataPreparationService.createProduct());
        Purchase purchase = purchaseRepository.create(randomDataPreparationService.createPurchase());

    }



}
