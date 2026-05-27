package com.monitor.model;

import java.time.LocalDateTime;

public class Subscription {
    private String subscriptionId;
    private String url;
    private Status status;
    private LocalDateTime createdAt;
    private NotiPreference preferences;
 
    public Subscription(String subscriptionId, String url) {
        this.subscriptionId = subscriptionId;
        this.url = url;
        this.status = Status.ACTIVE;
        this.createdAt = LocalDateTime.now();
    }
 
    public void modify(String newUrl) {
        this.url = newUrl;
    }
 
    public void cancel() {
        this.status = Status.CANCELLED;
    }
 
    public NotiPreference getPreferences() {
        return preferences;
    }
 
    public void setPreferences(NotiPreference preferences) {
        this.preferences = preferences;
    }
 
    public String getUrl() { return url; }
 
    public String getSubscriptionId() { return subscriptionId; }
    public Status getStatus()         { return status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
