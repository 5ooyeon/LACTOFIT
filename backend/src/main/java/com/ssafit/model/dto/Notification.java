package com.ssafit.model.dto;

import java.sql.Timestamp;

public class Notification {
    private int notificationId;
    private int userId;
    private String notificationContent;
    private boolean notificationRead;
    private Timestamp notificationRegDate;

    public Notification() {}

    public Notification(int notificationId, int userId, String notificationContent, boolean notificationRead, Timestamp notificationRegDate) {
        this.notificationId = notificationId;
        this.userId = userId;
        this.notificationContent = notificationContent;
        this.notificationRead = notificationRead;
        this.notificationRegDate = notificationRegDate;
    }

    public int getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(int notificationId) {
        this.notificationId = notificationId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getNotificationContent() {
        return notificationContent;
    }

    public void setNotificationContent(String notificationContent) {
        this.notificationContent = notificationContent;
    }

    public boolean isNotificationRead() {
        return notificationRead;
    }

    public void setNotificationRead(boolean notificationRead) {
        this.notificationRead = notificationRead;
    }

    public Timestamp getNotificationRegDate() {
        return notificationRegDate;
    }

    public void setNotificationRegDate(Timestamp notificationRegDate) {
        this.notificationRegDate = notificationRegDate;
    }

    @Override
    public String toString() {
        return "Notification [notificationId=" + notificationId + ", userId=" + userId + ", notificationContent=" + notificationContent + ", notificationRead=" + notificationRead + ", notificationRegDate=" + notificationRegDate + "]";
    }
}
