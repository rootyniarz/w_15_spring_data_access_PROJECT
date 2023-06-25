package pl.zajavka.business;

import pl.zajavka.domain.Product;

import java.util.List;

public interface ProductRepository {
    Product create(Product product);

    List<Product> findAll();

    void removeAll();
}
