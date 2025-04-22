import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;

//import javax.swing.event.ChangeListener;
//import javax.swing.event.ChangeEvent;

public class GUI extends JFrame implements ActionListener, ChangeListener{
    private Dimension screenSize;
    private Autohaus autohaus; 
    private JButton[] parkplatzButtons;

    private int selected;
    private String createSelected;

    private JButton bCreate;
    private JLabel lCreate;
    private JSlider sCreate;
    private JLabel dCreate;

    private JButton remove;

    private JPanel infoBox;
    private JLabel lTyp;
    private JLabel lPreis;
    private JLabel lFarbe;
    private JLabel lKmStand;
    private JLabel lMarke;
    private JLabel lFlughoehe;
    private JLabel lTraglast;
    private JLabel lSitzplaetze;
    private JLabel lLadeflaeche;
    private JLabel lBetten;
    private JLabel lDefault;

    private JComboBox<String> vehicleDropdown;
    private CardLayout cardLayout;
    private JPanel cardsPanel;
    private JPanel createPanel;
    private JButton createButton;

    private JTextField[] autoFields;
    private JTextField[] busFields;
    private JTextField[] flugzeugFields;
    private JTextField[] lKWFields;
    private JTextField[] wohnmobilFields;

    public GUI(Autohaus pAh) {
        super("Autohaus");
        autohaus = pAh;
        screenSize = new Dimension(1000, 800);
        setSize(screenSize);
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        //isResizable();

        // ==========================================================
        // Der Block, der den Parkplatz reprasentiert.
        parkplatzButtons = new JButton[autohaus.getParkplatzLength()];
        selected = 0;

        int buttonAreaX = 10;
        int buttonAreaY = 10;
        int buttonAreaWidth = 585;
        int buttonAreaHeight = 500;

        JPanel arrayPanel = new JPanel();
        arrayPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));

        for (int i = 0; i <autohaus.getParkplatzLength(); i++) {
            JButton button = new JButton("" + i);
            button.setPreferredSize(new Dimension(65, 45));
            button.setBackground(Color.red);
            parkplatzButtons[i]=button;
            arrayPanel.add(parkplatzButtons[i]);
            parkplatzButtons[i].addActionListener(this);
        }
        arrayPanel.setPreferredSize(new Dimension(380, 50*(int)Math.ceil(autohaus.getParkplatzLength()/8) + 50));

        JScrollPane scrollPane = new JScrollPane(arrayPanel);
        scrollPane.setBounds(buttonAreaX, buttonAreaY, buttonAreaWidth, buttonAreaHeight);

        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        add(scrollPane);
        // ==========================================================

        // ==========================================================
        // Die 'vieleAutos' Funktion
        bCreate = new JButton("hinzufugen");
        bCreate.setBounds(210, 560, 150, 40);
        bCreate.addActionListener(this);
        add(bCreate);

        lCreate = new JLabel("Neue Fahrzeuge hinzufugen:");
        lCreate.setBounds(20, 550, 200, 20);
        add(lCreate);

        int initial = 0;
        sCreate = new JSlider(initial, (int) autohaus.getParkplatzLength()-autohaus.findeLetztes()-1, 0);
        sCreate.setBounds(55, 580, 150, 15);
        sCreate.addChangeListener(this);
        add(sCreate);

        dCreate = new JLabel(String.valueOf(initial));
        dCreate.setBounds(25, 575, 50, 20);
        add(dCreate);
        // ==========================================================

        // ==========================================================
        // Auto entfernen
        remove = new JButton("entfernen");
        remove.setBounds(380,560, 150, 40);
        remove.addActionListener(this);
        add(remove);
        // ==========================================================

        // ==========================================================
        // Box with information about the selected vehicel
        infoBox = new JPanel();
        infoBox.setLayout(new BoxLayout(infoBox, BoxLayout.Y_AXIS));
        infoBox.setBounds(666, 50, 176, 180);
        infoBox.setBorder(new LineBorder(Color.blue, 2));

        lTyp = new JLabel();
        lPreis = new JLabel();
        lFarbe = new JLabel();
        lKmStand = new JLabel();
        lMarke = new JLabel();
        lFlughoehe = new JLabel();
        lTraglast = new JLabel();
        lSitzplaetze = new JLabel();
        lLadeflaeche = new JLabel();
        lBetten = new JLabel();
        lDefault = new JLabel("Nothing to see here");

        infoBox.add(lDefault);
        add(infoBox);
        // ==========================================================

        // ==========================================================
        // create new vehicels with custom attributes
        createPanel = new JPanel();
        createPanel.setBounds(625, 260, 280, 320);
        createPanel.setLayout(new BorderLayout());

        String[] typs = {"Auto", "Bus", "Flugzeug", "LKW", "Wohnmobil"};
        vehicleDropdown = new JComboBox<>(typs);

        createButton = new JButton("Hinzufuegen");
        createButton.addActionListener(this);

        cardLayout = new CardLayout();
        cardsPanel = new JPanel(cardLayout);

        autoFields = new JTextField[4];
        fillJTextField(autoFields);
        busFields = new JTextField[5];
        fillJTextField(busFields);
        flugzeugFields = new JTextField[6];
        fillJTextField(flugzeugFields);
        lKWFields = new JTextField[5];
        fillJTextField(lKWFields);
        wohnmobilFields = new JTextField[5];
        fillJTextField(wohnmobilFields);
        System.out.println("Fields initialised");
        cardsPanel.add(createInputCard(4, "Auto"), "Auto");
        cardsPanel.add(createInputCard(5, "Bus"), "Bus");
        cardsPanel.add(createInputCard(6, "Flugzeug"), "Flugzeug");
        cardsPanel.add(createInputCard(5, "LKW"), "LKW");
        cardsPanel.add(createInputCard(5, "Wohnmobil"), "Wohnmobil");
        System.out.println("cardsPanel done");

        vehicleDropdown.addActionListener(this);

        createPanel.add(vehicleDropdown, BorderLayout.NORTH);
        createPanel.add(cardsPanel, BorderLayout.CENTER);
        createPanel.add(createButton, BorderLayout.SOUTH);
        add(createPanel);
        // ==========================================================

        setVisible(true);
    }

    public GUI() {
        autohaus = new Autohaus(100);
        new GUI(autohaus);
        // autohaus.vieleAutos(500);
    }

    public void actionPerformed(ActionEvent ae) {
        // Wenn einer der Knöpfe, die die Plätze der Array repräsendtieren,
        // angeklickt werden, soll 'selected' angepasst werden
        for (int i = 0; i < parkplatzButtons.length; i++) {
            if (ae.getSource() == parkplatzButtons[i]) {
                selected = i;
            }
        }

        Fahrzeug f = (Fahrzeug) autohaus.parkplatz[selected];

        // Wenn auf diesen Knopf gedruckt wird, sollen so viele Autos hinzugefugt
        // werden,
        // wie auf dem Slider eingestellt sind.
        if (ae.getSource() == bCreate) {
            autohaus.vieleAutos(sCreate.getValue());
        }

        // Wenn auf diesen Knopf gedruckt wird, soll das ausgewahlte Fahrzeug verkauft
        // werden.
        if (ae.getSource() == remove) {
            autohaus.verkaufen(selected);
            // popupWindow("Success", new Dimension(300,80),"1 Fahrzeug wurde entfernt");
        }

        // Wenn dieses Menu aufgerufen wird, muss eventuell die Anzahl an input-Feldern
        // veraendert werden.
        if (ae.getSource() == vehicleDropdown) {
            createSelected = (String) vehicleDropdown.getSelectedItem();
            cardLayout.show(cardsPanel, createSelected);
        }

        // creates new vehicles with the given parameters after checking if they are of
        // the correct type
        if (ae.getSource() == createButton) {
            // this is for Auto
            if ((String) vehicleDropdown.getSelectedItem() == "Auto") {
                String pFarbe = autoFields[0].getText();
                String pMarke = autoFields[1].getText();
                int pKmStand;
                int pPreis;
                if (!checkInt(autoFields[2].getText()) | !checkInt(autoFields[3].getText())) {
                    popupWindow("Error", new Dimension(300, 80), "Not all inputs are of the correct data typ");
                } else {
                    pKmStand = Integer.parseInt(autoFields[2].getText());
                    pPreis = Integer.parseInt(autoFields[3].getText());
                    Auto auto1 = new Auto(pFarbe, pMarke, pKmStand, pPreis);
                    autohaus.parken(auto1);
                }
            }
            // this is for Bus
            else if ((String) vehicleDropdown.getSelectedItem() == "Bus") {
                String pFarbe = busFields[0].getText();
                String pMarke = busFields[1].getText();
                int pKmStand;
                int pPreis;
                int pSitzplaetze;

                if (!checkInt(busFields[2].getText()) | !checkInt(busFields[3].getText())
                        | !checkInt(busFields[4].getText())) {
                    popupWindow("Error", new Dimension(300, 80), "Not all inputs are of the correct data typ");
                } else {
                    pKmStand = Integer.parseInt(busFields[2].getText());
                    pPreis = Integer.parseInt(busFields[3].getText());
                    pSitzplaetze = Integer.parseInt(busFields[4].getText());
                    Bus bus1 = new Bus(pFarbe, pMarke, pKmStand, pPreis, pSitzplaetze);
                    autohaus.parken(bus1);
                }
            }
            // this is for Flugzeug
            else if ((String) vehicleDropdown.getSelectedItem() == "Flugzeug") {
                String pFarbe = flugzeugFields[0].getText();
                String pMarke = flugzeugFields[1].getText();
                int pKmStand;
                int pPreis;
                int pFlughoehe;
                int pTraglast;

                if (!checkInt(flugzeugFields[2].getText()) | !checkInt(flugzeugFields[3].getText()) |
                        !checkInt(flugzeugFields[4].getText()) | !checkInt(flugzeugFields[5].getText())) {
                    popupWindow("Error", new Dimension(300, 80), "Not all inputs are of the correct data typ");
                } else {
                    pKmStand = Integer.parseInt(flugzeugFields[2].getText());
                    pPreis = Integer.parseInt(flugzeugFields[3].getText());
                    pFlughoehe = Integer.parseInt(flugzeugFields[4].getText());
                    pTraglast = Integer.parseInt(flugzeugFields[5].getText());
                    Flugzeug flugzeug1 = new Flugzeug(pFarbe, pMarke, pKmStand, pPreis, pFlughoehe, pTraglast);
                    autohaus.parken(flugzeug1);
                }
            }
            // this is for LKW
            else if ((String) vehicleDropdown.getSelectedItem() == "LKW") {
                String pFarbe = lKWFields[0].getText();
                String pMarke = lKWFields[1].getText();
                int pKmStand;
                int pPreis;
                int pLadeflaeche;

                if (!checkInt(lKWFields[2].getText()) | !checkInt(lKWFields[3].getText())
                        | !checkInt(lKWFields[4].getText())) {
                    popupWindow("Error", new Dimension(300, 80), "Not all inputs are of the correct data typ");
                } else {
                    pKmStand = Integer.parseInt(lKWFields[2].getText());
                    pPreis = Integer.parseInt(lKWFields[3].getText());
                    pLadeflaeche = Integer.parseInt(lKWFields[4].getText());
                    LKW lKW1 = new LKW(pFarbe, pMarke, pKmStand, pPreis, pLadeflaeche);
                    autohaus.parken(lKW1);
                }
            }
            // this is for Wohnmobil
            else if ((String) vehicleDropdown.getSelectedItem() == "Wohnmobil") {
                String pFarbe = wohnmobilFields[0].getText();
                String pMarke = wohnmobilFields[1].getText();
                int pKmStand;
                int pPreis;
                int pBetten;

                if (!checkInt(wohnmobilFields[2].getText()) | !checkInt(wohnmobilFields[3].getText())
                        | !checkInt(wohnmobilFields[4].getText())) {
                    popupWindow("Error", new Dimension(300, 80), "Not all inputs are of the correct data typ");
                } else {
                    pKmStand = Integer.parseInt(wohnmobilFields[2].getText());
                    pPreis = Integer.parseInt(wohnmobilFields[3].getText());
                    pBetten = Integer.parseInt(wohnmobilFields[4].getText());
                    Wohnmobil wohnmobil1 = new Wohnmobil(pFarbe, pMarke, pKmStand, pPreis, pBetten);
                    autohaus.parken(wohnmobil1);
                }
            }
        }

        // updates the colors of all spots to be green when not empty, red otherwise
        for (int i = 0; i < parkplatzButtons.length; i++) {
            if (autohaus.isAuto(i)) {
                parkplatzButtons[i].setBackground(Color.green);
            } else {
                parkplatzButtons[i].setBackground(Color.red);
            }
        }

        if (f instanceof Auto) {
            lTyp.setText("Fahrzeugtyp: Auto");
            infoBox.add(lTyp);
        } else {
            infoBox.remove(lTyp);
        }

        if (f instanceof LKW) {
            LKW g = (LKW) f;
            lTyp.setText("Fahrzeugtyp: LKW");
            infoBox.add(lTyp);
            lLadeflaeche.setText("Ladefläche: " + g.getLadeflaeche() + "m^2");
            infoBox.add(lLadeflaeche);
        } else {
            infoBox.remove(lLadeflaeche);
        }

        if (f instanceof Flugzeug) {
            Flugzeug g = (Flugzeug) f;
            lTyp.setText("Fahrzeugtyp: Flugzeug");
            infoBox.add(lTyp);
            lFlughoehe.setText("Flughöhe: " + g.getFlughoehe());
            infoBox.add(lFlughoehe);
            lTraglast.setText("Traglast: " + g.getTraglast() + "kg");
            infoBox.add(lTraglast);
        } else {
            infoBox.remove(lFlughoehe);
            infoBox.remove(lTraglast);
        }

        if (f instanceof Bus) {
            Bus g = (Bus) f;
            lTyp.setText("Fahrzeugtyp: Bus");
            infoBox.add(lTyp);
            lSitzplaetze.setText("Sitzplätze: " + g.getSitzplaetze());
            infoBox.add(lSitzplaetze);
        } else {
            infoBox.remove(lSitzplaetze);
        }

        if (f instanceof Wohnmobil) {
            Wohnmobil g = (Wohnmobil) f;
            lTyp.setText("Fahrzeugtyp: Wohnmobil");
            infoBox.add(lTyp);
            lBetten.setText("Betten: " + g.getBetten());
            infoBox.add(lBetten);
        } else {
            infoBox.remove(lBetten);
        }

        if (autohaus.isAuto(selected)) {
            lPreis.setText("Preis: " + f.getPreis());
            infoBox.add(lPreis);
            lFarbe.setText("Farbe: " + f.getFarbe());
            infoBox.add(lFarbe);
            lKmStand.setText("KmStand: " + f.getKmStand());
            infoBox.add(lKmStand);
            lMarke.setText("Marke: " + f.getMarke());
            infoBox.add(lMarke);
        } else {
            infoBox.remove(lTyp);
            infoBox.remove(lPreis);
            infoBox.remove(lFarbe);
            infoBox.remove(lKmStand);
            infoBox.remove(lMarke);
        }

        infoBox.remove(lDefault);
        if (infoBox.getComponentCount() == 0) {
            lDefault.setText("Nothing to see here");
            infoBox.add(lDefault);
        }

        sCreate.setMaximum(autohaus.getParkplatzLength() - autohaus.findeLetztes());
        infoBox.setVisible(false);
        infoBox.setVisible(true);
    }

    public void stateChanged(ChangeEvent e) {
        dCreate.setText(String.valueOf(sCreate.getValue()));
    }

    public Autohaus getAutohaus() {
        return autohaus;
    }

    private static boolean checkInt(String input) {
        char[] against = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
        for (int i = 0; i < input.length(); i++) {
            char a = input.charAt(i);
            int n = 0;
            for (int j = 0; j < against.length; j++) {
                if (a == against[j]) {
                    n++;
                }
            }
            if (n == 0) {
                return false;
            }
        }
        return true;
    }

    private static void fillJTextField(JTextField[] input) {
        for (int i = 0; i < input.length; i++) {
            input[i] = new JTextField();
        }
    }

    private JPanel createInputCard(int numFields, String typ) {
        System.out.println("calling createInputCard");
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(numFields, 1, 5, 5));

        if (typ == "Bus") {
            System.out.println("Bus");
            firstFour(busFields);
            busFields[4].setBorder(BorderFactory.createTitledBorder("Sitzplaetze: "));
            for (int i = 0; i < 5; i++) {
                panel.add(busFields[i]);
            }
        }
        else if (typ == "Auto") {
            System.out.println("Auto");
            firstFour(autoFields);
            for (int i = 0; i < 4; i++) {
                panel.add(autoFields[i]);
            }
        }
        else if (typ == "LKW") {
            firstFour(lKWFields);
            lKWFields[4].setBorder(BorderFactory.createTitledBorder("Ladeflaeche: "));
            for (int i = 0; i < 5; i++) {
                panel.add(lKWFields[i]);
            }
        }
        else if (typ == "Wohnmobil") {
            firstFour(wohnmobilFields);
            wohnmobilFields[4].setBorder(BorderFactory.createTitledBorder("Betten: "));
            for (int i = 0; i < 5; i++) {
                panel.add(wohnmobilFields[i]);
            }
        }
        else if (typ == "Flugzeug") {
            firstFour(flugzeugFields);
            flugzeugFields[4].setBorder(BorderFactory.createTitledBorder("Flughoehe: "));
            flugzeugFields[5].setBorder(BorderFactory.createTitledBorder("Traglast: "));
            for (int i = 0; i < 6; i++) {
                panel.add(flugzeugFields[i]);
            }
        }
        
        return panel;
    }

    // creates textFields with the first four elements of the 'Fahrzeug' constructor
    private void firstFour(JTextField[] input) {
        for (int i = 0; i < 4; i++) {
            switch (i) {
                case 0:
                    input[i].setBorder(BorderFactory.createTitledBorder("Farbe:"));
                break;
                case 1:
                    input[i].setBorder(BorderFactory.createTitledBorder("Marke: "));
                break;
                case 2:
                    input[i].setBorder(BorderFactory.createTitledBorder("KmStand: "));
                break;
                case 3:
                    input[i].setBorder(BorderFactory.createTitledBorder("Preis: "));
                break;
            }
        }
    }

    private void popupWindow(String name, Dimension size, String displayText) {
        JFrame popup = new JFrame(name);
        popup.setSize(size);    
        JLabel label = new JLabel(displayText);
        //label.setBounds(size.width/5,size.height/2-20,(int)(size.width*0.6),40);
        popup.setLocationRelativeTo(null);
        popup.add(label);
        popup.setVisible(true);
    }
}
