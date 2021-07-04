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

public class ConeBinary extends Forme3D implements Forme3DGenerator {
	
	private float TWO_PI = (float)6.2831855;
	
	public ConeBinary(float width, float height, float length) {
		super(width, height, length);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void generate(String file_path) throws IOException {
		int sides = (int)this.width;
		float rayon = this.height;
		float hauteur = this.length;
		
		
		List<Rectangle> cone = new ArrayList<Rectangle>();
		Sommet [] s = new Sommet [sides];
		float angle ;
		for(int i=0; i<sides; i++) {
            angle = TWO_PI*i/sides ;
			s[i] = new Sommet((float)Math.cos(Math.toRadians(i*angle))* rayon, (float)Math.sin(Math.toRadians(i*angle))*rayon,-hauteur/2);
			//s1[i] = new Sommet((float)Math.cos(Math.toRadians(i*angle))* rayon, (float)Math.sin(Math.toRadians(i*angle))*rayon,hauteur/2);
		}
		
		for(int i=0; i<sides; i++) {
			int j = (i+1)%sides;
			cone.add(new Rectangle(s[i],new Sommet(0,0,-hauteur/2),s[j],new Sommet(0,0,0)));
			cone.add(new Rectangle(s[i],new Sommet(0,0,hauteur/2),s[j],new Sommet(0,0,0)));
		}
		
		DataOutputStream out;

        try {
            out = new DataOutputStream(
                    new BufferedOutputStream(
                        new FileOutputStream(file_path)));
            //  commentaire
            for (int i = 0; i < 80; i++) {
                out.writeByte( ' ' );
            }
           
           // nombre de triangles
            ByteBuffer buf = ByteBuffer.allocate(4);
            buf.order(ByteOrder.LITTLE_ENDIAN);
            buf.putInt(Triangle.nbTriangle);
            out.write(buf.array());
           
            for ( Rectangle t : cone) {
                t.write(out);
            }
           
            out.close();
        } catch (IOException ioe) {
            System.err.println(ioe);
            System.exit(1);
        }
		
	}

}
