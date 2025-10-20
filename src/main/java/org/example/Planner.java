package org.example;

import org.example.Algorithms.IPlanningAlgorithm;

import java.util.ArrayList;
import java.util.List;

// The Planner class orchestrates the execution of multiple queues in the
// Multi-Level Queue (MLQ) scheduling system. It manages global time,
// dispatches processes to their respective queues, and computes final metrics.
public class Planner {
    // --- Core components ---
    List<Queue> queues = new ArrayList<>();                     // All queues managed by the MLQ (each with its own algorithm)
    List<List<ProcessingRecord>> queueRecords = new ArrayList<>(); // Execution logs for each queue
    List<Process> processes;                                    // All processes to be scheduled
    List<Process> arrivedProcesses = new ArrayList<>();         // Processes that have already arrived in the system
    int currentTime = 0;                                        // Global simulation clock


    // --- Constructors ---

    public Planner(){
    }

    // Initializes the planner with a list of algorithms and processes.
    // Each algorithm creates its own queue, and processes are distributed
    // to their designated queue according to their 'queue' attribute.
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

    // --- Utility methods ---

    // Calculates individual timing metrics (wait, response, completion, turnaround)
    // for a given process based on its recorded execution in the queue.
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

    // Determines if all processes across all queues have completed execution
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

    // Checks for newly arrived processes and adds them to the appropriate queue
    public void checkArrivals(){
        for (Process process : processes) {
            if(process.getArrivalTime() <= currentTime && !arrivedProcesses.contains(process)){
                arrivedProcesses.add(process);
                int queue = process.getQueue()-1;
                System.out.println("Inserting process into queue: " + queue);
                queues.get(queue).addProcess(process);
            }
        }
    }

    // Executes all queues in order of priority (queue 1 is highest).
    // Each queue runs independently using its assigned algorithm.
    // When all queues finish, metrics are calculated for each process.
    public List<Process> RunPlanner(){
        while(!ended()){
            //Attempts to run all queues in order of priority
            for (int i = 0; i < queues.size(); i++){
                checkArrivals();
                System.out.println("Running queue: " + i);
                Queue queue = queues.get(i);
                queue.setCurrentTime(currentTime);
                queueRecords.add(queue.runQueue());
                this.currentTime = queue.getCurrentTime();
                System.out.println("Current time: " + currentTime);
            }
        }

        //Calculates metrics for each process
        for (Process process : processes){
            CalculateTimes(process);
        }

        return processes;
    }

    // Calculates the average of all timing metrics (response, completion,
    // wait, and turnaround) across all processes in the system.
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
