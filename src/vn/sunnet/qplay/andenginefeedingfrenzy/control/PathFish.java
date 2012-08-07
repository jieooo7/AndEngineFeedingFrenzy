package vn.sunnet.qplay.andenginefeedingfrenzy.control;

import java.util.ArrayList;
import java.util.Random;

import org.andengine.entity.modifier.PathModifier.Path;

import vn.sunnet.qplay.andenginefeedingfrenzy.constant.FishConstants;

/**
 * (c) D09CN2 - PTIT - Ha Noi
 * 
 * (c) Android6 - SUNNET ITC SOLUTION- QLAY
 * 
 * @author Nguyen Hoang Truong<truongnguyenptit@gmail.com>
 * @since 11:06:10 AM Aug 2, 2012 Tel: 0974 878 244
 * 
 */
public class PathFish implements FishConstants {
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Constants
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// ---------------------------------------------------------------------------------------------
	// Fields
	// ---------------------------------------------------------------------------------------------
	private ArrayList<Path> listPathCaNho1;
	private ArrayList<Path> listPathCaNho2;
	private ArrayList<Path> listPathCaNho3;
	private ArrayList<Path> listPathCaNho4;
	private Random random = new Random();

	// ===========================================================
	// Constructors
	// ===========================================================

	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Getter & Setter
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

	// ----------------------------------------------------------------------------------------------
	// Methods for/from SuperClass/Interfaces
	// ----------------------------------------------------------------------------------------------

