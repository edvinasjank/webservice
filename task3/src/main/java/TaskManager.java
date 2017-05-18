import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import  spark.Request;
import  spark.Response;
import java.util.UUID;

public class TaskManager {
    User[] users = new User[20];
    int usersCount = 0;
    
   
    public TaskManager(){
      users[0] = new User();
      users[1] = new User();
      users[2] = new User();
      users[0].setUsername("Edvinas");
      users[0].password = "edva123";
      users[0].permissions = "admin";
      users[0].token = "prisijungimas";
      users[1].username = "Arnas";
      users[1].password = "arnas123";
      users[1].permissions = "admin";
      users[1].token = "prisijungimas1";
      users[2].username = "Benas";
      users[2].password = "benas123";
      users[2].permissions = "user";
      users[2].token = "prisijungimas2";
      usersCount = 3;
    }
   
    
     public String registerHuman(Request request, Response response) throws Exception{
        UUID uid = UUID.fromString("38400000-8cf0-11bd-b23e-10b96e4ef00d"); 
        try{
            String body = request.body();
            if("".equals(body)){
                response.status(400);
                throw new Exception("400. Invalid input");
            }
            User tempUser = fromJson(request.body(), User.class);
            for(int i = 0 ; i < usersCount; i++){
                if( tempUser.username == null ||  tempUser.username.equals(users[i].username) ){
                    response.status(400);
                    throw new Exception("400. Error with input! Incorrect or missing fields!");  
                }
            }
            if(tempUser.permissions != null && (tempUser.permissions.equals("admin") || tempUser.permissions.equals("user")) 
                    && !tempUser.password.equals("") && !tempUser.username.equals("")
                   && tempUser.password != null ){
                tempUser.token = String.valueOf(uid.randomUUID());
                users[usersCount] = tempUser;
                usersCount++;
            }
            else{
                response.status(400);
                throw new Exception("400. Invalid input");
            }
        }
        catch(Exception e){
            response.status(400);
            return toJson(e.getMessage());
        }
        System.out.println(usersCount);
        return  users[usersCount-1].token;
    }
     public String loginHuman(Request request, Response response) throws Exception{
       int i;
         String body = request.body();
            if("".equals(body)){
                response.status(400);
                throw new Exception("400. Invalid input");
            }
            User tempUser = fromJson(request.body(), User.class);
         try{
            if(tempUser.username == null) {
                response.status(400);
                throw new Exception("400. Missing username!");
            }
            boolean found = false;
            for( i = 0; i < usersCount; i++){
                if( tempUser.username.equals(users[i].username) &&  tempUser.password.equals(users[i].password)){
                   found = true;
                   break;
                }
            }
            if( found == false){
                    response.status(400);
                    throw new Exception("400. Incorrect username!");  
            }
         }
         catch(Exception e){
             response.status(400);
            return toJson(e.getMessage());
         }
            return users[i].token;
     }
     public boolean checkToken(String token){
         for(int i = 0; i < usersCount; i++){
             if(users[i].token.equals(token)){
                 return true;
             }
         }
         return false;  
     }
     public boolean checkPermission(String token, String permission){
          for(int i = 0; i < usersCount; i++){
             if(users[i].token.equals(token) && users[i].permissions.equals(permission)){
                 return true;
             }
         }
         return false;  
     }
     public String getAllCompanies(Request request, Response response) throws IOException{
         try{
          String tokenToCheck = request.headers("Authorization");
         if(checkToken(tokenToCheck)){
             return getRequest("companies");
         }
         else{
            response.status(401);
             throw new Exception("401. Unauthorized request!");
            }
         }
         catch(Exception e){
             return toJson(e.getMessage());
         }
       
     }
     public String getCompanyById(Request request, Response response) throws IOException, Exception{
         try{
         String tokenToCheck = request.headers("Authorization");
         if(checkToken(tokenToCheck)){
             String tempRequest = "companies/" + request.params("id"); 
            return getRequest(tempRequest);
         }
         else{
             response.status(401);
             return toJson("401. Unauthorized request!");
         }
         }
         catch(Exception e){
             return toJson("400. Invalid id");
         }
     }
     public String getCompaniesByCity(Request request, Response response){
         try{
         String tokenToCheck = request.headers("Authorization");
         if(checkToken(tokenToCheck)){
             String tempRequest = "companies/city/" + request.params("city"); 
            return getRequest(tempRequest);
         }
         else{
             response.status(401);
             return toJson("401. Unauthorized request!");
         }
         }
         catch(Exception e){
             response.status(400);
             return toJson("400.Invalid city!");
         }
         
     }
     public String getCompaniesByName(Request request, Response response){
          try{
         String tokenToCheck = request.headers("Authorization");
         if(checkToken(tokenToCheck)){
             String tempRequest = "companies/name/" + request.params("name"); 
            return getRequest(tempRequest);
         }
         else{
             response.status(401);
             return toJson("401. Unauthorized request!");
         }
         }
         catch(Exception e){
             response.status(400);
             return toJson("400.Invalid company name!");
         }
     }
     public String getCountByCity(Request request, Response response){
           try{
         String tokenToCheck = request.headers("Authorization");
         if(checkToken(tokenToCheck)){
             String tempRequest = "companies/cityCount/" + request.params("city"); 
            return getRequest(tempRequest);
         }
         else{
             response.status(401);
             return toJson("401. Unauthorized request!");
         }
         }
         catch(Exception e){
             response.status(400);
             return toJson("400.Invalid city!");
         }
     }
     public String getCountByName(Request request, Response response){
           try{
         String tokenToCheck = request.headers("Authorization");
         if(checkToken(tokenToCheck)){
             String tempRequest = "companies/companyCount/" + request.params("name"); 
            return getRequest(tempRequest);
         }
         else{
             response.status(401);
             return toJson("401. Unauthorized request!");
         }
         }
         catch(Exception e){
             response.status(400);
             return toJson("400.Invalid name!");
         }
     }
     public String deleteById(Request request, Response response){
          try{
         String tokenToCheck = request.headers("Authorization");
         if(checkPermission(tokenToCheck,"admin")){
             String tempRequest = "companies/" + request.params("id"); 
            return deleteRequest(tempRequest);
         }
         else{
             response.status(401);
             return toJson("401. Unauthorized request(invalid token or you have no permission");
         }
         }
         catch(Exception e){
             response.status(400);
             return toJson("400. Invalid id!");
         }
     }              
    public String getRequest(String arg) throws IOException{
        String otherService = "";
        String url = "http://company" + "/" + arg;
      
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        BufferedReader in = new BufferedReader( new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response1 = new StringBuffer();
    
		while ((inputLine = in.readLine()) != null) {
                     
			response1.append(inputLine);
		}
		in.close(); 
                otherService = response1.toString();
          return otherService;
    }
    public String deleteRequest(String arg) throws ProtocolException, MalformedURLException, IOException{

        String otherService = "";
        String url = "http://company" + "/" + arg;
      
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setRequestMethod("DELETE");

        con.getResponseCode();

        BufferedReader in = new BufferedReader( new InputStreamReader(con.getInputStream()));

		String inputLine;
		StringBuffer response1 = new StringBuffer();
    
		while ((inputLine = in.readLine()) != null) {
                     
			response1.append(inputLine);
		}
		in.close(); 
                System.out.println("6");
                otherService = response1.toString();
          return otherService;
    }
    public String createPutRequest(String arg, String requestString, String requestParam) throws IOException{       
        String otherService = "";
        String url = "http://company" + "/" + arg;  
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        if(requestParam.equals("PATCH")){
            con.setRequestProperty("X-HTTP-Method-Override", "PATCH");
            con.setRequestMethod("POST");
        }
        else{
           con.setRequestMethod(requestParam); 
        }
         con.setDoOutput(true);
      DataOutputStream wr = new DataOutputStream(con.getOutputStream());
      wr.writeBytes(requestString);
      wr.flush();
      wr.close();
      
        BufferedReader in = new BufferedReader( new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response1 = new StringBuffer();
    
		while ((inputLine = in.readLine()) != null) {
                     
			response1.append(inputLine);
		}
		in.close(); 
                 
                otherService = response1.toString();
          return otherService;
    }
    public String createCompany(Request request, Response response) throws Exception{
        try{
            
    String tokenToCheck = request.headers("Authorization");
         if(checkPermission(tokenToCheck,"admin")){
             String tempRequest = "companies"; 
            
        String tempBody = request.body();
        if("".equals(tempBody)){
            response.status(400);
            throw new Exception("400. Invalid input!");
        }
        FinalJson jsonTemp = fromJson(request.body(),FinalJson.class);
        //jei ka cia gali riekt uzsetint headeri
        
         String requestString = " { \"ownerName\": " + "\""+jsonTemp.ownerName +"\""+  ", ";
         requestString += "\"ownerSurname\": " + "\"" +jsonTemp.ownerSurname+ "\"" + ", "; 
         requestString += "\"ownerGender\": " + "\"" + jsonTemp.ownerGender + "\"" +", ";
         requestString += "\"ownerAddress\": " + "\""+ jsonTemp.ownerAddress +"\""+ ",";
         requestString += "\"name\": " + "\""+ jsonTemp.name +"\""+ ",";
         requestString += "\"city\": " + "\""+ jsonTemp.city +"\""+ ",";
         requestString += "\"phoneNumber\": " + "\""+ jsonTemp.phoneNumber +"\""+ "}";
         return createPutRequest(tempRequest,requestString,"POST");
         }
         else{
            response.status(401);
             return toJson("401. Unauthorized request(invalid token or you have no permission"); 
         }
        }
        catch(Exception e){
            response.status(400);
            return toJson("400. Bad request!");
        }
        
    }
    public String updateCompany(Request request, Response response){
          try{        
    String tokenToCheck = request.headers("Authorization");
         if(checkPermission(tokenToCheck,"admin")){
             String tempRequest = "companies/" + request.params("id"); 
        String tempBody = request.body();
        if("".equals(tempBody)){
            response.status(400);
            throw new Exception("400. Invalid input!");
        }
        FinalJson jsonTemp = fromJson(request.body(),FinalJson.class);
         String requestString = " { \"ownerName\": " + "\""+jsonTemp.ownerName +"\""+  ", ";
         requestString += "\"ownerSurname\": " + "\"" +jsonTemp.ownerSurname+ "\"" + ", "; 
         requestString += "\"ownerGender\": " + "\"" + jsonTemp.ownerGender + "\"" +", ";
         requestString += "\"ownerAddress\": " + "\""+ jsonTemp.ownerAddress +"\""+ ",";
         if(jsonTemp.name != null ){
             requestString += "\"name\": " + "\""+ jsonTemp.name +"\""+ ",";
         }
         else{
             requestString += "\"name\": " + "\""+ "nenustatyta" +"\""+ ",";
         }
         if(jsonTemp.city != null ){
             requestString += "\"city\": " + "\""+ jsonTemp.city +"\""+ ",";
         }
         else{
             requestString += "\"city\": " + "\""+ "nenustatyta" +"\""+ ",";
         }
         if(jsonTemp.phoneNumber != 0  ){
             requestString += "\"phoneNumber\": " + "\""+ jsonTemp.phoneNumber +"\""+ "}";
         }
         else{
             requestString += "\"phoneNumber\": " + "\""+ 0 +"\""+ "}";
         }
      
         return createPutRequest(tempRequest,requestString,"PUT");
         }
         else{
            response.status(401);
             return toJson("401. Unauthorized request(invalid token or you have no permission"); 
         }
        }
        catch(Exception e){
            response.status(400);
            return toJson("400. Bad request!");
        }
    }
    public String patchCompany(Request request, Response response){
         try{        
    String tokenToCheck = request.headers("Authorization");
         if(checkPermission(tokenToCheck,"admin")){
             String tempRequest = "companies/" + request.params("id"); 
        String tempBody = request.body();
        if("".equals(tempBody)){
            response.status(400);
            throw new Exception("400. Invalid input!");
        }
        FinalJson jsonTemp = fromJson(request.body(),FinalJson.class);
        String requestString = " { ";
        boolean added1 = false;
        boolean added2 = false;
        if(jsonTemp.name != null ){
             requestString += "\"name\": " + "\""+ jsonTemp.name +"\"";
             added1 = true;
         }
         if(jsonTemp.city != null ){
             if(added1 == true){
                 requestString +=", " + "\"city\": " + "\""+ jsonTemp.city +"\""; 
                 added2 = true;
             }
             else{
                requestString += "\"city\": " + "\""+ jsonTemp.city +"\"";  
             }
         } 
         if(jsonTemp.phoneNumber != 0  ){
             if(added1 == true || added2 == true){
                requestString += "," + "\"phoneNumber\": " + "\""+ jsonTemp.phoneNumber +"\""; 
             }else{
                  requestString += "\"phoneNumber\": " + "\""+ jsonTemp.phoneNumber +"\""; 
             }
             
         }
         requestString += " }";
         
         return createPutRequest(tempRequest,requestString,"PATCH");
         }
         else{
            response.status(401);
             return toJson("401. Unauthorized request(invalid token or you have no permission"); 
         }
        }
        catch(Exception e){
            response.status(400);
            return toJson("400. Bad request!");
        }
    }
    
    public static <T extends Object> T  fromJson(String json, Class<T> classe) {
       Gson gson = new Gson();
       gson.fromJson(json, classe);
        return gson.fromJson(json, classe);
         
    }
    static class FinalJson{
        int id;
        String name;
        String city;
        int phoneNumber;
        String ownerSurname;
        String ownerName;
        String ownerGender;
        String ownerAddress;
        int ownerId;
     }
     public static String toJson(Object value){
         Gson gson = new Gson();
        String json = gson.toJson(value);
        return json;
     }
}
