package pl.zajavka.business;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.zajavka.domain.Product;

@Service
@AllArgsConstructor
public class ProductService {

    private ProductRepository productRepository;

    public void removeAll(){
        productRepository.removeAll();
    }

    public Product create(Product product) {
        return productRepository.create(product);
    }
}