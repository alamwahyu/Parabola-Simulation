import java.awt.BasicStroke;
import java.awt.Color;
import static java.awt.Color.RED;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BorderFactory;
import javax.swing.JPanel;



public class DrawGraph extends JPanel 
{ 
	public static final int X_SIZE = 900; // Ukuran kotak grafik di arah x.
	public static final int Y_SIZE = 600; // Ukuran kotak grafik ke arah y. 
	public static final int PADDING_SPACE = 39; // Ruang antara tepi layar dan sistem koordinat.
	
	private static final Color COLOR = new Color(245, 192, 48); 
	private static final Color IDEAL_COLOR = new Color(28,255,255); 
	private static final Color NUMBER_COLOR = Color.white; 
	private static final Color GRID_COLOR = new Color(89,89,89); 
	private Rumus object; 
	private Dimension origin; 
	
	private boolean beginGraphing; 
	private boolean isIdealPath; 
	private boolean isRealtime; 
	private boolean isDone = false; 
	private boolean idealIsDone = false; 
	private boolean canStartNewSimulation = true; 
	
	private Timer timer = new Timer();
	
	private int counter = 1; 
	private int idealCounter = 5; 
	
	private DrawInteractiveComponents components; 
	private Skala scale;   
         Image img = Toolkit.getDefaultToolkit().getImage("E:\\background.jpg");
        
	//--------------------------CONSTRUCTOR-----------------------------------------------
	
	// Digunakan untuk menampilkan utilitas grafik sebagai kotak hitam kosong dengan sumbu x dan y.
	public DrawGraph(DrawInteractiveComponents components)
	{
		this.components = components; 
		this.setBackground(Color.black); 
                //this.imageUpdate(img, SOMEBITS, 100, 100, 100, 50);
		this.setBorder(BorderFactory.createLineBorder(Color.GRAY)); 
		this.setPreferredSize(new Dimension(X_SIZE, Y_SIZE)); 
		beginGraphing = false; 
	}
	
	//-------------------------------SETUP DRAWING METHODS----------------------------------------

	//Menggambar grid dan skala. 
	private void drawGrid(Graphics g)
	{
		for(int i = 0; i < (StrictMath.max(X_SIZE, Y_SIZE) - PADDING_SPACE) / scale.getSpacing() + scale.getSpacing(); i++)
		{
			g.setColor(NUMBER_COLOR); 
			
			if(i == 0)
			{
				g.drawString("0", origin.width - 8, origin.height + 12);  
				
			}
			else
			{	
				
				g.drawString(Integer.toString(i * scale.getCountBy()), scale.getSpacing() * i + PADDING_SPACE, Y_SIZE - 4); 
				g.drawString(Integer.toString(i *  scale.getCountBy()), 4, Y_SIZE - (scale.getSpacing() * i + PADDING_SPACE)); 
			}
			
			g.setColor(GRID_COLOR); 
			g.drawLine((i * scale.getSpacing()) + PADDING_SPACE, Y_SIZE - PADDING_SPACE,
					   (i * scale.getSpacing()) + PADDING_SPACE, 0); 
			g.drawLine(PADDING_SPACE, Y_SIZE - (i * scale.getSpacing() + PADDING_SPACE), 
					   X_SIZE,  Y_SIZE - (i * scale.getSpacing() + PADDING_SPACE)); 
		}
		
	}
	
        /*private void drawTank(Graphics g)
        {
            g.setColor(RED);
            g.fillOval(45, 520, 20, 20);
        }*/
        
	// Gambarkan sumbu x dan y tanpa garis penskalaan, dan tetapkan asalnya
        
	private void drawAxis(Graphics g)
	{
		g.setColor(new Color(255, 153, 102)); 
		//g.fillRect(50, 500, 50, 50);
		g.drawLine(PADDING_SPACE, Y_SIZE, PADDING_SPACE, 0); 
		g.drawLine(0, Y_SIZE - PADDING_SPACE, X_SIZE, Y_SIZE - PADDING_SPACE); 
		origin = new Dimension(PADDING_SPACE, Y_SIZE - PADDING_SPACE); 
	}
	
	// Menggambar jalur ideal untuk dijalankan secara realtime.
	private void drawCurrentIdealPath(Graphics g)
	{
		for(int i = idealCounter; i >= 1; i--)
		{
                    super.paintComponents(g);
                    Graphics2D g2 = (Graphics2D) g;
			g2.setColor(IDEAL_COLOR); 
                        g2.setStroke(new BasicStroke(3));
			g2.drawLine((int) StrictMath.round(object.getIdealDistance(idealCounter - i) / scale.getMPS()) + origin.width,
				   (int) StrictMath.round(Y_SIZE - (Y_SIZE - origin.height) - object.getIdealHeight(idealCounter - i) / scale.getMPS()), 
				   (int) StrictMath.round(object.getIdealDistance(idealCounter - i + 1) / scale.getMPS()) + origin.width,
				   (int) StrictMath.round(Y_SIZE - (Y_SIZE - origin.height) - object.getIdealHeight(idealCounter - i + 1) / scale.getMPS())); 
		}
	}
	
