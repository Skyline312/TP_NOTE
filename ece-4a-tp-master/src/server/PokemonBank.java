package server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import shared.Request;
import shared.Pokemon;

/**
 * This class represents the server application, which is a Pokémon Bank.
 * It is a shared account: everyone's Pokémons are stored together.
 * @author strift
 *
 */
public class PokemonBank {
	
	public static final int SERVER_PORT = 3000;
	public static final String DB_FILE_NAME = "pokemons.db";
	
	/**
	 * The database instance
	 */
	private Database db;
	
	/**
	 * The ServerSocket instance
	 */
	private ServerSocket server;
	
	/**
	 * The Pokémons stored in memory
	 */
	private ArrayList<Pokemon> pokemons;
	
	/**
	 * Constructor
	 * @throws IOException
	 * @throws ClassNotFoundException 
	 */
	public PokemonBank() throws IOException, ClassNotFoundException 
	{
		/*
		 * TODO
		 * Here, you should initialize the Database and ServerSocket instances.
		 */
		this.db = new Database(DB_FILE_NAME);
		this.server = new ServerSocket(SERVER_PORT);
		

		System.out.println("Banque Pokémon (" + DB_FILE_NAME + ") démarrée sur le port " + SERVER_PORT);
		
		// Let's load all the Pokémons stored in database
		this.pokemons = this.db.loadPokemons();
		this.printState();
	}
	
	/**
	 * The main loop logic of your application goes there.
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public void handleClient() throws IOException, ClassNotFoundException 
	{
		ObjectOutputStream outputStream = null;
		ObjectInputStream inputStream = null;
		
		System.out.println("En attente de connexion...");
		/*
		 * TODO
		 * Here, you should wait for a client to connect.
		 */
		Socket serverSocket = server.accept();
		System.out.println("client connect�");
		
		/*
		 * TODO
		 * You will one stream to read and one to write.
		 * Classes you can use:
		 * - ObjectInputStream
		 * - ObjectOutputStream
		 * - BankOperation
		 */
			outputStream = new ObjectOutputStream(serverSocket.getOutputStream());
			inputStream = new ObjectInputStream(serverSocket.getInputStream());
		
		// For as long as the client wants it
		boolean running = true;
		while (running) 
		{
			/*
			 * TODO
			 * Here you should read the stream to retrieve a Request object
			 */
			Request request = (Request)(inputStream.readObject());
				
			/*
			 * Note: the server will only respond with String objects.
			 */
			switch(request) {
			case LIST:
				System.out.println("Request: LIST -- server");
				if (this.pokemons.size() == 0) {
					/*
					 * TODO
					 * There is no Pokémons, so just send a message to the client using the output stream.
					 */
					outputStream.writeObject("There is no pokemon");
					
				} 
				else 
				{	
					/*
					 * TODO
					 * Here you need to build a String containing the list of Pokémons, then write this String
					 * in the output stream.
					 * Classes you can use:
					 * - StringBuilder
					 * - String
					 * - the output stream
					 */
					for(int i = 0 ; i < pokemons.size();i++)
						outputStream.writeObject(pokemons.get(i).toString());
					
				}
				break;
				
			case STORE:
				System.out.println("Request: STORE -- server");
				/*
				 * TODO
				 * If the client sent a STORE request, the next object in the stream should be a Pokémon.
				 * You need to retrieve that Pokémon and add it to the ArrayList.
				 */
				this.pokemons.add((Pokemon)inputStream.readObject());
				
				
				/*
				 * TODO
				 * Then, send a message to the client so he knows his Pokémon is safe.
				 */
				outputStream.writeObject(pokemons.get(pokemons.size()-1).toString() + " a bien ete recu");
				break;
				
			case CLOSE:
				System.out.println("Request: CLOSE -- server");
				/*
				 * TODO
				 * Here, you should use the output stream to send a nice 'Au revoir !' message to the client. 
				 */
				outputStream.writeObject("Au revoir!");
				
				// Closing the connection
				System.out.println("Fermeture de la connexion...");
				running = false;
				break;
				
			}
			this.printState();
		};
		
		/*
		 * TODO
		 * Now you can close both I/O streams, and the client socket.
		 */
		if(inputStream != null)
			inputStream.close();
		if(outputStream != null)
			outputStream.close();
		
		
		/*
		 * TODO
		 * Now that everything is done, let's update the database.
		 */
		this.db.savePokemons(this.pokemons);
		
	}
	
	/**
	 * Print the current state of the bank
	 */
	private void printState() {
		System.out.print("[");
		for (int i = 0; i < this.pokemons.size(); i++) {
			if (i > 0) {
				System.out.print(", ");
			}
			System.out.print(this.pokemons.get(i));
		}
		System.out.println("]");
	}
	
	/**
	 * Stops the server.
	 * Note: This function will never be called in this project.
	 * @throws IOException 
	 */
	public void stop() throws IOException {
		this.server.close();
	}
}
