package org.learning;

import org.learning.workqueues.Worker;

import java.util.Random;

public class WorkerThread extends Thread {

    public void run() {
        try {
            Worker worker = new Worker();

            worker.receiveWork();
            System.out.println(worker.getId() + "Threads are cray");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}

