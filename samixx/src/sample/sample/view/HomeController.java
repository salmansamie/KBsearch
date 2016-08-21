package sample.sample.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import sample.Main;

import java.io.IOException;

/**
 * samixx project
 *
 * @author Salman Samie
 *         21 Aug, 2016
 */

public class HomeController {

    @FXML
    private Button signout;

    @FXML
    void onclickSignout() throws IOException{
        Main.showLogin();
    }
}
