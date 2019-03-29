package abused_master.abusedlib.client.shaders;

import com.mojang.blaze3d.platform.GLX;
import net.minecraft.util.Identifier;
import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

public class ShaderHelper {

    public static void init() {
        //Example Shader Loading
        //loadShaderProgram(new Identifier("MODID", "/shaders/testVS.vs"), new Identifier("MODID", "/shaders/testFS.fs"));
    }

    public static int loadShaderProgram(Identifier vshID, Identifier fshID) {
        int vertexShader = createShader(vshID, GLX.GL_VERTEX_SHADER);
        int fragmentShader = createShader(fshID, GLX.GL_FRAGMENT_SHADER);
        int program = GLX.glCreateProgram();
        GLX.glAttachShader(program, vertexShader);
        GLX.glAttachShader(program, fragmentShader);
        GLX.glLinkProgram(program);

        return program;
    }

    public static int createShader(Identifier filename, int shaderType) {
        int shader = GLX.glCreateShader(shaderType);

        if(shader == 0) {
            return 0;
        }

        try {
            ARBShaderObjects.glShaderSourceARB(shader, readFileAsString("assets/" + filename.getNamespace() + "/" + filename.getPath()));
        }catch (Exception e) {
            e.printStackTrace();
        }

        GLX.glCompileShader(shader);
        GLX.glCompileShader(shader);

        if(GL20.glGetShaderi(shader, GLX.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
            throw new RuntimeException("Error creating shader: " + getLogInfo(shader));
        }

        return shader;
    }

    public static String getLogInfo(int obj) {
        return ARBShaderObjects.glGetInfoLogARB(obj, ARBShaderObjects.glGetObjectParameteriARB(obj, ARBShaderObjects.GL_OBJECT_INFO_LOG_LENGTH_ARB));
    }

    public static String readFileAsString(String filename) throws Exception {
        System.out.println("Loading shader ["+filename+"]...");

        InputStream in = ShaderHelper.class.getResourceAsStream(filename);

        String s = "";

        if (in != null){
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"))) {
                s = reader.lines().collect(Collectors.joining("\n"));
            }
        }

        return s;
    }
}
