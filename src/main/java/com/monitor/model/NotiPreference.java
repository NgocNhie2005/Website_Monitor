package com.monitor.model;

public class NotiPreference {
    private Frequency frequency;
    private String channelType;
 
    public NotiPreference(Frequency frequency, String channelType) {
        this.frequency = frequency;
        this.channelType = channelType;
    }
 
    public void update(Frequency frequency, String channelType) {
        this.frequency = frequency;
        this.channelType = channelType;
    }
 
    public String getChannel() {
        return channelType;
    }
 
    public Frequency getFrequency() {
        return frequency;
    }
 
    public void setFrequency(Frequency frequency) { this.frequency = frequency; }
    public void setChannelType(String channelType) { this.channelType = channelType; }
}
