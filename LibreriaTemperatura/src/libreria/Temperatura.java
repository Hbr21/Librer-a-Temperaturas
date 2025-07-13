/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package libreria;

/**
 *
 * @author garci
 */
public class Temperatura {
    private double valorCelsius;
    private String descripcion;
    private boolean lluvia;
    private int timezone;
            
    
    public Temperatura(double valorCelsius, String descripcion, boolean lluvia, int timezone) {
        this.valorCelsius = valorCelsius;
        this.descripcion = descripcion;
        this.lluvia = lluvia;
        this.timezone=timezone;
    }
    
    public double getCelsius() {
        return valorCelsius;
    }
    
    public double getFahrenheit() {
        return (valorCelsius * 9 / 5) + 32;
    }
    
    public double getKelvin() {
        return valorCelsius + 273.15;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
    
    public boolean hayLluvia() {
        return lluvia;
    }
    
    public boolean haceFrio() {
        return valorCelsius < 15.0;
    }
    
    public boolean haceCalor() {
        return valorCelsius > 30.0;
    }
    public int getTimezone(){
        return timezone;
    }
}
