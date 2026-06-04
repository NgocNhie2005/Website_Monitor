package com.monitor.strategy;

public class ContentSizeStrategy implements ComparisonStrategy{
    @Override
    public boolean hasChanged(String oldContent, String newContent) {
        return oldContent.length() != newContent.length();
    }

    @Override
    public String getName() {
        return "Content Size";
    }
}
