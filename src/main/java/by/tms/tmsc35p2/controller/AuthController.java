package by.tms.tmsc35p2.controller;

import by.tms.tmsc35p2.model.User;
import by.tms.tmsc35p2.model.UserRole;
import by.tms.tmsc35p2.service.AuthService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/auth")
public class AuthController {
    
    private final AuthService authService;
    
    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }
    

    @GetMapping("/login")
    public String showLoginForm(Model model, HttpSession session) {
        // Если пользователь уже авторизован, перенаправляем на главную
        if (session.getAttribute("user") != null) {
            return "redirect:/";
        }
        
        model.addAttribute("loginForm", new LoginForm());
        return "auth/login";
    }
    

    @PostMapping("/login")
    public String processLogin(@Valid LoginForm loginForm, 
                              BindingResult bindingResult, 
                              Model model,
                              HttpSession session) {
        // Если пользователь уже авторизован, перенаправляем на главную
        if (session.getAttribute("user") != null) {
            return "redirect:/";
        }
        
        if (bindingResult.hasErrors()) {
            return "auth/login";
        }
        
        try {
            User user = authService.authenticateUser(loginForm.getUsername(), loginForm.getPassword());
            session.setAttribute("user", user);
            model.addAttribute("successMessage", "Добро пожаловать, " + user.getUsername() + "!");
            model.addAttribute("user", user);
            return "auth/success";
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "auth/login";
        }
    }
    

    @GetMapping("/register")
    public String showRegisterForm(Model model, HttpSession session) {
        if (session.getAttribute("user") != null) {
            return "redirect:/";
        }
        
        model.addAttribute("registerForm", new RegisterForm());
        model.addAttribute("userRoles", UserRole.values());
        return "auth/register";
    }
    

    @PostMapping("/register")
    public String processRegister(@Valid RegisterForm registerForm, 
                                 BindingResult bindingResult, 
                                 Model model,
                                 HttpSession session) {
        if (session.getAttribute("user") != null) {
            return "redirect:/";
        }
        
        if (bindingResult.hasErrors()) {
            model.addAttribute("userRoles", UserRole.values());
            return "auth/register";
        }
        
        try {
            User user = authService.registerUser(
                    registerForm.getUsername(),
                    registerForm.getEmail(),
                    registerForm.getPassword(),
                    registerForm.getRole(),
                    registerForm.getShopName()
            );
            
            model.addAttribute("successMessage", "Регистрация успешно завершена! Добро пожаловать, " + user.getUsername() + "!");
            model.addAttribute("user", user);
            return "auth/success";
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("userRoles", UserRole.values());
            return "auth/register";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    @GetMapping("/profile")
    public String profile(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/auth/login";
        }
        model.addAttribute("user", user);
        return "auth/profile";
    }
    

    public static class LoginForm {
        private String username;
        private String password;
        
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
    

    public static class RegisterForm {
        private String username;
        private String email;
        private String password;
        private String confirmPassword;
        private UserRole role;
        private String shopName;
        
        public String getUsername() {
            return username;
        }
        
        public void setUsername(String username) {
            this.username = username;
        }
        
        public String getEmail() {
            return email;
        }
        
        public void setEmail(String email) {
            this.email = email;
        }
        
        public String getPassword() {
            return password;
        }
        
        public void setPassword(String password) {
            this.password = password;
        }
        
        public String getConfirmPassword() {
            return confirmPassword;
        }
        
        public void setConfirmPassword(String confirmPassword) {
            this.confirmPassword = confirmPassword;
        }
        
        public UserRole getRole() {
            return role;
        }
        
        public void setRole(UserRole role) {
            this.role = role;
        }
        
        public String getShopName() {
            return shopName;
        }
        
        public void setShopName(String shopName) {
            this.shopName = shopName;
        }
    }
}
