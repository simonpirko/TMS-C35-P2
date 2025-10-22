package by.tms.tmsc35p2.dao;

import by.tms.tmsc35p2.model.Product;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
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
        p.setName(rs.getString("name"));
        p.setDescription(rs.getString("description"));
        p.setPrice(rs.getDouble("price"));
        p.setImageUrl(Arrays.toString(rs.getBytes("imageUrl")));
        return p;
    }

    public void save(Product product) {
        jdbcTemplate.update(
                "insert into products (name, description, price, image) values (?, ?, ?, ?)",
                product.getName(), product.getDescription(), product.getPrice(), product.getImageUrl()
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
    public List<Product> findByName(String name) {
        String sql = "SELECT * FROM products WHERE LOWER(name) LIKE LOWER(?)";
        String searchPattern = "%" + name + "%";
        return jdbcTemplate.query(sql, this::mapRow, searchPattern);
    }

}