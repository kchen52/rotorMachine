import java.util.ArrayList;

class RotorMachine {
    // Need at least 5 security schemes
    String securitySchemes[] = {"7-11-13"};

    // Need at least 2 rotors
    ArrayList<Rotor> rotors = new ArrayList<Rotor>();

    public static void main(String args[]) {
        int securitySchemeChosen = 0;
        String securityScheme = securitySchemes[securitySchemeChosen];
        //int numberOfRotors = StringUtils.countMatches(securityScheme, "-")+1;

        //System.out.println("Number of rotors: " + numberOfRotors);

    }
}
