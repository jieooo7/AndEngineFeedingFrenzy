package vn.sunnet.qplay.andenginefeedingfrenzy.activity;

import java.util.ArrayList;
import java.util.LinkedList;
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
import org.andengine.entity.modifier.PathModifier;
import org.andengine.entity.modifier.ScaleAtModifier;
import org.andengine.entity.modifier.PathModifier.IPathModifierListener;
import org.andengine.entity.modifier.PathModifier.Path;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.TextMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ColorMenuItemDecorator;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.AnimatedSprite.IAnimationListener;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.util.FPSLogger;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.color.Color;
import org.andengine.util.modifier.ease.EaseQuadIn;
import org.andengine.util.modifier.ease.EaseQuadOut;

import vn.sunnet.qplay.andenginefeedingfrenzy.constant.FishConstants;
import vn.sunnet.qplay.andenginefeedingfrenzy.control.CollidesWith;
import vn.sunnet.qplay.andenginefeedingfrenzy.control.PathFish;
import vn.sunnet.qplay.andenginefeedingfrenzy.fishpool.CaNhoPool;
import vn.sunnet.qplay.andenginefeedingfrenzy.fishpool.CaNhoSprite;
import vn.sunnet.qplay.andenginefeedingfrenzy.fishpool.Fish;
import vn.sunnet.qplay.andenginefeedingfrenzy.fishpool.FishPool;

