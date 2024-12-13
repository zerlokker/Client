package dev.blend.mixin.client.gui.screen;

import dev.blend.event.impl.ChatSendEvent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ChatScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ChatScreen.class)
public class ChatScreenMixin {

    @Inject(
            method = "keyPressed",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/screen/ChatScreen;sendMessage(Ljava/lang/String;Z)V"
            )
    )
    private void setScreenToNullBeforeTriggeringEventSoICanToggleClickGUIAndNotHaveItClosedByThis(int keyCode, int scanCode, int modifiers, CallbackInfoReturnable<Boolean> cir) {
        MinecraftClient.getInstance().setScreen(null);
    }

    @Inject(
            method = "sendMessage",
            at = @At(
                    value = "HEAD"
            ),
            cancellable = true
    )
    private void onChatSend(String chatText, boolean addToHistory, CallbackInfo ci) {
        final ChatSendEvent event = new ChatSendEvent(chatText);
        event.call();
        if (event.isCancelled()) {
            ci.cancel();
        }
    }

}
