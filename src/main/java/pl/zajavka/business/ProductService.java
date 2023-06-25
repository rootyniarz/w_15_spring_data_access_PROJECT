package pl.zajavka.business;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.zajavka.domain.Product;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductService {

    private ProductRepository productRepository;

    public Product create(Product product) {
        return productRepository.create(product);
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public void removeAll(){
        productRepository.removeAll();
    }
}