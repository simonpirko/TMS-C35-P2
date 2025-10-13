package by.tms.tmsc35p2.dao;

import by.tms.tmsc35p2.configuration.Product;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ProductDao {

    private final JdbcTemplate jdbcTemplate;

    public ProductDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private Product mapRow(ResultSet rs, int rowNum) throws SQLException {
        Product p = new Product();
        p.setId(rs.getLong("id"));
        p.setTitle(rs.getString("title"));
        p.setDescription(rs.getString("description"));
        p.setPrice(rs.getDouble("price"));
        p.setImage(rs.getBytes("image"));
        return p;
    }

    public void save(Product product) {
        jdbcTemplate.update(
                "insert into products (title, description, price, image) values (?, ?, ?, ?)",
                product.getTitle(), product.getDescription(), product.getPrice(), product.getImage()
        );
    }

    public List<Product> findAll() {
        return jdbcTemplate.query("select * from products", this::mapRow);
    }

    public Product findById(Long id) {
        return jdbcTemplate.queryForObject(
                "select * from products where id = ?",
                this::mapRow,
                id
        );
    }
}