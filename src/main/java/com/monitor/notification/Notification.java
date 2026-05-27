package com.monitor.notification;
import com.monitor.channel.NotificationChannel;
 
import java.time.LocalDateTime;
public class Notification {
    private String notifId;
    private String content;
    private LocalDateTime createdAt;
 
    public Notification(String notifId) {
        this.notifId = notifId;
        this.createdAt = LocalDateTime.now();
    }
 
    public void generate(String websiteUrl) {
        this.content = "Change detected on website: " + websiteUrl
                + " at " + LocalDateTime.now();
        System.out.println("Notification generated: " + content);
    }
 
    public void deliver(NotificationChannel channel) {
        channel.send(this);
    }
 
    public String getContent() { return content; }
 
    public String getNotifId()        { return notifId; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setContent(String content) { this.content = content; }
}
