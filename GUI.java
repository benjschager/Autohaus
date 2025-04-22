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

/*    private JPanel createBox;
    private JComboBox cTypCustom;
    private JButton bCustom;
    private JTextField[] tCustom; */

    private JComboBox<String> vehicleDropdown;
    private CardLayout cardLayout;
    private JPanel cardsPanel;
    private JPanel createPanel;

    public GUI(Autohaus pAh) {
        super("Autohaus");
        autohaus = pAh;
        setSize(1000,800);
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
        createPanel.setBounds(625, 260, 280, 300);
        createPanel.setLayout(new BorderLayout());

        String[] typs = {"Auto", "Bus", "Flugzeug", "LKW", "Wohnmobil"};
        vehicleDropdown = new JComboBox<>(typs);

        cardLayout = new CardLayout();
        cardsPanel = new JPanel(cardLayout);

        cardsPanel.add(createInputCard(4), "Auto");
        cardsPanel.add(createInputCard(5), "Bus");
        cardsPanel.add(createInputCard(6), "Flugzeug");
        cardsPanel.add(createInputCard(5), "LKW");
        cardsPanel.add(createInputCard(5), "Wohnmobil");
        
        vehicleDropdown.addActionListener(this);

        createPanel.add(vehicleDropdown, BorderLayout.NORTH);
        createPanel.add(cardsPanel, BorderLayout.CENTER);
        add(createPanel);
        // ==========================================================

        setVisible(true);
    }

    public GUI() {
        autohaus = new Autohaus(100);
        new GUI(autohaus);
        //autohaus.vieleAutos(500);
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

        // Wenn auf diesen Knopf gedruckt wird, sollen so viele Autos hinzugefugt werden,
        // wie auf dem Slider eingestellt sind.
        if (ae.getSource() == bCreate) {
            autohaus.vieleAutos(sCreate.getValue());
        }

        // Wenn auf diesen Knopf gedruckt wird, soll das ausgewahlte Fahrzeug verkauft werden.
        if (ae.getSource() == remove) {
            autohaus.verkaufen(selected);
            //popupWindow("Success", new Dimension(300,80),"1 Fahrzeug wurde entfernt");
        }

        // Wenn dieses Menu aufgerufen wird, muss eventuell die Anzahl an input-Feldern
        // veraendert werden.
        if (ae.getSource() == vehicleDropdown) {
            createSelected = (String) vehicleDropdown.getSelectedItem();
            cardLayout.show(cardsPanel, createSelected);
        }

        // updates the colors of all spots to be green when not empty, red otherwise
        for (int i = 0; i < parkplatzButtons.length; i ++) {
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

        if(autohaus.isAuto(selected)) {
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
        if(infoBox.getComponentCount()==0) {
            lDefault.setText("Nothing to see here");
            infoBox.add(lDefault);
        }

        sCreate.setMaximum(autohaus.getParkplatzLength()-autohaus.findeLetztes());
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
        char[] against = {'0','1','2','3','4','5','6','7','8','9'};
        for (int i = 0; i < input.length(); i++) {
            char a = input.charAt(i);
            int n = 0;
            for (int j = 0; j < against.length; j++) {
                if (a == against[j]) { n++; }
            }
            if (n == 0) { return false; }
        }
        return true;
    }

    private static JPanel createInputCard(int numFields) {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(numFields, 1, 5, 5));

        for (int i = 1; i <= numFields; i++) {
            JTextField textField = new JTextField();
            textField.setBorder(BorderFactory.createTitledBorder("Field " + i));
            panel.add(textField);
        }
        
        return panel;
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
