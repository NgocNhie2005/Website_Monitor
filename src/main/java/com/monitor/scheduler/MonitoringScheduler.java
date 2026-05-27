package com.monitor.scheduler;

import com.monitor.channel.NotificationChannel;
import com.monitor.model.Subscription;
import com.monitor.model.Website;
import com.monitor.notification.Notification;
 
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MonitoringScheduler {
    private Duration interval;
    private List<Subscription> activeJobs;
 
    public MonitoringScheduler(Duration interval) {
        this.interval = interval;
        this.activeJobs = new ArrayList<>();
    }
 
    public void scheduleCheck(Subscription sub) {
        if (!activeJobs.contains(sub)) {
            activeJobs.add(sub);
            System.out.println("Scheduled check for: " + sub.getUrl());
        }
    }
 
    public void runCheck(Website website) {
        System.out.println("Running check for: " + website.getUrl());
        if (detectUpdate(website)) {
            notifyUser(website);
        }
    }
 
    public boolean detectUpdate(Website website) {
        return website.hasChanged();
    }
 
    public void notifyUser(Website website) {
        // Find the matching subscription and notify via its configured channel
        activeJobs.stream()
                .filter(sub -> sub.getUrl().equals(website.getUrl()))
                .findFirst()
                .ifPresent(sub -> {
                    Notification notification = new Notification(UUID.randomUUID().toString());
                    notification.generate(website.getUrl());
 
                    if (sub.getPreferences() != null) {
                        String channelType = sub.getPreferences().getChannel();
                        NotificationChannel channel = new NotificationChannel(channelType, "user@example.com");
                        notification.deliver(channel);
                    }
                });
    }
 
    public String getStatus() {
        return "MonitoringScheduler running with interval=" + interval
                + ", activeJobs=" + activeJobs.size();
    }
 
    public Duration getInterval()          { return interval; }
    public List<Subscription> getActiveJobs() { return activeJobs; }
}
