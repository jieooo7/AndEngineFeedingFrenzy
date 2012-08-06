package vn.sunnet.qplay.andenginefeedingfrenzy.activity;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;

import javax.microedition.khronos.opengles.GL10;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.modifier.PathModifier;
import org.andengine.entity.modifier.ScaleAtModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.modifier.PathModifier.IPathModifierListener;
import org.andengine.entity.modifier.PathModifier.Path;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.TextMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ColorMenuItemDecorator;
import org.andengine.entity.shape.RectangularShape;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.ButtonSprite.OnClickListener;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.util.FPSLogger;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder.TextureAtlasBuilderException;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.ui.activity.BaseGameActivity;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.adt.pool.GenericPool;
import org.andengine.util.color.Color;
import org.andengine.util.debug.Debug;
import org.andengine.util.modifier.ease.EaseBackInOut;
import org.andengine.util.modifier.ease.EaseQuadIn;
import org.andengine.util.modifier.ease.EaseQuadOut;
import org.andengine.util.modifier.ease.EaseSineInOut;

import vn.sunnet.qplay.andenginefeedingfrenzy.constant.FishConstants;
import vn.sunnet.qplay.andenginefeedingfrenzy.control.PathFish;
import vn.sunnet.qplay.andenginefeedingfrenzy.fishpool.CaNhoPool;
import vn.sunnet.qplay.andenginefeedingfrenzy.fishpool.CaNhoSprite;
import vn.sunnet.qplay.andenginefeedingfrenzy.object.FishPool;

import android.content.Intent;
import android.graphics.Rect;
import android.opengl.GLES20;
import android.os.Handler;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

/**
 * (c) D09CN2 - PTIT - Ha Noi
 * 
 * (c) Android6 - SUNNET ITC SOLUTION- QLAY
 * 
 * @author Nguyen Hoang Truong<truongnguyenptit@gmail.com>
 * @since 2:05:33 PM Jul 25, 2012 Tel: 0974 878 244
 * 
 */
