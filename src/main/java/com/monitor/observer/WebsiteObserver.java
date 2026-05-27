package com.monitor.observer;

import com.monitor.model.Website;

public interface WebsiteObserver {
    void onWebsiteChanged(Website website);
}
