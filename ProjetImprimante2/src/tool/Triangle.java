package tool;

import java.io.DataOutputStream;
import java.io.FileWriter;
import java.io.IOException;



/**
 * 
 * Classe représentant un triangle (liaison entre 3 points)
 */
public class Triangle {
	private Sommet s1;
	private Sommet s2;
	private Sommet s3;
	private Sommet normal = new Sommet (0,0,0);
	
	public static int nbTriangle = 0;
	 
	public Triangle(Sommet s1, Sommet s2, Sommet s3) {
		this.s1 = s1;
		this.s2 = s2;
		this.s3 = s3;
		nbTriangle++;
	}
	
	public void write (DataOutputStream out) {
		try{
			normal.write(out);
			s1.write(out);
			s2.write(out);
			s3.write(out);
			out.writeShort(0);
		} catch (IOException e) {
			System.err.println(e);
            System.exit(1);
		}
		
	}
	
	public void write(FileWriter fw,float[] normal) throws IOException
	{
		fw.write(" facet normal " + normal[0] + " " +normal[1]+ " " + normal[2] + "\n");
		fw.write("  outer loop\n");
		s1.write(fw);s2.write(fw);s3.write(fw);
		fw.write("  endloop\n");
		fw.write(" endfacet\n\n");
	}
	
}