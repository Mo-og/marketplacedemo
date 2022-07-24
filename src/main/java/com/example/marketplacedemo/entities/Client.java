package com.example.marketplacedemo.entities;

import com.example.marketplacedemo.IllegalRequestInputException;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Client {
    @Id
    @SequenceGenerator(name = "client_seq",
            sequenceName = "client_sequence", allocationSize = 20)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "client_seq")
    @Column(nullable = false)
    private Long id;
    @Pattern(regexp = "\\p{L}{2,}", message = "Client's name must consist of at least 2 letters (and no other symbols).")
    private String firstName;
    @Pattern(regexp = "\\p{L}{2,}", message = "Client's last name must consist of at least 2 letters (and no other symbols).")
    private String lastName;
    private BigDecimal balance;
    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "purchase", joinColumns = @JoinColumn(name = "client_id"), inverseJoinColumns = @JoinColumn(name = "course_id"))
    Set<Product> products;

    public Client() {
    }

    public Client(Long id, String firstName, String lastName, BigDecimal balance) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.balance = balance;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    //purchasing
    public boolean addProduct(Product product) throws IllegalRequestInputException {
        if (products.contains(product))
            return false;
        if (balance.compareTo(product.getPrice()) < 0)
            throw new IllegalRequestInputException("User has not enough money to make the purchase!");
        boolean res = products.add(product);
        balance = balance.subtract(product.getPrice());
        product.addClient(this);
        return res;
    }

    //removing of a product refunds customer
    public boolean refundProduct(Product product) {
        if (!products.contains(product)) return false;
        products.remove(product);
        product.refundClient(this);
        balance = balance.add(product.getPrice());
        return true;
    }

    public boolean removeProduct(Product product) {
        if (!products.contains(product)) return false;
        products.remove(product);
        product.refundClient(this);
        return true;
    }

    public Set<Product> getProducts() {
        return new HashSet<>(products);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Client client = (Client) o;

        if (getId() != null ? !getId().equals(client.getId()) : client.getId() != null) return false;
        if (getFirstName() != null ? !getFirstName().equals(client.getFirstName()) : client.getFirstName() != null)
            return false;
        return (getLastName() != null ? !getLastName().equals(client.getLastName()) : client.getLastName() != null);
    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }
}
