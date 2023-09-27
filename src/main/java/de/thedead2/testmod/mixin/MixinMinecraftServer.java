package de.thedead2.testmod.mixin;

import com.mojang.datafixers.DataFixer;
import de.thedead2.testmod.Config;
import de.thedead2.testmod.exception.CustomException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.Services;
import net.minecraft.server.WorldStem;
import net.minecraft.server.level.progress.ChunkProgressListenerFactory;
import net.minecraft.server.packs.repository.PackRepository;
import net.minecraft.world.level.storage.LevelStorageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.net.Proxy;


@Mixin(MinecraftServer.class)
public class MixinMinecraftServer {

    @Inject(at = @At("TAIL"), method = "<init>(Ljava/lang/Thread;Lnet/minecraft/world/level/storage/LevelStorageSource$LevelStorageAccess;Lnet/minecraft/server/packs/repository/PackRepository;Lnet/minecraft/server/WorldStem;Ljava/net/Proxy;Lcom/mojang/datafixers/DataFixer;Lnet/minecraft/server/Services;Lnet/minecraft/server/level/progress/ChunkProgressListenerFactory;)V")
    public void onInit(Thread pServerThread, LevelStorageSource.LevelStorageAccess pStorageSource, PackRepository pPackRepository, WorldStem pWorldStem, Proxy pProxy, DataFixer pFixerUpper, Services pServices, ChunkProgressListenerFactory pProgressListenerFactory, CallbackInfo ci){
        if(Config.CRASH_ON_SERVER.get()){
            throw new CustomException("Server crash!");
        }
    }
}
