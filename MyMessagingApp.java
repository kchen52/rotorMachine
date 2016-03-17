import java.io.*;
import java.net.*;

class App {
	public static void main(String args[]) {
		boolean clientMode = false;
		boolean serverMode = false;

		System.out.print("Is this being run as the client or the server? (c/s):");

		String input = System.console().readLine();
		if (input.equals("client") || input.equals("c")) {
			clientMode = true;
		} else if (input.equals("server") || input.equals("s")) {
			serverMode = true;
		} else {
			// Nonsense answer... figure out what to do with this later
			return;
		}

		RotorMachine.initRotor();
		if (clientMode) {
			// ---------------------------------------------------------------
			// Do client stuff here...
			// ---------------------------------------------------------------
			System.out.print("Enter the server's address: ");
			String serverAddress = System.console().readLine();

			System.out.print("Enter the port number: ");
			// TODO: error handling for bad input here
			int portNumber = Integer.parseInt(System.console().readLine());

			try {
				BufferedReader fromUser = new BufferedReader (new InputStreamReader(System.in));
				Socket clientSocket = new Socket(serverAddress, portNumber);
				DataOutputStream toServer = new DataOutputStream(clientSocket.getOutputStream());
				BufferedReader fromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				Boolean someCondition = false;

				System.out.println("Connection established! Now accepting input.");
				while (!someCondition) {
					String sentence = fromUser.readLine();

					// TODO: SET SECURITY SCHEME DYNAMICALLY
					String encryptedMessage = RotorMachine.encryptMessage(sentence);

					toServer.writeBytes(encryptedMessage + '\n');
					if (sentence.equals("terminate")) {
						someCondition = true;
					}
					System.out.print("Enter text: ");
					String encryptedIncomingMessage = fromServer.readLine();
					encryptedIncomingMessage = encryptedIncomingMessage.replaceAll("\\n", "");
					String decryptedIncomingMessage = RotorMachine.decryptMessage(encryptedIncomingMessage);
					System.out.println("From server: " + decryptedIncomingMessage);
				}
				clientSocket.close();
			} catch (ConnectException e) {
				System.out.println("Connection refused. Perhaps the server is currently unreachable?");
			} catch (UnknownHostException e) {
				System.out.println("Error: IP Address of hostname \"" + serverAddress + "\" could not be determined");
			} catch (IOException e) {
				e.printStackTrace();
			}
			// ---------------------------------------------------------------
			// END OF CLIENT STUFF
			// ---------------------------------------------------------------
		} else if (serverMode) {
			// ---------------------------------------------------------------
			// Do server stuff here
			// ---------------------------------------------------------------
			ServerSocket serverSocket;
			Socket clientSocket;

			System.out.print("Enter the port number: ");
			// TODO: error handling for bad input here
			int portNumber = Integer.parseInt(System.console().readLine());

			try {
				serverSocket = new ServerSocket(portNumber);
				clientSocket = serverSocket.accept();
				while (true) {
					BufferedReader fromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
					DataOutputStream toClient = new DataOutputStream(clientSocket.getOutputStream());

					String encryptedTextFromClient = fromClient.readLine();
					encryptedTextFromClient = encryptedTextFromClient.replaceAll("\\n", "");
					String decryptedTextFromClient = RotorMachine.decryptMessage(encryptedTextFromClient);
					
					System.out.println("From client: " + decryptedTextFromClient);
					// Take user input, encrypt it, and send it to client
					System.out.print("Enter text: ");
					String sentence = System.console().readLine();

					// TODO: SET SECURITY SCHEME DYNAMICALLY
					String encryptedMessage = RotorMachine.encryptMessage(sentence);
					toClient.writeBytes(encryptedMessage + "\n");
				}
			} catch (SocketException e) {
				/*clientSocket.close();
				  serverSocket.close();*/
			} catch (IOException e) {
				e.printStackTrace();
			} catch (NullPointerException e) {
				/*clientSocket.close();
				  serverSocket.close();*/
			}
		} 
		return;
	}

}
