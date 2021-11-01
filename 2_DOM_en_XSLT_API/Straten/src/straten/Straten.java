/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package straten;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ComboBoxListCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import straten.bo.Gemeente;
import straten.bo.Sector;
import straten.bo.Stadsdeel;
import straten.bo.Straat;
import straten.bo.Wijk;
import straten.xml.XMLGemeente;

/**
 *
 * @author tiwi
 */
public class Straten extends Application {

    private final Gemeente gemeente = new XMLGemeente("stratenInGent.xml");
    private final int HGAP = 10;

    @Override
    public void start(Stage primaryStage) {
        ComboBox stadsdelen = new ComboBox();
        stadsdelen.getItems().addAll(gemeente.getStadsdelen());
        stadsdelen.setConverter(new StringConverter<Stadsdeel>() {
            @Override
            public String toString(Stadsdeel stadsdeel) {
                return stadsdeel.getNaam();
            }

            @Override
            public Stadsdeel fromString(String string) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

        });
        stadsdelen.setEditable(false);
        ComboBox wijken = new ComboBox();
        wijken.setConverter(new StringConverter<Wijk>() {
            @Override
            public String toString(Wijk wijk) {
                return wijk.getNaam();
            }

            @Override
            public Wijk fromString(String string) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

        });
        stadsdelen.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Stadsdeel stadsdeel = (Stadsdeel) stadsdelen.getValue();
                wijken.getItems().clear();
                wijken.getItems().addAll(stadsdeel.getWijken());
            }
        });

        ListView straten = new ListView();
        straten.setCellFactory(ComboBoxListCell.forListView(new StringConverter<Straat>() {
            @Override
            public String toString(Straat straat) {
                return straat.getNaam() + " (" + straat.getPostcode() +")";
            }

            @Override
            public Straat fromString(String string) {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        }));

        wijken.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Wijk wijk = (Wijk) wijken.getValue();
                straten.getItems().clear();
                if (wijk != null) {
                    straten.getItems().addAll(wijk.getStraten());
                }
            }
        });

        TableView sectoren = new TableView();
        TableColumn naamKolom = new TableColumn("Naam");
        TableColumn paarKolom = new TableColumn("Paar");
        TableColumn onpaarKolom = new TableColumn("Onpaar");
        naamKolom.setCellValueFactory(
                new PropertyValueFactory<Sector, String>("naam"));
        paarKolom.setCellValueFactory(
                new PropertyValueFactory<Sector, String>("paar"));
        onpaarKolom.setCellValueFactory(
                new PropertyValueFactory<Sector, String>("onpaar"));
        
        sectoren.getColumns().addAll(naamKolom, paarKolom, onpaarKolom);

        straten.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<Straat>() {
                    @Override
                    public void changed(ObservableValue<? extends Straat> ov, Straat t, Straat nieuw) {
                        sectoren.getItems().clear();
                        if (nieuw != null) {
                            sectoren.getItems().addAll(nieuw.getSectoren());
                        }
                    }
                });

        GridPane root = new GridPane();
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(HGAP, HGAP, HGAP, HGAP));
        root.setVgap(4);
        root.setHgap(10);
        root.add(new Label("Stadsdeel"), 0, 0);
        root.add(stadsdelen, 1, 0);
        root.add(new Label("Wijk"), 0, 1);
        root.add(wijken, 1, 1);
        root.add(new Label("Straten"), 0, 2, 2, 1);
        root.add(straten, 0, 3, 2, 1);
        root.add(new Label("Sectoren"), 2, 2);
        root.add(sectoren, 2, 3);

        Scene scene = new Scene(root, 800, 350);

        primaryStage.setTitle("Straten");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
