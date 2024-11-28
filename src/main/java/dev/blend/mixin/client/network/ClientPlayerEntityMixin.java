package dev.blend.mixin.client.network;

import com.mojang.authlib.GameProfile;
import dev.blend.event.api.EventBus;
import dev.blend.event.impl.PostMotionEvent;
import dev.blend.event.impl.PreMotionEvent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public abstract class ClientPlayerEntityMixin extends AbstractClientPlayerEntity {

    public ClientPlayerEntityMixin(ClientWorld world, GameProfile profile) {
        super(world, profile);
    }

    @Shadow 
    protected abstract void sendSprintingPacket();
    @Shadow 
    protected abstract boolean isCamera();
    
    @Shadow private double lastX;
    @Shadow private double lastBaseY;
    @Shadow private double lastZ;
    @Shadow private float lastYaw;
    @Shadow private float lastPitch;
    @Shadow private int ticksSinceLastPositionPacketSent;

    @Shadow private boolean lastOnGround;

    @Shadow @Final public ClientPlayNetworkHandler networkHandler;

    @Shadow private boolean lastHorizontalCollision;

    @Shadow @Final protected MinecraftClient client;

    @Shadow private boolean autoJumpEnabled;

    @Shadow public abstract float getYaw(float tickDelta);

    @Inject(
            method = "sendMovementPackets",
            at = @At(
                    value = "HEAD"
            ),
            cancellable = true
    )
    private void iLikePublicStaticVoidMainStringArgs(CallbackInfo ci) {
        ci.cancel(); // cancel the method already
        this.sendSprintingPacket();
        if (this.isCamera()) {
            final PreMotionEvent event = new PreMotionEvent(this.getX(), this.getY(), this.getZ(), this.getYaw(), this.getPitch(), this.isOnGround());
            event.call();
            double d = event.getX() - this.lastX;
            double e = event.getY() - this.lastBaseY;
            double f = event.getZ() - this.lastZ;
            double g = (double)(event.getYaw() - this.lastYaw);
            double h = (double)(event.getPitch() - this.lastPitch);
            ++this.ticksSinceLastPositionPacketSent;
            boolean bl = MathHelper.squaredMagnitude(d, e, f) > MathHelper.square(2.0E-4) || this.ticksSinceLastPositionPacketSent >= 20;
            boolean bl2 = g != (double)0.0F || h != (double)0.0F;
            if (bl && bl2) {
                this.networkHandler.sendPacket(new PlayerMoveC2SPacket.Full(event.getX(), event.getY(), event.getZ(), event.getYaw(), event.getPitch(), event.getOnGround(), this.horizontalCollision));
            } else if (bl) {
                this.networkHandler.sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(event.getX(), event.getY(), event.getZ(), event.getOnGround(), this.horizontalCollision));
            } else if (bl2) {
                this.networkHandler.sendPacket(new PlayerMoveC2SPacket.LookAndOnGround(event.getYaw(), event.getPitch(), event.getOnGround(), this.horizontalCollision));
            } else if (this.lastOnGround != event.getOnGround() || this.lastHorizontalCollision != this.horizontalCollision) {
                this.networkHandler.sendPacket(new PlayerMoveC2SPacket.OnGroundOnly(event.getOnGround(), this.horizontalCollision));
            }

            if (bl) {
                this.lastX = event.getX();
                this.lastBaseY = event.getY();
                this.lastZ = event.getZ();
                this.ticksSinceLastPositionPacketSent = 0;
            }

            if (bl2) {
                this.lastYaw = event.getYaw();
                this.lastPitch = event.getPitch();
            }

            this.lastOnGround = event.getOnGround();
            this.lastHorizontalCollision = this.horizontalCollision;
            this.autoJumpEnabled = (Boolean)this.client.options.getAutoJump().getValue();
            EventBus.INSTANCE.post(new PostMotionEvent());
        }
    }

}
