package log;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;

import javax.print.attribute.AttributeSet;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

import ca.nicho.foundation.SpriteSheet;

public class LogHandler extends JPanel{
	
	TransparentTextArea textArea;
	
	private final static int TYPE_SERVER = 0;
	private final static int TYPE_TUTORIAL = 2;
	private final static int TYPE_GUIDE = 3;
	
	public LogHandler(){
		setLayout(new BorderLayout());
		setOpaque(false);


			textArea = new TransparentTextArea();
			textArea.setEnabled(false);
			
			    
		/*
		textArea.setWrapStyleWord(true);
		textArea.setLineWrap(true);
		*/
        JScrollPane scroll = new JScrollPane (textArea, 
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setBorder(new EmptyBorder(8, 20, 8, 10));
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
     
        add(scroll, BorderLayout.CENTER);
	
	    //add(textArea, BorderLayout.CENTER);
	}
	
	/**
	 * Adds text to the log
	 * @param text to be entered
	 * @param type determines text color and style
	 */
	public void addToLog(String text, int type){


		Font font = new Font("Sans", Font.PLAIN, 16);
		switch(type){
			case(TYPE_SERVER):
				break;
			case(TYPE_TUTORIAL):
				break;
			case(TYPE_GUIDE):
				break;
		}

        textArea.setFont(font);

		try {
			textArea.getDocument().insertString(0, text+'\n', null);
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public class TransparentTextArea extends JTextPane {

        public TransparentTextArea() {
            setOpaque(false);
            setBorder(new EmptyBorder(5, 5, 5, 5));  
        }

        @Override
        protected void paintComponent(Graphics g) {
            g.setColor(new Color(0, 0, 0, 0));
            Insets insets = getInsets();
            int x = insets.left;
            int y = insets.top;
            int width = getWidth() - (insets.left + insets.right);
            int height = getHeight() - (insets.top + insets.bottom);
            g.fillRect(x, y, width, height);
            super.paintComponent(g);
        }

    }
}
