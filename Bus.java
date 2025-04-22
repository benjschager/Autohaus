public class Bus extends Fahrzeug {
    
    private int sitzplaetze;
    
    public Bus(String color, String mrk, int kmst, int prs, int szp) {
        super(color, mrk, kmst, prs);
        sitzplaetze = szp;
        typ =  "Bus";
    }
    
    public void anzeigen() {
        System.out.println("Bei diesem Fahrzeug handelt es sich um ein " + typ + ".");
        super.anzeigen();
        System.out.println("Anzahl Sitzpläze: " + sitzplaetze);
        System.out.println();
    }

    /*
	public String anzeigen(char a) {
		return ("Bei diesem Fahrzeug handelt es sich um ein " + typ + "." \n
					super.anzeigen('a') + \n
					"Anzahl Sitzplätze: " + sitzplaetze);
	}*/
        
    public int getSitzplaetze() {
        return sitzplaetze; }
    
    public void setSitzplaetze(int n) {
        sitzplaetze = n; }
}
