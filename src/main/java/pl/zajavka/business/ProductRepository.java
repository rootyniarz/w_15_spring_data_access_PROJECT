package pl.zajavka.business;

import pl.zajavka.domain.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    Product create(Product product);

    List<Product> findAll();

    void removeAll();

    Optional<Product> find(String productCode);
}
