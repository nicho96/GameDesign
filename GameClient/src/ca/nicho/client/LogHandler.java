package ca.nicho.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

import ca.nicho.foundation.Logger;
import ca.nicho.foundation.SpriteSheet;

public class LogHandler extends JPanel implements Logger{
	
	private static TransparentTextArea textPane;
	private static JScrollPane scroll;
	public static JTextField field;
	
	private final static String font = "fonts/coders_crux.ttf";
	private static volatile LogHandler pane; //Volatile? Why? ~Nicho
	
	public int height;
	public int width;
	
	public LogHandler(){
		this.pane = this;
		pane.setLayout(new BorderLayout());
		pane.setOpaque(false);


		textPane = new TransparentTextArea();
		textPane.setEnabled(false);
			
        scroll = new JScrollPane (textPane, 
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setBorder(new EmptyBorder(18, 30, 15, 25));
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        scroll.getVerticalScrollBar().setUI(new MyScrollbarUI());
        
        field = new JTextField(10);
        field.setOpaque(false);
       // field.addAncestorListener(new RequestFocusListener(false));        
        field.setFocusable(false);
        field.setRequestFocusEnabled(false);
        field.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub
		        field.setBorder(new EmptyBorder(0, 30, 0, 25));
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub
				field.setBorder(new CompoundBorder(new EmptyBorder(0, 30, 0, 25), BorderFactory.createLineBorder(Color.red)));

			}
		});


        field.setBorder(new EmptyBorder(0, 30, 0, 25));
        field.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
								
			} 
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				  if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
						field.setFocusable(false);	
						field.setFocusable(true);
				   }
			}
		});

        pane.add(scroll, BorderLayout.CENTER);
        //pane.add(field, BorderLayout.SOUTH);
        
        width = SpriteSheet.SPRITE_LOG_LG.width;
        height = SpriteSheet.SPRITE_LOG_LG.height; //+30 for seperation
	    pane.setSize(width, SpriteSheet.SPRITE_LOG_LG.height);
	}
	public static synchronized LogHandler getLogInstance() {
	    if (null == pane) {
	        pane = new LogHandler();
	    	//throw new NullPointerException();
	    }
	    return pane;
	}
	
	private static String textLOG;
	/**
	 * Adds text to the log
	 * @param text to be entered
	 * @param type determines text color and style
	 */
	
	public void addToLog(String text, String hex){
		Color c = Color.black;
		textPane.appendToPane(text, c);
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
            g.setColor(new Color(0, 0, 0, 0));
            Insets insets = getInsets();
            int x = insets.left;
            int y = insets.top;
            int width = getWidth() - (insets.left + insets.right);
            int height = getHeight() - (insets.top + insets.bottom);
            g.fillRect(x, y, width, height);
        }
        
        protected void appendToPane(String msg, Color c){
            StyleContext sc = StyleContext.getDefaultStyleContext();
            AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);

            aset = sc.addAttribute(aset, StyleConstants.FontFamily, "Lucida Console"); //We can change
            aset = sc.addAttribute(aset, StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED);

            setCaretPosition(0);
            setCharacterAttributes(aset, false);
            replaceSelection(msg + "\n");
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
	        	a.getScaledInstance(16, 49, Image.SCALE_FAST);
	        	g.drawImage(a, thumbRect.x, thumbRect.y, null);
	        }

	        @Override
	        protected void setThumbBounds(int x, int y, int width, int height) {
	          super.setThumbBounds(x, y, width, height);
	          scrollbar.repaint();
	        }
	    }


	@Override
	public void sendMessage(String msg, int owner) {
		this.addToLog(msg, "#67A1B9A");
	}
	
	@Override
	public void sendGlobalMessage(String msg) {
		
	}    
}
class RequestFocusListener implements AncestorListener
{
    private boolean removeListener;

    /*
     *  Convenience constructor. The listener is only used once and then it is
     *  removed from the component.
     */
    public RequestFocusListener()
    {
        this(true);
    }

    /*
     *  Constructor that controls whether this listen can be used once or
     *  multiple times.
     *
     *  @param removeListener when true this listener is only invoked once
     *                        otherwise it can be invoked multiple times.
     */
    public RequestFocusListener(boolean removeListener)
    {
        this.removeListener = removeListener;
    }
    
    
	@Override
	public void ancestorAdded(AncestorEvent event) {
		// TODO Auto-generated method stub

        JComponent component = event.getComponent();
        component.requestFocusInWindow();

        if (removeListener)
            component.removeAncestorListener( this );
	}

	@Override
	public void ancestorMoved(AncestorEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void ancestorRemoved(AncestorEvent event) {
		// TODO Auto-generated method stub
		
	}
}