package com.monitor.model;
import java.time.LocalDateTime;
public class Website {
    private String url;
    private LocalDateTime lastChecked;
    private String contentHash;
 
    public Website(String url) {
        this.url = url;
        this.contentHash = "";
    }
 
    public String fetchContent() {
        // Simulated fetch – in production would use HttpClient
        System.out.println("Fetching content from: " + url);
        return "<html>Simulated content from " + url + "</html>";
    }
 
    public boolean hasChanged() {
        String freshContent = fetchContent();
        String freshHash = String.valueOf(freshContent.hashCode());
        if (!freshHash.equals(contentHash)) {
            contentHash = freshHash;
            lastChecked = LocalDateTime.now();
            return true;
        }
        lastChecked = LocalDateTime.now();
        return false;
    }
 
    public String getUrl() { return url; }
 
    public LocalDateTime getLastChecked() { return lastChecked; }
    public String getContentHash()        { return contentHash; }
    public void setContentHash(String hash) { this.contentHash = hash; }
    public void setLastChecked(LocalDateTime time) { this.lastChecked = time; }
}
