package abused_master.abusedlib;

import abused_master.abusedlib.proxy.CommonProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = AbusedLib.MODID, name = AbusedLib.MODNAME,
version = AbusedLib.VERSION, acceptedMinecraftVersions = AbusedLib.ACCEPTED_VERSIONS)
public class AbusedLib {

    public static final String MODID = "abusedlib";
    public static final String MODNAME = "AbusedLib";
    public static final String VERSION = "1.0.7_1.12";
    public static final String ACCEPTED_VERSIONS = "[1.12,1.12.2] ";

    @SidedProxy(clientSide = "abused_master.abusedlib.proxy.ClientProxy", serverSide = "abused_master.abusedlib.proxy.CommonProxy")
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent e) {
        this.proxy.preInit(e);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent e) {
        this.proxy.init(e);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent e) {
        this.proxy.postInit(e);
    }
}
