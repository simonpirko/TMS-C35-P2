package by.tms.tmsc35p2.controller;

import by.tms.tmsc35p2.configuration.Product;
import by.tms.tmsc35p2.service.ProductService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("products", service.getAll());
        return "products/list";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("product", new Product());
        return "products/create";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute Product product,
                         @RequestParam("file") MultipartFile file) throws IOException {
        if (!file.isEmpty()) {
            product.setImage(file.getBytes()); // сохраняем картинку в Postgre
        }
        service.create(product);
        return "redirect:/products";
    }

    // отдаём картинку по id
    @GetMapping("/{id}/image")
    public void showImage(@PathVariable Long id, HttpServletResponse response) throws IOException {
        Product product = service.getById(id);
        if (product != null && product.getImage() != null) {
            response.setContentType("image/jpeg");
            response.getOutputStream().write(product.getImage());
        }
    }
}
