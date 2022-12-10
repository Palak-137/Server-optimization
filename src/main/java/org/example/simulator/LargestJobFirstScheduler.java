package org.example.simulator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class LargestJobFirstScheduler {
    public static void process(List<Task> tasks, Long processorCount) {
        List<Server> serverContexts = new ArrayList<>();

        for(int i=0;i<processorCount;i++) {
            serverContexts.add(new Server(i));
        }

        tasks.sort(Collections.reverseOrder(Comparator.comparing(Task::getProcessingTime)));

        for (Task currentTask : tasks) {
            int minLoadedProcessorIndex = 0, minLoadedProcessorWeight = 99999999;

            for (int j = 0; j < processorCount; j++) {
                Server state = serverContexts.get(j);
                if (state.taskWeight < minLoadedProcessorWeight) {
                    minLoadedProcessorIndex = j;
                    minLoadedProcessorWeight = state.taskWeight;
                }
            }

            Server minLoadedServer = serverContexts.get(minLoadedProcessorIndex);
            minLoadedServer.processTask(currentTask);
        }

        for (Server state: serverContexts) {
            System.out.println("processor id : "+state.serverId);
            System.out.println("execution time : "+state.taskWeight);
            System.out.print("tasks executed : ");
            for (Integer clientId : state.clientIds) {
                System.out.print(" "+clientId+" ");
            }
            System.out.println();
        }
    }
}
