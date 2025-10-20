package org.example.Algorithms;

import org.example.ProcessingTime;
import org.example.Process;

public interface IPlanningAlgorithm {
    public ProcessingTime nextProcessingTime();
    public void addProcess(Process process);

}
