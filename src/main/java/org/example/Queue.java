package org.example;

import org.example.Algorithms.IPlanningAlgorithm;

import java.util.ArrayList;
import java.util.List;

public class Queue {
    private List<Process> processes = new ArrayList<Process>();;
    private List<Process> arrivedProcesses = new ArrayList<>();;
    private List<ProcessingTime> record;
    private int currentTime;

    private IPlanningAlgorithm algorithm;
    public Queue() {
        currentTime = 0;
    }
    public Queue(IPlanningAlgorithm algorithm) {
        this.algorithm = algorithm;
        this.currentTime = 0;
    }

    public int getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(int currentTime) {
        this.currentTime = currentTime;
    }

    public boolean ended(){
        boolean ended = true;
        for (Process process : processes) {
            if (process.getRemainingBurstTime() != 0) {
                ended = false;
                break;
            }
        }
        return ended;
    }

    public void addProcess(Process process) {
        processes.add(process);
    }

    public ProcessingTime nextStep(){
        ProcessingTime nextStepTime = this.algorithm.nextProcessingTime();
        Process currentProcess = nextStepTime.getProcess();
        int duration = nextStepTime.getDuration();

        int remainingBurstTime = currentProcess.getRemainingBurstTime();
        currentProcess.setRemainingBurstTime(remainingBurstTime - duration);

        return nextStepTime;
    }

    public List<ProcessingRecord> runQueue(){
        List<ProcessingRecord> records = new ArrayList<ProcessingRecord>();
        while(!ended()){
            checkArrivals();
            ProcessingTime nextStep = nextStep();
            Process runningProcess = nextStep.getProcess();
            int duration = nextStep.getDuration();
            records.add(new ProcessingRecord(runningProcess, currentTime,
                    currentTime + duration));

            this.currentTime += duration;
        }
        return records;
    }

    public void checkArrivals(){
        for (Process process : processes) {
            if(process.getArrivalTime() <= currentTime && !arrivedProcesses.contains(process)){
                arrivedProcesses.add(process);
                algorithm.addProcess(process);
            }
        }
    }
}
