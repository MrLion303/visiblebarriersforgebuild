package xyz.amymialee.visiblebarriers.mixin;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Marker;
import net.minecraft.world.level.Level;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Marker.class)
public abstract class MarkerEntityMixin extends Entity {
    protected MarkerEntityMixin(EntityType<?> type, Level level) { super(type, level); }

    @Inject(method = "getAddEntityPacket", at = @At("HEAD"), cancellable = true)
    private void visibleBarriers$forcePacket(CallbackInfoReturnable<Packet<?>> cir) {
        cir.setReturnValue(new ClientboundAddEntityPacket((Marker)(Object)this));
    }
}
