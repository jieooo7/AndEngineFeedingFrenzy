package vn.sunnet.qplay.andenginefeedingfrenzy.activity;

import java.util.Random;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.PathModifier;
import org.andengine.entity.modifier.PathModifier.IPathModifierListener;
import org.andengine.entity.modifier.PathModifier.Path;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.util.FPSLogger;
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
import org.andengine.util.debug.Debug;
import org.andengine.util.modifier.ease.EaseSineInOut;

import android.os.Handler;

/**
 * (c) D09CN2 - PTIT - Ha Noi
 * 
 * (c) Android6 - SUNNET ITC SOLUTION- QLAY
 * 
 * @author Nguyen Hoang Truong<truongnguyenptit@gmail.com>
 * @since 10:30:17 PM Jul 25, 2012 Tel: 0974 878 244
 * 
 */
public class Level1Activity extends SimpleBaseGameActivity {

	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Constants
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	private static final int CAMERA_WIDTH = 640;
	private static final int CAMERA_HEIGHT = 384;
	private String tag = "Level1Activity";
	// ---------------------------------------------------------------------------------------------
	// Fields
	// ---------------------------------------------------------------------------------------------
	protected Scene level1Scene;
	protected Camera mCamera;

	private BitmapTextureAtlas mBackgroundLevel1BitmapTextureAtlas;
	private ITextureRegion mBackgroundLevel1ITextureRegion;

	private BitmapTextureAtlas mCaVangBitmapTextureAtlas;
	private ITextureRegion mCaVangITextureRegion;

	private BuildableBitmapTextureAtlas mCaHongBitmapTextureAtlas;
	private TiledTextureRegion mCaHongTiledTextureRegion;

	private BitmapTextureAtlas mBallTexture;
	private ITextureRegion mBallTextureRegion;
	private int numBall = 0;
	private Sprite[] mBall = new Sprite[10];
	private int j = 0;

	private Handler mHandler;

	private Random random;
	private int i = 0;

	final AnimatedSprite[] mCaHong = new AnimatedSprite[4];
	int numFish = 0;

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

