package pl.codewise.internships;

import java.util.List;

public class Snapshot {

    private List<Message> messages;

    Snapshot(List<Message> messages) {
        this.messages = messages;
    }

    public List<Message> getMessages() {
        return messages;
    }
}
