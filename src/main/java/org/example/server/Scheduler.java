package org.example.server;

import lombok.AllArgsConstructor;
import lombok.Synchronized;
import org.example.simulator.Task;

import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;

@AllArgsConstructor
public class Scheduler {
    private final LinkedList<Task> mainQueue;

    private final Queue<Task> heavyQueue;

    private int maxHeavyQueueSize;

    private final Queue<Task> normalQueue;

    private int maxNormalQueueSize;


    // assuming this variable keeps track of rolling window avg
    private int avgProcessingTime;

    private int serverCount;

    @Synchronized
    public Task getTask() {
        if (mainQueue.size() == 0) {
            return null;
        }

        // light tasks are present just drain them
        if (normalQueue.size() > 0) {
            return normalQueue.remove();
        }

        // both queues are empty and time to refill them
        if (heavyQueue.size() == 0) {
            Queue<Task> temporaryBuffer = new LinkedList<>();

            for(int i=0;i<serverCount;i++) {
                try {
                    Task task = mainQueue.remove();
                    if (task.processingTime > 2 * avgProcessingTime) {
                        if (heavyQueue.size() < maxHeavyQueueSize) {
                            // if heavy queue has space add task to heavy queue
                            heavyQueue.add(task);
                        } else {
                            // if heavy queue is already filled return task to main queue
                            mainQueue.push(task);
                        }
                    } else {
                        if (normalQueue.size() < maxNormalQueueSize) {
                            // if normal queue has space add task to normal queue
                            normalQueue.add(task);
                        } else {
                            // if normal queue is already filled return task to main queue
                            mainQueue.add(task);
                        }
                    }
                } catch (NoSuchElementException ignored) {
                    break;
                }

            }
        }

        // return from heavy queue if present or else from normal queue
        // element is guaranteed to be in either one of them cuz we pulled earlier if it wasnt present
        if (heavyQueue.size() > 0) {
            return heavyQueue.remove();
        }

        return normalQueue.remove();
    }
}
