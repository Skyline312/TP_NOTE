package server;

import java.io.*;
import java.util.ArrayList;

import shared.Pokemon;

/**
 * This class represents the server database.
 * In this project, it will simply provides an API for the server to interact with the file system.
 * @author strift
 *
 */

public class Database {

	/**
	 * The name of the file used to store the data
	 */
	private File file;
	
	/**
	 * Constructor
	 * @param fileName the name of the file used to store the data
	 */
	public Database(String fileName) {
		this.file = new File(fileName);
	}
	
	/**
	 * Load the list of Pokemons stored inside the file and returns it
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException 
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<Pokemon> loadPokemons() throws IOException, ClassNotFoundException 
	{
		ArrayList<Pokemon> data = new ArrayList<Pokemon>();

		// This checks if the file actually exists
		if(this.file.exists() && !this.file.isDirectory()) 
		{ 
			try
			{
				      InputStream file = new FileInputStream(this.file.getName());
				      InputStream buffer = new BufferedInputStream(file);
				      ObjectInput input = new ObjectInputStream (buffer);
				      try
				      {
					      do
					    	  data.add((Pokemon)(input.readObject()));
					      while(true);
				      }
				      catch(EOFException e)
				      {
				    	  
				      }
				      input.close();
				      
			}
			catch(ClassNotFoundException Ce)
			{
				Ce.printStackTrace();
			}
			catch(IOException IOe)
			{
				IOe.printStackTrace();
			}
			
			/*
			 * TODO
			 * Classes you can use:
			 * - ArrayList<Pokemon>
			 * - FileInputStream
			 * - ObjectInputStream
			 */
		} 
		else 
			System.out.println("Le fichier de sauvegarde n'existe pas.");
		
		System.out.println(data.size() + " Pokémon(s) chargé(s) depuis la sauvegarde.");
		return data;
	}
	
	/**
	 * Save the list of Pokémons received in parameters
	 * @param data the list of Pokémons
	 * @throws IOException 
	 */
	public void savePokemons(ArrayList<Pokemon> data) throws IOException 
	{
		try 
		{
			String saveFile = this.file.getName();
			
			FileOutputStream fileOut = new FileOutputStream(saveFile);
			ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
			
			System.out.println("Saving pokemons...");
			
			for(int i = 0; i<data.size(); i++)
				objectOut.writeObject(data.get(i));
			
			objectOut.close();
			System.out.println("Pokemons saved successfully");
		} 
		catch (IOException ioex) 
		{
			ioex.printStackTrace();
		}
		/*
		 * TODO
		 * Classes you can use:
		 * - FileOutputStream
		 * - ObjectOutputStream
		 */
		

		
		System.out.println("Sauvegarde effectuée... " + data.size() + " Pokémon(s) en banque.");
	}
}
