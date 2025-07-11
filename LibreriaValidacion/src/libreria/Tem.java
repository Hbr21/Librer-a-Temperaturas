package libreria;

import api.Clima;
import javax.swing.*;
import java.awt.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


public class Tem extends JPanel {
    private JLabel lblCiudadConsultada;
    private JLabel lblTemperatura;
    private JLabel lblLluvia;
    private JLabel lblTipo;
    private JLabel lblImagen;
    private JLabel[] lblHoras;
    
    /**
     * 
     **/
    public Tem() {
        setLayout(null);
        setPreferredSize(new Dimension(460, 380));

        JLabel lblTitulo = new JLabel("Consulta del Clima");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        lblTitulo.setBounds(120, 10, 200, 30);

        lblCiudadConsultada = new JLabel("Lugar: --");
        lblCiudadConsultada.setFont(new Font("Arial", Font.BOLD, 14));
        lblCiudadConsultada.setBounds(30, 50, 300, 20);

        lblTemperatura = new JLabel("Temperatura: --");
        lblTemperatura.setFont(new Font("Arial", Font.PLAIN, 14));
        lblTemperatura.setBounds(30, 75, 300, 20);

        lblLluvia = new JLabel("Lluvia: --");
        lblLluvia.setFont(new Font("Arial", Font.PLAIN, 14));
        lblLluvia.setBounds(30, 100, 300, 20);

        lblTipo = new JLabel("Clima: --");
        lblTipo.setFont(new Font("Arial", Font.PLAIN, 14));
        lblTipo.setBounds(30, 125, 300, 20);

        lblImagen = new JLabel();
        lblImagen.setBounds(250, 60, 190, 160);
        
        lblHoras = new JLabel[5];
        for (int i = 0; i < 5; i++) {
        lblHoras[i] = new JLabel("Hora " + (i + 1) + ": --°C");
        lblHoras[i].setBounds(30, 230 + (i * 20), 300, 20);
        add(lblHoras[i]);
        }

        add(lblTitulo);
        add(lblCiudadConsultada);
        add(lblTemperatura);
        add(lblLluvia);
        add(lblTipo);
        add(lblImagen);
    }

    // Este método se llama desde otro proyecto
    public void mostrarClimaDe(String ciudad) {
        try {
            Clima servicio = new Clima();
            Temperatura temp = servicio.obtenerClima(ciudad);
            int timezone=temp.getTimezone();

            int tempC = (int) Math.round(temp.getCelsius());
            String desc = temp.getDescripcion().toLowerCase();

            lblCiudadConsultada.setText("Lugar: " + ciudad);
            lblTemperatura.setText("Temperatura: " + tempC + "°C");
            lblLluvia.setText(temp.hayLluvia() ? "Probabilidad de lluvia" : "No se espera lluvia");
            lblTipo.setText("Clima: " + desc);

            if (desc.contains("lluvia")) {
                lblImagen.setIcon(new ImageIcon(getClass().getResource("/recursos/lluvia.png")));
            } else if (desc.contains("nube") || desc.contains("nublado")) {
                lblImagen.setIcon(new ImageIcon(getClass().getResource("/recursos/nublado.jpg")));
            } else {
                lblImagen.setIcon(new ImageIcon(getClass().getResource("/recursos/sol.jpg")));
            }
            
            List<Double> temps = servicio.obtenerProximasTemperaturas(ciudad);
            Instant ahoraUTC = Instant.now();
            ZoneOffset offset = ZoneOffset.ofTotalSeconds(timezone);
            ZonedDateTime horaLocal = ZonedDateTime.now(ZoneOffset.UTC).withZoneSameInstant(offset);
            DateTimeFormatter formatoHora = DateTimeFormatter.ofPattern("HH:mm");
            
            for (int i = 0; i < temps.size(); i++) {
                ZonedDateTime hora = horaLocal.plusHours(i + 1);
                String horatxt = hora.format(formatoHora);
                lblHoras[i].setText(horatxt + " - " + Math.round(temps.get(i)) + "°C");
            }               
        } catch (Exception ex) {
            lblCiudadConsultada.setText("Lugar: " + ciudad);
            lblTemperatura.setText("Error al obtener clima");
            lblLluvia.setText("");
            lblTipo.setText(ex.getMessage());
            lblImagen.setIcon(null);
        }
    }
    

    // Método opcional para pruebas independientes
    public static void main(String[] args) {
        JFrame frame = new JFrame("Panel de Clima");
        Tem panel = new Tem();
        frame.add(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // Prueba interna
       panel.mostrarClimaDe("Brasil");
      
    }
}

