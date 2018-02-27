package pl.codewise.internships;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class MessageQueueFactory {

    private volatile MessageQueue snapshotComponentInstance;

    public synchronized MessageQueue getInstance() {
        if (snapshotComponentInstance == null) {
            snapshotComponentInstance = new SnapshotComponent();
        }
        return snapshotComponentInstance;
    }

    public class SnapshotComponent implements MessageQueue {

        private Map<LocalDateTime, Message> messageMap;

        private SnapshotComponent() {
            messageMap = new ConcurrentHashMap<>();
        }

        @Override
        public synchronized void add(Message message) {
            LocalDateTime time = LocalDateTime.now();
            messageMap.put(time, message);
        }

        @Override
        public synchronized Snapshot snapshot() {
            LocalDateTime currentTime = LocalDateTime.now();
            List<Message> lastMessages = messageMap.entrySet().stream()
                    .sorted((time1, time2) -> time2.getKey().compareTo(time1.getKey()))
                    .filter(time -> (time.getKey().until(currentTime, ChronoUnit.MINUTES) <= 5))
                    .limit(100)
                    .map(Map.Entry::getValue)
                    .collect(Collectors.toList());
            return new Snapshot(lastMessages);
        }

        @Override
        public synchronized long numberOfErrorMessages() {
            LocalDateTime currentTime = LocalDateTime.now();
            return messageMap.entrySet().stream()
                    .sorted((t1, t2) -> t2.getKey().compareTo(t1.getKey()))
                    .filter(time -> (time.getKey().until(currentTime, ChronoUnit.MINUTES) <= 5))
                    .map(Map.Entry::getValue)
                    .filter(message -> (message.getErrorCode() >= 400
                            && message.getErrorCode() <= 599))
                    .limit(100)
                    .count();
        }
    }

}
