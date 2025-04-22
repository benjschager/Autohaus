public class Auto extends Fahrzeug
{

    public Auto(String color, String mrk, int kmst, int prs) {
        super(color, mrk, kmst, prs);
        typ =  "Auto";
    }

    public void anzeigen() {
        System.out.println("Bei diesem Fahrzeug handelt es sich um ein " + typ + ".");
        super.anzeigen();
        System.out.println();
    }

    /*
    public String anzeigen(char a) {
        return ("Bei diesem Fahrzeug handelt es sich um ein " + typ + "." + \n
        super.anzeigen('a'));
    }*/
}