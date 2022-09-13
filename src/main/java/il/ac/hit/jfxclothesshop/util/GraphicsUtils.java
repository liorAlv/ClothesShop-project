package il.ac.hit.jfxclothesshop.util;

import javafx.event.Event;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxWeaver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public final class GraphicsUtils {
    private static FxWeaver fxWeaver;

    @Autowired
    private GraphicsUtils(FxWeaver fxWeaver) {
        GraphicsUtils.fxWeaver = fxWeaver;
    }

    //Move between pages
    public static <T> void openWindow(Event event, Class<T> controllerClass) {
        Parent root = fxWeaver.loadView(controllerClass);
        Scene booksListScene = new Scene(root);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(booksListScene);
        window.show();
    }


}
