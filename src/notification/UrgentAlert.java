package notification;

import channel.Channel;

public class UrgentAlert extends Notification {

    private static final String TITLE = "URGENT: Goodnight, Meruem";

    public UrgentAlert(String id, String receiver, String message, Channel deliveryChannel) {
        super(id, receiver, message, deliveryChannel);
    }

    @Override
    public String execute() {
        return sendViaChannel(TITLE, getMessage());
    }
}