	// ===========================================================
	// Methods
	// ===========================================================
	public ArrayList<Path> pathCaNho1() {
		listPathCaNho1 = new ArrayList<Path>();
		Path path1 = new Path(5)
				.to(-80, CAMERA_HEIGHT / 8)
				.to(-70, CAMERA_HEIGHT - CAMERA_HEIGHT / 8 + random.nextInt(20))
				.to(50 + random.nextInt(20),
						CAMERA_HEIGHT - CAMERA_HEIGHT / 4 + random.nextInt(20))
				.to(30 + random.nextInt(20), CAMERA_HEIGHT - CAMERA_HEIGHT / 4)
				.to(-90, CAMERA_HEIGHT + random.nextInt(10));
		listPathCaNho1.add(path1);
		Path path2 = new Path(7)
				.to(-80, CAMERA_HEIGHT / 8)
				.to(-70, CAMERA_HEIGHT - 20 - random.nextInt(50))
				.to(60 + random.nextInt(10),
						CAMERA_HEIGHT - 20 - random.nextInt(50))
				.to(CAMERA_WIDTH / 2 + random.nextInt(10),
						CAMERA_HEIGHT + random.nextInt(10))
				.to(CAMERA_WIDTH + 10 + random.nextInt(10),
						CAMERA_HEIGHT - random.nextInt(50))
				.to(CAMERA_WIDTH - 30 - random.nextInt(30),
						50 + random.nextInt(50))
				.to(-90, 50 + random.nextInt(50));
		listPathCaNho1.add(path2);
		Path path3 = new Path(4)
				.to(-80, CAMERA_HEIGHT - CAMERA_HEIGHT / 8)
				.to(-70, CAMERA_HEIGHT - 30 - random.nextInt(50))
				.to(CAMERA_WIDTH / 4 + random.nextInt(20),
						CAMERA_HEIGHT - CAMERA_HEIGHT / 4 - random.nextInt(10))
				.to(CAMERA_WIDTH + 90, 50 + random.nextInt(50));
		listPathCaNho1.add(path3);
		Path path4 = new Path(6)
				.to(CAMERA_WIDTH + 80, 40 + random.nextInt(50))
				.to(CAMERA_WIDTH + 70, 40 + random.nextInt(50))
				.to(100 + random.nextInt(50), 70 + random.nextInt(30))
				.to(CAMERA_WIDTH / 4 - random.nextInt(20), CAMERA_HEIGHT)
				.to(CAMERA_WIDTH / 2 - random.nextInt(50),
						CAMERA_HEIGHT - random.nextInt(50) - 20)
				.to(-90, CAMERA_HEIGHT - random.nextInt(10));
		listPathCaNho1.add(path4);
		Path path5 = new Path(6)
				.to(CAMERA_WIDTH + 80, 50)
				.to(CAMERA_WIDTH + 70, CAMERA_HEIGHT / 2 - random.nextInt(50))
				.to(CAMERA_WIDTH - 40 - random.nextInt(20),
						CAMERA_HEIGHT / 2 + random.nextInt(20))
				.to(CAMERA_WIDTH / 2, CAMERA_HEIGHT / 4 + random.nextInt(10))
				.to(CAMERA_WIDTH / 4 + random.nextInt(20),
						CAMERA_HEIGHT - random.nextInt(20))
				.to(-90, CAMERA_HEIGHT - 30 - random.nextInt(30));
		listPathCaNho1.add(path5);
		Path path6 = new Path(7)
				.to(-80, 20)
				.to(-70, 20 + random.nextInt(50))
				.to(CAMERA_WIDTH / 4 - random.nextInt(10),
						30 + random.nextInt(50))
				.to(CAMERA_WIDTH - random.nextInt(10),
						CAMERA_HEIGHT - random.nextInt(10))
				.to(CAMERA_WIDTH + 30, CAMERA_HEIGHT)
				.to(CAMERA_WIDTH - random.nextInt(10),
						CAMERA_HEIGHT / 2 + random.nextInt(30))
				.to(-90, 20 + random.nextInt(10));
		listPathCaNho1.add(path6);
		Path path7 = new Path(5).to(-80, 50)
				.to(-70, CAMERA_HEIGHT / 4 + random.nextInt(10))
				.to(CAMERA_WIDTH / 4 + random.nextInt(40), CAMERA_HEIGHT / 2)
				.to(CAMERA_WIDTH / 2 + random.nextInt(50), CAMERA_HEIGHT - 10)
				.to(CAMERA_WIDTH + 90, CAMERA_HEIGHT - random.nextInt(10));
		listPathCaNho1.add(path7);
		Path path8 = new Path(3).to(CAMERA_WIDTH + 80, CAMERA_HEIGHT / 2)
				.to(CAMERA_WIDTH + 70, CAMERA_HEIGHT / 2 + random.nextInt(5))
				.to(-90, CAMERA_HEIGHT / 2 + random.nextInt(10));
		listPathCaNho1.add(path8);
		Path path9 = new Path(3).to(-80, CAMERA_HEIGHT / 2)
				.to(-70, CAMERA_HEIGHT / 2 - random.nextInt(5))
				.to(CAMERA_WIDTH + 90, CAMERA_HEIGHT / 2 - random.nextInt(5));
		listPathCaNho1.add(path9);
		Path path10 = new Path(3).to(-80, CAMERA_HEIGHT - random.nextInt(10))
				.to(-70, CAMERA_HEIGHT - random.nextInt(10))
				.to(CAMERA_WIDTH + 90, CAMERA_HEIGHT - random.nextInt(10));
		listPathCaNho1.add(path10);
		Path path11 = new Path(3)
				.to(CAMERA_WIDTH + 80, CAMERA_HEIGHT - random.nextInt(10))
				.to(CAMERA_WIDTH + 70, CAMERA_HEIGHT - random.nextInt(10))
				.to(-90, CAMERA_HEIGHT - random.nextInt(10));
		listPathCaNho1.add(path11);
		Path path12 = new Path(3).to(-80, 10 + random.nextInt(5))
				.to(-70, 10 + random.nextInt(5))
				.to(CAMERA_WIDTH + 80, 10 + random.nextInt(5));
		listPathCaNho1.add(path12);
		Path path13 = new Path(3).to(CAMERA_WIDTH + 80, 10 + random.nextInt(5))
				.to(CAMERA_WIDTH + 70, 10 + random.nextInt(5))
				.to(-90, 10 + random.nextInt(5));
		listPathCaNho1.add(path13);
		return listPathCaNho1;
	}

