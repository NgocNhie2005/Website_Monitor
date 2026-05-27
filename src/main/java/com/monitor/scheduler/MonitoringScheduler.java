package com.monitor.scheduler;

import com.monitor.model.Subscription;
import com.monitor.model.Website;
import com.monitor.observer.WebsiteObserver;
import com.monitor.observer.WebsiteSubject;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class MonitoringScheduler implements WebsiteSubject {
    private Duration interval;
    private List<Subscription> activeJobs;
    private List<WebsiteObserver> observers;
    private Website currentWebsite;

    public MonitoringScheduler(Duration interval) {
        this.interval = interval;
        this.activeJobs = new ArrayList<>();
        this.observers = new ArrayList<>();
    }

    @Override
    public void addObserver(WebsiteObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(WebsiteObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (WebsiteObserver observer : observers) {
            observer.onWebsiteChanged(currentWebsite);
        }
    }

    public void scheduleCheck(Subscription sub) {
        if (!activeJobs.contains(sub)) {
            activeJobs.add(sub);
            System.out.println("Scheduled check for: " + sub.getUrl());
        }
    }

    public void runCheck(Website website) {
        this.currentWebsite = website;
        System.out.println("Running check for: " + website.getUrl());
        if (detectUpdate(website)) {
            notifyObservers();
        }
    }

    public boolean detectUpdate(Website website) {
        return website.hasChanged();
    }

    public String getStatus() {
        return "MonitoringScheduler running with interval=" + interval
                + ", activeJobs=" + activeJobs.size();
    }

    public Duration getInterval() { return interval; }
    public List<Subscription> getActiveJobs() { return activeJobs; }
}
