package com.PrintHobe.PrintingManagement.DTOs;

public class UpdateAvailableTillRequest {
    private String time; // Expected format: "HH:mm"
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
