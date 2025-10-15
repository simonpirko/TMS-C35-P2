package by.tms.tmsc35p2.controller;

import by.tms.tmsc35p2.model.Product;
import by.tms.tmsc35p2.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("products", productService.findAll());
        return "products/list"; // => /WEB-INF/templates/products/list.html
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("product", new Product());
        return "products/add"; // => /WEB-INF/templates/products/add.html
    }

    @PostMapping("/add")
    public String add(@ModelAttribute Product product) {
        productService.save(product);
        return "redirect:/products";
    }
}