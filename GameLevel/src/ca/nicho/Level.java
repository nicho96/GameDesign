package ca.nicho;

import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import ca.nicho.client.tile.Tile;

public class Level {

	public Tile[][] map;
	
	public Level(){
		map = new Tile[100][100];
		for(int i = 0; i < map.length; i++){
			for(int o = 0; o < map[0].length; o++){
				if(i == 0 || i == map.length - 1 || o == 0 || o == map[0].length)
					map[i][o] = Tile.TILE_METAL;
			}
		}
	}
	
	public void changeDimensions(int width, int height){
		
		Tile[][] tmp = new Tile[width][height];
		
		for(int x = 0; x < width && x < map.length; x++){
			for(int y = 0; y < height && y < map[0].length; y++){
				tmp[x][y] = map[x][y];
			}
		}
		map = tmp;
		
	}
	
	public void setTile(Tile t, int x, int y){
		map[x][y] = t;
	}
	
	public void save(File f){
		
		try {
			DataOutputStream out = new DataOutputStream(new FileOutputStream(f));
			
			out.writeInt(map.length);
			out.writeInt(map[0].length);
			
			for(int x = 0; x < map.length; x++){
				for(int y = 0; y < map[0].length; y++){
					Tile t = map[x][y];
					if(t != null){
						out.writeInt(t.sprite.type);
					}else{
						out.writeInt(-1);
					}
				}
			}
			
			out.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void load(File f){
		
		try {
			DataInputStream in = new DataInputStream(new FileInputStream(f));
			
			int width = in.readInt();
			int height = in.readInt();
			
			System.out.println(width);
			System.out.println(height);
			
			map = new Tile[width][height];
			
			for(int x = 0; x < width; x++){
				for(int y = 0; y < height; y++){
					int type = in.readInt();
					map[x][y] = Tile.getTileByID(type);
				}
			}
			
			in.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public BufferedImage generateBufferedImage(){
		
		int width = map.length;
		int height = map[0].length;
		
		BufferedImage image = new BufferedImage(width * Tile.TILE_DIM, height * Tile.TILE_DIM, BufferedImage.TYPE_INT_RGB);
		
		for(int i = 0; i < width; i++){
			for(int o = 0; o < height; o++){
				Tile t = map[i][o];
				if(t != null){
					int ind = 0;
					for(int x = 0; x < t.sprite.width; x++){
						for(int y = 0; y < t.sprite.height; y++){
							image.setRGB(x + i * Tile.TILE_DIM, y + o * Tile.TILE_DIM, t.sprite.data[ind]);
							ind++;
						}
					}
				}
			}
		}
		
		return image;
	}
	
}
