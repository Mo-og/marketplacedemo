package com.example.marketplacedemo.services;

import com.example.marketplacedemo.entities.Product;
import com.example.marketplacedemo.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private ProductRepository productRepository;

    @Autowired
    public void setProductRepository(ProductRepository repository) {
        productRepository = repository;
    }

    public List<Product> getAll() {
        return productRepository.findAll();
    }

    public Optional<Product> getById(Long id) {
        return productRepository.findById(id);
    }

    public void add(Product product){
        productRepository.save(product);
    }

    public void removeById(Long id) {
        productRepository.deleteById(id);
    }
}