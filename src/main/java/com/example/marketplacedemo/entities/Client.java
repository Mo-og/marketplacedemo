package com.example.marketplacedemo.entities;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;

@Entity
public class Client {
    @Id
    @SequenceGenerator(name = "client_seq",
            sequenceName = "client_sequence",
            initialValue = 1, allocationSize = 20)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "client_seq")
    @Column(nullable = false)
    private Long id;
    @Pattern(regexp = "\\p{L}{2,}", message = "Client's name must consist of at least 2 letters (and no other symbols).")
    private String firstName;
    @Pattern(regexp = "\\p{L}{2,}", message = "Client's last name must consist of at least 2 letters (and no other symbols).")
    private String lastName;

    private BigDecimal balance;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Client client = (Client) o;

        if (getId() != null ? !getId().equals(client.getId()) : client.getId() != null) return false;
        if (getFirstName() != null ? !getFirstName().equals(client.getFirstName()) : client.getFirstName() != null)
            return false;
        if (getLastName() != null ? !getLastName().equals(client.getLastName()) : client.getLastName() != null)
            return false;
        return getBalance() != null ? getBalance().equals(client.getBalance()) : client.getBalance() == null;
    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }
}
