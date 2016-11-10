package ca.nicho.client.tile;

import java.util.HashMap;

import ca.nicho.client.Sprite;
import ca.nicho.client.SpriteSheet;

public class Tile {

	public static final int TILE_DIM = 60; 	//Convention states that all tiles should be 40x40 pixels
											//Any other size should consider using entities
	
	public static Tile TILE_WOOD;
	public static Tile TILE_STONE;
	public static Tile TILE_METAL;
	
	public Sprite sprite;
	
	public Tile(Sprite sprite){
		this.sprite = sprite;
	}
	
	public String toString(){
		return sprite.name;
	}
	
	public static HashMap<Integer, Tile> tiles = new HashMap<Integer, Tile>();
	
	public static void initTiles(){
		TILE_WOOD = new Tile(SpriteSheet.SPRITE_WOOD);
		TILE_STONE = new Tile(SpriteSheet.SPRITE_STONE);
		TILE_METAL = new Tile(SpriteSheet.SPRITE_METAL);
		
		tiles.put(SpriteSheet.TILE_WOOD, TILE_WOOD);
		tiles.put(SpriteSheet.TILE_STONE, TILE_STONE);
		tiles.put(SpriteSheet.TILE_METAL, TILE_METAL);
	}
	
	public static Tile getTileByID(int ID){
		return tiles.get(ID);
	}
	
}
