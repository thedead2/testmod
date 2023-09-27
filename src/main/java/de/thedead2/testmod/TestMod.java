package de.thedead2.testmod;

import com.mojang.logging.LogUtils;
import de.thedead2.testmod.exception.CustomException;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;


@Mod(TestMod.MOD_ID)
public class TestMod {
    public static final String MOD_ID = "testmod";
    private static final Logger LOGGER = LogUtils.getLogger();

    public TestMod() {
        FMLJavaModLoadingContext.get().getModEventBus().register(this);
        MinecraftForge.EVENT_BUS.register(this);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);

        if(Config.CRASH_ON_INIT.get()){
            throw new CustomException("Init crash!");
        }
    }

    @SubscribeEvent
    public void onSetup(final FMLCommonSetupEvent event){
        if(Config.CRASH_ON_SETUP.get()){
            throw new CustomException("Setup crash!");
        }
    }

    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
    public static class ClientModEvents {

        public static final KeyMapping CRASH_KEY = new KeyMapping("testmod.keys.crash_key", GLFW.GLFW_KEY_ENTER, "key.categories.misc");

        @SubscribeEvent
        public static void onKeyRegistering(final RegisterKeyMappingsEvent event){
            event.register(CRASH_KEY);
        }

        @SubscribeEvent
        public static void onClientTick(final TickEvent.ClientTickEvent event) {
            if (Config.CRASH_ON_KEY_PRESS.get() && CRASH_KEY.isDown()) {
                throw new CustomException("Key pressed!");
            }
        }

    }
}
