package com.example.marketplacedemo.controllers;

import com.example.marketplacedemo.entities.Client;
import com.example.marketplacedemo.services.ClientService;
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
public class ClientController {

    private ClientService clientService;

    @Autowired
    public void setClientService(ClientService service) {
        clientService = service;
    }

    @GetMapping("/rest/clients")
    @ResponseBody
    public List<Client> getAllClients() {
        return clientService.getAll();
    }

    @GetMapping("/rest/clients/{id}")
    @ResponseBody
    public ResponseEntity<?> getClientById(@PathVariable Long id) {
        Optional<Client> clientOptional = clientService.getById(id);
        if (clientOptional.isPresent())
            return ok(clientOptional.get());
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/rest/clients")
    public ResponseEntity<?> addClient(@ModelAttribute @Valid Client client, BindingResult bindingResult) {
        ResponseEntity<?> ErrorsMap = createErrorsMapResponse(bindingResult);
        if (ErrorsMap != null) return ErrorsMap;
        clientService.add(client);
        return ok();
    }
    @DeleteMapping("/rest/clients/{id}")
    public ResponseEntity<?> removeClientById(@PathVariable Long id) {
        clientService.removeById(id);
        return ok();
    }

}
