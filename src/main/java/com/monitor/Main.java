package com.monitor;
import com.monitor.channel.NotificationChannel;
import com.monitor.model.*;
import com.monitor.notification.Notification;
import com.monitor.scheduler.MonitoringScheduler;
 
import java.time.Duration;
import java.util.UUID;
public class Main {
    public static void main(String[] args) {
        // 1. Create user
        User user = new User("u1", "alice@example.com", "Alice");
        user.register();
 
        // 2. Set up notification preference
        NotiPreference pref = new NotiPreference(Frequency.HOURLY, "EMAIL");
 
        // 3. Create subscription
        Subscription sub = new Subscription(UUID.randomUUID().toString(), "https://example.com");
        sub.setPreferences(pref);
        user.addSubscription(sub);
 
        // 4. Create website
        Website website = new Website("https://example.com");
 
        // 5. Schedule monitoring
        MonitoringScheduler scheduler = new MonitoringScheduler(Duration.ofMinutes(60));
        scheduler.scheduleCheck(sub);
        scheduler.runCheck(website);
 
        System.out.println(scheduler.getStatus());
 
        // 6. Direct notification delivery
        Notification notification = new Notification(UUID.randomUUID().toString());
        notification.generate("https://example.com");
        NotificationChannel channel = new NotificationChannel("EMAIL", "alice@example.com");
        notification.deliver(channel);
    }
}
