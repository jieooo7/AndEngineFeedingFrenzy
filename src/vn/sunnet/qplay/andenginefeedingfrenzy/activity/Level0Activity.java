package vn.sunnet.qplay.andenginefeedingfrenzy.activity;

import java.util.LinkedList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.handler.collision.CollisionHandler;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.modifier.PathModifier;
import org.andengine.entity.modifier.PathModifier.IPathModifierListener;
import org.andengine.entity.modifier.PathModifier.Path;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.AnimatedSprite.IAnimationListener;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.util.FPSLogger;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.adt.pool.PoolItem;
import org.andengine.util.debug.Debug;
import org.andengine.util.modifier.ease.EaseSineInOut;

import vn.sunnet.qplay.andenginefeedingfrenzy.constant.FishConstants;
import vn.sunnet.qplay.andenginefeedingfrenzy.control.LevelController;
import vn.sunnet.qplay.andenginefeedingfrenzy.fishpool.CaNhoPool;
import vn.sunnet.qplay.andenginefeedingfrenzy.fishpool.CaNhoSprite;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.util.Log;

/**
 * (c) D09CN2 - PTIT - Ha Noi
 * 
 * (c) Android6 - SUNNET ITC SOLUTION- QLAY
 * 
 * @author Nguyen Hoang Truong<truongnguyenptit@gmail.com>
 * @since 3:04:16 PM Jul 26, 2012 Tel: 0974 878 244
 * 
 */
