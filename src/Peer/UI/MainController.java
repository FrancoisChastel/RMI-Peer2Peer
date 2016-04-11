package Peer.UI;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;


/**
 * @author Alan BAZARSI && François CHASTEL
 */
public class MainController {

    /** Vista holder that can hold vista */
    @FXML
    private StackPane vistaHolder;

    /**
     * Replace screen displayed in vista horlder by node.
     *
     * @param node screen that you want to display.
     */
    public void setVista(Node node) {
        vistaHolder.getChildren().setAll(node);
    }

}