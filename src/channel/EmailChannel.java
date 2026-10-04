package channel;

public class EmailChannel implements Channel {

    @Override
    public String channelLabel() {
        return "Email";
    }

    @Override
    public String transmit(String address, String title, String content) {
        return "[EMAIL] To: " + address
                + " | Subject: " + title
                + " | Body: " + content;
    }
}