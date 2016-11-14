package ca.nicho.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.FileInputStream;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;

import ca.nicho.client.store.StoreHandler;
import ca.nicho.client.store.StoreHandler.StoreItem;
import ca.nicho.foundation.Game;
import ca.nicho.foundation.Sprite;
import ca.nicho.foundation.SpriteSheet;
import ca.nicho.foundation.entity.Entity;
import ca.nicho.foundation.entity.EntityPlayer;
import ca.nicho.foundation.entity.EntityRadar;
import ca.nicho.foundation.tile.Tile;
import ca.nicho.foundation.world.World;
import javazoom.jl.player.Player;
import log.LogHandler;

public class ClientStart extends JFrame {
	
	public static int tickDelta = 0; //This delta value will control player velocities, no matter the frame rate
	public static boolean DEBUG = false;
	
	public static int FRAME_WIDTH = 1000;
	public static int FRAME_HEIGHT = 600;
	
	public static String host_port = "";
	
	public static SpriteSheet sheet;
	public static StoreHandler store;
	public static ClientStart window;
	public static ClientGameSocket con;
	
	public static LogHandler log;
	
	public static ControlListener listener;
	
	public Screen mainPanel;
	
	public static String HOST = "localhost";
	public static int PORT = 1024;
	
	public static Sprite CURRENT_BACKGROUND_SPRITE;
	
	public static void main(String[] s){
		SpriteSheet.initSprites(); //Load media (sprites, audio, etc) prior to any other content
		Tile.initTiles();
		GamePadListener.init();
		/*Scanner sc = new Scanner(System.in);
		System.out.print("Enter host: ");
		HOST = sc.nextLine();
		System.out.print("Enter port: ");
		PORT = Integer.parseInt(sc.nextLine());
		sc.close();*/
		Game.initWorld();
		store = new StoreHandler();
		log = new LogHandler();
		new Thread(Game.world).start();
		window = new ClientStart();
		window.setVisible(true);
		
		try{
		    FileInputStream fis = new FileInputStream("res/pandemic.mp3");
		    Player playMP3 = new Player(fis);
		    playMP3.play();
		}
		catch(Exception exc){
		    exc.printStackTrace();
		    System.out.println("Failed to play the file.");
		}
		
	}
	
	public static GraphicsEnvironment gfxEnv;
	public static GraphicsDevice gfxDev;
	
	public ClientStart(){
		this.setUndecorated(true);
		gfxEnv = GraphicsEnvironment.getLocalGraphicsEnvironment();
		gfxDev = gfxEnv.getDefaultScreenDevice();
		mainPanel = new Screen();
		listener = new ControlListener();
		this.addKeyListener(listener);
		this.setLayout(new BorderLayout());
		this.getContentPane().add(mainPanel, BorderLayout.CENTER);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.pack();
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		gfxDev.setFullScreenWindow(this);
	}
	
	private class Screen extends JPanel implements Runnable {
		
		public boolean running = true;

		public int playerXRender;
		public int playerYRender;
		
		public BufferedImage screen;
		public int[] pixels;
		
		public int stretchHeight;
		public int stretchWidth;
		public int xOff;
		public int yOff;
		
		public Screen(){
			FRAME_WIDTH = ((int)gfxEnv.getMaximumWindowBounds().getWidth()) / Tile.TILE_DIM * Tile.TILE_DIM; //Will round to nearest tile
			FRAME_HEIGHT = ((int)gfxEnv.getMaximumWindowBounds().getHeight()) / Tile.TILE_DIM * Tile.TILE_DIM;
			this.setBounds(0, 0, FRAME_WIDTH, FRAME_HEIGHT);
			this.setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
			screen = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);	
			this.setBackground(Color.black);
			pixels = ((DataBufferInt)screen.getRaster().getDataBuffer()).getData();
			playerXRender = (FRAME_WIDTH - SpriteSheet.SPRITE_PLAYER.width) / 2;
			playerYRender = (FRAME_HEIGHT - SpriteSheet.SPRITE_PLAYER.height) / 2;
			
