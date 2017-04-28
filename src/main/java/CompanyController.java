
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;
import spark.Request;
import spark.Response;
import static spark.utils.StringUtils.isEmpty;
import java.net.URL;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author edvinas
 */
public class CompanyController {
     public static Object getAllCompanies(Request request, Response response, CompaniesEditor compEdit) {
        return compEdit.getAll();
    }
    public static Object getCompanyById(Request request, Response response, CompaniesEditor compEdit){
        HttpURLConnection con = null;
        try{
           int id = Integer.valueOf(request.params("id"));
           Company company = compEdit.get(id);
           
           if(company != null){
             FinalJson json = new FinalJson();
                  String otherService = "";
                try{  
       String url = "http://owner:4321/people/" + id ;
		URL obj = new URL(url);
		con = (HttpURLConnection) obj.openConnection();
                
		con.setRequestMethod("GET");
		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response1 = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response1.append(inputLine);
		}
		in.close(); 
                otherService = response1.toString();
		
		JsonObject jsonObject = new JsonParser().parse(otherService).getAsJsonObject();
        String ownerName = jsonObject.get("name").getAsString();
        String ownerSurname =  jsonObject.get("surname").getAsString();
        String ownerGender = jsonObject.get("gender").getAsString();
        String ownerAddress = jsonObject.get("address").getAsString();
        json.ownerAddress = ownerAddress;
       json.ownerGender = ownerGender;
       json.ownerName = ownerName ;
       json.ownerSurname =  ownerSurname;
       json.id = company.getId();
       json.city = company.getCity();
       json.name = company.getName();
       json.phoneNumber = company.getPhoneNumber();
        
               return json;
                }
                catch(Exception e){
                  return company;
                }
        
           }
           else{
              response.status(404);
               throw new Exception("404. Failed to find company by id: " + id + "!");
           }

        }
       catch(Exception e){
           return e.getMessage();
       }
        finally{
            
            if(con != null){
                con.disconnect();
            }
        }
    }
    public static Object getCompaniesByCity(Request request, Response response, CompaniesEditor compEdit){
        try{
            String city = String.valueOf(request.params("city"));
            Map<Integer,Company> companies = new HashMap();
            companies = compEdit.getByCity(city);
            if(companies.size() > 0){
                return companies;
            }
            else{
                response.status(404);
                throw new Exception("404. Failed to find company by city: " + city + "!");
            }
            
        }catch(Exception e){
            return e.getMessage();
        }
    }
    public static Object getCompaniesByName(Request request, Response response, CompaniesEditor compEdit){
        try{
            String name = String.valueOf(request.params("name"));
            Map<Integer,Company> companies = new HashMap();
            companies = compEdit.getByName(name);
            if(companies.size() > 0){
                return companies;
            }
            else{
                response.status(404);
                throw new Exception("404. Failed to find company by name: " + name + "!");
            }
            
        }catch(Exception e){
            return e.getMessage();
        }
    }
    public static Object getCountByCity(Request request, Response response, CompaniesEditor compEdit){
        try{
            String city = String.valueOf(request.params("city"));
            int count = compEdit.cityCount(city);
            if(count != 0){
                return "Total companies in " + city + ": " + count;
            }
            else{
                response.status(404);
                throw new Exception("404. There is no companies in city: " + city);
            }
        }
        catch(Exception e){
            return e.getMessage();
        }
    }
    public static Object getCountByCompany(Request request, Response response, CompaniesEditor compEdit){
        try{
            String name = String.valueOf(request.params("name"));
            int count = compEdit.companyCount(name);
            if(count != 0){
                return "Total companies in " + name + ": " + count;
            }
            else{
                response.status(404);
                throw new Exception("404. There is no companies with name: " + name);
            }
        }
        catch(Exception e){
            return e.getMessage();
        }
    }
    public static Object deleteById(Request request, Response response, CompaniesEditor compEdit){
        int id = Integer.valueOf(request.params("id"));
        HttpURLConnection con = null;
        URL obj = null;
        Company company = compEdit.get(id);
       
        try{
            if(company != null){
                 
                int ownerId = company.getOwnerId();
           try {
                   obj = new URL("http://owner:4321/people/"  + ownerId);  
                             
                System.out.println("33333");
                
                con = (HttpURLConnection) obj.openConnection();
                System.out.println("12563");
                 con.setRequestProperty("Content-Type",
                "application/x-www-form-urlencoded");
                 //System.out.println(con.getResponseCode());
                
                System.out.println("000");
                 
		con.setRequestMethod("DELETE");
                con.getResponseCode();
                System.out.println("44444");
                compEdit.delete(id);
                System.out.println("55555");
                 return "Company with ID: " + id + "  successfully deleted!";  
            } catch (Exception exception) {
                compEdit.delete(id);
                return "Company with ID: " + id + "  successfully deleted from company service!";
            }  
            }
            else{
                response.status(404);
                throw new Exception("404. There is no company with id: " + id + "!");
            } 
            
        }
        catch(Exception e){
            return e.getMessage();
        }
        finally {         
                 if (con != null) {
                     con.disconnect();
                     System.out.println("9999999");
                }
        }
    }
    
     public static Object createCompany(Request request, Response response, CompaniesEditor compEdit) {
        System.out.println( request.body());
        HttpURLConnection con = null;
        try{
            String temp = request.body();
            if("".equals(temp)){
                response.status(400);
                throw new Exception("400. Invalid input");
            }
                Company company = new Company();
                FinalJson jsonTemp = fromJson(request.body(), FinalJson.class);
                company.setCity(jsonTemp.city);
                company.setName(jsonTemp.name);
                company.setPhoneNumber(jsonTemp.phoneNumber); 
                response.header("PATH", "companies/" + company.getId());
                compEdit.create(company);
                System.out.println("Haasas");
                try{
                String url = "http://owner:4321/people" ;
		URL obj = new URL(url);
		con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("POST");
		String requestString = " { \"name\": " + "\""+jsonTemp.ownerName +"\""+  ", ";
                requestString += "\"surname\": " + "\"" +jsonTemp.ownerSurname+ "\"" + ", "; 
                requestString += "\"gender\": " + "\"" + jsonTemp.ownerGender + "\"" +", ";
                requestString += "\"address\": " + "\""+ jsonTemp.ownerAddress +"\""+ "}";
                System.out.println(requestString);
                 con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(requestString);
		wr.flush();
		wr.close();
            //int responseCode = con.getResponseCode();
            BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response2 = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response2.append(inputLine);
		}
		in.close();

		//print result
                System.out.println(request.body());
		
                System.out.println("hellooo");
                System.out.println(response2.toString());
                return company; 
        }
                 catch(Exception e){
            company = new Company();
                jsonTemp = fromJson(request.body(), FinalJson.class);
                company.setCity(jsonTemp.city);
                company.setName(jsonTemp.name);
                company.setPhoneNumber(jsonTemp.phoneNumber); 
                response.header("PATH", "companies/" + company.getId());
                compEdit.create(company);
                company.setOwnerId(-1);
            return company;
                
       }
        }  catch(Exception e){    
            response.status(400);
            return e.getMessage();
        }
        /*for(int i = 0; i < temp.length(); i++){
            if(temp.charAt(i) == 'p' && temp.charAt(i+1) == 'h' && temp.charAt(i+2) == 'o' && temp.charAt(i+3) == 'n' &&
                    temp.charAt(i+4) == 'e' &&
                    temp.charAt(i+5) == 'N' &&
                    temp.charAt(i+6) == 'u' &&
                    temp.charAt(i+7) == 'm' &&
                    temp.charAt(i+8) == 'b' &&
                    temp.charAt(i+9) == 'e' &&
                    temp.charAt(i+10) == 'r' 
                    ){
               
                for(int j = i+13; j < temp.length(); j++){
                        if(temp.charAt(j) == '0' || temp.charAt(j) == '1' ||temp.charAt(j) == '2' ||temp.charAt(j) =='3' ||temp.charAt(j) == '4' ||
                                temp.charAt(j) == '5' ||temp.charAt(j) == '6' ||temp.charAt(j) == '7' || temp.charAt(j) == '8' || temp.charAt(j) == '9'){
                                numberCount++;
                        }
                        
                }
            }
        }
       if(numberCount == 9){
            
            try{
                if(isAlpha(company.getCity()) && company.getPhoneNumber()<870000000 
                        && company.getPhoneNumber() >= 860000000){
                    compEdit.create(company,id);
                return company; 
                }
                else {response.status(400);
                    return "Error with data input!";
                }
                
            }
            catch(Exception e){
                return e.getMessage();
            }
        } 
       else{
           response.status(400);
           return "Error with data input!";
       }*/
        finally{
            if(con != null){
                con.disconnect();
            }
        }
    }
    public static Object updateCompany(Request request, Response response, CompaniesEditor compEdit) throws Exception{
        int id = Integer.valueOf(request.params("id"));
        //Company company = fromJson(request.body(), Company.class);
       // System.out.println(company);
       // System.out.println(company.getName());
       Company company = null;
       HttpURLConnection con = null;
        try{
            if(isEmpty(request.body())){
                response.status(400);
                throw new Exception("400. Empty body");
            }
            
            else{
                 if(compEdit.get(id) == null){
                        response.status(404);
                       throw new Exception("404. There is no company with id: " + id + "!");
                 }
                company = new Company();
                FinalJson jsonTemp = fromJson(request.body(), FinalJson.class);
                company.setCity(jsonTemp.city);
                company.setName(jsonTemp.name);
                company.setPhoneNumber(jsonTemp.phoneNumber); 
                company.setOwnerId(id);
                response.header("PATH", "companies/" + company.getId());
                compEdit.update(id, company);
                String url = "http://owner:4321/people/" + company.getOwnerId();
                System.out.println(company.getOwnerId());
		URL obj = new URL(url);
		con = (HttpURLConnection) obj.openConnection();
                //con.
		con.setRequestMethod("PUT");
		String requestString = " { \"name\": " + "\""+jsonTemp.ownerName +"\""+  ", ";
                requestString += "\"surname\": " + "\"" +jsonTemp.ownerSurname+ "\"" + ", "; 
                requestString += "\"gender\": " + "\"" + jsonTemp.ownerGender + "\"" +", ";
                requestString += "\"address\": " + "\""+ jsonTemp.ownerAddress +"\""+ "}";
                  con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(requestString);
		wr.flush();
		wr.close();
            //int responseCode = con.getResponseCode();
            BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response2 = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response2.append(inputLine);
		}
		in.close();
                
                return "Company with ID: " + id + "  successfully updated!";  
            }
        } catch(Exception e){
            return e.getMessage();
        }
        
    } 
     public static Object patchCompany(Request request, Response response, CompaniesEditor compEdit) throws Exception{
        int id = Integer.valueOf(request.params("id"));
        try{ 
        Company company = fromJson(request.body(), Company.class);
        if(isEmpty(request.body())){
            response.status(400);
            throw new Exception("400. Empty body");
        }
            if(compEdit.get(id) != null){
                 if(company.getName() == null && company.getCity() == null && company.getPhoneNumber() == 0){
                     response.status(400); 
                     throw new Exception("There is no required field in ur json");
                  }
                if(company.getName() == null){
                    company.setName(compEdit.get(id).getName());
                }
                 if(company.getCity() == null){
                    company.setCity(compEdit.get(id).getCity());
                }
                  if(company.getPhoneNumber() == 0){
                    company.setPhoneNumber(compEdit.get(id).getPhoneNumber());
                }
                compEdit.update(id, company);
                  
                
                 return "Company with ID: " + id + "  successfully updated!";  
            }
            else{
                response.status(404);
                throw new Exception("404. There is no company with id: " + id + "!");
            }    
        }
        catch(Exception e){
            if(response.status() == 404){
                return e.getMessage();
            }
            response.status(400);
            return e.getMessage();
        }
    } 
    
     public static <T extends Object> T  fromJson(String json, Class<T> classe) {
       Gson gson = new Gson();
       gson.fromJson(json, classe);
        return gson.fromJson(json, classe);
         
    }
     public static String toJson(Object value){
         
            Gson gson = new Gson();
           String json = gson.toJson(value);
           return json; 
        
        
         
     }
     public static boolean isAlpha(String name) {
         return name.matches("[a-zA-Z]+");
    }   
        static class  Adr{
         public  String ownerName;
         public String ownerGender;
         public String ownerAddress;
         
}
     static class FinalJson{
        int id;
        String name;
        String city;
        int phoneNumber;
        String ownerName;
        String ownerSurname;
        String ownerGender;
        String ownerAddress;
     }
}
