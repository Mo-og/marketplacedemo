package com.example.marketplacedemo.controllers;

import com.example.marketplacedemo.entities.Product;
import com.example.marketplacedemo.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

import static com.example.marketplacedemo.Util.createErrorsMapResponse;
import static com.example.marketplacedemo.Util.ok;

@Controller
public class ProductController {

    private ProductService productService;

    @Autowired
    public void setProductService(ProductService service) {
        productService = service;
    }

    @GetMapping("/rest/products")
    @ResponseBody
    public List<Product> getAllProducts() {
        return productService.getAll();
    }

    @GetMapping("/rest/products/{id}")
    @ResponseBody
    public ResponseEntity<?> getProductById(@PathVariable Long id) {
        Optional<Product> productOptional = productService.getById(id);
        if (productOptional.isPresent())
            return ok(productOptional.get());
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/rest/products")
    public ResponseEntity<?> addProduct(@ModelAttribute @Valid Product product, BindingResult bindingResult) {
        ResponseEntity<?> ErrorsMap = createErrorsMapResponse(bindingResult);
        if (ErrorsMap != null) return ErrorsMap;
        productService.add(product);
        return ok();
    }

    @DeleteMapping("/rest/products/{id}")
    public ResponseEntity<?> removeProductById(@PathVariable Long id) {
        productService.removeById(id);
        return ok();
    }

}
