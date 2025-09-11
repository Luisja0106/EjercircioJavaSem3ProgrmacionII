package model;

public class Empleado {
    private int id;
    private String nombre;
    private String genero;
    private float salarioB;
    private int estrato;
    private int horasExtras;
    private int fechaV;
    private float salarioNeto;

    public Empleado(int id, String nombre, String genero, float salarioB, int estrato, int horasExtras, int fechaV) {
        this.id = id;
        this.nombre = nombre;
        this.genero = genero;
        this.salarioB = salarioB;
        this.estrato = estrato;
        this.horasExtras = horasExtras;
        this.fechaV = fechaV;
        this.salarioNeto =0f;
    }

    public Empleado() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public float getSalarioB() {
        return salarioB;
    }

    public void setSalarioB(float salarioB) {
        this.salarioB = salarioB;
    }

    public int getEstrato() {
        return estrato;
    }

    public void setEstrato(int estrato) {
        this.estrato = estrato;
    }

    public int getHorasExtras() {
        return horasExtras;
    }

    public void setHorasExtras(int horasExtras) {
        this.horasExtras = horasExtras;
    }

    public int getFechaV() {
        return fechaV;
    }

    public void setFechaV(int fechaV) {
        this.fechaV = fechaV;
    }

    public float getSalarioNeto() {
        return salarioNeto;
    }

    public void setSalarioNeto(float salarioNeto) {
        this.salarioNeto = salarioNeto;
    }
}