	public ArrayList<Path> pathCaNho2() {
		listPathCaNho2 = new ArrayList<Path>();
		Path path = new Path(13)
				.to(-80, 20 + random.nextInt(10))
				.to(-70, 20 + random.nextInt(10))
				.to(40, 20 + random.nextInt(10))
				.to(50 + random.nextInt(20), 30 + random.nextInt(20))
				.to(20 + random.nextInt(20), 20 + random.nextInt(30))
				.to(60 + random.nextInt(20), 50 + random.nextInt(20))
				.to(CAMERA_WIDTH / 2, CAMERA_HEIGHT / 2)
				.to(CAMERA_WIDTH / 4, CAMERA_HEIGHT / 4)
				.to(80 + random.nextInt(30), 100 + random.nextInt(20))
				.to(CAMERA_WIDTH - 80,
						CAMERA_HEIGHT - 100 - random.nextInt(100))
				.to(CAMERA_WIDTH / 2 + random.nextInt(100),
						100 + random.nextInt(20))
				.to(random.nextInt(20) + 70,
						CAMERA_HEIGHT - 200 - random.nextInt(100))
				.to(-90, CAMERA_HEIGHT - random.nextInt(100));
		listPathCaNho2.add(path);
		Path path11 = new Path(13)
				.to(-80, CAMERA_HEIGHT / 2)
				.to(-70, CAMERA_HEIGHT / 2)
				.to(CAMERA_WIDTH - CAMERA_WIDTH / 4,
						CAMERA_HEIGHT / 4 + random.nextInt(50))
				.to(CAMERA_WIDTH / 2 - random.nextInt(50),
						80 + random.nextInt(50))
				.to(CAMERA_WIDTH / 2 + random.nextInt(10),
						80 + random.nextInt(50))
				.to(CAMERA_WIDTH / 2 - random.nextInt(20),
						90 + random.nextInt(20))
				.to(CAMERA_WIDTH /2 +random.nextInt(20), 80 + random.nextInt(50))
				.to(CAMERA_WIDTH - random.nextInt(50),
						CAMERA_HEIGHT - random.nextInt(100))
				.to(CAMERA_WIDTH - random.nextInt(100), CAMERA_HEIGHT / 2)
				.to(CAMERA_WIDTH / 2, CAMERA_HEIGHT / 2)
				.to(CAMERA_WIDTH - random.nextInt(50) - 60, CAMERA_HEIGHT / 8)
				.to(CAMERA_WIDTH / 4, 200)
				.to(CAMERA_WIDTH + 90, CAMERA_HEIGHT / 2);
		Path path12 = new Path(13)
				.to(CAMERA_WIDTH + 80, CAMERA_HEIGHT / 2)
				.to(CAMERA_WIDTH + 70, CAMERA_HEIGHT / 2)
				.to(CAMERA_WIDTH / 4, CAMERA_HEIGHT / 8 + random.nextInt(50))
				.to(CAMERA_WIDTH / 4 + random.nextInt(50),
						60 + random.nextInt(50))
				.to(CAMERA_WIDTH / 2 - random.nextInt(10),
						60 + random.nextInt(50))
				.to(CAMERA_WIDTH / 2 + random.nextInt(20),
						70 + random.nextInt(20))
				.to(CAMERA_WIDTH / 2 + random.nextInt(20),
						80 + random.nextInt(50))
				.to(CAMERA_WIDTH - random.nextInt(50),
						CAMERA_HEIGHT - random.nextInt(100))
				.to(CAMERA_WIDTH - random.nextInt(100), CAMERA_HEIGHT / 2)
				.to(CAMERA_WIDTH / 2, CAMERA_HEIGHT / 2)
				.to(CAMERA_WIDTH - random.nextInt(50) - 60, CAMERA_HEIGHT / 8)
				.to(CAMERA_WIDTH / 4, 200)
				.to(CAMERA_WIDTH + 90, CAMERA_HEIGHT / 2);
		listPathCaNho2.add(path12);
		listPathCaNho2.add(path11);
		Path path1 = new Path(13)
				.to(CAMERA_WIDTH + 80, 20 + random.nextInt(10))
				.to(CAMERA_WIDTH + 70, 20 + random.nextInt(10))
				.to(40, 20 + random.nextInt(10))
				.to(80 + random.nextInt(20), 80 + random.nextInt(20))
				.to(70 + random.nextInt(20), 90 + random.nextInt(30))
				.to(60 + random.nextInt(20), 50 + random.nextInt(20))
				.to(CAMERA_WIDTH / 2, CAMERA_HEIGHT / 2)
				.to(CAMERA_WIDTH / 4, CAMERA_HEIGHT / 4)
				.to(80 + random.nextInt(30), 100 + random.nextInt(20))
				.to(CAMERA_WIDTH - 80,
						CAMERA_HEIGHT - 100 - random.nextInt(100))
				.to(CAMERA_WIDTH / 2 + random.nextInt(100),
						100 + random.nextInt(20))
				.to(random.nextInt(20) + 70,
						CAMERA_HEIGHT - 200 - random.nextInt(100))
				.to(-90, CAMERA_HEIGHT - random.nextInt(100));
		listPathCaNho2.add(path1);
		Path path10 = new Path(5)
				.to(-80, CAMERA_HEIGHT / 8)
				.to(-70, CAMERA_HEIGHT - CAMERA_HEIGHT / 8 + random.nextInt(20))
				.to(50 + random.nextInt(20),
						CAMERA_HEIGHT - CAMERA_HEIGHT / 4 + random.nextInt(20))
				.to(30 + random.nextInt(20), CAMERA_HEIGHT - CAMERA_HEIGHT / 4)
				.to(-90, CAMERA_HEIGHT + random.nextInt(10));
		listPathCaNho2.add(path10);
		Path path2 = new Path(7)
				.to(-80, CAMERA_HEIGHT / 8)
				.to(-70, CAMERA_HEIGHT - 20 - random.nextInt(50))
				.to(60 + random.nextInt(10),
						CAMERA_HEIGHT - 20 - random.nextInt(50))
				.to(CAMERA_WIDTH / 2 + random.nextInt(10),
						CAMERA_HEIGHT + random.nextInt(10))
				.to(CAMERA_WIDTH + 10 + random.nextInt(10),
						CAMERA_HEIGHT - random.nextInt(50))
				.to(CAMERA_WIDTH - 30 - random.nextInt(30),
						50 + random.nextInt(50))
				.to(-90, 50 + random.nextInt(50));
		listPathCaNho2.add(path2);
		Path path3 = new Path(4)
				.to(-80, CAMERA_HEIGHT - CAMERA_HEIGHT / 8)
				.to(-70, CAMERA_HEIGHT - 30 - random.nextInt(50))
				.to(CAMERA_WIDTH / 4 + random.nextInt(20),
						CAMERA_HEIGHT - CAMERA_HEIGHT / 4 - random.nextInt(10))
				.to(CAMERA_WIDTH + 90, 50 + random.nextInt(50));
		listPathCaNho2.add(path3);
		Path path4 = new Path(6)
				.to(CAMERA_WIDTH + 80, 40 + random.nextInt(50))
				.to(CAMERA_WIDTH + 70, 40 + random.nextInt(50))
				.to(100 + random.nextInt(50), 70 + random.nextInt(30))
				.to(CAMERA_WIDTH / 4 - random.nextInt(20), CAMERA_HEIGHT)
				.to(CAMERA_WIDTH / 2 - random.nextInt(50),
						CAMERA_HEIGHT - random.nextInt(50) - 20)
				.to(-90, CAMERA_HEIGHT - random.nextInt(10));
		listPathCaNho2.add(path4);
		Path path5 = new Path(6)
				.to(CAMERA_WIDTH + 80, 50)
				.to(CAMERA_WIDTH + 70, CAMERA_HEIGHT / 2 - random.nextInt(50))
				.to(CAMERA_WIDTH - 40 - random.nextInt(20),
						CAMERA_HEIGHT / 2 + random.nextInt(20))
				.to(CAMERA_WIDTH / 2, CAMERA_HEIGHT / 4 + random.nextInt(10))
				.to(CAMERA_WIDTH / 4 + random.nextInt(20),
						CAMERA_HEIGHT - random.nextInt(20))
				.to(-90, CAMERA_HEIGHT - 30 - random.nextInt(30));
		listPathCaNho2.add(path5);
		Path path6 = new Path(7)
				.to(-80, 20)
				.to(-70, 20 + random.nextInt(50))
				.to(CAMERA_WIDTH / 4 - random.nextInt(10),
						30 + random.nextInt(50))
				.to(CAMERA_WIDTH - random.nextInt(10),
						CAMERA_HEIGHT - random.nextInt(10))
				.to(CAMERA_WIDTH + 30, CAMERA_HEIGHT)
				.to(CAMERA_WIDTH - random.nextInt(10),
						CAMERA_HEIGHT / 2 + random.nextInt(30))
				.to(-90, 20 + random.nextInt(10));
		listPathCaNho2.add(path6);
		Path path7 = new Path(5).to(-80, 50)
				.to(-70, CAMERA_HEIGHT / 4 + random.nextInt(10))
				.to(CAMERA_WIDTH / 4 + random.nextInt(40), CAMERA_HEIGHT / 2)
				.to(CAMERA_WIDTH / 2 + random.nextInt(50), CAMERA_HEIGHT - 10)
				.to(CAMERA_WIDTH + 90, CAMERA_HEIGHT - random.nextInt(10));
		listPathCaNho2.add(path7);
		Path path8 = new Path(3).to(CAMERA_WIDTH + 80, CAMERA_HEIGHT / 2)
				.to(CAMERA_WIDTH + 70, CAMERA_HEIGHT / 2 + random.nextInt(5))
				.to(-90, CAMERA_HEIGHT / 2 + random.nextInt(10));
		listPathCaNho2.add(path8);
		Path path9 = new Path(3).to(-80, CAMERA_HEIGHT / 2)
				.to(-70, CAMERA_HEIGHT / 2 - random.nextInt(5))
				.to(CAMERA_WIDTH + 90, CAMERA_HEIGHT / 2 - random.nextInt(5));
		listPathCaNho2.add(path9);
		Path path15 = new Path(3).to(-80, CAMERA_HEIGHT - random.nextInt(10))
				.to(-70, CAMERA_HEIGHT - random.nextInt(10))
				.to(CAMERA_WIDTH + 90, CAMERA_HEIGHT - random.nextInt(10));
		listPathCaNho2.add(path15);
		Path path16 = new Path(3)
				.to(CAMERA_WIDTH + 80, CAMERA_HEIGHT - random.nextInt(10))
				.to(CAMERA_WIDTH + 70, CAMERA_HEIGHT - random.nextInt(10))
				.to(-90, CAMERA_HEIGHT - random.nextInt(10));
		listPathCaNho2.add(path16);
		Path path14 = new Path(3).to(-80, 10 + random.nextInt(5))
				.to(-70, 10 + random.nextInt(5))
				.to(CAMERA_WIDTH + 90, 10 + random.nextInt(5));
		listPathCaNho2.add(path14);
		Path path17 = new Path(3).to(CAMERA_WIDTH + 80, 10 + random.nextInt(5))
				.to(CAMERA_WIDTH + 70, 10 + random.nextInt(5))
				.to(-90, 10 + random.nextInt(5));
		listPathCaNho2.add(path17);
		return listPathCaNho2;
	}

