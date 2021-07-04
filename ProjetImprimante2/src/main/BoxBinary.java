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
 * Représente une boite en 3D décrite dans un fichier STL Binaire
 * 
 *
 */
public class BoxBinary extends Forme3D implements Forme3DGenerator 
{
	
	private int count = 1;
	private float scale = 0;
	
	public BoxBinary(int width, int height, int length, int count) {
		super(width, height, length);
		this.count = count;
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
		Rectangle r1 = new Rectangle (s1,s2,s3,s4);
		rectangle.add(r1);
		Rectangle r2 = new Rectangle (s5,s6,s7,s8);
		rectangle.add(r2);
		Rectangle r3 = new Rectangle(s1,s3,s5,s6);
		rectangle.add(r3);
		Rectangle r4 = new Rectangle(s2,s4,s7,s8);
		rectangle.add(r4);
		Rectangle r5 = new Rectangle (s1,s2,s5,s7);
		rectangle.add(r5);
		Rectangle r6 = new Rectangle (s3,s4,s6,s8);
		rectangle.add(r6);
		
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
