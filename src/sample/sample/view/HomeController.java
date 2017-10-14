package sample.sample.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
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
import java.sql.SQLException;
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
    FilteredList<SearchEngine> filteredData = new FilteredList<>(data, e -> true);

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

            engineBox.clear();
            keywordBox.clear();
            queryBox.clear();
            data.clear();

            preparedStatement = connection.prepareStatement(query);
            rs = preparedStatement.executeQuery();

            while (rs.next()) {
                data.add(new SearchEngine(
                        rs.getString("short_name"),
                        rs.getString("keyword"),
                        rs.getString("url")));
                table.setItems((data));

                //Testing outputs  ::::  Done 100%
                System.out.println(rs.getString("short_name"));
                System.out.println(rs.getString("keyword"));
                System.out.println(rs.getString("url"));
                System.out.println("\n");


            }
            preparedStatement.close();
            rs.close();
            System.out.println("\nData loaded. Done :::: 100%\n");

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
    TextField searchIdSamix;

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


    @FXML
    public void AddNewEngine() throws SQLException {
        String query = "INSERT INTO keywords (short_name, keyword, url, favicon_url) VALUES(?,?,?,'')";
        preparedStatement = null;
        rs = null;

        String tempStorage = null;
        try {
            tempStorage = engineBox.getText();
            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, engineBox.getText());
            preparedStatement.setString(2, keywordBox.getText());
            preparedStatement.setString(3, queryBox.getText());
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            preparedStatement.executeUpdate();
            preparedStatement.close();

            engineBox.clear();
            keywordBox.clear();
            queryBox.clear();
            data.clear();

            loadData();
        }

        Main.showInformationAlertBox("New Search Engine '" + tempStorage + "' has been saved successfully.");

    }

    static String tempStorage;

    @FXML
    public void importToBoxes() {
        try {
            SearchEngine searchEngine = table.getSelectionModel().getSelectedItem();

            String query = "SELECT * FROM keywords";
            preparedStatement = connection.prepareStatement(query);

            tempStorage = engineBox.getText();
            engineBox.setText(searchEngine.getShort_name());
            keywordBox.setText(searchEngine.getKeyword());
            queryBox.setText(searchEngine.getUrl());

            preparedStatement.close();
            rs.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    @FXML
    public void deleteEngine() {

        try {
            tempStorage = engineBox.getText();
            SearchEngine searchEngine = table.getSelectionModel().getSelectedItem();
            String query = "DELETE FROM keywords WHERE short_name=?";

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, searchEngine.getShort_name());

            preparedStatement.executeUpdate();
            preparedStatement.close();
            rs.close();

            loadData();
        } catch (SQLException e) {
            System.out.println(e);
        }

        loadData();
        Main.showInformationAlertBox("Engine '" + tempStorage + "' has been deleted.");
    }


    public void updateEngine() {
        String query = "UPDATE keywords set short_name=?, keyword=?, url=? WHERE short_name='" + tempStorage + "'";
        try {
            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, engineBox.getText());
            preparedStatement.setString(2, keywordBox.getText());
            preparedStatement.setString(3, queryBox.getText());

            preparedStatement.executeUpdate();
            preparedStatement.close();

            Main.showInformationAlertBox("Engine " + tempStorage + " has been changed and saved.");
            loadData();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }


    @FXML
    public void systemSearch() {
        loadData();
        searchIdSamix.textProperty().addListener((observableValue, oldValue, newValue) -> {
            filteredData.setPredicate(searchEng -> {

                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();
                if (searchEng.getShort_name().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (searchEng.getUrl().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (searchEng.getKeyword().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });
        SortedList<SearchEngine> sortedData = new SortedList<SearchEngine>(filteredData);
        sortedData.comparatorProperty().bind(table.comparatorProperty());
        table.setItems(sortedData);
    }

}