			//log initialization
			this.setLayout(null);
			this.add(log);
			log.setSize(SpriteSheet.SPRITE_LOG_1.width, SpriteSheet.SPRITE_LOG_1.height);
			log.setLocation(10, FRAME_HEIGHT-180);
			
			new Thread(this).start();
		}		
		
		@Override
		public void paintComponent(Graphics g){
			super.paintComponent(g);
			updateScreen(g);
			g.setColor(Color.white);
			
			//g.drawImage(screen, xOff / 2, yOff / 2, stretchWidth + xOff, stretchHeight + yOff, null);	
			g.drawImage(screen, 0, 0, null);
			if(con == null){
				g.setFont(new Font("Verdana", Font.BOLD, 25));
				g.drawString(host_port, (this.getWidth() - SpriteSheet.SPRITE_HOST.width) / 2 + 10, 95);
				return;
			}
			
			if(DEBUG){
				g.setColor(Color.white);
				g.drawString("FPS: " + rate, 10, 20);
				if(player != null){
					g.drawString("LOCATION: " + player.locX + " " + player.locY, 10, 35);
					g.drawString("ENTITIES: " + Game.world.entities.size(), 10, 50);
				}
				if(Game.ships != null){
					String ships = "";
					for(int i : Game.ships)
						ships += i + " ";
					g.drawString("SHIPS: " + ships , 10, 65);
				}
				g.drawString("PLAYER ID: " + Game.ownerID, 10, 80);
				for(Map.Entry<Integer, Entity> ent : Game.world.entities.entrySet()){
					if(ent.getValue() == player){
						g.setColor(Color.green);
						g.drawString(ent.getKey() + "", playerXRender, playerYRender - 10);
					}else{
						int deltaX = (int)(ent.getValue().locX - player.locX);
						int deltaY = (int)(ent.getValue().locY - player.locY);
						int x = deltaX + playerXRender;
						int y = deltaY + playerYRender;
						if(Game.isIDPlayer(ent.getValue().id))
							g.setColor(Color.blue);
						else
							g.setColor(Color.white);
						
						g.drawString(ent.getKey() + " " + ent.getValue().owner, x, y);
						
						if(ent instanceof EntityRadar){
							g.drawString(((EntityRadar)ent).count + "", x + 50, y);
						}
					}
				}
			}
		}
		
		/**
		 * Draw an entity to the screen
		 * @param ent the entity to be drawn
		 */
		public void drawEntity(Entity ent){
			int ind = 0;
			if(ent == null || ent.sprites[ent.current] == null)
				return; //Prevent crashes if sprite is not loaded
			if(ent == player)
				drawPlayerSprite(); //Special case when drawing player sprite
			else
				drawSprite((int)ent.locX, (int)ent.locY, ent.sprites[ent.current]);
		}
		
		/**
		 * Draw a tile to the screen
		 * @param pos the tile's position in the map array
		 * @param tile the tile to be drawn
		 */
		public void drawTile(int pos, Tile tile){
			if(tile.sprites[tile.current] != null){
				int x = pos % World.MAP_WIDTH;
				int y = pos / World.MAP_WIDTH;
				drawSprite(x * Tile.TILE_DIM, y * Tile.TILE_DIM, tile.sprites[tile.current]);
			}
		}
		
		/**
		 * Draw a sprite to the screen by position
		 * @param x1
		 * @param y1
		 * @param sprite the sprite to be drawn
		 */
		public void drawSprite(int x1, int y1, Sprite sprite){
			int ind = 0;
			int deltaX = x1 - (int)player.locX;
			int deltaY = y1 - (int)player.locY;
			x1 = deltaX + playerXRender;
			y1 = deltaY + playerYRender;
			for(int i = 0; i < sprite.height; i++){
				for(int o = 0; o < sprite.width; o++){
					int x2 = x1 + o;
					int y2 = y1 + i;
					drawPixel(x2, y2, sprite.data[ind]);
					ind ++;
				}
			}
		}
		
		public void drawGUISprite(int x1, int y1, Sprite sprite){
			int ind = 0;
			for(int i = 0; i < sprite.height; i++){
				for(int o = 0; o < sprite.width; o++){
					int x = x1 + o;
					int y = y1 + i;
					drawPixel(x, y, sprite.data[ind]);
					ind ++;
				}
			}
		}
		
