/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package api;
import libreria.Temperatura;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
/**
 *
 * @author Heber de Jesus García Bautista
 * 
 * Esta clase es la conexion a la API del clima, la cual estamos usando la api
 */
public class Clima {
     private final String API_KEY= "deecee7b720a895d2912c925d5a76347";
     
   public Temperatura obtenerClima(String ciudad) throws Exception {
        String urlStr = "https://api.openweathermap.org/data/2.5/weather?q=" +
                        ciudad + "&appid=" + API_KEY + "&units=metric&lang=es";

        URL url = new URL(urlStr);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder respuesta = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            respuesta.append(inputLine);
        }
        in.close();

        JSONObject json = new JSONObject(respuesta.toString());

        double temp = json.getJSONObject("main").getDouble("temp");
        String descripcion = json.getJSONArray("weather").getJSONObject(0).getString("description");

        // ← Lluvia: buscar si el texto contiene la palabra "lluvia"
        boolean hayLluvia = descripcion.toLowerCase().contains("lluvia");
        
        int timezone = json.getInt("timezone");
        return new Temperatura(temp, descripcion, hayLluvia,timezone);
    }
   
   
    public List<Double> obtenerProximasTemperaturas(String ciudad) throws Exception {
    String urlStr = "https://api.openweathermap.org/data/2.5/forecast?q=" +
                    ciudad + "&appid=" + API_KEY + "&units=metric&lang=es";

    URL url = new URL(urlStr);
    HttpURLConnection con = (HttpURLConnection) url.openConnection();
    con.setRequestMethod("GET");

    BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
    StringBuilder respuesta = new StringBuilder();
    String inputLine;
    while ((inputLine = in.readLine()) != null) {
        respuesta.append(inputLine);
    }
    in.close();

    JSONObject json = new JSONObject(respuesta.toString());
    JSONArray lista = json.getJSONArray("list");
 

    List<Double> temperaturas = new ArrayList<>();
    for (int i = 0; i < 5 && i < lista.length(); i++) {
        JSONObject item = lista.getJSONObject(i);
        double temp = item.getJSONObject("main").getDouble("temp");
        temperaturas.add(temp);
    }

    return temperaturas;
    }
}
