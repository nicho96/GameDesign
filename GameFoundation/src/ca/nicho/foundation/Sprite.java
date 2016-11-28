package ca.nicho.foundation;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Sprite { 
	public String name;
	public int[] data;
	public int width = 0;
	public int height = 0;
	public int type;
	
	public Sprite(float health, boolean somethingtodistinguishfromnum){
		width = 50;
		height = 4;
		data = new int[width * height];
	
		int color = 0x00FF00;
		if(health < 0.5)
			color = 0xFF0000;
		
		int pos = (int)(health * 50);
		
		for(int i = 0; i < data.length; i++){
			int val = i % width;
			if(val <= pos)
				data[i] = color;
			else
				data[i] = 0x000000;
		}
	}
	
	public Sprite(int num){
		String numS = num + "";
		this.width = SpriteSheet.SPRITE_0.width * numS.length();
		this.height = SpriteSheet.SPRITE_0.height;
		this.data = new int[width * height];
		this.type = num;
		int ind = 0;
		for(char c : numS.toCharArray()){
			Sprite cur = null;
			switch(c){
				case '0':
					cur = SpriteSheet.SPRITE_0;
					break;
				case '1':
					cur = SpriteSheet.SPRITE_1;
					break;
				case '2':
					cur = SpriteSheet.SPRITE_2;
					break;
				case '3':
					cur = SpriteSheet.SPRITE_3;
					break;
				case '4':
					cur = SpriteSheet.SPRITE_4;
					break;
				case '5':
					cur = SpriteSheet.SPRITE_5;
					break;
				case '6':
					cur = SpriteSheet.SPRITE_6;
					break;
				case '7':
					cur = SpriteSheet.SPRITE_7;
					break;
				case '8':
					cur = SpriteSheet.SPRITE_8;
					break;
				case '9':
					cur = SpriteSheet.SPRITE_9;
					break;
			}
			int j = 0;
			for(int o = 0; o < cur.height; o++){
				for(int i = 0; i < cur.width; i++){
					data[ind * cur.width + i + o * width] = cur.data[j];
					j++;
				}
			}
			ind++;
			
		}
	}
	
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