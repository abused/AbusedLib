package abused_master.abusedlib;

import abused_master.abusedlib.registry.CapabilityRegistry;
import net.fabricmc.api.ModInitializer;

import java.util.logging.Logger;

public class AbusedLib implements ModInitializer {

    public static final String MODID = "abusedlib";
    public static Logger LOGGER = Logger.getLogger("AbusedLib");

    @Override
    public void onInitialize() {
        CapabilityRegistry.INSTANCE.registerCapabilities();
    }
}
