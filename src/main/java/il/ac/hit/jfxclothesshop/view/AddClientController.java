package il.ac.hit.jfxclothesshop.view;

import il.ac.hit.jfxclothesshop.person.Client;
import il.ac.hit.jfxclothesshop.person.managing.ClientUtils;
import il.ac.hit.jfxclothesshop.person.managing.ClientManager;
import il.ac.hit.jfxclothesshop.util.GraphicsUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;

@Component
@FxmlView("addClientPage.fxml")
public class AddClientController {

    @FXML
    private TextField nameTextField;
    @FXML
    private TextField emailTextField;
    @FXML
    private TextField phoneTextField;
    @FXML
    private TextField addressTextField;
    @FXML
    private Label responseTextFalseInformation;

    @Autowired
    private FxWeaver fxWeaver;

    @Autowired
    private ClientManager clientManager;

    //Move between pages
    public void onBackButtonClick(ActionEvent event) {
        GraphicsUtils.openWindow(event, ClientListController.class);
    }


    public void onAddClientButtonClick(ActionEvent event) {
        //if the field empty or phone number starting with 0
        try {
            if (ClientUtils.isClientInputNotOkay(phoneTextField, nameTextField, emailTextField, addressTextField)) {
                responseTextFalseInformation.setText("Please check that you filled the information correctly");
            } else {      //add the information client to data
                Client client = Client
                        .builder()
                        .name(nameTextField.getText())
                        .email(emailTextField.getText())
                        .phone(phoneTextField.getText())
                        .address(addressTextField.getText())
                        .build();
                clientManager.addClient(client);
                onBackButtonClick(event); //Move between pages
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }


}
