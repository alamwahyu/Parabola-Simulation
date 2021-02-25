public class Skala 
{
	private Rumus object; 
	private boolean isIdealPath; // Jalur ideal dipilih untuk grafik.
	private double metersPerPixel; //Berapa meter setiap melambangkan piksel.
	private int spacing; // Berapa banyak piksel di antara setiap tanda.
	private int countBy; //Satuan pengukuran.
	public final int MAX; 
	
	public int getSpacing()
	{
		return spacing; 
	}
	
	public double getMPS()
	{
		return metersPerPixel; 
	}
	
	public int getCountBy()
	{
		return countBy; 
	}
	
	public Skala(Rumus object, boolean isIdealPath)
	{
		MAX = (int) StrictMath.ceil(object.getLargestArrayValue(isIdealPath));
		this.object = object; 
		this.isIdealPath = isIdealPath; 
		determineCountBy(); 
		determineMetersPerPixel(); 
	}
	
	private void determineCountBy()
	{ 
		
		if(MAX >= 1 && MAX<= 20)
			countBy = 1; 
		else if(MAX > 20 && MAX <= 40)
			countBy = 2; 
		else if(MAX > 40 && MAX <= 100)
			countBy = 5; 
		else if(MAX > 100 && MAX <= 200)
			countBy = 10; 	
		else if(MAX > 200 && MAX <= 400)
			countBy = 20; 
		else if(MAX > 400 && MAX <= 1000)
			countBy = 50; 
		else if(MAX > 1000 && MAX <= 2000) 
			countBy = 100; 
		else if(MAX > 2000 && MAX <= 4000)
			countBy = 200; 
		else if(MAX > 4000 && MAX <= 5000)
			countBy = 500; 
		else if(MAX > 5000 && MAX <= 10000)
			countBy = 1000; 
		else if(MAX > 10000 && MAX <= 20000)
			countBy = 2000; 
		else if(MAX > 20000 && MAX <= 50000)
			countBy = 5000; 
		else if(MAX > 50000 && MAX <= 100000)
			countBy = 10000; 
		else if(MAX > 100000 && MAX <= 150000)
			countBy = 20000; 
		else
			countBy = 100000; 
		
	}
	
	private void determineMetersPerPixel()
	{
		int screenSize = StrictMath.min(DrawGraph.X_SIZE, DrawGraph.Y_SIZE)
						 - DrawGraph.PADDING_SPACE; 
		
		metersPerPixel = MAX / (double) screenSize;  
		
		spacing = (int) StrictMath.round(countBy / metersPerPixel);  
	}
	
}
