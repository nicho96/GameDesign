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
	
	//Reserve 100-199 for Tiles
	public static final int TILE_WOOD = 100;
	public static final int TILE_STONE = 101;
	
	public static final int INVISIBLE_COLOR = 0xFFFF00FF;
	
	public static Sprite SPRITE_PLAYER;
	public static Sprite SPRITE_ENEMY;
	public static Sprite SPRITE_BATTLESHIP;
	
	public static Sprite SPRITE_WOOD;
	public static Sprite SPRITE_STONE;
	
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
		
		SPRITE_WOOD = new Sprite("wood", TILE_WOOD);
		SPRITE_STONE = new Sprite("stone", TILE_STONE);
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
