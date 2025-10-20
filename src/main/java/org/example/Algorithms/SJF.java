package org.example.Algorithms;

import org.example.Process;
import org.example.ProcessingTime;

import java.util.ArrayList;
import java.util.List;

public class SJF implements IPlanningAlgorithm {
    private List<Process> processes;

    public SJF(){
        processes = new ArrayList<Process>();
    }

    public SJF(List<Process> processes){
        this.processes = processes;
    }

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

        Process shortestJob = processes.get(0);
        int shortestJobDuration = shortestJob.getRemainingBurstTime();

        for (Process process : processes) {
            if(process.getRemainingBurstTime() < shortestJobDuration){
                shortestJob = process;
                shortestJobDuration = process.getRemainingBurstTime();
            }
        }
        return new ProcessingTime(shortestJob, shortestJobDuration);
    }
}