		return new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED,
				new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT),
				this.mCamera);
	}

	@Override
	public void onCreateResources() {
		// TODO Auto-generated method stub
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/Level1/");
		this.mBackgroundLevel1BitmapTextureAtlas = new BitmapTextureAtlas(
				this.getTextureManager(), 1024, 1024,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mBackgroundLevel1ITextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(this.mBackgroundLevel1BitmapTextureAtlas,
						this, "bg2.jpg", 0, 0);
		this.mBackgroundLevel1BitmapTextureAtlas.load();

		// this.mCaVangBitmapTextureAtlas = new
		// BitmapTextureAtlas(getTextureManager(),
		// 512, 512, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		// this.mCaVangITextureRegion = BitmapTextureAtlasTextureRegionFactory
		// .createFromAsset(this.mCaVangBitmapTextureAtlas, this, "ca-vang.png",
		// 0, 0);
		// this.mCaVangBitmapTextureAtlas.load();
		//
		// this.mBallTexture = new BitmapTextureAtlas(this.getTextureManager(),
		// 32, 32, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		// this.mBallTextureRegion = BitmapTextureAtlasTextureRegionFactory
		// .createFromAsset(mBallTexture, this, "ball2.png", 0, 0);
		// this.mBallTexture.load();
		//
		// this.mCaHongBitmapTextureAtlas = new BuildableBitmapTextureAtlas(
		// getTextureManager(), 512, 512,
		// TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		// this.mCaHongTiledTextureRegion =
		// BitmapTextureAtlasTextureRegionFactory
		// .createTiledFromAsset(this.mCaHongBitmapTextureAtlas, this,
		// "ca-hong-nho.png", 1, 7);
		// try {
		// this.mCaHongBitmapTextureAtlas
		// .build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource,
		// BitmapTextureAtlas>(
		// 0, 0, 1));
		// this.mCaHongBitmapTextureAtlas.load();
		// } catch (TextureAtlasBuilderException e) {
		// Debug.e(e);
		// }
	}

	@Override
	public Scene onCreateScene() {
		this.mEngine.registerUpdateHandler(new FPSLogger());

		this.level1Scene = new Scene();
		final Sprite backgroundLevel1 = new Sprite(0, 0,
				mBackgroundLevel1ITextureRegion,
				this.getVertexBufferObjectManager());
		this.level1Scene.setBackground(new SpriteBackground(backgroundLevel1));

		// mHandler.postDelayed(mStartCaHong, 1000);
		// mHandler.postDelayed(mStartBall, 1000);
		return this.level1Scene;
	}

	// ===========================================================
	// Methods
	// ===========================================================
	public Runnable mStartCaHong = new Runnable() {

		public void run() {
			// TODO Auto-generated method stub
			i = numFish++;
			int X1 = 10 + random.nextInt(100);
			int X2 = 78 + random.nextInt(100);
			int X3 = 10 + random.nextInt(100);
			int Y1 = 50 + random.nextInt(100);
			int Y2 = 20 + random.nextInt(100);
			int Y3 = 50 + random.nextInt(100);
			final Path path = new Path(3).to(CAMERA_WIDTH + X1, Y1).to(X2, Y2)
					.to(CAMERA_WIDTH + X3, Y3);
			mCaHong[i] = new AnimatedSprite(18, 18, 18, 18,
					Level1Activity.this.mCaHongTiledTextureRegion,
					Level1Activity.this.getVertexBufferObjectManager());
			mCaHong[i].registerEntityModifier(new LoopEntityModifier(
					new PathModifier(60, path, null,
							new IPathModifierListener() {
								public void onPathStarted(
										final PathModifier pPathModifier,
										final IEntity pEntity) {
									Debug.d("onPathStarted");
								}

								public void onPathWaypointStarted(
										final PathModifier pPathModifier,
										final IEntity pEntity,
										final int pWaypointIndex) {
									Debug.d("onPathWaypointStarted:  "
											+ pWaypointIndex);
									switch (pWaypointIndex) {
									case 0:
										mCaHong[i].animate(new long[] { 200,
												200, 200 }, 0, 2, true);
										break;
									case 1:
										mCaHong[i].animate(new long[] { 200,
												200, 200 }, 2, 4, true);
										break;
									}
								}

								public void onPathWaypointFinished(
										final PathModifier pPathModifier,
										final IEntity pEntity,
										final int pWaypointIndex) {
									Debug.d("onPathWaypointFinished: "
											+ pWaypointIndex);
								}

								public void onPathFinished(
										final PathModifier pPathModifier,
										final IEntity pEntity) {
									Debug.d("onPathFinished");
								}
							}, EaseSineInOut.getInstance())));
			Level1Activity.this.level1Scene.attachChild(mCaHong[i]);
			if (numFish < 4) {
				mHandler.postDelayed(mStartCaHong, 1000);
			}
		}
	};

	public Runnable mStartBall = new Runnable() {

		public void run() {
			// TODO Auto-generated method stub
			j = numBall++;
			float startX = random.nextFloat() * (CAMERA_HEIGHT + 20.0f);
			mBall[j] = new Sprite(startX, CAMERA_HEIGHT + 20.0f,
					mBallTextureRegion,
					Level1Activity.this.getVertexBufferObjectManager());
			final Path ballPath = new Path(2).to(CAMERA_WIDTH - 30,
					CAMERA_HEIGHT + 10).to(
					CAMERA_WIDTH - 30 - random.nextInt(100), -10);
			mBall[j].registerEntityModifier(new LoopEntityModifier(
					new PathModifier(5, ballPath, null,
							new IPathModifierListener() {

								public void onPathWaypointStarted(
										PathModifier arg0, IEntity arg1,
										int arg2) {
									// TODO Auto-generated method stub
								}

								public void onPathWaypointFinished(
										PathModifier arg0, IEntity arg1,
										int arg2) {
									// TODO Auto-generated method stub

								}

								public void onPathStarted(PathModifier arg0,
										IEntity arg1) {
									// TODO Auto-generated method stub
									Debug.d("onPathStarted");
								}

								public void onPathFinished(PathModifier arg0,
										IEntity arg1) {
									// TODO Auto-generated method stub
									Debug.d("onPathStarted");
								}
							})));
			Level1Activity.this.level1Scene.attachChild(mBall[j]);
			if (numBall < 10) {
				mHandler.postDelayed(mStartBall, 2000);
			}
		}
	};

	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Inner and Anonymous Classes
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

}
