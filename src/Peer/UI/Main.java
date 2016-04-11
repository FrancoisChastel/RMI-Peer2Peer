package Peer.UI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 * Made By Chastel François
 */
public class Main extends Application {

    /**
     * Définie une page principal ayant pour référence main.fxml
     * se situera à l'intérieur les différents écran affichées
     * il sert de "zone de chargement" des écrans ou de "parents"
     *
     * @param stage
     * @throws Exception
     */
    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("P2P Chat - Démonstration V0.1");

        stage.setScene(
                createScene(
                        loadMainPane()
                )
        );

        stage.show();
    }

    /**
     * Charge le fichier main fxml.
     * Charge le navigateur de page VistaNagigator.
     * Charge le premier écran dans la zone fxml parent.
     *
     * @return la page chargé.
     * @throws java.io.IOException if the pane could not be loaded.
     */
    private Pane loadMainPane() throws IOException {
        FXMLLoader loader = new FXMLLoader();

        Pane mainPane = (Pane) loader.load(
                getClass().getResourceAsStream(
                        VistaNavigator.MAIN
                )
        );

        MainController mainController = loader.getController();

        VistaNavigator.setMainController(mainController);
        VistaNavigator.loadVista(VistaNavigator.MESSENGER);

        return mainPane;
    }

    /**
     * Créer l'application principal d'affichage.
     * Charge le CSS pour tous les écrans.
     *
     * @param mainPane l'application principal.
     *
     * @return the created scene.
     */
    private Scene createScene(Pane mainPane) {
        Scene scene = new Scene(
                mainPane
        );


        return scene;
    }

    public static void main(String[] args) throws RemoteException, NotBoundException, AlreadyBoundException
    {
        launch(args);
    }
}
