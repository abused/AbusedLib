package abused_master.abusedlib.client.render.obj;

import abused_master.abusedlib.utils.VertexDataUtils;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormatElement;
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
    private Sprite sprite;
    private VertexFormat format;

    public OBJModel(List<Vector3f> vertices, List<Vec2f> textCoords, List<Vector3f> normals, List<Face> faces) {
        this.vertices = vertices;
        this.textCoords = textCoords;
        this.normals = normals;
        this.faces = faces;
        this.format = VertexFormats.POSITION_COLOR_UV_NORMAL;
        //TODO later for now just get the model rendering
        this.sprite = MinecraftClient.getInstance().getSpriteAtlas().getSprite(SpriteAtlasTexture.BLOCK_ATLAS_TEX);
    }

    public OBJModel() {
        this(new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
    }

    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState var1, @Nullable Direction var2, Random var3) {
        return buildQuads();
    }

    private List<BakedQuad> buildQuads() {
        if(getVertices().isEmpty()) {
            throw new NullPointerException("Model cannot be parsed!");
        }

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

        int x = ((byte)(normal.x() * 127)) & 0xFF;
        int y = ((byte)(normal.y() * 127)) & 0xFF;
        int z = ((byte)(normal.z() * 127)) & 0xFF;

        //TODO later, for now use textures idc
        int tintIndex = 0;
        int[] quadData = new int[format.getVertexSize() * 4];

        for (int v = 0; v < vertices.length; v++) {
            Vector3f vertex = vertices[v];
            Vec2f textureCoords = textCoords[v];

            for(int e = 0; e < format.getElementCount(); e++) {
                VertexFormatElement element = format.getElement(e);

                switch (element.getType()) {
                    case POSITION:
                        VertexDataUtils.packData(new float[] {vertex.x(), vertex.y(), vertex.z()}, quadData, format, v, e);
                        break;
                    case NORMAL:
                        VertexDataUtils.packData(new float[] {x, y, z}, quadData, format, v, e);
                        break;
                    case COLOR:
                        VertexDataUtils.packData(new float[] {1, 1, 1, 1}, quadData, format, v, e);
                        break;
                    case UV:
                        VertexDataUtils.packData(new float[] {textureCoords.x, textureCoords.y}, quadData, format, v, e);
                        break;
                }
            }
        }

        return new BakedQuad(quadData, tintIndex, Direction.getFacing(normal.x(), normal.y(), normal.z()), sprite);
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
}
