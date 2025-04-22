public class Wohnmobil extends Fahrzeug {

    private int betten;
    private boolean[] ausstattung = {false, false, false, false};

    public Wohnmobil(String color, String mrk, int kmst, int prs, int pBetten, boolean toilette, 
    boolean ofen, boolean mikrowelle, boolean kaffeemaschine) {
        super(color, mrk, kmst, prs);
        betten = pBetten;
        typ = "Wohnmobil";

        /* Ausstattung: [0] : Toilette
         *              [1] : Ofen
         *              [2] : Mikrowelle
         *              [3] : Kaffeemaschine */
        ausstattung[0] = toilette;
        ausstattung[1] = ofen;
        ausstattung[2] = mikrowelle;
        ausstattung[3] = kaffeemaschine;
    }
    
    public Wohnmobil(String color, String mrk, int kmst, int prs, int pBetten) {
        super(color, mrk, kmst, prs);
        for (int j = 0; j < ausstattung.length; j++) {    
            int x = (int)(Math.random()*2);
            switch(x) {
                case 0: ausstattung[j] = true; break;
                case 1: ausstattung[j] = false; break;
            }
        }
    }

    public void anzeigen() {
        int n = 0;
        System.out.println("Bei diesem Fahrzeug handelt es sich um ein " + typ + ".");
        super.anzeigen();
        System.out.println("Anzahl an Betten: " + betten);
        System.out.println("Außerdem ist folgende Ausstattung vorhanden:");
        if(ausstattung[0]) { System.out.println("Eine Toilette"); n++; }
        if(ausstattung[1]) { System.out.println("Ein Ofen"); n++; }
        if(ausstattung[2]) { System.out.println("Eine Mikrowelle"); n++; }
        if(ausstattung[3]) { System.out.println("Eine Kaffeemaschine"); n++; }
        if(n == 0) { System.out.println("-- keine --"); }
        System.out.println();
    }

    /*
	public String anzeigen(char a) {
		super.anzeigen('a');
	}*/

    public int getBetten() {
        return betten; }

    public void setBetten(int pBetten) {
        betten = pBetten; }

    public boolean getToilette() {
        return ausstattung[0]; }

    public void setToilette(boolean input) {
        ausstattung[0] = input; }

    public boolean getOfen() {
        return ausstattung[1]; }

    public void setOfen(boolean input) {
        ausstattung[1] = input; }

    public boolean getMikrowelle() {
        return ausstattung[2]; }

    public void setMikrowelle(boolean input) {
        ausstattung[2] = input; }

    public boolean getKaffeemaschine() {
        return ausstattung[3]; }

    public void setKaffeemaschine(boolean input) {
        ausstattung[3] = input; }
}
