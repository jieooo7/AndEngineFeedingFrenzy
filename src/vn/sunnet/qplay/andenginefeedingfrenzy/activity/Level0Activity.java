package vn.sunnet.qplay.andenginefeedingfrenzy.activity;

import java.util.Random;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.IUpdateHandler;
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
import org.andengine.util.debug.Debug;
import org.andengine.util.modifier.ease.EaseSineInOut;

import vn.sunnet.qplay.andenginefeedingfrenzy.object.FishPool;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

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
		SensorEventListener {

	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Constants
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	private static final int CAMERA_WIDTH = 640;
	private static final int CAMERA_HEIGHT = 384;

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

	private BitmapTextureAtlas mCaHongBitmapTextureAtlas;
	private TiledTextureRegion mCaHongTiledTextureRegion;
	private AnimatedSprite[] mCaHong = new AnimatedSprite[10];
	private int numCaHong = 0;

	private BitmapTextureAtlas mCaMapTextureAtlas;
	private TiledTextureRegion mCaMapTiledTextureRegion;
	private static AnimatedSprite[] mCaMap = new AnimatedSprite[4];
	private int numCaMap = 0;

	private BitmapTextureAtlas mCaNguAtlas;
	private TiledTextureRegion mCaNguRegion;
	private AnimatedSprite[] mCaNgu = new AnimatedSprite[10];
	private int numCaNgu = 0;

	private BitmapTextureAtlas mCaToTextureAtlas;
	private TiledTextureRegion mCaToTextureRegion;
	private AnimatedSprite[] mCaTo = new AnimatedSprite[10];
	private int numCaTo = 0;

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

	// private FishPool mFishPool;

	// ===========================================================
	// Constructors
	// ===========================================================

	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Getter & Setter
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

		this.mCaHongBitmapTextureAtlas = new BitmapTextureAtlas(
				getTextureManager(), 512, 64,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mCaHongTiledTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(this.mCaHongBitmapTextureAtlas, this,
						"ca_nho.png", 0, 0, 6, 1);
		this.mCaHongBitmapTextureAtlas.load();

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

		this.mCaNguAtlas = new BitmapTextureAtlas(getTextureManager(), 512,
				256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mCaNguRegion = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(this.mCaNguAtlas, this, "ca_nho2.png", 0,
						0, 6, 1);
		this.mCaNguAtlas.load();

		this.mBitmapTextureAtlas = new BitmapTextureAtlas(
				this.getTextureManager(), 64, 64, TextureOptions.BILINEAR);
		this.mBoxFaceTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(this.mBitmapTextureAtlas, this,
						"face_box_tiled.png", 0, 0, 2, 1); // 64x32
		this.mBitmapTextureAtlas.load();

		this.mCaToTextureAtlas = new BitmapTextureAtlas(getTextureManager(),
				1024, 512, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mCaToTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(this.mCaToTextureAtlas, this,
						"ca_nho7.png", 0, 0, 6, 1);
		this.mCaToTextureAtlas.load();
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
		// mMainFish.registerEntityModifier(new SequenceEntityModifier(
		// new MoveModifier(5, CAMERA_WIDTH / 3, CAMERA_WIDTH / 2, 0,
		// CAMERA_HEIGHT / 2)));
		// mMainFish.
		mMainFish.animate(new long[] { 100, 100, 100 }, 3, 5, true);
		// mMainFish.animate(100);
		mMainFish.setScale(1);
		mMainScene.attachChild(mMainFish);

		// CaNho caNho = new CaNho(3, 2000, false);
		// mHandler.postDelayed(caNho.mStartCaHong, 2000);
		CaMap caMap = new CaMap(1, 2000);
		mHandler.postDelayed(caMap.mStartCaMap, 1000);
		CaNgu caNgu = new CaNgu(4, 4000);
		mHandler.postDelayed(caNgu.mStartCaNgu, 2000);

		/*
		 * Object pool
		 */

		final FishPool mFishPool = new FishPool(mEngine,
				mCaHongTiledTextureRegion, 1);
		final FishPool nFishPool = new FishPool(mEngine, mCaToTextureRegion, 0);
		TimerHandler timerHandler = new TimerHandler(5, true,
				new ITimerCallback() {
					int i = 0;

					public void onTimePassed(TimerHandler pTimerHandler) {
						// TODO Auto-generated method stub
						mFishPool.obtainPoolItem();
						// i = i + 1;
						// if (i % 5 == 0) {
						mFishPool.doSomething();
						nFishPool.obtainPoolItem();
						nFishPool.doSomething();
						// }
					}
				});
		this.mEngine.registerUpdateHandler(timerHandler);
		final Path ballPath = new Path(2).to(CAMERA_WIDTH - 30,
				CAMERA_HEIGHT + 10).to(CAMERA_WIDTH - 30 - random.nextInt(100),
				-10);
		BongBong bongBong = new BongBong(10, 2000, CAMERA_WIDTH - 30,
				CAMERA_WIDTH - 30, CAMERA_HEIGHT + 10, -10);
		mHandler.postDelayed(bongBong.mStartBall, 1000);

		CaTo caTo = new CaTo(4, 2000);
		mHandler.postDelayed(caTo.mLunchCaLon, 2000);

		this.mEngine.registerUpdateHandler(new IUpdateHandler() {

			public void reset() {

			}

			public void onUpdate(float pSecondsElapsed) {
				updateSpritePosition();
				changeFish();
				Log.e(tag, "toa do X = " + mMainFishRegion.getTextureX()
						+ " toa do Y = " + mMainFishRegion.getTextureY()
						+ " so width = " + mMainFish.getWidth()
						+ " so height = " + mMainFish.getHeight());
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
		} else {
			mMainFish.setFlippedHorizontal(true);
		}
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

	public class CaNho {

		public int soLuong;
		public long time;
		public int numCaHong = 0;
		public boolean tmp;

		public CaNho(int soLuong, long time, boolean tmp) {
			this.soLuong = soLuong;
			this.time = time;
			this.tmp = tmp;
		}

		private Runnable mStartCaHong = new Runnable() {

			public void run() {
				final int j = numCaHong++;
				int X1 = 10 + random.nextInt(100);
				int X2 = 78 + random.nextInt(100);
				int X3 = 10 + random.nextInt(100);
				int Y1 = 50 + random.nextInt(100);
				int Y2 = 20 + random.nextInt(100);
				int Y3 = 50 + random.nextInt(100);
				final Path path1 = new Path(3).to(CAMERA_WIDTH + X1, Y1)
						.to(X2, Y2).to(CAMERA_WIDTH + X3, Y3);
				final Path path2 = new Path(3).to(-10, Y1)
						.to(CAMERA_WIDTH - X2, Y2).to(-10, Y1);
				final Path path;

				if (tmp != true) {
					path = new Path(path1);
				} else {
					path = new Path(path2);
				}

				mCaHong[j] = new AnimatedSprite(X1, Y1,
						Level0Activity.this.mCaHongTiledTextureRegion,
						Level0Activity.this.getVertexBufferObjectManager());
				mCaHong[j].registerEntityModifier(new PathModifier(20, path,
						null, new IPathModifierListener() {
							public void onPathStarted(
									final PathModifier pPathModifier,
									final IEntity pEntity) {
							}

							public void onPathWaypointStarted(
									final PathModifier pPathModifier,
									final IEntity pEntity,
									final int pWaypointIndex) {
								switch (pWaypointIndex) {
								case 0:
									mCaHong[j].animate(new long[] { 200, 200,
											200 }, 0, 2, true);
									mCaHong[j].setFlippedHorizontal(false);
									break;
								case 1:
									// mCaHong[j].animate(
									// new long[] { 200, 200, 200 }, 4, 6,
									// true);
									mCaHong[j].setFlippedHorizontal(true);
									break;
								}
							}

							public void onPathWaypointFinished(
									final PathModifier pPathModifier,
									final IEntity pEntity,
									final int pWaypointIndex) {
							}

							public void onPathFinished(
									final PathModifier pPathModifier,
									final IEntity pEntity) {
							}
						}));
				Level0Activity.this.mMainScene
						.attachChild(Level0Activity.this.mCaHong[j]);
				if (numCaHong < soLuong) {
					mHandler.postDelayed(mStartCaHong, time);
				}
			}
		};
	}

	public class CaMap {
		public int soLuong;
		public long time;

		public CaMap(int soLuong, long time) {
			this.soLuong = soLuong;
			this.time = time;
		}

		private Runnable mStartCaMap = new Runnable() {

			public void run() {
				final int j = numCaMap++;
				int X1 = 10 + random.nextInt(100);
				int X2 = CAMERA_WIDTH + random.nextInt(50);
				int X3 = 10 + random.nextInt(100);
				int Y1 = CAMERA_HEIGHT - random.nextInt(50);
				int Y2 = CAMERA_HEIGHT - random.nextInt(50);
				int Y3 = 20 + random.nextInt(50);
				final Path path = new Path(5).to(-100, Y1).to(X2, Y2)
						.to(CAMERA_WIDTH + 100, Y3).to(-100, Y3).to(-100, Y1);
				mCaMap[j] = new AnimatedSprite(-10, Y1,
						Level0Activity.this.mCaMapTiledTextureRegion,
						Level0Activity.this.getVertexBufferObjectManager());
				mCaMap[j].registerEntityModifier(new LoopEntityModifier(
						new PathModifier(10, path, null,
								new IPathModifierListener() {
									public void onPathStarted(
											final PathModifier pPathModifier,
											final IEntity pEntity) {
									}

									public void onPathWaypointStarted(
											final PathModifier pPathModifier,
											final IEntity pEntity,
											final int pWaypointIndex) {
										switch (pWaypointIndex) {
										case 0:
											mCaMap[j]
													.animate(new long[] { 200,
															200, 200, 200 }, 0,
															3, true);
											mCaMap[j]
													.setFlippedHorizontal(false);
											break;

										case 1:
											mCaMap[j]
													.animate(new long[] { 200,
															200, 200, 200 }, 0,
															3, true);

											break;
										case 2:
											mCaMap[j]
													.animate(new long[] { 200,
															200, 200, 200 }, 0,
															3, true);
											mCaMap[j]
													.setFlippedHorizontal(true);
											break;
										case 3:
											mCaMap[j]
													.animate(new long[] { 200,
															200, 200, 200 }, 0,
															3, true);
											break;

										case 4:
											mCaMap[j]
													.animate(new long[] { 200,
															200, 200, 200 }, 0,
															3, true);
											break;
										}
									}

									public void onPathWaypointFinished(
											final PathModifier pPathModifier,
											final IEntity pEntity,
											final int pWaypointIndex) {
									}

									public void onPathFinished(
											final PathModifier pPathModifier,
											final IEntity pEntity) {

									}
								})));
				Level0Activity.this.mMainScene
						.attachChild(Level0Activity.this.mCaMap[j]);
				// if (numCaMap < 1) {
				// mHandler.postDelayed(mStartCaMap, 20000);
				// }
			}
		};
	}

	public class CaNgu {
		public int soLuong;
		public long time;

		public CaNgu(int soLuong, long time) {
			this.soLuong = soLuong;
			this.time = time;
		}

		private Runnable mStartCaNgu = new Runnable() {

			public void run() {
				final int j = numCaNgu++;
				int X1 = 10 + random.nextInt(100);
				int X2 = 78 + random.nextInt(100);
				int X3 = 10 + random.nextInt(100);
				int Y1 = CAMERA_HEIGHT - random.nextInt(100);
				int Y2 = CAMERA_HEIGHT - random.nextInt(100);
				int Y3 = 50 + random.nextInt(100);
				final Path path = new Path(4).to(CAMERA_WIDTH + X1, Y1)
						.to(-50, Y2).to(-50, Y3).to(CAMERA_WIDTH + X1, Y3);
				mCaNgu[j] = new AnimatedSprite(X1, Y1,
						Level0Activity.this.mCaNguRegion,
						Level0Activity.this.getVertexBufferObjectManager());
				mCaNgu[j].registerEntityModifier(new PathModifier(60, path,
						null, new IPathModifierListener() {
							public void onPathStarted(
									final PathModifier pPathModifier,
									final IEntity pEntity) {
							}

							public void onPathWaypointStarted(
									final PathModifier pPathModifier,
									final IEntity pEntity,
									final int pWaypointIndex) {
								switch (pWaypointIndex) {
								case 0:
									mCaNgu[j].animate(new long[] { 200, 200,
											200, 200 }, 0, 3, true);
									mCaNgu[j].setFlippedHorizontal(false);
									break;
								case 1:
									mCaNgu[j].animate(new long[] { 200, 200,
											200, 200 }, 0, 3, true);
									break;
								case 2:
									mCaNgu[j].animate(new long[] { 200, 200,
											200, 200 }, 0, 3, true);
									mCaNgu[j].setFlippedHorizontal(true);
									break;
								case 3:
									mCaNgu[j].animate(new long[] { 200, 200,
											200, 200 }, 0, 3, true);
									break;
								}
							}

							public void onPathWaypointFinished(
									final PathModifier pPathModifier,
									final IEntity pEntity,
									final int pWaypointIndex) {
							}

							public void onPathFinished(
									final PathModifier pPathModifier,
									final IEntity pEntity) {
							}
						}));
				Level0Activity.this.mMainScene
						.attachChild(Level0Activity.this.mCaNgu[j]);
				if (numCaNgu < soLuong) {
					mHandler.postDelayed(mStartCaNgu, time);
				}
			}
		};
	}

	class CaTo {
		private int soLuong;
		private long time;

		public CaTo(int soLuong, long time) {
			this.soLuong = soLuong;
			this.time = time;
		}

		private Runnable mLunchCaLon = new Runnable() {

			public void run() {
				// TODO Auto-generated method stub
				final int i = numCaTo++;
				final Path path = new Path(3)
						.to(-15, CAMERA_HEIGHT / 2)
						.to(CAMERA_WIDTH / 2,
								CAMERA_HEIGHT / 2 + random.nextInt(100))
						.to(-15, CAMERA_HEIGHT / 2 + random.nextInt(100));
				mCaTo[i] = new AnimatedSprite(-15, CAMERA_HEIGHT / 2,
						Level0Activity.this.mCaToTextureRegion,
						Level0Activity.this.getVertexBufferObjectManager());
				mCaTo[i].registerEntityModifier(new LoopEntityModifier(
						new PathModifier(20, path, new IPathModifierListener() {

							public void onPathWaypointStarted(
									PathModifier pPathModifier,
									IEntity pEntity, int pWaypointIndex) {
								// TODO Auto-generated method stub
								switch (pWaypointIndex) {
								case 0:
									mCaTo[i].animate(
											new long[] { 200, 200, 200 }, 0, 2,
											true);
									mCaTo[i].setFlippedHorizontal(true);
									break;
								case 1:
									mCaTo[i].animate(
											new long[] { 200, 200, 200 }, 0, 2,
											true);
									mCaTo[i].setFlippedHorizontal(false);
									break;
								default:
									break;
								}
							}

							public void onPathWaypointFinished(
									PathModifier pPathModifier,
									IEntity pEntity, int pWaypointIndex) {
								// TODO Auto-generated method stub

							}

							public void onPathStarted(
									PathModifier pPathModifier, IEntity pEntity) {
								// TODO Auto-generated method stub

							}

							public void onPathFinished(
									PathModifier pPathModifier, IEntity pEntity) {
								// TODO Auto-generated method stub

							}
						})));
				Level0Activity.this.mMainScene.attachChild(mCaTo[i]);
				if (numCaTo < 4) {
					mHandler.postDelayed(mLunchCaLon, 2000);
				}
			}
		};
	}
}
