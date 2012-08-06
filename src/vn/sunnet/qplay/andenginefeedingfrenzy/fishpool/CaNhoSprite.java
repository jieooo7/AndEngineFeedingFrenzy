package vn.sunnet.qplay.andenginefeedingfrenzy.fishpool;

import java.util.ArrayList;
import java.util.Random;

import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.PathModifier;
import org.andengine.entity.modifier.PathModifier.IPathModifierListener;
import org.andengine.entity.modifier.PathModifier.Path;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.vbo.ITiledSpriteVertexBufferObject;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.math.MathUtils;

import android.util.Log;

import vn.sunnet.qplay.andenginefeedingfrenzy.constant.FishConstants;
import vn.sunnet.qplay.andenginefeedingfrenzy.control.PathFish;
import vn.sunnet.qplay.andenginefeedingfrenzy.object.FishSprite;

/**
 * (c) D09CN2 - PTIT - Ha Noi
 * 
 * (c) Android6 - SUNNET ITC SOLUTION- QLAY
 * 
 * @author Nguyen Hoang Truong<truongnguyenptit@gmail.com>
 * @since 9:48:31 AM Aug 2, 2012 Tel: 0974 878 244
 * 
 */
public class CaNhoSprite extends AnimatedSprite implements FishConstants {

	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Constants
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	private String tag = "CaNhoSprite";
	// ---------------------------------------------------------------------------------------------
	// Fields
	// ---------------------------------------------------------------------------------------------
	private PathModifier pm;
	private Random random;
	private PathFish mPathFish;
	private ArrayList<Path> listPaths;
	public float pointFinish;

	// ===========================================================
	// Constructors
	// ===========================================================
	public CaNhoSprite(float pX, float pY, float pWidth, float pHeight,
			ITiledTextureRegion pTiledTextureRegion,
			VertexBufferObjectManager vertexBufferObjectManager) {
		super(pX, pY, pWidth, pHeight, pTiledTextureRegion,
				vertexBufferObjectManager);
		// TODO Auto-generated constructor stub
		random = new Random();
		mPathFish = new PathFish();
		listPaths = mPathFish.pathCaNho1();

		int X1 = 10;
		int X2 = random.nextInt(100) + random.nextInt(100);
		int X3 = 10 + random.nextInt(100);
		int Y1 = random.nextInt(50) + random.nextInt(100);
		int Y2 = random.nextInt(20) + random.nextInt(100);
		int Y3 = 50 + random.nextInt(100);
		final Path path1 = new Path(3).to(CAMERA_WIDTH + X1, Y1).to(X2, Y2)
				.to(CAMERA_WIDTH + 20, Y3);
		final Path path2 = new Path(3).to(-60, Y1).to(X2, Y2).to(-70, Y1);
		final Path path3 = new Path(5)
				.to(-50, CAMERA_HEIGHT - 20 - random.nextInt(200))
				.to(random.nextInt(200),
						CAMERA_HEIGHT - 40 - random.nextInt(200))
				.to(random.nextInt(200) - random.nextInt(100),
						CAMERA_HEIGHT - 20 - random.nextInt(200))
				.to(random.nextInt(800), random.nextInt(640))
				.to(CAMERA_WIDTH + 50, CAMERA_HEIGHT - random.nextInt(200));
		int i = random.nextInt(listPaths.size());
		int j = random.nextInt(listPaths.size());
		getInstance().animate(new long[] { 200, 200, 200 }, 0, 2, true);
		Log.e(tag, "So path = " + listPaths.size());
//		settingPath(listPaths.get(i));
		settingPath(path1);
		// if (pX == 0) {
		// settingPath(listPaths.get(i));
		// } else if (pX == 1) {
		// settingPath(path2);
		// } else if (pX == 3) {
		// settingPath(listPaths.get(j));
		// }
		this.registerUpdateHandler(new IUpdateHandler() {

			public void reset() {
				// TODO Auto-generated method stub

			}

			public void onUpdate(float pSecondsElapsed) {
				// TODO Auto-generated method stub

				update();
			}
		});
	}

	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Getter & Setter
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	public CaNhoSprite getInstance() {
		return this;
	}

	// ----------------------------------------------------------------------------------------------
	// Methods for/from SuperClass/Interfaces
	// ----------------------------------------------------------------------------------------------

	// ===========================================================
	// Methods
	// ===========================================================
	private void settingPath(Path path) {
		pm = new PathModifier(40, path, new IPathModifierListener() {

			public void onPathWaypointStarted(PathModifier pPathModifier,
					IEntity pEntity, int pWaypointIndex) {
				// TODO Auto-generated method stub
				pointFinish = getInstance().getX();
				// switch (pWaypointIndex) {
				// case 0:
				// getInstance().animate(new long[] { 200, 200, 200 }, 0, 2,
				// true);
				// if (flag == true) {
				// getInstance().setFlippedHorizontal(false);
				// } else {
				// getInstance().setFlippedHorizontal(true);
				// }
				// break;
				// case 1:
				// if (flag == true) {
				// getInstance().setFlippedHorizontal(true);
				// } else {
				// getInstance().setFlippedHorizontal(false);
				// }
				// break;
				// case 2:
				// if (flag == true) {
				// getInstance().setFlippedHorizontal(false);
				// } else {
				// getInstance().setFlippedHorizontal(true);
				// }
				// break;
				// case 3:
				// if (flag == true) {
				// getInstance().setFlippedHorizontal(false);
				// } else {
				// getInstance().setFlippedHorizontal(true);
				// }
				// break;
				// case 4:
				// if (flag == true) {
				// getInstance().setFlippedHorizontal(false);
				// } else {
				// getInstance().setFlippedHorizontal(true);
				// }
				// break;
				// default:
				// break;
				// }
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
			}
		});
		// this.mLoop = new LoopEntityModifier(pm);
		this.registerEntityModifier(pm);

	}

	public void unRegister() {
		this.unregisterEntityModifier(this.pm);
	}

	public void update() {
		if (this.getInstance().getX() > pointFinish) {
			this.getInstance().setFlippedHorizontal(true);
		} else {
			this.getInstance().setFlippedHorizontal(false);
		}
	}
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Inner and Anonymous Classes
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

}
