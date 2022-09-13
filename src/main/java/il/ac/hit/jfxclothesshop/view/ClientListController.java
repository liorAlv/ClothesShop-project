package il.ac.hit.jfxclothesshop.view;

import il.ac.hit.jfxclothesshop.JdbcDriverSetup;
import il.ac.hit.jfxclothesshop.person.Client;
import il.ac.hit.jfxclothesshop.util.GraphicsUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TextField;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;

import static il.ac.hit.jfxclothesshop.session.SessionContext.getInstance;

@Component
@FxmlView("clientListPage.fxml")
public class ClientListController {

    @FXML
    private TextField searchClientField;

    @FXML
    private TableView<Client> dataTable;
    @FXML
    private TableColumn<Client, Integer> idCTableColumn;
    @FXML
    private TableColumn<Client, String> nameCTableColumn;
    @FXML
    private TableColumn<Client, String> emailCTableColumn;
    @FXML
    private TableColumn<Client, String> addressCTableColumn;
    @FXML
    private TableColumn<Client, String> phoneCTableColumn;



    private final ObservableList<Client> clientObservableList = FXCollections.observableArrayList();

    public void initialize() {

        //push double click on row and open new scene
        dataTable.setRowFactory(tv ->{
            TableRow<Client> row = new TableRow<>();

            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    var client = row.getItem();
                    getInstance().setCurrentClient(client);
                    clientObservableList.clear();
                    GraphicsUtils.openWindow(event, ChangeClientController.class);
                }
                });
            return row;
        });

        try {
            //show the data on table view
            List<Client> c = JdbcDriverSetup.getDao(Client.class).queryForAll();
            idCTableColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
            nameCTableColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            emailCTableColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
            addressCTableColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
            phoneCTableColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
            clientObservableList.addAll(c);
            dataTable.setItems(clientObservableList);

            //search on data table
            FilteredList<Client> filteredData = new FilteredList<>(clientObservableList, client -> true);

            searchClientField.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredData.setPredicate(client -> {
                    //if no search value then display all records or what ever records it current have. no change
                    if (newValue.isBlank() || newValue.isEmpty() || newValue==null){
                        return true;
                    }
                    String searchKeyWord = newValue.toLowerCase();
                    return client.getName().toLowerCase().contains(searchKeyWord) ||
                            client.getEmail().toLowerCase().contains(searchKeyWord) ||
                            client.getPhone().toLowerCase().contains(searchKeyWord) ||
                            client.getAddress().toLowerCase().contains(searchKeyWord)||
                            Integer.toString(client.getId()).contains(searchKeyWord);

                });
            });

            SortedList<Client> sortedData = new SortedList<>(filteredData);

            //Bind sorted result with table view
            sortedData.comparatorProperty().bind(dataTable.comparatorProperty());

            //Apply filtered and sorted data to the table view
            dataTable.setItems(sortedData);

        } catch (SQLException e) {
            //todo: handle error
            e.printStackTrace();
        }
    }

    public void onAddClientButtonClient(ActionEvent event) {
        clientObservableList.clear();   //That there will be no duplicates in data table
        GraphicsUtils.openWindow(event, AddClientController.class);//Move between pages
    }


    public void onBackButtonClick(ActionEvent event) {
        clientObservableList.clear();   //That there will be no duplicates in data table
        GraphicsUtils.openWindow(event, ItemsListController.class);//Move between pages
    }


}
