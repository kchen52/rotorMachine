import java.io.*;
import java.net.*;

class App {
	public static void main(String args[]) {

		RotorMachine machine = new RotorMachine();

		boolean clientMode = false;
		boolean serverMode = false;

		System.out.print("Is this being run as the client or the server? (c/s):");

		String input = System.console().readLine();
		if (input.equals("client") || input.equals("c")) {
			clientMode = true;
		} else if (input.equals("server") || input.equals("s")) {
			serverMode = true;
		} else {
			System.out.println("Self destruct mode activated. Terminating now.");
			return;
		}

		if (clientMode) {
			clientMode(machine);
		} else if (serverMode) {
			serverMode(machine);
		}
		return;
	}

	public static void clientMode(RotorMachine machine) {
		System.out.print("Enter the server's address: ");
		String serverAddress = System.console().readLine();

		System.out.print("Enter the port number: ");
		int portNumber = Integer.parseInt(System.console().readLine());

		try {
			// TODO: SET SECURITY SCHEME DYNAMICALLY
			machine.initRotor(2);
			BufferedReader fromUser = new BufferedReader (new InputStreamReader(System.in));
			Socket clientSocket = new Socket(serverAddress, portNumber);
			DataOutputStream toServer = new DataOutputStream(clientSocket.getOutputStream());
			BufferedReader fromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

			System.out.println("Connection established! Now accepting input.");
			System.out.println("Type 'exit' at anytime to close this application.");
			while (true) {
				System.out.print("Enter text: ");
				String sentence = fromUser.readLine();

				String encryptedMessage = machine.encryptMessage(sentence);

				if (sentence.equals("exit")) {
					break;
				}
				toServer.writeBytes(encryptedMessage + '\n');
				System.out.println("Waiting for server response...\r");
				String encryptedIncomingMessage = fromServer.readLine();
				if (encryptedIncomingMessage == null) {
					// The server has terminated the connection, so close our own connection
					System.out.println(" * Server has terminated the connection");
					break;
				}
				encryptedIncomingMessage = encryptedIncomingMessage.replaceAll("\\n", "");
				String decryptedIncomingMessage = machine.decryptMessage(encryptedIncomingMessage);
				System.out.println("Encrypted message from server: " + encryptedIncomingMessage);
				System.out.println("Decrypted message from server: " + decryptedIncomingMessage);
			}
			clientSocket.close();
		} catch (ConnectException e) {
			System.out.println("Connection refused. Perhaps the server is currently unreachable?");
		} catch (UnknownHostException e) {
			System.out.println("Error: IP Address of hostname \"" + serverAddress + "\" could not be determined");
		} catch (IOException e) {
		}
	}

	public static void serverMode(RotorMachine machine) {
		ServerSocket serverSocket;
		Socket clientSocket;

		System.out.print("Enter the port number: ");
		int portNumber = Integer.parseInt(System.console().readLine());
		// TODO: Check that these numbers are legit
		if (portNumber > 25565) {
			portNumber = 25565;
		} else if (portNumber < 0) {
			portNumber = 0;
		}

		System.out.println("Waiting for a connection...");

		try {
			// TODO: Diffie hellman stuff
			machine.initRotor(2);
			serverSocket = new ServerSocket(portNumber);
			clientSocket = serverSocket.accept();
			System.out.println("Connection established with " + clientSocket.getInetAddress());
			System.out.println("Type 'exit' at anytime to close this application.");
			while (true) {
				BufferedReader fromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				DataOutputStream toClient = new DataOutputStream(clientSocket.getOutputStream());

				System.out.println("Waiting for client message...\r");
				String encryptedTextFromClient = fromClient.readLine();
				if (encryptedTextFromClient == null) {
					// The server has terminated the connection, so close our own connection
					System.out.println(" * Client has terminated the connection");
					break;
				}
				encryptedTextFromClient = encryptedTextFromClient.replaceAll("\\n", "");
				String decryptedTextFromClient = machine.decryptMessage(encryptedTextFromClient);

				System.out.println("Encrypted message from client: " + encryptedTextFromClient);
				System.out.println("Decrypted message from client: " + decryptedTextFromClient);

				// Take user input, encrypt it, and send it to client
				System.out.print("Enter text: ");
				String sentence = System.console().readLine();

				if (sentence.equals("exit")) {
					break;
				}
				String encryptedMessage = machine.encryptMessage(sentence);
				toClient.writeBytes(encryptedMessage + "\n");
			}
			clientSocket.close();
			serverSocket.close();
		} catch (SocketException e) {
		} catch (IOException e) {
		} catch (NullPointerException e) {
		}
	} 
}
