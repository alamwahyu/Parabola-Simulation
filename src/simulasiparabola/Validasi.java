import javax.swing.JOptionPane;


public class Validasi extends JOptionPane 
{
	public Validasi(String errorText)
	{   	
		this.showMessageDialog(null, errorText, null, JOptionPane.ERROR_MESSAGE); 
		
	}

}
