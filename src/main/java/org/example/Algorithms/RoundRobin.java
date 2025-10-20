package org.example.Algorithms;

import org.example.Process;
import org.example.ProcessingTime;

import java.util.ArrayList;
import java.util.List;

// Implements the Round Robin (RR) scheduling algorithm.
// Each process gets an equal time slice (quantum) in a circular order.
// When its quantum expires, the next process in the list is selected.
public class RoundRobin implements IPlanningAlgorithm{
    // --- Core RR parameters ---
    private int quantum;                // Fixed time slice assigned to each process
    private Process pivot;              // First process (optional, used as reference)
    private Process currentProcess;     // Process currently being scheduled
    private int currentProcessIndex;    // Index of the process currently running
    private List<Process> processes;    // Ready queue of processes managed by RR

    // --- Constructors ---

    public RoundRobin(int quantum){
        this.quantum = quantum;
        this.processes = new ArrayList<Process>();
    }

    public RoundRobin(int quantum, List<Process> processes){
        this.quantum = quantum;
        this.processes = processes;
        this.pivot = processes.get(0);
        this.currentProcessIndex = 0;
        this.currentProcess = pivot;
    }

    // --- Core operations ---

    // Adds a new process to the RR queue
    @Override
    public void addProcess(Process process){
        processes.add(process);
    }

    // Removes a completed process from the RR queue
    public void removeProcess(Process process){
        processes.remove(process);
    }

    // Determines which process should execute next and for how long.
    // Returns a ProcessingTime object containing that process and its execution duration.
    @Override
    public ProcessingTime nextProcessingTime() {
        if (processes.isEmpty()){
            return null;
        }
        if(this.currentProcessIndex >= processes.size()){
            this.currentProcessIndex = 0;
        }
        currentProcess = processes.get(currentProcessIndex);
        int runTime;
        if(currentProcess.getRemainingBurstTime() <= quantum){
            runTime = currentProcess.getRemainingBurstTime();
            processes.remove(currentProcess);
        }else {
            runTime = quantum;
        }
        this.currentProcessIndex++;
        return new ProcessingTime(currentProcess, runTime);
    }
}
