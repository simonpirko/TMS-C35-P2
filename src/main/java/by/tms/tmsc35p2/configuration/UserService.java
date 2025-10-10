package by.tms.tmsc35p2.configuration;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public boolean register(User user) {
        Optional<User> existing = repository.findByEmail(user.getEmail());
        if (existing.isPresent()) return false;
        repository.save(user);
        return true;
    }

    public Optional<User> login(String email, String password) {
        return repository.findByEmail(email)
                .filter(u -> u.getPassword().equals(password));
    }
}