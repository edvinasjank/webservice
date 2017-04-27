
import com.google.gson.Gson;
import static spark.Spark.*;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author edvinas
 */
public class Companies {
     public static void main(String[] args) {
           port(80);
           
         CompaniesEditor compEdit = new CompaniesEditor();
          
         
         path("/companies", () -> {
            get("", (request, response) -> {
                    return toJson(CompanyController.getAllCompanies(request, response, compEdit));
                } );
            get("/:id", (request, response) -> {
                    return toJson(CompanyController.getCompanyById(request, response, compEdit));
                } );
            get("/city/:city", (request, response) -> {
                    return toJson(CompanyController.getCompaniesByCity(request, response, compEdit));
                } );
            get("/name/:name", (request, response) -> {
                    return toJson(CompanyController.getCompaniesByName(request, response, compEdit));
                } );
            get("/cityCount/:city", (request, response) -> {
                    return toJson(CompanyController.getCountByCity(request, response, compEdit));
                } );
            get("/companyCount/:name", (request, response) -> {
                    return toJson(CompanyController.getCountByCompany(request, response, compEdit));
                } );
            delete("/:id", (request, response) -> {
                    return toJson(CompanyController.deleteById(request, response, compEdit));
                } );
            post("", (request, response) -> {
                    return toJson(CompanyController.createCompany(request, response, compEdit));
                } );
            put("/:id", (request, response) -> {
                    return toJson(CompanyController.updateCompany(request, response, compEdit));
                } );
            patch("/:id", (request, response) -> {
                    return toJson(CompanyController.patchCompany(request, response, compEdit));
                } );
 
        }); 
         after((request, response) -> response.type("application/json")); 
     }
     public static String toJson(Object value){
         Gson gson = new Gson();
        String json = gson.toJson(value);
        return json;
     }
}
