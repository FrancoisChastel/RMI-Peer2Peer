package Peer.UI;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

import java.io.IOException;

/**
 * Class usable in order to control navigation between windows.
 * @author Alan BAZARSI && François CHASTEL
 */
public class VistaNavigator {

    /**
     * Constants in order to display FXML for managing by the navigator
     */
    public static final String MAIN = "main.fxml";
    public static final String LOGIN = "vista1.fxml";
    public static final String MESSENGER = "messages.fxml";

    /** MainController's controller. */
    private static MainController mainController;

    /**
     * Instanciate MainController in order to use it navigation
     *
     * @param mainController
     */
    public static void setMainController(MainController mainController) {
        VistaNavigator.mainController = mainController;
    }

    /**
     * Load vista specified by the FXML file in the vista holder.
     *
     * Windows loaded will not be set in cache (performance's optimisation)
     * FXML is loaded again and a new hierarchy of window's node is generated every calls.
     *
     * @param fxml FXML you need to load.
     */
    public static void loadVista(String fxml) {
        try {
            mainController.setVista(
                    (Node) FXMLLoader.load(
                            VistaNavigator.class.getResource(
                                    fxml
                            )
                    )
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}