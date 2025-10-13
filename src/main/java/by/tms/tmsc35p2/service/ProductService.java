package by.tms.tmsc35p2.service;

import by.tms.tmsc35p2.configuration.Product;
import by.tms.tmsc35p2.dao.ProductDao;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductDao dao;

    public ProductService(ProductDao dao) {
        this.dao = dao;
    }

    public void create(Product product) {
        dao.save(product);
    }

    public List<Product> getAll() {
        return dao.findAll();
    }

    public Product getById(Long id) {
        return dao.findById(id);
    }
}