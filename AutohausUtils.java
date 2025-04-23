
/**
 * A Class designed to implement the 'vieleAutos' Method
 * as well as the static Methods used consisting mainly
 * of the sorting algorithm previously owned by 'Autohaus'
 * to make the structure more comprehensible
 */
public class AutohausUtils{
    public AutohausUtils() {
    }

    /** wenn criteria == 'p', dann wird nach Preis sortiert
     * wenn criteria == 'm', dann wird nach Modell sortiert
     * wenn criteria == 'b', dann wird erst nach Modell, dann nach Preis sortiert
     * wenn criteria == 't', dann wird der Typ priorisiert  */
    public static void sort(Fahrzeug[] A, int low, int high, char criteria) {

        if (low >= high || low < 0)
            return;

        int pivot;
        switch (criteria) {
            case 'p':   pivot = partitionPreis(A, low, high); break;
            case 'm':   pivot = partitionModell(A, low, high); break;
            case 'b':   pivot = partitionModellAndPreis(A, low, high); break;
            case 't':   pivot = partitionTypAndMore(A, low, high); break;
            default: return;
        }

        sort(A, low, pivot-1, criteria);
        sort(A, pivot+1, high, criteria);
    }

    private static int partitionPreis(Fahrzeug[] A, int low, int high) {
        int pivot = A[high].getPreis();
        int i = low;

        for(int j = low; j < high; j++) {
            if(A[j].getPreis() >= pivot) {
                swapFahrzeug(A, i, j); 
                i++;
            }
        }
        swapFahrzeug(A, i, high);
        return i;
    }

    private static int partitionModell(Fahrzeug[] A, int low, int high) {
        int i = low;

        for(int j = low; j < high; j++) {
            if (A[j].getMarke().compareTo(A[high].getMarke()) <= 0) {
                swapFahrzeug(A, i, j); 
                i++;
            }
        }
        swapFahrzeug(A, i, high);
        return i;
    }

    private static int partitionModellAndPreis(Fahrzeug[] A, int low, int high) {
        Fahrzeug pivot = A[high];
        int i = low;

        for (int j = low; j < high; j++) {
            int modellVergleich = A[j].getMarke().compareTo(pivot.getMarke());

            if (modellVergleich < 0 || (modellVergleich == 0 && A[j].getPreis() >= pivot.getPreis())) {
                swapFahrzeug(A, i, j);
                i++;
            }
        }

        swapFahrzeug(A, i, high);
        return i;
    }

    private static int partitionTypAndMore(Fahrzeug[] A, int low, int high) {
        Fahrzeug pivot = A[high];
        int i = low;

        for (int j = low; j < high; j++) {
            int typVergleich = A[j].getTyp().compareTo(pivot.getTyp());
            int markeVergleich = A[j].getMarke().compareTo(pivot.getMarke());

            // Erst wird nach Typ verglichen, dann nach Marke und zuletzt nach Preis
            if (typVergleich < 0 || (typVergleich == 0 && markeVergleich < 0) ||
            (typVergleich == 0 && markeVergleich == 0 && A[j].getPreis() >= pivot.getPreis())) {
                swapFahrzeug(A, i, j);
                i++;
            }
        }

        swapFahrzeug(A, i, high);
        return i;
    }

    public static void swapFahrzeug(Fahrzeug[] A, int j, int k) {
        Fahrzeug buff = A[j];
        A[j] = A[k];
        A[k] = buff;
    }

    // ============================================
    
    /**
     *  Erstellt 'anzahl' viele Fahrzeuge einer zufälligen Art mit einem zufälligem 
     *  Preis (zwischen 1000 und 1999), mit einem zufäller Farbe
     *  (aus einer Liste mit 10 verschiedenen Farben), und einem 
     *  zufälligem Kilometerstand (zwischen 0 und 1000).
     */
    public Fahrzeug[] utilsVieleAutos(int anzahl) {
        String marke = "-1";
        String farbe = "-1";
        Fahrzeug[] output = new Fahrzeug[anzahl];
        for (int i = 0; i < anzahl; i++) {
            int x = (int)(Math.random()*10);
            switch (x) {
                case 0: marke = "Volkswagen";  break;
                case 1: marke = "BMW";         break;
                case 2: marke = "Audi";        break;
                case 3: marke = "Ford";        break;
                case 4: marke = "Opel";        break;
                case 5: marke = "Mercedes-Benz"; break;
                case 6: marke = "Toyota";      break;
                case 7: marke = "Hyundai";     break;
                case 8: marke = "Stellantis";  break;
                case 9: marke = "Fiat";        break;
            }
            x = (int)(Math.random()*10);
            switch (x) {
                case 0: farbe = "Grün";     break;
                case 1: farbe = "Gelb";     break;
                case 2: farbe = "Blau";     break;
                case 3: farbe = "Rot";      break;
                case 4: farbe = "Silber";   break;
                case 5: farbe = "Schwarz";  break;
                case 6: farbe = "Grau";     break;
                case 7: farbe = "Weiß";     break;
                case 8: farbe = "Lila";     break;
                case 9: farbe = "Gelb";     break;
            }
            Fahrzeug fahrzeug1;
            String typ;
            x = (int)(Math.random()*5);
            switch(x) {
                case 0:
                    fahrzeug1 = new Auto(farbe, marke, (int)(Math.random()*1000),
                        (int)(Math.random()*1000)+1000);
                    typ = "Auto";
                    break;
                case 1: 
                    fahrzeug1 = new LKW(farbe, marke, (int)(Math.random()*1000),
                        (int)(Math.random()*1000)+1000, (int)(Math.random()*20)+10);
                    typ = "LKW";
                    break;
                case 2:
                    fahrzeug1 = new Flugzeug(farbe, marke, (int)(Math.random()*1000),
                        (int)(Math.random()*1000)+1000, (int)(Math.random()*1000+1000),
                        (int)(Math.random()*1000+1000));
                    typ = "Flugzeug";
                    break;
                case 3:
                    boolean[] ausstattung = new boolean[4];
                    for (int j = 0; j < ausstattung.length; j++) {    
                        x = (int)(Math.random()*2);
                        switch(x) {
                            case 0: ausstattung[j] = true; break;
                            case 1: ausstattung[j] = false; break;
                        }
                    }
                    fahrzeug1 = new Wohnmobil(farbe, marke, (int)(Math.random()*1000),
                        (int)(Math.random()*1000)+1000, (int)(Math.random()*5), (int)(Math.random()*4)+3, 
                        ausstattung[0], ausstattung[1], ausstattung[2], ausstattung[3]);
                    typ = "Wohnmobil";
                    break;
                case 4:
                    fahrzeug1 = new Bus(farbe, marke, (int)(Math.random()*1000),
                        (int)(Math.random()*1000)+1000, ((int)(Math.random()*6)+1)*10);
                    break;
                default:
                    fahrzeug1 = new Auto(farbe, marke, (int)(Math.random()*1000),
                        (int)(Math.random()*1000)+1000);
                    typ = "Auto";    
                    break; 
            }

            output[i] = fahrzeug1;
        }
        return output;
    }

    public static <T> int last(T[] array) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == null && i > 0) {
                return i-1;
            }
            else if (array[i] == null) {
                return i;
            }
        }
        return array.length;
    }
}
