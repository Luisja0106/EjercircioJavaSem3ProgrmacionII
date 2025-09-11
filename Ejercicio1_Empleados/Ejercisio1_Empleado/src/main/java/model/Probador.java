package model;
import utils.InputDialog;
import javafx.scene.control.TextInputDialog;

import java.time.LocalDate;

public class Probador {
    private Empleado[] empleado;
    private static int nEmpleados;
    private int index;
    //constructor
    public Probador() {// Constructor
        nEmpleados = 0;
         empleado = null;
         index = 0;
    }
    
    public  void setNEmpleados(){ // asigna el numero de empleados utilizando el inputDialog
        try {
            String imput = InputDialog.text("N° Empleados", "Ingrese el numero de empleados");
            if(imput !=null && !imput.isEmpty() ) {
                nEmpleados = Integer.parseInt(imput);
                if(nEmpleados > 0) {
                    empleado = new Empleado[nEmpleados];
                    InputDialog.information("Exito", "Vector Creado con exito");
                } else { InputDialog.warning("Erro empleados", "Los empleados no pueden ser negativos");}
            } 
        } catch(NumberFormatException e) { InputDialog.warning("Error", "Ingrese un numero valido");}
    }
    public int busqueda(int id){ //busqueda de empleados segun el id
        int i;
        for(i = 0; i<index; i++){
            if(empleado[i].getId() == id){
                return i;
            }
        }
        return -1;
    }
    
    public boolean addEmpleado(Empleado em) { //metodo para agregar empleados boolean para verificar que se satisfactorio o no
        try{
            if(empleado != null) { // verifica que el vector haya sido creado previamente
                if (index < nEmpleados) { //verifica que exista espacio en el vector para crearlo
                    int x = busqueda(em.getId()); 
                    if (x != -1) {//con ayuda del metodo buscar id verifica que no sea un id repetido
                        InputDialog.warning("ID ya registrado", "Este ID ya ha sido registrado");
                        return false;
                    } else { //verifica que el proceso haya sido satisfactorio
                        empleado[index] = em;
                        SalTotal(index);
                        index++;
                        InputDialog.information("Informacion Guardada", "La informacion ha sido registrado");
                        return true;
                    }
                    
                } else {
                    InputDialog.warning("Cupos Llenos", "No existen mas cupos para empleados");
                    return false;
                }
                
            } else {
                InputDialog.warning("Vector vacio", "Primero debe crear el vector");
                return false;
            }
            
        } catch(Exception e){
            InputDialog.error("Error", e.getMessage());
            return false;
        }
        
    }
    public Empleado[] getEmpleados(){
        return empleado;
    }
    public Empleado getEmpleado(int id){
        return empleado[id];
    }
    
    public float Salud(int con){
        float salB = empleado[con].getSalarioB();
        float descSalud= salB * 0.04f;
        return descSalud;
    }
    public float Pension(int cont){
        float salB =empleado[cont].getSalarioB();
        float pension = salB*0.0375f;
        return pension;
    }
    public float Arl(int cont){
        float salB =empleado[cont].getSalarioB();
        float Arl = salB*0.02f;
        return Arl;
    }
    public float DescSalBasico(int con){ //calcula el descueto del salario basico segun lo que pide el ejercisio
        float salB =empleado[con].getSalarioB();
        float SalBT = salB-(Salud(con) + Pension(con) + Arl(con));
        return SalBT;
    }
    public int devengador(int con){ //calcula el valor de las horas extras segun la fidelidad a la empresa
        int valHE=0;
        int fechaA = LocalDate.now().getYear();
        if((fechaA-empleado[con].getFechaV()) > 10){
            valHE = 45000;
        } else if(fechaA-empleado[con].getFechaV() <= 10 && fechaA-empleado[con].getFechaV() >5 ){
            valHE = 35000;
        } else if (fechaA - empleado[con].getFechaV() <= 5 && fechaA - empleado[con].getFechaV() >= 3) {
            valHE = 30000;
        }else if(fechaA - empleado[con].getFechaV() <3 && fechaA - empleado[con].getFechaV() >0){ 
            valHE = 25000;
        } else {valHE = 0;}
        return valHE;
    }
    public float valorTotalHE(int con){
        return  devengador(con)*empleado[con].getHorasExtras();
    }
    public int subsidioTransporte(int con){ //calculoa el subsidio de transporta si aplica
        int valSu=0;
        if(empleado[con].getEstrato() <3){
            valSu = 78000;
        }
        return valSu;
    }
    public float SalTotal(int con){ //calcula el salario total 
        float salBasico = DescSalBasico(con);
        float transporte = subsidioTransporte(con);
        float extra =  valorTotalHE(con);
        float St = salBasico + extra +transporte;
        empleado[con].setSalarioNeto(St);
        return St;
    }
   public void Quicksort(){ //metodo utilizado para solo pedir como dato el array no los index
        Empleado[] array = empleado;
        Quicksort(array,0, array.length-1);
   }
    public  void Quicksort(Empleado[] empleados, int lowIndex, int highIndex){
        
        if(lowIndex >= highIndex){ //verifica que een caso de que queden dos numeros en una particion del array ya sea a la derecha o izquierda del pivote, se encarga de sortearlos en caso de que el izquierdo sea mayor o igual al derecho
            return;
        }
        float pivot = empleados[highIndex].getSalarioNeto();// marco como pivote el numero mas alto del array
        int lP = lowIndex; // punteador izquierdo
        int rP = highIndex; // punteador derecho
       while(lP < rP){ // marco que el punteador izq nu puede sobrepasar el derecho
           while(empleados[lP].getSalarioNeto() >= pivot && lP < rP){
               lP++; // indico que el puntero izquiero incremente siempre y cuando el numero al que apunta sea menor que el pivote y sea menor que el derecho
           }
           while(empleados[rP].getSalarioNeto() <= pivot && lP < rP){
               rP--; //indico que el puntero derecho disminuya siempre y cuadno al numero que apunta sea mayor que el pivote y sea mayor al puntero izquierdo
           }
           SwapQuick(empleados, lP, rP);
       }
       SwapQuick(empleados, lP, highIndex);
       Quicksort(empleados, lowIndex, lP-1);
       Quicksort(empleados,lP+1, highIndex);
   }
    
