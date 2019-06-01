package abused_master.abusedlib.client.render.obj;

import net.fabricmc.fabric.api.renderer.v1.RendererAccess;
import net.fabricmc.fabric.api.renderer.v1.mesh.MeshBuilder;
import net.fabricmc.fabric.api.renderer.v1.mesh.QuadEmitter;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec2f;

import java.util.ArrayList;
import java.util.List;

public class OBJMeshBuilder {

    private MeshBuilder meshBuilder;
    private QuadEmitter quadEmitter;

    private final List<Vector3f> vertices;
    private final List<Vec2f> textCoords;
    private final List<Vector3f> normals;
    private final List<Face> faces;

    public OBJMeshBuilder(List<Vector3f> vertices, List<Vec2f> textCoords, List<Vector3f> normals, List<Face> faces) {
        meshBuilder = RendererAccess.INSTANCE.getRenderer().meshBuilder();
        quadEmitter = meshBuilder.getEmitter();

        this.vertices = vertices;
        this.textCoords = textCoords;
        this.normals = normals;
        this.faces = faces;
    }

    public OBJMeshBuilder() {
        this(new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
    }

    public OBJModel build() {
        if(getVertices().isEmpty()) {
            throw new NullPointerException("Model cannot be parsed!");
        }

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

            for (int i = 0; i < vertices.length; i++) {
                Vector3f vertex = vertices[i];
                Vec2f textCoord = texCoords[i];

                quadEmitter.pos(i, vertex);
                quadEmitter.normal(i + 1, x, y, z);
                quadEmitter.spriteColor(i + 2, 1, 1, 1, 1);
                quadEmitter.sprite(i + 3, i, textCoord.x, textCoord.y);
                quadEmitter.nominalFace(Direction.getFacing(normal.x(), normal.y(), normal.z()));
            }
        }

        quadEmitter.emit();
        return new OBJModel(meshBuilder.build());
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