import android.content.Intent;
import android.opengl.GLES20;
import android.os.Handler;
import android.util.Log;
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

	protected Camera mCamera;
	protected Scene mMainScene;

	private BitmapTextureAtlas mTexture;
	private ITextureRegion mFlashTextureRegion;

	private BitmapTextureAtlas mLogoTexture;
	private ITextureRegion mLogoTextureRegion;

	private BitmapTextureAtlas mButtonPlayAtlas;
	private ITextureRegion mButtonPlayITextureRegion;

	private BitmapTextureAtlas mButtonCuaHangAtlas;
	private ITextureRegion mButtonCuaHangITextureRegion;

	private BitmapTextureAtlas mButtonKyLucAtlas;
	private ITextureRegion mButtonKyLucITextureRegion;

	private BitmapTextureAtlas mCaNho1TextureAtlas;
	private TiledTextureRegion mCaNho1TextureRegion;
	private BitmapTextureAtlas mCaNho2TextureAtlas;
	private TiledTextureRegion mCaNho2TextureRegion;
	private BitmapTextureAtlas mCaNho6TextureAtlas;
	private TiledTextureRegion mCaNho6TextureRegion;
	private BitmapTextureAtlas mMainFishBitmapTextureAtlas;
	private TiledTextureRegion mMainFishTiledTextureRegion;

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

	private ArrayList<Path> listMainPath;
	private float pointX;
	private AnimatedSprite mMainFishSprite;

	private LinkedList<CaNhoSprite> linkedCaNho;
	public CaNhoPool caNhoPool;
	public PathFish mPathFish = new PathFish();
	private ArrayList<Path> listPaths;
	private CollidesWith collidesWith;

	private FishPool mFishPool;
	private LinkedList<Fish> mListFish;
	private AnimatedSprite mFish1;
	private AnimatedSprite mFish2;
	private AnimatedSprite mFish6;
	public Sprite buttonPlay;

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
				512, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mFlashTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(this.mTexture, StartActivity.this,
						"map/bg setting menu.png", 0, 0);
		this.mTexture.load();

		this.mCaNho1TextureAtlas = new BitmapTextureAtlas(
				this.getTextureManager(), 256, 32,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mCaNho1TextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(this.mCaNho1TextureAtlas, this,
						"caphu/cl1.png", 0, 0, 3, 1);
		this.mCaNho1TextureAtlas.load();

		this.mCaNho2TextureAtlas = new BitmapTextureAtlas(getTextureManager(),
				512, 64, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mCaNho2TextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(this.mCaNho2TextureAtlas, this,
						"caphu/cl2.png", 0, 0, 6, 1);
		this.mCaNho2TextureAtlas.load();

		this.mCaNho6TextureAtlas = new BitmapTextureAtlas(getTextureManager(),
				1024, 64, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mCaNho6TextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(this.mCaNho6TextureAtlas, this,
						"caphu/cl6.png", 0, 0, 6, 1);
		this.mCaNho6TextureAtlas.load();

		FontFactory.setAssetBasePath("font/");

		final ITexture fontTexture = new BitmapTextureAtlas(
				this.getTextureManager(), 256, 256, TextureOptions.BILINEAR);
		this.mFont = FontFactory.createFromAsset(this.getFontManager(),
				fontTexture, this.getAssets(), "Flubber.ttf", 32, true,
				android.graphics.Color.WHITE);
		this.mFont.load();

		this.mBallTexture = new BitmapTextureAtlas(this.getTextureManager(),
				64, 64, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mBallTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(mBallTexture, this, "bongbong.png", 0, 0);
		this.mBallTexture.load();

		this.mMainFishBitmapTextureAtlas = new BitmapTextureAtlas(
				getTextureManager(), 512, 64,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mMainFishTiledTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(this.mMainFishBitmapTextureAtlas, this,
						"cachinh/c3.png", 0, 0, 6, 1);
		this.mMainFishBitmapTextureAtlas.load();

		this.mButtonPlayAtlas = new BitmapTextureAtlas(getTextureManager(),
				256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mButtonPlayITextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(this.mButtonPlayAtlas, this,
						"button/ic17.png", 0, 0);
		this.mButtonPlayAtlas.load();

		this.mLogoTexture = new BitmapTextureAtlas(getTextureManager(), 1024,
				256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mLogoTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(this.mLogoTexture, this, "name.png", 0, 0);
		this.mLogoTexture.load();

		this.mButtonCuaHangAtlas = new BitmapTextureAtlas(getTextureManager(),
				256, 128, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mButtonCuaHangITextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(this.mButtonCuaHangAtlas, this,
						"button/ic18b.png", 0, 0);
		this.mButtonCuaHangAtlas.load();

		this.mButtonKyLucAtlas = new BitmapTextureAtlas(getTextureManager(),
				256, 128, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mButtonKyLucITextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(this.mButtonKyLucAtlas, this,
						"button/ic19b.png", 0, 0);
		this.mButtonKyLucAtlas.load();
	}

	@Override
	protected Scene onCreateScene() {
		// TODO Auto-generated method stub
		this.mEngine.registerUpdateHandler(new FPSLogger());

		// this.mStaticMenuScene = this.createStaticMenuSence();
		mMainScene = new Scene();
		// mMainScene.setChildScene(mStaticMenuScene);

		final Sprite background = new Sprite(0, 0, mFlashTextureRegion,
				this.getVertexBufferObjectManager());
		mMainScene.setBackground(new SpriteBackground(background));

		/*
		 * Button
		 */
		float centerX = (CAMERA_WIDTH - mButtonPlayITextureRegion.getWidth()) / 2;
		buttonPlay = new Sprite(centerX, 20, mButtonPlayITextureRegion,
				this.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(final TouchEvent pAreaTouchEvent,
					final float pLocalx, final float pLocalY) {
				switch (pAreaTouchEvent.getAction()) {
				case TouchEvent.ACTION_DOWN:
					mHandler.postDelayed(mLaunchLevel1Task, 1000);
					break;

				default:
					break;
				}
				return true;
			}
		};
		Path path = new Path(3).to(centerX, 20).to(centerX, 35).to(centerX, 20);
		buttonPlay.registerEntityModifier(new LoopEntityModifier(
				new PathModifier(15, path)));
		this.mMainScene.attachChild(buttonPlay);
		this.mMainScene.registerTouchArea(buttonPlay);

		float centerY = (CAMERA_HEIGHT - this.mLogoTextureRegion.getHeight()) / 2;
		Path pathLogo = new Path(3).to(10, centerY).to(10, centerY - 10)
				.to(10, centerY);
		final Sprite logo = new Sprite(10, centerY, this.mLogoTextureRegion,
				this.getVertexBufferObjectManager());
		logo.registerEntityModifier(new LoopEntityModifier(new PathModifier(15,
				pathLogo)));
		this.mMainScene.attachChild(logo);
		// this.mMainScene.getLastChild().attachChild(logo);

		final Sprite kyLuc = new Sprite(CAMERA_WIDTH - 20
				- this.mButtonKyLucITextureRegion.getWidth(), CAMERA_HEIGHT
				- 20 - this.mButtonKyLucITextureRegion.getHeight(),
				this.mButtonKyLucITextureRegion, getVertexBufferObjectManager());
		this.mMainScene.attachChild(kyLuc);

		final Sprite cuaHang = new Sprite(CAMERA_WIDTH - 10
				- this.mButtonCuaHangAtlas.getWidth() - 10
				- this.mButtonCuaHangITextureRegion.getWidth(), CAMERA_HEIGHT
				- 20 - this.mButtonCuaHangITextureRegion.getHeight(),
				this.mButtonCuaHangITextureRegion,
				this.getVertexBufferObjectManager());
		this.mMainScene.attachChild(cuaHang);

		/*
		 * Get Path
		 */
		collidesWith = new CollidesWith(caNhoPool, mMainScene, linkedCaNho);
		caNhoPool = new CaNhoPool(this.mEngine, mCaNho1TextureRegion, 1);
		linkedCaNho = new LinkedList<CaNhoSprite>();

		final CaNhoPool mCaNho5Pool = new CaNhoPool(this.mEngine,
				mCaNho2TextureRegion, 2);
		final LinkedList<CaNhoSprite> listCaNho5 = new LinkedList<CaNhoSprite>();

		final AnimatedSprite animatedSprite = new AnimatedSprite(0, 0,
				mCaNho6TextureRegion, this.getVertexBufferObjectManager());
		final FishPool mFishPool6 = new FishPool(animatedSprite, path, 40, 6,
				EaseQuadIn.getInstance());
		final LinkedList<AnimatedSprite> listFish6 = new LinkedList<AnimatedSprite>();

		/*
		 * Time Update
		 */
		TimerHandler timerHandler = new TimerHandler(4, true,
				new ITimerCallback() {

					public void onTimePassed(TimerHandler pTimerHandler) {
						linkedCaNho.add(caNhoPool.obtainPoolItem());
						listFish6.add(mFishPool6.obtainPoolItem()
								.getAnimatedSprite());
						try {
							StartActivity.this.mMainScene
									.attachChild(linkedCaNho.getLast());
							mMainScene.attachChild(listFish6.getLast());
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

		/*
		 * Collides
		 */

		this.mMainScene.registerUpdateHandler(new IUpdateHandler() {

			public void reset() {
				// TODO Auto-generated method stub

			}

			public void onUpdate(float arg0) {
				// TODO Auto-generated method stub
				updateMainFish();

				float pY = 0;
				float pX = 0;
				try {
					for (int j = 0; j < listCaNho5.size(); j++) {
						if (listCaNho5.get(j).getX() > CAMERA_WIDTH + 85
								|| listCaNho5.get(j).getX() < -85) {
							mCaNho5Pool.recyclePoolItem(listCaNho5.get(j));
							listCaNho5.remove(j);
						}
						pY = listCaNho5.get(j).getX()
								+ listCaNho5.get(j).getHeight() / 2;
						pX = collidesWith.getPX(CaNhoSprite.flag,
								listCaNho5.get(j));
						// Log.e(tag, "To do con ca= " + pX + " va " + pY);
					}
					for (int i = 0; i < linkedCaNho.size(); i++) {
						if (linkedCaNho.get(i).getX() > CAMERA_WIDTH + 85
								|| linkedCaNho.get(i).getX() < -85) {
							caNhoPool.recyclePoolItem(linkedCaNho.get(i));
							linkedCaNho.remove(i);

						}
					}
					for (int j = 0; j < listCaNho5.size(); j++) {
						pY = listCaNho5.get(j).getX()
								+ listCaNho5.get(j).getHeight() / 2;
						pX = collidesWith.getPX(CaNhoSprite.flag,
								listCaNho5.get(j));
						final int tmp = j;
						for (int i = 0; i < linkedCaNho.size(); i++) {
							if (linkedCaNho.get(i).contains(pX, pY)) {
								listCaNho5.get(j).stopAnimation();
								listCaNho5.get(j).animate(
										new long[] { 200, 200, 200 }, 0, 2,
										false, new IAnimationListener() {

											public void onAnimationStarted(
													AnimatedSprite pAnimatedSprite,
													int pInitialLoopCount) {
												// TODO Auto-generated method
												// stub

											}

											public void onAnimationLoopFinished(
													AnimatedSprite pAnimatedSprite,
													int pRemainingLoopCount,
													int pInitialLoopCount) {
												// TODO Auto-generated method
												// stub

											}

											public void onAnimationFrameChanged(
													AnimatedSprite pAnimatedSprite,
													int pOldFrameIndex,
													int pNewFrameIndex) {
												// TODO Auto-generated method
												// stub

											}

											public void onAnimationFinished(
													AnimatedSprite pAnimatedSprite) {
												// TODO Auto-generated method
												// stub
												listCaNho5.get(tmp).animate(
														new long[] { 200, 200,
																200 }, 0, 2,
														true);
											}
										});
								StartActivity.this.mMainScene
										.detachChild(linkedCaNho.get(i));
								caNhoPool.recyclePoolItem(linkedCaNho.get(i));
								linkedCaNho.remove(i);
								Log.e(tag, "va cham = " + i);
							}
						}
					}
				} catch (Exception e) {

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
		float centerX = (CAMERA_WIDTH - mButtonPlayITextureRegion.getWidth()) / 2;
		this.buttonPlay.registerEntityModifier(new ScaleAtModifier(0.5f, 0.0f,
				1.0f, centerX, 20));
		// mStaticMenuScene.registerEntityModifier(new ScaleAtModifier(0.5f,
		// 0.0f,
		// 1.0f, CAMERA_WIDTH / 2, CAMERA_HEIGHT / 2));
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
		mMainFishSprite.animate(new long[] { 200, 200, 200 }, 0, 2, true);
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
			mMainFishSprite.setFlippedHorizontal(false);
		} else {
			mMainFishSprite.setFlippedHorizontal(true);
		}
	}

	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Inner and Anonymous Classes
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

}
