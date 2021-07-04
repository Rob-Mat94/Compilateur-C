package main;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import tool.Rectangle;
import tool.Sommet;
import tool.Triangle;

/**
 * 
 * Représente une boite en 3D décrite dans un fichier STL Ascii
 *
 */
public class BoxAscii extends Forme3D implements Forme3DGenerator {
	
	private int count = 1;
	private int scale = 0;
	
	public BoxAscii(float width, float height, float length, int count ) {
		super(width, height, length);
		this.count = count;
	}

	@Override
	public void generate(final String file_path) throws IOException {
		File file = super.createFile(file_path);
		FileWriter fw = new FileWriter(file);
		
		/* Nom de la figure */
		fw.write("solid Test\n");
		
		for(int i = 0; i < count ; i++)
		{
			
			// SCALE + 0
			
			List<Rectangle> _listgeo = new ArrayList<>();
			
			// SCALE + 0
			
		
			/* face gauche */
			Sommet s1 = new Sommet(scale,0,getHeight());
			Sommet s2 = new Sommet(scale,getWidth(),0);
			Sommet s3 = new Sommet(scale,0,0);
			Sommet s4 = new Sommet(scale,getWidth(),getHeight());
			
			_listgeo.add(new Rectangle(new Triangle(s1,s2,s3), new Triangle(s4,s1,s2)));
			
			/* face droite */
			s1 = new Sommet(getLength() + scale,getWidth(),getHeight());
			s2 = new Sommet(getLength()+ scale,0,getHeight());
			s3 = new Sommet(getLength()+ scale, getWidth(), 0);
			s4 = new Sommet(getLength()+ scale,0,0);
			
			_listgeo.add(new Rectangle(new Triangle(s1,s2,s3), new Triangle(s4, s2, s3)));
			
			/* face devant */
			s1 = new Sommet(getLength()+ scale,0, getHeight());
			s2 = new Sommet(getLength()+ scale, 0, 0);
			s3 = new Sommet(scale, 0, 0);
			s4 = new Sommet(scale,0,getHeight());
			
			_listgeo.add(new Rectangle(new Triangle(s1,s2,s3),new Triangle(s1,s4,s3)));
			
			/* face derrière */
			s1 = new Sommet(getLength() + scale,getWidth(),getHeight());
			s2 = new Sommet(getLength() + scale,getWidth(), 0);
			s3 = new Sommet(scale, getWidth(), 0);
			s4 = new Sommet(scale,getWidth(),getHeight());
			
			_listgeo.add(new Rectangle(new Triangle(s1,s2,s3),new Triangle(s1,s4,s3)));	
			
			/* bas */
			
			s1 = new Sommet(scale, 0,0);
			s2 = new Sommet(scale,getWidth(),0);
			s3 = new Sommet(getLength()+scale,getWidth(),0);
			s4 = new Sommet(scale + getLength(),0,0);
			
			_listgeo.add(new Rectangle(new Triangle(s1,s2,s3),new Triangle(s4,s1,s3)));
			
			/* haut */
			
			s1 = new Sommet(scale, 0,getHeight());
			s2 = new Sommet(scale,getWidth(),getHeight());
			s3 = new Sommet(getLength()+scale,getWidth(),getHeight());
			s4 = new Sommet(scale + getLength(),0,getHeight());
			
			_listgeo.add(new Rectangle(new Triangle(s1,s2,s3),new Triangle(s4,s1,s3)));
			
			for(Rectangle r : _listgeo)
				r.write(fw,this.getV_normal());
			
			scale += getLength() + 1;
		}
		
		/* clotûre du fichier */
		fw.write("endsolid Test\n");
		fw.flush();
		fw.close();
	}

}
