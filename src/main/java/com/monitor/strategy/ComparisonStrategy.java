package com.monitor.strategy;

public interface ComparisonStrategy {
    boolean hasChanged(String oldContent, String newContent);
    String getName();
}
