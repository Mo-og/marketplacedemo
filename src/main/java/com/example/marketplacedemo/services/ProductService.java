package com.example.marketplacedemo.services;

import com.example.marketplacedemo.entities.Client;
import com.example.marketplacedemo.entities.Product;
import com.example.marketplacedemo.repositories.ClientRepository;
import com.example.marketplacedemo.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private ProductRepository productRepository;
    private ClientRepository clientRepository;

    @Autowired
    public void setProductRepository(ProductRepository repository) {
        productRepository = repository;
    }

    @Autowired
    public void setProductRepository(ClientRepository repository) {
        clientRepository = repository;
    }

    public List<Product> getAll() {
        return productRepository.findAll();
    }

    public Optional<Product> getById(Long id) {
        return productRepository.findById(id);
    }

    public void add(Product product) {
        productRepository.save(product);
    }

    public void removeById(Long id) {
        HashSet<Client> clientHashSet = new HashSet<>();
        Optional<Product> optionalProduct = getById(id);
        if (optionalProduct.isEmpty()) return;
        Product product = optionalProduct.get();
        product.getClients().forEach(client -> {
            client.removeProduct(product);
            clientHashSet.add(client);
        });
        clientRepository.saveAll(clientHashSet);
        productRepository.delete(product);
    }
}