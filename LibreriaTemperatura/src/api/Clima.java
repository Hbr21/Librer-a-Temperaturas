/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package api;

import libreria.Temperatura;
import libreria.PronosticoHora;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Clase que se conecta a la API de OpenWeather para obtener información del clima.
 * Autor: Heber de Jesús García Bautista
 */
public class Clima {
    private final String API_KEY = "deecee7b720a895d2912c925d5a76347";

    // Método para obtener el clima actual
    public Temperatura obtenerClima(String ciudad) throws Exception {
        String urlStr = "https://api.openweathermap.org/data/2.5/weather?q=" +
                ciudad + "&appid=" + API_KEY + "&units=metric&lang=es";

        String respuesta = obtenerRespuestaDeUrl(urlStr);
        JSONObject json = new JSONObject(respuesta);

        double temp = json.getJSONObject("main").getDouble("temp");
        String descripcion = json.getJSONArray("weather").getJSONObject(0).getString("description");
        boolean hayLluvia = descripcion.toLowerCase().contains("lluvia");
        int timezone = json.getInt("timezone");

        return new Temperatura(temp, descripcion, hayLluvia, timezone);
    }

    // Método para obtener pronóstico de las próximas 5 horas
    public List<PronosticoHora> obtenerProximosPronosticos(String ciudad) throws Exception {
        String urlStr = "https://api.openweathermap.org/data/2.5/forecast?q=" +
                ciudad + "&appid=" + API_KEY + "&units=metric&lang=es";

        String respuesta = obtenerRespuestaDeUrl(urlStr);
        JSONObject json = new JSONObject(respuesta);
        JSONArray lista = json.getJSONArray("list");

        List<PronosticoHora> pronosticos = new ArrayList<>();
        for (int i = 0; i < 5 && i < lista.length(); i++) {
            JSONObject item = lista.getJSONObject(i);
            double temp = item.getJSONObject("main").getDouble("temp");
            String icono = item.getJSONArray("weather").getJSONObject(0).getString("icon");
            pronosticos.add(new PronosticoHora(temp, icono));
        }

        return pronosticos;
    }

    // Método privado reutilizable para hacer llamadas a la API
    private String obtenerRespuestaDeUrl(String urlStr) throws Exception {
        URL url = new URL(urlStr);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        StringBuilder respuesta = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                respuesta.append(inputLine);
            }
        }

        return respuesta.toString();
    }
}

