package ca.nicho.client;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class SpriteSheet {
	
	//Reserve 0-99 for entities
	public static final int ENTITY_PLAYER = 0;
	public static final int ENTITY_ENEMY = 1;
	public static final int ENTITY_BATTLESHIP = 2;
	public static final int ENTITY_MISSILE = 3;
	public static final int ENTITY_MEDIC = 4;
	public static final int ENTITY_CARGO = 5;
	public static final int ENTITY_RADAR = 6;
	
	//Reserve 100-199 for Tiles
	public static final int TILE_WOOD = 100;
	public static final int TILE_STONE = 101;
	
	//Reserve 200-299 for GUI elements (may need to extend in future
	public static final int GUI_MAP = 200;
	public static final int GUI_HOST = 201;
	public static final int GUI_ENTER = 202;
	public static final int GUI_SLOT = 203;
	
	//Reserver 500+ for generic sprites
	public static final int BACK_OCEAN_1 = 500;
	public static final int BACK_OCEAN_2 = 501;
	public static final int BACK_OCEAN_3 = 502;
	public static final int BACK_OCEAN_4 = 503;
	
	public static final int DOT_GREEN = 504;
	public static final int DOT_RED = 505;
	public static final int DOT_BLUE = 506;
	
	public static final int INVISIBLE_COLOR = 0xFFFF00FF;
	
	public static Sprite SPRITE_PLAYER;
	public static Sprite SPRITE_ENEMY;
	public static Sprite SPRITE_BATTLESHIP;
	public static Sprite SPRITE_MISSILE;
	public static Sprite SPRITE_MEDIC;
	public static Sprite SPRITE_CARGO;
	public static Sprite SPRITE_RADAR;
	
	public static Sprite SPRITE_WOOD;
	public static Sprite SPRITE_STONE;
	
	//GUI
	public static Sprite SPRITE_MAP_SMALL;
	public static Sprite SPRITE_HOST;
	public static Sprite SPRITE_ENTER;
	public static Sprite SPRITE_SLOT;
	
	//Ocean
	public static Sprite SPRITE_OCEAN_1;
	public static Sprite SPRITE_OCEAN_2;
	public static Sprite SPRITE_OCEAN_3;
	public static Sprite SPRITE_OCEAN_4;
	
	//Dots
	public static Sprite SPRITE_DOT_GREEN;
	public static Sprite SPRITE_DOT_RED;
	public static Sprite SPRITE_DOT_BLUE;
	
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
		SPRITE_MEDIC = new Sprite("medic", ENTITY_MEDIC);
		SPRITE_CARGO = new Sprite("cargo", ENTITY_CARGO);
		SPRITE_RADAR = new Sprite("radar", ENTITY_RADAR);
		
		SPRITE_WOOD = new Sprite("wood", TILE_WOOD);
		SPRITE_STONE = new Sprite("stone", TILE_STONE);
		
		//Load GUI elements
		SPRITE_MAP_SMALL = new Sprite("map", GUI_MAP);
		SPRITE_HOST = new Sprite("host", GUI_HOST);
		SPRITE_ENTER = new Sprite("enter", GUI_ENTER);
		SPRITE_SLOT = new Sprite("slot", GUI_SLOT);
		
		//Load ocean tiles
		SPRITE_OCEAN_1 = new Sprite("ocean1", BACK_OCEAN_1);
		SPRITE_OCEAN_2 = new Sprite("ocean2", BACK_OCEAN_2);
		SPRITE_OCEAN_3 = new Sprite("ocean3", BACK_OCEAN_3);
		SPRITE_OCEAN_4 = new Sprite("ocean4", BACK_OCEAN_4);
		
		SPRITE_DOT_GREEN = new Sprite("greendot", DOT_GREEN);
		SPRITE_DOT_RED = new Sprite("reddot", DOT_RED);
		SPRITE_DOT_BLUE = new Sprite("bluedot", DOT_BLUE);

		System.out.println("SpriteSheet: Sprites have been loaded!");
	}
	
	public class Sprite { 
		public String name;
		public int[] data;
		public int width = 0;
		public int height = 0;
		public int type;
		
		public Sprite(String name, int type){
			this.name = name;
			this.type = type;
			File f = new File("res/" + name + ".png");
			if(f.exists()){
				try {
					BufferedImage sprite = ImageIO.read(f);
					data = new int[sprite.getWidth() * sprite.getHeight()];
					int ind = 0;
					for(int i = 0; i < sprite.getHeight(); i++){
						for(int o = 0; o < sprite.getWidth(); o++){
							int pixel = sprite.getRGB(o, i);							
							data[ind] = pixel;
							ind++;
						}
					}
					width = sprite.getWidth();
					height = sprite.getHeight();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}else{
				System.out.println("SpriteSheet: Sprite named " + name + " does not exist.");
			}
		}
	}	
}
