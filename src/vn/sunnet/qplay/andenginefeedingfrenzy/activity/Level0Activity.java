package vn.sunnet.qplay.andenginefeedingfrenzy.activity;

import java.util.LinkedList;
import java.util.Random;

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
import org.andengine.extension.physics.box2d.FixedStepPhysicsWorld;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.extension.physics.box2d.util.Vector2Pool;
import org.andengine.input.sensor.acceleration.AccelerationData;
import org.andengine.input.sensor.acceleration.IAccelerationListener;
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
import vn.sunnet.qplay.andenginefeedingfrenzy.fishpool.CaNhoPool;
import vn.sunnet.qplay.andenginefeedingfrenzy.fishpool.CaNhoSprite;
import vn.sunnet.qplay.andenginefeedingfrenzy.object.FishPool;

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

	private BitmapTextureAtlas mMainFishAtlas;
	private TiledTextureRegion mMainFishRegion;
	private AnimatedSprite mMainFish;

	private BitmapTextureAtlas mBallAtlas;
	private ITextureRegion mBallRegion;
	private Sprite[] mBall = new Sprite[10];
	private int numBall = 0;

	private BitmapTextureAtlas mCaNho1BitmapTextureAtlas;
	private TiledTextureRegion mCaNho1TiledTextureRegion;
	private AnimatedSprite[] mCaHong = new AnimatedSprite[10];
	private int numCaHong = 0;

	private BitmapTextureAtlas mCaMapTextureAtlas;
	private TiledTextureRegion mCaMapTiledTextureRegion;
	private static AnimatedSprite[] mCaMap = new AnimatedSprite[4];
	private int numCaMap = 0;

	private BitmapTextureAtlas mCaNho2Atlas;
	private TiledTextureRegion mCaNho2Region;
	private AnimatedSprite[] mCaNgu = new AnimatedSprite[10];
	private int numCaNgu = 0;

	private BitmapTextureAtlas mCaNho3TextureAtlas;
	private TiledTextureRegion mCaNho3TextureRegion;

	private Random random;
	private Handler mHandler;

	private BitmapTextureAtlas mBitmapTextureAtlas;
	private TiledTextureRegion mBoxFaceTextureRegion;

	private SensorManager sensorManager;

	private float accellerometerSpeedX;
	private float accellerometerSpeedY;
	private float sX;
	private float sY; // Sprite coordinates

	private float tmp;

	private CaNhoPool caNho1Pool;
	private LinkedList<CaNhoSprite> linkCaNho1;

	private CaNhoPool caNho2Pool;
	private LinkedList<CaNhoSprite> linkCaNho2;

	private CaNhoPool caNho3Pool;
	private LinkedList<CaNhoSprite> linkCaNho3;

	private boolean flag = false;

	// ==================================================
	// Constructors
	// ==================================================

	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Getter & Setters
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

	// ----------------------------------------------------------------------------------------------
	// Methods for/from SuperClass/Interfaces
	// ----------------------------------------------------------------------------------------------
	public EngineOptions onCreateEngineOptions() {
		mHandler = new Handler();
		random = new Random();
		this.mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);

		return new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED,
				new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT),
				this.mCamera);
	}

	@Override
	protected void onCreateResources() {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/Level0/");
		this.mBackgroundAtlas = new BitmapTextureAtlas(getTextureManager(),
				1024, 1024, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mBackgroundRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(this.mBackgroundAtlas, this, "bg2.jpg", 0, 0);
		this.mBackgroundAtlas.load();

		this.mBallAtlas = new BitmapTextureAtlas(getTextureManager(), 128, 128,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mBallRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(this.mBallAtlas, this, "ball2.png", 0, 0);
		this.mBallAtlas.load();

		this.mCaNho1BitmapTextureAtlas = new BitmapTextureAtlas(
				getTextureManager(), 512, 256,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mCaNho1TiledTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(this.mCaNho1BitmapTextureAtlas, this,
						"ca_nho2.png", 0, 0, 6, 1);
		this.mCaNho1BitmapTextureAtlas.load();

		this.mCaMapTextureAtlas = new BitmapTextureAtlas(getTextureManager(),
				256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mCaMapTiledTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(this.mCaMapTextureAtlas, this,
						"ca-map.png", 0, 0, 1, 4);
		this.mCaMapTextureAtlas.load();

		this.mMainFishAtlas = new BitmapTextureAtlas(getTextureManager(), 1024,
				512, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mMainFishRegion = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(this.mMainFishAtlas, this,
						"mainfish5.png", 0, 0, 6, 1);
		this.mMainFishAtlas.load();

		this.mCaNho2Atlas = new BitmapTextureAtlas(getTextureManager(), 1024,
				256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mCaNho2Region = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(this.mCaNho2Atlas, this, "ca_nho3.png",
						0, 0, 6, 1);
		this.mCaNho2Atlas.load();

		this.mBitmapTextureAtlas = new BitmapTextureAtlas(
				this.getTextureManager(), 64, 64, TextureOptions.BILINEAR);
		this.mBoxFaceTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(this.mBitmapTextureAtlas, this,
						"face_box_tiled.png", 0, 0, 2, 1); // 64x32
		this.mBitmapTextureAtlas.load();

		this.mCaNho3TextureAtlas = new BitmapTextureAtlas(getTextureManager(),
				1024, 512, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mCaNho3TextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(this.mCaNho3TextureAtlas, this,
						"ca_nho4.png", 0, 0, 6, 1);
		this.mCaNho3TextureAtlas.load();
	}

	@Override
	protected Scene onCreateScene() {
		this.mEngine.registerUpdateHandler(new FPSLogger());
		this.mMainScene = new Scene();
		/*
		 * Sensor
		 */
		sensorManager = (SensorManager) this
				.getSystemService(this.SENSOR_SERVICE);
		sensorManager.registerListener(this,
				sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
				sensorManager.SENSOR_DELAY_GAME);

		final Sprite background = new Sprite(0, 0, mBackgroundRegion,
				this.getVertexBufferObjectManager());
		mMainScene.setBackground(new SpriteBackground(background));

		sX = (CAMERA_WIDTH - this.mMainFishRegion.getWidth()) / 2;
		sY = (CAMERA_HEIGHT - this.mMainFishRegion.getHeight()) / 2;

		mMainFish = new AnimatedSprite(sX, sY, this.mMainFishRegion,
				this.mEngine.getVertexBufferObjectManager());
		mMainFish.animate(new long[] { 100, 100, 100 }, 3, 5, true);
		// mMainFish.setScale(1);
		mMainScene.attachChild(mMainFish);

		/*
		 * Object pool
		 */
		linkCaNho1 = new LinkedList<CaNhoSprite>();
		caNho1Pool = new CaNhoPool(mEngine, mCaNho1TiledTextureRegion, 1);
		// linkCaNho1.add(caNho1Pool.obtainPoolItem());
		// this.mMainScene.attachChild(linkCaNho1.getLast());

		linkCaNho2 = new LinkedList<CaNhoSprite>();
		caNho2Pool = new CaNhoPool(getEngine(), mCaNho2Region, 2);
		// linkCaNho2.add(caNho2Pool.obtainPoolItem());
		// this.mMainScene.attachChild(linkCaNho2.getLast());

		linkCaNho3 = new LinkedList<CaNhoSprite>();
		caNho3Pool = new CaNhoPool(mEngine, mCaNho3TextureRegion, 3);
		// linkCaNho3.add(caNho3Pool.obtainPoolItem());
		// this.mMainScene.attachChild(linkCaNho3.getLast());

		TimerHandler timerHandler = new TimerHandler(4, true,
				new ITimerCallback() {

					public void onTimePassed(TimerHandler pTimerHandler) {
						// TODO Auto-generated method stub
						linkCaNho1.add(caNho1Pool.obtainPoolItem());
						linkCaNho2.add(caNho2Pool.obtainPoolItem());
						linkCaNho3.add(caNho3Pool.obtainPoolItem());
						try {
							Level0Activity.this.mMainScene
									.attachChild(linkCaNho3.getLast());
							Level0Activity.this.mMainScene
									.attachChild(linkCaNho1.getLast());
							Level0Activity.this.mMainScene
									.attachChild(linkCaNho2.getLast());
						} catch (IllegalStateException e) {

						}
					}
				});
		this.mEngine.registerUpdateHandler(timerHandler);

		BongBong bongBong = new BongBong(10, 2000, CAMERA_WIDTH - 30,
				CAMERA_WIDTH - 30, CAMERA_HEIGHT + 10, -10);
		mHandler.postDelayed(bongBong.mStartBall, 1000);

		this.mEngine.registerUpdateHandler(new IUpdateHandler() {

			public void reset() {

			}

			int i;

			public void onUpdate(float pSecondsElapsed) {
				updateSpritePosition();
				changeFish();
				float pX = 0;
				if (flag == true) {
					pX = mMainFish.getX();
				} else if (flag == false) {
					pX = mMainFish.getX() + mMainFish.getWidth();
				}
				try {
					for (i = 0; i < linkCaNho1.size(); i++) {
						if (linkCaNho1.get(i).getX() > CAMERA_WIDTH + 85
								|| linkCaNho1.get(i).getX() < -85) {
							caNho1Pool.recyclePoolItem(linkCaNho1.get(i));
							// Log.e(tag, "Remove");
							linkCaNho1.remove(i);
						}

						float pY = mMainFish.getY() + mMainFish.getHeight() / 2;
						// Log.e(tag, "toa do mom ca = " + pX + " va " + pY);
						if (pX == linkCaNho1.get(i).getX()
								&& pY == (linkCaNho1.get(i).getY())
										+ linkCaNho1.get(i).getHeight() / 2) {
							Level0Activity.this.mMainScene
									.detachChild(linkCaNho1.get(i));
							linkCaNho1.remove(i);
							Log.e(tag, "Va Cham");
						}
						if (linkCaNho1.get(i).contains(pX, pY)) {
							Log.e(tag, "Va Cham");
							mMainFish.setFlippedHorizontal(true);
							mMainFish.stopAnimation(0);
							mMainFish.animate(new long[] { 200, 200, 200 }, 0,
									2, false, new IAnimationListener() {

										public void onAnimationStarted(
												AnimatedSprite pAnimatedSprite,
												int pInitialLoopCount) {
											// TODO Auto-generated method stub

										}

										public void onAnimationLoopFinished(
												AnimatedSprite pAnimatedSprite,
												int pRemainingLoopCount,
												int pInitialLoopCount) {
											// TODO Auto-generated method stub

										}

										public void onAnimationFrameChanged(
												AnimatedSprite pAnimatedSprite,
												int pOldFrameIndex,
												int pNewFrameIndex) {
											// TODO Auto-generated method stub

										}

										public void onAnimationFinished(
												AnimatedSprite pAnimatedSprite) {
											// TODO Auto-generated method stub
											mMainFish.animate(new long[] { 200,
													200, 200 }, 3, 5, true);
										}
									});
							Level0Activity.this.mMainScene
									.detachChild(linkCaNho1.get(i));
							caNho1Pool.recyclePoolItem(linkCaNho1.get(i));
							linkCaNho1.remove(i);
						}

					}

					for (int j = 0; j < linkCaNho2.size(); j++) {
						if (linkCaNho2.get(j).getX() > CAMERA_WIDTH + 85
								|| linkCaNho2.get(j).getX() < -85) {
							caNho2Pool.recyclePoolItem(linkCaNho2.get(j));
							linkCaNho2.remove(j);
						}
					}
					for (int z = 0; z < linkCaNho3.size(); z++) {
						if (linkCaNho3.get(z).getX() > CAMERA_WIDTH + 85
								|| linkCaNho3.get(z).getX() < -85) {
							caNho3Pool.recyclePoolItem(linkCaNho3.get(z));
							linkCaNho3.remove(z);
						}
					}
				} catch (Exception e) {

				}
				// Log.e(tag, "So ca nho1 = " + linkCaNho1.size());
				// Log.e(tag, "toa do X = " + mMainFishRegion.getTextureX()
				// + " toa do Y = " + mMainFishRegion.getTextureY()
				// + " so width = " + mMainFish.getWidth()
				// + " so height = " + mMainFish.getHeight());

			}
		});
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
			mMainFish.setFlippedHorizontal(false);
			flag = false;
		} else {
			mMainFish.setFlippedHorizontal(true);
			flag = true;
		}
	}

	// public CollisionHandler getPoint(){
	// CollisionHandler handler=new CollisionHandler(pCollisionCallback,
	// pCheckShape, pTargetShape)
	// }

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