	public ArrayList<Path> pathCaNho3() {
		listPathCaNho3 = new ArrayList<Path>();
		Path path = new Path(13)
				.to(-80, 20 + random.nextInt(10))
				.to(-70, 20 + random.nextInt(10))
				.to(40, 20 + random.nextInt(10))
				.to(50 + random.nextInt(20), 30 + random.nextInt(20))
				.to(20 + random.nextInt(20), 20 + random.nextInt(30))
				.to(60 + random.nextInt(20), 50 + random.nextInt(20))
				.to(CAMERA_WIDTH / 2, CAMERA_HEIGHT / 2)
				.to(CAMERA_WIDTH / 4, CAMERA_HEIGHT / 4)
				.to(80 + random.nextInt(30), 100 + random.nextInt(20))
				.to(CAMERA_WIDTH - 80,
						CAMERA_HEIGHT - 100 - random.nextInt(100))
				.to(CAMERA_WIDTH / 2 + random.nextInt(100),
						100 + random.nextInt(20))
				.to(random.nextInt(20) + 70,
						CAMERA_HEIGHT - 200 - random.nextInt(100))
				.to(-90, CAMERA_HEIGHT - random.nextInt(100));
		listPathCaNho3.add(path);
		Path path1 = new Path(13)
				.to(-80, CAMERA_HEIGHT / 2)
				.to(-70, CAMERA_HEIGHT / 2)
				.to(CAMERA_WIDTH - CAMERA_WIDTH / 4,
						CAMERA_HEIGHT / 4 + random.nextInt(50))
				.to(CAMERA_WIDTH / 2 - random.nextInt(50),
						80 + random.nextInt(50))
				.to(CAMERA_WIDTH / 2 + random.nextInt(10),
						80 + random.nextInt(50))
				.to(CAMERA_WIDTH / 2 - random.nextInt(20),
						90 + random.nextInt(20))
				.to(CAMERA_WIDTH /2 +random.nextInt(20), 80 + random.nextInt(50))
				.to(CAMERA_WIDTH - random.nextInt(50),
						CAMERA_HEIGHT - random.nextInt(100))
				.to(CAMERA_WIDTH - random.nextInt(100), CAMERA_HEIGHT / 2)
				.to(CAMERA_WIDTH / 2, CAMERA_HEIGHT / 2)
				.to(CAMERA_WIDTH - random.nextInt(50) - 60, CAMERA_HEIGHT / 8)
				.to(CAMERA_WIDTH / 4, 200)
				.to(CAMERA_WIDTH + 90, CAMERA_HEIGHT / 2);
		Path path2 = new Path(13)
				.to(CAMERA_WIDTH + 80, CAMERA_HEIGHT / 2)
				.to(CAMERA_WIDTH + 70, CAMERA_HEIGHT / 2)
				.to(CAMERA_WIDTH / 4, CAMERA_HEIGHT / 8 + random.nextInt(50))
				.to(CAMERA_WIDTH / 4 + random.nextInt(50),
						60 + random.nextInt(50))
				.to(CAMERA_WIDTH / 2 - random.nextInt(10),
						60 + random.nextInt(50))
				.to(CAMERA_WIDTH / 2 + random.nextInt(20),
						70 + random.nextInt(20))
				.to(CAMERA_WIDTH / 2 + random.nextInt(20),
						80 + random.nextInt(50))
				.to(CAMERA_WIDTH - random.nextInt(50),
						CAMERA_HEIGHT - random.nextInt(100))
				.to(CAMERA_WIDTH - random.nextInt(100), CAMERA_HEIGHT / 2)
				.to(CAMERA_WIDTH / 2, CAMERA_HEIGHT / 2)
				.to(CAMERA_WIDTH - random.nextInt(50) - 60, CAMERA_HEIGHT / 8)
				.to(CAMERA_WIDTH / 4, 200)
				.to(CAMERA_WIDTH + 90, CAMERA_HEIGHT / 2);
		listPathCaNho3.add(path1);
		listPathCaNho3.add(path2);
		Path path3 = new Path(3).to(CAMERA_WIDTH + 80, CAMERA_HEIGHT / 2)
				.to(CAMERA_WIDTH + 70, CAMERA_HEIGHT / 2 + random.nextInt(5))
				.to(-90, CAMERA_HEIGHT / 2 + random.nextInt(10));
		listPathCaNho3.add(path3);
		Path path4 = new Path(3).to(-80, CAMERA_HEIGHT / 2)
				.to(-70, CAMERA_HEIGHT / 2 - random.nextInt(5))
				.to(CAMERA_WIDTH + 90, CAMERA_HEIGHT / 2 - random.nextInt(5));
		listPathCaNho3.add(path4);
		Path path8 = new Path(3).to(-80, CAMERA_HEIGHT - random.nextInt(10))
				.to(-70, CAMERA_HEIGHT - random.nextInt(10))
				.to(CAMERA_WIDTH + 90, CAMERA_HEIGHT - random.nextInt(10));
		listPathCaNho3.add(path8);
		Path path7 = new Path(3)
				.to(CAMERA_WIDTH + 80, CAMERA_HEIGHT - random.nextInt(10))
				.to(CAMERA_WIDTH + 70, CAMERA_HEIGHT - random.nextInt(10))
				.to(-90, CAMERA_HEIGHT - random.nextInt(10));
		listPathCaNho3.add(path7);
		Path path6 = new Path(3).to(-80, 10 + random.nextInt(5))
				.to(-70, 10 + random.nextInt(5))
				.to(CAMERA_WIDTH + 90, 10 + random.nextInt(5));
		listPathCaNho3.add(path6);
		Path path5 = new Path(3).to(CAMERA_WIDTH + 80, 10 + random.nextInt(5))
				.to(CAMERA_WIDTH + 70, 10 + random.nextInt(5))
				.to(-90, 10 + random.nextInt(5));
		listPathCaNho3.add(path5);
		return listPathCaNho3;
	}

