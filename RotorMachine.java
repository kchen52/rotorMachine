class RotorMachine {
    public static void main(String args[]) {
        Rotor testRotor = new Rotor(7);
        testRotor.printMappings();
        testRotor.rotateRotors();
        testRotor.printMappings();
        testRotor.printOriginalPins();
        // Any input will be converted to uppercase
        String userInput = "HELLO WORLD";
        for (int i = 0; i < userInput.length(); i++) {
            char currentChar = Character.toUpperCase(userInput.charAt(i));
            System.out.println("Encrypting " + currentChar + " gives us " + testRotor.encryptChar(currentChar));
        }
    }
}
