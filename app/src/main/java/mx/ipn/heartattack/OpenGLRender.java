package mx.ipn.heartattack;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import android.opengl.Matrix;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 * Created by vfran_000 on 2018.
 */
/*
class OpenGLRender implements GLSurfaceView.Renderer {
    Triangle mTriangle;
    private final float[] mMVPMatrix = new float[16];
    private final float[] mProjectionMatrix = new float[16];
    private final float[] mViewMatrix = new float[16];

    private float[] mRotationMatrix = new float[16];


    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        GLES20.glClearColor(1.0f,0.0f,0.0f,1.0f);
        mTriangle=new Triangle();
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0,0,width,height);
        float ratio = (float) width / height;
        Matrix.frustumM(mProjectionMatrix, 0, -ratio, ratio, -1, 1, 3, 7);

    }

    @Override
    public void onDrawFrame(GL10 gl10) {

        float[] scratch = new float[16];


        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        Matrix.setLookAtM(mViewMatrix, 0, 0, 0, -3, 0f, 0f, 0f, 0f, 1.0f, 0.0f);
        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);

        // Draw shape
        //mTriangle.draw(mMVPMatrix);


        // Create a rotation transformation for the triangle
        //long time = SystemClock.uptimeMillis() % 4000L;
        //float angle = 0.090f * ((int) time);
        //Matrix.setRotateM(mRotationMatrix, 0, angle, 0, 0, -1.0f);
        Matrix.setRotateM(mRotationMatrix, 0, mAngle, 0, 0, -1.0f);

        // Combine the rotation matrix with the projection and camera view
        // Note that the mMVPMatrix factor *must be first* in order
        // for the matrix multiplication product to be correct.
        Matrix.multiplyMM(scratch, 0, mMVPMatrix, 0, mRotationMatrix, 0);

        mTriangle.draw(scratch);
    }


    public volatile float mAngle;

    public float getAngle() {
        return mAngle;
    }

    public void setAngle(float angle) {
        mAngle = angle;
    }

    class Triangle {

        FloatBuffer vertexBuffer;
        String vertexShaderCode =
                "uniform mat4 uMVPMatrix;" +
                        "attribute vec4 vPosition;" +
                        "void main() {" +
                        "  gl_Position = uMVPMatrix * vPosition;" +
                        "}";

        int mMVPMatrixHandle;

        String fragmentShaderCode =
                "precision mediump float;" +
                        "uniform vec4 vColor;" +
                        "void main() {" +
                        "  gl_FragColor = vColor;" +
                        "}";

        int COORDS_PER_VERTEX = 3;

        float triangleCoords[] = {   // in counterclockwise order:
                0.0f,  0.622008459f, 0.0f, // top
                -0.5f, -0.311004243f, 0.0f, // bottom left
                0.5f, -0.311004243f, 0.0f  // bottom right
        };

        // Set color with red, green, blue and alpha (opacity) values
        float color[] = { 0.63671875f, 0.76953125f, 0.22265625f, 1.0f };

        int mProgram;

        int mPositionHandle;
        int mColorHandle;

        int vertexCount = triangleCoords.length / COORDS_PER_VERTEX;
        int vertexStride = COORDS_PER_VERTEX * 4; // 4 bytes per vertex

        Triangle() {
            ByteBuffer bb = ByteBuffer.allocateDirect(triangleCoords.length * 4);
            bb.order(ByteOrder.nativeOrder());
            vertexBuffer = bb.asFloatBuffer();
            vertexBuffer.put(triangleCoords);
            vertexBuffer.position(0);

            int vertexShader = OpenGLRender.loadShader(GLES20.GL_VERTEX_SHADER,
                    vertexShaderCode);
            int fragmentShader = OpenGLRender.loadShader(GLES20.GL_FRAGMENT_SHADER,
                    fragmentShaderCode);

            mProgram = GLES20.glCreateProgram();

            GLES20.glAttachShader(mProgram, vertexShader);

            GLES20.glAttachShader(mProgram, fragmentShader);

            GLES20.glLinkProgram(mProgram);
        }

        void draw(float[] mMVPMatrix) {
            GLES20.glUseProgram(mProgram);

            mPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");

            GLES20.glEnableVertexAttribArray(mPositionHandle);

            GLES20.glVertexAttribPointer(mPositionHandle, COORDS_PER_VERTEX,
                    GLES20.GL_FLOAT, false,
                    vertexStride, vertexBuffer);

            mColorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");

            GLES20.glUniform4fv(mColorHandle, 1, color, 0);

            GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount);

            GLES20.glDisableVertexAttribArray(mPositionHandle);

            mMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");

            GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mMVPMatrix, 0);

            // Draw the triangle
            GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount);

            // Disable vertex array
            GLES20.glDisableVertexAttribArray(mPositionHandle);

        }
    }

    private static int loadShader(int type, String shaderCode) {
        int shader = GLES20.glCreateShader(type);

        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);

        return shader;
    }*/
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.os.SystemClock;
import android.util.Log;

/**
 * Created by vfran_000 on 2018.
 */

public class OpenGLRender implements GLSurfaceView.Renderer{
	Context context;
	int fn = 0;
	int bb = 0;
	OpenGLView loader;
	int sProgram, vShader, fShader, posHandle, modMatHandle, viewMatHandle, projMatHandle, textureHandle, texCoordHandle, texId;
	float[] modMat, viewMat, projMat;

	float[] verts = {-0.5f, -0.5f, 0.0f, 0.5f, -0.5f, 0.0f, 0.0f, 0.5f, 0.0f};

	short[] ind = {0, 1, 2};
	FloatBuffer vertsBuff;
	ShortBuffer indicesBuff;

