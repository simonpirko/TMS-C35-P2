package by.tms.tmsc35p2.configuration;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public class User {

    private Long id;

    @Size(min = 3, max = 30, message = "Длина имени пользователя должна составлять от 3 до 30 символов")
    private String username;

    @Email(message = "Неверный формат почты")
    private String email;

    @Size(min = 6, max = 30, message = "Пароль должен содержать не менее 6 символов")
    private String password;

    public User() {}

    public User(Long id, String username, String email, String password) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
