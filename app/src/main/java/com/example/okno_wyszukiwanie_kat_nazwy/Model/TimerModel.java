package com.example.okno_wyszukiwanie_kat_nazwy.Model;

public class TimerModel {
    private String TimerId;
    private String TimerName;
    private String TimerTime;

    public TimerModel(String TimerId, String TimerName, String TimerTime) {
        this.TimerId = TimerId;
        this.TimerName = TimerName;
        this.TimerTime = TimerTime;
    }

    //Get methods
    public String getTimerId() {
        return TimerId;
    }

    public String getTimerName() {
        return TimerName;
    }

    public String getTimerTime() {
        return TimerTime;
    }


    //Set methods
    public void setTimerId(String TimerId) {
        this.TimerId = TimerId;
    }

    public void setTimerName(String TimerName) { this.TimerName = TimerName; }

    public void setTimerTime(String TimerTime) {
        this.TimerTime = TimerTime;
    }
}
