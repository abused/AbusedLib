package abused_master.abusedlib.client.render.obj;

import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormatElement;
import net.minecraft.client.render.model.BakedQuad;
import net.minecraft.client.texture.Sprite;
import net.minecraft.util.math.Direction;

public class UnpackedBakedQuad extends BakedQuad {

    protected final float[][][] unpackedData;
    protected final VertexFormat format;
    protected boolean packed = false;

    public UnpackedBakedQuad(float[][][] unpackedData, int tint, Direction orientation, Sprite texture, VertexFormat format) {
        super(new int[format.getVertexSize()], tint, orientation, texture);
        this.unpackedData = unpackedData;
        this.format = format;
    }

    @Override
    public int[] getVertexData() {
        if (!packed) {
            packed = true;
            for (int v = 0; v < 4; v++) {
                for (int e = 0; e < format.getElementCount(); e++) {
                    pack(unpackedData[v][e], vertexData, format, v, e);
                }
            }
        }
        return vertexData;
    }

    public static void pack(float[] from, int[] to, VertexFormat formatTo, int v, int e) {
        VertexFormatElement element = formatTo.getElement(e);
        int vertexStart = v * formatTo.getVertexSize() + formatTo.getElementOffset(e);
        int count = element.getCount();
        VertexFormatElement.Format type = element.getFormat();
        int size = type.getSize();
        int mask = (256 << (8 * (size - 1))) - 1;
        for (int i = 0; i < 4; i++) {
            if (i < count) {
                int pos = vertexStart + size * i;
                int index = pos >> 2;
                int offset = pos & 3;
                int bits;
                float f = i < from.length ? from[i] : 0;
                if (type == VertexFormatElement.Format.FLOAT) {
                    bits = Float.floatToRawIntBits(f);
                } else if (
                        type == VertexFormatElement.Format.UBYTE ||
                                type == VertexFormatElement.Format.USHORT ||
                                type == VertexFormatElement.Format.UINT
                ) {
                    bits = Math.round(f * mask);
                } else {
                    bits = Math.round(f * (mask >> 1));
                }
                to[index] &= ~(mask << (offset * 8));
                to[index] |= (((bits & mask) << (offset * 8)));
            }
        }
    }

    public static class Builder {
        private final VertexFormat format;
        private final float[][][] unpackedData;
        private int tint = -1;
        private Direction orientation;
        private Sprite texture;

        private int vertices = 0;
        private int elements = 0;
        private boolean full = false;
        private boolean contractUVs = false;

        public Builder(VertexFormat format) {
            this.format = format;
            unpackedData = new float[4][format.getElementCount()][4];
        }

        public VertexFormat getVertexFormat() {
            return format;
        }

        public void setContractUVs(boolean value) {
            this.contractUVs = value;
        }

        public void setQuadTint(int tint) {
            this.tint = tint;
        }

        public void setQuadOrientation(Direction orientation) {
            this.orientation = orientation;
        }

        public void setTexture(Sprite texture) {
            this.texture = texture;
        }

        public void put(int element, float... data) {
            for (int i = 0; i < 4; i++) {
                if (i < data.length) {
                    unpackedData[vertices][element][i] = data[i];
                } else {
                    unpackedData[vertices][element][i] = 0;
                }
            }
            elements++;
            if (elements == format.getElementCount()) {
                vertices++;
                elements = 0;
            }
            if (vertices == 4) {
                full = true;
            }
        }

        private final float eps = 1f / 0x100;

        public UnpackedBakedQuad build() {
            if (!full) {
                throw new IllegalStateException("not enough data");
            }
            if (texture == null) {
                throw new IllegalStateException("texture not set");
            }
            if (contractUVs) {
                float tX = texture.getWidth() / (texture.getMaxU() - texture.getMinU());
                float tY = texture.getHeight() / (texture.getMaxV() - texture.getMinV());
                float tS = tX > tY ? tX : tY;
                float ep = 1f / (tS * 0x100);
                int uve = 0;
                while (uve < format.getElementCount()) {
                    VertexFormatElement e = format.getElement(uve);
                    if (e.getType() == VertexFormatElement.Type.UV && e.getIndex() == 0) {
                        break;
                    }
                    uve++;
                }
                if (uve == format.getElementCount()) {
                    throw new IllegalStateException("Can't contract UVs: format doesn't contain UVs");
                }
                float[] uvc = new float[4];
                for (int v = 0; v < 4; v++) {
                    for (int i = 0; i < 4; i++) {
                        uvc[i] += unpackedData[v][uve][i] / 4;
                    }
                }
                for (int v = 0; v < 4; v++) {
                    for (int i = 0; i < 4; i++) {
                        float uo = unpackedData[v][uve][i];
                        float un = uo * (1 - eps) + uvc[i] * eps;
                        float ud = uo - un;
                        float aud = ud;
                        if (aud < 0) aud = -aud;
                        if (aud < ep) // not moving a fraction of a pixel
                        {
                            float udc = uo - uvc[i];
                            if (udc < 0) udc = -udc;
                            if (udc < 2 * ep) // center is closer than 2 fractions of a pixel, don't move too close
                            {
                                un = (uo + uvc[i]) / 2;
                            } else // move at least by a fraction
                            {
                                un = uo + (ud < 0 ? ep : -ep);
                            }
                        }
                        unpackedData[v][uve][i] = un;
                    }
                }
            }
            return new UnpackedBakedQuad(unpackedData, tint, orientation, texture, format);
        }
    }
}