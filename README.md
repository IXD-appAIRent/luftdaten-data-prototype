# luftdaten-data-prototype
This is a prototype for getting luftdaten.info sensor data 

LuftdatenRequest contains:

public static JSONArray OnlyDust()
- removes humidity and temperature sensors 
- orders from lowest to highest PM10 value


public static JSONArray getValuesPM10(JSONArray array)
- returns array with PM10 values, sorted

public static JSONArray getValuesPM25(JSONArray array)
- returns array with PM2.5 values, sorted

public static JSONObject getMaxP1(JSONArray array)
- gets the maximum PM10/PM2.5 Value depending on which input
- getValues must have happened before

public static Double AverageValue(JSONArray array)
- returns Average of PM10/PM2.5 Values depending on which input

public static JSONArray getSensors(JSONArray array)
- takes unimportant information away
- you get: PM10, PM25, latitude, longitude, timestamp, id
