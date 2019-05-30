package abused_master.abusedlib.client.render.obj;

import com.google.common.primitives.Ints;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.model.*;
import net.minecraft.client.render.model.json.ModelItemPropertyOverrideList;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec2f;

import javax.annotation.Nullable;
import java.util.*;

public class OBJModel implements BakedModel {

    private final List<Vector3f> vertices;
    private final List<Vec2f> textCoords;
    private final List<Vector3f> normals;
    private final List<Face> faces;
    private boolean smoothShading;
    private VertexFormat format;
    private Sprite sprite;

    public OBJModel(List<Vector3f> vertices, List<Vec2f> textCoords, List<Vector3f> normals, List<Face> faces, boolean smoothShading) {
        this.vertices = vertices;
        this.textCoords = textCoords;
        this.normals = normals;
        this.faces = faces;
        this.smoothShading = smoothShading;
        this.format = VertexFormats.POSITION_UV_COLOR_NORMAL;
        this.sprite = MinecraftClient.getInstance().getSpriteAtlas().getSprite(SpriteAtlasTexture.BLOCK_ATLAS_TEX);
    }

    public OBJModel() {
        this(new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), true);
    }

    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState var1, @Nullable Direction var2, Random var3) {
        return buildQuads();
    }

    private List<BakedQuad> buildQuads() {
        List<BakedQuad> quads = new ArrayList<>();
        for (Face face : faces) {
            Vec2f[] texCoords = {
                    getTextCoords().get(face.getTextureCoords()[0] - 1),
                    getTextCoords().get(face.getTextureCoords()[1] - 1),
                    getTextCoords().get(face.getTextureCoords()[2] - 1),
                    getTextCoords().get(face.getTextureCoords()[3] - 1)
            };
            Vector3f[] vertices = {
                    getVertices().get(face.getVertices()[0] - 1),
                    getVertices().get(face.getVertices()[1] - 1),
                    getVertices().get(face.getVertices()[2] - 1),
                    getVertices().get(face.getVertices()[3] - 1)
            };

            quads.add(createQuad(vertices, texCoords));
        }

        return quads;
    }

    public BakedQuad createQuad(Vector3f[] vertices, Vec2f[] textCoords) {
        Vector3f normal = vertices[2];
        normal.subtract(vertices[0]);
        Vector3f cross = vertices[3];
        cross.subtract(vertices[1]);
        normal.cross(cross);

        float scale = MathHelper.sqrt(normal.x() * normal.x() + normal.y() * normal.y() + normal.z() * normal.z());
        if(scale < 1.0E-4f) normal.set(0, 0, 0);
        normal.scale(1 / scale);

        //TODO later, for now use textures idc
        int tintIndex = 0;

        return new BakedQuad(Ints.concat(
                vertexToInts(vertices[0], normal, textCoords[0]),
                vertexToInts(vertices[1], normal, textCoords[1]),
                vertexToInts(vertices[2], normal, textCoords[2]),
                vertexToInts(vertices[3], normal, textCoords[3])),
                tintIndex, Direction.getFacing(normal.x(), normal.y(), normal.z()), sprite);
    }

    public int[] vertexToInts(Vector3f vertex, Vector3f normal, Vec2f textCoord) {
        int x = ((byte)(normal.x() * 127)) & 0xFF;
        int y = ((byte)(normal.y() * 127)) & 0xFF;
        int z = ((byte)(normal.z() * 127)) & 0xFF;
        int compressedNormal = x | (y << 0x08) | (z << 0x10);

        return new int[]{
                Float.floatToRawIntBits(vertex.x()),
                Float.floatToRawIntBits(vertex.y()),
                Float.floatToRawIntBits(vertex.z()),
                1,
                Float.floatToRawIntBits(textCoord.x),
                Float.floatToRawIntBits(textCoord.y),
                compressedNormal
        };
    }

    @Override
    public boolean useAmbientOcclusion() {
        return true;
    }

    @Override
    public boolean hasDepthInGui() {
        return true;
    }

    @Override
    public boolean isBuiltin() {
        return false;
    }

    @Override
    public Sprite getSprite() {
        return sprite;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    @Override
    public ModelTransformation getTransformation() {
        return ModelTransformation.NONE;
    }

    @Override
    public ModelItemPropertyOverrideList getItemPropertyOverrides() {
        return ModelItemPropertyOverrideList.EMPTY;
    }

    public List<Vector3f> getVertices() {
        return vertices;
    }

    public List<Vec2f> getTextCoords() {
        return textCoords;
    }

    public List<Vector3f> getNormals() {
        return normals;
    }

    public List<Face> getFaces() {
        return faces;
    }

    public VertexFormat getFormat() {
        return format;
    }

    public void setFormat(VertexFormat format) {
        this.format = format;
    }

    public boolean isSmoothShadingEnabled() {
        return smoothShading;
    }

    public void setSmoothShading(boolean smoothShading) {
        this.smoothShading = smoothShading;
    }
}
