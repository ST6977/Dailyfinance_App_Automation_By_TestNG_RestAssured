package Utils;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

import static io.restassured.RestAssured.given;


public class utils {

    public static String FilePath = "./src/test/resources/users.json";
    public static Properties props;
    public static FileInputStream file;


    public static void setEnvVar(String key, String value) throws org.apache.commons.configuration.ConfigurationException {
        PropertiesConfiguration config = new PropertiesConfiguration("./src/test/resources/config.properties");
        config.setProperty(key, value);
        config.save();
    }


    public static int generateRandomNumber(int min, int max) {
        double randomId = Math.random() * (max - min) + min;
        return (int) randomId;
    }


    public static void SaveAllInfo(String filePath, JSONObject jsonObject) throws IOException, ParseException, IOException, ParseException {
        JSONParser jsonParser = new JSONParser();
        JSONArray jsonArray = (JSONArray) jsonParser.parse(new FileReader(filePath));
        jsonArray.add(jsonObject);
        FileWriter fileWriter = new FileWriter(filePath);
        fileWriter.write(jsonArray.toString());
        fileWriter.flush();
        fileWriter.close();
    }


    public static String getEmailList() throws IOException {

        props = new Properties();
        file = new FileInputStream("./src/test/resources/config.properties");
        props.load(file);

        RestAssured.baseURI = "https://gmail.googleapis.com";
        Response res = given().contentType("application/json")
                .header("Authorization", "Bearer " + props.getProperty("google_access_token"))
                .when().get("/gmail/v1/users/me/messages");



              JsonPath jsonPath = res.jsonPath();
              return jsonPath.getString("messages[0].id");

    }



    public static String readLatestMail() throws IOException {

        String messageId = getEmailList();
        RestAssured.baseURI = "https://gmail.googleapis.com";
        Response res = given().contentType("application/json")
                .header("Authorization", "Bearer " + props.getProperty("google_access_token"))
                .when().get("/gmail/v1/users/me/messages/" + messageId);

        // System.out.println(res.asString());

        JsonPath jsonPath = res.jsonPath();
        return jsonPath.getString("snippet");

    }


    public static String getUpdatedEmailPassProperty(String key) throws IOException, ParseException {

        JSONParser parser=new JSONParser();
        JSONArray jsonArray= (JSONArray) parser.parse(new FileReader(String.valueOf(FilePath)));
        JSONObject jsonObject = (JSONObject) jsonArray.get(jsonArray.size() -1);

        String value = (String) jsonObject.get(key);
        return  value;



    }



    public static void updatedGmailCreds(String field, String newData) throws IOException, ParseException {
        JSONParser jsonParser = new JSONParser();
        JSONArray jsonArray = (JSONArray) jsonParser.parse(new FileReader(FilePath));
        JSONObject updatedUserObj = (JSONObject) jsonArray.get(jsonArray.size() - 1);
        updatedUserObj.put(field, newData);
        fileWriteProcess(jsonArray);
    }


    private static void fileWriteProcess(JSONArray jsonArray) throws IOException {
        try (FileWriter fileWriter = new FileWriter(FilePath)) {
            fileWriter.write(jsonArray.toJSONString());
            fileWriter.flush();
        }
    }



    public static void incrementEmailCount() throws IOException, ParseException {

        JSONParser jsonParser=new JSONParser();
        JSONArray jsonArray= (JSONArray) jsonParser.parse(new FileReader(FilePath ));
        JSONObject emailCountObj = (JSONObject) jsonArray.get(0);
        // Retrieve the email count and check for null
        String emailCountStr = (String) emailCountObj.get("emailCount");
        int emailCount = (emailCountStr != null) ? Integer.parseInt(emailCountStr) : 0;
        // Increment the email count
        emailCount += 1;
        emailCountObj.put("emailCount" , String.valueOf(emailCount));
        fileWriteProcess(jsonArray);


    }


}
