
import com.google.gson.Gson;
import java.util.HashMap;
import java.util.Map;
import spark.Request;
import spark.Response;

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
               return company;
           }
           else{
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
                throw new Exception("404. There is no company with id: " + id + "!");
            }    
        }
        catch(Exception e){
            return e.getMessage();
        }
    }
     public static Object createCompany(Request request, Response response, CompaniesEditor compEdit){
        Company company = fromJson(request.body(), Company.class);
        compEdit.create(company);
        return "OK";

    }
    public static Object updateCompany(Request request, Response response, CompaniesEditor compEdit){
        int id = Integer.valueOf(request.params("id"));
        Company company = fromJson(request.body(), Company.class);
        try{ 
            if(compEdit.get(id) != null){
                compEdit.update(id, company);
                 return "Company with ID: " + id + "  successfully updated!";  
            }
            else{
                throw new Exception("404. There is no company with id: " + id + "!");
            }    
        }
        catch(Exception e){
            return e.getMessage();
        }
    } 
    
     public static <T extends Object> T  fromJson(String json, Class<T> classe) {
       Gson gson = new Gson();
        return gson.fromJson(json, classe);
    }
}
