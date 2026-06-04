package com.monitor.model;

import com.monitor.observer.WebsiteObserver;
import com.monitor.observer.WebsiteSubject;
import com.monitor.strategy.ComparisonStrategy;
import com.monitor.strategy.HtmlContentStrategy;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Website implements WebsiteSubject {
    private String url;
    private LocalDateTime lastChecked;
    private String lastContent;
    private ComparisonStrategy strategy;
    private List<WebsiteObserver> observers;

    public Website(String url) {
        this.url = url;
        this.lastContent = "";
        this.strategy = new HtmlContentStrategy();
        this.observers = new ArrayList<>();
    }

    public Website(String url, ComparisonStrategy strategy) {
        this.url = url;
        this.lastContent = "";
        this.strategy = strategy;
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
            observer.onWebsiteChanged(this);
        }
    }

    public String fetchContent() {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
            } catch (Exception e) {
            System.err.println("Failed to fetch: " + url + " - " + e.getMessage());
            return "";
        }
    }

    public void check() {
        String freshContent = fetchContent();
        boolean changed = strategy.hasChanged(lastContent, freshContent);
        lastContent = freshContent;
        lastChecked = LocalDateTime.now();
        if (changed) {
            notifyObservers();
        } else {
            System.out.println("No change detected on: " + url + " (strategy: " + strategy.getName() + ")");
        }
    }

    public void setStrategy(ComparisonStrategy strategy) { this.strategy = strategy; }
    public ComparisonStrategy getStrategy()  { return strategy; }
    public String getUrl()                   { return url; }
    public LocalDateTime getLastChecked()    { return lastChecked; }
}