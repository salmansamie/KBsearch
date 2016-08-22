package sample.sample.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.Main;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

/**
 * samixx project
 *
 * @author Salman Samie
 *         21 Aug, 2016
 */

public class HomeController implements Initializable {

    Connection connection = DbConnection.LoginConnector();

    ObservableList<SearchEngine> data = FXCollections.observableArrayList();
    //FilteredList<SearchEngine> filteredData = new FilteredList<>(data, e->true);

    PreparedStatement preparedStatement = null;
    ResultSet rs = null;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        searchenginecol.setCellValueFactory(new PropertyValueFactory<>("short_name"));
        keywordcol.setCellValueFactory(new PropertyValueFactory<>("keyword"));
        querycol.setCellValueFactory(new PropertyValueFactory<>("url"));
        loadData();
    }

    @SuppressWarnings("ThrowablePrintedToSystemOut")
    public void loadData() {
        String query = "SELECT * FROM keywords";

        try {
            preparedStatement = connection.prepareStatement(query);
            rs = preparedStatement.executeQuery();

            while (rs.next()) {
                data.add(new SearchEngine(
                        rs.getString("short_name"),
                        rs.getString("keyword"),
                        rs.getString("url")));
                table.setItems((data));
            }

            preparedStatement.close();
            rs.close();

        } catch (Exception e) {
            System.err.println(e);
        }
    }

    @FXML
    TableView<SearchEngine> table;

    @FXML
    private TableColumn<?, ?> searchenginecol;
    @FXML
    private TableColumn<?, ?> keywordcol;
    @FXML
    private TableColumn<?, ?> querycol;


    @FXML
    Button addKeyword;
    @FXML
    Button editKeyword;
    @FXML
    Button deleteKeyword;


    @FXML
    TextField searchSamix;

    @FXML
    TextField engineBox;
    @FXML
    TextField keywordBox;
    @FXML
    TextField queryBox;

    @FXML
    private Button signout;

    @FXML
    void onclickSignout() throws IOException {
        Main.showLogin();

    }

//    @FXML
//    public void AddNewEngine() throws SQLException {
//        String engine = engineBox.getText();
//        String keyword = keywordBox.getText();
//        String queryurl = queryBox.getText();
//
//    }

}
