package main;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;

import tool.Rectangle;
import tool.Sommet;
import tool.Triangle;

public class SphereBinary extends Forme3D implements Forme3DGenerator {

	private float rayon;
	private int p;
	
	private int count = 1;
	
	public SphereBinary(float width, float height, float length, int count) {
		super(width, height, length);
		this.count = 1;
		this.p = (int) height;
		this.rayon = width;
	}

	@Override
	public void generate(String file_path) throws IOException {
		List<Rectangle> rectangle = new ArrayList<Rectangle>();
		
		// Une sph�re peut �tre d�crite comme un planisph�re -> on cr�e un tableau de sommets
		// p est la pr�cision de notre planisph�re, on va cr�er des carr�s de longueur p 
		// thehta est compris entre -pi/2 et pi/2
		//phi est compris entre -pi et pi
		Sommet [][] s = new Sommet [p][p];
		
		
		for(int i =0;i<p;i++) { 
			float theta = i*180/p +90;
			for(int j =0;j<p;j++) {
				float phi = j*360/p +180;
				float x = (float) (rayon * Math.cos(theta) * Math.cos(phi));
				float y = (float) (rayon * Math.cos(theta) * Math.sin(phi));
				float z = (float) (rayon * Math.sin(theta));
				
				
				s[i][j] = new Sommet(x,y,z);
				
			}
		}
		
		for(int k =0;k<p-1;k++) {
			for(int l =0;l<p-1;l++) {
				Sommet s1= s[k][l];
				Sommet s2 = s[k+1][l];
				Sommet s3 = s[k][l+1];
				Sommet s4 = s[k+1][l+1];
				
				rectangle.add(new Rectangle(s1,s2,s3,s4));
				
			}
		}
		
		DataOutputStream out;
		 
        try {
            out = new DataOutputStream(
                    new BufferedOutputStream(
                        new FileOutputStream(file_path)));
            //  commentaire 
            for (int k = 0; k < 80; k++) {
                out.writeByte( ' ' );
            }
            
           // nombre de triangles
            ByteBuffer buf = ByteBuffer.allocate(4); 
    		buf.order(ByteOrder.LITTLE_ENDIAN);
    		buf.putInt(Triangle.nbTriangle);
    		out.write(buf.array());
            
    		
            for (Rectangle rect : rectangle) {
            	rect.write(out);
            }
            
            out.close();
        } catch (IOException ioe) {
            System.err.println(ioe);
            System.exit(1);
        }
		
	}
		
	}
	
