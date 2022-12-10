package org.example.simulator;

import java.util.ArrayList;
import java.util.List;

class Server {
    public int serverId;

    public int taskWeight;

    public List<Integer> clientIds;

    public Server(int serverId) {
        this.serverId = serverId;
        this.taskWeight = 0;
        this.clientIds = new ArrayList<>();
    }

    public void processTask(Task task) {
        System.out.println("server "+ serverId +" processing task "+task.clientId);
        taskWeight += task.processingTime;
        clientIds.add(task.clientId);
    }
}

