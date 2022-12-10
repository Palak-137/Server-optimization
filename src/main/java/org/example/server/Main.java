package org.example.server;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.simulator.Task;

import java.io.FileReader;
import java.sql.Timestamp;
import java.util.LinkedList;

public class Main {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static void main(String args[]) {
        // validate input file path is given in param
        if (args.length < 2) {
            System.out.println("invalid input args: expected input file path as param 1 and number of heavy processing servers as param 2, number of normal processing servers 3");
            return;
        }

        try {
            LinkedList<Task> tasks = objectMapper.readValue(new FileReader(args[0]), new TypeReference<LinkedList<Task>>(){});


            for(int i=0;i<tasks.size();i++) {
                tasks.get(i).setTimestamp(new Timestamp(System.currentTimeMillis()+i* 1000L));
            }
            int processorCount = Integer.parseInt(args[1]);

            Scheduler scheduler = new Scheduler(tasks, new LinkedList<Task>(), (int)(processorCount * 0.3),  new LinkedList<>(), (int)(processorCount * 0.7), 5, processorCount);

            for(int i=0;i<processorCount;i++) {
                Server server = new Server(i, scheduler);
                new Thread(server).start();
            }

        } catch (Exception ex) {
            System.out.println("error processing the data" + ex);
        }

    }
}
