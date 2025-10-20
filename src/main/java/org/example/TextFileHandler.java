package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TextFileHandler {
    public TextFileHandler() {}

    public List<String> readTextFile(String fileName) {
        List<String> text = new ArrayList<String>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine())!= null){
                text.add(line.trim());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return text;
    }

    public void writeTextFile(String fileName, List<String> text) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            for (int i = 0; i < text.size(); i++) {
                bw.write(text.get(i));
                bw.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Process> readProcesses(String fileName) {
        List<String> lines = readTextFile(fileName);
        List<Process> processes = new ArrayList<Process>();
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            if (line.charAt(0) == '#') {
                continue;
            }
            String[] parts = line.split(";");
            for (int j = 0; j < parts.length; j++) {
                parts[j] = parts[j].trim();
            }
            String id = parts[0];
            int burstTime = Integer.parseInt(parts[1]);
            int arrivalTime = Integer.parseInt(parts[2]);
            int queue = Integer.parseInt(parts[3]);
            int priority = Integer.parseInt(parts[4]);
            processes.add(new Process(id, burstTime, arrivalTime, queue, priority));
        }

        return processes;
    }

    public void writeOutputFile(String fileName, List<Process> processes, List<Double> averageMetrics) {
        List<String> lines = new ArrayList<String>();
        lines.add("# Archivo: " + fileName);
        lines.add("# etiqueta; BT; AT; Q; Pr; WT; CT; RT; TAT");
        for (int i = 0; i < processes.size(); i++) {
            String line = getLine(processes, i);
            lines.add(line);
        }
        String waitTime = String.valueOf(averageMetrics.get(0));
        String completionTime = String.valueOf(averageMetrics.get(1));
        String responseTime = String.valueOf(averageMetrics.get(2));
        String turnAroundTime = String.valueOf(averageMetrics.get(3));

        lines.add("WT="+ waitTime + ";" + "CT="+completionTime + ";" + "AT="+responseTime + ";" + "TAT="+turnAroundTime + ";");

        writeTextFile("data/output/"+ fileName, lines);
    }

    //Extracts and formats process info
    private static String getLine(List<Process> processes, int i) {
        Process process = processes.get(i);
        String line = "";
        line += process.getId() + ";";
        line += process.getBurstTime() + ";";
        line += process.getArrivalTime() + ";";
        line += process.getQueue() + ";";
        line += process.getPriority() + ";";
        line += process.getWaitTime() + ";";
        line += process.getCompletionTime() + ";";
        line += process.getResponseTime() + ";";
        line += process.getTurnAroundTime() + "";
        return line;
    }

}
