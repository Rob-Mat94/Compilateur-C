package main;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;

import tool.Rectangle;
import tool.Sommet;
import tool.Triangle;


/**
 * Représente une boite avec un trou en 3D décrite dans un fichier STL Binaire
 * 
 */
public class BoxHoleBinary extends Forme3D implements Forme3DGenerator {

	public BoxHoleBinary(float width, float height, float length) {
		super(width, height, length);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void generate(String file_path) throws IOException {
		  Sommet s1 = new Sommet (0,0,0);
	        Sommet s2 = new Sommet (0,length,0);
	        Sommet s3 = new Sommet (width,0,0);
	        Sommet s4 = new Sommet (width,length,0);
	        Sommet s5 = new Sommet (0,0,height);
	        Sommet s6 = new Sommet (width,0,height);
	        Sommet s7 = new Sommet (0,length,height);
	        Sommet s8 = new Sommet (width,length,height);
	       
	        List<Rectangle> rectangle = new ArrayList<Rectangle>();
	        Rectangle r2 = new Rectangle(s1,s3,s5,s6);
	        rectangle.add(r2);
	        Rectangle r3 = new Rectangle(s2,s4,s7,s8);
	        rectangle.add(r3);
	        Rectangle r4 = new Rectangle (s1,s2,s5,s7);
	        rectangle.add(r4);
	        Rectangle r5 = new Rectangle (s3,s4,s6,s8);
	        rectangle.add(r5);
	       
	        Sommet ss1 = new Sommet (width/3,length,height);
	        Sommet ss2 = new Sommet (2*width/3,length,height);
	        Sommet ss3 = new Sommet (width/3,0,height);
	        Sommet ss4 = new Sommet (2*width/3,0,height);
	       
	        Rectangle r6 = new Rectangle (s5,s7,ss3,ss1);
	        rectangle.add(r6);
	       
	        Rectangle r7 = new Rectangle(ss4,ss2,s6,s8);
	        rectangle.add(r7);
	       
	        Sommet ss5 = new Sommet(width/3,2*length/3, height);
	        Sommet ss6 = new Sommet(2*width/3, 2*length/3,height);
	       
	        Rectangle r8 = new Rectangle (ss5,ss1,ss6,ss2);
	        rectangle.add(r8);
	       
	        Sommet ss7 = new Sommet(width/3,length/3, height);
	        Sommet ss8 = new Sommet(2*width/3, length/3,height);
	       
	        Rectangle r9 = new Rectangle (ss3,ss7,ss4,ss8);
	        rectangle.add(r9);
	       
	        Sommet ss9 = new Sommet(width/3,2*length/3, 0);
	        Sommet ss10 = new Sommet(width/3,length/3, 0);
	        Sommet ss11 = new Sommet(2*width/3, 2*length/3,0);
	        Sommet ss12 = new Sommet(2*width/3, length/3,0);
	       
	        Rectangle r10 = new Rectangle (ss9,ss5,ss11,ss6);
	        rectangle.add(r10);
	        Rectangle r11 = new Rectangle (ss11,ss6,ss12,ss8);
	        rectangle.add(r11);
	        Rectangle r12 = new Rectangle (ss12,ss8,ss10,ss7);
	        rectangle.add(r12);
	        Rectangle r13 = new Rectangle (ss10,ss7,ss9,ss5);
	        rectangle.add(r13);
	       
	        Sommet ss13 = new Sommet (width/3,0,0);
	        Sommet ss14 = new Sommet (width/3,length,0);
	        Sommet ss15 = new Sommet (2*width/3,0,0);
	        Sommet ss16 = new Sommet (2*width/3,length,0);
	       
	        Rectangle r15 = new Rectangle (s1,s2,ss13,ss14);
	        rectangle.add(r15);
	        Rectangle r16 = new Rectangle (ss15,ss16,s3,s4);
	        rectangle.add(r16);
	        Rectangle r17 = new Rectangle(ss9,ss14,ss11,ss16);
	        rectangle.add(r17);
	        Rectangle r18 = new Rectangle (ss13,ss10,ss15,ss12);
	        rectangle.add(r18);
	       
	       
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
	           
	            for (Rectangle r : rectangle) {
	                r.write(out);
	            }
	           
	            out.close();
	        } catch (IOException ioe) {
	            System.err.println(ioe);
	            System.exit(1);
	        }

	
	}
}
