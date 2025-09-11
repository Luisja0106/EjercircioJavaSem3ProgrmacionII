package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.Empleado;
import model.Probador;
import utils.InputDialog;

public class NominaController {
    @FXML
    private Label valARL;
    @FXML
    private Button salir;

    @FXML
    private Label valHEL;

    @FXML
    private Label valHEV;

    @FXML
    private Label valPension;

    @FXML
    private Label valSB;

    @FXML
    private Label valSalud;

    @FXML
    private Label valSub;

    @FXML
    private Label valTo;

    @FXML
    private Label valTotalHE;
    Probador probador = new Probador();
    Empleado empleado = new Empleado();
    
    public void cargarNomina(int idEmpleado) {
        int pos = probador.busqueda(idEmpleado); // Ahora busca por ID correcto
        if (pos == -1) {
            InputDialog.warning("Error", "Empleado no encontrado");
            return;
        }
        Empleado e = probador.getEmpleados()[pos];
        // Mostrar datos
        valSB.setText(String.valueOf(e.getSalarioB()));
        valHEL.setText(String.valueOf(e.getHorasExtras()));
        valHEV.setText(String.valueOf(probador.devengador(pos)));
        valTotalHE.setText(String.valueOf(probador.valorTotalHE(pos)));
        valSalud.setText(String.valueOf(probador.Salud(pos)));
        valPension.setText(String.valueOf(probador.Pension(pos)));
        valARL.setText(String.valueOf(probador.Arl(pos)));
        valSub.setText(String.valueOf(probador.subsidioTransporte(pos)));
        valTo.setText(String.valueOf(probador.SalTotal(pos)));
    }
    @FXML
    void salir(ActionEvent event) {
        Stage stage =  (Stage) salir.getScene().getWindow();
        stage.close();
    }

    public void setProbador(Probador probador) {
        this.probador = probador;
    }
}


