package com.monitor.notification;
 
import com.monitor.channel.NotificationChannel;
import com.monitor.model.Website;
import com.monitor.observer.WebsiteObserver;

import java.time.LocalDateTime;
import java.util.UUID;

public class Notification implements WebsiteObserver{
    private String notifId;
    private String content;
    private LocalDateTime createdAt;
    private NotificationChannel channel;

    public Notification(NotificationChannel channel) {
        this.notifId = UUID.randomUUID().toString();
        this.createdAt = LocalDateTime.now();
        this.channel = channel;
    }

    @Override
    public void onWebsiteChanged(Website website) {
        generate(website.getUrl());
        deliver(channel);
    }

    public void generate(String websiteUrl) {
        this.content = "Change detected on website: " + websiteUrl + " at " + LocalDateTime.now();
        System.out.println("Notification generated: " + content);
    }

    public void deliver(NotificationChannel channel) {
        channel.send(this);
    }

    public String getContent() { return content; }
    public String getNotifId() { return notifId; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
