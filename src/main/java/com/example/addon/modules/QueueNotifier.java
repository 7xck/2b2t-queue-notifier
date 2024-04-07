package com.example.addon.modules;

import com.example.addon.Addon;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.events.game.ReceiveMessageEvent;
import meteordevelopment.orbit.EventHandler;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class QueueNotifier extends Module {
    public QueueNotifier() {
        super(Addon.CATEGORY, "Queue Notifier", "An addon to notify you of your position in the 2b2t queue.");


    }

    Set<Integer> seen_positions = new HashSet<>();

    @EventHandler
    private void onQueueMsgReceive(ReceiveMessageEvent event) {
        String msg = event.getMessage().getString();

        // Use regular expression to find numbers in the message
        Integer extractedInt = extractQueuePosition(msg);

        if (!seen_positions.contains(extractedInt) && extractedInt % 10 == 0)  {
            seen_positions.add(extractedInt);
            sendPostRequest(String.valueOf(extractedInt));
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
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("")) // ZAPIER url here, or wherever you want to send the number to
            .header("Content-Type", "application/json")
            .POST(BodyPublishers.ofString(data))
            .build();

        client.sendAsync(request, BodyHandlers.ofString())
            .thenApply(HttpResponse::body)
            .thenAccept(System.out::println)
            .join();
    }
}
