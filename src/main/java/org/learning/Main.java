package org.learning;

import org.learning.helloworld.Receive;
import org.learning.helloworld.Send;
import org.learning.workqueues.NewTask;
import org.learning.workqueues.Worker;

public class Main {
    public static void main(String[] args) throws Exception {
        try {

            // Hello World
//            Send.send();
//            Receive.receive();

            // Worker Queues
            for (int i=0; i<2; i++) {
                System.out.println("Thread: " + i);
                WorkerThread thread = new WorkerThread();
                thread.run();
            }

            for (int i=0; i<10; i++) {
                String[] work = {"hello... id:" + i};
                NewTask.sendWork(work);
            }

        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}