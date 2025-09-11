package applications;
import controllers.ProbadorVistaController;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import model.Probador;
import javafx.application.Application;
import javafx.stage.Stage;
import utils.path;

import java.util.Scanner;


public class App extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Probador probador = new Probador();
        Platform.runLater(()->{probador.setNEmpleados();});

        FXMLLoader loader = new FXMLLoader(getClass().getResource(path.GESTIONAR_PROBADOR_VIEW));
        AnchorPane root = loader.load();
        Scene scene = new Scene(root);

// aquí conectamos el probador
        ProbadorVistaController controller = loader.getController();
        controller.setProbador(probador);

        stage.setScene(scene);
        stage.setTitle("Sistema de Gestion De empleados");
        stage.show();

    }
    public static void main(String[] args) {
        launch();
    }
}
