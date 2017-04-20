
import com.google.gson.Gson;
import java.util.HashMap;
import java.util.Map;
import spark.Request;
import spark.Response;
import static spark.utils.StringUtils.isEmpty;

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
        try{
           int id = Integer.valueOf(request.params("id"));
           Company company = compEdit.get(id);
           if(company != null){
               System.out.println("if");
         //      throw new Exception("pries return404. Failed to find company by id: " + id + "!");
               return company;
           }
           else{
               System.out.println("else");
              response.status(404);
              System.out.println("sss");
               throw new Exception("404. Failed to find company by id: " + id + "!");
           }

        }
       catch(Exception e){
           return e.getMessage();
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
        try{
            if(compEdit.get(id) != null){
                compEdit.delete(id);
                 return "Company with ID: " + id + "  successfully deleted!";  
            }
            else{
                response.status(404);
                throw new Exception("404. There is no company with id: " + id + "!");
            }    
        }
        catch(Exception e){
            return e.getMessage();
        }
    }
     public static Object createCompany(Request request, Response response, CompaniesEditor compEdit) {
        System.out.println( request.body());
        int id = Integer.valueOf(request.params("id"));
        try{     
        int numberCount = 0;
       
           if(compEdit.companies.containsKey(id)){
                 response.status(400);
                throw new Exception("The company already exist with id: " + id + "!");
            }
       }catch(Exception e){
            return e.getMessage();
        }
        try{
            String temp = request.body();
            if("".equals(temp)){
                response.status(400);
                throw new Exception("400. Invalid input");
            }
        Company company = fromJson(request.body(), Company.class);
        compEdit.create(company,id);
                return company; 
        }
       catch(Exception e){
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

    }
    public static Object updateCompany(Request request, Response response, CompaniesEditor compEdit) throws Exception{
        int id = Integer.valueOf(request.params("id"));
        Company company = fromJson(request.body(), Company.class);
        System.out.println(company);
        System.out.println(company.getName());
        try{
            if(isEmpty(request.body())){
                response.status(400);
                throw new Exception("400. Empty body");
            }
        } catch(Exception e){
            return e.getMessage();
        }
        try{ 
            if(compEdit.get(id) != null){
                compEdit.update(id, company);
                 return "Company with ID: " + id + "  successfully updated!";  
            }
            else{
                response.status(404);
                throw new Exception("404. There is no company with id: " + id + "!");
            }    
        }
        catch(Exception e){
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
                 if(company.getName() == null || company.getCity() == null || company.getPhoneNumber() == 0){
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
                 
                  else{
                       compEdit.update(id, company);
                  }
                
                 return "Company with ID: " + id + "  successfully updated!";  
            }
            else{
                response.status(404);
                throw new Exception("404. There is no company with id: " + id + "!");
            }    
        }
        catch(Exception e){
          
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
}
