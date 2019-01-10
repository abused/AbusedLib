package abused_master.abusedlib;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;

import java.util.logging.Logger;

public class AbusedLib implements ModInitializer, ClientModInitializer {

    public static final String MODID = "abusedlib";
    public static Logger LOGGER = Logger.getLogger("AbusedLib");

    @Override
    public void onInitializeClient() {
    }

    @Override
    public void onInitialize() {
    }
}
