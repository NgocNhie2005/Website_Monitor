package com.monitor.scheduler;

import com.monitor.model.Subscription;
import com.monitor.model.Website;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

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
        website.check();
    }

    public String getStatus() {
        return "MonitoringScheduler running with interval=" + interval + ", activeJobs=" + activeJobs.size();
    }

    public Duration getInterval()             { return interval; }
    public List<Subscription> getActiveJobs() { return activeJobs; }
}