package abused_master.abusedlib.client.render.obj;

import java.util.Arrays;

public class Face {

    private final int[] vertexIndices;
    private final int[] normalIndices;
    private final int[] textureCoordinateIndices;

    public boolean hasNormals() {
        return this.normalIndices != null;
    }

    public boolean hasTextureCoords() {
        return this.textureCoordinateIndices != null;
    }

    public int[] getVertices() {
        return this.vertexIndices;
    }

    public int[] getTextureCoords() {
        return this.textureCoordinateIndices;
    }

    public int[] getNormals() {
        return this.normalIndices;
    }

    public Face(int[] vertexIndices, int[] textureCoordinateIndices,
                int[] normalIndices) {
        super();

        this.vertexIndices = vertexIndices;
        this.normalIndices = normalIndices;
        this.textureCoordinateIndices = textureCoordinateIndices;
    }

    @Override
    public String toString() {
        return String.format("Face[vertexIndices%s normalIndices%s textureCoordinateIndices%s]",
                Arrays.toString(vertexIndices), Arrays.toString(normalIndices), Arrays.toString(textureCoordinateIndices));
    }
}
