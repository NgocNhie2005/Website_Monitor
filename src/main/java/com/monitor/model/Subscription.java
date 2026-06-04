package com.monitor.model;

import com.monitor.channel.NotificationChannel;
import com.monitor.notification.Notification;
import com.monitor.observer.WebsiteObserver;

import java.time.LocalDateTime;
import java.util.UUID;

public class Subscription implements WebsiteObserver {
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

    @Override
    public void onWebsiteChanged(Website website) {
        if (status != Status.ACTIVE) return;
        System.out.println("Subscription notified: " + url);

        Notification notification = new Notification(UUID.randomUUID().toString());
        notification.generate(website.getUrl());

        if (preferences != null) {
            NotificationChannel channel = new NotificationChannel(
                preferences.getChannel(), "user@example.com"
            );
            notification.deliver(channel);
        }
    }

    public void modify(String newUrl) { this.url = newUrl; }
    public void cancel()              { this.status = Status.CANCELLED; }

    public void setPreferences(NotiPreference preferences) { this.preferences = preferences; }
    public NotiPreference getPreferences() { return preferences; }
    public String getUrl()                 { return url; }
    public String getSubscriptionId()      { return subscriptionId; }
    public Status getStatus()              { return status; }
    public LocalDateTime getCreatedAt()    { return createdAt; }
}