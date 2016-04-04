/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stamboom;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

/**
 *
 * @author Roy
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private ListView<Persoon> lv_personen;
    @FXML
    private TextField txt_naam;
    @FXML
    private TextField txt_leeftijd;
    @FXML
    private ComboBox<Persoon> cb_vader;
    @FXML
    private ComboBox<Persoon> cb_moeder;
    @FXML
    private Button btn_voegtoe;
    @FXML
    private Button btn_showstamboom;
    @FXML
    private Button btn_slaStamboomOp;
    @FXML
    private TextArea txtarea_stamboom;
    @FXML
    private Label label;
    @FXML
    private ToggleGroup geslacht;
    @FXML
    private Button btn_slapersonenop;

    ObservableList<Persoon> personen;
    ObservableList<Persoon> vaders;
    ObservableList<Persoon> moeders;
    @FXML
    private RadioButton rb_man;
    @FXML
    private RadioButton rb_vrouw;

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        personen = FXCollections.observableArrayList();
        vaders = FXCollections.observableArrayList();
        moeders = FXCollections.observableArrayList();/*
        Persoon p1 = new Persoon(personen.size(), "Sanderlientje Geraedts", 17, "Vrouw", null, null);
        personen.add(p1);
        Persoon p2 = new Persoon(personen.size(), "Roy Timmers", 21, "Man", null, null);
        personen.add(p2);
        Persoon p3 = new Persoon(personen.size(), "Bertje Bertram", 55, "Man", null, null);
        personen.add(p3);
        Persoon p4 = new Persoon(personen.size(), "Frits Timmers", 1, "Man", p2, p1);
        personen.add(p4);
         */
        try {
            load();
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }

        lv_personen.setItems(personen);
        setComboBoxes();
    }

    public void load() throws IOException, ClassNotFoundException
    {
        try {
            FileInputStream fis = new FileInputStream("D:\\Users\\Roy\\Dropbox\\Dropbox\\Fontys ICT\\ICT en Software\\P5\\JCC\\Stamboom\\personen.ser");
            ObjectInputStream ois = new ObjectInputStream(fis);
            ArrayList<Persoon> p = new ArrayList<>();
            p.addAll((Collection<? extends Persoon>) ois.readObject());
            ois.close();
            fis.close();
            personen.addAll(p);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setComboBoxes()
    {
        vaders.clear();
        moeders.clear();
        for (Persoon p : personen) {
            if (p.geslacht.equals("Man")) {
                vaders.add(p);
            }
            if (p.geslacht.equals("Vrouw")) {
                moeders.add(p);
            }
        }
        cb_moeder.setItems(moeders);
        cb_vader.setItems(vaders);
    }

    @FXML
    private void btn_voegPersoonToe(ActionEvent event)
    {
        if (txt_naam.getText().isEmpty() != true && txt_leeftijd.getText().isEmpty() != true) {

            String geslacht;
            if (rb_man.isSelected()) {
                geslacht = "Man";
            } else {
                geslacht = "Vrouw";
            }
            Persoon vader = cb_vader.getValue();
            Persoon moeder = cb_moeder.getValue();
            int i = Integer.parseInt(txt_leeftijd.getText());
            Persoon p = new Persoon(personen.size(), txt_naam.getText(), i, geslacht, vader, moeder);
            personen.add(p);
            if (p.geslacht.equals("Man")) {
                vaders.add(p);
            } else {
                moeders.add(p);
            }

        }

    }

    @FXML
    private void btn_showStamboom(ActionEvent event)
    {
        Persoon p;
        p = lv_personen.getSelectionModel().getSelectedItem();
        txtarea_stamboom.setText(p.stamboomAlsString());
    }

    @FXML
    private void btn_slaStamboomOp(ActionEvent event) throws FileNotFoundException
    {
        Persoon p = lv_personen.getSelectionModel().getSelectedItem();
        try (PrintWriter out = new PrintWriter(p.name + " Stamboom.txt")) {
            out.println(p.stamboomAlsString());
        }
    }

    @FXML
    private void btn_slaPersonenOp(ActionEvent event)
    {
        ArrayList<Persoon> ps = new ArrayList<>();
        ps.addAll(personen);
        try {
            FileOutputStream fileOut
                    = new FileOutputStream("personen.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(ps);
            out.close();
            fileOut.close();
            System.out.printf("Serialized data is saved");
        } catch (IOException i) {
            i.printStackTrace();
        }
    }
}
