package pl.zajavka.business;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.zajavka.domain.Producer;

import java.util.List;

@Service
@AllArgsConstructor
public class ProducerService {

    private ProductService productService;
    private ProducerRepository producerRepository;

    public Producer create(Producer producer) {
        return producerRepository.create(producer);
    }

    public List<Producer> findAll() {
        return producerRepository.findAll();
    }

    public void removeAll(){
        productService.removeAll();
        producerRepository.removeAll();
    }
}

