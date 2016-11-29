package ca.nicho.foundation.tile;

import java.util.HashMap;
import java.util.Map;

import ca.nicho.foundation.Sprite;
import ca.nicho.foundation.SpriteSheet;

public class Tile {

	public static final int TILE_DIM = 60; 	//Convention states that all tiles should be 40x40 pixels
											//Any other size should consider using entities
	public static Tile TILE_P1_SPAWN;
	public static Tile TILE_P2_SPAWN;
	
	public static Tile TILE_WOOD;
	public static Tile TILE_STONE;
	public static Tile TILE_METAL;
	public static Tile TILE_MARSH;
	public static Tile TILE_NET;
	public static Tile TILE_BOX;
	
	public static Tile TILE_ROCK;
	
	//private static final Sprite[] rockSprites = {SpriteSheet.SPRITE_STONE_1, SpriteSheet.SPRITE_STONE_2, SpriteSheet.SPRITE_STONE_3, SpriteSheet.SPRITE_STONE_4};
	
	public Sprite[] sprites;
	public int current = 0;
	
	public Tile(Sprite sprite){
		sprites = new Sprite[1];
		this.sprites[0] = sprite;
	}
	
	public Tile(Sprite[] sprites){
		this.sprites = sprites;
	}
	
	public String toString(){
		return sprites[0].name;
	}
	
	public static HashMap<Integer, Tile> tiles = new HashMap<Integer, Tile>();
	
	public static void initTiles(){
		
		TILE_P1_SPAWN = new Tile(SpriteSheet.SPRITE_P1_SPAWN);
		TILE_P2_SPAWN = new Tile(SpriteSheet.SPRITE_P2_SPAWN);
		
		TILE_WOOD = new Tile(SpriteSheet.SPRITE_WOOD);
		
		TILE_STONE = new Tile(SpriteSheet.SPRITE_STONE);
		TILE_METAL = new Tile(SpriteSheet.SPRITE_METAL);
		TILE_MARSH = new Tile(SpriteSheet.SPRITE_MARSH);
		TILE_NET = new Tile(SpriteSheet.SPRITE_NET);
		TILE_BOX = new Tile(SpriteSheet.SPRITE_BOX);
		
		tiles.put(SpriteSheet.TILE_P1_SPAWN, TILE_P1_SPAWN);
		tiles.put(SpriteSheet.TILE_P2_SPAWN, TILE_P2_SPAWN);

		tiles.put(SpriteSheet.TILE_WOOD, TILE_WOOD);
		tiles.put(SpriteSheet.TILE_STONE, TILE_STONE);
		tiles.put(SpriteSheet.TILE_METAL, TILE_METAL);
		tiles.put(SpriteSheet.TILE_MARSH, TILE_MARSH);
		tiles.put(SpriteSheet.TILE_NET, TILE_NET);
		tiles.put(SpriteSheet.TILE_BOX, TILE_BOX);
		
	}
	
	public static Tile getTileByID(int ID){
		return tiles.get(ID);
	}
	
	int updateTick = 0;
	public void tick(){
		updateTick++;
		if(updateTick == 10){
			updateTick = 0;
			current = (current + 1) % sprites.length;
		}
	}
	
	public static void update(){
		for(Map.Entry<Integer, Tile> set : Tile.tiles.entrySet()){
			set.getValue().tick();
		}
	}
	
}
