package com.monitor.strategy;

public class HtmlContentStrategy implements ComparisonStrategy{
    @Override
    public boolean hasChanged(String oldContent, String newContent) {
        return !oldContent.equals(newContent);
    }

    @Override
    public String getName() {
        return "HTML Content";
    }
}
