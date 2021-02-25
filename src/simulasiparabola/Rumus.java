import static java.time.Clock.system;
import java.util.ArrayList;
import java.util.Collections;



public class Rumus 
{
	//------------------------VARIABLES-------------------------------
	
	//Gravitasi
	private double gravitasi; // m / s ^ 2 (turun didefinisikan sebagai positif)
	//Udara
	private double masaJenisUdara; // Kepadatan udara dalam kg / m ^ 3
	private double gayaHambatUdara; // Hambatan udara pada saat ini di N
	private double koefHambatUdara; 
	//Sudut
	private double sudut; // Sudut lempar dalam derajat
	//LuasPenampang
	private double luasPenampang; // Area penampang melintang objek
	//Kecepatan
	private double inisialKecepatan; 
	private double kecepatanXsaatini; // x komponen objek
	private double kecepatanYsaatini; // y komponen objek
	private double kecepatanSaatdiUdara; // inisialKecepatan objek aktual
	//Posisi
	private double inisialTinggi; 
	private ArrayList<Double> tinggi = new ArrayList<Double>(0);  
	private ArrayList<Double> jarak = new ArrayList<Double>(0);  
	private ArrayList<Double> idealTinggi = new ArrayList<Double>(0); 
	private ArrayList<Double> idealJarak = new ArrayList<Double>(0); 

	//Massa
	private double massa; 
	//Waktu
	private double time = 0; // Waktu yang telah berlalu dalam simulasi
	private double idealTime = 0;// Waktu berlalu dalam simulasi ideal.
	private int timeInterval = 0; // Digunakan untuk mengakses array ketinggian dan jarak
	public static final double PHYSICS_REFRESH_RATE = .01;  // Interval waktu dihitung
	
	
	//--------------------------GETTERS AND SETTERS--------------------------
	
	public void setGravity(double gravitasi)
	{
		this.gravitasi = gravitasi; 
	}
	
	public void setAirDensity(double masaJenisUdara)
	{
		this.masaJenisUdara = masaJenisUdara; 
	}
	
	public void setDragCoefficient(double koefHambatUdara)
	{
		this.koefHambatUdara = koefHambatUdara; 
	}
	
	public void setArea(double luasPenampang)
	{
		this.luasPenampang = luasPenampang; 
	}

	public void setMass(double massa)
	{
		this.massa = massa; 
	}
	
	public int getArraySize()
	{
		return StrictMath.min(tinggi.size(), jarak.size()); 
	}
	
	public int getIdealArraySize()
	{
		return StrictMath.min(idealTinggi.size(), idealJarak.size()); 
	}
	
	public Double getHeight(int i)
	{
		return tinggi.get(i);  
	}
	
	public Double getMaxHeight()
	{
		return Collections.max(tinggi); 
	}
	
	public Double getMaxDistance()
	{
		return Collections.max(jarak); 
	}
	
	public Double getMaxIdealHeight()
	{
		return Collections.max(idealTinggi); 
	}
	
	public Double getMaxIdealDistance()
	{
		return Collections.max(idealJarak); 
	}
	
	public double getLargestArrayValue(boolean isIdealPath)
	{
		if(isIdealPath)
		{
			return StrictMath.max(StrictMath.max(Collections.max(idealTinggi), 
								  Collections.max(idealJarak)), 
								  StrictMath.max(Collections.max(tinggi),  
								  Collections.max(jarak))); 
		}
		else
		{
			return StrictMath.max(Collections.max(tinggi), Collections.max(jarak)); 
		}	
	}
	
	public Double getDistance(int i)
	{
		return jarak.get(i); 
	}
	
	public Double getIdealHeight(int i)
	{
		return idealTinggi.get(i); 
	}

	public Double getIdealDistance(int i)
	{
		return idealJarak.get(i); 
	}
	
	public int getTimeInterval()
	{
		return timeInterval; 
	}
	
	public double getTime()
	{
		return time; 
	}
	
	public double getIdealTime()
	{
		return idealTime; 
	}
	
	public double getAirSpeed()
	{
		return kecepatanXsaatini; 
	}
	
	
	//----------------------------CONSTRUCTOR-----------------------------------
	
