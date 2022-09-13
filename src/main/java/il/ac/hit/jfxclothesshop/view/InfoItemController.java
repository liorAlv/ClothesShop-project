package il.ac.hit.jfxclothesshop.view;

import il.ac.hit.jfxclothesshop.JdbcDriverSetup;
import il.ac.hit.jfxclothesshop.shop.clothing.Clothing;
import il.ac.hit.jfxclothesshop.shop.sales.SalesManager;
import il.ac.hit.jfxclothesshop.shop.sales.Sales;
import il.ac.hit.jfxclothesshop.shop.sales.Inventory;
import il.ac.hit.jfxclothesshop.person.Client;
import il.ac.hit.jfxclothesshop.person.User;
import il.ac.hit.jfxclothesshop.session.SessionContext;
import il.ac.hit.jfxclothesshop.util.GraphicsUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;

import static il.ac.hit.jfxclothesshop.session.SessionContext.getInstance;


@Component
@FxmlView("infoItemPage.fxml")
public class InfoItemController {

    @FXML
    private Button removeBookButton;
    @FXML
    private Label skuLabel;
    @FXML
    private Label titleLabel;
    @FXML
    private Label authorLabel;
    @FXML
    private Label categoryLabel;
    @FXML
    private Label locationLabel;
    @FXML
    private Label isBorrowedLabel;
    @FXML
    private Label clientLabel;
    @FXML
    private TextField clientIdPhoneField;
    @FXML
    private Button borrowButton;
    @FXML
    private Button returnButton;
    @FXML
    private Label explanationText;
    @FXML
    private Label clientDoesntExist;

    @Autowired
    private SalesManager bookBorrowManager;


    @Autowired
    private FxWeaver fxWeaver;

    public void initialize() {
        //Permissions for only manager on button
        removeBookButton.setVisible(User.UserType.LIBRARIAN != getInstance().getCurrentUser().getUserType());

        //if the item is sale
        Clothing item = SessionContext.getInstance().getCurrentBook();
        Client activeClientForBook = null;
        try {
            activeClientForBook = bookBorrowManager.getActiveClientForBook(item.getSku());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //show the specific item data
        skuLabel.setText(String.valueOf(item.getSku()));
        titleLabel.setText(item.getTitle());
        authorLabel.setText(item.getAuthor());
        categoryLabel.setText(item.getCategory());
        locationLabel.setText(item.getLocation());
        if (activeClientForBook != null) {     //borrowed item
            isBorrowedLabel.setText("Borrowed");
            clientLabel.setText(activeClientForBook.getInfo());
            borrowButton.setVisible(false);
            explanationText.setVisible(false);
            clientIdPhoneField.setVisible(false);
        } else {
            isBorrowedLabel.setText("In shop");
            returnButton.setVisible(false);
        }


    }

    //delete item
    public void onRemoveBookButtonClick(ActionEvent event) {
        Inventory inventory = new Inventory();
        try {
            inventory.remove(Integer.parseInt(skuLabel.getText()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void onBackButtonClick(ActionEvent event) {
        SessionContext.getInstance().setCurrentBook(null);
        GraphicsUtils.openWindow(event, ItemsListController.class);//Move between pages
    }

    public void onBorrowButtonClick(ActionEvent event) {

        String idPhone = clientIdPhoneField.getText();
        try {
            Client client = JdbcDriverSetup.getDao(Client.class).queryForId(idPhone);
            if (client == null){
                clientDoesntExist.setText("This client does not exist");
            }
            else {

                bookBorrowManager.borrowBookByClient(SessionContext.getInstance().getCurrentBook(), client);
                onBackButtonClick(event);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }



    }

    public void onReturnButtonClick(ActionEvent actionEvent) {
        Client activeClientForBook = null;
        Clothing book = null;
        try {
            book = SessionContext.getInstance().getCurrentBook();
            activeClientForBook = bookBorrowManager.getActiveClientForBook(book.getSku());
            Sales borrowBook = JdbcDriverSetup.getDao(Sales.class).queryBuilder()
                    .where()
                    .eq("book_id", SessionContext.getInstance().getCurrentBook().getSku())
                    .and()
                    .eq("client_id", activeClientForBook.getId())
                    .and()
                    .eq("active", true)
                    .queryForFirst();
            bookBorrowManager.deactivateBookBorrow(borrowBook);
            returnButton.setVisible(false);
            borrowButton.setVisible(true);
            explanationText.setVisible(true);
            clientIdPhoneField.setVisible(true);
            isBorrowedLabel.setText("In shop");
            clientLabel.setText("");


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
