package com.monitor.observer;

public interface WebsiteSubject {
    void addObserver(WebsiteObserver observer);
    void removeObserver(WebsiteObserver observer);
    void notifyObservers();
}