	public ArrayList<Path> pathCaNho4() {
		listPathCaNho4 = new ArrayList<Path>();
		Path path = new Path(3).to(CAMERA_WIDTH + 80, CAMERA_HEIGHT / 2)
				.to(CAMERA_WIDTH + 70, CAMERA_HEIGHT / 2 + random.nextInt(5))
				.to(-90, CAMERA_HEIGHT / 2 + random.nextInt(10));
		listPathCaNho4.add(path);
		Path path1 = new Path(3).to(-80, CAMERA_HEIGHT / 2)
				.to(-70, CAMERA_HEIGHT / 2 - random.nextInt(5))
				.to(CAMERA_WIDTH + 90, CAMERA_HEIGHT / 2 - random.nextInt(5));
		listPathCaNho4.add(path1);
		Path path2 = new Path(3).to(-80, CAMERA_HEIGHT - random.nextInt(10))
				.to(-70, CAMERA_HEIGHT - random.nextInt(10))
				.to(CAMERA_WIDTH + 90, CAMERA_HEIGHT - random.nextInt(10));
		listPathCaNho4.add(path2);
		Path path3 = new Path(3)
				.to(CAMERA_WIDTH + 80, CAMERA_HEIGHT - random.nextInt(10))
				.to(CAMERA_WIDTH + 70, CAMERA_HEIGHT - random.nextInt(10))
				.to(-90, CAMERA_HEIGHT - random.nextInt(10));
		listPathCaNho4.add(path3);
		Path path4 = new Path(3).to(-80, 10 + random.nextInt(5))
				.to(-70, 10 + random.nextInt(5))
				.to(CAMERA_WIDTH + 90, 10 + random.nextInt(5));
		listPathCaNho4.add(path4);
		Path path5 = new Path(3).to(CAMERA_WIDTH + 80, 10 + random.nextInt(5))
				.to(CAMERA_WIDTH + 70, 10 + random.nextInt(5))
				.to(-90, 10 + random.nextInt(5));
		listPathCaNho4.add(path5);
		return listPathCaNho4;
	}
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Inner and Anonymous Classes
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

}
