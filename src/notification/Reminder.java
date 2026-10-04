package notification;

import channel.Channel;

public class Reminder extends Notification {

    private static final String TITLE = "Reminder";

    public Reminder(String id, String receiver, String message, Channel deliveryChannel) {
        super(id, receiver, message, deliveryChannel);
    }

    @Override
    public String execute() {
        return sendViaChannel(TITLE, getMessage());
    }
}