package channel;

public class SmsChannel implements Channel {

    @Override
    public String channelLabel() {
        return "SMS";
    }

    @Override
    public String transmit(String address, String title, String content) {
        return "[SMS] " + address + ": " + toOneLine(title + " - " + content);
    }

    private String toOneLine(String text) {
        return text.replaceAll("\\s*\\R\\s*", " ").trim();
    }
}