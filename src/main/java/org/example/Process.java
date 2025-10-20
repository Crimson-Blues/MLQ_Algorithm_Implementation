package org.example;

public class Process {
    private String id;
    private int burstTime;
    private int remainingBurstTime;
    private int arrivalTime;
    private int queue;
    private int priority;
    private int waitTime;
    private int completionTime;
    private int responseTime;
    private int turnAroundTime;

    public Process() {
        this.id = "";
        this.burstTime = 0;
        this.remainingBurstTime = 0;
        this.arrivalTime = 0;
        this.queue = 0;
        this.priority = 0;
        this.waitTime = 0;
        this.completionTime = 0;
        this.responseTime = 0;
        this.turnAroundTime = 0;
    }

    public Process(String id, int burstTime, int arrivalTime, int queue, int priority) {
        this.id = id;
        this.burstTime = burstTime;
        this.remainingBurstTime = burstTime;
        this.arrivalTime = arrivalTime;
        this.queue = queue;
        this.priority = priority;
        this.waitTime = 0;
        this.completionTime = 0;
        this.responseTime = 0;
        this.turnAroundTime = 0;
    }

    public String getId() {
        return id;
    }
    public int getBurstTime() {
        return burstTime;
    }

    public int getRemainingBurstTime() {
        return remainingBurstTime;
    }

    public void setRemainingBurstTime(int remainingBurstTime) {
        this.remainingBurstTime = remainingBurstTime;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }
    public int getQueue() {
        return queue;
    }
    public int getPriority() {
        return priority;
    }
    public void setWaitTime(int waitTime) {
        this.waitTime = waitTime;
    }
    public int getWaitTime() {
        return waitTime;
    }
    public void setCompletionTime(int completionTime) {
        this.completionTime = completionTime;
    }
    public int getCompletionTime() {
        return completionTime;
    }
    public void setResponseTime(int responseTime) {
        this.responseTime = responseTime;
    }
    public int getResponseTime() {
        return responseTime;
    }
    public void setTurnAroundTime() {
        this.turnAroundTime =  this.completionTime - this.arrivalTime;
    }
    public int getTurnAroundTime() {
        return turnAroundTime;
    }
}
