package io.github.cyborgcabbage.explodus.entity;

import io.github.cyborgcabbage.explodus.Explodus;
import io.github.cyborgcabbage.explodus.block.BombBlock;
import io.github.cyborgcabbage.explodus.explosion.NeoExplosion;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import net.modificationstation.stationapi.api.server.entity.EntitySpawnDataProvider;
import net.modificationstation.stationapi.api.server.entity.HasTrackingParameters;
import net.modificationstation.stationapi.api.util.Identifier;
import net.modificationstation.stationapi.api.util.TriState;

@HasTrackingParameters(trackingDistance = 160, updatePeriod = 1, sendVelocity = TriState.TRUE)
public class PrimedBombEntity extends Entity implements EntitySpawnDataProvider {
    public static final Identifier ID = Explodus.NAMESPACE.id("primed_bomb");
    public int blockId;
    public int fuse;

    public PrimedBombEntity(World world) {
        super(world);
        this.blocksSameBlockSpawning = true;
        this.setBoundingBoxSpacing(0.98F, 0.98F);
        this.standingEyeHeight = this.height / 2.0F;
    }

    public PrimedBombEntity(World world, double x, double y, double z) {
        this(world);
        setPosition(x, y, z);
    }

    public PrimedBombEntity(World world, double x, double y, double z, int blockId) {
        this(world, x, y, z);
        this.blockId = blockId;
        double radians = Math.random() * Math.PI * 2.0;
        this.velocityX = Math.sin(radians * Math.PI / 180.0) * 0.02;
        this.velocityY = 0.2;
        this.velocityZ = Math.cos(radians * Math.PI / 180.0) * 0.02;
        this.fuse = 80;
        this.prevX = x;
        this.prevY = y;
        this.prevZ = z;
    }

    protected boolean bypassesSteppingEffects() {
        return false;
    }

    public boolean isCollidable() {
        return !this.dead;
    }

    public void tick() {
        Explodus.LOGGER.debug("TICK");
        this.prevX = this.x;
        this.prevY = this.y;
        this.prevZ = this.z;
        this.velocityY -= 0.04;
        this.move(this.velocityX, this.velocityY, this.velocityZ);
        this.velocityX *= 0.98;
        this.velocityY *= 0.98;
        this.velocityZ *= 0.98;
        if (this.onGround) {
            this.velocityX *= 0.7;
            this.velocityZ *= 0.7;
            this.velocityY *= -0.5;
        }

        if (this.fuse-- <= 0) {
            if (!this.world.isRemote) {
                this.explode();
            }
            this.markDead();
        } else {
            this.world.addParticle("smoke", this.x, this.y + 0.5, this.z, 0.0, 0.0, 0.0);
        }
    }

    private void explode() {
        NeoExplosion explosion = ((BombBlock)Block.BLOCKS[blockId]).createExplosion(world, this, this.x, this.y, this.z);
        explosion.explode();
    }

    @Override
    protected void initDataTracker() {
    }

    @Override
    protected void readNbt(NbtCompound nbt) {
        this.blockId = nbt.getInt("BlockID");
        this.fuse = nbt.getInt("Fuse");
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        nbt.putInt("BlockID", this.blockId);
        nbt.putInt("Fuse", this.fuse);
    }

    @Override
    public Identifier getHandlerIdentifier() {
        return ID;
    }

    @Environment(EnvType.CLIENT)
    @Override
    public float getShadowRadius() {
        return 0.0F;
    }
}
