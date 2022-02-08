package io.github.apple502j.consolewebhook;

import org.jetbrains.annotations.Nullable;

import java.net.URI;
import java.net.http.HttpClient;

public abstract class Webhook {
    private static Webhook current;
    protected URI uri;
    protected HttpClient client = HttpClient.newBuilder().build();
    protected Webhook(URI uri) {
        this.uri = uri;
    }

    private static @Nullable Webhook get() {
        if (current != null) return current;
        current = DiscordWebhook.create();
        return current;
    }

    public static void onMessage(String message) {
        Webhook hook = Webhook.get();
        if (hook != null) hook.send(message);
    }

    public abstract void send(String message);
}