public class Level0Activity extends SimpleBaseGameActivity implements
		SensorEventListener, FishConstants {

	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Constants
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

	private String tag = "Level0Activity";

	// ---------------------------------------------------------------------------------------------
	// Fields
	// ---------------------------------------------------------------------------------------------
	protected Camera mCamera;
	protected Scene mMainScene;

	private BitmapTextureAtlas mBackgroundAtlas;
	private ITextureRegion mBackgroundRegion;

	private BitmapTextureAtlas mBallAtlas;
	private ITextureRegion mBallRegion;
	private Sprite[] mBall = new Sprite[10];
	private int numBall = 0;

	public AnimatedSprite mMainFish;

	public BitmapTextureAtlas mMainFish1TextureAtlas;
	public TiledTextureRegion mMainFish1TextureRegion;

	public BitmapTextureAtlas mMainFish2TextureAtlas;
	public TiledTextureRegion mMainFish2TextureRegion;

	public BitmapTextureAtlas mMainFish3TextureAtlas;
	public TiledTextureRegion mMainFish3TextureRegion;

	public BitmapTextureAtlas mMainFish4TextureAtlas;
	public TiledTextureRegion mMainFish4TextureRegion;

	public BitmapTextureAtlas mMainFish5TextureAtlas;
	public TiledTextureRegion mMainFish5TextureRegion;

	public BitmapTextureAtlas mCaNho1TextureAtlas;
	public TiledTextureRegion mCaNho1TextureRegion;

	public BitmapTextureAtlas mCaNho2TextureAtlas;
	public TiledTextureRegion mCaNho2TextureRegion;

	public BitmapTextureAtlas mCaNho3TextureAtlas;
	public TiledTextureRegion mCaNho3TextureRegion;

	public BitmapTextureAtlas mCaNho4TextureAtlas;
	public TiledTextureRegion mCaNho4TextureRegion;

	public BitmapTextureAtlas mCaNho5TextureAtlas;
	public TiledTextureRegion mCaNho5TextureRegion;

	public BitmapTextureAtlas mCaNho6TextureAtlas;
	public TiledTextureRegion mCaNho6TextureRegion;

	public BitmapTextureAtlas mCaNho7TextureAtlas;
	public TiledTextureRegion mCaNho7TextureRegion;

	public BitmapTextureAtlas mBackground1TextureAtlas;
	public ITextureRegion mBackground1ITextureRegion;

	public BitmapTextureAtlas mBackground2TextureAtlas;
	public ITextureRegion mBackground2ITextureRegion;

	public BitmapTextureAtlas mBackground3TextureAtlas;
	public ITextureRegion mBackground3ITextureRegion;

	public Random random;
	public Handler mHandler;

	public SensorManager sensorManager;

	public float accellerometerSpeedX;
	public float accellerometerSpeedY;
	public float sX;
	public float sY; // Sprite coordinates

	public float tmp;

	private CaNhoPool caNho1Pool;
	private LinkedList<CaNhoSprite> linkCaNho1;

	private CaNhoPool caNho2Pool;
	private LinkedList<CaNhoSprite> linkCaNho2;

	private CaNhoPool caNho3Pool;
	private LinkedList<CaNhoSprite> linkCaNho3;

	private boolean flag = false;

	static Level0Activity mLevel0Activity;
	public LevelController mLevelController;

	// ==================================================
	// Constructors
	// ==================================================

	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Getter & Setters
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	public Level0Activity getInstance() {
		return mLevel0Activity;
	}

	// ----------------------------------------------------------------------------------------------
	// Methods for/from SuperClass/Interfaces
	// ----------------------------------------------------------------------------------------------
	public EngineOptions onCreateEngineOptions() {
		mHandler = new Handler();
		random = new Random();
		mLevel0Activity = this;
		mLevelController = new LevelController(mLevel0Activity);
		this.mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);

		return new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED,
				new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT),
				this.mCamera);
	}

	@Override
	protected void onCreateResources() {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		this.mBackgroundAtlas = new BitmapTextureAtlas(getTextureManager(),
				1024, 1024, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mBackgroundRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(this.mBackgroundAtlas, this, "map/map1.png",
						0, 0);
		this.mBackgroundAtlas.load();

		this.mBallAtlas = new BitmapTextureAtlas(getTextureManager(), 128, 128,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mBallRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(this.mBallAtlas, this, "bongbong.png", 0, 0);
		this.mBallAtlas.load();

		/********************** Load Background **********************/

		this.mBackground1TextureAtlas = new BitmapTextureAtlas(
				getTextureManager(), 1024, 1024,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mBackground1ITextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(this.mBackground1TextureAtlas, this,
						"map/map1.png", 0, 0);
		this.mBackground1TextureAtlas.load();

		this.mBackground2TextureAtlas = new BitmapTextureAtlas(
				getTextureManager(), 1024, 1024,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mBackground2ITextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(this.mBackground2TextureAtlas, this,
						"map/map2.png", 0, 0);
		this.mBackground2TextureAtlas.load();

		this.mBackground3TextureAtlas = new BitmapTextureAtlas(
				getTextureManager(), 1024, 1024,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mBackground3ITextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(this.mBackground3TextureAtlas, this,
						"map/map3.png", 0, 0);
		this.mBackground3TextureAtlas.load();

		/********** ************Load Main Fish **********************/

		this.mMainFish1TextureAtlas = new BitmapTextureAtlas(
				getTextureManager(), 256, 64,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mMainFish1TextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(this.mMainFish1TextureAtlas, this,
						"cachinh/c1.png", 0, 0, 6, 1);
		this.mMainFish1TextureAtlas.load();

		this.mMainFish2TextureAtlas = new BitmapTextureAtlas(
				getTextureManager(), 512, 64,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mMainFish2TextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(this.mMainFish2TextureAtlas, this,
						"cachinh/c2.png", 0, 0, 6, 1);
		this.mMainFish2TextureAtlas.load();

		this.mMainFish3TextureAtlas = new BitmapTextureAtlas(
				getTextureManager(), 512, 64,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mMainFish3TextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(this.mMainFish3TextureAtlas, this,
						"cachinh/c3.png", 0, 0, 6, 1);
		this.mMainFish3TextureAtlas.load();

		this.mMainFish4TextureAtlas = new BitmapTextureAtlas(
				getTextureManager(), 512, 64,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mMainFish4TextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(this.mMainFish4TextureAtlas, this,
						"cachinh/c4.png", 0, 0, 6, 1);
		this.mMainFish4TextureAtlas.load();

		this.mMainFish5TextureAtlas = new BitmapTextureAtlas(
				getTextureManager(), 512, 64,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mMainFish5TextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(this.mMainFish5TextureAtlas, this,
						"cachinh/c6.png", 0, 0, 6, 1);
		this.mMainFish5TextureAtlas.load();

		/************************* Load Fish *********************************/

		this.mCaNho1TextureAtlas = new BitmapTextureAtlas(getTextureManager(),
				512, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mCaNho1TextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(this.mCaNho1TextureAtlas, this,
						"caphu/cl1.png", 0, 0, 3, 1);
		this.mCaNho1TextureAtlas.load();

		this.mCaNho2TextureAtlas = new BitmapTextureAtlas(getTextureManager(),
				1024, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mCaNho2TextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(this.mCaNho2TextureAtlas, this,
						"caphu/cl2.png", 0, 0, 6, 1);
		this.mCaNho2TextureAtlas.load();

		this.mCaNho3TextureAtlas = new BitmapTextureAtlas(getTextureManager(),
				1024, 512, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mCaNho3TextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(this.mCaNho3TextureAtlas, this,
						"caphu/cl3.png", 0, 0, 6, 1);
		this.mCaNho3TextureAtlas.load();

		this.mCaNho4TextureAtlas = new BitmapTextureAtlas(getTextureManager(),
				512, 64, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mCaNho4TextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(this.mCaNho4TextureAtlas, this,
						"caphu/cl4.png", 0, 0, 6, 1);
		this.mCaNho4TextureAtlas.load();

		this.mCaNho5TextureAtlas = new BitmapTextureAtlas(getTextureManager(),
				256, 64, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mCaNho5TextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(this.mCaNho5TextureAtlas, this,
						"caphu/cl5.png", 0, 0, 4, 1);
		this.mCaNho5TextureAtlas.load();

		this.mCaNho6TextureAtlas = new BitmapTextureAtlas(getTextureManager(),
				1024, 64, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mCaNho6TextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(this.mCaNho6TextureAtlas, this,
						"caphu/cl6.png", 0, 0, 6, 1);
		this.mCaNho6TextureAtlas.load();

		this.mCaNho7TextureAtlas = new BitmapTextureAtlas(getTextureManager(),
				512, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mCaNho7TextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(this.mCaNho7TextureAtlas, this,
						"caphu/cl7.png", 0, 0, 4, 1);
		this.mCaNho7TextureAtlas.load();
	}

	@Override
	protected Scene onCreateScene() {
		this.mEngine.registerUpdateHandler(new FPSLogger());
		this.mMainScene = new Scene();

		/******** Sensor **********/

		sensorManager = (SensorManager) this
				.getSystemService(this.SENSOR_SERVICE);
		sensorManager.registerListener(this,
				sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
				sensorManager.SENSOR_DELAY_GAME);

		this.mMainScene = levelScene(2);
		//
		// final Sprite background = new Sprite(0, 0, mBackgroundRegion,
		// this.getVertexBufferObjectManager());
		// mMainScene.setBackground(new SpriteBackground(background));
		//
		// sX = (CAMERA_WIDTH - this.mMainFish1TextureRegion.getWidth()) / 2;
		// sY = (CAMERA_HEIGHT - this.mMainFish1TextureRegion.getHeight()) / 2;
		//
		// mMainFish = new AnimatedSprite(sX, sY, this.mMainFish1TextureRegion,
		// this.mEngine.getVertexBufferObjectManager());
		// mMainFish.animate(new long[] { 100, 100, 100 }, 0, 2, true);
		// // mMainFish.setScale(1);
		// mMainScene.attachChild(mMainFish);
		//
		// /*
		// * Object pool
		// */
		// linkCaNho1 = new LinkedList<CaNhoSprite>();
		// caNho1Pool = new CaNhoPool(mEngine, mCaNho1TextureRegion, 1);
		// // linkCaNho1.add(caNho1Pool.obtainPoolItem());
		// // this.mMainScene.attachChild(linkCaNho1.getLast());
		//
		// linkCaNho2 = new LinkedList<CaNhoSprite>();
		// caNho2Pool = new CaNhoPool(getEngine(), mCaNho2TextureRegion, 2);
		// // linkCaNho2.add(caNho2Pool.obtainPoolItem());
		// // this.mMainScene.attachChild(linkCaNho2.getLast());
		//
		// linkCaNho3 = new LinkedList<CaNhoSprite>();
		// caNho3Pool = new CaNhoPool(mEngine, mCaNho3TextureRegion, 3);
		// // linkCaNho3.add(caNho3Pool.obtainPoolItem());
		// // this.mMainScene.attachChild(linkCaNho3.getLast());
		// final Timer timer = new Timer();
		// final TimerTask timerTask = new TimerTask() {
		//
		// @Override
		// public void run() {
		// // TODO Auto-generated method stub
		// linkCaNho1.add(caNho1Pool.obtainPoolItem());
		// linkCaNho1.add(caNho2Pool.obtainPoolItem());
		// linkCaNho1.add(caNho3Pool.obtainPoolItem());
		// try {
		// // Level0Activity.this.mMainScene
		// // .attachChild(linkCaNho3.getLast());
		// for (int i = 0; i < linkCaNho1.size(); i++) {
		// Level0Activity.this.mMainScene.attachChild(linkCaNho1
		// .get(i));
		// }
		// // Level0Activity.this.mMainScene
		// // .attachChild(linkCaNho1.getLast());
		// // Level0Activity.this.mMainScene
		// // .attachChild(linkCaNho2.getLast());
		// } catch (IllegalStateException e) {
		//
		// }
		// }
		// };
		// TimerHandler timerHandler = new TimerHandler(1, true,
		// new ITimerCallback() {
		//
		// public void onTimePassed(TimerHandler pTimerHandler) {
		// timer.schedule(timerTask, 1000);
		//
		// }
		// });
		// this.mEngine.registerUpdateHandler(timerHandler);
		//
		// BongBong bongBong = new BongBong(10, 2000, CAMERA_WIDTH - 30,
		// CAMERA_WIDTH - 30, CAMERA_HEIGHT + 10, -10);
		// mHandler.postDelayed(bongBong.mStartBall, 1000);
		//
		// this.mEngine.registerUpdateHandler(new IUpdateHandler() {
		//
		// public void reset() {
		//
		// }
		//
		// int i;
		//
		// public void onUpdate(float pSecondsElapsed) {
		// updateSpritePosition();
		// changeFish();
		// float pX = 0;
		// if (flag == true) {
		// pX = mMainFish.getX();
		// } else if (flag == false) {
		// pX = mMainFish.getX() + mMainFish.getWidth();
		// }
		// try {
		// for (i = 0; i < linkCaNho1.size(); i++) {
		// if (linkCaNho1.get(i).getX() > CAMERA_WIDTH + 85
		// || linkCaNho1.get(i).getX() < -85) {
		// caNho1Pool.recyclePoolItem(linkCaNho1.get(i));
		// // Log.e(tag, "Remove");
		// linkCaNho1.remove(i);
		// }
		//
		// float pY = mMainFish.getY() + mMainFish.getHeight() / 2;
		// // Log.e(tag, "toa do mom ca = " + pX + " va " + pY);
		// if (linkCaNho1.get(i).contains(pX, pY)) {
		// // Log.e(tag, "Va Cham");
		// mMainFish.setFlippedHorizontal(false);
		// mMainFish.stopAnimation(0);
		// mMainFish.animate(new long[] { 200, 200, 200 }, 3,
		// 5, false, new IAnimationListener() {
		//
		// public void onAnimationStarted(
		// AnimatedSprite pAnimatedSprite,
		// int pInitialLoopCount) {
		// // TODO Auto-generated method stub
		//
		// }
		//
		// public void onAnimationLoopFinished(
		// AnimatedSprite pAnimatedSprite,
		// int pRemainingLoopCount,
		// int pInitialLoopCount) {
		// // TODO Auto-generated method stub
		//
		// }
		//
		// public void onAnimationFrameChanged(
		// AnimatedSprite pAnimatedSprite,
		// int pOldFrameIndex,
		// int pNewFrameIndex) {
		// // TODO Auto-generated method stub
		//
		// }
		//
		// public void onAnimationFinished(
		// AnimatedSprite pAnimatedSprite) {
		// // TODO Auto-generated method stub
		// mMainFish.animate(new long[] { 200,
		// 200, 200 }, 0, 2, true);
		// }
		// });
		// Level0Activity.this.mMainScene
		// .detachChild(linkCaNho1.get(i));
		// caNho1Pool.recyclePoolItem(linkCaNho1.get(i));
		// linkCaNho1.remove(i);
		// }
		//
		// }
		//
		// // for (int j = 0; j < linkCaNho2.size(); j++) {
		// // if (linkCaNho2.get(j).getX() > CAMERA_WIDTH + 85
		// // || linkCaNho2.get(j).getX() < -85) {
		// // caNho2Pool.recyclePoolItem(linkCaNho2.get(j));
		// // linkCaNho2.remove(j);
		// // }
		// // }
		// // for (int z = 0; z < linkCaNho3.size(); z++) {
		// // if (linkCaNho3.get(z).getX() > CAMERA_WIDTH + 85
		// // || linkCaNho3.get(z).getX() < -85) {
		// // caNho3Pool.recyclePoolItem(linkCaNho3.get(z));
		// // linkCaNho3.remove(z);
		// // }
		// // }
		// } catch (Exception e) {
		//
		// }
		// // Log.e(tag, "So ca nho1 = " + linkCaNho1.size());
		// // Log.e(tag, "toa do X = " + mMainFishRegion.getTextureX()
		// // + " toa do Y = " + mMainFishRegion.getTextureY()
		// // + " so width = " + mMainFish.getWidth()
		// // + " so height = " + mMainFish.getHeight());
		//
		// }
		// });
		return mMainScene;
	}

	@Override
	public void onResumeGame() {
		super.onResumeGame();

	}

	@Override
	public void onPauseGame() {
		super.onPauseGame();

	}

	public void onSensorChanged(SensorEvent event) {
		synchronized (this) {
			switch (event.sensor.getType()) {
			case Sensor.TYPE_ACCELEROMETER:
				accellerometerSpeedX = (float) event.values[1];
				accellerometerSpeedY = (float) event.values[0];
				break;
			}
		}
	}

	public void onAccuracyChanged(Sensor sensor, int accuracy) {

	}

	// ===========================================================
	// Methods
	// ===========================================================

	private float updateSpritePosition() {
		if ((accellerometerSpeedX != 0) || (accellerometerSpeedY != 0)) {
			// Set the Boundary limits
			float tL = -15;
			float lL = -15;
			float rL = CAMERA_WIDTH - (float) mMainFish.getWidth() + 15;
			float bL = CAMERA_HEIGHT - (float) mMainFish.getHeight() + 15;

			// Calculate New X,Y Coordinates within Limits
			if (sX >= lL) {
				sX += accellerometerSpeedX;
			} else
				sX = lL;
			if (sX <= rL) {
				sX += accellerometerSpeedX;
			} else
				sX = rL;
			if (sY >= tL)
				sY += accellerometerSpeedY;
			else
				sY = tL;
			if (sY <= bL)
				sY += accellerometerSpeedY;
			else
				sY = bL;

			// Double Check That New X,Y Coordinates are within Limits
			if (sX < lL)
				sX = lL;
			else if (sX > rL)
				sX = rL;
			if (sY < tL)
				sY = tL;
			else if (sY > bL)
				sY = bL;
			mMainFish.setPosition(sX, sY);
			tmp = sX;
		}
		return tmp;
	}

	public void changeFish() {
		if (accellerometerSpeedX > 1) {
			mMainFish.setFlippedHorizontal(true);
			flag = false;
		} else {
			mMainFish.setFlippedHorizontal(false);
			flag = true;
		}
	}

	public void collides(final AnimatedSprite animatedSprite, float pX, float pY) {
		if (animatedSprite.contains(pX, pY)) {
			animatedSprite.stopAnimation(0);
			animatedSprite.animate(new long[] { 200, 200, 200 }, 0, 2, false,
					new IAnimationListener() {

						public void onAnimationStarted(
								AnimatedSprite pAnimatedSprite,
								int pInitialLoopCount) {
							// TODO Auto-generated method stub

						}

						public void onAnimationLoopFinished(
								AnimatedSprite pAnimatedSprite,
								int pRemainingLoopCount, int pInitialLoopCount) {
							// TODO Auto-generated method stub

						}

						public void onAnimationFrameChanged(
								AnimatedSprite pAnimatedSprite,
								int pOldFrameIndex, int pNewFrameIndex) {
							// TODO Auto-generated method stub

						}

						public void onAnimationFinished(
								AnimatedSprite pAnimatedSprite) {
							// TODO Auto-generated method stub
							animatedSprite.animate(
									new long[] { 200, 200, 200 }, 0, 2, true);
						}
					});
		}
		this.mMainFish.detachChild(animatedSprite);
	}

	public Scene levelScene(int levelID) {
		Scene scene = new Scene();
		mLevelController.setScene(scene);
		mLevelController.loadLevel(levelID);
		return scene;
	}

	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Inner and Anonymous Classes
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	public class BongBong {
		public int soLuong;
		public long time;
		public int X1;
		public int X2;
		public int Y1;
		public int Y2;

		// public Path path;

		public BongBong(int soLuong, long time, int X1, int X2, int Y1, int Y2) {
			this.soLuong = soLuong;
			this.time = time;
			this.X1 = X1;
			this.X2 = X2;
			this.Y1 = Y1;
			this.Y2 = Y2;
			// this.path = path;
		}

		private Runnable mStartBall = new Runnable() {

			public void run() {
				int i = numBall++;
				float startX = random.nextFloat() * (CAMERA_HEIGHT + 20.0f);
				mBall[i] = new Sprite(startX, CAMERA_HEIGHT + 20.0f,
						mBallRegion,
						Level0Activity.this.getVertexBufferObjectManager());
				final Path ballPath = new Path(2).to(X1, Y1).to(
						X2 - random.nextInt(100), Y2);
				mBall[i].registerEntityModifier(new LoopEntityModifier(
						new PathModifier(5, ballPath, null,
								new IPathModifierListener() {

									public void onPathWaypointStarted(
											PathModifier arg0, IEntity arg1,
											int arg2) {
									}

									public void onPathWaypointFinished(
											PathModifier arg0, IEntity arg1,
											int arg2) {
									}

									public void onPathStarted(
											PathModifier arg0, IEntity arg1) {
									}

									public void onPathFinished(
											PathModifier arg0, IEntity arg1) {
									}
								})));
				Level0Activity.this.mMainScene.attachChild(mBall[i]);
				if (numBall < soLuong) {
					mHandler.postDelayed(mStartBall, time);
				}
			}
		};
	}
}
