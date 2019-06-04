package abused_master.abusedlib.client.render.obj;

import abused_master.abusedlib.AbusedLib;
import de.javagl.obj.Obj;
import de.javagl.obj.ObjReader;
import de.javagl.obj.ObjUtils;

import java.io.*;
import java.util.*;

public class OBJLoader {

    public static final OBJLoader INSTANCE = new OBJLoader();
    private Set<String> objHandlers = new HashSet<>();

    public void registerObjHandler(String modid) {
        if(!objHandlers.contains(modid)) {
            objHandlers.add(modid);
        }else {
            AbusedLib.LOGGER.warn("Mod has already been registered as a OBJ Handler: " + modid);
        }
    }

    public boolean isRegisteredDomain(String modid) {
        return objHandlers.contains(modid);
    }

    /**
    public int createDisplayList(OBJBakedModel model) {
        int displayList = GlStateManager.genLists(1);
        GlStateManager.newList(displayList, GL11.GL_COMPILE);
        {
            this.render(model);
        }
        GlStateManager.endList();

        return displayList;
    }

    //For TESR calls and whatnot
    public void render(OBJBakedModel model) {
        GL11.glMaterialf(GL11.GL_FRONT, GL11.GL_SHININESS, 120);
        GlStateManager.begin(GL11.GL_TRIANGLES);
        for (Face face : model.getFaces()) {
            Vector3f[] normals = {
                    model.getNormals().get(face.getNormals()[0] - 1),
                    model.getNormals().get(face.getNormals()[1] - 1),
                    model.getNormals().get(face.getNormals()[2] - 1),
            };
            Vec2f[] texCoords = {
                    model.getTextCoords().get(face.getTextureCoords()[0] - 1),
                    model.getTextCoords().get(face.getTextureCoords()[1] - 1),
                    model.getTextCoords().get(face.getTextureCoords()[2] - 1),
            };
            Vector3f[] vertices = {
                    model.getVertices().get(face.getVertices()[0] - 1),
                    model.getVertices().get(face.getVertices()[1] - 1),
                    model.getVertices().get(face.getVertices()[2] - 1),
            };

            GlStateManager.normal3f(normals[0].x(), normals[0].y(), normals[0].z());
            GlStateManager.texCoord2f(texCoords[0].x, texCoords[0].y);
            GlStateManager.vertex3f(vertices[0].x(), vertices[0].y(), vertices[0].z());

            GlStateManager.normal3f(normals[1].x(), normals[1].y(), normals[1].z());
            GlStateManager.texCoord2f(texCoords[1].x, texCoords[1].y);
            GlStateManager.vertex3f(vertices[1].x(), vertices[1].y(), vertices[1].z());

            GlStateManager.normal3f(normals[2].x(), normals[2].y(), normals[2].z());
            GlStateManager.texCoord2f(texCoords[2].x, texCoords[2].y);
            GlStateManager.vertex3f(vertices[2].x(), vertices[2].y(), vertices[2].z());
        }
        GlStateManager.end();
    }
    */

    public OBJModel loadModel(Reader reader) {
        OBJBuilder model;
        try {
            Obj obj = ObjUtils.convertToRenderable(ObjReader.read(reader));
            model = new OBJBuilder(ObjUtils.triangulate(obj));
        } catch (IOException e) {
            AbusedLib.LOGGER.error("Could not read obj model!", e);
            return null;
        }

        return model.build();
    }
}
