package model;

public class Producto {
    private String codigo;
    private String nombre;
    private String marca;
    private Float precioC;
    private Float precioV;
    private int cantStock;
    private Float ganancia;
    
    //Constructors

    public Producto(String codigo, String nombre, String marca, Float precioC, int cantStock, Float precioV) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.marca = marca;
        this.precioC = precioC;
        this.cantStock = cantStock;
        this.precioV = precioV;
        this.ganancia = 0f;
    }

    public Producto() {
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public Float getPrecioC() {
        return precioC;
    }

    public void setPrecioC(Float precioC) {
        this.precioC = precioC;
    }

    public int getCantStock() {
        return cantStock;
    }

    public void setCantStock(int cantStock) {
        this.cantStock = cantStock;
    }
    public void setPrecioV(Float precioV) {
        this.precioV = precioV;
    }
    public Float getPrecioV(){
        return precioV;
    }

    public Float getGanancia() {
        return ganancia;
    }

    public void setGanancia(Float ganancia) {
        this.ganancia = ganancia;
    }
}
