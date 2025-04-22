public class LKW extends Fahrzeug
{
    private int ladeflaeche;

    public LKW(String color, String mrk, int kmst, int prs, int pLadeflaeche) {
        super(color, mrk, kmst, prs);
        ladeflaeche = pLadeflaeche;
        typ = "LKW";
    }

    public void anzeigen() {
        System.out.println("Bei diesem Fahrzeug handelt es sich um ein " + typ + ".");
        super.anzeigen();
        System.out.println("Ladefläche: " + ladeflaeche + "m^2");
        System.out.println();
    }

    /*
	public String anzeigen(char a) {
		return ("Bei diesem Fahrzeug handelt es sich um ein " + typ + "." + \n
			super.anzeigen('a') + \n
			"Ladefläche: " + ladeflaeche + "m^2");
	}*/

    public int getLadeflaeche() {
        return ladeflaeche; }

    public void setLadeflaeche(int updatedLadeflaeche) {
        ladeflaeche = updatedLadeflaeche; }
}
