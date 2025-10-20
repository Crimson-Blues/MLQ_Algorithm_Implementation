package org.example;

// Represents a single execution record for a process in the MLQ timeline.
// Stores when the process started and finished during a specific CPU burst.
public class ProcessingRecord {
    // The process associated with this record
    private Process process;
    // Time (in system units) when the process started executing
    private int beginningTime;
    // Time (in system units) when the process stopped executing
    private int endTime;

    // --- Constructors ---

    public ProcessingRecord(Process process) {
        this.process = process;
        beginningTime = 0;
        endTime = 0;
    }
    public ProcessingRecord(Process process, int beginningTime, int endTime) {
        this.process = process;
        this.beginningTime = beginningTime;
        this.endTime = endTime;
    }

    // --- Getters and setters ---

    public Process getProcess() {
        return process;
    }

    public int getBeginningTime() {
        return beginningTime;
    }

    public int getEndTime() {
        return endTime;
    }

    public int getDuration() {
        return endTime - beginningTime;
    }
}
