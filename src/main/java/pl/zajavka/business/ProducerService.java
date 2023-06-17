package pl.zajavka.business;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class ProducerService {

    private ProductService productService;
    private ProducerRepository producerRepository;

    public void removeAll(){
        productService.removeAll();
        producerRepository.removeAll();
    }
}

