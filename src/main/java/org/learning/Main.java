package org.learning;

import org.learning.sendrecv.Receive;
import org.learning.sendrecv.Send;

public class Main {
    public static void main(String[] args) throws Exception {
        try {
            Send.send();
            Receive.receive();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}