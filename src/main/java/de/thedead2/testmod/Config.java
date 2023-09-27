package de.thedead2.testmod;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.loading.FMLPaths;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Supplier;


@Mod.EventBusSubscriber(modid = TestMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config
{

    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.BooleanValue CRASH_ON_KEY_PRESS;
    public static final Supplier<Boolean> CRASH_ON_INIT = () -> {
        Path configDir = FMLPaths.CONFIGDIR.get();
        Path testModePath = configDir.resolve("init_crash.txt");
        try {
            Files.createDirectories(testModePath.getParent());
            if (!Files.exists(testModePath)) {
                Files.createFile(testModePath);

                InputStream inputStream = new ByteArrayInputStream("false".getBytes());

                OutputStream fileOut = Files.newOutputStream(testModePath);
                int input;
                while ((input = inputStream.read()) != -1){
                    fileOut.write(input);
                }

                inputStream.close();
                fileOut.close();
            }
            return Boolean.parseBoolean(new String(Files.readAllBytes(testModePath)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    };
    public static final ForgeConfigSpec.BooleanValue CRASH_ON_SETUP;
    public static final ForgeConfigSpec.BooleanValue CRASH_ON_SERVER;

    static {
        CRASH_ON_KEY_PRESS = BUILDER.define("crashOnKeyPress", false);
        CRASH_ON_SETUP = BUILDER.define("crashOnSetup", false);
        CRASH_ON_SERVER = BUILDER.define("crashOnServer", false);

        SPEC = BUILDER.build();
    }

}
