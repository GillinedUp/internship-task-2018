package pl.codewise.internships;

public class Message {

    private final String userAgent;
    private final int errorCode;

    public Message(String userAgent, int errorCode) {
        this.userAgent = userAgent;
        this.errorCode = errorCode;
    }

    public static synchronized Message getInstance(String userAgent, int errorCode) {
        return new Message(userAgent, errorCode);
    }

    public synchronized String getUserAgent() {
        return userAgent;
    }

    public synchronized int getErrorCode() {
        return errorCode;
    }
}
