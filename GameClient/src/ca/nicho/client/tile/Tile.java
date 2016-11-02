package ca.nicho.client.tile;

import ca.nicho.client.Sprite;
import ca.nicho.client.SpriteSheet;

public class Tile {

	public static final int TILE_DIM = 40; 	//Convention states that all tiles should be 40x40 pixels
											//Any other size should consider using entities
	
	public static Tile TILE_WOOD;
	public static Tile TILE_STONE;
	
	public Sprite sprite;
	
	public Tile(Sprite sprite){
		this.sprite = sprite;
	}
	
	public static void initTiles(){
		TILE_WOOD = new Tile(SpriteSheet.SPRITE_WOOD);
		TILE_STONE = new Tile(SpriteSheet.SPRITE_STONE);
	}
	
	public static Tile getTileByID(int ID){
		switch(ID){
			case SpriteSheet.TILE_WOOD:
				return TILE_WOOD;
			case SpriteSheet.TILE_STONE:
				return TILE_STONE;
		}
		return null;
	}
	
}
