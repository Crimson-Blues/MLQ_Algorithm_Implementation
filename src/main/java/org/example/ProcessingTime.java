package org.example;

public class ProcessingTime {
    private Process process;
    private int duration;

    public ProcessingTime() {
        this.process = null;
        this.duration = 0;
    }
    public ProcessingTime(Process process, int duration) {
        this.process = process;
        this.duration = duration;
    }

    public Process getProcess() {
        return process;
    }
    public void setProcess(Process process) {
        this.process = process;
    }
    public int getDuration() {
        return duration;
    }
    public void setDuration(int duration) {
        this.duration = duration;
    }
}
