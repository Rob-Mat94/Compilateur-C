package main;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import tool.Rectangle;
import tool.Sommet;

public class CylinderAscii extends Forme3D implements Forme3DGenerator{

	
	private int count = 1;
	private int scale = 0;
	
	/* width, height, length */
	
	public CylinderAscii(float sides, float rayon, float hauteur) {
		super(sides, rayon, hauteur);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void generate(String file_path) throws IOException {
		
		int sides = (int)width;
		float rayon = height;
		float hauteur = length;
		
		 File file = new File(file_path);
			try {
	            FileWriter fw = new FileWriter(file);
	            fw.write("solid Test\n");

	            List<Rectangle> cylindre = new ArrayList<Rectangle>();
	            Sommet [] s = new Sommet [sides];
	            Sommet [] s1 = new Sommet [sides];
	            float angle = 360/sides;
	            for(int i=0; i<sides; i++) {

	                s[i] = new Sommet((float)Math.cos(Math.toRadians(i*angle))* rayon, (float)Math.sin(Math.toRadians(i*angle))*rayon,-hauteur/2);
	                s1[i] = new Sommet((float)Math.cos(Math.toRadians(i*angle))* rayon, (float)Math.sin(Math.toRadians(i*angle))*rayon,hauteur/2);
	            }
	            //Base du dessus
	            
	            for(int i=0; i<sides; i++) {
	                int j = (i+1)%sides;
	                cylindre.add(new Rectangle(s[i],new Sommet(0,0,-hauteur/2),s[j],new Sommet(0,0,0)));
	                cylindre.add(new Rectangle(s[i],s1[i],s[j],s1[j]));
	                cylindre.add(new Rectangle(s1[i],new Sommet(0,0,hauteur/2),s1[j],new Sommet(0,0,0)));
	                //cylindre.add(new Rectangle(s1[i],new Sommet((float)Math.cos(Math.toRadians(i*angle))* rayon, (float)Math.sin(Math.toRadians(i*angle))*rayon,-hauteur/2),s1[j],new Sommet((float)Math.cos(Math.toRadians(i*angle))* rayon, (float)Math.sin(Math.toRadians(i*angle)),-hauteur/2)));
	            }
	            
	            for(Rectangle r : cylindre){
	                r.write(fw, new float[] {rayon,hauteur,-8997});
	            }
	            //scale += 1 + 1;
	            
	            fw.write("endsolid Test\n");
	            fw.flush();
	            fw.close();
	        } catch (Exception e) {
	            //TODO: handle exception
	            e.printStackTrace();
	        }
		
	}

}
