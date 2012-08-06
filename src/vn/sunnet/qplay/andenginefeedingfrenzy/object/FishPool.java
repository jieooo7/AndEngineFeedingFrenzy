package vn.sunnet.qplay.andenginefeedingfrenzy.object;

import java.util.ArrayList;

import org.andengine.engine.Engine;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.util.adt.pool.GenericPool;

import vn.sunnet.qplay.andenginefeedingfrenzy.constant.FishConstants;

import android.util.Log;

/**
 * (c) D09CN2 - PTIT - Ha Noi
 * 
 * (c) Android6 - SUNNET ITC SOLUTION- QLAY
 * 
 * @author Nguyen Hoang Truong<truongnguyenptit@gmail.com>
 * @since 8:34:15 AM Jul 31, 2012 Tel: 0974 878 244
 * 
 */
public class FishPool extends GenericPool<FishSprite> implements FishConstants {

	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Constants
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	private String tag = "Fish Pool";
	// ---------------------------------------------------------------------------------------------
	// Fields
	// ---------------------------------------------------------------------------------------------

	private ITextureRegion mITextureRegion;
	private TiledTextureRegion mTiledTextureRegion;
	private ArrayList<FishSprite> listFishSprite;
	private Engine mEngine;
	private int tmp;

	// ===========================================================
	// Constructors
	// ===========================================================
	public FishPool(Engine mEngine, TiledTextureRegion mTiledTextureRegion,
			int tmp) {
		this.mEngine = mEngine;
		this.mTiledTextureRegion = mTiledTextureRegion;
		listFishSprite = new ArrayList<FishSprite>();
		this.tmp = tmp;
	}

	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Getter & Setter
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

	// ----------------------------------------------------------------------------------------------
	// Methods for/from SuperClass/Interfaces
	// ----------------------------------------------------------------------------------------------
	@Override
	protected FishSprite onAllocatePoolItem() {
		// TODO Auto-generated method stub
		FishSprite mFishSprite = new FishSprite(this.tmp, 0, 64, 64,
				mTiledTextureRegion,
				this.mEngine.getVertexBufferObjectManager());
		mFishSprite.setAttachScene(true);
		this.mEngine.getScene().attachChild(mFishSprite);
		listFishSprite.add(mFishSprite);
		return mFishSprite;

	}

	@Override
	protected void onHandleRecycleItem(FishSprite pItem) {
		// TODO Auto-generated method stu
		if (pItem.getAttachScene()) {
			pItem.setIgnoreUpdate(true);
			pItem.setVisible(false);
			pItem.setAttachScene(false);
		}
		super.onHandleRecycleItem(pItem);

	}

	@Override
	protected void onHandleObtainItem(FishSprite pItem) {
		// TODO Auto-generated method stub
		pItem.reset();
		super.onHandleObtainItem(pItem);

	}

	// ===========================================================
	// Methods
	// ===========================================================
	public void doSomething() {
		for (FishSprite fs : listFishSprite) {
			if (fs.tmp == true) {
				this.recyclePoolItem(fs);
				Log.e(tag, "Thoa man dieu kien");
			}
			Log.e(tag, "So ca = " + listFishSprite.size());
		}
	}
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Inner and Anonymous Classes
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

}
