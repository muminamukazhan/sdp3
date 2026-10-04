import channel.Channel;
import channel.EmailChannel;
import channel.SmsChannel;
import notification.Notification;
import notification.Reminder;
import notification.UrgentAlert;

import java.util.List;

public class Main {

    private static final String RECEIVER = "Komugi";
    private static final String REMINDER_TEXT = "Gungi match with Meruem at 20:00";
    private static final String ALERT_TEXT = "The palace lights go out in 10 minutes";

    private static final String EMAIL_REMINDER =
            "[EMAIL] To: Komugi | Subject: Reminder | Body: Gungi match with Meruem at 20:00";
    private static final String SMS_REMINDER =
            "[SMS] Komugi: Reminder - Gungi match with Meruem at 20:00";
    private static final String EMAIL_ALERT =
            "[EMAIL] To: Komugi | Subject: URGENT: Goodnight, Meruem | Body: The palace lights go out in 10 minutes";
    private static final String SMS_ALERT =
            "[SMS] Komugi: URGENT: Goodnight, Meruem - The palace lights go out in 10 minutes";

    private static int passed = 0;
    private static int total = 0;

    public static void main(String[] args) {
        if (args.length == 1 && "--demo".equals(args[0])) {
            runDemo();
        } else {
            System.out.println("Usage: java -cp out Main --demo");
        }
    }

    private static void runDemo() {
        Channel email = new EmailChannel();
        Channel sms = new SmsChannel();

        verifyPair("T1", new Reminder("REM-001", RECEIVER, REMINDER_TEXT, email), email, EMAIL_REMINDER);
        verifyPair("T2", new Reminder("REM-001", RECEIVER, REMINDER_TEXT, sms), sms, SMS_REMINDER);
        verifyPair("T3", new UrgentAlert("ALR-001", RECEIVER, ALERT_TEXT, email), email, EMAIL_ALERT);
        verifyPair("T4", new UrgentAlert("ALR-001", RECEIVER, ALERT_TEXT, sms), sms, SMS_ALERT);
        verifyRuntimeSwitch();

        System.out.println("SUMMARY: " + passed + "/" + total + " PASS");
        if (passed != total) {
            System.exit(1);
        }
    }

    private static void verifyPair(String id, Notification notification, Channel channel, String expected) {
        String actual = notification.execute();
        boolean pass = expected.equals(actual);
        String participants = notification.getClass().getSimpleName()
                + " + " + channel.getClass().getSimpleName();
        report(pass, id + " " + verdict(pass) + " | " + participants + " | result=" + actual,
                "result=" + expected);
    }

    private static void verifyRuntimeSwitch() {
        Notification firstRef = new Reminder("REM-005", RECEIVER, REMINDER_TEXT, new EmailChannel());
        List<Notification> queue = List.of(firstRef);

        Notification current = queue.get(0);
        String before = current.execute();
        current.setImplementation(new SmsChannel());
        String after = current.execute();

        boolean sameObject = firstRef == current;
        boolean stateUnchanged = "REM-005".equals(current.getId())
                && RECEIVER.equals(current.getReceiver())
                && REMINDER_TEXT.equals(current.getMessage());
        boolean pass = sameObject && stateUnchanged
                && EMAIL_REMINDER.equals(before) && SMS_REMINDER.equals(after);

        report(pass,
                "T5 " + verdict(pass) + " | sameObject=" + sameObject
                        + " | stateUnchanged=" + stateUnchanged
                        + "\n before=" + before + " | after=" + after,
                "sameObject=true | stateUnchanged=true\n before=" + EMAIL_REMINDER
                        + " | after=" + SMS_REMINDER);
    }

    private static void report(boolean pass, String line, String expectedLine) {
        total++;
        if (pass) {
            passed++;
        }
        System.out.println(line);
        if (!pass) {
            System.out.println(" expected: " + expectedLine);
        }
    }

    private static String verdict(boolean pass) {
        return pass ? "PASS" : "FAIL";
    }
}
