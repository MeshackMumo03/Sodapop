package com.example.sodapop;

public class Order {
        public String name;
        public String drink;
        public String branch;
        public String amount;



        public Order(String name, String drink, String branch, String amount) {
            this.name = name;
            this.drink = drink;
            this.branch = branch;
            this.amount = amount;
        }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDrink() {
        return drink;
    }

    public void setDrink(String drink) {
        this.drink = drink;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public Order() {

    }
}
