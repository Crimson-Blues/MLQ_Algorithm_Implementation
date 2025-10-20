package org.example;

public class ProcessingRecord {
    private Process process;
    private int beginningTime;
    private int endTime;

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
