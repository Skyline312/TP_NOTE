package server;

import java.io.IOException;
import java.util.ArrayList;

import javax.management.ObjectInstance;

import shared.Pokemon;

public class TestServer {

	/**
	 * The server application entry point
	 * @param args
	 */
	public static void main(String[] args) throws IOException, ClassNotFoundException
	{
		PokemonBank serverApp;
		
		try {
			serverApp = new PokemonBank();
			while(true) {
				serverApp.handleClient();
			}
			/*
			 *  To simply the project, we will not handle properly shutting down the server.
			 *  In the terminal, you can use Ctrl+C to exit a running application.
			 */
			
			// After properly shutting down the server, this would be a good time to call
			// serverApp.stop();
		} 
		catch (Exception e) {
			/*
			 * Exception handling is not required for this project.
			 */
		e.printStackTrace();
		}	
		
	/*	Database db = new Database("coucou");
		ArrayList<Pokemon> re = new ArrayList<>();
		re.add(new Pokemon("joefr"));
		re.add(new Pokemon("froeijp", 78));
		
		db.savePokemons(re);
		db.loadPokemons();*/
	}
}
