public class Flugzeug extends Fahrzeug
{
    private int maxFlughoehe;
    private int traglast;

    public Flugzeug(String color, String mrk, int kmst, int prs, int altitude, int trgl) {
        super(color, mrk, kmst, prs);
        maxFlughoehe = altitude;
        traglast = trgl;
        typ = "Flugzeug";
    }

    public void anzeigen() {
        System.out.println("Bei diesem Fahrzeug handelt es sich um ein " + typ + ".");
        super.anzeigen();
        System.out.println("Maximale Flughöhe: " + maxFlughoehe);
        System.out.println("Maximale Traglast: " + traglast);
        System.out.println();
    }
    
    /*
    public String anzeigen(char a) {
        return ("Bei diesem Fahrzeug handelt es sich um ein " + typ + "." + \n
            super.anzeigen('a') + \n
            "Maximale Flughöhe: " + maxFlughoehe + \n    
            "Maximale Traglast: " + traglast);
    }*/
    
    public int getFlughoehe() {
        return maxFlughoehe;
    }
    public int getTraglast() {
        return traglast;
    }
}
