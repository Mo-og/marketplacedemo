package com.example.marketplacedemo.entities;

import com.example.marketplacedemo.IllegalRequestInputException;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Product {
    @Id
    @SequenceGenerator(name = "product_seq", sequenceName = "product_sequence", allocationSize = 20)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_seq")
    @Column(nullable = false)
    private Long id;
    @Pattern(regexp = "\\p{L}+", message = "Name must consist of letters and have at least one.")
    private String name;
    @Min(value = 0, message = "Minimum allowed price is 0.")
    private BigDecimal price;
    @JsonIgnore
    @ManyToMany(mappedBy = "products")
    Set<Client> clients;

    public Product() {
    }

    public Product(Long id, String name, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Set<Client> getClients() {
        return new HashSet<>(clients);
    }

    public boolean addClient(Client client) throws IllegalRequestInputException {
        if (clients.contains(client)) return false;
        boolean res = clients.add(client);
        client.addProduct(this);
        return res;
    }

    public boolean refundClient(Client client) {
        if (!clients.contains(client)) return false;
        boolean res = clients.remove(client);
        client.refundProduct(this);
        return res;
    }

    public boolean removeClient(Client client) {
        if (!clients.contains(client)) return false;
        boolean res = clients.remove(client);
        client.removeProduct(this);
        return res;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        if (getId() != null ? !getId().equals(product.getId()) : product.getId() != null) return false;
        if (getName() != null ? !getName().equals(product.getName()) : product.getName() != null) return false;
        return getPrice() != null ? getPrice().equals(product.getPrice()) : product.getPrice() == null;
    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }


}
