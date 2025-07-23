package de.kyrohpaneup.parkourutils.hud.module;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.MathHelper;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class CoordsTracker {

    private boolean jumpTracker = false;

    public static String lastStrafe = "N/A";

    private int ticks = 0;
    private boolean isWad = false;
    private boolean checkStrafe = false;
    private boolean isInAir = false;
    private boolean wasInAir = false;
    public static double lastLandX, lastLandY, lastLandZ;
    public static double jumpX, jumpY, jumpZ;
    public static double jumpAngle;
    public static double facing, pitch;
    public static double x, y, z;

    private Minecraft mc;

    public CoordsTracker() {
        this.mc = Minecraft.getMinecraft();
    }

    public void onUpdate() {
        //mc = Minecraft.getMinecraft();
        EntityPlayerSP player = mc.thePlayer;

        if (player == null) return;

        // normal coords
        x = mc.thePlayer.posX;
        y = mc.thePlayer.posY;
        z = mc.thePlayer.posZ;
        facing = Float.valueOf(MathHelper.wrapAngleTo180_float(player.rotationYaw));
        pitch = Float.valueOf(MathHelper.wrapAngleTo180_float(player.rotationPitch));

        if (!player.onGround) {
            wasInAir = true;
        } else {
            if (wasInAir) {
                lastLandX = player.prevPosX;
                lastLandY = player.prevPosY;
                lastLandZ = player.prevPosZ;

                wasInAir = false;
            }
        }
    }

    private boolean isWad() {
        return isD() && isA();
    }

    private boolean isStrafe() {
        return isD() || isA();
    }

    private boolean isA() {
        return mc.gameSettings.keyBindLeft.isKeyDown();
    }

    private boolean isD() {
        return mc.gameSettings.keyBindRight.isKeyDown();
    }

    private boolean strafedOnJump = false; // Neue Variable, um zu prüfen, ob beim Sprung gestraft wurde

    @SubscribeEvent
    public void onJump(LivingEvent.LivingJumpEvent event) {
        if (event.entity instanceof EntityPlayerSP) {
            EntityPlayerSP player = mc.thePlayer;
            // Jump Coords
            jumpX = mc.thePlayer.posX;
            jumpY = mc.thePlayer.posY;
            jumpZ = mc.thePlayer.posZ;

            // Facings;
            jumpAngle = MathHelper.wrapAngleTo180_float(player.rotationYaw);
            // Last Sidestep
            ticks = 0;
            checkStrafe = true;
            isWad = isWad();

            if (isStrafe() && !isWad) {
                lastStrafe = "0t Strafe";
                strafedOnJump = true;
                checkStrafe = false;
            } else {
                lastStrafe = "N/A";
                strafedOnJump = false;
            }
        }
    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.START || !(event.player instanceof EntityPlayerSP)) return;

        isInAir = !event.player.onGround;
        if (isInAir && checkStrafe) {
            ticks++;

            if (isWad() && isWad) {
                lastStrafe = "WAD " + (ticks + 1) + "t";
            } else if (!strafedOnJump && isStrafe() && !isWad) {
                lastStrafe = ticks + "t Strafe";
                checkStrafe = false;
            } else if (isWad) {
                checkStrafe = false;
            }

        } else if (!isInAir) {
            checkStrafe = false;
            strafedOnJump = false;
            ticks = 0;
        }
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            onUpdate();
        }
    }
}
