package vn.sunnet.qplay.andenginefeedingfrenzy.object;

import java.util.Random;

import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.PathModifier;
import org.andengine.entity.modifier.PathModifier.IPathModifierListener;
import org.andengine.entity.modifier.PathModifier.Path;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.vbo.ITiledSpriteVertexBufferObject;
import org.andengine.opengl.shader.ShaderProgram;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import vn.sunnet.qplay.andenginefeedingfrenzy.constant.FishConstants;

/**
 * (c) D09CN2 - PTIT - Ha Noi
 * 
 * (c) Android6 - SUNNET ITC SOLUTION- QLAY
 * 
 * @author Nguyen Hoang Truong<truongnguyenptit@gmail.com>
 * @since 8:55:48 AM Jul 31, 2012 Tel: 0974 878 244
 * 
 */
public class FishSprite extends AnimatedSprite implements FishConstants {

	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Constants
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

	// ---------------------------------------------------------------------------------------------
	// Fields
	// ---------------------------------------------------------------------------------------------
	private Path mPath;
	private LoopEntityModifier mLoop;
	private PathModifier pm;
	private boolean isAttachScene;
	private Random random;
	boolean tmp = false;

	// ===========================================================
	// Constructors
	// ===========================================================
	/**
	 * @param pX
	 * @param pY
	 * @param pWidth
	 * @param pHeight
	 * @param pTiledTextureRegion
	 * @param vertexBufferObjectManager
	 * @param pShaderProgram
	 */
	public FishSprite(float pX, float pY, float pWidth, float pHeight,
			ITiledTextureRegion pTiledTextureRegion,
			VertexBufferObjectManager vertexBufferObjectManager) {
		super(pX, pY, pWidth, pHeight, pTiledTextureRegion,
				vertexBufferObjectManager);
		// TODO Auto-generated constructor stub
		this.isAttachScene = false;
		random = new Random();
		int X1 = 10 + random.nextInt(100);
		int X2 = random.nextInt(100) + random.nextInt(100);
		int X3 = 10 + random.nextInt(100);
		int Y1 = random.nextInt(50) + random.nextInt(100);
		int Y2 = random.nextInt(20) + random.nextInt(100);
		int Y3 = 50 + random.nextInt(100);
		final Path path1 = new Path(3).to(CAMERA_WIDTH + X1, Y1).to(X2, Y2)
				.to(CAMERA_WIDTH - 10, Y3);
		final Path path2 = new Path(3).to(-60, Y1).to(X2, Y2).to(-70, Y1);
		final Path path3 = new Path(5)
				.to(-50, CAMERA_HEIGHT - 20 - random.nextInt(200))
				.to(random.nextInt(200),
						CAMERA_HEIGHT - 40 - random.nextInt(200))
				.to(random.nextInt(200) - random.nextInt(100),
						CAMERA_HEIGHT - 20 - random.nextInt(200))
				.to(random.nextInt(800), random.nextInt(640))
				.to(CAMERA_WIDTH + 50, CAMERA_HEIGHT - random.nextInt(200));
		if (pX == 0) {
			settingPath(path1, true);
		} else if (pX == 1) {
			settingPath(path2, false);
		} else if (pX == 3) {
			settingPath(path3, false);
		}
	}

	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Getter & Setter
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	public FishSprite getInstance() {
		return this;
	}

	public boolean getAttachScene() {
		return isAttachScene;
	}

	public void setAttachScene(boolean isAttachScene) {
		this.isAttachScene = isAttachScene;
	}

	// ----------------------------------------------------------------------------------------------
	// Methods for/from SuperClass/Interfaces
	// ----------------------------------------------------------------------------------------------

	// ===========================================================
	// Methods
	// ===========================================================
	private void settingPath(Path path, final boolean flag) {
		pm = new PathModifier(40, path, new IPathModifierListener() {

			public void onPathWaypointStarted(PathModifier pPathModifier,
					IEntity pEntity, int pWaypointIndex) {
				// TODO Auto-generated method stub
				switch (pWaypointIndex) {
				case 0:
					getInstance().animate(new long[] { 200, 200, 200 }, 0, 2,
							true);
					if (flag == true) {
						getInstance().setFlippedHorizontal(false);
					} else {
						getInstance().setFlippedHorizontal(true);
					}
					break;
				case 1:
					if (flag == true) {
						getInstance().setFlippedHorizontal(true);
					} else {
						getInstance().setFlippedHorizontal(false);
					}
					break;
				case 2:
					if (flag == true) {
						getInstance().setFlippedHorizontal(false);
					} else {
						getInstance().setFlippedHorizontal(true);
					}
					break;
				case 3:
					if (flag == true) {
						getInstance().setFlippedHorizontal(false);
					} else {
						getInstance().setFlippedHorizontal(true);
					}
					break;
				case 4:
					if (flag == true) {
						getInstance().setFlippedHorizontal(false);
					} else {
						getInstance().setFlippedHorizontal(true);
					}
					break;
				default:
					break;
				}
			}

			public void onPathWaypointFinished(PathModifier pPathModifier,
					IEntity pEntity, int pWaypointIndex) {
				// TODO Auto-generated method stub

			}

			public void onPathStarted(PathModifier pPathModifier,
					IEntity pEntity) {
				// TODO Auto-generated method stub

			}

			public void onPathFinished(PathModifier pPathModifier,
					IEntity pEntity) {
				// TODO Auto-generated method stub
//				pEntity.
				tmp = true;
			}
		});
		// this.mLoop = new LoopEntityModifier(pm);
		this.registerEntityModifier(pm);
	}

	public void unRegister() {
		this.unregisterEntityModifier(this.pm);
	}
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Inner and Anonymous Classes
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

}