	//menggambar lintasan hambatan secara realtime.
	private void drawCurrentPath(Graphics g)
	{
		for(int i = counter; i >= 1; i--)
		{
                    super.paintComponents(g);
			Graphics2D g3 = (Graphics2D) g;
			g3.setColor(COLOR);
                        g3.setStroke(new BasicStroke (3));
			g3.drawLine((int) StrictMath.round(object.getDistance(counter - i) / scale.getMPS()) + origin.width,
                                    (int) StrictMath.round(Y_SIZE - (Y_SIZE - origin.height) - object.getHeight(counter - i) / scale.getMPS()), 
                                    (int) StrictMath.round(object.getDistance(counter - i + 1) / scale.getMPS()) + origin.width,
                                    (int) StrictMath.round(Y_SIZE - (Y_SIZE - origin.height) - object.getHeight(counter - i + 1)/ scale.getMPS())); 
		}
	}
	
	//menggambar idealpath (tanpa hambatan) no realtime
	private void drawIdealPath(Graphics g)
	{
		for(int i = 1; i < object.getIdealArraySize(); i++)
		{
			g.setColor(IDEAL_COLOR); 
			g.drawLine((int) StrictMath.round(object.getIdealDistance(i - 1) / scale.getMPS()) + origin.width,
				   (int) StrictMath.round(Y_SIZE - (Y_SIZE - origin.height) - object.getIdealHeight(i - 1) / scale.getMPS()), 
				   (int) StrictMath.round(object.getIdealDistance(i) / scale.getMPS()) + origin.width,
				   (int) StrictMath.round(Y_SIZE - (Y_SIZE - origin.height) - object.getIdealHeight(i) / scale.getMPS())); 
		}	
	}
	
	//menggambar lintasan hambatan tanpa realtime. 
	private void drawPath(Graphics g)
	{
		for(int i = 1; i < object.getArraySize(); i++)
		{
			g.setColor(COLOR); 
			g.drawLine((int) StrictMath.round(object.getDistance(i - 1) / scale.getMPS()) + origin.width,
					   (int) StrictMath.round(Y_SIZE - (Y_SIZE - origin.height) - object.getHeight(i - 1) / scale.getMPS()), 
					   (int) StrictMath.round(object.getDistance(i) / scale.getMPS()) + origin.width,
					   (int) StrictMath.round(Y_SIZE - (Y_SIZE - origin.height) - object.getHeight(i) / scale.getMPS()));
		}
	}
	
	//----------------------------DRAWING METHODS--------------------------------------------
	
	//menggambar lintasan. 
	private void drawObjectPath(Graphics g, boolean isRealtime, boolean isIdealPath)
	{	
		if(isRealtime)
		{
			if(!(isDone))
				drawCurrentPath(g); 
			else
				drawPath(g); 
			
			if(isIdealPath)
				drawCurrentIdealPath(g); 
			else 
				idealIsDone = true; 
			 
			if(idealIsDone && isIdealPath)
				drawIdealPath(g); 
		}
		else
		{
			drawPath(g); 
			
			if(isIdealPath)	
				drawIdealPath(g);
			
			isDone = true; 
			idealIsDone = true; 
		}
		
	
	}
	
	
	public void updateGraph(Rumus object, boolean isIdealPath, boolean isRealtime)
	{
		if(canStartNewSimulation)
		{	
			canStartNewSimulation = false;
			this.object = object; 
			this.isIdealPath = isIdealPath; 
			this.isRealtime = isRealtime; 
			scale = new Skala(object, isIdealPath); 
			
			this.repaint();
			beginGraphing = true; 
			isDone = false; 
			idealIsDone = false; 
			if(isRealtime)
			{
				timer = new Timer();  
				timer.scheduleAtFixedRate(new TaskScheduler(), 10, (int) (Rumus.PHYSICS_REFRESH_RATE * 1000));
			}
		
		}
		else
		{
			isDone = true; 
			idealIsDone = true; 
			beginGraphing = true; 
			timer.cancel(); 
			
			this.repaint(); 
			idealCounter = 1; 
			counter = 1; 
			components.setButtonText("Run Simulation"); 
		}
	}
	
	//menampilkan pada layar.. 
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g); 
		  
		if(beginGraphing)
		{ 
                        
			drawGrid(g); 
			drawAxis(g); 
			drawObjectPath(g, isRealtime, isIdealPath);  
			
			if(isDone && idealIsDone)
			{
				timer.cancel(); 
				canStartNewSimulation = true; 
				isDone = false; 
				idealIsDone = false; 
				idealCounter = 1; 
				counter = 1; 
				drawObjectPath(g, false, isIdealPath); 
				components.setButtonText("Run Simulation");  
			}
			
			
		}
		else
		{
			drawAxis(g);  
		}	
		
	}
	
	
	private class TaskScheduler extends TimerTask
	{
		public void run()
		{
			if(isIdealPath)
			{
				if(idealCounter < object.getIdealArraySize() - 1)
				{
					idealCounter++; 
					DrawGraph.this.repaint();  
				}
				else
				{
					idealCounter = 1;  
					idealIsDone = true;   
				}
			}
			
			if(counter < object.getArraySize() - 1)
			{
				counter++; 
				DrawGraph.this.repaint(); 
			}
			else
			{
				counter = 1; 
				isDone = true; 
			}
			
		}
		 
	}
}
