package abused_master.abusedlib.proxy;

import abused_master.abusedlib.api.Essence;
import abused_master.abusedlib.api.EssenceStorage;
import abused_master.abusedlib.api.IEssence;
import abused_master.abusedlib.network.capability.CapabilityHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy {

    public void preInit(FMLPreInitializationEvent e) {
    }

    public void init(FMLInitializationEvent e) {
        MinecraftForge.EVENT_BUS.register(new CapabilityHandler());
        CapabilityManager.INSTANCE.register(IEssence.class, new EssenceStorage(), Essence.class);
    }

    public void postInit(FMLPostInitializationEvent e) {
    }
}
