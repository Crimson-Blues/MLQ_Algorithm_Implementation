package org.example;

// Represents a single process in the MLQ (Multi-Level Queue) scheduling simulation
public class Process {
    // --- Core attributes ---
    private String id;                 // Unique identifier for the process (e.g., "P1")
    private int burstTime;             // Total CPU time the process requires
    private int remainingBurstTime;    // Time still needed to complete execution
    private int arrivalTime;           // Time at which the process arrives in the system
    private int queue;                 // Queue level the process belongs to (for MLQ)
    private int priority;              // Priority value (used by priority-based queues)

    // --- Timing metrics (calculated during simulation) ---
    private int waitTime;              // Total time spent waiting in ready queues
    private int completionTime;        // Time when process finishes execution
    private int responseTime;          // Time between arrival and first CPU execution
    private int turnAroundTime;        // Total time from arrival to completion

    // --- Default constructor ---
    // Initializes all values to defaults
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

    // --- Parameterized constructor ---
    // Creates a process with given attributes and initializes tracking variables
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

    // --- Getters and setters ---

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
    //Sets the turnAroundTime automatically given that completionTime and arrivalTime have been set.
    public void setTurnAroundTime() {
        this.turnAroundTime =  this.completionTime - this.arrivalTime;
    }
    public int getTurnAroundTime() {
        return turnAroundTime;
    }
}
