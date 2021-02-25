import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;


public class DrawInteractiveComponents extends JPanel 
{
	private JButton runSimulationButton; 
	
	private JCheckBox idealPathButton; 
	private JCheckBox realtimeButton; 
	
        
	private JTextField areaField = new JTextField("0.0314", 5); 
	private JTextField dragCoefficientField = new JTextField("0.5", 5); 
	private JTextField densityField = new JTextField("1.225", 5);
	private JTextField angleField = new JTextField("45.0", 5);
	private JTextField speedField = new JTextField("30.0", 5); 
	private JTextField heightField = new JTextField("0.0", 5);
	private JTextField gravityField = new JTextField("9.81", 5);
	private JTextField massField = new JTextField("1.0", 5);
	private JTextField [] fieldArray = {speedField, heightField, angleField, densityField, areaField, 
				dragCoefficientField, gravityField, massField}; 
	
	private JLabel label; 

	private boolean showIdealPath;  
	private boolean showRealtime; 
	private boolean stopText = false; 
	
	private Rumus object; 
	private GUI gui; 
	private Listener listener = new Listener();
	
	private String buttonText; 
	
	//----------------GETTERS AND SETTERS-------------------------------
	
	public Rumus getObject()
	{
		return this.object; 
	}
	
	public boolean isIdealPath()
	{
		return this.showIdealPath; 
	}

	public boolean isRealtime()
	{
		return this.showRealtime; 
	}
	
	public void setButtonText(String buttonText)
	{ 
		stopText = false; 
		this.buttonText = buttonText; 
		listener.setText();  
	}
	
	public boolean getStopText()
	{
		return stopText; 
	}
	
	
	//--------------------CONSTRUCTOR-----------------------------------
	
	public DrawInteractiveComponents(GridBagLayout g, GUI gui)
	{
		super(g); 
		this.gui = gui; 
		
		GridBagConstraints c = new GridBagConstraints(); 

		this.setPreferredSize(new Dimension(270, 400)); 
		c.anchor = GridBagConstraints.FIRST_LINE_END; 
		drawButton(); 
		drawTextFields(); 
		drawCheckBoxes(); 	
	}
	
	//---------------------COMPONENTS------------------------------------
	
	private void drawButton()
	{
		GridBagConstraints c = new GridBagConstraints(); 

		c.anchor = GridBagConstraints.LINE_END;  
		runSimulationButton = new JButton("Run Simulation"); 
		runSimulationButton.setBorder(BorderFactory.createEmptyBorder(12, 17, 12, 17)); 
		c.weighty = 0.6; 
		
		c.gridx = 0;
		c.gridy =11; 
		c.insets = new Insets(10, 0, 23, 0); 
		runSimulationButton.addActionListener(listener); 
		this.add(runSimulationButton, c); 
		
	}
	
	private void drawCheckBoxes()
	{
		GridBagConstraints c = new GridBagConstraints(); 
		
		idealPathButton = new JCheckBox("Tampilkan Gerak Ideal", true); 
		realtimeButton = new JCheckBox("Jalankan secara Realtime", true); 
		
		c.anchor = GridBagConstraints.FIRST_LINE_START; 
		c.gridx = 0; 
		c.gridy = 0; 
		
		this.add(idealPathButton, c); 
		
		c.insets = new Insets(0, 0, 20, 0); 
		c.gridx = 0; 
		c.gridy = 1; 
		
		this.add(realtimeButton, c); 
	}
	
	private void drawTextFields()
	{
		GridBagConstraints c = new GridBagConstraints(); 

		c.anchor = GridBagConstraints.FIRST_LINE_START; 
		c.weightx = 0.05; 
		c.weighty = 0.3; 
		
		//------------------KECEPATAN--------------------------
		
		label = new JLabel("Kecepatan awal (m/s):"); 
		c.gridx = 0; 
		c.gridy = 2; 
		this.add(label, c); 
		
		c.gridx = 1; 
		this.add(speedField, c); 
		
		//-----------------------SUDUT---------------------------------
		
		label = new JLabel("sudut peluncuran (deg):"); 
		c.gridx = 0; 
		c.gridy = 3; 
		this.add(label, c); 
		
		c.gridx = 1; 
		
		this.add(angleField, c); 
		
		//-----------------------TINGGI---------------------------------
		
		/*label = new JLabel("Ketinggian awal (m):"); 
		c.gridx = 0; 
		c.gridy = 4; 
		this.add(label, c); 
		
		c.gridx = 1; 
		
		this.add(heightField, c);*/
		
		//-------------------------MASSA---------------------------------
		
		label = new JLabel("Massa (kg):"); 
		c.gridx = 0; 
		c.gridy = 5; 
		this.add(label, c); 
		
		c.gridx = 1; 
		
		this.add(massField, c);
		
		//----------------------GRAVITASI-------------------------------------
		
		label = new JLabel("Gravity (m/s^2):"); 
		c.gridx = 0; 
		c.gridy = 6; 
		this.add(label, c); 
		
		c.gridx = 1; 
		
		this.add(gravityField, c); 
		
		//------------------------KEPADATAN UDARA--------------------------------
		
		label = new JLabel("Kepadatan Udara (kg/m^3):"); 
		c.gridx = 0; 
		c.gridy = 7; 
		this.add(label, c); 
		
		c.gridx = 1; 
		
		this.add(densityField, c); 
		
		//----------------------DKOEF HAMBAT------------------------------
		
		label = new JLabel("koefisien Hambat Udara:"); 
		c.gridx = 0; 
		c.gridy = 8; 
		this.add(label, c); 
		
		c.gridx = 1; 
		
		this.add(dragCoefficientField, c); 
		
		//-----------------------LUAS PENAMPANG------------------------------------------
		
		label = new JLabel("Luas penampang (m^2):"); 
		c.gridx = 0; 
		c.gridy = 9; 
		this.add(label, c); 
		
		c.gridx = 1; 
		
		this.add(areaField, c);
		
	}
	
