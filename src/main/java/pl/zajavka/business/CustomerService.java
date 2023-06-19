package pl.zajavka.business;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.zajavka.domain.Customer;

@Service
@AllArgsConstructor
public class CustomerService {

    private OpinionService opinionService;
    private PurchaseService purchaseService;
    private CustomerRepository customerRepository;

    @Transactional
    public Customer create(Customer customer){
        return customerRepository.create(customer);
    }

    @Transactional
    public void removeAll() {
        opinionService.removeAll();
        purchaseService.removeAll();
        customerRepository.removeAll();
    }

    public Customer find(String email) {
        return customerRepository.find(email)
                .orElseThrow(()-> new RuntimeException("Customer with email: [%s] is missing".formatted(email)));
    }

    @Transactional
    public void remove(String email) {
    Customer existingCustomer = find(email);

    //TODO
    opinionService.removeAll(email);

    //TODO
    purchaseService.removeAll(email);
    }
}




