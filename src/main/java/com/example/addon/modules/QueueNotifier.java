package com.example.addon.modules;

import com.example.addon.Addon;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.events.game.ReceiveMessageEvent;
import meteordevelopment.orbit.EventHandler;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import meteordevelopment.meteorclient.settings.*;


public class QueueNotifier extends Module {
    Set<Integer> seen_positions = new HashSet<>();
    Integer last_alerted_position = 9999999;

    public QueueNotifier() {
        super(Addon.CATEGORY, "Queue Notifier", "An addon to notify you of your position in the 2b2t queue.");
    }
    private final SettingGroup sgDiscord = settings.createGroup("Discord");
    private final Setting<String> discordWebhook = sgDiscord.add(new StringSetting.Builder()
        .name("discord-webhook")
        .description("Add your discord server's webhook.")
        .defaultValue("your-webhook-from-server-settings")
        .build()
    );
    private final SettingGroup sgAlertStep = settings.createGroup("Alert Settings");
    private final Setting<Integer> queueAlertStep = sgAlertStep.add(new IntSetting.Builder()
        .name("queue-step")
        .description("Set the magnitude of movement to alert on. Max 50.")
        .defaultValue(10)
        .min(1)
        .max(50)
        .noSlider()
        .build()
    );
    private final Setting<Boolean> queueAggressiveAlert = sgAlertStep.add(new BoolSetting.Builder()
        .name("aggro-alert")
        .description("Aggressively alert when you're close to position 1.")
        .defaultValue(true)
        .build()
    );

    @EventHandler
    private void onQueueMsgReceive(ReceiveMessageEvent event) {
        String msg = event.getMessage().getString();
        // Use regular expression to find numbers in the message
        Integer extractedInt = extractQueuePosition(msg);
        boolean time_to_alert = (last_alerted_position - extractedInt) >= queueAlertStep.get();

        if (time_to_alert) {
            last_alerted_position = extractedInt;
            sendPostRequest(String.valueOf(extractedInt));
        }
        else if (queueAggressiveAlert.get() && extractedInt < 10) {
            if (!seen_positions.contains(extractedInt)) {
                seen_positions.add(extractedInt);
                sendPostRequest(String.valueOf(extractedInt));
            }
        }
    }

    public static int extractQueuePosition(String text) {
        Pattern pattern = Pattern.compile("Position in queue: (\\d+)");
        Matcher matcher = pattern.matcher(text);

        if (matcher.find()) {
            return Integer.parseInt(matcher.group(1));
        } else {
            return 11;
        }
    }

    private void sendPostRequest(String data) {
        try {
            assert mc.player != null;
            String username = mc.player.getName().getString();
            URL url = new URL(discordWebhook.get());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            String jsonPayload = "{\"content\": \"" + username + " is in position " + data + " in the 2b2t queue.\"}"; // Format the data as a JSON payload
            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(jsonPayload.getBytes(StandardCharsets.UTF_8));
            outputStream.close();
            int responseCode = connection.getResponseCode();
        }

        catch (Exception ignored) {}
    }
}
