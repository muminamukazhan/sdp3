# Assignment 3 | Bridge Pattern

- **Name:** Mumina Mukazhan
- **Group:** SE-2530
- **Topic:** B (Notifications)
- **Repository:** https://github.com/muminamukazhan/sdp3
- **Base commit (I1/I2 version, before PushChannel):** a1095d6c6741d49008b39494e41a23ce58374d9f

## Role map

| Role | Class | Source path |
|---|---|---|
| Abstraction | `Notification` | `src/notification/Notification.java` |
| A1 | `Reminder` | `src/notification/Reminder.java` |
| A2 | `UrgentAlert` | `src/notification/UrgentAlert.java` |
| Implementor | `Channel` | `src/channel/Channel.java` |
| I1 | `EmailChannel` | `src/channel/EmailChannel.java` |
| I2 | `SmsChannel` | `src/channel/SmsChannel.java` |
| I3 (extension) | `PushChannel` | `src/channel/PushChannel.java` |
| Client | `Main` | `src/Main.java` |

## Where to look

- **Bridge field:** `private Channel deliveryChannel` in `Notification` (interface-typed, supplied through the constructor).
- **`execute()`:** abstract in `Notification`; implemented in `Reminder` and `UrgentAlert`.
- **`setImplementation(Channel)`:** in `Notification`; replaces the bridge reference at runtime.
- **Delegation point:** `Notification.sendViaChannel(...)` calls `deliveryChannel.transmit(...)`.
- **T5 check:** `Main.verifyRuntimeSwitch()`.

## Build and run

From the extracted project folder:

    javac --release 17 -encoding UTF-8 -d out "@sources.txt"
    java -cp out Main --demo

## Expected results

All seven checks print PASS, followed by `SUMMARY: 7/7 PASS`.

| Check | Setup | Expected result |
|---|---|---|
| T1 | Reminder + EmailChannel | `[EMAIL] To: Komugi \| Subject: Reminder \| Body: Gungi match with Meruem at 20:00` |
| T2 | Reminder + SmsChannel | `[SMS] Komugi: Reminder - Gungi match with Meruem at 20:00` |
| T3 | UrgentAlert + EmailChannel | `[EMAIL] To: Komugi \| Subject: URGENT: Goodnight, Meruem \| Body: The palace lights go out in 10 minutes` |
| T4 | UrgentAlert + SmsChannel | `[SMS] Komugi: URGENT: Goodnight, Meruem - The palace lights go out in 10 minutes` |
| T5 | One Reminder object: Email, then switched to SMS | `sameObject=true`, `stateUnchanged=true`, before = T1 result, after = T2 result |
| T6 | Reminder + PushChannel | `[PUSH] Device: Komugi \| Title: Reminder \| Text: Gungi match with Meruem at 20:00` |
| T7 | UrgentAlert + PushChannel | `[PUSH] Device: Komugi \| Title: URGENT: Goodnight, Meruem \| Text: The palace lights go out in 10 minutes` |

Last line: `SUMMARY: 7/7 PASS`

The captured console output is in `demo-output.txt`.

## Extension (I3)

`PushChannel` was added after the base commit. In `src/`, only the new `PushChannel.java` and `Main.java` (checks T6/T7) changed.
`Notification`, `Reminder`, `UrgentAlert`, `Channel`, `EmailChannel` and `SmsChannel` stayed unchanged.
The source diff is in `extension.diff`.

## Design notes

- `Notification` is the Abstraction. It stores the domain data (`id`, `receiver`, `message`) and the bridge field.
- `Channel` is the Implementor. Each channel only formats and "delivers" a message as a string.
- The title `URGENT: Goodnight, Meruem` is in `UrgentAlert` because it is part of A2 behavior, not of any channel.
- Expected strings in `Main` are written by hand, so each PASS comes from a real comparison with the application output.

## Submission files

`src/`, `sources.txt`, `README.md`, `report.pdf`, `demo-output.txt`, `extension.diff`