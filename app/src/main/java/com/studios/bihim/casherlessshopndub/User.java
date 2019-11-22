package com.studios.bihim.casherlessshopndub;

public class User
{
    String username, email, fullname, balance,order;

    public User()
    {

    }

    public User(String username, String email, String fullname,String balance, String order)
    {
        this.email = email;
        this.username = username;
        this.fullname = fullname;
        this.balance = balance;
        this.order = order;
    }

    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }
}
