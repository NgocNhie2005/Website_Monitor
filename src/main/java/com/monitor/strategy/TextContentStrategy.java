package com.monitor.strategy;

public class TextContentStrategy implements ComparisonStrategy{
    @Override
    public boolean hasChanged(String oldContent, String newContent) {
        String oldText = stripHtml(oldContent);
        String newText = stripHtml(newContent);
        return !oldText.equals(newText);
    }

    private String stripHtml(String html) {
        if (html == null) return "";
        return html.replaceAll("<[^>]*>", "").trim();
    }

    @Override
    public String getName() {
        return "Text Content";
    }
}
