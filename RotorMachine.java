import java.util.ArrayList;

class RotorMachine {
	public RotorMachine() {}
	private String pins = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789 ,.?";

	// Need at least 5 security schemes, with at least 2 rotors each
	private String securitySchemes[] = {"7-11-13", "5-31-19-7", "73-23", "211-73-31", "403-173-73"};

	private ArrayList<Rotor> rotors = new ArrayList<Rotor>();
	private int securityScheme = 0;

	public void initRotor(int securitySchemeChosen) {

		if (0 < securitySchemeChosen || securitySchemeChosen > 4) {
			// Default to 0
			securitySchemeChosen = 0;
		}

		String securityScheme = securitySchemes[securitySchemeChosen];
		int numberOfRotors = numberOfTimesCharAppeared('-', securityScheme)+1;

		for (int i = 0; i < numberOfRotors; i++) {
			int rotorInitValue = Integer.parseInt(securityScheme.split("-")[i]);
			rotors.add(new Rotor(rotorInitValue));
		}
	}

	public String encryptMessage(String message) {
		// If the message isn't already uppercase, uppercase it
		message = message.toUpperCase();

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
				//char old = currentCharacter;
				currentCharacter = rotors.get(j).encryptChar(currentCharacter);
				//System.out.println(old + " converted to " + currentCharacter);
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

	public String decryptMessage(String message) {

		// According to Java language spec, the default value for each element in the array is set to 0,
		// which is what we want here
		int numberOfRotors = rotors.size();
		int[] rotorRotationStatus = new int[numberOfRotors];

		String decryptedMessage = "";
		for (int i = 0; i < message.length(); i++) {
			char currentChar = message.charAt(i);
			for (int j = rotors.size()-1; j >= 0; j--) {
				int index = rotors.get(j).getMapping().indexOf(currentChar);
				if (index == -1) { 
					currentChar = '#';
				} else {
					currentChar = pins.charAt(index);
				}
			}
			decryptedMessage += currentChar;

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

		return decryptedMessage;
	}

	private int numberOfTimesCharAppeared(char lookFor, String temp) {
		int counter = 0;
		for (int i = 0; i < temp.length(); i++) {
			if (temp.charAt(i) == lookFor) {
				counter++;
			}
		}
		return counter;
	}
}
