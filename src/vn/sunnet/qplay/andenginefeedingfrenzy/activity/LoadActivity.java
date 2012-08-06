package vn.sunnet.qplay.andenginefeedingfrenzy.activity;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.util.FPSLogger;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.ui.activity.SimpleAsyncGameActivity;
import org.andengine.util.progress.IProgressListener;

public class LoadActivity extends SimpleAsyncGameActivity {
	// ===========================================================
	// Constants
	// ===========================================================

	private static final int CAMERA_WIDTH = 720;
	private static final int CAMERA_HEIGHT = 480;

	// ===========================================================
	// Fields
	// ===========================================================

	private BitmapTextureAtlas mBitmapTextureAtlas;
	private ITextureRegion mFaceTextureRegion;
	private BitmapTextureAtlas mFishBitmapTextureAtlas;
	private ITextureRegion mFishITextureRegion;

	// ===========================================================
	// Constructors
	// ===========================================================

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================
	public EngineOptions onCreateEngineOptions() {
		// TODO Auto-generated method stub
		final Camera camera = new Camera(0, 0, LoadActivity.CAMERA_WIDTH,
				LoadActivity.CAMERA_HEIGHT);

		return new EngineOptions(true, ScreenOrientation.LANDSCAPE_SENSOR,
				new RatioResolutionPolicy(LoadActivity.CAMERA_WIDTH,
						LoadActivity.CAMERA_HEIGHT), camera);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.andengine.ui.activity.SimpleAsyncGameActivity#onCreateResourcesAsync
	 * (org.andengine.util.progress.IProgressListener)
	 */
	@Override
	public void onCreateResourcesAsync(IProgressListener pProgressListener)
			throws Exception {
		// TODO Auto-generated method stub
		pProgressListener.onProgressChanged(0);
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		this.mBitmapTextureAtlas = new BitmapTextureAtlas(
				this.getTextureManager(), 32, 32, TextureOptions.BILINEAR);
		pProgressListener.onProgressChanged(50);
		Thread.sleep(1000);
		this.mFaceTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(LoadActivity.this.mBitmapTextureAtlas,
						LoadActivity.this, "face_box.png", 0, 0);
		pProgressListener.onProgressChanged(60);
		Thread.sleep(1000);
		this.mFishBitmapTextureAtlas = new BitmapTextureAtlas(
				getTextureManager(), 512, 512,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mFishITextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(LoadActivity.this.mFishBitmapTextureAtlas,
						LoadActivity.this, "thumb-fish3.png", 0, 0);
		this.mBitmapTextureAtlas.load();
		pProgressListener.onProgressChanged(100);
	}

	@Override
	public Scene onCreateSceneAsync(IProgressListener arg0) throws Exception {
		// TODO Auto-generated method stub
		this.mEngine.registerUpdateHandler(new FPSLogger());

		final Scene scene = new Scene();
		scene.setBackground(new Background(0.09804f, 0.6274f, 0.8784f));

		return scene;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.andengine.ui.activity.SimpleAsyncGameActivity#onPopulateSceneAsync
	 * (org.andengine.entity.scene.Scene,
	 * org.andengine.util.progress.IProgressListener)
	 */
	@Override
	public void onPopulateSceneAsync(Scene pScene, IProgressListener arg1)
			throws Exception {
		// TODO Auto-generated method stub
		final float centerX = (LoadActivity.CAMERA_WIDTH - this.mFaceTextureRegion
				.getWidth()) / 2;
		final float centerY = (LoadActivity.CAMERA_HEIGHT - this.mFaceTextureRegion
				.getHeight()) / 2;

		/* Create the face and add it to the scene. */
		final Sprite face = new Sprite(centerX, centerY,
				this.mFaceTextureRegion, this.getVertexBufferObjectManager());
		final Sprite fish = new Sprite(310, 10, mFishITextureRegion,
				this.getVertexBufferObjectManager());
		pScene.attachChild(face);
		pScene.attachChild(fish);
	}
}
