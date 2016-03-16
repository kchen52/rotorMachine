import java.util.Arrays;

class Rotor {
    // From the assignment outline: The circuit in rotor C(i) connects one input to one output, defines a permutation
    // from the m inputs to the m outputs of C(i)

    private int numberOfPins = 40; 
    private String pins = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789 ,.?";

    private String mappings = "";

    // Initialise the rotor based on the parameter given
    public Rotor(int someValue) {
        
        someValue *= 31;
        for (int i = 0; i < numberOfPins; i++) {
            int intValue = (int)pins.charAt(i);
            int newMapping = (intValue * (someValue % numberOfPins)) % numberOfPins;

            while (mappings.indexOf(pins.charAt(newMapping)) != -1) {
                //System.out.println("Array already has value " + pins.charAt(newMapping));
                newMapping++;
                if (newMapping >= 40) {
                    newMapping = 0;
                }
            }
            mappings += pins.charAt(newMapping);
        }
		
			
    }

    public char encryptChar(char incoming) {
        int index = pins.indexOf(incoming);
        if (index == -1) {
            // An invalid incoming character, return garbage
            return '#';
        }
        return mappings.charAt(index);
    }


	public String getMapping() {
		return mappings;
	}

    public void printMappings() {
        System.out.println(mappings);
    }

    public void rotateRotor() {
        // Shift each value in mappings to the right by one, with the last value being shift into the first position 
        char oldLastValue = mappings.charAt(mappings.length()-1);
        String newMappings = "";
        newMappings += oldLastValue; 
        for (int i = 0; i < mappings.length()-1; i++) {
            newMappings += mappings.charAt(i);
        }
        mappings = newMappings;
    }

    public void rotateRotorBackwards() {
        // Shift each value in mappings to the right by one, with the last value being shift into the first position 
        char oldFirstValue = mappings.charAt(0);
        String newMappings = "";
        for (int i = 1; i < mappings.length(); i++) {
            newMappings += mappings.charAt(i);
        }
        newMappings += oldFirstValue; 
        mappings = newMappings;
    }
}
