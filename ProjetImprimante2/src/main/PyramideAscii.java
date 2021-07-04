package main;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import tool.Sommet;
import tool.Triangle;

public class PyramideAscii extends Forme3D implements Forme3DGenerator {

	
	private int sides;
	private float d;
	private float h;
	private float TWO_PI = (float)6.2831855;
	
	private int count = 1;
	private int scale = 0;
	
	public PyramideAscii(float width, float height, float length, int count) {
		super(width, height, length);
		this.h = height;
		this.d = width;
		this.sides = (int) length;
		this.count = count;
		
	}

	@Override
	public void generate(String file_path) throws IOException {
		 File file = new File(file_path);
			try {
	            FileWriter fw = new FileWriter(file);
	            fw.write("solid Test\n");

	            
	            for(int c = 0; c < count ; c++)
	            {
		            List<Triangle> pyramid = new ArrayList<Triangle>();		
		            Sommet [] s = new Sommet [sides];
		            
		            //crée plusieurs cotés
		            for(int i=0; i<sides; i++)
		            {
		                float ang = TWO_PI * i / sides;
		                s[i] = new Sommet((float)Math.cos(ang) * d/2 + scale, (float)Math.sin(ang)* d/2, -h/2 );
		            }
	
		            //Base de la pyramide
		            for(int i = 0; i< sides; i++)
		            {
		                int j = (i+1)%sides;
		                pyramid.add(new Triangle(s[i],s[j],new Sommet(0 + scale,0,-h/2)));
		                pyramid.add(new Triangle(s[i],s[j],new Sommet(0 + scale,0,0)));
		            }
		            
		            for(Triangle r : pyramid){
		                r.write(fw, new float[] {this.d,this.h,-8998});
		            }
		            scale += this.d + 1;
	            }
	            
	            fw.write("endsolid Test\n");
	            fw.flush();
	            fw.close();
	        } catch (Exception e) {
	            //TODO: handle exception
	            e.printStackTrace();
	        }
		
	}

}
