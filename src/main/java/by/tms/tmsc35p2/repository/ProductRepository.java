package by.tms.tmsc35p2.repository;

import by.tms.tmsc35p2.model.Product;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductRepository {

    private final DataSource dataSource;

    public ProductRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void save(Product product) {
        String sql = "INSERT INTO products (name, description, price, image_url) VALUES (?, ?, ?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, product.getName());
            stmt.setString(2, product.getDescription());
            stmt.setDouble(3, product.getPrice());
            stmt.setString(4, product.getImageUrl());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка сохранения продукта", e);
        }
    }

    public List<Product> findAll() {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products";
        try (Connection connection = dataSource.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                products.add(new Product(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getDouble("price"),
                        rs.getString("image_url")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка получения списка продуктов", e);
        }
        return products;
    }

    public List<Product> findProductsByName(String searchTerm) {
        String sql = "SELECT * FROM products WHERE name LIKE ?";
        List<Product> products = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, "%" + searchTerm + "%");

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    products.add(mapRowToProduct(rs));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при поиске продуктов", e);
        }

        return products;
    }

    private Product mapRowToProduct(ResultSet rs) {
        try {
            Product product = new Product();
            product.setId(rs.getLong("id"));
            product.setName(rs.getString("name"));
            product.setPrice(rs.getLong("price"));
            product.setDescription(rs.getString("description"));
            return product;
        } catch (SQLException e) {
            System.err.println("Ошибка при маппинге строки: " + e.getMessage());
            throw new RuntimeException("Ошибка при преобразовании строки в Product", e);
        }
    }
}