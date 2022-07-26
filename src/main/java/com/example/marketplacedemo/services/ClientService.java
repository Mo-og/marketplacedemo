package com.example.marketplacedemo.services;

import com.example.marketplacedemo.entities.Client;
import com.example.marketplacedemo.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {
    private ClientRepository clientRepository;

    @Autowired
    public void setClientRepository(ClientRepository repository) {
        clientRepository = repository;
    }

    public List<Client> getAll() {
        return clientRepository.findAllByOrderById();
    }

    public Optional<Client> getById(Long id) {
        return clientRepository.findById(id);
    }

    public void save(Client client) {
        clientRepository.save(client);
    }

    public void removeById(Long id) {
        clientRepository.deleteById(id);
    }
}
