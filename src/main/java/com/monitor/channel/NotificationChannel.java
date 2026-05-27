package com.monitor.channel;

import com.monitor.notification.Notification;

public class NotificationChannel {
    private String channelType;
    private String address;
 
    public NotificationChannel(String channelType, String address) {
        this.channelType = channelType;
        this.address = address;
    }
 
    public void send(Notification notification) {
        if (validate()) {
            System.out.println("[" + channelType.toUpperCase() + "] Sending to " + address
                    + ": " + notification.getContent());
        } else {
            System.err.println("Invalid channel configuration for type: " + channelType);
        }
    }
 
    public boolean validate() {
        return address != null && !address.isBlank();
    }
 
    public String getType() { return channelType; }
 
    public String getChannelType() { return channelType; }
    public String getAddress()     { return address; }
}
