import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;


public class GUI 
{
	private JFrame frame = new JFrame(); 
	private JPanel panel = new JPanel(new GridBagLayout()); //Main panel.
	private DrawInteractiveComponents components = 
			new DrawInteractiveComponents(new GridBagLayout(), this); //Buttons and text fields. 
	private DrawGraph grapher = new DrawGraph(components);    //Sumbu grafik sebelum inisialisasi objek. 
	private DrawInfoPanel info = new DrawInfoPanel(); //menampilkan info seperti waktu, dan tinggi maks.
	//-------------------GETTERS AND SETTERS-------------------------------
        Image img = Toolkit.getDefaultToolkit().getImage("E:\\background.jpg");
	public JPanel getPanel()
	{
		return panel; 
	}
	
	//-----------------------------CONSTRUCTOR-----------------------------
	
	//memulai GUI. 
	public GUI()
	{
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				createGUI(); 
			}
		}); 
	}
	
	//--------------------------GUI SETUP-------------------------------------
	
	//menggabungkan semua method utk membuat gui. 
	private void createGUI()
	{
		frame.add(panel); 
		frame.setResizable(false);  
		setInitialGraphPanel();
		setInfoPanel(); 
		setComponentsPanel();
                
		frame.pack(); 
		frame.setLocationRelativeTo(null);
		frame.setVisible(true); 
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
	}
        
	 
	private void setInitialGraphPanel()
	{
		GridBagConstraints c = new GridBagConstraints(); 

		c.insets = new Insets(15, 15, 15, 5); 
		c.anchor = GridBagConstraints.FIRST_LINE_START; 
		c.gridx = 0; 
		c.gridy = 0; 
		panel.add(grapher, c); 
	}
	
	private void setComponentsPanel()
	{
		GridBagConstraints c = new GridBagConstraints(); 

		c.gridx = 1; 
		c.gridy = 0; 
		c.anchor = GridBagConstraints.LAST_LINE_END; 
		
		panel.add(components, c); 
	}
	
	private void setInfoPanel()
	{
		GridBagConstraints c = new GridBagConstraints(); 
		
		c.anchor = GridBagConstraints.FIRST_LINE_END; 
		c.gridx = 1; 
		c.gridy = 0; 
		c.insets = new Insets(15, 0, 0, 6); 
		panel.add(info, c); 
	}

	//-------------------------GUI UPDATING----------------------------------------
	
	// Memperbarui GUI untuk menggambar grafik baru berdasarkan input ke komponen.
	public void updateGUI()
	{ 
		if(!(components.getStopText()))
		{   
			info.setVisible(false); 
			info = new DrawInfoPanel(components.getObject()); 
			setInfoPanel();
					
		} 
				
		grapher.updateGraph(components.getObject(), components.isIdealPath(),
							components.isRealtime());
			
		 
		
	}
		
	
}
