package abused_master.abusedlib.client.render.obj;

import de.javagl.obj.FloatTuple;
import de.javagl.obj.Obj;

import net.fabricmc.fabric.api.renderer.v1.RendererAccess;
import net.fabricmc.fabric.api.renderer.v1.mesh.MeshBuilder;
import net.fabricmc.fabric.api.renderer.v1.mesh.QuadEmitter;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.util.math.Direction;

public class OBJBuilder {

    private MeshBuilder meshBuilder;
    private QuadEmitter quadEmitter;

    private final Obj obj;
    private Sprite sprite;

    public OBJBuilder(Obj obj) {
        meshBuilder = RendererAccess.INSTANCE.getRenderer().meshBuilder();
        quadEmitter = meshBuilder.getEmitter();
        this.obj = obj;
        this.sprite = MinecraftClient.getInstance().getSpriteAtlas().getSprite(SpriteAtlasTexture.BLOCK_ATLAS_TEX);
    }

    public OBJModel build() {
        for (int i = 0; i < obj.getNumFaces(); i++) {

            FloatTuple vertex = null;
            FloatTuple normal = null;
            int v;
            for (v = 0; v < obj.getFace(i).getNumVertices(); v++) {
                vertex = obj.getVertex(obj.getFace(i).getVertexIndex(v));
                normal = obj.getNormal(obj.getFace(i).getNormalIndex(v));

                quadEmitter.pos(v, vertex.getX(), vertex.getY(), vertex.getZ());
                quadEmitter.normal(v + 1, normal.getX(), normal.getY(), normal.getZ());

                if(obj.getNumTexCoords() > 0) {
                    FloatTuple text = obj.getTexCoord(obj.getFace(i).getTexCoordIndex(v));
                    quadEmitter.sprite(v, 0, text.getX(), text.getY());
                    quadEmitter.spriteBake(0, sprite, quadEmitter.BAKE_ROTATE_NONE);
                    quadEmitter.spriteColor(0, 1, 1, 1, 1);
                }else {
                    quadEmitter.nominalFace(Direction.getFacing(normal.getX(), normal.getY(), normal.getZ()));
                }
                quadEmitter.colorIndex(1);
            }

            if(vertex != null && normal != null) {
                quadEmitter.pos(v + 1, vertex.getX(), vertex.getY(), vertex.getZ());
                quadEmitter.normal(v + 2, normal.getX(), normal.getY(), normal.getZ());
            }

            quadEmitter.emit();
        }

        return new OBJModel(meshBuilder.build(), sprite);
    }
}
