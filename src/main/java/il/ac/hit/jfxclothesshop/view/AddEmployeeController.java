package il.ac.hit.jfxclothesshop.view;

import il.ac.hit.jfxclothesshop.util.GraphicsUtils;
import javafx.event.ActionEvent;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

@Component
@FxmlView("addCEmployeePage.fxml")
public class AddEmployeeController {



    public void onBackButtonClick(ActionEvent event) {
        GraphicsUtils.openWindow(event, ItemsListController.class);
    }
}
