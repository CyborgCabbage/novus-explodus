package io.github.cyborgcabbage.explodus.render;

import io.github.cyborgcabbage.explodus.entity.PrimedBombEntity;
import net.minecraft.block.Block;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;

public class PrimedBombRenderer extends EntityRenderer {
    private final BlockRenderManager renderBlocks = new BlockRenderManager();

    public PrimedBombRenderer() {
        this.shadowRadius = 0.5F;
    }

    public void render(Entity entity, double x, double y, double z, float yaw, float delta) {
        if (entity instanceof PrimedBombEntity primedBombEntity) {
            GL11.glPushMatrix();
            GL11.glTranslatef((float) x, (float) y, (float) z);
            // Grow just before explosion
            float adjustedFuse = primedBombEntity.fuse - delta + 1.0F;
            if (adjustedFuse < 10.0F) {
                float var10 = 1.0F - adjustedFuse / 10.0F;
                if (var10 < 0.0F) {
                    var10 = 0.0F;
                }

                if (var10 > 1.0F) {
                    var10 = 1.0F;
                }

                var10 *= var10;
                var10 *= var10;
                float scale = 1.0F + var10 * 0.3F;
                GL11.glScalef(scale, scale, scale);
            }
            // Render block normal
            this.bindTexture("/terrain.png");
            this.renderBlocks.render(Block.BLOCKS[primedBombEntity.blockId], 0, primedBombEntity.getBrightnessAtEyes(delta));
            // Render block white
            float whiteness = (1.0F - Math.min(adjustedFuse, 80.0f) / 100.0F) * 0.8F;
            if (primedBombEntity.fuse / 5 % 2 == 0) {
                GL11.glDisable(GL11.GL_TEXTURE_2D);
                GL11.glDisable(GL11.GL_LIGHTING);
                GL11.glEnable(GL11.GL_BLEND);
                GL11.glBlendFunc(770, 772);
                GL11.glColor4f(1.0F, 1.0F, 1.0F, whiteness);
                this.renderBlocks.render(Block.BLOCKS[primedBombEntity.blockId], 0, 1.0f);
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                GL11.glDisable(GL11.GL_BLEND);
                GL11.glEnable(GL11.GL_LIGHTING);
                GL11.glEnable(GL11.GL_TEXTURE_2D);
            }

            GL11.glPopMatrix();
        }
    }
}
