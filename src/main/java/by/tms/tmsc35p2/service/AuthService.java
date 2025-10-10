package by.tms.tmsc35p2.service;

import by.tms.tmsc35p2.model.User;
import by.tms.tmsc35p2.model.UserRole;
import by.tms.tmsc35p2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class AuthService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    
    @Autowired
    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder(12);
    }
    

    public User registerUser(String username, String email, String password, UserRole role, String shopName) {

        if (userRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("Пользователь с именем '" + username + "' уже существует");
        }
        

        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Пользователь с email '" + email + "' уже существует");
        }
        

        if (role == UserRole.SELLER && (shopName == null || shopName.trim().isEmpty())) {
            throw new IllegalArgumentException("Продавец должен указать название своего магазина");
        }
        

        String hashedPassword = passwordEncoder.encode(password);
        

        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(hashedPassword);
        user.setRole(role);
        user.setShopName(role == UserRole.SELLER ? shopName : null);
        

        return userRepository.save(user);
    }
    

    public User authenticateUser(String username, String password) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        
        if (userOptional.isEmpty()) {
            throw new IllegalArgumentException("Пользователь с именем '" + username + "' не найден");
        }
        
        User user = userOptional.get();

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("Неверный пароль");
        }
        
        return user;
    }
    

    public User authenticateUserByEmail(String email, String password) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        
        if (userOptional.isEmpty()) {
            throw new IllegalArgumentException("Пользователь с email '" + email + "' не найден");
        }
        
        User user = userOptional.get();

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("Неверный пароль");
        }
        
        return user;
    }
    

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }
    

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }
    

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
