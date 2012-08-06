package vn.sunnet.qplay.andenginefeedingfrenzy.fishpool;

import java.util.ArrayList;
import java.util.Random;

import org.andengine.engine.Engine;
import org.andengine.entity.modifier.PathModifier.Path;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.util.adt.pool.GenericPool;

import vn.sunnet.qplay.andenginefeedingfrenzy.control.PathFish;

/**
 * (c) D09CN2 - PTIT - Ha Noi
 * 
 * (c) Android6 - SUNNET ITC SOLUTION- QLAY
 * 
 * @author Nguyen Hoang Truong<truongnguyenptit@gmail.com>
 * @since 9:52:08 AM Aug 2, 2012 Tel: 0974 878 244
 * 
 */
public class CaNhoPool extends GenericPool<CaNhoSprite> {

	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Constants
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

	// ---------------------------------------------------------------------------------------------
	// Fields
	// ---------------------------------------------------------------------------------------------
	private TiledTextureRegion mTiledTextureRegion;
	private Engine mEngine;
	private int tmp;

	private ArrayList<Path> listPaths;
	private Random random = new Random();
	private PathFish mPathFish = new PathFish();

	// ===========================================================
	// Constructors
	// ===========================================================
	public CaNhoPool(Engine mEngine, TiledTextureRegion mTiledTextureRegion,
			int tmp) {
		this.mEngine = mEngine;
		this.mTiledTextureRegion = mTiledTextureRegion;
		this.tmp = tmp;
	}

	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Getter & Setter
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

	// ----------------------------------------------------------------------------------------------
	// Methods for/from SuperClass/Interfaces
	// ----------------------------------------------------------------------------------------------
	@Override
	protected CaNhoSprite onAllocatePoolItem() {
		// TODO Auto-generated method stub
		listPaths = mPathFish.pathCaNho1();

		return new CaNhoSprite(random.nextInt(listPaths.size()), 0, 64, 64,
				mTiledTextureRegion,
				this.mEngine.getVertexBufferObjectManager());
	}

	@Override
	protected void onHandleRecycleItem(final CaNhoSprite pBullet) {
		pBullet.setIgnoreUpdate(true);
		pBullet.setVisible(false);
	}

	@Override
	protected void onHandleObtainItem(final CaNhoSprite pBullet) {
		pBullet.reset();
	}
	// ===========================================================
	// Methods
	// ===========================================================

	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Inner and Anonymous Classes
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

}
