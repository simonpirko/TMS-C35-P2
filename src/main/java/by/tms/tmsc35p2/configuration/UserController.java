package by.tms.tmsc35p2.configuration;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static org.springframework.http.HttpStatus.*;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") @Valid User user,
                               BindingResult result,
                               Model model) {
        if (result.hasErrors()) {
            return "register";
        }

        boolean success = userService.register(user);
        if (!success) {
            model.addAttribute("error", "Пользователь с таким email уже существует");
            return "register";
        }

        model.addAttribute("message", "Регистрация успешна! Теперь войдите в систему.");
        return "login";
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        return "login";
    }

    @PostMapping("/login")
    public String login(
            @RequestParam String email,
            @RequestParam String password,
            Model model) {
        Optional<User> user = userService.login(email, password);
        if (user.isPresent()) {
            model.addAttribute("user", user.get());
            return "profile";
        } else {
            model.addAttribute("error", "Неверный email или пароль");
            return "login";
        }
    }

    @DeleteMapping("/user/delete")
    public ResponseEntity<String> deleteUser(@RequestParam Long id) {
        boolean deleted = userService.deleteById(id);
        if (deleted) {
            return ResponseEntity.ok("Пользователь успешно удалён");
        } else {
            return ResponseEntity.status(NOT_FOUND)
                    .body("Пользователь с таким ID не найден");
        }
    }
}
