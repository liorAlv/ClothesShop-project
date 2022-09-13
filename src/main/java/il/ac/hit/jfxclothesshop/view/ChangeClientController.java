package il.ac.hit.jfxclothesshop.view;

import il.ac.hit.jfxclothesshop.shop.sales.SalesManager;
import il.ac.hit.jfxclothesshop.person.Client;
import il.ac.hit.jfxclothesshop.person.managing.ClientUtils;
import il.ac.hit.jfxclothesshop.person.managing.ClientManager;
import il.ac.hit.jfxclothesshop.session.SessionContext;
import il.ac.hit.jfxclothesshop.util.GraphicsUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;

@Component
@FxmlView("changeClientPage.fxml")
public class ChangeClientController {
    @FXML
    private Label idLabel;
    @FXML
    private TextField changeNameTextField;
    @FXML
    private TextField changeEmailTextField;
    @FXML
    private TextField changeAddressTextField;
    @FXML
    private TextField changePhoneTextField;
    @FXML
    private Label informationLabel;

    @Autowired
    private ClientManager clientManager;
    @Autowired
    private SalesManager bookBorrow;

    public void initialize() {
        //loaded the data on specific client
        Client client = SessionContext.getInstance().getCurrentClient();
        idLabel.setText(String.valueOf(client.getId()));
        changeNameTextField.setText(client.getName());
        changeEmailTextField.setText(client.getEmail());
        changeAddressTextField.setText(client.getAddress());
        changePhoneTextField.setText(client.getPhone());
    }


    //Move between pages
    public void onBackButtonClick(ActionEvent event) {
        GraphicsUtils.openWindow(event, ClientListController.class);
    }

    //delete client
    public void onRemoveClientButtonClick(ActionEvent event) {

        try {
            clientManager.deleteClient(String.valueOf(Integer.parseInt(idLabel.getText())));
            bookBorrow.deleteBookBorrowByClient(Integer.parseInt(idLabel.getText()));
            GraphicsUtils.openWindow(event, ClientListController.class);  //Move between pages
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }


    public void onSaveChangesButtonClick(ActionEvent event) {
        try {
            //if the field empty or phone number starting with 0 and end with number
            if (ClientUtils.isClientInputNotOkay(changePhoneTextField, changeNameTextField, changeEmailTextField, changeAddressTextField)) {
                informationLabel.setText("Please check that you filled the information correctly");
            } else {      //push the new(change) data
                Client client = SessionContext.getInstance().getCurrentClient();
                client.setName(changeNameTextField.getText());
                client.setAddress(changeAddressTextField.getText());
                client.setEmail(changeEmailTextField.getText());
                client.setPhone(changePhoneTextField.getText());

                clientManager.updateClient(client);
                GraphicsUtils.openWindow(event, ClientListController.class);//Move between pages

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }


}



