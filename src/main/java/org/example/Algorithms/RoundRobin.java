package org.example.Algorithms;

import org.example.Process;
import org.example.ProcessingTime;

import java.util.ArrayList;
import java.util.List;

public class RoundRobin implements IPlanningAlgorithm{
    private int quantum;
    private Process pivot;
    private Process currentProcess;
    private int currentProcessIndex;
    private List<Process> processes;

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

    @Override
    public void addProcess(Process process){
        processes.add(process);
    }

    public void removeProcess(Process process){
        processes.remove(process);
    }

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
