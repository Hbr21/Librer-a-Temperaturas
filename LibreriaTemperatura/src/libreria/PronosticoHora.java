/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package libreria;

/**
 *
 * @author garci
 */
public class PronosticoHora {
    private double temperatura;
    private String icono;

    public PronosticoHora(double temperatura, String icono) {
        this.temperatura = temperatura;
        this.icono = icono;
    }

    public double getTemperatura() {
        return temperatura;
    }

    public String getIcono() {
        return icono;
    }
}
