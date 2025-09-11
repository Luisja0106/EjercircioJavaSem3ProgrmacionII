package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.*;
import utils.InputDialog;

public class ControllerTiendaView {
    
    
    @FXML
    private ComboBox<String> cbFunciones;
    
    private ObservableList<Producto> productos = FXCollections.observableArrayList();

    @FXML
    private ComboBox<String> cbOrdenar;

    @FXML
    private TableColumn<Producto, String> colCod;

    @FXML
    private TableColumn<Producto, Float> colCosto;

    @FXML
    private TableColumn<Producto, String> colMarca;

    @FXML
    private TableColumn<Producto, String> colNom;

    @FXML
    private TableColumn<Producto, Integer> colStock;

    @FXML
    private TableColumn<Producto, Float> colVenta;
    @FXML
    private TableView<Producto> tblProductos;
    

    @FXML
    private TextField lblCant;

    @FXML
    private TextField lblCodigo;

    @FXML
    private TextField lblMarc;

    @FXML
    private TextField lblNom;

    @FXML
    private TextField lblPCos;

    @FXML
    private TextField lblPVne;
    @FXML
    private Button btnReset; 

    @FXML
    void Activar(ActionEvent event) {
        String tipo = cbFunciones.getValue();
        if(tipo.equals("BUSCAR CODIGO")){
            String cod = InputDialog.text("Codigo", "Ingrese el codigo a buscar"); //input dialog para pedir el codigo
            int index = tienda.buscar(cod); // se saca el index
            if(index != -1){ //se verifica que exista
                productos.clear(); // se limpia la tabla
                productos.add(tienda.getProducto(index)); 
                btnReset.setVisible(true); // se hace visible el boton del reset
                btnReset.setManaged(true);; // se agrega  el producto del index
            } else {InputDialog.warning("Inexistente", "Codigo no encontrado");}
            
        }else if(tipo.equals("DETALLE VENTA")){
            tienda.mostrarDetallesVenta();
        }else { InputDialog.error("Funcion invalida","Funcion Invalida");}

    }

    @FXML
    void Ordenar(ActionEvent event) {
        String tipo = cbOrdenar.getValue();
        if(tipo.equals("GANANCIA")){ //en caso de ser por ganancia
            tienda.BubbleSort(); //se llama al metodo de sort
            productos.clear(); //se limpia la tabla
            for(Producto e : tienda.getProductos()) //se usa un for each (para cada index del array haz lo siguiente)
                if(e != null) productos.add(e); //verifica que no sean null y agg a la tabla
        }else if(tipo.equals("INFORMACION (ALFABETICAMENTE)")){
            tienda.InsercionSort();
            productos.clear();
            for(Producto e : tienda.getProductos())
                if(e != null) productos.add(e);
        }else if(tipo.equals("CANTIDAD DE STOCK")){
            tienda.SelectionSort();
            productos.clear();
            for(Producto e :  tienda.getProductos())
                if(e != null) productos.add(e);
        } else {InputDialog.error("Invalido", "Metodo de ordenamiento invalido");}
    }
    private Tienda tienda= new Tienda();

    @FXML
    void registrar(ActionEvent event) {
        try {
            if(lblCant.getText().isEmpty() || lblCodigo.getText().isEmpty() || lblMarc.getText().isEmpty() || lblNom.getText().isEmpty() || lblPVne.getText().isEmpty() || lblPCos.getText().isEmpty()) {InputDialog.warning("Error", "Llene todos los datos"); return;} //verificando que los datos esten llenos
                Producto pdct = new Producto(); 
                pdct.setCantStock(Integer.parseInt(lblCant.getText()));
                pdct.setCodigo(lblCodigo.getText());
                pdct.setMarca(lblMarc.getText());
                pdct.setNombre(lblNom.getText());
                pdct.setPrecioC(Float.parseFloat(lblPCos.getText()));
                pdct.setPrecioV(Float.parseFloat(lblPVne.getText()));
                boolean add = tienda.addProducto(pdct); //verifico que se hayan agg correctamente
                if(add){ // si es true los agg a la tabla
                    productos.add(pdct);
                    limpiar();
                }
        }catch(NumberFormatException e){InputDialog.error("Eror", "Ingrese datos validos");}
    }
    private void limpiar(){
        lblCant.setText("");
        lblCodigo.setText("");
        lblMarc.setText("");
        lblNom.setText("");
        lblPVne.setText("");
        lblPCos.setText("");
    }

    @FXML
    void salir(ActionEvent event) {
        System.exit(0);
    }
    
    public void initialize(){
        //combo box funciones
        cbFunciones.getItems().add("BUSCAR CODIGO");
        cbFunciones.getItems().add("DETALLE VENTA");
        //combo box organizar
        cbOrdenar.getItems().add("GANANCIA");
        cbOrdenar.getItems().add("INFORMACION (ALFABETICAMENTE)");
        cbOrdenar.getItems().add("CANTIDAD DE STOCK");
        //tabla
        colCod.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        colNom.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colMarca.setCellValueFactory(new PropertyValueFactory<>("marca"));
        colVenta.setCellValueFactory(new PropertyValueFactory<>("precioV"));
        colCosto.setCellValueFactory(new PropertyValueFactory<>("precioC"));
        colStock.setCellValueFactory(new PropertyValueFactory<>("cantStock"));
        tblProductos.setItems(productos);
        btnReset.setVisible(false); // se hace visible el boton del reset
        btnReset.setManaged(false);

    }

    public void setTienda(Tienda tienda) {
        this.tienda = tienda;
    }
    @FXML
    void Reset(ActionEvent event) {
        tienda.BubbleSort(); //se llama al metodo de sort
        productos.clear(); //se limpia la tabla
        btnReset.setVisible(false);
        btnReset.setManaged(false);
        for(Producto e : tienda.getProductos()) //se usa un for each (para cada index del array haz lo siguiente)
            if(e != null) productos.add(e); //verifica que no sean null y agg a la tabla 
    }
}
