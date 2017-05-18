import com.google.gson.Gson;
import static spark.Spark.*;
public class Controller {
    public static void main(String [ ] args){
        TaskManager task  = new TaskManager();
        port(888);
         path("", () -> {
            post("/login", (request, response) -> {
                    return toJson(task.loginHuman(request, response));
                } );
          
            post("/register", (request, response) -> {
                    return toJson(task.registerHuman(request, response));
                } ); 
           
            get("/companies", (request, response) -> {
                    return task.getAllCompanies(request,response);
                } );
           get("/companies/:id", (request, response) -> {
                    return task.getCompanyById(request,response);
                } );
           
            get("/companies/city/:city", (request, response) -> {
                    return task.getCompaniesByCity(request,response);
                } );
            
            get("/companies/name/:name", (request, response) -> {
                    return task.getCompaniesByName(request,response);
                } );
            
            get("/companies/cityCount/:city", (request, response) -> {
                    return task.getCountByCity(request,response);
                } );
            get("/companies/companyCount/:name", (request, response) -> {
                    return task.getCountByName(request,response);
                } );
            
            delete("/companies/:id", (request, response) -> {
                    return task.deleteById(request, response);
                } );
            
            post("/companies", (request, response) -> {
                    return task.createCompany(request, response);
                } );
            
            put("/companies/:id", (request, response) -> {
                    return task.updateCompany(request, response);
                } );
            
            patch("/companies/:id", (request, response) -> {
                    return task.patchCompany(request, response);
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
