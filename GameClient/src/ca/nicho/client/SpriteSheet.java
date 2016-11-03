package ca.nicho.client;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class SpriteSheet {
	
	//Reserve 0-299 for entities
	public static final int ENTITY_PLAYER = 0;
	public static final int ENTITY_ENEMY = 4;
	public static final int ENTITY_BATTLESHIP = 8;
	public static final int ENTITY_MISSILE = 12;
	public static final int ENTITY_MEDIC = 16;
	public static final int ENTITY_CARGO = 20;
	public static final int ENTITY_RADAR = 24;
	
	//Reserve 300-399 for Tiles
	public static final int TILE_WOOD = 300;
	public static final int TILE_STONE = 301;
	
	//Reserve 400-499 for GUI elements (may need to extend in future
	public static final int GUI_MAP = 400;
	public static final int GUI_HOST = 401;
	public static final int GUI_ENTER = 402;
	public static final int GUI_SLOT = 403;
	public static final int GUI_EPIC_THING = 404;
	
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
	public static Sprite SPRITE_CARGO;
	public static Sprite SPRITE_RADAR;
	
	public static Sprite SPRITE_WOOD;
	public static Sprite SPRITE_STONE;
	
	//GUI
	public static Sprite SPRITE_MAP_SMALL;
	public static Sprite SPRITE_HOST;
	public static Sprite SPRITE_ENTER;
	public static Sprite SPRITE_SLOT;
	public static Sprite SPRITE_EPIC_THING;
	
	//Ocean
	public static Sprite SPRITE_OCEAN_1;
	public static Sprite SPRITE_OCEAN_2;
	public static Sprite SPRITE_OCEAN_3;
	public static Sprite SPRITE_OCEAN_4;
	
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
		SPRITE_BATTLESHIP = new Sprite("battleship", ENTITY_BATTLESHIP);
		SPRITE_MISSILE = new Sprite("missile", ENTITY_MISSILE);

		SPRITE_MEDIC_1 = new Sprite("medic1", ENTITY_MEDIC);
		SPRITE_MEDIC_2 = new Sprite("medic2", ENTITY_MEDIC);
		SPRITE_MEDIC_3 = new Sprite("medic3", ENTITY_MEDIC);
		SPRITE_MEDIC_4 = new Sprite("medic4", ENTITY_MEDIC);
		
		SPRITE_CARGO = new Sprite("cargo", ENTITY_CARGO);
		SPRITE_RADAR = new Sprite("radar", ENTITY_RADAR);
		
		SPRITE_WOOD = new Sprite("wood", TILE_WOOD);
		SPRITE_STONE = new Sprite("stone", TILE_STONE);
		
		//Load GUI elements
		SPRITE_MAP_SMALL = new Sprite("map", GUI_MAP);
		SPRITE_HOST = new Sprite("host", GUI_HOST);
		SPRITE_ENTER = new Sprite("enter", GUI_ENTER);
		SPRITE_SLOT = new Sprite("slot", GUI_SLOT);
		SPRITE_EPIC_THING = new Sprite("epic_thing", GUI_EPIC_THING);
		
		//Load ocean tiles
		SPRITE_OCEAN_1 = new Sprite("ocean1", BACK_OCEAN_1);
		SPRITE_OCEAN_2 = new Sprite("ocean2", BACK_OCEAN_2);
		SPRITE_OCEAN_3 = new Sprite("ocean3", BACK_OCEAN_3);
		SPRITE_OCEAN_4 = new Sprite("ocean4", BACK_OCEAN_4);
		
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
