package notification;

import channel.Channel;

import java.util.Objects;

public abstract class Notification {

    private final String id;
    private final String receiver;
    private final String message;
    private Channel deliveryChannel;

    protected Notification(String id, String receiver, String message, Channel deliveryChannel) {
        this.id = requireNotBlank(id, "id");
        this.receiver = requireNotBlank(receiver, "receiver");
        this.message = requireNotBlank(message, "message");
        this.deliveryChannel = Objects.requireNonNull(deliveryChannel, "channel must not be null");
    }

    public abstract String execute();

    public void setImplementation(Channel deliveryChannel) {
        this.deliveryChannel = Objects.requireNonNull(deliveryChannel, "channel must not be null");
    }

    public String getId() {
        return id;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getMessage() {
        return message;
    }

    protected final String sendViaChannel(String title, String content) {
        return deliveryChannel.transmit(receiver, title, content);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "[" + id + " via " + deliveryChannel.channelLabel() + "]";
    }

    private static String requireNotBlank(String value, String field) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(field + " must not be blank");
        }
        return value;
    }
}