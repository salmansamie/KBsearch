package sample.sample.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import sample.Main;

import java.io.IOException;
import java.sql.SQLException;

/**
 * samixx project
 *
 * @author Salman Samie
 *         21 Aug, 2016
 */

public class LoginController {

    LoginModel loginModel = new LoginModel();


    @FXML
    TextField usernameTextField;

    @FXML
    PasswordField passwordTextField;

    @FXML
    Button LoginButton;

    @FXML
    Text errorMessage;

    @FXML
    void onclickLogin() throws IOException{

        try{
            if(loginModel.isLoginValid(usernameTextField.getText(), passwordTextField.getText()) == true) {
                Main.showHome();
            }
            else{
                errorMessage.setText("Invalid Username or Password. Try again.");
            }
        }

        catch(SQLException e){
            e.printStackTrace();
        }
    }


}
