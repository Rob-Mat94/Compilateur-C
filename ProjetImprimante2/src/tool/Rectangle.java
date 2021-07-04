package tool;

import java.io.DataOutputStream;
import java.io.FileWriter;
import java.io.IOException;



/**
 * 
 * Classe représentant deux triangles et donc un rectangle
 *
 */

public class Rectangle 
{
	private Triangle t1;
	private Triangle t2;
		
	public Rectangle(Sommet s1,Sommet s2,Sommet s3, Sommet s4) {
		this.t1 = new Triangle (s1,s2,s3); 
		this.t2 = new Triangle(s2,s3,s4);
	}
	
	public Rectangle(Triangle t1 , Triangle t2)
	{
		this.t1 = t1;
		this.t2 = t2;
	}
	
	/**
	 * Ecrit les deux triangles dans la sortie out 
	 * @param out sortie
	 */
	public void write (DataOutputStream out) 
	{
		t1.write(out);
		t2.write(out);
	}
	
	public void write (FileWriter fw, float[] normal) throws IOException
	{
		t1.write(fw,normal);
		t2.write(fw,normal);
	}
	
}
