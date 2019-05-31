package abused_master.abusedlib.utils;

import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormatElement;
import net.minecraft.util.math.Direction;


public class VertexDataUtils {

    /**
     * Packs the unpacked float data for the specified element and vertex back into the quad
     * int packed data format.
     *
     * Credits to MinecraftForge's RainWarrior(Fry)
     * https://github.com/MinecraftForge/MinecraftForge/blob/a5f29570a39508afe96870e7e665ff4bbf964aa8/src/main/java/net/minecraftforge/client/model/pipeline/LightUtil.java#L151-L244
     *
     * @param source The source to pack.
     * @param dest   The destination to pack into.
     * @param fmt    The VertexFormat of the destination.
     * @param v      The Vertex we are packing.
     * @param e      The element of the Vertex we are packing.
     */
    public static void packData(float[] source, int[] dest, VertexFormat fmt, int v, int e) {
        VertexFormatElement element = fmt.getElement(e);
        int vertexStart = v * fmt.getVertexSize() + fmt.getElementOffset(e);
        int count = element.getCount();
        VertexFormatElement.Format format = element.getFormat();
        int size = format.getSize();
        int mask = (256 << (8 * (size - 1))) - 1;
        for (int i = 0; i < 4; i++) {
            if (i < count) {
                int pos = vertexStart + size * i;
                int index = pos >> 2;
                int offset = pos & 3;
                int bits;
                float f = i < source.length ? source[i] : 0;
                switch (format) {
                    case FLOAT:
                        bits = Float.floatToRawIntBits(f);
                        break;
                    case UBYTE:
                    case USHORT:
                    case UINT:
                        bits = Math.round(f * mask);
                        break;
                    default:
                        bits = Math.round(f * (mask >> 1));
                        break;
                }
                dest[index] &= ~(mask << (offset * 8));
                dest[index] |= (((bits & mask) << (offset * 8)));
            }
        }
    }

    /**
     * Calculates the EnumFacing for a given normal.
     *
     * @param normal The normal to calculate from.
     * @return The direction the normal is facing.
     */
    public static Direction calcNormalSide(float[] normal) {
        if (normal[1] <= -0.99) {
            return Direction.DOWN;
        }
        if (normal[1] >= 0.99) {
            return Direction.UP;
        }
        if (normal[2] <= -0.99) {
            return Direction.NORTH;
        }
        if (normal[2] >= 0.99) {
            return Direction.SOUTH;
        }
        if (normal[0] <= -0.99) {
            return Direction.WEST;
        }
        if (normal[0] >= 0.99) {
            return Direction.EAST;
        }
        return null;
    }
}
