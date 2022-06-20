package game;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

import edu.monash.fit2099.engine.*;
import game.bonfires.Bonfire;
import game.bonfires.BonfireManager;
import game.enemies.AldrichTheDevourer;
import game.enemies.Mimic;
import game.enemies.Skeleton;
import game.enemies.YhormTheGiant;
import game.enums.Status;
import game.terrains.*;
import game.weapons.StormRuler;

/**
 * The main class for the Dark Souls III game. (Design O' Souls)
 *
 */
public class Application {


	public static void main(String[] args) {

			World world = new World(new Display());

			FancyGroundFactory groundFactory = new FancyGroundFactory(new Dirt(), new Wall(), new Floor(), new Valley());

			List<String> map1 = Arrays.asList(
					"..++++++..+++...........................++++......+++.................+++.......",
					"........+++++..............................+++++++.................+++++........",
					"...........+++.......................................................+++++......",
					"........................................................................++......",
					".........................................................................+++....",
					"............................+.............................................+++...",
					".............................+++.......++++.....................................",
					".............................++.......+......................++++...............",
					".............................................................+++++++............",
					"..................................###___###...................+++...............",
					"..................................#_______#......................+++............",
					"...........++.....................#_______#.......................+.............",
					".........+++......................#_______#........................++...........",
					"............+++...................####_####..........................+..........",
					"..............+......................................................++.........",
					"..............++.................................................++++++.........",
					"............+++...................................................++++..........",
					"+..................................................................++...........",
					"++...+++.........................................................++++...........",
					"+++......................................+++........................+.++........",
					"++++.......++++.........................++.........................+....++......",
					"#####___#####++++......................+...............................+..+.....",
					"_..._....._._#.++......................+...................................+....",
					"...+.__..+...#+++...........................................................+...",
					"...+.....+._.#.+.....+++++...++..............................................++.",
					"___.......___#.++++++++++++++.+++.............................................++");
		GameMap profaneCapitalMap = new GameMap(groundFactory, map1);
		world.addGameMap(profaneCapitalMap);

		List<String> map2 = Arrays.asList(
				"..++++++..+++....................................+++.................+++.......",
				"........+++++.....................................................++...........",
				"...........+++......................................................+..........",
				".......................................+++.............................++......",
				"..........................................................++++..........+++....",
				"................................+........................................+++...",
				".........................++.##___################..............................",
				"............................#...................#..............................",
				"............................#.......#...#.......#..............................",
				"..................++........#..#.............#..#..............................",
				"............................#...................#..............................",
				"++...+++....................#..#.............#..#...............++++...........",
				"++...+++....................#.......#...#.......#...............+.++...........",
				"+++.........................#...............__..#..................+...........",
				"++++.......++++.............##___################.................+....++......",
				".............++++.....................+...............................+..+.....",
				"......................................+...................................+....",
				"...........................................................................+...",
				"...+++++++++++.....................................+++++.....................++");
		GameMap anorLondoMap = new GameMap(groundFactory, map2);
		world.addGameMap(anorLondoMap);

		// --- Setting for profane capital

		// Place player
		Player player = new Player("Unkindled (Player)");
		world.addPlayer(player, profaneCapitalMap.at(38, 12));

		// Add bonfire manager
		BonfireManager bonfireManager = new BonfireManager(player);

		// Place Bonfire in the map
		Bonfire fireLinkShrine = new Bonfire("Firelink shrine Bonfire",profaneCapitalMap.at(38,11),bonfireManager);
		fireLinkShrine.addCapability(Status.IS_LIT);
		profaneCapitalMap.at(38,11).setGround(fireLinkShrine);

		// Place Yhorm the Giant/boss in the map
		YhormTheGiant yhorm = new YhormTheGiant("Yhorm the Giant", profaneCapitalMap.at(6,25));
		profaneCapitalMap.addActor(yhorm, yhorm.getInitialLocation());

		// Place StormRuler beside Yhorm
		StormRuler stormRuler = new StormRuler();
		profaneCapitalMap.at(7,25).addItem(stormRuler);

		// Place Skeletons in the map
		Point[] skeletonLocations= {new Point(15,10),new Point(18,21),new Point(45,10),new Point(16,12),
				new Point(40,25),new Point(53,23),new Point(61,2),new Point(21,17)};
		for (Point skeletonLocation: skeletonLocations) {
			Skeleton skeleton = new Skeleton("Skeleton",profaneCapitalMap.at(skeletonLocation.x,skeletonLocation.y));
			profaneCapitalMap.addActor(skeleton, skeleton.getInitialLocation());
		}

		// Place cemeteries in the map
		Point[] cemeteriesLocation= {new Point(0,10),new Point(18,3),new Point(60,15),new Point(40,5),new Point(16,16),new Point(50,23),new Point(65,4)};
		for (Point cemeteryLocation: cemeteriesLocation) {
			profaneCapitalMap.at(cemeteryLocation.x,cemeteryLocation.y).setGround(new Cemetery());
		}

		// Place fog door
		FogDoor fogDoor = new FogDoor(anorLondoMap.at(38,0),"Anor Londo");
		profaneCapitalMap.at(38,25).setGround(fogDoor);

		// --- Setting for anor londo

		// Place fog door
		FogDoor fogDoor2 = new FogDoor(profaneCapitalMap.at(38,25),"Profane Capital");
		anorLondoMap.at(38,0).setGround(fogDoor2);

		// Place Bonfire in the map
		Bonfire anorLondoShrine = new Bonfire("Anor Londo's Bonfire",anorLondoMap.at(33,0),bonfireManager);
		anorLondoMap.at(33,0).setGround(anorLondoShrine);

		// Place Aldrich the Giant/boss in the map
		AldrichTheDevourer aldrich = new AldrichTheDevourer("Aldrich the Devourer", anorLondoMap.at(38,10));
		anorLondoMap.addActor(aldrich, aldrich.getInitialLocation());

		// Place Skeletons in the map
		Point[] skeletonLocations2= {new Point(15,3),new Point(3,11),new Point(25,7),new Point(18,18)};
		for (Point skeletonLocation: skeletonLocations2) {
			Skeleton skeleton = new Skeleton("Skeleton",anorLondoMap.at(skeletonLocation.x,skeletonLocation.y));
			anorLondoMap.addActor(skeleton, skeleton.getInitialLocation());
		}

		// Place cemeteries in the map
		Point[] cemeteriesLocation2= {new Point(0,10),new Point(18,3),new Point(16,16),new Point(50,18),new Point(65,4)};
		for (Point cemeteryLocation: cemeteriesLocation2) {
			anorLondoMap.at(cemeteryLocation.x,cemeteryLocation.y).setGround(new Cemetery());
		}

		// Place Mimics in the map
		Point[] mimicLocations= {new Point(25,2),new Point(55,4),new Point(65,7),new Point(8,8),new Point(23,11),new Point(47,17)};
		for (Point mimicLocation: mimicLocations) {
			Mimic mimic = new Mimic("Chest",anorLondoMap.at(mimicLocation.x,mimicLocation.y));
			anorLondoMap.addActor(mimic, mimic.getInitialLocation());
		}

		world.run();
	}
}
