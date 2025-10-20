package org.example;

import org.example.Algorithms.IPlanningAlgorithm;

import java.util.ArrayList;
import java.util.List;

public class Planner {
    List<Queue> queues = new ArrayList<Queue>();;
    List<List<ProcessingRecord>> queueRecords = new ArrayList<>();
    List<Process> processes;
    List<Process> arrivedProcesses = new ArrayList<>();
    int currentTime = 0;

    public Planner(){
    }

    public Planner(List<IPlanningAlgorithm> algorithms, List<Process> processes){
        this.processes = processes;
        queues = new ArrayList<Queue>();
        for (IPlanningAlgorithm algorithm : algorithms){
            queues.add(new Queue(algorithm));
        }
        System.out.println(processes.size());
        for (Process process : processes){
            int queue = process.getQueue()-1;
            queues.get(queue).addProcess(process);
        }
    }

    public void CalculateTimes(Process process){
        int queue = process.getQueue()-1;
        List<ProcessingRecord> queueRecord = queueRecords.get(queue);
        int waitTime = 0;
        List<Process> processOrder = new ArrayList<>();
        for (ProcessingRecord record : queueRecord){
            processOrder.add(record.getProcess());
        }

        int responseTime = queueRecord.get(processOrder.indexOf(process)).getBeginningTime();
        int completionTime = queueRecord.get(processOrder.lastIndexOf(process)).getEndTime();

        int lastApparition = processOrder.lastIndexOf(process);
        for (int i = 0; i < lastApparition; i++){
            ProcessingRecord record = queueRecord.get(i);
            if(record.getProcess()!=process){
                waitTime += queueRecord.get(i).getDuration();
            }
        }

        process.setResponseTime(responseTime);
        process.setCompletionTime(completionTime);
        process.setWaitTime(waitTime);
        process.setTurnAroundTime();
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

    public void checkArrivals(){
        for (Process process : processes) {
            if(process.getArrivalTime() <= currentTime && !arrivedProcesses.contains(process)){
                arrivedProcesses.add(process);
                int queue = process.getQueue()-1;
                queues.get(queue).addProcess(process);
            }
        }
    }

    public List<Process> RunPlanner(){
        while(!ended()){
            checkArrivals();
            //Attempts to run all queues in order of priority
            for (int i = 0; i < queues.size(); i++){
                Queue queue = queues.get(i);
                queue.setCurrentTime(currentTime);
                queueRecords.add(queue.runQueue());
                currentTime = queue.getCurrentTime();
            }
        }

        //Calculates metrics for each process
        for (Process process : processes){
            CalculateTimes(process);
        }

        return processes;
    }

    public List<Double> CalculateAverageMetrics(){
        //Calculates average metric values by adding them for each process and then dividing
        double averageResponseTime = 0;
        double averageCompletionTime = 0;
        double averageWaitTime = 0;
        double averageTurnAroundTime = 0;

        for (Process process : processes){
            averageResponseTime += process.getResponseTime();
            averageCompletionTime += process.getCompletionTime();
            averageWaitTime += process.getWaitTime();
            averageTurnAroundTime += process.getTurnAroundTime();
        }
        averageResponseTime = averageResponseTime / processes.size();
        averageCompletionTime = averageCompletionTime / processes.size();
        averageWaitTime = averageWaitTime / processes.size();
        averageTurnAroundTime = averageTurnAroundTime / processes.size();

        List<Double> averageMetrics = new ArrayList<>();
        averageMetrics.add(averageWaitTime);
        averageMetrics.add(averageCompletionTime);
        averageMetrics.add(averageResponseTime);
        averageMetrics.add(averageTurnAroundTime);

        return averageMetrics;
    }
}