	public Rumus(double initialSpeed, double initialHeight, double angle) 
	{  
		this.sudut = StrictMath.toRadians(angle);  
		this.inisialKecepatan = initialSpeed; 
		this.inisialTinggi = initialHeight; 
		
		kecepatanXsaatini = StrictMath.cos(this.sudut) * initialSpeed;  
		kecepatanYsaatini = StrictMath.sin(this.sudut) * initialSpeed;  
		kecepatanSaatdiUdara = initialSpeed;  
		
		tinggi.add(initialHeight); 
		jarak.add(0.0);  
	}
	
	//---------------------------CALCULATIONS------------------------------------
	
	
	// Hitung hambatan udara untuk waktu tertentu. Ini harus disebut dalam interval yang ditetapkan
        // waktu untuk terus memperbarui perubahan hambatan udara.
	private void menghitungGayaHambat()
	{
		gayaHambatUdara = (1/2.0) * masaJenisUdara * StrictMath.pow(kecepatanSaatdiUdara, 2) * luasPenampang * koefHambatUdara;  
	}
	
	// Kecepatan dihitung ulang setiap PHYSICS_REFRESH_RATE milidetik, dan hambatan udara yang dihitung
        // dapat dimodelkan untuk bertindak pada objek untuk periode waktu tersebut, memungkinkan untuk perubahan
        // dalam inisialKecepatan untuk dihitung.
	private void menghitungKecepatanSaatini()
	{
		menghitungGayaHambat(); 
		
		sudut = Math.atan2(kecepatanYsaatini, kecepatanXsaatini); 
		
		kecepatanXsaatini -= ((gayaHambatUdara * StrictMath.cos(sudut)) / massa) 
								  * PHYSICS_REFRESH_RATE; 
		kecepatanYsaatini -= (gravitasi + ((gayaHambatUdara * StrictMath.sin(sudut)) / massa))
								* PHYSICS_REFRESH_RATE; 
		kecepatanSaatdiUdara = StrictMath.hypot(kecepatanXsaatini, kecepatanYsaatini); 
		
	}
	
	// Menggunakan inisialKecepatan yang dihitung, kita bisa mengalikannya dengan waktu yang berlalu atau PHYSICS_REFRESH_RATE
// untuk menemukan di mana objek berada di waktu yang baru.
	private void menghitungPosisiSaatini()
	{
		menghitungKecepatanSaatini(); 
			
		tinggi.add(tinggi.get(timeInterval - 1) + (kecepatanYsaatini * PHYSICS_REFRESH_RATE)); 
		jarak.add(jarak.get(timeInterval - 1) + (kecepatanXsaatini * PHYSICS_REFRESH_RATE)); 
                    
		time += PHYSICS_REFRESH_RATE; 
                
	}
	/*return (massa <= 0 || koefHambatUdara <= 0 || luasPenampang <= 0 || masaJenisUdara <= 0); */
	public boolean hanyaMenghitungIdealPath()
	{
		return (massa < 0 || koefHambatUdara < 0 || luasPenampang < 0 || masaJenisUdara < 0); 
	}
	
	// Menjalankan simulasi untuk menempatkan nilai yang sesuai dalam array.
	public void runSimulation()
	{
		idealTinggi.add(inisialTinggi); 
		idealJarak.add(0.0);  
		
		timeInterval++; 
		
		// Menghitung jalur ideal.
		do
		{
			
			idealTime += PHYSICS_REFRESH_RATE; 
		                                                                              	
			idealTinggi.add((-(gravitasi / 2.0) * StrictMath.pow(idealTime, 2) + (inisialKecepatan * StrictMath.sin(sudut)
							* idealTime)) + inisialTinggi);  
			idealJarak.add((idealJarak.get(timeInterval - 1) + (inisialKecepatan * StrictMath.cos(sudut)) 
							   * PHYSICS_REFRESH_RATE));  
			
			timeInterval++; 
		}
		while(idealTinggi.get(timeInterval - 1) > 0); 
		
		timeInterval = 1; 
		
		if(hanyaMenghitungIdealPath())
		{
			tinggi = idealTinggi; 
			jarak = idealJarak; 
			time = idealTime;  
		}
		else
		{
			do
			{ 
				menghitungPosisiSaatini(); 
				timeInterval++; 
			}
			while(tinggi.get(timeInterval - 1) > 0); 
			 
		}
		
		idealTime -= 0.01; 
		time -= 0.01; 
		
	}
	
	

}
