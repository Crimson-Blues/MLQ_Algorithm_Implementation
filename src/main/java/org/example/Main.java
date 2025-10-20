package org.example;

import org.example.Algorithms.IPlanningAlgorithm;
import org.example.Algorithms.RoundRobin;
import org.example.Algorithms.SJF;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        TextFileHandler textFileHandler = new TextFileHandler();
        List<Process> processList = textFileHandler.readProcesses("data/input/mlq003.txt");
        List<IPlanningAlgorithm> algorithms = new ArrayList<>();
        algorithms.add(new RoundRobin(1));
        algorithms.add(new RoundRobin(3));
        algorithms.add(new SJF());

        System.out.println(processList.size());
        Planner planner = new Planner(algorithms, processList);

        List<Process> obtainedProcesses = planner.RunPlanner();
        List<Double> averageMetrics = planner.CalculateAverageMetrics();

        textFileHandler.writeOutputFile("mlq003.txt",obtainedProcesses,averageMetrics);
    }
}