package applications;

import controllers.ParkingControllerView;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Parking;
import utils.Paths;

public class App extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Parking parking = new Parking();
        Platform.runLater(()->{parking.setCantVehiculos();});

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(Paths.GESTIONAR_PARKING_VIEW));
        AnchorPane root = fxmlLoader.load();
        Scene scene = new Scene(root);

        ParkingControllerView controllerParkingView = fxmlLoader.getController();
        controllerParkingView.setParking(parking);

        stage.setScene(scene);
        stage.setTitle("Tienda");
        stage.show();
        
    }
    public static void main(String[] args) {
        launch();
    }
}
