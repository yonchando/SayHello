package com.rupp.yonchando.sayhello;

/**
 * Created by PC User on 21-Jan-18.
 */

public class Messages {

    private String message;
    private String seen;
    private long timestamp;
    private String types;
    private String from;
    public Messages() {

    }

    public Messages(String message, String types, String seen, long timestamp) {
        this.message = message;
        this.types = types;
        this.seen = seen;
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getTypes() {
        return types;
    }

    public void setTypes(String types) {
        this.types = types;
    }

    public String getSeen() {
        return seen;
    }

    public void setSeen(String seen) {
        this.seen = seen;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }
}
