package log;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.TimerTask;

import javax.print.attribute.AttributeSet;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

import ca.nicho.foundation.SpriteSheet;

public class LogHandler extends JPanel{
	
	TransparentTextArea textPane;
	
	private final static int TYPE_SERVER = 0;
	private final static int TYPE_TUTORIAL = 2;
	private final static int TYPE_GUIDE = 3;
	private final static String font = "fonts/coders_crux.ttf";
	
	public LogHandler(){
		setLayout(new BorderLayout());
		setOpaque(false);


		textPane = new TransparentTextArea();
		textPane.setEnabled(false);
			
        JScrollPane scroll = new JScrollPane (textPane, 
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setBorder(new EmptyBorder(18, 30, 15, 25));
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        scroll.getVerticalScrollBar().setUI(new MyScrollbarUI());
	        
     
        add(scroll, BorderLayout.CENTER);
	
	    //add(textArea, BorderLayout.CENTER);
	}
	
	/**
	 * Adds text to the log
	 * @param text to be entered
	 * @param type determines text color and style
	 */
	public void addToLog(String text, int type){


		   Font customFont;
		try {
			customFont = Font.createFont(Font.TRUETYPE_FONT, new File(font)).deriveFont(26f);
	        textPane.setFont(customFont);
		} catch (FontFormatException | IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}




		switch(type){
			case(TYPE_SERVER):
				break;
			case(TYPE_TUTORIAL):
				break;
			case(TYPE_GUIDE):
				break;
		}

		try {    
			textPane.getDocument().insertString(0, text+"\n", null);
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	
	public class TransparentTextArea extends JTextPane {

        public TransparentTextArea() {
        	super();
            setOpaque(false);
            setBorder(new EmptyBorder(5, 5, 5, 5));
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(new Color(255, 0, 255, 0));
            Insets insets = getInsets();
            int x = insets.left;
            int y = insets.top;
            int width = getWidth() - (insets.left + insets.right);
            int height = getHeight() - (insets.top + insets.bottom);
            g.fillRect(x, y, width, height);

        	
        }

    }

	
	 class MyScrollbarUI extends BasicScrollBarUI {

	        private ImageIcon downArrow, upArrow, bar;

	        public MyScrollbarUI(){
	            upArrow = new ImageIcon("res/ic_thumb_up.png","up");
				downArrow = new ImageIcon("res/ic_thumb_down.png","down");
				bar = new ImageIcon("res/ic_bar.png","bar");        
	        }

	        @Override
	        protected JButton createDecreaseButton(int orientation) {
	            JButton button = new JButton(upArrow){
	                @Override
	                public Dimension getPreferredSize() {
	  
	                    return new Dimension(16, 14);
	                }
	            };
	            button.setOpaque(false);
	            button.setContentAreaFilled(false);
	            button.setBorderPainted(false);
	            return button;
	        }

	        @Override
	        protected JButton createIncreaseButton(int orientation) {
	            JButton button = new JButton(downArrow){
	                @Override
	                public Dimension getPreferredSize() {
	                    return new Dimension(16, 14);
	                }
	            };
	            button.setOpaque(false);
	            button.setContentAreaFilled(false);
	            button.setBorderPainted(false);
	            return button;
	        }


	        @Override
	        protected void paintTrack(Graphics g, JComponent c, Rectangle r) {
	        	c.setBackground(new Color(0, 0, 0, 24));
	        	//c.setOpaque(false);
	        }
	        
	        @Override
	        protected void paintThumb(Graphics g, JComponent c, Rectangle r) {
	        	Image a = bar.getImage();
	        	a.getScaledInstance(16, thumbRect.y, Image.SCALE_FAST);
	        	g.drawImage(a, thumbRect.x, thumbRect.y, null);
	        }

	        @Override
	        protected void setThumbBounds(int x, int y, int width, int height) {
	          super.setThumbBounds(x, y, width, height);
	          scrollbar.repaint();
	        }
	    }    
}
