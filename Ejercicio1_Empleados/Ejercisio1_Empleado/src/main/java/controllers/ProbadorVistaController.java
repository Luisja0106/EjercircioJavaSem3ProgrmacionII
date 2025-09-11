package controllers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Empleado;
import model.Probador;
import utils.InputDialog;
import utils.path;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ProbadorVistaController {
    public Probador getProbador() {
        return probador;
    }

    public void setProbador(Probador probador) {
        this.probador = probador;
    }
    private ObservableList<Empleado> empleados = FXCollections.observableArrayList();

    @FXML
    private ComboBox<Integer> cbEstra;

    @FXML
    private ComboBox<String> cbFunciones;

    @FXML
    private ComboBox<String> cbGenero;

    @FXML
    private ComboBox<String> cbOrdenar;

    @FXML
    private TableColumn<Empleado, Integer> colEstrato;

    @FXML
    private TableColumn<Empleado, Integer> colFecha;

    @FXML
    private TableColumn<Empleado, String> colGenero;

    @FXML
    private TableColumn<Empleado, Integer> colHE;

    @FXML
    private TableColumn<Empleado, Integer> colID;

    @FXML
    private TableColumn<Empleado, String> colNombre;

    @FXML
    private TableColumn<Empleado, Float> colSalario;

    @FXML
    private DatePicker dateFecha;

    @FXML
    private TextField lblHorasExtra;

    @FXML
    private TextField lblID;

    @FXML
    private TextField lblNom;

    @FXML
    private TextField lblSal;

    @FXML
    private TableView<Empleado> tbContent;
    @FXML
    private Button btnReset;
    
    private Probador probador = new Probador();
 //Funciones
    @FXML
    void Ordenar(ActionEvent event) {
    String forma = cbOrdenar.getValue();
        if(forma.equals(  "Nombre (alfabeticamente)")) {
            probador.SelectionSort();
            empleados.clear();
            for(Empleado e : probador.getEmpleados()){
                if(e != null) empleados.add(e);
            }
        }else if(forma.equals("Estrato")){
            probador.ShellSort();
            empleados.clear();
            for(Empleado e : probador.getEmpleados()){
                if(e != null) empleados.add(e);
            }
        }else if(forma.equals("Salario")){
           probador.Quicksort(); 
           empleados.clear();
            for(Empleado e : probador.getEmpleados()){
                if(e != null) empleados.add(e);
            }
        } else {
            InputDialog.warning("Error", "Seleccione un metodo de ordenamiento");
        }
    }

    @FXML
    void Salir(ActionEvent event) {
        System.exit(0);

    }

    @FXML
    void registrar(ActionEvent event) {
        //guaradar los empleados
        try {
            if(lblID.getText().isEmpty() || lblNom.getText().isEmpty() || lblSal.getText().isEmpty() || lblHorasExtra.getText().isEmpty() || cbGenero.getValue() == null || cbEstra.getValue() == null || dateFecha.getValue() == null) {InputDialog.warning("LLene todos los datos", "Favor llenar todos los datos");return;} //Verifica que todos los campos hayan sido llenados
            Empleado empleado = new Empleado();
            empleado.setId(Integer.parseInt(lblID.getText())); //Id
            empleado.setNombre(lblNom.getText());//Nombre
            empleado.setGenero(cbGenero.getValue());//Genero
            empleado.setSalarioB(Float.parseFloat(lblSal.getText()));//Salario
            empleado.setEstrato(cbEstra.getValue());//Estrato
            int fecha = (dateFecha != null || dateFecha.getValue().getYear() > LocalDate.now().getYear()) ? dateFecha.getValue().getYear() : 0;// conseguir el año de la fecha en int
            if(fecha != 0 && fecha <= LocalDate.now().getYear()) { //se asegura que la fecha exista y no sobrepase
                empleado.setFechaV(fecha);//fecha
            } else{InputDialog.warning("Error", "Fechas incorrectos"); return;}
            empleado.setHorasExtras(Integer.parseInt(lblHorasExtra.getText())); // horas extra
            boolean add = probador.addEmpleado(empleado); // verifica si fue satisfactorio el registro
            if (add) { // si y solo si es true añade la atabla
                empleados.add(empleado);
                limpiar();
            } 
        } catch(NumberFormatException e){ //erro en caso de que los formatos sean invalido
            InputDialog.error("Error de Formato", "Por Favor Ingrese valores validos");
        } catch(Exception e) { // error desconocido
            InputDialog.error("Error", ("Error inesperado: "+ e.getMessage()));
        }
        
    }
    public void limpiar(){
        lblID.clear();
        lblNom.clear();
        lblSal.clear();
        lblHorasExtra.clear();
        dateFecha.setValue(null);
        cbGenero.setValue(null);
        cbEstra.setValue(null);
    }
    @FXML
    void Activar(ActionEvent event) throws IOException {
            String funcion = cbFunciones.getValue();
            if (funcion == null) {
                InputDialog.warning("Error", "Seleccione una función");
                return;
            }

            try {
                if (funcion.equals("Nomina de Empleado")) { //Segunda Scena para la nomina
                    int id = probador.busquedaNomina();
                    if (id <= 0) return; // Si el ID es inválido, no hacer nada

                    FXMLLoader loader = new FXMLLoader(getClass().getResource(path.GESTIONAR_NOMINA_VIEW)); // creador de la segunda escena
                    Parent root = loader.load(); //cargar la segunda escena

                    NominaController controller = loader.getController(); // llamada del controller para utilizar funiones
                    controller.setProbador(probador); // inicializar la nueva nomina
                    controller.cargarNomina(id); //pasarle el id a la nomina para que registre

                    Stage stage = new Stage(); // se defince como una stage
                    stage.setTitle("Nómina de Empleado"); // nombramos la stage
                    stage.setScene(new Scene(root)); // se define que la probador va a ser la root
                    stage.show(); // se muestra
                } else if (funcion.equals("Buscar Por id")) { //metodo para busqueda por id
                    int id = Integer.parseInt(InputDialog.text("buscar ID", "Ingrese el id a buscar")); // input diaalog para pedir el id
                    int index = probador.busqueda(id); //se saca el index
                    if(index == -1){ //se verifica que el index sea valido
                        InputDialog.warning("Error", "No se encontro el id del Empleado"); //error en caso de no encontrar el id
                    } else {
                        empleados.clear(); //se limpia la tabla para centrarse en la busqueda
                        empleados.add(probador.getEmpleado(index)); // se agrega el empleado del id
                        btnReset.setVisible(true); // se hace visible el boton del reset
                        btnReset.setManaged(true); // para que ocupe espacio en el layout
                    }
                }
            } catch (IOException e) {
                InputDialog.error("Error", "No se pudo cargar la ventana de nómina");
                e.printStackTrace(); // error si la ventana no carga
            }
    }
    
    public void initialize() {
        //Combo box Genero
        cbGenero.getItems().add("MASCULINO");
        cbGenero.getItems().add("FEMENINO");
        cbGenero.getItems().add("OTRO");
        // Combo box Estrato
       cbEstra.getItems().addAll(1, 2,3,4,5,6);
       // Combo box Ordenar
        cbOrdenar.getItems().add("Nombre (alfabeticamente)");
        cbOrdenar.getItems().add("Estrato");
        cbOrdenar.getItems().add("Salario");
        //Combo box Funciones
        cbFunciones.getItems().add("Nomina de Empleado");
        cbFunciones.getItems().add("Buscar Por id");
        //Las tablas
        colID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colGenero.setCellValueFactory(new PropertyValueFactory<>("genero"));
        colSalario.setCellValueFactory(new PropertyValueFactory<>("salarioB"));
        colEstrato.setCellValueFactory(new PropertyValueFactory<>("estrato"));
        colHE.setCellValueFactory(new PropertyValueFactory<>("horasExtras"));
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fechaV"));
        tbContent.setItems(empleados);
        btnReset.setVisible(false);
        btnReset.setManaged(false);
        
       
    }

    @FXML
    void Reset(ActionEvent event) {
        probador.SelectionSort(); // se devuleven valores a la tabla utilizando un sort
        empleados.clear();
        for(Empleado e : probador.getEmpleados()){
            if(e != null) empleados.add(e);
        }
        btnReset.setVisible(false); // se vuelve a ocultar el boton
        btnReset.setManaged(false); // se vuelve a hacer intangible
    }

}
