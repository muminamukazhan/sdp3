package channel;

public class PushChannel implements Channel {

    @Override
    public String channelLabel() {
        return "Push";
    }

    @Override
    public String transmit(String address, String title, String content) {
        return "[PUSH] Device: " + address
                + " | Title: " + title
                + " | Text: " + content;
    }
}