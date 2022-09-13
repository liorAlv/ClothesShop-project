package il.ac.hit.jfxclothesshop.person.managing;

import javafx.scene.control.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public final class ClientUtils {
    private static ClientManager clientManager;

    @Autowired
    private ClientUtils(ClientManager clientManager) {
        ClientUtils.clientManager = clientManager;
    }

    public static boolean isClientInputNotOkay(TextField phone,
                                         TextField name,
                                         TextField email,
                                         TextField address) throws SQLException {
        return !phone.getText().matches("^0\\d+$") ||     //phone number start with 0 and end with number
                name.getText().isBlank() ||
                email.getText().isBlank() ||
                phone.getText().isBlank() ||
                address.getText().isBlank()||
                clientManager.getClientByIdOrPhone(null, phone.getText()) != null;
    }
}
