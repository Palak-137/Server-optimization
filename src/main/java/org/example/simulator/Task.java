package org.example.simulator;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class Task {
    public int clientId;

    public String data;
    public int processingTime;

    public Timestamp timestamp;
}
