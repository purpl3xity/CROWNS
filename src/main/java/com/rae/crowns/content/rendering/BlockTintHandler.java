package com.rae.crowns.content.rendering;

import com.rae.crowns.content.nuclear.fuel_assembly.AssemblyBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.rae.crowns.Constants.*;
import static org.joml.Math.clamp;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class BlockTintHandler {

    @SubscribeEvent
    public static void registerBlockTints(RegisterColorHandlersEvent.Block event) {
        event.register((state, world, pos, tintIndex) -> {
            if (world != null && pos != null) {
                BlockEntity be = world.getBlockEntity(pos);

                if (be instanceof AssemblyBlockEntity abe) {
                    double t = abe.getTemperature() / 1000.0;
                    double r, g, b;

                    r = clamp(t < 1.5 ? 0 : (t - 1.5) / 1.5, 0, 1);
                    g = clamp(Math.exp(-Math.pow((t - 2.0) / 0.8, 2)), 0, 1);
                    b = clamp(t > 3.0 ? (t - 3.0) : 0, 0, 1);

                    r = Math.pow(r, 1/2.2);
                    g = Math.pow(g, 1/2.2);
                    b = Math.pow(b, 1/2.2);

                    return ((int)(r*255) << 16) | ((int)(g*255) << 8) | (int)(b*255);
                }
            }

            return 0xFFFFFF;
        });
    }
}
