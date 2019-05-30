package abused_master.abusedlib.client.render.obj;

import abused_master.abusedlib.AbusedLib;
import com.mojang.blaze3d.platform.GlStateManager;
import org.lwjgl.opengl.GL11;

import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;
import java.io.*;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

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
        GlStateManager.begin(GL11.GL_QUADS);
        for (Face face : model.getFaces()) {
            Vector3f[] normals = {
                    model.getNormals().get(face.getNormals()[0] - 1),
                    model.getNormals().get(face.getNormals()[1] - 1),
                    model.getNormals().get(face.getNormals()[2] - 1),
                    model.getNormals().get(face.getNormals()[3] - 1)
            };
            Vector2f[] texCoords = {
                    model.getTextCoords().get(face.getTextureCoords()[0] - 1),
                    model.getTextCoords().get(face.getTextureCoords()[1] - 1),
                    model.getTextCoords().get(face.getTextureCoords()[2] - 1),
                    model.getTextCoords().get(face.getTextureCoords()[3] - 1)
            };
            Vector3f[] vertices = {
                    model.getVertices().get(face.getVertices()[0] - 1),
                    model.getVertices().get(face.getVertices()[1] - 1),
                    model.getVertices().get(face.getVertices()[2] - 1),
                    model.getVertices().get(face.getVertices()[3] - 1)
            };

            GlStateManager.normal3f(normals[0].getX(), normals[0].getY(), normals[0].getZ());
            GlStateManager.texCoord2f(texCoords[0].getX(), texCoords[0].getY());
            GlStateManager.vertex3f(vertices[0].getX(), vertices[0].getY(), vertices[0].getZ());

            GlStateManager.normal3f(normals[1].getX(), normals[1].getY(), normals[1].getZ());
            GlStateManager.texCoord2f(texCoords[1].getX(), texCoords[1].getY());
            GlStateManager.vertex3f(vertices[1].getX(), vertices[1].getY(), vertices[1].getZ());

            GlStateManager.normal3f(normals[2].getX(), normals[2].getY(), normals[2].getZ());
            GlStateManager.texCoord2f(texCoords[2].getX(), texCoords[2].getY());
            GlStateManager.vertex3f(vertices[2].getX(), vertices[2].getY(), vertices[2].getZ());

            GlStateManager.normal3f(normals[3].getX(), normals[3].getY(), normals[3].getZ());
            GlStateManager.texCoord2f(texCoords[3].getX(), texCoords[3].getY());
            GlStateManager.vertex3f(vertices[3].getX(), vertices[3].getY(), vertices[3].getZ());
        }
        GlStateManager.end();
    }

    public OBJModel loadModel(Reader reader) {
        OBJModel model = new OBJModel();

        Scanner scanner = new Scanner(reader);
        while (scanner.hasNextLine()) {
            String ln = scanner.nextLine();
            if (ln == null || ln.equals("") || ln.startsWith("#")) {
            } else {
                String[] split = ln.split(" ");
                switch (split[0]) {
                    case "v":
                        model.getVertices().add(new Vector3f(
                                Float.parseFloat(split[1]),
                                Float.parseFloat(split[2]),
                                Float.parseFloat(split[3])
                        ));
                        break;
                    case "vn":
                        model.getNormals().add(new Vector3f(
                                Float.parseFloat(split[1]),
                                Float.parseFloat(split[2]),
                                Float.parseFloat(split[3])
                        ));
                        break;
                    case "vt":
                        model.getTextCoords().add(new Vector2f(
                                Float.parseFloat(split[1]),
                                Float.parseFloat(split[2])
                        ));
                        break;
                    case "f":
                        model.getFaces().add(new Face(
                                new int[]{
                                        Integer.parseInt(split[1].split("/")[0]),
                                        Integer.parseInt(split[2].split("/")[0]),
                                        Integer.parseInt(split[3].split("/")[0]),
                                        Integer.parseInt(split[4].split("/")[0])
                                },
                                new int[]{
                                        Integer.parseInt(split[1].split("/")[1]),
                                        Integer.parseInt(split[2].split("/")[1]),
                                        Integer.parseInt(split[3].split("/")[1]),
                                        Integer.parseInt(split[4].split("/")[1])
                                },
                                new int[]{
                                        Integer.parseInt(split[1].split("/")[2]),
                                        Integer.parseInt(split[2].split("/")[2]),
                                        Integer.parseInt(split[3].split("/")[2]),
                                        Integer.parseInt(split[4].split("/")[2])
                                }
                        ));
                        break;
                    case "s":
                        model.setSmoothShading(!ln.contains("off"));
                        break;
                    default:
                        AbusedLib.LOGGER.warn("Unknown line: " + ln);
                }
            }
        }

        scanner.close();
        return model;
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

    public ObjUnbakedModel toUnbakedModel(OBJModel model) {
        return new ObjUnbakedModel(model);
    }
}
