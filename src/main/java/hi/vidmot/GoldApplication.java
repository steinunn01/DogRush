package hi.vidmot;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

/******************************************************************************
 *  Nafn    : Sigurjón Grímsson, Steinunn María Bergþórsdóttir
 *
 *  Lýsing  :  Application klasi fyrir GoldRush
 *****************************************************************************/
public class GoldApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(GoldApplication.class.getResource("dogrush-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 640, 500);
        GoldController goldController = fxmlLoader.getController();
        stage.setTitle("DogRush!");
        stage.setScene(scene);
        stage.show();

        goldController.orvatakkar(); //setur upp örvatakka
        goldController.hefjaLeik(); //byrjar animation fyrir leik
    }

    public static void main(String[] args) {
        launch();
    }
}
