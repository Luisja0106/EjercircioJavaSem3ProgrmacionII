package application;

import controllers.ControllerTiendaView;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Tienda;
import utils.Paths;

public class App extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Tienda tienda = new Tienda();
        Platform.runLater(()->{tienda.setNProductos();});
        
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(Paths.GESTIONAR_TIENDA_VIEW));
        AnchorPane root = fxmlLoader.load();
        Scene scene = new Scene(root);
        
        ControllerTiendaView controllerTiendaView = fxmlLoader.getController();
        controllerTiendaView.setTienda(tienda);
        
        stage.setScene(scene);
        stage.setTitle("Tienda");
        stage.show();
    }
    public static void main(String[] args) {
       launch(); 
    }
}
