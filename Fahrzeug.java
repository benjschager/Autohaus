public abstract class Fahrzeug { 
    private int preis;
    private String farbe;
    private int kmStand;
    private String marke;
    
    protected String typ;

    public Fahrzeug(String color, String mrk, int kmst, int prs) {
        farbe = color;
        kmStand = kmst;
        preis = prs;
        marke = mrk;
    }

    public void anzeigen() {
        System.out.println("Preis: " + preis);
        System.out.println("Farbe: " + farbe);
        System.out.println("kmStand: " + kmStand + " km");
        System.out.println("Marke: " + marke);
    }

    /*
	public String anzeigen(char a) {
		return ("Preis: " + preis + \n
					 "Farbe: " + farbe + \n
					 "kmStand: " + kmStand + " km" + \n
					 "Marke: " + marke);
	}*/

    public String getTyp() {
        return typ; }
    
    public int getPreis() {
        return preis; }

    public void setPreis(int updatedPreis) {
        preis = updatedPreis; }

    public int getKmStand() {
        return kmStand; }

    public void setKmStand(int updatedStand) {
        kmStand = updatedStand; }

    public String getFarbe() {
        return farbe; }

    public void setFarbe(String updatedFarbe) {
        farbe = updatedFarbe; }

    public String getMarke() {
        return marke; }

    public void setMarke(String updatedMarke) {
        marke = updatedMarke; }
}
