package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Parking;
import model.Vehiculo;
import utils.InputDialog;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ParkingControllerView {
    private ObservableList<Vehiculo> vehiculos = FXCollections.observableArrayList();
    private Parking parking = new Parking();

    @FXML
    private Button btnReset;
    @FXML
    private ComboBox<String> cbFunciones;

    @FXML
    private ComboBox<String> cbOrdenar;

    @FXML
    private ComboBox<String> cbTipo;

    @FXML
    private TableColumn<Vehiculo, String> colFechaE;

    @FXML
    private TableColumn<Vehiculo, String> colFechaS;

    @FXML
    private TableColumn<Vehiculo, String> colPLaca;

    @FXML
    private TableColumn<Vehiculo, String> colTipo;

    @FXML
    private DatePicker dTEntrada;

    @FXML
    private DatePicker dtSalida;

    @FXML
    private Spinner<Integer> spHoraE;

    @FXML
    private Spinner<Integer> spHoraS;

    @FXML
    private Spinner<Integer> spMinE;

    @FXML
    private Spinner<Integer> spMinS;

    @FXML
    private Spinner<Integer> spSegE;

    @FXML
    private Spinner<Integer> spSegS;

    @FXML
    private TableView<Vehiculo> tblView;

    @FXML
    private TextField tlbPlaca;

    @FXML
    void registrar(ActionEvent event) {
        try{
            if(cbTipo.valueProperty().getValue()==null || tlbPlaca.getText().isEmpty() || dTEntrada.getValue()==null || dtSalida.getValue()==null){InputDialog.warning("Error", "LLene los datos"); return;}
            Vehiculo v = new Vehiculo(); //se crea un objeto para agregar los valores
            v.setPlaca(tlbPlaca.getText()); //se agrega la placa
            v.setTipoVehiculo(cbTipo.getValue()); // se agrega el tipo de vehiculo
            LocalDate dE = dTEntrada.getValue(); //se extrae la fecha dd:mm:yyyy de entrada
            int hE = spHoraE.getValue(); //se extra la hora de entrada
            int mE = spMinE.getValue(); //minuto de entrada
            int sE = spSegE.getValue();//segundo de entrada
            LocalDateTime fechaE = (dE.atTime(hE,mE,sE)).isBefore(LocalDateTime.now()) ? dE.atTime(hE,mE,sE): null; //se asgura de que la fecha sea valida
            if(fechaE!=null) {
                v.setFechaIngreso(fechaE);
            }else {InputDialog.warning("Fecha incorrecta", "Fecha invalidad ingrese una correcta");return;}
            LocalDate dS = dtSalida.getValue(); //lo mismo pero con la de salida
            int hS = spHoraS.getValue();
            int mS = spMinS.getValue();
            int sS = spSegS.getValue();
            LocalDateTime fechaS = (dS.atTime(hS,mS,sS)).isBefore(LocalDateTime.now()) ? dS.atTime(hS,mS,sS): null; //se asgura de que la fecha sea valida
            if(fechaS!=null){
                v.setFechaSalida(fechaS);
            }else {InputDialog.warning("Fecha incorrecta", "Fecha invalidad ingrese una correcta");return;}
            boolean add = parking.addVehiculo(v); //se agg el vector
            if(add){
                vehiculos.add(v);
                limpiar();
            }
        }catch(NumberFormatException e){
            InputDialog.error("Error","Datos invaliddo");
        }
    }

    private void limpiar() {
        tlbPlaca.setText("");
        dTEntrada.setValue(null);
        dtSalida.setValue(null);
    }

    @FXML
    void ordenar(ActionEvent event) {
        String forma = cbOrdenar.getValue();
        if(forma.equals("TIPO DE VEHICULO")){
            parking.SelectionSort();
            vehiculos.clear();
            for(Vehiculo v : parking.getVehiculos())
                if(v !=null) vehiculos.add(v);
        }else if(forma.equals("TIEMPO DE ESTACIONAMIENTO")){
            parking.InsertionSort();
            vehiculos.clear();
            for(Vehiculo v : parking.getVehiculos())
                if(v !=null) vehiculos.add(v);
        }else if(forma.equals("VALOR A PAGAR")){
            parking.BubbleSort();
            vehiculos.clear();
            for(Vehiculo v : parking.getVehiculos())
                if(v !=null) vehiculos.add(v);
        }

    }

    @FXML
    void salir(ActionEvent event) {
        System.exit(0);
    }
    @FXML
    void activar(ActionEvent event) {
        String tipo = cbFunciones.getValue();
        if(tipo.equals("BUSCAR PLACA")){
            String placa = InputDialog.text("Codigo", "Ingrese la placa a buscar"); //input dialog para pedir  la placa
            int index = parking.buscar(placa); // se saca el index
            if(index != -1){ //se verifica que exista
                vehiculos.clear(); // se limpia la tabla
                vehiculos.add(parking.getVehiculo(index));
                btnReset.setVisible(true); // se hace visible el boton del reset
                btnReset.setManaged(true);; // se agrega  el producto del index
            } else {InputDialog.warning("Inexistente", "Codigo no encontrado");}

        }else if(tipo.equals("DETALLE DE COBRO")){
            parking.estacionamiento();
        }else { InputDialog.error("Funcion invalida","Funcion Invalida");}
        
    }
    @FXML
    void reset(ActionEvent event) {
        btnReset.setVisible(false);
        btnReset.setManaged(false);
        parking.SelectionSort();
        vehiculos.clear();
        for(Vehiculo v : parking.getVehiculos())
            if(v !=null) vehiculos.add(v);
    }
    public void initialize() {
        //combo box funciones
        cbFunciones.getItems().add("DETALLE DE COBRO");
        cbFunciones.getItems().add("BUSCAR PLACA");
        //combo box Ordenar
        cbOrdenar.getItems().add("TIPO DE VEHICULO");
        cbOrdenar.getItems().add("TIEMPO DE ESTACIONAMIENTO");
        cbOrdenar.getItems().add("VALOR A PAGAR");
        //combo box Tipo
        cbTipo.getItems().add("AUTOMOVIL");
        cbTipo.getItems().add("MOTOCICLETA");
        cbTipo.getItems().add("CAMIONETA");
        //Tabla
        colPLaca.setCellValueFactory(new PropertyValueFactory<>("placa"));
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipoVehiculo"));
        colFechaE.setCellValueFactory(new PropertyValueFactory<>("SfechaIngreso"));
        colFechaS.setCellValueFactory(new PropertyValueFactory<>("SfechaSalida"));
       tblView.setItems(vehiculos);
       //sppiners
        spHoraE.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, 0));
        spHoraS.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, 0));
        spMinE.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0));
        spMinS.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0));
        spSegE.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0));
        spSegS.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0));
        //boton reset
        btnReset.setVisible(false);
        btnReset.setManaged(false);
    }

    public void setParking(Parking parking) {
        this.parking = parking;
    }
}