		/**
		 * Draw the player sprite. Needs a special method for this since it is
		 * always centered in the screen
		 */
		public void drawPlayerSprite(){
			if(player != null){
				int x1 = playerXRender;
				int y1 = playerYRender;
				int ind = 0;
				for(int i = 0; i < player.sprites[player.current].height; i++){
					for(int o = 0; o < player.sprites[player.current].width; o++){
						int x2 = x1 + o;
						int y2 = y1 + i;
						drawPixel(x2, y2, player.sprites[player.current].data[ind]);
						ind ++;
					}
				}
			}
		}
				
		/**
		 * Draw a pixel to the screen based on x and y positions
		 * @param x
		 * @param y
		 * @param color the color integer for this location
		 */
		public void drawPixel(int x, int y, int color){
			
			//Don't render pixel if it is the invisible color #FF00FF or off screen
			if(color == SpriteSheet.INVISIBLE_COLOR || x >= FRAME_WIDTH || x < 0 || y >= FRAME_HEIGHT || y < 0){
				return;
			}
			pixels[x + y * FRAME_WIDTH] = color;
		}
		
		public void updateOceanTile(){
			if(CURRENT_BACKGROUND_SPRITE == SpriteSheet.SPRITE_OCEAN_1){
				CURRENT_BACKGROUND_SPRITE = SpriteSheet.SPRITE_OCEAN_2;
			}else if(CURRENT_BACKGROUND_SPRITE == SpriteSheet.SPRITE_OCEAN_2){
				CURRENT_BACKGROUND_SPRITE = SpriteSheet.SPRITE_OCEAN_3;
			}else if(CURRENT_BACKGROUND_SPRITE == SpriteSheet.SPRITE_OCEAN_3){
				CURRENT_BACKGROUND_SPRITE = SpriteSheet.SPRITE_OCEAN_4;
			}else if(CURRENT_BACKGROUND_SPRITE == SpriteSheet.SPRITE_OCEAN_4 || CURRENT_BACKGROUND_SPRITE == null){
				CURRENT_BACKGROUND_SPRITE = SpriteSheet.SPRITE_OCEAN_1;
			}
		}
		
