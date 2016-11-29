package ca.nicho;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import ca.nicho.client.GamePadListener;
import ca.nicho.foundation.SpriteSheet;
import ca.nicho.foundation.tile.Tile;
import ca.nicho.foundation.world.World;

public class LevelStart extends JFrame {

	public static final int FRAME_WIDTH = 1800;
	public static final int FRAME_HEIGHT = 1000;
	
	public static Level level;
	public static int locX = 0;
	public static int locY = 0;
	public static int centerX;
	public static int centerY;
	
	public static Tile selectedTile = null;
	
	public static Screen mainPanel;
	
	public static void main(String[] s){
		SpriteSheet.initSprites();
		Tile.initTiles();
		level = new Level();
		LevelStart start = new LevelStart();
		start.loadTools();
		start.setVisible(true);
	}
	
	public LevelStart(){
		mainPanel = new Screen();
		centerX = FRAME_WIDTH / 2;
		centerY = FRAME_HEIGHT / 2;
		this.setLayout(new BorderLayout());
		this.getContentPane().add(mainPanel, BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.setBounds(FRAME_WIDTH, 0, 100, 100);
		this.getContentPane().add(panel, BorderLayout.EAST);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.addKeyListener(new Listener());
		this.addMouseListener(new Mouse());
		
		this.pack();
		
	}
	
	public void loadTools(){
		new Tools();
	}
	
	public class Tools extends JFrame {
		
		public Tools(){
			this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			this.setBounds(FRAME_WIDTH, 0, 200, FRAME_HEIGHT);
			this.setLayout(null);
			
			JComboBox<Tile> box = new JComboBox<Tile>();
			JLabel icon = new JLabel();
			icon.setBounds(110, 0, 40, 40);
			
			box.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					Tile t = (Tile)box.getSelectedItem();
					selectedTile = t;
					icon.setIcon(new ImageIcon("res/" + t + ".png"));
				}
			});
			box.setBounds(0, 0, 100, 20);
			for(Map.Entry<Integer, Tile> set : Tile.tiles.entrySet()){
				Tile t = set.getValue();
				box.addItem(t);
			}
						
			JButton save = new JButton("Save");
			save.setBounds(0, 50, 80, 25);
			save.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					JFileChooser chooser = new JFileChooser();
					chooser.setCurrentDirectory(new File("level/"));
					chooser.showSaveDialog(null);
					File f = chooser.getSelectedFile();
					if(f != null){
						level.save(f);
					}
				}
			});
			
			JButton load = new JButton("Load");
			load.setBounds(85, 50, 80, 25);
			load.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					JFileChooser chooser = new JFileChooser();
					chooser.setCurrentDirectory(new File("level/"));
					chooser.showOpenDialog(null);
					File f = chooser.getSelectedFile();
					if(f != null){
						level.load(f);
					}
				}
			});
			
			JButton image = new JButton("Generate Image");
			image.setBounds(0, 80, 180, 25);
			image.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					JFileChooser chooser = new JFileChooser();
					chooser.setCurrentDirectory(new File("level/"));
					chooser.showSaveDialog(null);
					File f = chooser.getSelectedFile();
					if(f != null){
						BufferedImage img = level.generateBufferedImage();
						try {
							ImageIO.write(img, "png", f);
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
				}
			});
			
			this.getContentPane().add(box);
			this.getContentPane().add(icon);
			this.getContentPane().add(save);
			this.getContentPane().add(load);
			this.getContentPane().add(image);
			
			this.setVisible(true);
			
		}
		
	}
	
	public class Screen extends JPanel implements Runnable{
		
		public BufferedImage screen;
		public int[] pixels;
		
		public Screen(){
			this.setBounds(0, 0, FRAME_WIDTH, FRAME_HEIGHT);
			this.setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
			screen = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
			pixels = ((DataBufferInt)screen.getRaster().getDataBuffer()).getData();
			new Thread(this).start();
		}
	
		
		@Override
		public void paintComponent(Graphics g){
			
			for(int i = 0; i < FRAME_WIDTH; i++){
				for(int o = 0; o < FRAME_HEIGHT; o++){
					this.drawPixel(i, o, 0xDEDEDE);
				}
			}
			
			for(int x = 0; x < level.map.length; x++){
				for(int y = 0; y < level.map[0].length; y++){
					Tile t = level.map[x][y];
					if(t != null)
						this.drawTile(x + y * level.map.length, t);
					else
						this.drawSprite(x * Tile.TILE_DIM, y * Tile.TILE_DIM, SpriteSheet.SPRITE_BLANK);
				}
			}
			
			for(int i = 0; i < FRAME_WIDTH; i++){
				for(int o = 0; o < FRAME_HEIGHT; o++){
					if(i % Tile.TILE_DIM == 0 || o % Tile.TILE_DIM == 0){
						this.drawPixel(i + centerX % Tile.TILE_DIM, o + centerY % Tile.TILE_DIM, 0x000000);
					}
				}
			}
			
			g.drawImage(screen, 0, 0, null);
		}
		
		/**
		 * Draw a sprite to the screen by position
		 * @param x1
		 * @param y1
		 * @param sprite the sprite to be drawn
		 */
		public void drawTile(int pos, Tile tile){
			if(tile.sprites[0] != null){
				int x = pos % World.MAP_WIDTH;
				int y = pos / World.MAP_WIDTH;
				drawSprite(x * Tile.TILE_DIM, y * Tile.TILE_DIM, tile.sprites[0]);
			}
		}
		
		/**
		 * Draw a sprite to the screen by position
		 * @param x1
		 * @param y1
		 * @param sprite the sprite to be drawn
		 */
		public void drawSprite(int x1, int y1, ca.nicho.foundation.Sprite sprite){
			int ind = 0;
			int deltaX = x1 - locX;
			int deltaY = y1 - locY;
			x1 = deltaX + centerX;
			y1 = deltaY + centerY;
			for(int i = 0; i < sprite.height; i++){
				for(int o = 0; o < sprite.width; o++){
					int x2 = x1 + o;
					int y2 = y1 + i;
					drawPixel(x2, y2, sprite.data[ind]);
					ind ++;
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
		
		public int rate;
		public int tickDelta;
		public boolean running = true;
		
		@Override
		public void run(){
			
			long last = System.currentTimeMillis();
			
			while(running){
				long current = System.currentTimeMillis();
				tickDelta = (int)(current - last);
				if(tickDelta >= 10){
					
					//Poll the controller
					GamePadListener.tick();
					
					rate = (int)(1000f / (current - last));
					last = current;
					
					//Update game screen
					//if(listener != null){
						//listener.tick();
					//}
					this.repaint();
				}
			}
			
		}
		
		
	}

	public class Listener implements KeyListener {

		@Override
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_UP){
				LevelStart.locY -= Tile.TILE_DIM;
			}else if(e.getKeyCode() == KeyEvent.VK_DOWN){
				LevelStart.locY += Tile.TILE_DIM;
			}else if(e.getKeyCode() == KeyEvent.VK_LEFT){
				LevelStart.locX -= Tile.TILE_DIM;
			}else if(e.getKeyCode() == KeyEvent.VK_RIGHT){
				LevelStart.locX += Tile.TILE_DIM;
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}
		
		
		
	}

	public class Mouse implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
		
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			int x = (e.getX() + locX - centerX) / Tile.TILE_DIM;
			int y = (e.getY() + locY - centerY - 20) / Tile.TILE_DIM;
			System.out.println(x + " " + y);
			if(SwingUtilities.isLeftMouseButton(e)){
				level.setTile(selectedTile, x, y);
			}else{
				level.setTile(null, x, y);
			}
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
}
