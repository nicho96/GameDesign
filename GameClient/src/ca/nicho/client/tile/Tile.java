package ca.nicho.client.tile;

import ca.nicho.client.SpriteSheet;
import ca.nicho.client.SpriteSheet.Sprite;

public class Tile {

	public static final int TILE_DIM = 40; 	//Convention states that all tiles should be 40x40 pixels
											//Any other size should consider using entities
	
	public static final Tile TILE_WOOD = new Tile(SpriteSheet.SPRITE_WOOD);
	public static final Tile TILE_STONE = new Tile(SpriteSheet.SPRITE_STONE);
	
	public Sprite sprite;
	
	public Tile(Sprite sprite){
		this.sprite = sprite;
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
