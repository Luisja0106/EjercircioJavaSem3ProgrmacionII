package model;

import java.time.LocalDateTime;

public class Vehiculo {
    private String placa;
    private String tipoVehiculo;
    private LocalDateTime fechaIngreso;
    private LocalDateTime fechaSalida;
    private String SfechaIngreso;
    private String SfechaSalida;
    private int tiempo;
    private float Pago;

    public Vehiculo(String placa, String tipoVehiculo, LocalDateTime fechaIngreso, LocalDateTime fechaSalida) {
        this.placa = placa;
        this.tipoVehiculo = tipoVehiculo;
        this.fechaIngreso = fechaIngreso;
        this.fechaSalida = fechaSalida;
        tiempo = 0;
        Pago = 0f;
        SfechaIngreso = "";
        SfechaSalida = "";
    }

    public Vehiculo() {
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getTipoVehiculo() {
        return tipoVehiculo;
    }

    public void setTipoVehiculo(String tipoVehiculo) {
        this.tipoVehiculo = tipoVehiculo;
    }

    public LocalDateTime getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(LocalDateTime fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public LocalDateTime getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(LocalDateTime fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public int getTiempo() {
        return tiempo;
    }

    public void setTiempo(int tiempo) {
        this.tiempo = tiempo;
    }

    public float getPago() {
        return Pago;
    }

    public void setPago(float pago) {
        Pago = pago;
    }

    public String getSfechaIngreso() {
        return SfechaIngreso;
    }

    public void setSfechaIngreso(String sfechaIngreso) {
        SfechaIngreso = sfechaIngreso;
    }

    public String getSfechaSalida() {
        return SfechaSalida;
    }

    public void setSfechaSalida(String sfechaSalida) {
        SfechaSalida = sfechaSalida;
    }
}
