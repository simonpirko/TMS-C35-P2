package by.tms.tmsc35p2.service;

import by.tms.tmsc35p2.model.Product;
import by.tms.tmsc35p2.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void save(Product product) {
        productRepository.save(product);
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public List<Product> searchProducts(String name) {
        return productRepository.findProductsByName(name);
    }
}