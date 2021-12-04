package com.example.fragmentproject.Bean;

public class Account {
    private int id;
    private String name;
    private int amount;

    public Account(String name, int amount) {
        this.name = name;
        this.amount = amount;
    }

    public Account(int id, String name, int amount) {
         this(name,amount);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", amount=" + amount +
                '}';
    }
}
