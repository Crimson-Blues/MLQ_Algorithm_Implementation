package org.example;

// Represents a unit of scheduled execution time for a specific process.
// Used by planning algorithms to indicate which process runs next and for how long.
public class ProcessingTime {
    // The process that will be executed
    private Process process;
    // Duration (in time units) that the process will execute for
    // before yielding, completing, or being preempted.
    private int duration;

    // --- Constructors ---

    // Default constructor
    public ProcessingTime() {
        this.process = null;
        this.duration = 0;
    }
    // Creates a ProcessingTime object for a specific process and execution duration
    public ProcessingTime(Process process, int duration) {
        this.process = process;
        this.duration = duration;
    }
    // --- Getters and setters ---

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
