package com.example.marketplacedemo.controllers;

import com.example.marketplacedemo.services.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import static com.example.marketplacedemo.Util.ok;

@Controller
public class PurchaseController {

    private PurchaseService purchaseService;

    @Autowired
    public void setPurchaseService(PurchaseService service) {
        purchaseService = service;
    }

    @GetMapping("/rest/purchases/client/{id}")
    public ResponseEntity<?> getClientsProducts(@PathVariable Long id) {
        return ok(purchaseService.getClientsProducts(id));
    }

    @GetMapping("/rest/purchases/product/{id}")
    public ResponseEntity<?> getProductCustomers(@PathVariable Long id) {
        return ok(purchaseService.getProductCustomers(id));
    }

    @PostMapping("/rest/purchases")
    public ResponseEntity<?> makeNewPurchase(Long clientId, Long productId) {
        purchaseService.makePurchase(clientId, productId);
        return ok();
    }

    @DeleteMapping("/rest/purchases")
    public ResponseEntity<?> refundPurchase(Long clientId, Long productId) {
        purchaseService.refundPurchase(clientId, productId);
        return ok();
    }
}
