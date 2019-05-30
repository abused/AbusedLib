package abused_master.abusedlib.client.render.obj;

import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.model.*;
import net.minecraft.client.render.model.json.ModelItemPropertyOverrideList;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.util.math.Direction;

import javax.annotation.Nullable;
import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;
import java.util.*;

public class OBJModel implements BakedModel {

    private final List<Vector3f> vertices;
    private final List<Vector2f> textCoords;
    private final List<Vector3f> normals;
    private final List<Face> faces;
    private boolean smoothShading;
    private VertexFormat format;
    private Sprite sprite;

    public OBJModel(List<Vector3f> vertices, List<Vector2f> textCoords, List<Vector3f> normals, List<Face> faces, boolean smoothShading) {
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
            Vector3f[] normals = {
                    getNormals().get(face.getNormals()[0] - 1),
                    getNormals().get(face.getNormals()[1] - 1),
                    getNormals().get(face.getNormals()[2] - 1),
                    getNormals().get(face.getNormals()[3] - 1)
            };
            Vector2f[] texCoords = {
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

            quads.add(createQuad(vertices, normals, texCoords));
        }

        return quads;
    }

    public BakedQuad createQuad(Vector3f[] vertices, Vector3f[] normals, Vector2f[] textCoords) {

        UnpackedBakedQuad.Builder builder = new UnpackedBakedQuad.Builder(format);
        builder.setTexture(getSprite());
        putVertex(builder, vertices[0], normals[0], textCoords[0]);
        putVertex(builder, vertices[1], normals[1], textCoords[1]);
        putVertex(builder, vertices[2], normals[2], textCoords[2]);
        putVertex(builder, vertices[3], normals[3], textCoords[3]);

        return builder.build();
    }

    public void putVertex(UnpackedBakedQuad.Builder builder, Vector3f vertex, Vector3f normal, Vector2f textCoord) {
        for (int e = 0; e < format.getElementCount(); e++) {
            switch (format.getElement(e).getType()) {
                case POSITION:
                    builder.put(e, vertex.x, vertex.y, vertex.z);
                    break;
                case COLOR:
                    builder.put(e, 1.0f, 1.0f, 1.0f);
                    break;
                case UV:
                    if(format.getElement(e).getIndex() == 0) {
                        float u = getSprite().getU(textCoord.x);
                        float v = getSprite().getV(textCoord.y);
                        builder.put(e, u, v, 0, 1);
                        break;
                    }
                case NORMAL:
                    builder.put(e, normal.x, normal.y, normal.z);
                    break;
                default:
                    builder.put(e);
                    break;
            }
        }
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

    public List<Vector2f> getTextCoords() {
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
