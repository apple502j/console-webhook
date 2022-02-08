package io.github.apple502j.consolewebhook.mixin;

import io.github.apple502j.consolewebhook.ConsoleWebhookMod;
import io.github.apple502j.consolewebhook.Webhook;
import net.minecraft.server.MinecraftServer;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.UUID;

@Mixin(MinecraftServer.class)
public class MinecraftServerMixin {
    @Inject(method = "sendSystemMessage", at = @At("HEAD"))
    private void sendSystemMessage(Text message, UUID sender, CallbackInfo ci) {
        ConsoleWebhookMod.EXECUTOR.execute(() -> {
            Webhook.onMessage(message.getString());
        });
    }
}