public class StartActivity extends SimpleBaseGameActivity implements
		IOnMenuItemClickListener, IOnSceneTouchListener, FishConstants {

	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Constants
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// private static final int CAMERA_WD = CAMERA_WIDTH;
	// private static final int CAMERA_HEIGHT = 384;

	protected final static int MENU_PLAY = 100;
	protected final static int MENU_SCORES = MENU_PLAY + 1;
	protected final static int MENU_OPTIONS = MENU_SCORES + 1;
	protected final static int MENU_ABOUT = MENU_OPTIONS + 1;
	protected final static int MENU_QUIT = MENU_ABOUT + 1;

	protected final static int LAYER_COUNT = 2;
	protected final static int LAYER_BACKGROUND = 0;
	protected final static int LAYER_PLANT = LAYER_BACKGROUND + 1;

	protected final static int BALL_VISIBLE = 1;
	protected final static int FISH_VISIBLE = BALL_VISIBLE + 1;
	private String tag = "StartActivity";
	// ---------------------------------------------------------------------------------------------
	// Fields
	// ---------------------------------------------------------------------------------------------

	private BitmapTextureAtlas mBackgroundAtlas;
	private ITextureRegion mBackgroundITextureRegion;

	protected Camera mCamera;
	protected Scene mMainScene;

	private BitmapTextureAtlas mTexture;
	private ITextureRegion mFlashTextureRegion;

	private BitmapTextureAtlas mLogoTexture;
	private ITextureRegion mLogoTextureRegion;

	private BitmapTextureAtlas mBitmapTextureAtlas;
	private TiledTextureRegion mFishTiledTextureRegion;
	private BitmapTextureAtlas mCaNho5TextureAtlas;
	private TiledTextureRegion mCaNho5TextureRegion;
	private BitmapTextureAtlas mMainFishBitmapTextureAtlas;
	private TiledTextureRegion mMainFishTiledTextureRegion;
	private BitmapTextureAtlas mCaMapBitmapTextureAtlas;
	private TiledTextureRegion mCaMapTiledTextureRegion;

	private BitmapTextureAtlas mOnScreenControl;
	private ITextureRegion mOnScreenControlBaseTextureOptions;
	private ITextureRegion mOnScreenControlKnopTextureOptions;

	protected MenuScene mStaticMenuScene, mPopupMenuScene;

	private BitmapTextureAtlas mPopUpTexture;
	private BitmapTextureAtlas mFontTexture;

	private Font mFont;
	protected ITextureRegion mPopUpAboutTextureRegion;
	protected ITextureRegion mPopUpQuitTextureRegion;
	protected ITextureRegion mMenuPlayTextureRegion;
	protected ITextureRegion mMenuScoresTextureRegion;
	protected ITextureRegion mMenuOptionsTextureRegion;
	protected ITextureRegion mMenuHelpTextureRegion;

	private BitmapTextureAtlas mBallTexture;
	private ITextureRegion mBallTextureRegion;
	private int numBall = 0;
	private Sprite[] mBall = new Sprite[10];

	private Handler mHandler;

	private Random random;

	private Sprite logo;
	private boolean chuyenLogo;

	private ArrayList<Path> listMainPath;
	private ArrayList<Path> listCaMapPath;
	private float pointXCaMap;
	private float pointX;
	private AnimatedSprite mMainFishSprite;
	private AnimatedSprite mCaMapSprite;

	private LinkedList<CaNhoSprite> linkedCaNho;
	public CaNhoPool caNhoPool;
	public PathFish mPathFish = new PathFish();
	private ArrayList<Path> listPaths;

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
		// TODO Auto-generated method stub
		mHandler = new Handler();
		random = new Random();

		this.mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);

		return new EngineOptions(true, ScreenOrientation.LANDSCAPE_SENSOR,
				new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT),
				this.mCamera);
	}

	@Override
	protected void onCreateResources() {
		// TODO Auto-generated method stub
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		this.mTexture = new BitmapTextureAtlas(this.getTextureManager(), 1024,
				1024, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mFlashTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(this.mTexture, StartActivity.this,
						"Start/bg_ac_1.jpg", 0, 0);
		this.mTexture.load();

		this.mBitmapTextureAtlas = new BitmapTextureAtlas(
				this.getTextureManager(), 1024, 512,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mFishTiledTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(this.mBitmapTextureAtlas, this,
						"Start/ca_nho3.png", 0, 0, 6, 1);
		this.mBitmapTextureAtlas.load();

		this.mLogoTexture = new BitmapTextureAtlas(this.getTextureManager(),
				512, 512, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mLogoTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(this.mLogoTexture, this, "Start/logo.jpg", 0,
						0);
		this.mLogoTexture.load();

		FontFactory.setAssetBasePath("font/");

		final ITexture fontTexture = new BitmapTextureAtlas(
				this.getTextureManager(), 256, 256, TextureOptions.BILINEAR);
		this.mFont = FontFactory.createFromAsset(this.getFontManager(),
				fontTexture, this.getAssets(), "Flubber.ttf", 32, true,
				android.graphics.Color.WHITE);
		this.mFont.load();

		this.mBallTexture = new BitmapTextureAtlas(this.getTextureManager(),
				32, 32, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mBallTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(mBallTexture, this, "Start/ball2.png", 0, 0);
		this.mBallTexture.load();

		this.mOnScreenControl = new BitmapTextureAtlas(
				this.getTextureManager(), 512, 128,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mOnScreenControlBaseTextureOptions = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(mOnScreenControl, this,
						"Control/onscreen_control_base.png", 0, 0);
		this.mOnScreenControlKnopTextureOptions = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(mOnScreenControl, this,
						"Control/onscreen_control_knob.png", 128, 0);
		this.mOnScreenControl.load();

		this.mCaNho5TextureAtlas = new BitmapTextureAtlas(getTextureManager(),
				1024, 128, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mCaNho5TextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(this.mCaNho5TextureAtlas, this,
						"Level0/ca_nho4.png", 0, 0, 6, 1);
		this.mCaNho5TextureAtlas.load();

		this.mMainFishBitmapTextureAtlas = new BitmapTextureAtlas(
				getTextureManager(), 512, 64,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mMainFishTiledTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(this.mMainFishBitmapTextureAtlas, this,
						"Level0/mainfish1_1.png", 0, 0, 6, 1);
		this.mMainFishBitmapTextureAtlas.load();

		this.mCaMapBitmapTextureAtlas = new BitmapTextureAtlas(
				getTextureManager(), 1024, 128,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mCaMapTiledTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(this.mCaMapBitmapTextureAtlas, this,
						"Level0/ca_nho7.png", 0, 0, 6, 1);
		this.mCaMapBitmapTextureAtlas.load();
	}

	@Override
	protected Scene onCreateScene() {
		// TODO Auto-generated method stub
		this.mEngine.registerUpdateHandler(new FPSLogger());

		this.mStaticMenuScene = this.createStaticMenuSence();
		mMainScene = new Scene();
		mMainScene.setChildScene(mStaticMenuScene);

		final Sprite background = new Sprite(0, 0, mFlashTextureRegion,
				this.getVertexBufferObjectManager());
		mMainScene.setBackground(new SpriteBackground(background));

		/*
		 * Get Path
		 */

		listPaths = mPathFish.pathCaNho1();
		final int path = random.nextInt(listPaths.size());

		caNhoPool = new CaNhoPool(this.mEngine, mFishTiledTextureRegion, path);
		linkedCaNho = new LinkedList<CaNhoSprite>();
		linkedCaNho.add(caNhoPool.obtainPoolItem());
		this.mMainScene.attachChild(linkedCaNho.getLast());

		final CaNhoPool mCaNho5Pool = new CaNhoPool(this.mEngine,
				mCaNho5TextureRegion, path);
		final LinkedList<CaNhoSprite> listCaNho5 = new LinkedList<CaNhoSprite>();
		listCaNho5.add(mCaNho5Pool.obtainPoolItem());
		this.mMainScene.attachChild(listCaNho5.getLast());

		TimerHandler timerHandler = new TimerHandler(4, true,
				new ITimerCallback() {

					public void onTimePassed(TimerHandler pTimerHandler) {
						linkedCaNho.add(caNhoPool.obtainPoolItem());
						try {
							StartActivity.this.mMainScene
									.attachChild(linkedCaNho.getLast());
						} catch (IllegalStateException e) {
							// TODO: handle exception
						}

						listCaNho5.add(mCaNho5Pool.obtainPoolItem());
						try {
							StartActivity.this.mMainScene
									.attachChild(listCaNho5.getLast());
						} catch (IllegalStateException e) {
							// TODO: handle exception
						}
					}
				});
		this.mMainScene.registerUpdateHandler(timerHandler);
		startMainFish();
		TimerHandler timerHandler2 = new TimerHandler(35, true,
				new ITimerCallback() {

					public void onTimePassed(TimerHandler pTimerHandler) {
						// TODO Auto-generated method stub
						startMainFish();
					}
				});
		mHandler.postDelayed(mStartBall, 1000);
		this.mMainScene.registerUpdateHandler(timerHandler2);

		startCaMap();
		TimerHandler timerHandler3 = new TimerHandler(50, true,
				new ITimerCallback() {

					public void onTimePassed(TimerHandler pTimerHandler) {
						// TODO Auto-generated method stub
						startCaMap();
					}
				});
		this.mMainScene.registerUpdateHandler(timerHandler3);
		this.mMainScene.registerUpdateHandler(new IUpdateHandler() {

			public void reset() {
				// TODO Auto-generated method stub

			}

			public void onUpdate(float arg0) {
				// TODO Auto-generated method stub
				updateMainFish();
				updateCaMap();
				for (int i = 0; i < linkedCaNho.size(); i++) {
					if (linkedCaNho.get(i).getX() > CAMERA_WIDTH + 10
							|| linkedCaNho.get(i).getX() < -10) {
						caNhoPool.recyclePoolItem(linkedCaNho.get(i));
						linkedCaNho.remove(i);
					}
					if (mMainFishSprite.collidesWith(linkedCaNho.get(i))) {
						// linkedCaNho.get(i).isVisible();
						StartActivity.this.mMainScene.detachChild(linkedCaNho
								.get(i));
						Log.e(tag, "Va cham");
					}
				}
				// Log.e(tag, "So sence = " +);
				for (int j = 0; j < listCaNho5.size(); j++) {
					if (listCaNho5.get(j).getX() > CAMERA_WIDTH + 10
							|| listCaNho5.get(j).getX() < -10) {
						mCaNho5Pool.recyclePoolItem(listCaNho5.get(j));
						listCaNho5.remove(j);
					}
				}
			}
		});

		return mMainScene;
	}

	@Override
	public void onResumeGame() {
		super.onResumeGame();
		mMainScene.registerEntityModifier(new ScaleAtModifier(0.5f, 0.0f, 1.0f,
				CAMERA_WIDTH / 2, CAMERA_HEIGHT / 2));
		mStaticMenuScene.registerEntityModifier(new ScaleAtModifier(0.5f, 0.0f,
				1.0f, CAMERA_WIDTH / 2, CAMERA_HEIGHT / 2));
	}

	public boolean onMenuItemClicked(final MenuScene pMenuScene,
			final IMenuItem pMenuItem, final float pMenuItemLocalX,
			final float pMenuItemLocalY) {
		switch (pMenuItem.getID()) {
		case MENU_ABOUT:
			Toast.makeText(StartActivity.this, "About", Toast.LENGTH_SHORT)
					.show();
			return true;
		case MENU_QUIT:
			/* End Activity. */
			this.finish();
			return true;
		case MENU_OPTIONS:
			Toast.makeText(StartActivity.this, "Options", Toast.LENGTH_SHORT)
					.show();
			return true;
		case MENU_PLAY:
			mHandler.postDelayed(mLaunchLevel1Task, 1000);
			return true;
		case MENU_SCORES:
			return true;
		default:
			return false;
		}
	}

	public boolean onSceneTouchEvent(Scene arg0, TouchEvent arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	// ===========================================================
	// Methods
	// ===========================================================
	protected MenuScene createStaticMenuSence() {
		final MenuScene menuScene = new MenuScene(this.mCamera);

		final IMenuItem playMenuItem = new ColorMenuItemDecorator(
				new TextMenuItem(MENU_PLAY, this.mFont, "Play Game",
						this.getVertexBufferObjectManager()),
				new Color(0, 0, 0), new Color(1, 0, 0));
		playMenuItem.setBlendFunction(GLES20.GL_SRC_ALPHA,
				GLES20.GL_ONE_MINUS_SRC_ALPHA);
		menuScene.addMenuItem(playMenuItem);

		final IMenuItem scoresMenuItem = new ColorMenuItemDecorator(
				new TextMenuItem(MENU_SCORES, this.mFont, "Score",
						this.getVertexBufferObjectManager()),
				new Color(0, 0, 0), new Color(1, 0, 0));
		scoresMenuItem.setBlendFunction(GLES20.GL_SRC_ALPHA,
				GLES20.GL_ONE_MINUS_SRC_ALPHA);
		menuScene.addMenuItem(scoresMenuItem);

		final IMenuItem optionsMenuItem = new ColorMenuItemDecorator(
				new TextMenuItem(MENU_OPTIONS, this.mFont, "Options",
						this.getVertexBufferObjectManager()),
				new Color(0, 0, 0), new Color(1, 0, 0));
		optionsMenuItem.setBlendFunction(GLES20.GL_SRC_ALPHA,
				GLES20.GL_ONE_MINUS_SRC_ALPHA);
		menuScene.addMenuItem(optionsMenuItem);

		final IMenuItem aboutMenuItem = new ColorMenuItemDecorator(
				new TextMenuItem(MENU_ABOUT, this.mFont, "About",
						this.getVertexBufferObjectManager()),
				new Color(0, 0, 0), new Color(1, 0, 0));
		optionsMenuItem.setBlendFunction(GLES20.GL_SRC_ALPHA,
				GLES20.GL_ONE_MINUS_SRC_ALPHA);
		menuScene.addMenuItem(aboutMenuItem);

		final IMenuItem quitMenuItem = new ColorMenuItemDecorator(
				new TextMenuItem(MENU_QUIT, this.mFont, "Quit",
						this.getVertexBufferObjectManager()),
				new Color(0, 0, 0), new Color(1, 0, 0));
		optionsMenuItem.setBlendFunction(GLES20.GL_SRC_ALPHA,
				GLES20.GL_ONE_MINUS_SRC_ALPHA);
		menuScene.addMenuItem(quitMenuItem);

		menuScene.buildAnimations();
		menuScene.setBackgroundEnabled(false);
		menuScene.setOnMenuItemClickListener(this);

		return menuScene;
	}

	private Runnable mLaunchLevel1Task = new Runnable() {

		public void run() {
			// TODO Auto-generated method stub
			Intent intent = new Intent(StartActivity.this, Level0Activity.class);
			startActivity(intent);
		}
	};

	public Runnable mStartBall = new Runnable() {

		public void run() {
			int j = numBall++;

			float startX = random.nextFloat() * (CAMERA_HEIGHT + 20.0f);
			mBall[j] = new Sprite(startX, CAMERA_HEIGHT + 20.0f,
					mBallTextureRegion,
					StartActivity.this.getVertexBufferObjectManager());
			final Path ballPath = new Path(2).to(CAMERA_WIDTH - 30,
					CAMERA_HEIGHT + 10).to(
					CAMERA_WIDTH - 30 - random.nextInt(100), -10);
			mBall[j].registerEntityModifier(new LoopEntityModifier(
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

								public void onPathStarted(PathModifier arg0,
										IEntity arg1) {
								}

								public void onPathFinished(PathModifier arg0,
										IEntity arg1) {
								}
							})));
			StartActivity.this.mMainScene.attachChild(mBall[j]);
			if (numBall < 10) {
				mHandler.postDelayed(mStartBall, 2000);
			}
		}
	};

	public void setListMainPath() {
		listMainPath = new ArrayList<PathModifier.Path>();
		Path path1 = new Path(4)
				.to(CAMERA_WIDTH + 50, CAMERA_HEIGHT - random.nextInt(100))
				.to(CAMERA_WIDTH + 20, CAMERA_HEIGHT - random.nextInt(100))
				.to(CAMERA_WIDTH / 2 + random.nextInt(100),
						CAMERA_HEIGHT / 2 + random.nextInt(100))
				.to(-60, CAMERA_HEIGHT - random.nextInt(100));
		listMainPath.add(path1);
		Path path2 = new Path(11)
				.to(CAMERA_WIDTH + 50, CAMERA_HEIGHT - random.nextInt(50))
				.to(CAMERA_WIDTH + 45, CAMERA_HEIGHT - random.nextInt(50))
				.to(CAMERA_WIDTH - 20, CAMERA_HEIGHT - 50)
				.to(CAMERA_WIDTH - 50, CAMERA_HEIGHT - 30)
				.to(CAMERA_WIDTH - 20, CAMERA_HEIGHT - 50)
				.to(20, 20)
				.to(20 + random.nextInt(50), 20 + random.nextInt(50))
				.to(CAMERA_WIDTH / 2, CAMERA_HEIGHT / 2)
				.to(CAMERA_WIDTH / 2, CAMERA_HEIGHT / 2 + random.nextInt(50))
				.to(CAMERA_WIDTH / 2 + random.nextInt(100), random.nextInt(100))
				.to(-50, CAMERA_HEIGHT - random.nextInt(50));
		listMainPath.add(path2);
		Path path3 = new Path(9)
				.to(-60, CAMERA_HEIGHT / 2)
				.to(-50, CAMERA_HEIGHT / 2)
				.to(CAMERA_WIDTH / 2, CAMERA_HEIGHT / 2 + random.nextInt(100))
				.to(CAMERA_WIDTH / 2 - random.nextInt(50),
						CAMERA_HEIGHT / 2 - random.nextInt(100))
				.to(CAMERA_WIDTH - random.nextInt(100), random.nextInt(100))
				.to(CAMERA_WIDTH - random.nextInt(100) - 20,
						random.nextInt(100))
				.to(CAMERA_WIDTH - random.nextInt(100), random.nextInt(100))
				.to(20, CAMERA_HEIGHT - random.nextInt(50))
				.to(CAMERA_WIDTH / 2, CAMERA_HEIGHT + random.nextInt(100));
		listMainPath.add(path3);
		Path path4 = new Path(13)
				.to(CAMERA_WIDTH + 60, random.nextInt(50))
				.to(CAMERA_WIDTH + 30, random.nextInt(50))
				.to(CAMERA_WIDTH / 2 - random.nextInt(100), random.nextInt(50))
				.to(CAMERA_WIDTH / 2 - random.nextInt(100) - random.nextInt(50),
						random.nextInt(100))
				.to(CAMERA_WIDTH / 2 - random.nextInt(100), random.nextInt(50))
				.to(CAMERA_WIDTH / 2 - random.nextInt(100) - random.nextInt(50),
						random.nextInt(100))
				.to(CAMERA_WIDTH / 2, CAMERA_HEIGHT / 2)
				.to(CAMERA_WIDTH / 2 - random.nextInt(100) - random.nextInt(50),
						random.nextInt(100))
				.to(CAMERA_WIDTH - random.nextInt(100),
						CAMERA_HEIGHT / 2 + random.nextInt(50))
				.to(random.nextInt(50), CAMERA_HEIGHT / 2 + random.nextInt(100))
				.to(CAMERA_WIDTH / 2, CAMERA_HEIGHT - random.nextInt(50))
				.to(random.nextInt(100) + random.nextInt(50), CAMERA_HEIGHT / 2)
				.to(-70, CAMERA_HEIGHT / 2 + random.nextInt(50));
		listMainPath.add(path4);
	}

	public void startMainFish() {
		mMainFishSprite = new AnimatedSprite(0, 0, mMainFishTiledTextureRegion,
				this.getVertexBufferObjectManager());
		setListMainPath();
		final int i = random.nextInt(listMainPath.size());
		mMainFishSprite.animate(new long[] { 200, 200, 200 }, 3, 5, true);
		mMainFishSprite.registerEntityModifier(new PathModifier(33,
				listMainPath.get(i), new IPathModifierListener() {

					public void onPathWaypointStarted(
							PathModifier pPathModifier, IEntity pEntity,
							int pWaypointIndex) {
						// TODO Auto-generated method stub

					}

					public void onPathWaypointFinished(
							PathModifier pPathModifier, IEntity pEntity,
							int pWaypointIndex) {

						pointX = mMainFishSprite.getX();
					}

					public void onPathStarted(PathModifier pPathModifier,
							IEntity pEntity) {

					}

					public void onPathFinished(PathModifier pPathModifier,
							IEntity pEntity) {
						// TODO Auto-generated method stub

					}
				}, EaseQuadOut.getInstance()));
		this.mMainScene.attachChild(mMainFishSprite);
	}

	public void updateMainFish() {
		if (mMainFishSprite.getX() < pointX) {
			mMainFishSprite.setFlippedHorizontal(true);
		} else {
			mMainFishSprite.setFlippedHorizontal(false);
		}
	}

	public void startCaMap() {
		mCaMapSprite = new AnimatedSprite(0, 0, mCaMapTiledTextureRegion,
				this.getVertexBufferObjectManager());
		setListCaMapPath();
		int i = random.nextInt(listCaMapPath.size());
		mCaMapSprite.animate(new long[] { 200, 200, 200 }, 3, 5, true);
		mCaMapSprite.registerEntityModifier(new PathModifier(33, listCaMapPath
				.get(i), new IPathModifierListener() {

			public void onPathWaypointStarted(PathModifier pPathModifier,
					IEntity pEntity, int pWaypointIndex) {
				// TODO Auto-generated method stub

			}

			public void onPathWaypointFinished(PathModifier pPathModifier,
					IEntity pEntity, int pWaypointIndex) {
				// TODO Auto-generated method stub
				pointXCaMap = mCaMapSprite.getX();
			}

			public void onPathStarted(PathModifier pPathModifier,
					IEntity pEntity) {
				// TODO Auto-generated method stub

			}

			public void onPathFinished(PathModifier pPathModifier,
					IEntity pEntity) {
				// TODO Auto-generated method stub

			}
		}, EaseQuadIn.getInstance()));
		this.mMainScene.attachChild(mCaMapSprite);
	}

	public void updateCaMap() {
		if (mCaMapSprite.getX() > pointXCaMap) {
			mCaMapSprite.setFlippedHorizontal(true);
		} else {
			mCaMapSprite.setFlippedHorizontal(false);
		}
	}

	public void setListCaMapPath() {
		listCaMapPath = new ArrayList<PathModifier.Path>();
		Path path = new Path(3).to(CAMERA_WIDTH + 70, CAMERA_HEIGHT / 2)
				.to(CAMERA_WIDTH + 50, CAMERA_HEIGHT / 2)
				.to(-60, CAMERA_HEIGHT - random.nextInt(100));
		listCaMapPath.add(path);
		Path path1 = new Path(5)
				.to(CAMERA_WIDTH + 70, CAMERA_HEIGHT / 2)
				.to(CAMERA_WIDTH + 50,
						CAMERA_HEIGHT / 2 - 20 - random.nextInt(100))
				.to(CAMERA_WIDTH - random.nextInt(100),
						random.nextInt(100) + 20)
				.to(CAMERA_WIDTH / 2 - random.nextInt(50), CAMERA_HEIGHT / 2)
				.to(-60, CAMERA_HEIGHT / 2 - random.nextInt(100));
		listCaMapPath.add(path1);
		Path path2 = new Path(3).to(-70, CAMERA_HEIGHT - random.nextInt(50))
				.to(-50, CAMERA_HEIGHT - random.nextInt(50))
				.to(CAMERA_WIDTH + 50, CAMERA_HEIGHT - random.nextInt(50));
		listCaMapPath.add(path2);
		Path path3 = new Path(3).to(-70, random.nextInt(50))
				.to(-50, random.nextInt(50))
				.to(CAMERA_WIDTH + 50, random.nextInt(50));
		listCaMapPath.add(path3);
		Path path4 = new Path(3)
				.to(CAMERA_WIDTH + 70, CAMERA_HEIGHT - random.nextInt(50))
				.to(CAMERA_WIDTH + 50, CAMERA_HEIGHT - random.nextInt(50))
				.to(-60, CAMERA_HEIGHT - random.nextInt(50));
		listCaMapPath.add(path4);
	}

	// public Rectangle getRecFish(Sprite sprite) {
	// float pX = sprite.getX();
	// float pY = sprite.getX();
	// float pWidth = sprite.getWidth();
	// float pHeight = sprite.getHeight();
	// Rectangle rectangle = new Rectangle(pX, pY, pWidth, pHeight,
	// this.getVertexBufferObjectManager());
	// return rectangle;
	// }
	public Rect getRect(AnimatedSprite sprite) {
		int left = 0;
		int top = 0;
		int right = 0;
		int bottom = 0;
		Rect rect = new Rect(left, top, right, bottom);
		
		return rect;
	}
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Inner and Anonymous Classes
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

}
