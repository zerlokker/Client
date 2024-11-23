package dev.blend.mixin.client;

import dev.blend.event.impl.KeyEvent;
import net.minecraft.client.Keyboard;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Keyboard.class)
public class KeyboardMixin {

    @Inject(
            method = "onKey",
            at = @At(
                    value = "HEAD"
            )
    )
    private void onKey(long window, int key, int scancode, int action, int modifiers, CallbackInfo ci) {
        if (
                MinecraftClient.getInstance().getWindow().getHandle() == window
                && MinecraftClient.getInstance().player != null
                && MinecraftClient.getInstance().world != null
                && MinecraftClient.getInstance().currentScreen == null
        ) {
            new KeyEvent(window, key, scancode, action, modifiers).call();
        }
    }

}
