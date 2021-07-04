package tool;


import java.io.DataOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
/**
 * Classe représentant les 3 points du triangle
 * 
 */
public class Sommet {
	
	private float x;
	private float y;
	private float z;
	
	
	public Sommet(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	
	public byte[] toByteBuffer(float value) {
		ByteBuffer buf = ByteBuffer.allocate(4); //4 octets
		buf.order(ByteOrder.LITTLE_ENDIAN);
		buf.putFloat(value);
		return buf.array();
	}
	
	public void write (DataOutputStream out) {
		try {
			out.write(toByteBuffer(x));
			out.write(toByteBuffer(y));
			out.write(toByteBuffer(z));
			
        } catch (IOException ioe) {
            System.err.println(ioe);
            System.exit(1);
        }
		
	}
	
	public void write(FileWriter fw) throws IOException
	{
		fw.write("  vertex "+ x+" "+ y +" "+ z+"\n");
	}
}