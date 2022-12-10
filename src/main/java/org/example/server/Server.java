package org.example.server;

import lombok.AllArgsConstructor;
import org.example.simulator.Task;

import java.util.Objects;

@AllArgsConstructor
public class Server implements Runnable {
    private int id;

    private Scheduler scheduler;

    @Override
    public void run() {
        System.out.println(System.currentTimeMillis()+" server "+id+" running");
        while(true) {
            System.out.println(System.currentTimeMillis()+" server "+id+" polling for tasks");
            try {
                Task task = scheduler.getTask();

                if (Objects.isNull(task)) {
                    System.out.println(System.currentTimeMillis()+" server "+id+" got empty response from scheduler exiting");
                    return;
                }

                System.out.println(System.currentTimeMillis()+" server "+id+" got task "+task.clientId+ " with processing time "+task.processingTime);

                try {
                    Thread.sleep(task.processingTime * 1000L);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            } catch (Exception ex) {
                System.out.println(System.currentTimeMillis()+" error while fetching task "+ex);
                break;
            }
        }
    }
}
