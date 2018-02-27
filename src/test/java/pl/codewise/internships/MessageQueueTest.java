package pl.codewise.internships;

import org.junit.Test;

import static org.junit.Assert.*;

public class MessageQueueTest {

    @Test
    public void initializationTest() {
        MessageQueueFactory factory = new MessageQueueFactory();
        MessageQueue messageQueue = factory.getInstance();
        messageQueue.add(Message.getInstance("agent1", 200));
        messageQueue.add(Message.getInstance("agent2", 300));
        messageQueue.add(Message.getInstance("agent3", 400));
        messageQueue.add(Message.getInstance("agent4", 500));
        assertEquals(2, messageQueue.numberOfErrorMessages());
    }

}