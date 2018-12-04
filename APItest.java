import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.*;

public class APItest {


public static void main(String[] args) {
   try {
     LuftdatenRequest test = new LuftdatenRequest();
       JSONArray Sensordaten = test.OnlyDust();
      System.out.println(test.getMaxP1(Sensordaten));
      JSONArray PM10Values = test.getValuesPM10(Sensordaten);
      System.out.println("PM 10 Werte: "+PM10Values);
      Double AveragePM10 = test.AverageValue(PM10Values);
      System.out.println("Durchschnitt PM10: "+AveragePM10);
      JSONArray PM25Values = test.getValuesPM25(Sensordaten);
      System.out.println("PM 2.5 Werte: "+PM25Values);
      Double AveragePM25 = test.AverageValue(PM25Values);
      System.out.println("Durchschnitt PM2.5: "+AveragePM25);
      JSONArray Sensors = test.getSensors(Sensordaten);
      for(int i=0; i<20; i++){
         System.out.println("Sensor: "+Sensors.getJSONObject(i).get("id")+ " : "+Sensors.get(i));
      }

      } catch (Exception e) {
       e.printStackTrace();
     }
   }
}
