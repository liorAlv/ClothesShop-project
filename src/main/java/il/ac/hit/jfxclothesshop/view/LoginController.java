package il.ac.hit.jfxclothesshop.view;

import il.ac.hit.jfxclothesshop.JdbcDriverSetup;
import il.ac.hit.jfxclothesshop.person.User;
import il.ac.hit.jfxclothesshop.session.SessionContext;
import il.ac.hit.jfxclothesshop.util.GraphicsUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;

@Component
@FxmlView("loginPage.fxml")
public class LoginController {
    @FXML
    private Label responseText;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;

    @FXML
    private Button loginButton;

    @Autowired
    private FxWeaver fxWeaver;

    @FXML
    protected void onLoginButtonClick(ActionEvent event) {
        try { //checks if the username and password that match what we have in the DB
            //using SQL question
            List<User> result = JdbcDriverSetup.getDao(User.class).queryBuilder()
                    .where()
                    .eq("username", username.getText())
                    .and()
                    .eq("password", DigestUtils.sha512Hex(password.getText()))
                    .query();
            if (result.size() == 0) { //if we did not get a result then the username or password are incorrect
                responseText.setText("Username/password is incorrect");
            } else { //if we did get a result then it will go to the next page
                SessionContext.getInstance().setCurrentUser(result.get(0));
                onSuccessfulLogin(event);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // todo: workout how to handle exception with DB
        }

    }

    //Move between pages
    private void onSuccessfulLogin(ActionEvent event){
        GraphicsUtils.openWindow(event, ItemsListController.class);
    }


}