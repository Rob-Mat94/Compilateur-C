package main;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 
 * 
 * Interface pour générer les fichiers (implémenter générate)
 */
public interface Forme3DGenerator  {
	
	/**
	 * 
	 * @param file_path Nom du fichier
	 * @return Un objet File correspondant au fichier ouvert avec le paramètre
	 * @throws IOException
	 */
	default public File createFile(final String file_path) throws IOException
	{
		File fichier = new File(file_path);
	     if(fichier.createNewFile())
	    	return fichier;
	     return null;
	}
	
	/**
	 * Cette méthode sera implémenter afin d'écrire la description de l'objet en format STL dans le fichier file_path 
	 * @param file_path Nom du fichier
	 * @throws IOException
	 */
	public void generate(final String file_path)throws IOException;
	
	
}
