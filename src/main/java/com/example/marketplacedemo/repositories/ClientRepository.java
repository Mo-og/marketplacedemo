package com.example.marketplacedemo.repositories;

import com.example.marketplacedemo.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    List<Client> findAllByOrderById();
}