	//-----------------------ACTION EVENTS---------------------------------
	
	
	public class Listener implements ActionListener
	{
		private boolean isValid = true; 
		
		private void checkIfValid()
		{
			if(new Double(speedField.getText()) > 1000 || new Double(speedField.getText()) < 1)
			{
				isValid = false; 
				new Validasi("Kecepatan harus antara 1 dan 1000 m/s."); 
			}
			
			if(new Double(angleField.getText()) > 90 || new Double(angleField.getText()) < -90)
			{
				isValid = false; 
				new Validasi("Sudut harus antara 90 dan -90 derajat."); 
			}
			
			if(new Double(heightField.getText()) > 100000 || new Double(heightField.getText()) < 0)
			{
				isValid = false; 
				new Validasi("Tingginya harus antara 0 dan 100.000 meter."); 
			}
			
			if(new Double(massField.getText()) > 100000 || new Double(massField.getText()) < 0)
			{
				isValid = false; 
				new Validasi("Massa harus antara 0 dan 100.000 kg. \n\n" +
								"NOTE:   Jika massa 0, jalur ideal dihitung."); 
			}
			
			if(new Double(gravityField.getText()) > 200 || new Double(gravityField.getText()) < 1)
			{
				isValid = false; 
				new Validasi("Gravitasi harus antara 1 dan 200 m/s^2."); 
			}
			
			if(new Double(densityField.getText()) > 10 || new Double(densityField.getText()) < 0)
			{
				isValid = false; 
				new Validasi("Kepadatan udara harus antara 0 dan 10 kg/m^3."); 
			}
			
			if(new Double(dragCoefficientField.getText()) > 10 || new Double(densityField.getText()) < 0)
			{
				isValid = false; 
				new Validasi("Koefisien hambat udara harus antara 0 dan 10."); 
			}
			
			if(new Double(areaField.getText()) > 100 || new Double(areaField.getText()) < 0)
			{
				isValid = false; 
				new Validasi("Luas penampang harus antara 0 dan 100 m^2."); 
			}
		}
		
		private void setText()
		{
			runSimulationButton.setText(buttonText); 
		}
		
		public void actionPerformed(ActionEvent e) 
		{	
			
			if(e.getSource() == runSimulationButton)
			{
				
				for(int i = 0; i < fieldArray.length; i++)
				{
					if(fieldArray[i].getText().isEmpty())
							fieldArray[i].setText("0"); 
						
				}
						
				SwingUtilities.invokeLater(new Runnable()
				{
					public void run()
					{
						try
						{
							checkIfValid(); 
							
							if(isValid)
							{
								object = new Rumus(new Double(speedField.getText()), new Double(heightField.getText()), 
													 new Double(angleField.getText())); 
								
								
								object.setAirDensity(new Double(densityField.getText())); 
								object.setArea(new Double(areaField.getText())); 
								object.setDragCoefficient(new Double(dragCoefficientField.getText())); 
								
								object.setGravity(new Double(gravityField.getText())); 
								object.setMass(new Double(massField.getText())); 
								object.runSimulation(); 
								
								showIdealPath = idealPathButton.isSelected(); 
								showRealtime = realtimeButton.isSelected(); 
								
								gui.updateGUI(); 
								
								if((!stopText))
								{
									runSimulationButton.setText("Stop Simulation"); 
									stopText = true; 
								}
								else
								{
									setText(); 
									stopText = false; 
								}
							}
							else
							{
								isValid = true; 
							}
							
						}
						catch(NumberFormatException ex)
						{ 
							new Validasi("Invalid input.");  
						}
						
					}
				}); 
			} 
		} 
	}	
}
