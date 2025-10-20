package org.example.Algorithms;

import org.example.ProcessingTime;
import org.example.Process;

// Defines the common behavior for all scheduling (planning) algorithms
// used in the MLQ (Multi-Level Queue) system.
public interface IPlanningAlgorithm {
    // Returns information about the next process to execute as a ProcessingTime object
    public ProcessingTime nextProcessingTime();
    // Adds a new process to the ready queue managed by the algorithm.
    public void addProcess(Process process);

}
