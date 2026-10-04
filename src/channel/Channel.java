package channel;

public interface Channel {

    String channelLabel();

    String transmit(String address, String title, String content);
}