	public OpenGLRender(Context context){
		this.context = context;
		loader = new OpenGLView(context);
		//loader.load(R.raw.monkey);
		loader.load(R.raw.modeladocontriangulitos);
		verts = loader.vtx;
		vertsBuff = ByteBuffer.allocateDirect(verts.length*4).order(ByteOrder.nativeOrder()).asFloatBuffer();
		vertsBuff.put(verts).position(0);

		indicesBuff = ByteBuffer.allocateDirect(ind.length*2).order(ByteOrder.nativeOrder()).asShortBuffer();
		indicesBuff.put(ind).position(0);

		modMat = new float[16];
		viewMat = new float[16];
		projMat = new float[16];
	}
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		Log.v("renderer", "on surfacecreated");
		final float eyeX = 0.0f;
        final float eyeY = 0.0f;
        final float eyeZ = 3.0f;
        final float centerX = 0.0f;
        final float centerY = 0.0f;
        final float centerZ = 0.0f;
        final float upX = 0.0f;
        final float upY = 1.0f;
        final float upZ = 0.0f;

        Matrix.setLookAtM(viewMat, 0, eyeX, eyeY, eyeZ, centerX, centerY, centerZ, upX, upY, upZ);

		vShader = GLES20.glCreateShader(GLES20.GL_VERTEX_SHADER);
		GLES20.glShaderSource(vShader, vCode);
		GLES20.glCompileShader(vShader);

		fShader = GLES20.glCreateShader(GLES20.GL_FRAGMENT_SHADER);
		GLES20.glShaderSource(fShader, fCode);
		GLES20.glCompileShader(fShader);

		sProgram = GLES20.glCreateProgram();
		GLES20.glAttachShader(sProgram, vShader);
		GLES20.glAttachShader(sProgram, fShader);
		GLES20.glLinkProgram(sProgram);

		posHandle = GLES20.glGetAttribLocation(sProgram, "aPos");
		texCoordHandle = GLES20.glGetAttribLocation(sProgram, "aTexPos");

		modMatHandle = GLES20.glGetUniformLocation(sProgram, "uModMat");
		viewMatHandle = GLES20.glGetUniformLocation(sProgram, "uViewMat");
		projMatHandle = GLES20.glGetUniformLocation(sProgram, "uProjMat");
		textureHandle = GLES20.glGetUniformLocation(sProgram, "texture");
	}

	public void onSurfaceChanged(GL10 gl, int width, int height) {
		Log.v("renderer", "on surfacechanged "+width+" "+height);
		GLES20.glViewport(0, 0, width, height);
		final float ratio = (float)width/height;
		final float left = -ratio;
		final float right = ratio;
		final float bottom = -1.0f;
		final float top = 1.0f;
		final float near = 1.0f;
		final float far = 100.0f;
		Matrix.frustumM(projMat, 0, left, right, bottom, top, near, far);

		GLES20.glUseProgram(sProgram);
		GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

	}

	public void onDrawFrame(GL10 gl) {
		GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

		long time = SystemClock.uptimeMillis() % 10000L;
        float angleInDegrees = (360.0f / 10000.0f) * ((int) time);

		Matrix.setIdentityM(modMat, 0);
		Matrix.translateM(modMat, 0, 0.0f, -2.0f, -10.0f);
		Matrix.rotateM(modMat, 0, angleInDegrees, 0.0f, 1.0f, 0.0f);

		//vertsBuff.position(0);
		//GLES20.glVertexAttribPointer(posHandle, 3, GLES20.GL_FLOAT, false, 0, vertsBuff);
		loader.vertsBuffer.position(0);
		GLES20.glVertexAttribPointer(posHandle, 3, GLES20.GL_FLOAT, false, 0, loader.vertsBuffer);
        GLES20.glEnableVertexAttribArray(posHandle);

        GLES20.glUniformMatrix4fv(modMatHandle, 1, false, modMat, 0);
        GLES20.glUniformMatrix4fv(viewMatHandle, 1, false, viewMat, 0);
        GLES20.glUniformMatrix4fv(projMatHandle, 1, false, projMat, 0);

        GLES20.glUniform1i(textureHandle, 0);
        GLES20.glDrawElements(GLES20.GL_TRIANGLES, loader.numFaces*3, GLES20.GL_UNSIGNED_SHORT, loader.indicesBuffer);
        //GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, loader.numFaces*3);
	}

	private final String vCode =
			  "uniform mat4 uModMat;            \n"
			+ "uniform mat4 uViewMat;           \n"
			+ "uniform mat4 uProjMat;           \n"
			+ "attribute vec4 aPos;             \n"
			+ "attribute vec4 aCol;             \n"
			+ "attribute vec2 aTexPos;          \n"
			+ "varying vec2 vTexPos;            \n"
			+ "varying vec4 vCol;               \n"
			+ "void main(){                     \n"
			+ " vCol = aCol;                    \n"
			+ " mat4 mv = uViewMat * uModMat;   \n"
			+ " mat4 mvp = uProjMat * mv;       \n"
			//+ " gl_Position =  aPos;          \n"
			+ " vTexPos = aTexPos;              \n"
			+ " gl_Position = mvp * aPos;       \n"
			+ " }                               \n";

	private final String fCode =
			  "precision mediump float;        \n"
			+ "uniform sampler2D texture;      \n"
			+ "varying vec4 vCol;              \n"
			+ "varying vec2 vTexPos;           \n"
			+ "void main(){                    \n"
			+ " gl_FragColor = vec4(1.0, 0.2, 0.2, 1.0);       \n"
			//+ " gl_FragColor = texture2D(texture, vTexPos);  \n"
			+ " }                              \n";
}