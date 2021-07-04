package main;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import tool.Rectangle;
import tool.Sommet;

public class SphereASCII extends Forme3D implements Forme3DGenerator {
	

	private int count = 1;
	private int scale = 0;
	
	public SphereASCII(float width, float height, float length, int count) {
		super(width, height, length);
		this.count = count;
	}

	@Override
	public void generate(String file_path) throws IOException {

		float rayon = width;
		int p = (int)height;
		
		List<Rectangle> lisRec = new ArrayList<Rectangle>();

        // Une sphère peut être décrite comme un planisphère -> on crée un tableau de sommets
        // p est la précision de notre planisphère, on va créer des carrés de longueur p 
        // thehta est compris entre -pi/2 et pi/2
        //phi est compris entre -pi et pi
        Sommet [][] s = new Sommet [p][p];

        for(int i =0;i<p;i++) 
        { 
	        float theta = i*180/p +90;
	        for(int j =0;j<p;j++) 
	        {
		        float phi = j*360/p +180;
		        float x = (float) (rayon * Math.cos(theta) * Math.cos(phi));
		        float y = (float) (rayon * Math.cos(theta) * Math.sin(phi));
		        float z = (float) (rayon * Math.sin(theta));

		        s[i][j] = new Sommet(x,y,z);
	        }
	        
       }
        
        for(int k =0;k<p-1;k++) {
            for(int l = 0;l<p-1;l++) {
            Sommet s1 = s[k][l];
            Sommet s2 = s[k+1][l];
            Sommet s3 = s[k][l+1];
            Sommet s4 = s[k+1][l+1];

            lisRec.add(new Rectangle(s1,s2,s3,s4));

            }
	
        }
        
        File file = super.createFile(file_path);
		FileWriter fw = new FileWriter(file);
		
		/* Nom de la figure */
		fw.write("solid Test\n");
		
		
		for(Rectangle r : lisRec)
		{
			r.write(fw, new float[] {rayon,(int)p,-8999});
		}
		
		
		fw.write("endsolid Test\n");
        fw.flush();
        fw.close();
	}

}
