package by.tms.tmsc35p2.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    
    
    private Long id;
    
    
    @NotBlank(message = "Имя пользователя не может быть пустым")
    @Size(min = 3, max = 50, message = "Имя пользователя должно содержать от 3 до 50 символов")
    private String username;
    
    
    @NotBlank(message = "Email не может быть пустым")
    @Email(message = "Некорректный формат email")
    private String email;
    
    
    @NotBlank(message = "Пароль не может быть пустым")
    @Size(min = 6, message = "Пароль должен содержать минимум 6 символов")
    private String password;
    
    
    @NotNull(message = "Роль пользователя должна быть указана")
    private UserRole role;
    
    
    private String shopName;
    
    
    public boolean isSeller() {
        return UserRole.SELLER.equals(this.role);
    }
    
    public boolean isBuyer() {
        return UserRole.BUYER.equals(this.role);
    }
}
