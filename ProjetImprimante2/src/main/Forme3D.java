package main;
/**
 * 
 * 
 * Classe abstraite représentant une Forme3D
 * 
 */
abstract public class Forme3D implements Forme3DGenerator
{	
	/**
	 * longueur de la forme
	 */
	protected float length;
	/**
	 * largeur de la forme
	 */
	protected float width;
	/**
	 * hauteur de la forme
	 */
	protected float height;
	
	protected float[] v_normal = new float[3]; 
	
	public Forme3D(float width,float height, float length)
	{
		this.length = length;
		this.width = width;
		this.height = height;
		
		v_normal[0] = length;
		v_normal[1] = width;
		v_normal[2] = height;
	}
	
	public float[] getV_normal() {
		return v_normal;
	}
	
	public float getLength() {
		return length;
	}

	public float getWidth() {
		return width;
	}

	public float getHeight() {
		return height;
	}
}
