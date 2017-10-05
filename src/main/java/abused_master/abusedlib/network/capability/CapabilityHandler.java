package abused_master.abusedlib.network.capability;

import abused_master.abusedlib.AbusedLib;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class CapabilityHandler {

    public static final ResourceLocation CAPABILITY_ESSENCE = new ResourceLocation(AbusedLib.MODID, "essence");

    @SubscribeEvent
    public void attachCapability(AttachCapabilitiesEvent<Entity> event) {
    }
}
