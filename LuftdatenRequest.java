import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.*;

public class LuftdatenRequest {
	// public static void main(String[] args) {
  //    try {
  //        JSONArray Sensordaten = LuftdatenRequest.OnlyDust();
	// 			//System.out.println(LuftdatenRequest.getMaxP1(Sensordaten));
	// 			JSONArray PM10Values = LuftdatenRequest.getValuesPM10(Sensordaten);
	// 			//System.out.println("PM 10 Werte: "+PM10Values);
	// 			Double AveragePM10 = LuftdatenRequest.AverageValue(PM10Values);
	// 			System.out.println("Durchschnitt PM10: "+AveragePM10);
	// 			JSONArray PM25Values = LuftdatenRequest.getValuesPM25(Sensordaten);
	// 			//System.out.println("PM 2.5 Werte: "+PM25Values);
	// 			Double AveragePM25 = LuftdatenRequest.AverageValue(PM25Values);
	// 			System.out.println("Durchschnitt PM2.5: "+AveragePM25);
	// 			JSONArray Sensors = LuftdatenRequest.getSensors(Sensordaten);
	// 			/*for(int i=0; i<Sensors.length(); i++){
	// 			 	 System.out.println("Sensor: "+Sensors.getJSONObject(i).get("id")+ " : "+Sensors.get(i));
	// 		  }*/
	//
	// 			} catch (Exception e) {
  //        e.printStackTrace();
  //      }
  //    }

     public static JSONArray OnlyDust() throws Exception {
          String url = "http://api.luftdaten.info/v1/filter/area=52.5221,13.3347,30";
          URL obj = new URL(url);
          HttpURLConnection con = (HttpURLConnection) obj.openConnection();
          // optional default is GET
          con.setRequestMethod("GET");
          //add request header
          con.setRequestProperty("User-Agent", "Mozilla/5.0");
          int responseCode = con.getResponseCode();
          System.out.println("\nSending 'GET' request to URL : " + url);
          System.out.println("Response Code : " + responseCode);
          BufferedReader in = new BufferedReader(
                  new InputStreamReader(con.getInputStream()));
          String inputLine;
          StringBuffer response = new StringBuffer();
          while ((inputLine = in.readLine()) != null) {
          	response.append(inputLine);
          }
          in.close();
          //print in String
          //System.out.println(response.toString());
          //Read JSON response and print
          JSONArray myResponse = new JSONArray(response.toString());
          // System.out.println("result after Reading JSON Response");
					// JSONObject curr = new JSONObject();
					// JSONObject currLoc = new JSONObject();
					// for(int i = 0; i < myResponse.length(); i++){
					// 	try{
					// 		curr = myResponse.getJSONObject(i);
					// 		currLoc = curr.getJSONObject("location");
					// 		System.out.println("Sensor " +curr.get("id")+ " latitude: "+currLoc.get("latitude")+" longitude: "+currLoc.get("longitude"));
					// 	} catch ( Exception e){
					// 		System.out.println("JSON Problem");
					// 	}
					// }

					ArrayList<JSONObject> myArray = new ArrayList<JSONObject>();
					for(int i = 0; i< myResponse.length(); i++){
						if(myResponse.getJSONObject(i).getJSONArray("sensordatavalues").getJSONObject(0).getString("value_type").equals("P1")
						&& myResponse.getJSONObject(i).getJSONObject("sensor").getJSONObject("sensor_type").getString("name").equals("SDS011")){
							myArray.add(myResponse.getJSONObject(i));
						}
					}

					Collections.sort(myArray, new Comparator<JSONObject>() {
				    @Override
				    public int compare(JSONObject jsonObjectA, JSONObject jsonObjectB) {
				        int compare = 0;
				        try
				        {

				            Double keyA = jsonObjectA.getJSONArray("sensordatavalues").getJSONObject(0).getDouble("value");
										Double keyB = jsonObjectB.getJSONArray("sensordatavalues").getJSONObject(0).getDouble("value");

				            compare = Double.compare(keyA, keyB);
				        }
				        catch(Exception e)
				        {
				            e.printStackTrace();
				        }
				        return compare;
				    }
					 });
					 JSONArray myJsonArray = new JSONArray();
 					 for (int i = 0; i < myArray.size(); i++) {
 					    myJsonArray.put(myArray.get(i));
 					 }
					 return myJsonArray;
					//  JSONObject max = myArray.get(myArray.size()-1);
					//  JSONObject min = myArray.get(0);
					//  System.out.println("höchster Wert: "+ max.getJSONArray("sensordatavalues").getJSONObject(0).getDouble("value"));
					//  System.out.println("niedrigster Wert: "+ min.getJSONArray("sensordatavalues").getJSONObject(0).getDouble("value"));



        }


			public static JSONObject getMaxP1(JSONArray array) throws Exception {
				JSONObject max = new JSONObject();
				try{
					max = array.getJSONObject(array.length()-1);
				} catch(Exception e)
				{
						e.printStackTrace();
				}
				return max;
			}

			public static JSONArray getValuesPM10(JSONArray array) throws Exception {
				JSONArray newArray = new JSONArray();
				try{
					for(int i = 0; i< array.length(); i++){
							newArray.put(i, array.getJSONObject(i).getJSONArray("sensordatavalues").getJSONObject(0).getDouble("value") );
					}
				} catch(Exception e)
				{
						e.printStackTrace();
				}
				return newArray;
			}

			public static JSONArray getValuesPM25(JSONArray array) throws Exception {
				JSONArray newArray = new JSONArray();
				try{
					for(int i = 0; i< array.length(); i++){
							newArray.put(i, array.getJSONObject(i).getJSONArray("sensordatavalues").getJSONObject(1).getDouble("value") );
					}
				} catch(Exception e)
				{
						e.printStackTrace();
				}
				return newArray;
			}


			public static Double AverageValue(JSONArray array) throws Exception {
				Double sum = 0.0;
				for(int i = 0; i<array.length();i++){
					sum = sum + array.getDouble(i);
				}
				sum = sum/array.length();
				return sum;
			}

			public static JSONArray getSensors(JSONArray array) throws Exception {
				JSONArray newArray = new JSONArray();
				try{
					for(int i = 0; i< array.length(); i++){

						 array.getJSONObject(i).put("PM10", array.getJSONObject(i).getJSONArray("sensordatavalues").getJSONObject(0).get("value"));

						 array.getJSONObject(i).put("PM25", array.getJSONObject(i).getJSONArray("sensordatavalues").getJSONObject(1).get("value"));

						 array.getJSONObject(i).remove("sensordatavalues");

						 array.getJSONObject(i).remove("sensor");

						 array.getJSONObject(i).put("latitude", array.getJSONObject(i).getJSONObject("location").remove("latitude"));

						 array.getJSONObject(i).put("longitude", array.getJSONObject(i).getJSONObject("location").remove("longitude"));

						 array.getJSONObject(i).remove("location");

						 array.getJSONObject(i).remove("sampling_rate");


						 newArray.put(array.get(i));


					}
				} catch(Exception e)
				{
						e.printStackTrace();
				}
				return newArray;
			}


     }
