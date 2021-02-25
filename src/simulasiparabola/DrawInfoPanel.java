import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.text.DecimalFormat;

import javax.swing.JLabel;
import javax.swing.JPanel;


public class DrawInfoPanel extends JPanel  
{
	private Rumus object;
	private JLabel label; 
	private Color BOX_COLOR = new Color(255, 153, 102); 
	
	
	public DrawInfoPanel()
	{
		this.setPreferredSize(new Dimension(270, 200)); 
		this.setBackground(BOX_COLOR);
	}
	
	public DrawInfoPanel(Rumus object)
	{
		super(new GridBagLayout()); 
		this.object = object; 
		this.setPreferredSize(new Dimension(270, 200)); 
		this.setBackground(BOX_COLOR); 
		drawLabels();    
	}
	
	private void drawLabels() 
	{
		GridBagConstraints c = new GridBagConstraints(); 
		c.anchor = GridBagConstraints.PAGE_START; 
		c.gridx = 0; 
		c.gridy = 0;  
		c.weighty = .04; 
		DecimalFormat df = new DecimalFormat("0.00");
		
		label = new JLabel("STATISTICS"); 
		this.add(label, c); 
		
		//---------------------- WAKTU IDEAL-------------------------------
		c.anchor = GridBagConstraints.FIRST_LINE_START; 
		c.gridy = 1; 
		label = new JLabel("Waktu Peluncuran Ideal (s):   "); 
		this.add(label, c); 
		
		c.anchor = GridBagConstraints.FIRST_LINE_END; 
		c.gridx = 1; 
		label = new JLabel(df.format(object.getIdealTime())); 
		this.add(label, c);
		
		//----------------------WAKTU HAMBATAN-------------------------------
		c.anchor = GridBagConstraints.FIRST_LINE_START; 
		c.gridx = 0; 
		c.gridy = 2; 
		label = new JLabel("Hambatan Waktu Penerbangan (s):   "); 
		this.add(label, c); 
		
		c.anchor = GridBagConstraints.FIRST_LINE_END; 
		c.gridx = 1; 
		label = new JLabel(df.format(object.getTime())); 
		this.add(label, c); 
                
               
		
		//----------------------SPACING------------------------------------
		c.anchor = GridBagConstraints.FIRST_LINE_START; 
		c.gridx = 0; 
		c.gridy = 3; 
		label = new JLabel(""); 
		this.add(label, c); 
		
		//----------------------JARAK IDEAL-----------------------------
		c.anchor = GridBagConstraints.FIRST_LINE_START; 
		c.gridx = 0; 
		c.gridy = 4; 
		label = new JLabel("Jarak Proyektil Yang Ideal (m):   "); 
		this.add(label, c); 
		
		c.anchor = GridBagConstraints.FIRST_LINE_END; 
		c.gridx = 1; 
		label = new JLabel(df.format(object.getMaxIdealDistance())); 
		this.add(label, c); 
		
		//----------------------JARAK DENGAN HAMBATAN-------------------------------
		c.anchor = GridBagConstraints.FIRST_LINE_START; 
		c.gridx = 0; 
		c.gridy = 5; 
		label = new JLabel("Jarak Proyektil hambat udara (m):   "); 
		this.add(label, c); 
		
		c.anchor = GridBagConstraints.FIRST_LINE_END; 
		c.gridx = 1; 
		label = new JLabel(df.format(object.getMaxDistance())); 
		this.add(label, c); 
		
		//-----------------------SPACING------------------------------------
		c.anchor = GridBagConstraints.FIRST_LINE_START; 
		c.gridx = 0; 
		c.gridy = 6; 
		label = new JLabel(""); 
		this.add(label, c); 
		
		//--------------------TINGGI IDEAL-----------------------------------
		c.anchor = GridBagConstraints.FIRST_LINE_START; 
		c.gridx = 0; 
		c.gridy = 7; 
		label = new JLabel("Tinggi Proyektil Yang Ideal (m):   "); 
		this.add(label, c); 
		
		c.anchor = GridBagConstraints.FIRST_LINE_END; 
		c.gridx = 1; 
		label = new JLabel(df.format(object.getMaxIdealHeight())); 
		this.add(label, c); 
		
		//------------------TINGGI DENGAN HAMBATAN-------------------------------------
		c.anchor = GridBagConstraints.FIRST_LINE_START; 
		c.gridx = 0; 
		c.gridy = 8; 
		label = new JLabel("Tinggi Proyektil hambat udara (m):   "); 
		this.add(label, c); 
		
		c.anchor = GridBagConstraints.FIRST_LINE_END; 
		c.gridx = 1; 
		label = new JLabel(df.format(object.getMaxHeight())); 
		this.add(label, c); 
	}
	
	
	
}


