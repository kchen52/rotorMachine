import java.util.ArrayList;

class RotorMachine {
	private static  String pins = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789 ,.?";

	// Need at least 5 security schemes
	private static String securitySchemes[] = {"7-11-13"};

	// Need at least 2 rotors
	private static ArrayList<Rotor> rotors = new ArrayList<Rotor>();

	public static void main(String args[]) {
		int securitySchemeChosen = 0;
		String securityScheme = securitySchemes[securitySchemeChosen];
		int numberOfRotors = numberOfTimesCharAppeared('-', securityScheme)+1;

		for (int i = 0; i < numberOfRotors; i++) {
			int rotorInitValue = Integer.parseInt(securityScheme.split("-")[i]);
			//System.out.println("Rotor Init value: " + rotorInitValue);
			rotors.add(new Rotor(rotorInitValue));


		}
		System.out.println(pins);
		for (Rotor rotor : rotors) {
			rotor.printMappings();
		}
		System.out.println(encryptMessage("HELLO WORLD", rotors));
		//encryptMessage(pins + pins + pins + pins + pins, rotors);
	}

	private static String encryptMessage(String message, ArrayList<Rotor> rotors) {
		// If the message isn't already uppercase, uppercase it
		message.toUpperCase();
		String encryptedMessage = "";

		// According to Java language spec, the default value for each element in the array is set to 0,
		// which is what we want here
		int numberOfRotors = rotors.size();
		int[] rotorRotationStatus = new int[numberOfRotors];

		for (int i = 0; i < message.length(); i++) {
			char currentCharacter = message.charAt(i);
			int indexOfCharacter = pins.indexOf(currentCharacter);
			

			for (int j = 0; j < rotors.size(); j++) {
				// For debug purposes
				char old = currentCharacter;
				currentCharacter = rotors.get(j).encryptChar(currentCharacter);
				System.out.println(old + " converted to " + currentCharacter);
			}
			encryptedMessage += currentCharacter;

			// Always rotate the last rotor in the sequence
			rotors.get(rotors.size()-1).rotateRotor();
			rotorRotationStatus[numberOfRotors-1]++;

			// If a rotor has done a full revolution, rotate the rotor before it, if it exists
			boolean rotateNextRotor = false;
			for (int j = numberOfRotors-1; j >= 0; j--) {
				if (rotateNextRotor) {
					rotors.get(j).rotateRotor();
					rotorRotationStatus[j]++;
					rotateNextRotor = false;
				}

				if (rotorRotationStatus[j] >= 40) {
					// Rotate the rotor before this one
					rotateNextRotor = true;
					rotorRotationStatus[j] = 0;
				} 
			}
		}
		return encryptedMessage;
	}

	private static String decryptMessage(String message, ArrayList<Rotor> rotors) {
		String decryptedMessage = "";
		for (int i = 0; i < message.length(); i++) {

		}
		
		return decryptedMessage;
	}

	private static int numberOfTimesCharAppeared(char lookFor, String temp) {
		int counter = 0;
		for (int i = 0; i < temp.length(); i++) {
			if (temp.charAt(i) == lookFor) {
				counter++;
			}
		}
		return counter;
	}
}
