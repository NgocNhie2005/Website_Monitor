package com.monitor.model;
import java.util.ArrayList;
import java.util.List;
public class User {
    private String userId;
    private String email;
    private String name;
    private List<Subscription> subscriptions;
 
    public User(String userId, String email, String name) {
        this.userId = userId;
        this.email = email;
        this.name = name;
        this.subscriptions = new ArrayList<>();
    }
 
    public void register() {
        System.out.println("User " + name + " registered with email: " + email);
    }
 
    public List<Subscription> manageSubscriptions() {
        return subscriptions;
    }
 
    public void addSubscription(Subscription subscription) {
        subscriptions.add(subscription);
    }
 
    public String getUserId() { return userId; }
    public String getEmail()   { return email; }
    public String getName()    { return name; }
}
