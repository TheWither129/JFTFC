package net.witheraway.tfcjutefurniture.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.network.NetworkHooks;

import java.util.List;

public class
SeatEntity extends Entity {
    private BlockPos seat;

    public SeatEntity(EntityType<? extends Entity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public SeatEntity(Level level) {
        super(JFTFCEntities.SEAT_ENTITY.get(), level);
        this.noPhysics = true;
    }

    private SeatEntity(Level level, BlockPos pos, double yOffset) {
        this(level);
        this.seat = pos;
        this.setPos((double) this.seat.getX() + 0.5, (double) this.seat.getY() + yOffset, (double) this.seat.getZ() + 0.5);
    }

    protected void readAdditionalSaveData(CompoundTag tag) {
    }

    protected void addAdditionalSaveData(CompoundTag tag) {
    }

    protected boolean canRide(Entity entity) {
        return true;
    }

    protected void defineSynchedData() {
    }

    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    public void tick() {
        super.tick();
        if (!this.level().isClientSide && (this.getPassengers().isEmpty() || this.level().isEmptyBlock(this.blockPosition()))) {
            this.remove(RemovalReason.DISCARDED);
        }

    }

    public double getMyRidingOffset() {
        return 0.0;
    }

    public static InteractionResult create(Level level, BlockPos pos, double doub, Player player) {
        if (!level.isClientSide()) {
            List<SeatEntity> seatsInThisBlock = level.getEntitiesOfClass(SeatEntity.class, new AABB((double) pos.getX(), (double) pos.getY(), (double) pos.getZ(), (double) pos.getX() + 1.0, (double) pos.getY() + 1.0, (double) pos.getZ() + 1.0));
            if (seatsInThisBlock.isEmpty()) {
                SeatEntity seat = new SeatEntity(level, pos, doub);
                level.addFreshEntity(seat);
                player.startRiding(seat);
            }
        }
        return InteractionResult.SUCCESS;
    }
}
