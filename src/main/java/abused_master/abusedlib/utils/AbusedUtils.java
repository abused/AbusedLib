package abused_master.abusedlib.utils;

import net.minecraft.util.EnumFacing;

public class AbusedUtils {

    public static final int MASK_ORIENTATION_HORIZONTAL = 0x3;

    public static EnumFacing getOrientationHoriz(int metadata) {
        return EnumFacing.VALUES[(metadata & MASK_ORIENTATION_HORIZONTAL)+2];
    }
}
