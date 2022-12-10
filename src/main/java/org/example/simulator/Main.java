package org.example.simulator;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileReader;
import java.util.List;

public class Main {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static void main(String[] args) {

        // validate input file path is given in param
        if (args.length < 2) {
            System.out.println("invalid input args: expected input file path as param 1 and number of processors as param 2");
            return;
        }

        try {
            List<Task> tasks = objectMapper.readValue(new FileReader(args[0]), new TypeReference<List<Task>>(){});
            Long processorCount = Long.parseLong(args[1]);

            LargestJobFirstScheduler.process(tasks, processorCount);
//            ShortestJobFirstScheduler.process(tasks, processorCount);

        } catch (Exception ex) {
            System.out.println("error processing the data" + ex);
        }
    }
}

// 10 20 30 45 50