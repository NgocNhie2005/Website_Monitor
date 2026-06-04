package com.monitor;

import com.monitor.model.*;
import com.monitor.strategy.*;

import java.util.UUID;

public class Main {
    public static void main(String[] args) {

        System.out.println("== Strategy: Content Size ==");
        runDemo(new ContentSizeStrategy());

        System.out.println("\n== Strategy: HTML Content ==");
        runDemo(new HtmlContentStrategy());

        System.out.println("\n== Strategy: Text Content ==");
        runDemo(new TextContentStrategy());
    }

    private static void runDemo(ComparisonStrategy strategy) {
        // User 1 with 2 subscriptions
        User alice = new User("u1", "alice@example.com", "Alice");

        Subscription sub1 = new Subscription(UUID.randomUUID().toString(), "https://wikipedia.org");
        sub1.setPreferences(new NotiPreference(Frequency.HOURLY, "EMAIL"));

        Subscription sub2 = new Subscription(UUID.randomUUID().toString(), "https://github.com");
        sub2.setPreferences(new NotiPreference(Frequency.DAILY, "SMS"));

        alice.addSubscription(sub1);
        alice.addSubscription(sub2);

        // User 2 with 1 subscription
        User bob = new User("u2", "bob@example.com", "Bob");

        Subscription sub3 = new Subscription(UUID.randomUUID().toString(), "https://github.com");
        sub3.setPreferences(new NotiPreference(Frequency.MINUTELY, "EMAIL"));

        bob.addSubscription(sub3);

        // Website 1 - observed by alice sub1 and bob sub3
        Website site1 = new Website("https://wikipedia.org", strategy);
        site1.addObserver(sub1);
        site1.addObserver(sub3);

        // Website 2 - observed by alice sub2 only
        Website site2 = new Website("https://github.com", strategy);
        site2.addObserver(sub2);

        System.out.println("-- Checking site1 --");
        System.out.println("-- First check --");
        site1.check();

        System.out.println("-- Second check --");
        site1.check();

        System.out.println("-- Checking site2 --");
        site2.check();

        System.out.println("Strategy used: " + strategy.getName());
    }
}