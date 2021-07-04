package main;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public abstract class Collections {
	static final private Map<String,String> urlCollection = new HashMap<>();
	
	static final private Map<String,String[]> dimCollection = new HashMap<>();


	public static Map<String, String> getUrlcollection() {
		return urlCollection;
	}
	public static Map<String, String[]> getDimcollection() {
		return dimCollection;
	}
	
	public static void initCollections()
	{
		
		urlCollection.put("Box", "capture2bis.png");
		dimCollection.put("Box",new String[] {"Width (mm)", "Length (mm)", "Height (mm)"});
	
		urlCollection.put("Cylinder","cylindre.png");
		dimCollection.put("Cylinder",new String[] {"Sides", "Height (mm)", "Ray (mm)"});
		
		urlCollection.put("Cone","cone.png");
		dimCollection.put("Cone",new String[] {"Sides", "Height (mm)", "Ray (mm)"});
	
		urlCollection.put("Box Hole","capturetrou.png");
		dimCollection.put("Box Hole",new String[] {"Width (mm)", "Length (mm)", "Height (mm)"});
		
		urlCollection.put("Sphere","sphere.png");
		dimCollection.put("Sphere",new String[] {"Ray (mm)", "0", "Precision"});
	
		urlCollection.put("Pyramid","pyramide.png");
		dimCollection.put("Pyramid",new String[] {"Diameter (mm)", "Sides", "Height (mm)"});
	
	}
}
