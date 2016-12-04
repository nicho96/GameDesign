package ca.nicho.foundation;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class SpriteSheet {
	
	//Reserve 0-299 for entities
	public static final int ENTITY_PLAYER = 0;
	public static final int ENTITY_ENEMY = 1;
	public static final int ENTITY_BATTLESHIP = 2;
	public static final int ENTITY_MISSILE = 3;
	public static final int ENTITY_MEDIC = 4;
	public static final int ENTITY_CARGO = 5;
	public static final int ENTITY_RADAR = 6;
	public static final int ENTITY_TRAIL = 7;
	public static final int ENTITY_WINDMILL = 8;
	public static final int ENTITY_NAVY_BASE = 9;
	public static final int ENTITY_EXPLOSION = 10;
	public static final int ENTITY_WAVE = 11;
	public static final int ENTITY_CARE_PACKAGE = 12;
	public static final int ENTITY_TURRET = 13;
	
	//Reserver 298 + 299 for Spawn Tiles (tiles than get converted to player spawn regions)
	public static final int TILE_P1_SPAWN = 298;
	public static final int TILE_P2_SPAWN = 299;
	
	//Reserve 300-399 for Tiles
	public static final int TILE_WOOD = 300;
	public static final int TILE_STONE = 301;
	public static final int TILE_METAL = 302;
	public static final int TILE_MARSH = 303;
	public static final int TILE_NET = 304;
	public static final int TILE_BOX = 305;
	
	//Reserve 400-499 for GUI elements (may need to extend in future
	public static final int GUI_MAP = 400;
	public static final int GUI_HOST = 401;
	public static final int GUI_ENTER = 402;
	public static final int GUI_SLOT = 403;
	public static final int GUI_SELECTED = 404; //404 not found
	public static final int GUI_LARGE_MAP = 405;
	public static final int GUI_LOG = 406;
	public static final int GUI_DISABLED = 407;
	public static final int GUI_BACKGROUND = 407;
	public static final int GUI_RADAR_SCAN = 408;
	public static final int GUI_CROSSHAIR = 409;
	public static final int GUI_LOG_BOX = 410;
	public static final int GUI_POINTS_BACKGROUND = 411;
	public static final int GUI_BACKGROUND_TOP = 412;
	public static final int GUI_SHIP_BACKGROUND = 413;
	public static final int GUI_SHIPCRAFT = 414;
	public static final int GUI_SLOT_NUM = 415;
	public static final int GUI_AIRSTRIKE = 416;
		
	//Reserver 500+ for generic sprites
	public static final int BACK_OCEAN_1 = 500;
	public static final int BACK_OCEAN_2 = 501;
	public static final int BACK_OCEAN_3 = 502;
	public static final int BACK_OCEAN_4 = 503;
	
	public static final int DOT_GREEN = 504;
	public static final int DOT_RED = 505;
	public static final int DOT_BLUE = 506;
	
	//Reserve 990 - 999 for number sprites
	public static final int NUM_0 = 990;
	public static final int NUM_1 = 991;
	public static final int NUM_2 = 992;
	public static final int NUM_3 = 993;
	public static final int NUM_4 = 994;
	public static final int NUM_5 = 995;
	public static final int NUM_6 = 996;
	public static final int NUM_7 = 997;
	public static final int NUM_8 = 998;
	public static final int NUM_9 = 999;

	
	public static final int INVISIBLE_COLOR = 0xFFFF00FF;
	
	public static Sprite SPRITE_PLAYER;
	public static Sprite SPRITE_ENEMY;
	public static Sprite SPRITE_BATTLESHIP;
	public static Sprite SPRITE_MISSILE;
	public static Sprite SPRITE_MEDIC_1;
	public static Sprite SPRITE_MEDIC_2;
	public static Sprite SPRITE_MEDIC_3;
	public static Sprite SPRITE_MEDIC_4;
	public static Sprite SPRITE_MEDIC_1_DEAD;
	public static Sprite SPRITE_MEDIC_2_DEAD;
	public static Sprite SPRITE_MEDIC_3_DEAD;
	public static Sprite SPRITE_MEDIC_4_DEAD;
	public static Sprite SPRITE_MEDIC_1_HEAL;
	public static Sprite SPRITE_MEDIC_2_HEAL;
	public static Sprite SPRITE_MEDIC_3_HEAL;
	public static Sprite SPRITE_MEDIC_4_HEAL;
	public static Sprite SPRITE_CARGO_1;
	public static Sprite SPRITE_CARGO_2;
	public static Sprite SPRITE_CARGO_3;
	public static Sprite SPRITE_CARGO_4;
	public static Sprite SPRITE_CARGO_5;	
	public static Sprite SPRITE_CARGO_1_DEAD;
	public static Sprite SPRITE_CARGO_2_DEAD;
	public static Sprite SPRITE_CARGO_3_DEAD;
	public static Sprite SPRITE_CARGO_4_DEAD;
	public static Sprite SPRITE_CARGO_5_DEAD;

	public static Sprite SPRITE_BATTLE_1;
	public static Sprite SPRITE_BATTLE_2;
	public static Sprite SPRITE_BATTLE_3;
	public static Sprite SPRITE_BATTLE_4;

	public static Sprite SPRITE_BATTLE_1_DEAD;
	public static Sprite SPRITE_BATTLE_2_DEAD;
	public static Sprite SPRITE_BATTLE_3_DEAD;
	public static Sprite SPRITE_BATTLE_4_DEAD;
	
	//public static Sprite SPRITE_CARGO;
	public static Sprite SPRITE_RADAR_ICON;
	public static Sprite SPRITE_RADAR_1;
	public static Sprite SPRITE_RADAR_2;
	public static Sprite SPRITE_RADAR_3;
	public static Sprite SPRITE_RADAR_4;
	public static Sprite SPRITE_RADAR_5;
	public static Sprite SPRITE_RADAR_6;
	public static Sprite SPRITE_RADAR_7;
	public static Sprite SPRITE_RADAR_8;
	
	public static Sprite SPRITE_WAVE_1;
	public static Sprite SPRITE_WAVE_2;
	public static Sprite SPRITE_WAVE_3;
	public static Sprite SPRITE_WAVE_4;
	public static Sprite SPRITE_WAVE_5;
	public static Sprite SPRITE_WAVE_6;
	public static Sprite SPRITE_WAVE_7;
	public static Sprite SPRITE_WAVE_8;
	public static Sprite SPRITE_WAVE_9;
		
	public static Sprite SPRITE_EXPLOSION;
	
	public static Sprite SPRITE_AIRSTRIKE;
	
	public static Sprite SPRITE_TRAIL_1;
	public static Sprite SPRITE_TRAIL_2;
	public static Sprite SPRITE_TRAIL_3;
	public static Sprite SPRITE_TRAIL_4;
	public static Sprite SPRITE_TRAIL_5;
	public static Sprite SPRITE_TRAIL_6;
	public static Sprite SPRITE_TRAIL_7;
	public static Sprite SPRITE_TRAIL_8;
	public static Sprite SPRITE_TRAIL_9;
	public static Sprite SPRITE_TRAIL_10;
	public static Sprite SPRITE_TRAIL_11;
	
	public static Sprite SPRITE_PACKAGE_ICON;
	public static Sprite SPRITE_PACKAGE_1;
	public static Sprite SPRITE_PACKAGE_2;
	public static Sprite SPRITE_PACKAGE_3;
	public static Sprite SPRITE_PACKAGE_4;
	public static Sprite SPRITE_PACKAGE_5;

	public static Sprite SPRITE_WINDMILL_1;
	public static Sprite SPRITE_WINDMILL_2;
	public static Sprite SPRITE_WINDMILL_3;
	
	public static Sprite SPRITE_TURRET_1;
	public static Sprite SPRITE_TURRET_2;
	public static Sprite SPRITE_TURRET_3;
	public static Sprite SPRITE_TURRET_4;
	public static Sprite SPRITE_TURRET_5;
	public static Sprite SPRITE_TURRET_6;
	public static Sprite SPRITE_TURRET_7;
	public static Sprite SPRITE_TURRET_8;
	public static Sprite SPRITE_TURRET_1_FIRE;
	public static Sprite SPRITE_TURRET_2_FIRE;

	public static Sprite SPRITE_NAVY_BASE_R_1;
	public static Sprite SPRITE_NAVY_BASE_R_2;
	public static Sprite SPRITE_NAVY_BASE_R_3;
	public static Sprite SPRITE_NAVY_BASE_R_4;
	public static Sprite SPRITE_NAVY_BASE_G_1;
	public static Sprite SPRITE_NAVY_BASE_G_2;
	public static Sprite SPRITE_NAVY_BASE_G_3;
	public static Sprite SPRITE_NAVY_BASE_G_4;

	public static Sprite SPRITE_LOG_LG;
	public static Sprite SPRITE_LOG_SM;
	
	public static Sprite SPRITE_P1_SPAWN;
	public static Sprite SPRITE_P2_SPAWN;
		
	public static Sprite SPRITE_WOOD;
	public static Sprite SPRITE_STONE;
	public static Sprite SPRITE_METAL;
	public static Sprite SPRITE_STONE_1;
	public static Sprite SPRITE_STONE_2;
	public static Sprite SPRITE_STONE_3;
	public static Sprite SPRITE_STONE_4;
	public static Sprite SPRITE_MARSH;
	public static Sprite SPRITE_NET;
	public static Sprite SPRITE_BOX;
	
	//GUI
	public static Sprite SPRITE_MAP_SMALL;
	public static Sprite SPRITE_HOST;
	public static Sprite SPRITE_ENTER;
	public static Sprite SPRITE_SLOT;
	public static Sprite SPRITE_SLOT_NUM;
	public static Sprite SPRITE_SELECTED;
	public static Sprite SPRITE_MAP_LARGE;
	public static Sprite SPRITE_CROSSHAIR;
	public static Sprite SPRITE_DISABLED;
	public static Sprite SPRITE_BACKGROUND;
	public static Sprite SPRITE_BACKGROUND_TOP;
	public static Sprite SPRITE_SCAN;
	public static Sprite SPRITE_POINTS_BACKGROUND;
	public static Sprite SPRITE_SHIPS_BACKGROUND;
	public static Sprite SPRITE_SHIPCRAFT;
	
	//Ocean
	public static Sprite SPRITE_OCEAN_1;
	public static Sprite SPRITE_OCEAN_2;
	public static Sprite SPRITE_OCEAN_3;
	public static Sprite SPRITE_OCEAN_4;
	
	//Blank
	public static Sprite SPRITE_BLANK;
	
	//Dots
	public static Sprite SPRITE_DOT_GREEN;
	public static Sprite SPRITE_DOT_RED;
	public static Sprite SPRITE_DOT_BLUE;
	
	//Numbers
	public static Sprite SPRITE_0;
	public static Sprite SPRITE_1;
	public static Sprite SPRITE_2;
	public static Sprite SPRITE_3;
	public static Sprite SPRITE_4;
	public static Sprite SPRITE_5;
	public static Sprite SPRITE_6;
	public static Sprite SPRITE_7;
	public static Sprite SPRITE_8;
	public static Sprite SPRITE_9;
	
	//This object style allows for a singleton object creation, meaning it will only be instantiated once
	private static boolean isInitialized = false;
	public static void initSprites(){
		if(!isInitialized)
			new SpriteSheet();
		isInitialized = true;
	}
	
	private SpriteSheet(){
		SPRITE_PLAYER = new Sprite("battleship", ENTITY_PLAYER);
		SPRITE_ENEMY = new Sprite("enemy", ENTITY_ENEMY);
		SPRITE_MISSILE = new Sprite("missile", ENTITY_MISSILE);

		SPRITE_MEDIC_1 = new Sprite("medic_1", ENTITY_MEDIC);
		SPRITE_MEDIC_2 = new Sprite("medic_2", ENTITY_MEDIC);
		SPRITE_MEDIC_3 = new Sprite("medic_3", ENTITY_MEDIC);
		SPRITE_MEDIC_4 = new Sprite("medic_4", ENTITY_MEDIC);
		SPRITE_MEDIC_1_DEAD = new Sprite("medic_dead_1", ENTITY_MEDIC);
		SPRITE_MEDIC_2_DEAD = new Sprite("medic_dead_2", ENTITY_MEDIC);
		SPRITE_MEDIC_3_DEAD = new Sprite("medic_dead_3", ENTITY_MEDIC);
		SPRITE_MEDIC_4_DEAD = new Sprite("medic_dead_4", ENTITY_MEDIC);
		SPRITE_MEDIC_1_HEAL = new Sprite("medic_heal_1", ENTITY_MEDIC);
		SPRITE_MEDIC_2_HEAL = new Sprite("medic_heal_2", ENTITY_MEDIC);
		SPRITE_MEDIC_3_HEAL = new Sprite("medic_heal_3", ENTITY_MEDIC);
		SPRITE_MEDIC_4_HEAL = new Sprite("medic_heal_4", ENTITY_MEDIC);
		
		SPRITE_CARGO_1 = new Sprite("cargo_1", ENTITY_CARGO);
		SPRITE_CARGO_2 = new Sprite("cargo_2", ENTITY_CARGO);
		SPRITE_CARGO_3 = new Sprite("cargo_3", ENTITY_CARGO);
		SPRITE_CARGO_4 = new Sprite("cargo_4", ENTITY_CARGO);
		SPRITE_CARGO_5 = new Sprite("cargo_5", ENTITY_CARGO);
		SPRITE_CARGO_1_DEAD = new Sprite("cargo_dead_1", ENTITY_CARGO);
		SPRITE_CARGO_2_DEAD = new Sprite("cargo_dead_2", ENTITY_CARGO);
		SPRITE_CARGO_3_DEAD = new Sprite("cargo_dead_3", ENTITY_CARGO);
		SPRITE_CARGO_4_DEAD = new Sprite("cargo_dead_4", ENTITY_CARGO);
		SPRITE_CARGO_5_DEAD = new Sprite("cargo_dead_5", ENTITY_CARGO);
		
		SPRITE_BATTLE_1 = new Sprite("special1", ENTITY_BATTLESHIP);
		SPRITE_BATTLE_2 = new Sprite("special2", ENTITY_BATTLESHIP);
		SPRITE_BATTLE_3 = new Sprite("special3", ENTITY_BATTLESHIP);
		SPRITE_BATTLE_4 = new Sprite("special4", ENTITY_BATTLESHIP);
		SPRITE_BATTLE_1_DEAD = new Sprite("special_dead_1", ENTITY_BATTLESHIP);
		SPRITE_BATTLE_2_DEAD = new Sprite("special_dead_2", ENTITY_BATTLESHIP);
		SPRITE_BATTLE_3_DEAD = new Sprite("special_dead_3", ENTITY_BATTLESHIP);
		SPRITE_BATTLE_4_DEAD = new Sprite("special_dead_4", ENTITY_BATTLESHIP);
		
		SPRITE_RADAR_ICON = new Sprite("radar", ENTITY_RADAR);
		SPRITE_RADAR_1 = new Sprite("radar_1", ENTITY_RADAR);
		SPRITE_RADAR_2 = new Sprite("radar_2", ENTITY_RADAR);
		SPRITE_RADAR_3 = new Sprite("radar_3", ENTITY_RADAR);
		SPRITE_RADAR_4 = new Sprite("radar_4", ENTITY_RADAR);
		SPRITE_RADAR_5 = new Sprite("radar_5", ENTITY_RADAR);
		SPRITE_RADAR_6 = new Sprite("radar_6", ENTITY_RADAR);
		SPRITE_RADAR_7 = new Sprite("radar_7", ENTITY_RADAR);
		SPRITE_RADAR_8 = new Sprite("radar_8", ENTITY_RADAR);
		
		SPRITE_STONE_1 = new Sprite("rock1", TILE_STONE);
		
		SPRITE_WAVE_1 = new Sprite("wave_1", ENTITY_WAVE);
		SPRITE_WAVE_2 = new Sprite("wave_2", ENTITY_WAVE);
		SPRITE_WAVE_3 = new Sprite("wave_3", ENTITY_WAVE);
		SPRITE_WAVE_4 = new Sprite("wave_4", ENTITY_WAVE);
		SPRITE_WAVE_5 = new Sprite("wave_5", ENTITY_WAVE);
		SPRITE_WAVE_6 = new Sprite("wave_6", ENTITY_WAVE);
		SPRITE_WAVE_7 = new Sprite("wave_7", ENTITY_WAVE);
		SPRITE_WAVE_8 = new Sprite("wave_8", ENTITY_WAVE);
		SPRITE_WAVE_9 = new Sprite("wave_9", ENTITY_WAVE);

		SPRITE_TRAIL_1 = new Sprite("ripple_1", ENTITY_TRAIL);
		SPRITE_TRAIL_2 = new Sprite("ripple_2", ENTITY_TRAIL);
		SPRITE_TRAIL_3 = new Sprite("ripple_3", ENTITY_TRAIL);
		SPRITE_TRAIL_4 = new Sprite("ripple_4", ENTITY_TRAIL);
		SPRITE_TRAIL_5 = new Sprite("ripple_5", ENTITY_TRAIL);
		SPRITE_TRAIL_6 = new Sprite("ripple_6", ENTITY_TRAIL);
		SPRITE_TRAIL_7 = new Sprite("ripple_7", ENTITY_TRAIL);
		SPRITE_TRAIL_8 = new Sprite("ripple_8", ENTITY_TRAIL);
		SPRITE_TRAIL_9 = new Sprite("ripple_9", ENTITY_TRAIL);
		SPRITE_TRAIL_10 = new Sprite("ripple_10", ENTITY_TRAIL);
		SPRITE_TRAIL_11 = new Sprite("ripple_11", ENTITY_TRAIL);
		

		SPRITE_PACKAGE_ICON = new Sprite("package", ENTITY_CARE_PACKAGE);
		SPRITE_PACKAGE_1 = new Sprite("package_1", ENTITY_CARE_PACKAGE);
		SPRITE_PACKAGE_2 = new Sprite("package_2", ENTITY_CARE_PACKAGE);
		SPRITE_PACKAGE_3 = new Sprite("package_3", ENTITY_CARE_PACKAGE);
		SPRITE_PACKAGE_4 = new Sprite("package_4", ENTITY_CARE_PACKAGE);
		SPRITE_PACKAGE_5 = new Sprite("package_5", ENTITY_CARE_PACKAGE);
		
		SPRITE_WINDMILL_1 = new Sprite("windmill_1", ENTITY_WINDMILL);
		SPRITE_WINDMILL_2 = new Sprite("windmill_2", ENTITY_WINDMILL);
		SPRITE_WINDMILL_3 = new Sprite("windmill_3", ENTITY_WINDMILL);
		
		SPRITE_TURRET_1 = new Sprite("turret_1", ENTITY_TURRET);
		SPRITE_TURRET_2 = new Sprite("turret_2", ENTITY_TURRET);
		SPRITE_TURRET_3 = new Sprite("turret_3", ENTITY_TURRET);
		SPRITE_TURRET_4 = new Sprite("turret_4", ENTITY_TURRET);
		SPRITE_TURRET_5 = new Sprite("turret_5", ENTITY_TURRET);
		SPRITE_TURRET_6 = new Sprite("turret_6", ENTITY_TURRET);
		SPRITE_TURRET_7 = new Sprite("turret_7", ENTITY_TURRET);
		SPRITE_TURRET_8 = new Sprite("turret_8", ENTITY_TURRET);
		SPRITE_TURRET_1_FIRE = new Sprite("turret_fire_1", ENTITY_TURRET);
		SPRITE_TURRET_2_FIRE = new Sprite("turret_fire_2", ENTITY_TURRET);

		SPRITE_NAVY_BASE_G_1 = new Sprite("base_green_1", ENTITY_NAVY_BASE);
		SPRITE_NAVY_BASE_G_2 = new Sprite("base_green_2", ENTITY_NAVY_BASE);
		SPRITE_NAVY_BASE_G_3 = new Sprite("base_green_3", ENTITY_NAVY_BASE);
		SPRITE_NAVY_BASE_G_4 = new Sprite("base_green_4", ENTITY_NAVY_BASE);
		SPRITE_NAVY_BASE_R_1 = new Sprite("base_red_1", ENTITY_NAVY_BASE);
		SPRITE_NAVY_BASE_R_2 = new Sprite("base_red_2", ENTITY_NAVY_BASE);
		SPRITE_NAVY_BASE_R_3 = new Sprite("base_red_3", ENTITY_NAVY_BASE);
		SPRITE_NAVY_BASE_R_4 = new Sprite("base_red_4", ENTITY_NAVY_BASE);

		SPRITE_EXPLOSION = new Sprite("explosion", ENTITY_EXPLOSION);
		
		/*SPRITE_STONE_1 = new Sprite("rock1", TILE_STONE);
		SPRITE_STONE_2 = new Sprite("rock2", TILE_STONE);
		SPRITE_STONE_3 = new Sprite("rock3", TILE_STONE);
		SPRITE_STONE_4 = new Sprite("rock4", TILE_STONE);*/
		
		SPRITE_P1_SPAWN = new Sprite("p1spawn", TILE_P1_SPAWN);
		SPRITE_P2_SPAWN = new Sprite("p2spawn", TILE_P2_SPAWN);
		
		SPRITE_WOOD = new Sprite("wood", TILE_WOOD);
		SPRITE_STONE = new Sprite("stone", TILE_STONE);
		SPRITE_METAL = new Sprite("metal", TILE_METAL);
		SPRITE_MARSH = new Sprite("marsh", TILE_MARSH);
		SPRITE_NET = new Sprite("net", TILE_NET);
		SPRITE_BOX = new Sprite("box", TILE_BOX);
		
		//Load GUI elements
		SPRITE_MAP_SMALL = new Sprite("map", GUI_MAP);
		SPRITE_HOST = new Sprite("host", GUI_HOST);
		SPRITE_ENTER = new Sprite("enter", GUI_ENTER);
		SPRITE_SLOT = new Sprite("slot", GUI_SLOT);
		SPRITE_SLOT_NUM = new Sprite("slot_num", GUI_SLOT_NUM);
		SPRITE_SELECTED = new Sprite("selected", GUI_SELECTED);
		SPRITE_MAP_LARGE = new Sprite("largemap", GUI_LARGE_MAP);
		SPRITE_CROSSHAIR = new Sprite("crosshair", GUI_CROSSHAIR);
		SPRITE_DISABLED = new Sprite("disabled", GUI_DISABLED);
		SPRITE_BACKGROUND = new Sprite("backgroundbase", GUI_BACKGROUND);
		SPRITE_BACKGROUND_TOP = new Sprite("backgroundtop", GUI_BACKGROUND_TOP);
		SPRITE_SCAN = new Sprite("radar_scan", GUI_RADAR_SCAN);
		SPRITE_POINTS_BACKGROUND = new Sprite("points_bg", GUI_POINTS_BACKGROUND);
		SPRITE_SHIPS_BACKGROUND = new Sprite("ship_bg", GUI_SHIP_BACKGROUND);
		SPRITE_SHIPCRAFT = new Sprite("shipcraft", GUI_SHIPCRAFT);
		SPRITE_AIRSTRIKE = new Sprite("airstrike", GUI_AIRSTRIKE);
		
		SPRITE_LOG_LG = new Sprite("mesh1", GUI_LOG);
		SPRITE_LOG_SM = new Sprite("mesh_sm", GUI_LOG_BOX);
		
		//Load ocean tiles
		SPRITE_OCEAN_1 = new Sprite("ocean1", BACK_OCEAN_1);
		SPRITE_OCEAN_2 = new Sprite("ocean2", BACK_OCEAN_2);
		SPRITE_OCEAN_3 = new Sprite("ocean3", BACK_OCEAN_3);
		SPRITE_OCEAN_4 = new Sprite("ocean4", BACK_OCEAN_4);
		
		//Blank tile (for level editor)
		SPRITE_BLANK = new Sprite("blank", -1);
		
		SPRITE_DOT_GREEN = new Sprite("greendot", DOT_GREEN);
		SPRITE_DOT_RED = new Sprite("reddot", DOT_RED);
		SPRITE_DOT_BLUE = new Sprite("bluedot", DOT_BLUE);
		
		//Load numbers 
		SPRITE_0 = new Sprite("0", NUM_0);
		SPRITE_1 = new Sprite("1", NUM_1);
		SPRITE_2 = new Sprite("2", NUM_2);
		SPRITE_3 = new Sprite("3", NUM_3);
		SPRITE_4 = new Sprite("4", NUM_4);
		SPRITE_5 = new Sprite("5", NUM_5);
		SPRITE_6 = new Sprite("6", NUM_6);
		SPRITE_7 = new Sprite("7", NUM_7);
		SPRITE_8 = new Sprite("8", NUM_8);
		SPRITE_9 = new Sprite("9", NUM_9);
		
		System.out.println("SpriteSheet: Sprites have been loaded!");
	}

}
