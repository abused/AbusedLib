package abused_master.abusedlib.client.render.obj;

import abused_master.abusedlib.AbusedLib;
import com.mojang.blaze3d.platform.GlStateManager;
import joptsimple.internal.Strings;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.util.math.Vec2f;
import org.lwjgl.opengl.GL11;

import java.io.*;
import java.util.*;

public class OBJLoader {

    public static final OBJLoader INSTANCE = new OBJLoader();
    private Set<String> objHandlers = new HashSet<>();

    public static final String VERTEX = "v";
    public static final String TEXTURE_COORDINATE = "vt";
    public static final String NORMAL = "vn";
    public static final String FACE = "f";

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
    public int createDisplayList(OBJModel model) {
        int displayList = GlStateManager.genLists(1);
        GlStateManager.newList(displayList, GL11.GL_COMPILE);
        {
            this.render(model);
        }
        GlStateManager.endList();

        return displayList;
    }

    //For TESR calls and whatnot
    public void render(OBJModel model) {
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
        OBJMeshBuilder meshModel = new OBJMeshBuilder();
        Scanner scanner = new Scanner(reader);

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line != null && !line.equals("") && !line.startsWith("#")) {
                String[] split = line.split(" ");
                String[] tokens = Arrays.copyOfRange(split, 1, split.length);

                switch (split[0]) {
                    case VERTEX:
                        if(tokens.length < 3) AbusedLib.LOGGER.warn("Vertices must be a length of 3, x, y and z. Line: " + line);
                        meshModel.getVertices().add(new Vector3f(
                                Float.parseFloat(tokens[0]),
                                Float.parseFloat(tokens[1]),
                                Float.parseFloat(tokens[2])
                        ));
                        break;
                    case NORMAL:
                        if(tokens.length < 3) AbusedLib.LOGGER.warn("Normals must be a length of 3, x, y and z. Line: " + line);
                        meshModel.getNormals().add(new Vector3f(
                                Float.parseFloat(tokens[0]),
                                Float.parseFloat(tokens[1]),
                                Float.parseFloat(tokens[2])
                        ));
                        break;
                    case TEXTURE_COORDINATE:
                        if(tokens.length < 3) AbusedLib.LOGGER.warn("Texture Coordinates must be a length of 2, with a u and a v. Line: " + line);
                        meshModel.getTextCoords().add(new Vec2f(
                                Float.parseFloat(tokens[0]),
                                Float.parseFloat(tokens[1])
                        ));
                        break;
                    case FACE:
                        if(tokens.length < 3) AbusedLib.LOGGER.warn("Faces must have at least 3 vertices");
                        int[] vertexIndices = new int[tokens.length];
                        int[] textureCoordIndices = new int[tokens.length];
                        int[] normalIndices = new int[tokens.length];

                        for (int i = 0; i < tokens.length; i++) {
                            String[] data = tokens[i].split("/");

                            vertexIndices[i] = Integer.parseInt(data[0]);
                            textureCoordIndices[i] = data.length < 2 || Strings.isNullOrEmpty(data[1]) ? 0 : Integer.parseInt(data[1]);
                            normalIndices[i] = data.length < 3 || Strings.isNullOrEmpty(data[2]) ? 0 : Integer.parseInt(data[2]);
                        }

                        meshModel.getFaces().add(new Face(vertexIndices, textureCoordIndices, normalIndices));
                        break;
                    default:
                        AbusedLib.LOGGER.warn("Unknown line: " + line);
                }
            }
        }

        scanner.close();
        return meshModel.build();
    }

    //TODO
    public OBJModel loadSprite(Reader reader, OBJModel model) {
        Scanner scanner = new Scanner(reader);
        String ln;
        while ((ln = scanner.nextLine()) != null) {
            if (ln == null || ln.equals("") || ln.startsWith("#")) {

            }else if(ln.equals("map_Kd")){

            }
        }

        return model;
    }

    public OBJUnbakedModel toUnbakedModel(OBJModel model) {
        return new OBJUnbakedModel(model);
    }
}
