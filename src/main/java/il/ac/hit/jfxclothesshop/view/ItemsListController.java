package il.ac.hit.jfxclothesshop.view;

import il.ac.hit.jfxclothesshop.shop.clothing.Clothing;
import il.ac.hit.jfxclothesshop.shop.sales.SalesManager;
import il.ac.hit.jfxclothesshop.shop.sales.Inventory;
import il.ac.hit.jfxclothesshop.person.Client;
import il.ac.hit.jfxclothesshop.util.GraphicsUtils;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;

import static il.ac.hit.jfxclothesshop.session.SessionContext.getInstance;

@Component
@FxmlView("itemsListPage.fxml")
public class ItemsListController {
    @FXML
    private Button newBookButton;
    @FXML
    private TextField searchBookField;
    @FXML
    private Button reportButton;
    @FXML
    private Label userData;

    @FXML
    private TableView<Clothing> dataTable;
    @FXML
    private TableColumn<Clothing, Integer> idTableColumn;
    @FXML
    private TableColumn<Clothing, String> titleTableColumn;
    @FXML
    private TableColumn<Clothing, String> authorTableColumn;
    @FXML
    private TableColumn<Clothing, String> categoryTableColumn;
    @FXML
    private TableColumn<Clothing, String> locationTableColumn;
    @FXML
    private TableColumn<Clothing, String> borrowedByColumn;

    @Autowired
    private Inventory inventory;

    @Autowired
    private SalesManager bookBorrowManager;

    @Autowired
    private FxWeaver fxWeaver;



    private final ObservableList<Clothing> bookObservableList = FXCollections.observableArrayList();

    public ItemsListController() {

    }

    // enter the books data to the list
    public void initialize() {
        userData.setText(getInstance().getCurrentUser().toString());
        newBookButton.setVisible(getInstance().isCurrentUserManager());
        reportButton.setVisible(getInstance().isCurrentUserManager());
        dataTable.setRowFactory(tv -> {          //push double click on row and open new scene
            TableRow<Clothing> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    var item = row.getItem();
                    getInstance().setCurrentBook(item);
                    bookObservableList.clear();
                    GraphicsUtils.openWindow(event, InfoItemController.class);
                }
            });
            return row;
        });


        try {
            List<Clothing> c = inventory.getAllBooks();
            //show the data on table view
            idTableColumn.setCellValueFactory(new PropertyValueFactory<>("sku"));
            titleTableColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
            authorTableColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
            categoryTableColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
            locationTableColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
            borrowedByColumn.setCellValueFactory(cellData -> {
                var item = cellData.getValue();
                try {
                    Client activeClientForBook = bookBorrowManager.getActiveClientForBook(item.getSku());
                    if (activeClientForBook != null) {
                        return new ReadOnlyStringWrapper(Integer.toString(activeClientForBook.getId()));
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return null;
            });

            bookObservableList.addAll(c);
            dataTable.setItems(bookObservableList);
            //search on data table
            FilteredList<Clothing> filteredData = new FilteredList<>(bookObservableList, item -> true);

            searchBookField.textProperty().addListener(((observableValue, oldValue, newValue) -> {
                filteredData.setPredicate(item -> {

                    //if no search value then display all records or what ever records it current have. no change
                    if (newValue.isEmpty() || newValue.isBlank() || newValue == null) {
                        return true;
                    }

                    String searchKeyWord = newValue.toLowerCase();
                    return item.getTitle().toLowerCase().contains(searchKeyWord) ||
                            item.getAuthor().toLowerCase().contains(searchKeyWord) ||
                            item.getCategory().toLowerCase().contains(searchKeyWord) ||
                            item.getLocation().toLowerCase().contains(searchKeyWord) ||
                            Integer.toString(item.getSku()).contains(searchKeyWord);
                });
            }));

            SortedList<Clothing> sortedData = new SortedList<>(filteredData);

            //Bind sorted result with table view
            sortedData.comparatorProperty().bind(dataTable.comparatorProperty());

            //Apply filtered and sorted data to the table view
            dataTable.setItems(sortedData);


        } catch (SQLException e) {
            //todo: handle error
            e.printStackTrace();
        }
    }

    @FXML
    private void onClientButtonClick(ActionEvent event) {
        bookObservableList.clear();   //That there will be no duplicates in data table
        GraphicsUtils.openWindow(event, ClientListController.class); //Move between pages
    }

    public void onNewBookButtonClick(ActionEvent event) {
        bookObservableList.clear();   //That there will be no duplicates in data table
        GraphicsUtils.openWindow(event, AddItemController.class);//Move between pages
    }

    public void onReportButtonClick(ActionEvent event) {
        bookObservableList.clear();//That there will be no duplicates in data table
        GraphicsUtils.openWindow(event, ReportController.class);//Move between pages
    }
}
