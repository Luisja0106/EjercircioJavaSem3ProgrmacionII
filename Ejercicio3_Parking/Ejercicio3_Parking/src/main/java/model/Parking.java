package model;

import utils.InputDialog;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Parking {
    private Vehiculo[] vehiculos;
    private int cantVehiculos;
    private int index;
    
    public Parking(){
        vehiculos = null;
        cantVehiculos = 0;
        index = 0;
    }
    public void setCantVehiculos(){
        try {
            String cant = InputDialog.text("Cantidad de vehiculos", "Ingrese la cantidad de viehuclos"); //se pide la cantidad por medio de un inputDialog
            if(cant != null && !cant.isEmpty()){ // se asegura de que la cantidad no sea null ni este vacia
                cantVehiculos =Integer.parseInt(cant); //se pasa a int
                if(cantVehiculos > 0){ //se verifica que no sea negativo
                    vehiculos = new Vehiculo[cantVehiculos]; //se crea el vector
                    InputDialog.information("Vector Creado", "Se ha creado el vector Satisfactoriamente"); //se da feedback
                } else{InputDialog.warning("Precaucion", "El vector no puede ser negativo ni 0");} //en caso de que se ingrese un dato negativo
            } else{InputDialog.warning("Vector Vacio", "Precaucion ingrese un dato");} //en caso de que el input este vacio
        } catch(NumberFormatException e){ //en caso de ingresar datos invalidos
            InputDialog.error("Error", "Ingrese un dato Valido"); //input dialog error
        }
    }
   public int buscar(String placa){ //metodo para buscar la placa
        int i;
        for(i=0; i<index; i++){
            if(placa.equalsIgnoreCase(vehiculos[i].getPlaca()))
                return i;
        }
        return -1;
   } 
   public boolean addVehiculo(Vehiculo v){
       try {
           if (vehiculos != null) { //se verifica que el arrya exista
               if (index < cantVehiculos) { //se verifica que exista espacio en el arreglo
                   int x = buscar(v.getPlaca());
                   if (x != -1) { //se verooca que no exista un vehiculo con esa placa
                       InputDialog.warning("Error", "El vehiculo ya existe");
                       return false;
                   } else {
                       vehiculos[index] = v; 
                       pago(index);
                       FechaIngreso(index);
                       FechaSalida(index);
                       InputDialog.information("Informacion Guardada", "Vehiculo guardado");
                       index++;
                       return true;
                   }
               } else {
                   InputDialog.warning("No hay mas espacio", "Error No existe mas espacio para registrar vehiculos");
                   return false;
               }
           } else {
               InputDialog.error("Vector inexistente", "Error el vector no ha sido creado");
               return false;
           }
       } catch (NumberFormatException e) {
           InputDialog.error("Error", "Ingrese un dato Valido");
           return false;
       }
  
   } 
   public Vehiculo[]  getVehiculos(){
        return vehiculos;
   }
   public Vehiculo getVehiculo(int i){
        return vehiculos[i];
   }
   public String FechaIngreso(int i){ //metodo para pasar la fecha de ingreso al tipo que solicito el docente y pasarla a String
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"); // configuro el modo de guardar
       String fechai = vehiculos[i].getFechaIngreso().format(dtf);//llamo a la fecha
       vehiculos[i].setSfechaIngreso(fechai);
       return fechai; //retorno la fecha con el modo correcto y en String
   }
   public String FechaSalida(int i){ //igual que el de entrada pero con el de salida
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String fechaS = vehiculos[i].getFechaSalida().format(dtf);
        vehiculos[i].setSfechaSalida(fechaS);
        return fechaS;
   }
   public int tiempoEstacionamiento(int i){ //metodo para calcular el tiempo de estacionamiento
        LocalDateTime ing = vehiculos[i].getFechaIngreso();
        LocalDateTime sal = vehiculos[i].getFechaSalida();
        Duration d = Duration.between(ing, sal); //comparo las fechas
        int horas =  (d.toHours() <= Integer.MAX_VALUE)? (int) d.toHours() : -1; //guardo la diferencai de horas en entero, me aseguro de que la diferencia no sobrepase el valor que puede tomar un int
        vehiculos[i].setTiempo(horas); //gurado el valor en la clase
        return horas; //retorno
    } 
    public float pago(int i){
        int costo = 0;
        if(vehiculos[i].getTipoVehiculo().equalsIgnoreCase("AUTOMOVIL") || vehiculos[i].getTipoVehiculo().equalsIgnoreCase("CAMIONETAS")){
            costo = 2000; //agrego el costo de horas segun el tipo de vehiculo
        }else if(vehiculos[i].getTipoVehiculo().equalsIgnoreCase("MOTOCICLETAS")){
            costo = 1000;
        }
        float pagoT = costo* vehiculos[i].getTiempo(); 
        vehiculos[i].setPago(pagoT); //guardo en vehiculos
        return pagoT; //retorno
    }
    public void estacionamiento(){
        String x = InputDialog.text("ingrese la placa", "Ingrese la placa a hacer factura");
        int i = buscar(x);
        if(i == -1){
            InputDialog.warning("Placa no encontrada", "No se ha encontrado la placa digitada");
        }else {
            String info = "Detalles de Parking:  \n";
            info += "FECHA Y HORA DE INGRESO:  " + FechaIngreso(i) + "\n";
            info += "FECHA Y HORA DE SALIDA:  " + FechaSalida(i) + "\n";
            info += "TIEMPO DE ESTACIONAMIENTO:  " + tiempoEstacionamiento(i) + "H \n";
            info += "VALOR A PAGAR:  $" + pago(i) + "\n";
            InputDialog.information("Recibo", info);
        }
    }
    public void SelectionSort(){
        Vehiculo[] array = vehiculos;
        for(int i=0; i<index; i++){
            int minIndex = i;
            for(int j=i+1; j<index; j++){
                if(array[j].getTipoVehiculo().compareTo(array[minIndex].getTipoVehiculo()) <0) {
                    minIndex = j;
                }
            }
            //swap
            Vehiculo temp = array[i];
            array[i] = array[minIndex];
            array[minIndex] = temp;
        }
    }
    public void InsertionSort(){
        for(int j=0; j<index; j++){
            Vehiculo temp =  vehiculos[j];
            int time = temp.getTiempo();
            int i = j-1;
            while(i>=0 && vehiculos[i].getTiempo() < time){
                vehiculos[i+1] = vehiculos[i];
                i--;
            }
            vehiculos[i+1] = temp;
        }
    }
    public void BubbleSort(){
        int i, j;
        for(i=0; i<index-1; i++){
            for(j=0; j<(index-i-1); j++){
                if(vehiculos[j].getPago() < vehiculos[j+1].getPago()){
                    Vehiculo temp = vehiculos[j];
                    vehiculos[j] = vehiculos[j+1];
                    vehiculos[j+1] = temp;
                }
            }
        }
    }
   
}