		public EntityPlayer player;
		public int backTick = 0;
		/**
		 * Called whenever there is a frame update. It will handle all necessary rendering
		 */
		public void updateScreen(Graphics g){

			//Update ocean tile if need be
			if(backTick == 0){
				updateOceanTile();
			}
			backTick = (backTick + 1) % 10; //Loop at every 50 ticks
			
			//Draw main menu
			if(con == null){
				//Draw background without player locations
				for(int x = 0; x < FRAME_WIDTH / 50 + 1; x++){
					for(int y = 0; y < FRAME_HEIGHT / 50 + 1; y++){
						this.drawGUISprite(x * 50, y * 50, CURRENT_BACKGROUND_SPRITE);
					}
				}
				
				this.drawGUISprite((this.getWidth() - SpriteSheet.SPRITE_HOST.width) / 2, 50, SpriteSheet.SPRITE_HOST);
				this.drawGUISprite((this.getWidth() - SpriteSheet.SPRITE_ENTER.width) / 2, 150, SpriteSheet.SPRITE_ENTER);
			}
			
			//Set the current player for this update
			player = Game.world.getPlayer();
			
			//Second check. If it is still null, don't render
			if(player == null)
				return;
		
		
			//Draw background tiles
			for(int x = 0; x < FRAME_WIDTH / 50 + 1; x++){
				for(int y = 0; y < FRAME_HEIGHT / 50 + 1; y++){
					this.drawGUISprite(x * 50 - (int)player.locX % 50, y * 50 - (int)player.locY % 50, CURRENT_BACKGROUND_SPRITE);
				}
			}
			
			
			if(Game.world != null){
				//Render entities
				for(Map.Entry<Integer, Entity> set : Game.world.entities.entrySet()){
					drawEntity(set.getValue());
				}
				
				//Render tiles
				for(int pos = 0; pos < Game.world.map.length; pos++){
					Tile tile = Game.world.map[pos];
					if(tile != null){
						drawTile(pos, tile);
					}
				}
			}
			
			//Load overlays last
			if(!DEBUG)
				drawGUISprite(10, 10, SpriteSheet.SPRITE_MAP_SMALL);
			
			//Draw the ships on the map
			for(int i = 0; i < Game.ships.length; i++){
				Entity e = Game.world.entities.get(Game.ships[i]);
				if(e == player){
					int x = i * 50 + 200;
					this.drawGUISprite(x, 10, e.sprites[e.current]);
					this.drawGUISprite(x, 10, SpriteSheet.SPRITE_DOT_GREEN);
					drawGUISprite(10 + (int)(e.locX / (World.MAP_WIDTH * Tile.TILE_DIM) * 100), 10 + (int)(e.locY / (World.MAP_HEIGHT * Tile.TILE_DIM) * 100), SpriteSheet.SPRITE_DOT_GREEN);
				}else{
					int x = i * 50 + 200;
					this.drawGUISprite(x, 10, e.sprites[e.current]);
					this.drawGUISprite(x, 10, SpriteSheet.SPRITE_DOT_BLUE);
					drawGUISprite(10 + (int)(e.locX / (World.MAP_WIDTH * Tile.TILE_DIM) * 100), 10 + (int)(e.locY / (World.MAP_HEIGHT * Tile.TILE_DIM) * 100), SpriteSheet.SPRITE_DOT_BLUE);	
				}
			}
			
			for(Map.Entry<Integer, Entity> set : Game.world.entities.entrySet()) {
				Entity e = set.getValue();
				if(e.detected){
					System.out.println(e.id + " " + e.owner + " " + e.detected);
					drawGUISprite(10 + (int)(e.locX / (World.MAP_WIDTH * Tile.TILE_DIM) * 100), 10 + (int)(e.locY / (World.MAP_HEIGHT * Tile.TILE_DIM) * 100), SpriteSheet.SPRITE_DOT_RED);	
				}
			}
			
			for(int i = 0; i < player.inventory.length; i++){
				Entity e = player.inventory[i];
				int guiX = FRAME_WIDTH - 100 - i * 95;
				int guiY = FRAME_HEIGHT - 100;
				if(player.position == i)
					drawGUISprite(guiX, guiY, SpriteSheet.SPRITE_SELECTED);
				else
					drawGUISprite(guiX, guiY, SpriteSheet.SPRITE_SLOT);
				if(e != null){
					drawGUISprite(guiX + 20, guiY + 20, e.sprites[e.current]);
				}
			}

			this.drawGUISprite(log.getX(), log.getY(), SpriteSheet.SPRITE_LOG_1);	
			//Load store overlays
			if(StoreHandler.isOpen){
				int storeIndex = 0;
				for(StoreItem item: store.costs){
					Entity e = item.entity;
					int leftX =  FRAME_WIDTH - 195 + 95 * (storeIndex % 2);
					int leftY = (storeIndex / 2) * 95;
					int x = (100 - e.sprites[e.current].width) / 2 + leftX;
					int y = (100 - e.sprites[e.current].height) / 2 + leftY;
					if(store.position != storeIndex)
						this.drawGUISprite(leftX, leftY, SpriteSheet.SPRITE_SLOT);
					else
						this.drawGUISprite(leftX, leftY, SpriteSheet.SPRITE_SELECTED);
					this.drawGUISprite(x, y, e.sprites[e.current]);
					this.drawGUISprite(leftX + 7, leftY + 7, item.sprite);
					//g.drawString(set.getValue() + "", leftX + 5, leftY + 5); -> Needs to find a way to right the text onto the physical screen
					storeIndex ++;
				}
			}
			this.drawGUISprite(10, 120, new Sprite(Game.points));		
			
		}
		
		public int rate;
		
		@Override
		public void run(){
			
			long last = System.currentTimeMillis();
			
			while(running){
				long current = System.currentTimeMillis();
				tickDelta = (int)(current - last);
				if(tickDelta >= 0){
					
					//Poll the controller
					GamePadListener.tick();
					
					rate = (int)(1000f / (current - last));
					last = current;
					
					//Update game screen
					if(listener != null){
						listener.tick();
					}
					this.repaint();
				}
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
		
	}
	
}
