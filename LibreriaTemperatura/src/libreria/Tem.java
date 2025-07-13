package libreria;

import api.Clima;
import javax.swing.*;
import java.awt.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Tem extends JPanel {
    private JLabel lblCiudadConsultada;
    private JLabel lblTemperatura;
    private JLabel lblLluvia;
    private JLabel lblTipo;
    private JLabel[] lblHoras;
    private JLabel[] lblIconosHora;
    private Image fondo;

    public Tem() {
        setLayout(null);
        setPreferredSize(new Dimension(450, 430));
        fondo = new ImageIcon(getClass().getResource("/recursos/fondo.jpg")).getImage();

        lblCiudadConsultada = crearLabel("Lugar: --", 30, 20, 300);
        lblTemperatura = crearLabel("Temperatura: --", 30, 50, 300);
        lblLluvia = crearLabel("Lluvia: --", 30, 75, 300);
        lblTipo = crearLabel("Clima: --", 30, 100, 300);

        lblHoras = new JLabel[5];
        lblIconosHora = new JLabel[5];

        for (int i = 0; i < 5; i++) {
            lblHoras[i] = new JLabel("Hora " + (i + 1) + ": --°C");
            lblHoras[i].setBounds(70, 235 + (i * 40), 200, 20);
            lblHoras[i].setForeground(Color.BLACK);
            add(lblHoras[i]);

            lblIconosHora[i] = new JLabel();
            lblIconosHora[i].setBounds(30, 230 + (i * 40), 32, 32);
            add(lblIconosHora[i]);
        }

        add(lblCiudadConsultada);
        add(lblTemperatura);
        add(lblLluvia);
        add(lblTipo);
    }

    private JLabel crearLabel(String texto, int x, int y, int ancho) {
        JLabel label = new JLabel(texto);
        label.setFont(new Font("Arial", Font.BOLD, 16));
        label.setForeground(Color.BLACK);
        label.setBounds(x, y, ancho, 20);
        return label;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(fondo, 0, 0, getWidth(), getHeight(), this);
    }

    public void mostrarClimaDe(String ciudad) {
        try {
            Clima servicio = new Clima();
            Temperatura temp = servicio.obtenerClima(ciudad);
            int timezone = temp.getTimezone();

            lblCiudadConsultada.setText("Lugar: " + ciudad);
            lblTemperatura.setText("Temperatura: " + Math.round(temp.getCelsius()) + "°C");
            lblLluvia.setText(temp.hayLluvia() ? "Probabilidad de lluvia" : "No se espera lluvia");
            lblTipo.setText("Clima: " + temp.getDescripcion());

            // Cambiar fondo según descripción
            String desc = temp.getDescripcion().toLowerCase();
            if (desc.contains("lluvia")) {
                fondo = new ImageIcon(getClass().getResource("/recursos/lluvia.jpg")).getImage();
            } else if (desc.contains("nube")) {
                fondo = new ImageIcon(getClass().getResource("/recursos/nublado.jpg")).getImage();
            } else {
                fondo = new ImageIcon(getClass().getResource("/recursos/sol.jpg")).getImage();
            }
            repaint();

            // Obtener datos por hora
            List<PronosticoHora> datos = servicio.obtenerProximosPronosticos(ciudad);
            Instant ahoraUTC = Instant.now();
            ZoneOffset offset = ZoneOffset.ofTotalSeconds(timezone);
            ZonedDateTime horaLocal = ZonedDateTime.ofInstant(ahoraUTC, ZoneOffset.UTC).withZoneSameInstant(offset);
            DateTimeFormatter formatoHora = DateTimeFormatter.ofPattern("HH:mm");

            for (int i = 0; i < datos.size(); i++) {
                ZonedDateTime hora = horaLocal.plusHours(i + 1);
                String horatxt = hora.format(formatoHora);

                PronosticoHora info = datos.get(i);
                lblHoras[i].setText(horatxt + " - " + Math.round(info.getTemperatura()) + "°C");
                

                String nombreIcono = "/recursos/" + info.getIcono() + ".png";
                ImageIcon original = new ImageIcon(getClass().getResource(nombreIcono));
    Image imagenEscalada = original.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
    lblIconosHora[i].setIcon(new ImageIcon(imagenEscalada));
            }

        } catch (Exception ex) {
            lblCiudadConsultada.setText("Lugar: " + ciudad);
            lblTemperatura.setText("Error al obtener clima");
            lblLluvia.setText("");
            lblTipo.setText(ex.getMessage());
            fondo = new ImageIcon(getClass().getResource("/recursos/fondo_error.jpg")).getImage();
            repaint();
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Panel de Clima");
        Tem panel = new Tem();
        frame.add(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // Prueba local
        panel.mostrarClimaDe("Oaxaca");
    }
}




