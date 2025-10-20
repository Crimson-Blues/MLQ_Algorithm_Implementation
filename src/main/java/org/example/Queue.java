package org.example;

import org.example.Algorithms.IPlanningAlgorithm;

import java.util.ArrayList;
import java.util.List;

// Represents a single scheduling queue in the Multi-Level Queue (MLQ) system.
// Each queue manages its own processes, planning algorithm, and execution timeline.
public class Queue {
    // --- Core process collections ---
    private List<Process> processes = new ArrayList<>();       // All processes assigned to this queue
    private List<Process> arrivedProcesses = new ArrayList<>(); // Processes that have already arrived (ready to run)
    private List<ProcessingTime> record;                        // Record of processing times (used for logging or debugging)

    // --- Timing and control ---
    private int currentTime;                                   // Tracks the simulated time within this queue
    private IPlanningAlgorithm algorithm;                      // Scheduling algorithm used by this queue (e.g., RR, FCFS, etc.)

    // --- Constructors ---
    public Queue() {
        currentTime = 0;
    }
    // Creates a queue that uses a specific scheduling algorithm
    public Queue(IPlanningAlgorithm algorithm) {
        this.algorithm = algorithm;
        this.currentTime = 0;
    }

    // --- Getters and setters ---
    public int getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(int currentTime) {
        this.currentTime = currentTime;
    }

    // --- Core logic ---

    // Checks whether all processes in the queue have finished executing
    public boolean ended(){
        if(processes.isEmpty()){
            return true;
        }
        boolean ended = true;
        for (Process process : processes) {
            if (process.getRemainingBurstTime() != 0) {
                ended = false;
                break;
            }
        }
        return ended;
    }

    // Adds a process to this queue
    public void addProcess(Process process) {
        processes.add(process);
    }

    // Requests the next processing time from the algorithm and updates the process state
    public ProcessingTime nextStep(){
        ProcessingTime nextStepTime = this.algorithm.nextProcessingTime();
        Process currentProcess = nextStepTime.getProcess();
        int duration = nextStepTime.getDuration();
        System.out.println("Duration of " + currentProcess.getId() + " is " + duration);
        // Deduct the executed time from the process’s remaining burst time
        int remainingBurstTime = currentProcess.getRemainingBurstTime();
        currentProcess.setRemainingBurstTime(remainingBurstTime - duration);

        return nextStepTime;
    }

    // Runs the entire queue until all processes are completed
    // Returns a list of ProcessingRecords (execution history)
    public List<ProcessingRecord> runQueue(){
        List<ProcessingRecord> records = new ArrayList<ProcessingRecord>();
        while(!ended()){
            checkArrivals();
            ProcessingTime nextStep = nextStep();
            if(nextStep == null){
                break;
            }
            Process runningProcess = nextStep.getProcess();
            int duration = nextStep.getDuration();
            records.add(new ProcessingRecord(runningProcess, currentTime,
                    currentTime + duration));
            this.currentTime += duration;
        }
        return records;
    }

    // Verifies which processes have arrived based on current time
    // Adds newly arrived processes to the ready queue of the algorithm
    public void checkArrivals(){
        for (Process process : processes) {
            if(process.getArrivalTime() <= currentTime && !arrivedProcesses.contains(process)){
                arrivedProcesses.add(process);
                algorithm.addProcess(process);
            }
        }
    }
}
