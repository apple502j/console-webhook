package io.github.apple502j.consolewebhook;

import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Util;

import java.util.concurrent.ExecutorService;

public class ConsoleWebhookMod implements ModInitializer {
    public static final ExecutorService EXECUTOR = Util.getIoWorkerExecutor();

    @Override
    public void onInitialize() {
    }
}