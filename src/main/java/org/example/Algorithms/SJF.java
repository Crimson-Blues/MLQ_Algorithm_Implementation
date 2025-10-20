package org.example.Algorithms;

import org.example.Process;
import org.example.ProcessingTime;

import java.util.ArrayList;
import java.util.List;

// Implements the Shortest Job First (SJF) scheduling algorithm.
// Always selects the process with the smallest remaining burst time to execute next.
// Non-preemptive version: the chosen process runs until completion.
public class SJF implements IPlanningAlgorithm {
    // List of processes managed by this algorithm (ready queue)
    private List<Process> processes;

    // --- Constructors ---

    public SJF(){
        processes = new ArrayList<Process>();
    }

    public SJF(List<Process> processes){
        this.processes = processes;
    }

    // --- Queue management methods ---

    // Adds a process to the SJF ready queue
    @Override
    public void addProcess(Process process){
        processes.add(process);
    }
    // Removes a completed process from the queue
    public void removeProcess(Process process){
        processes.remove(process);
    }

    // --- Core scheduling logic ---

    // Determines the next process to execute based on the shortest remaining burst time.
    // Returns a ProcessingTime object representing the selected process and its runtime.
    @Override
    public ProcessingTime nextProcessingTime() {
        if (processes.isEmpty()){
            return null;
        }
        Process shortestJob = processes.get(0);
        int shortestJobDuration = shortestJob.getRemainingBurstTime();

        for (Process process : processes) {
            if(process.getRemainingBurstTime() < shortestJobDuration){
                shortestJob = process;
                shortestJobDuration = process.getRemainingBurstTime();
            }
        }
        removeProcess(shortestJob);
        return new ProcessingTime(shortestJob, shortestJobDuration);
    }
}
