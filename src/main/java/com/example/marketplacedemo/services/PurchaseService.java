package com.example.marketplacedemo.services;

import com.example.marketplacedemo.IllegalRequestInputException;
import com.example.marketplacedemo.entities.Client;
import com.example.marketplacedemo.entities.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class PurchaseService {
    private ClientService clientService;
    private ProductService productService;

    @Autowired
    public void setClientService(ClientService service) {
        clientService = service;
    }

    @Autowired
    public void setProductService(ProductService service) {
        productService = service;
    }

    private Client getClientOrThrow(Long id) {
        return clientService.getById(id).orElseThrow(() -> new IllegalRequestInputException("No client found for id = " + id));
    }

    private Product getProductOrThrow(Long id) {
        return productService.getById(id).orElseThrow(() -> new IllegalRequestInputException("No product found for id = " + id));
    }

    public void makePurchase(Long clientId, Long productId) throws IllegalRequestInputException {
        Client client = getClientOrThrow(clientId);
        Product product = getProductOrThrow(productId);
        if (!client.addProduct(product))
            throw new IllegalRequestInputException("The client #" + clientId + " already has product '" + product.getName() + "'!");
        clientService.save(client);
    }

    public void refundPurchase(Long clientId, Long productId) throws IllegalRequestInputException {
        Client client = getClientOrThrow(clientId);
        Product product = getProductOrThrow(productId);

        if (!client.refundProduct(product))
            throw new IllegalRequestInputException("The client #" + clientId + " doesn't have product '" + product.getName() + "'!");
        clientService.save(client);
    }

    public Set<Product> getClientsProducts(Long clientId) {
        return getClientOrThrow(clientId).getProducts();
    }

    public Set<Client> getProductCustomers(Long productId) {
        return getProductOrThrow(productId).getClients();
    }
}
