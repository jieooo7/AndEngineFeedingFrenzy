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
import org.andengine.util.modifier.ease.EaseQuadIn;

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
		getInstance().animate(new long[] { 100, 100, 100 }, 0, 2, true);
		if (pX == 1) {
			ArrayList<Path> listPaths = mPathFish.pathCaNho1();
			int i = random.nextInt(listPaths.size());
			settingPath(listPaths.get(i));
		} else if (pX == 2) {
			PathFish mPathFish = new PathFish();
			ArrayList<Path> listPaths2 = mPathFish.pathCaNho2();
			int i = random.nextInt(listPaths2.size());
			settingPath(listPaths2.get(i));
		} else if (pX == 3) {
			PathFish mPathFish = new PathFish();
			ArrayList<Path> listPaths3 = mPathFish.pathCaNho3();
			int i = random.nextInt(listPaths3.size());
			settingPath(listPaths3.get(i));
		} else if (pX == 4) {
			PathFish mPathFish = new PathFish();
			ArrayList<Path> listPaths4 = mPathFish.pathCaNho4();
			int i = random.nextInt(listPaths4.size());
			settingPath(listPaths4.get(i));
		}

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
				pointFinish = getInstance().getX();
			}

			public void onPathWaypointFinished(PathModifier pPathModifier,
					IEntity pEntity, int pWaypointIndex) {

			}

			public void onPathStarted(PathModifier pPathModifier,
					IEntity pEntity) {

			}

			public void onPathFinished(PathModifier pPathModifier,
					IEntity pEntity) {
			}
		}, EaseQuadIn.getInstance());
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
