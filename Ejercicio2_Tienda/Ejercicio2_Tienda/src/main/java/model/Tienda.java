package model;

import utils.InputDialog;

public class Tienda {
    private Producto[] productos;
    private static int nProductos;
    private int index;
    
    public Tienda(){
        productos = null;
        nProductos = 0;
        index = 0;
    }
    public void setNProductos() {
        try {
            String nprodu = InputDialog.text("Cantidad de productos", "Ingrese la cantidad de productos a ingresar, aunque sean diferentes marcas"); // se pide el numero de productos
            if (nprodu != null && !nprodu.isEmpty()) { //mientras es una String se verifica que no sea null ni este vacio
                nProductos = Integer.parseInt(nprodu); //se pasa a entero
                if (nProductos > 0) { //verificacion de que sea positivo
                    productos = new Producto[nProductos];
                    InputDialog.information("Vector creado", "Se ha creado el vector satisfactoriamente");
                } else {InputDialog.warning("Error", "Error asegurse de que la cantidad es positiva");}
            } else {
                InputDialog.warning("Error", "Favor ingrese un dato, la cantidad de productos no puede estar vacia ");
            }
        } catch (NumberFormatException e) { // mensaje en caso de que sea un dato invalido
            InputDialog.error("Eror", "Error ingrese un dato valido");
        }
    }
   public int buscar(String codigo){
        int i;
        for(i = 0; i < index; i++){
            if(codigo.equals(productos[i].getCodigo())){
                return i;
            }
        }
        return -1;
   } 
   public boolean addProducto(Producto p){ //metodo para agg un producto nuevo
        try{
            if(productos != null) { // se verifica que el vector exista
                if(index < nProductos) { //se verifica que exista cupo para el producto 
                    int x = buscar(p.getCodigo()); //se busca que el codigo sea nuevo y repetido
                    if(x != -1){ //en caso de ser repetido salta un error
                        InputDialog.warning("Error", "El producto ya existe");
                        return false;
                    }else { //se confirma que se agg el producto
                        productos[index] = p;
                        InputDialog.information("Producto Agregado","Producto Agregado Satisfactoriamente");
                        valorTotal(index);
                        index++;
                        return true;
                    }
                }else{ // error en caso de no existir cupos
                    InputDialog.warning("Error", "No hay mas espacio para agregar productos");
                    return false;
                }
                
            }else { // error en caso de que le vector no se haya creado
                InputDialog.warning("Error", "Error el vector no ha sido creado");
                return false;
            }
   } catch (Exception e) { // error en cualquier otro caso
            InputDialog.error("Eror", "Ha ocurrido un eror" + e.getMessage());
            return false;
        }
    }
    
    public Producto[] getProductos() { // retorno del array de objetos
        return productos;
    }
    public Producto getProducto(int cod){ //retorno de una posicion en el array
        return productos[cod];
    }
    
    public Float ganancia(int i){ //se calcula la ganancia
        Float ganancia = productos[i].getPrecioV() - productos[i].getPrecioC();
        return ganancia;
    }
    public Float impuesto(int i){ // se calcula el impuesto y el resultado se guarda en el atributo "escondido" de ganancia para mayor facilidad al hacer los sorts
        
        Float x;
        if(ganancia(i) > 50000){
            x = ganancia(i) * 0.10f;
        } else {x = 0f;}
        return x;
    }
    public Float valorTotal(int i){
        Float x = ganancia(i) -  impuesto(i);
        productos[i].setGanancia(x);
        return x;
        
    }
    public void BubbleSort(){ //bubblesort para orgnaizad de mayor a menor la ganancia
        int i, j;
        for(i = 0; i < index - 1; i++){
            for(j =0; j < (index-i-1); j++){
                if(productos[j].getGanancia()< productos[j+1].getGanancia()){
                    Producto temp;
                    temp = productos[j];
                    productos[j] = productos[j+1];
                    productos[j+1] = temp;
                }
            }
        }
    }
    public void InsercionSort(){
       for(int i = 1; i < index;i++){ //un for que recorra el array iniciando en la seguna posicion 
           Producto temp = productos[i];
           String Stemp=temp.getNombre(); //optengo el nombre de esa posicion
        int j = i-1; //nuevo contador que va estar por detras de mi contador inicial (i)
        while(j >=0 && productos[j].getNombre().compareTo(Stemp)>0){ //siempre que j sea mayor o igual a 0 y el nombre de la pisicion j sea mayor en unicode a la temporal 
            productos[j+1] = productos[j];
            j = j-1;
        }
        productos[j+1] = temp;
       }
    }
    public void SelectionSort(){
        Producto[] array = productos; // creo la variable array para comodidad
        for(int i = 0; i < index; i++){ // ciclo que rrcorre todo el arreglo
            int minIndex = i; // guardo la supuesta posicion con el valor minimo
            for(int j = i+1; j < index; j++) { //ciclo que recorre todo el array sin contar la pisicon i con el objetivo de encontrar el verdadero min
                if (array[j].getCantStock() > array[minIndex].getCantStock()) { //encaso de encontrarlo pues asignarlo
                    minIndex = j;
                }
            
            }
            //swicheo
            Producto aux = array[i]; 
            array[i] = array[minIndex];
            array[minIndex] = aux;
        }
    }
    public void mostrarDetallesVenta(){
        String index =InputDialog.text("Codigo", "Ingrese el codigo a mostrar detalles");
        int x = buscar(index);
        if(x == -1){
            InputDialog.error("Error", "El producto no existe");
        }else {
            String info = "Detalles de venta: \n";
            info += "Precio de venta: " + getProducto(x).getPrecioV() + "\n";
            info  += "Precio de costo: " + getProducto(x).getPrecioC() + "\n";
            info += "Ganancia: " + ganancia(x) + "\n";
            info += "Impuestos: -" + impuesto(x) + "\n";
            info += "valor Total Venta: " + valorTotal(x) + "\n";
            InputDialog.information("Detalles de venta", info);
        }
    }
    
}
