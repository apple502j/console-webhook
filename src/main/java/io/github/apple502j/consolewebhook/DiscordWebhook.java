package io.github.apple502j.consolewebhook;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Nullable;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class DiscordWebhook extends Webhook {
    private final Gson gson = new Gson();
    protected DiscordWebhook(String url) {
        super(URI.create(url));
    }

    public static @Nullable Webhook create() {
        String url = System.getenv("DISCORD_WEBHOOK");
        if (StringUtils.isEmpty(url)) return null;
        try {
            return new DiscordWebhook(url);
        } catch (RuntimeException exc) {
            return null;
        }
    }

    @Override
    public void send(String message) {
        JsonObject allowedMentions = new JsonObject();
        allowedMentions.add("parse", new JsonArray());
        JsonObject json = new JsonObject();
        json.addProperty("content", message);
        json.add("allowed_mentions", allowedMentions);
        json.addProperty("flags", 1 << 2); // suppress embeds
        HttpRequest request = HttpRequest.newBuilder()
                .uri(this.uri)
                .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(json), StandardCharsets.UTF_8))
                .header("Content-Type", "application/json")
                .header("User-Agent", "Console-Webhook-Mod")
                .build();
        this.client.sendAsync(request, HttpResponse.BodyHandlers.discarding());
    }
}
