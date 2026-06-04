package com.monitor;

import com.monitor.model.*;
import com.monitor.strategy.*;

import java.util.UUID;

public class Main {
    public static void main(String[] args) {

        System.out.println("=== Strategy: Content Size ===");
        runDemo(new ContentSizeStrategy());

        System.out.println("\n=== Strategy: HTML Content ===");
        runDemo(new HtmlContentStrategy());

        System.out.println("\n=== Strategy: Text Content ===");
        runDemo(new TextContentStrategy());
    }

    private static void runDemo(ComparisonStrategy strategy) {
        User user = new User("u1", "alice@example.com", "Alice");

        NotiPreference pref = new NotiPreference(Frequency.HOURLY, "EMAIL");

        Subscription sub = new Subscription(UUID.randomUUID().toString(), "https://example.com");
        sub.setPreferences(pref);
        user.addSubscription(sub);

        Website website = new Website("https://example.com", strategy);
        website.addObserver(sub);  // Subscription is the observer

        website.check();

        System.out.println("Strategy used: " + strategy.getName());
    }
}