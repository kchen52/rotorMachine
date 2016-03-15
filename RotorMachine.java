import java.util.ArrayList;

class RotorMachine {
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
            System.out.println("Rotor Init value: " + rotorInitValue);
            rotors.add(new Rotor(rotorInitValue));
        }
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
