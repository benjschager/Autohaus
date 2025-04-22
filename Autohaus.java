public class Autohaus extends AutohausUtils {

    public Fahrzeug[] parkplatz;
    private int konto;

    

    // =============================================================================    

    public Autohaus(int parkplatzgroesse) {
        konto = 1000;
        parkplatz = new Fahrzeug[parkplatzgroesse];
    }

    private boolean parken(Fahrzeug f) {
        for (int i = 0; i < parkplatz.length; i++) {
            if (parkplatz[i] == null) {
                parkplatz[i] = f;
                return true;
            }
        }
        return false;
    }

    public boolean kaufen(Fahrzeug f) {
        if (f.getPreis() <= konto) {
            if (parken(f)) {
                konto -= f.getPreis();
                return true;
            }
        }
        return false;
    }

    public boolean verkaufen(Fahrzeug f) {
        for (int i = 0; i < parkplatz.length; i++) {
            if (parkplatz[i] == f) {
                konto += f.getPreis();
                parkplatz[i] = null;
                for (int j = i+1; j < parkplatz.length; j++) {
                    parkplatz[j-1] = parkplatz[j];
                }
                parkplatz[parkplatz.length-1] = null;
                return true;
            }
        }
        return false;
    }

    public boolean verkaufen(int i) {
        konto -= parkplatz[i].getPreis();
        parkplatz[i] = null;
        for (int j = i+1; j < parkplatz.length; j++) {
            parkplatz[j-1] = parkplatz[j];
        }
        parkplatz[parkplatz.length-1] = null;
        return true;
    }

    public int findeLetztes() {
        for (int i = 0; i < parkplatz.length; i++) {
            if (parkplatz[i] == null && i > 0) return i-1;
            else if (parkplatz[i] == null) return 0;
        }
        return parkplatz.length-1;
    }

    public void sort() {
        super.sort(parkplatz, 0, findeLetztes(), 't');
    }

    /**
     *  Gibt aus, auf welches Fahrzeug sich dieser Position
     *  auf dem Parkplatz befindet.
     */
    public Fahrzeug gibAuto(int pSpot) {
        return parkplatz[pSpot];
    }

    /**
     *  Gibt das Fahrzeug aus, welches sich auf der jeweiligen 
     *  Position des  Parkplatz befindet. 
     */
    public void zeigeAuto(int pSpot) {
        if (parkplatz[pSpot] != null) {
            parkplatz[pSpot].anzeigen();
        }
        else System.out.println(-1);
    }

    public boolean isAuto(int pSpot) {
        if (parkplatz[pSpot] != null) {
            return true;
        }
        return false;
    }

    /**
     *  Gibt den Preis, die Farbe, den Kilometerstand, und das 
     *  Modell aller Fahrzeuge auf dem Parkplatz in der Konsole aus.
     */
    public void zeigeAlleAutos() {
        for (int i = 0; i < findeLetztes(); i++) {
            if (parkplatz[i] != null) {
                System.out.println("Dieses Fahrzeug befindet sich auf Platz " + i +":");
                parkplatz[i].anzeigen();
            }
        }
    }

    /**
     *  Gibt die Position des gesuchten Fahrzeuges auf 
     *  dem Parkplatz zurück
     */
    public int findeAuto(Fahrzeug f) {
        for (int i = 0; i < findeLetztes(); i++) {
            if (parkplatz[i] == f)
                return i;
        }
        return -1;
    }    

    
    /**
     * Erstellt 'anzahl' viele Fahrzeuge einer zufälligen Art mit einem zufälligem 
     *  Preis (zwischen 1000 und 1999), mit einem zufäller Farbe
     *  (aus einer Liste mit 10 verschiedenen Farben), und einem 
     *  zufälligem Kilometerstand (zwischen 0 und 1000).
     */
    public void vieleAutos(int anzahl) {
        Fahrzeug[] buffer = super.utilsVieleAutos(anzahl);
        for (int i = findeLetztes(); i < Math.min(parkplatz.length, buffer.length); i ++) {
            parkplatz[i] = buffer[i];
        }
        sort();
    }

    public void schnellesFluellen() {
        for (int j = findeLetztes(); j < parkplatz.length; j++) {
            Fahrzeug fahrzeug1 = new Auto("Pink-Schwarz kariert", "NCAP", -1, -1);
            parken(fahrzeug1);
        }
    }
    
    public void uebersichtAutos() {
        for (int i = 0; i < findeLetztes(); i++) {
            if (parkplatz[i] != null) {
                System.out.println(i + ": " + parkplatz[i].getTyp());
            }
        }
    }

    private void findeAutosModell(String modell) {
        for (int i = 0; i < findeLetztes(); i++) {
            if (parkplatz[i].getMarke() == modell) {
                System.out.println();
                System.out.println("Ein Fahrzeug mit dem Modell '" + modell + "' befindet sich auf Platz " + i +".");
                System.out.println("Dies ist es:");
                parkplatz[i].anzeigen();
            }
        }
    }

    private void findeAutosFarbe(String farbe) {
        for (int i = 0; i < findeLetztes(); i++) {
            if (parkplatz[i].getFarbe() == farbe) {
                System.out.println();
                System.out.println("Ein Fahrzeug mit der Farbe '" + farbe + "' befindet sich auf Platz " + i +".");
                System.out.println("Dies ist es:");
                parkplatz[i].anzeigen();
            }
        }
    }

    private void findeAutosTyp(String typ) {
        for (int i = 0; i < findeLetztes(); i++) {
            if (parkplatz[i].getTyp() == typ) {
                System.out.println();
                System.out.println("Ein Fahrzeug mit des Typs '" + typ + "' befindet sich auf Platz " + i +".");
                System.out.println("Dies ist es:");
                parkplatz[i].anzeigen();
            }
        }
    }

    public void suche(Class pClass) {
        for (int i = 0; i < findeLetztes(); i++) {
            if (pClass.isInstance(parkplatz[i])) {
                System.out.println();
                System.out.println("Ein Fahrzeug mit des Typs '" + pClass + "' befindet sich auf Platz " + i +".");
                System.out.println("Dies ist es:");
                parkplatz[i].anzeigen();
            }
        }
    }

    public void suche(String input) {
        int n = 0;
        String[] validBrands = { "Volkswagen", "BMW", "Mercedes-Benz", "Audi",
                "Ford", "Peugeot", "Renault", "Citroën", "Hyundai", "Porsche",
                "DS Automobiles", "Fiat", "Alfa Romeo", "Lancia", "Ferrari", "Opel",
                "Lamborghini", "Volvo", "Škoda", "SEAT", "Tesla", "Stellantis", "NCAP"};

        for (int i = 0; i < validBrands.length; i++) {
            if (input == validBrands[i]) {
                findeAutosModell(input);
                n++;
            }
        }

        String[] validColors = { "Grün", "Gelb", "Blau", "Rot", "Silber", "Schwarz", "Grau", "Weiß",
                "Lila", "Braun", "Violett", "Petrol", "Lavendel", "Bordeaux", "Pink-Schwarz kariert"};

        for (int i = 0; i < validColors.length; i++) {
            if (input == validColors[i]) {
                findeAutosFarbe(input);
                n++;
            }
        }

        String[] validTyps = { "Wohnmobil", "Flugzeug", "LKW", "Auto", "Bus" };

        for (int i = 0; i < validTyps.length; i++) {
            if (input == validTyps[i]) {
                findeAutosTyp(input);
                n++;
            }
        }

        if (n == 0) {
            System.out.println("Das ist weder eine bekannte Farbe, Marke noch ein bekannter Fahrzeugtyp");
        } else {
            System.out.println("Es wurden " + n + " Fahrzeuge mit diesen Attributen gefunden"); 
        }
    }

    /**
     * Gibt die Autos auf dem Parkplatz zurück deren Preis n ∈ (von;bis) 
     */
    public void suchePreis(int von, int bis) {
        int n = 0;
        for (int i = 0; i < findeLetztes(); i++) {
            if (parkplatz[i].getPreis() > von && parkplatz[i].getPreis() < bis) {
                System.out.println();
                System.out.println("Ein Fahrzeug mit enem Preis n ∈ (" + von + ";" + bis + ") befindet sich auf Platz " + findeAuto(parkplatz[i]) + " des Parkplatzes");
                System.out.println("Dies ist es:");
                parkplatz[i].anzeigen();

                n++;
            }
        }
        if (n == 0) {
            System.out.println("Wir führen keine Autos in dieser Preisklasse");
        } else {
            System.out.println("Es wurden " + n + " Fahrzeuge mit diesen Attributen gefunden"); 
        }
    }

    /**
     * Gibt die Autos auf dem Parkplatz zurück deren kmStand ∈ (von;bis) 
     */
    public void sucheKmStand(int von, int bis) {
        int n = 0;
        for (int i = 0; i < findeLetztes(); i++) {
            if (parkplatz[i].getKmStand() > von && parkplatz[i].getKmStand() < bis) {
                System.out.println();
                System.out.println("Ein Fahrzeug mit einem Kilometerstqand n ∈ (" + von + ";" + bis + ") befindet sich auf Platz " + findeAuto(parkplatz[i]) + " des Parkplatzes");
                System.out.println("Dies ist es:");
                parkplatz[i].anzeigen();

                n++;
            }
        }
        if (n == 0) {
            System.out.println("Wir führen keine Autos mit einem Kilometerstand in diesem Intervall");
        } else {
            System.out.println("Es wurden " + n + " Fahrzeuge mit diesen Attributen gefunden"); 
        }
    }

    public int getKonto() {
        return konto; }

    public void setKonto(int updatedKonto) {
        konto = updatedKonto; }

    public int getParkplatzLength() {
        return parkplatz.length; }
}