    public  void ShellSort(){
        Empleado[] array = empleado;
        int jump,i;
        Empleado aux;
        boolean changes;
        for(jump=array.length/2; jump !=0 ; jump /=2){ //define que siempre y cuando el salto sea diferente de 0 los saltos avansaran dividiento el tamaño del vector a la mitad
            changes = true; //verifica que se hayan echo cambios en el vector
            while(changes){ //si si hay cambios revisa el vector
                changes = false;
                for(i=jump; i < array.length; i++){ //recorre el vector desde los saltos
                    if(array[i-jump].getEstrato() < array[i].getEstrato()){ //intercambia posiciones 
                        aux = array[i];
                        array[i] = array[i-jump];
                        array[i-jump] = aux;
                        changes = true; //en caso de intercambiar posiciones devuleve un true
                    }
                }
            }
        }
    }
    public void SelectionSort(){
        Empleado[] array = empleado;
        for(int i=0; i< array.length; i++){ //primer array en que recorre el array
            int minIndex = i; // guarda la supuesta posicion minima
            for(int j=i+1; j< array.length; j++){ //segundo ciclo que me informa recorre el array en busqueda del verdadero minimo
                if(array[j].getNombre().compareTo(array[minIndex].getNombre()) < 0){ //en caso de que el verdader minimo sea diferente de el minIndex se guarda el nuevo minimo, se utiliza el compareTo pues en java al comparar dos Strings este devulve la diferencia de numeros en el unicodex si es mayor el numero es positivo si es menor el numero es negativo y si son iguales pues el es 0
                    minIndex = j;
                }
            }
            Empleado aux = array[i]; //se hace el switch
            array[i] = array[minIndex];
            array[minIndex] = aux;
        }
    }
    private static void SwapQuick(Empleado[] array, int index1, int index2){ //metodo que intercambiara las posiciones del quicksort
            Empleado temp = array[index1];
            array[index1] = array[index2];
            array[index2] = temp;
    }
    public int busquedaNomina() { //metodo que se asegura de que la nomina exista y que el id sea valido
        try {
            String input = InputDialog.text("Ingrese el id", "Ingrese el id del empleado al cual se calculará la nómina"); //pide el id por medio de un emergente
            if (input == null || input.isEmpty()) { //en cado de ser null o vacio retrona -1
                    InputDialog.warning("Entrada vacía", "Debe ingresar un ID válido");
                    return -1; // Retorna -1 para evitar confuciones con posiciones
            }

            int id = Integer.parseInt(input);
            int posicion = busqueda(id);

            if (posicion != -1) {
                return id; // Devuelve el ID, no la posición
            } else { //si el id no existe
                InputDialog.warning("ID inválido", "El ID ingresado no existe");
                return -1;
            }
        } catch (NumberFormatException e) { // si el id es invalido
            InputDialog.warning("Error de formato", "Debe ingresar un número válido");
            return -1;}
    }
        
}

