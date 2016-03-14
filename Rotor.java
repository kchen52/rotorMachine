import java.util.Arrays;

class Rotor {
    // From the assignment outline: The circuit in rotor C(i) connects one input to one output, defines a permutation
    // from the m inputs to the m outputs of C(i)

    private int numberOfPins = 40; 
    private String pins = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789 ,.?";
    /*private Character[] pins = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z',
        '0','1','2','3','4','5','6','7','8','9',' ',',','.','?'};*/
    private int[] mappingsAsInt = new int[40]; 
    private String mappingsAsString = "";

    // Initialise the rotor based on the parameter given
    public Rotor(int someValue) {
        
        someValue *= 31;
        for (int i = 0; i < numberOfPins; i++) {
            int intValue = (int)pins.charAt(i);
            int newMapping = (intValue * (someValue % numberOfPins)) % numberOfPins;
            while (Arrays.asList(mappingsAsInt).contains(newMapping)) {
                System.out.println("Array already has value " + newMapping);
                newMapping++;
            }
            mappingsAsInt[i] = newMapping;
        }
        for (int i = 0; i < mappingsAsInt.length; i++) {
            mappingsAsString += pins.charAt(mappingsAsInt[i]);
        }
    }



    public void printMappings() {
        for (int i = 0; i < mappingsAsString.length(); i++) {
            System.out.print(mappingsAsString.charAt(i) + ", ");
        }
        System.out.print("\n");
    }
    public void rotate() {
    }
}
