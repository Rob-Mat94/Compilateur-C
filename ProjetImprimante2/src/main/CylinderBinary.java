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

public class CylinderBinary extends Forme3D implements Forme3DGenerator {

	/* width, height, length */
	public CylinderBinary(float sides, float rayon, float hauteur) {
		super(sides, rayon, hauteur);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void generate(String file_path) throws IOException {
		
		int sides = (int)width;
		float rayon = height;
		float hauteur = length;
		
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
           
            for ( Rectangle t : cylindre) {
                t.write(out);
            }
           
            out.close();
        } catch (IOException ioe) {
            System.err.println(ioe);
            System.exit(1);
        }
	}

}
