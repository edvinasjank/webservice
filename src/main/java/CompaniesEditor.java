
import com.google.gson.Gson;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Iterator;
import java.util.TreeMap;
import java.util.stream.Collectors;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author edvinas
 */
public class CompaniesEditor {
   public Map<Integer, Company> companies = new HashMap();
   Map<Integer, Company> treeMap;
   public static int total = 6;

    
    public CompaniesEditor(){
        Company temp = new Company(1,"Maxima", "Vilnius", 86511234);
        companies.put(1,temp);
        temp = new Company(2,"LemonGym", "Vilnius", 11111111);
        companies.put(2,temp);
        temp = new Company(3,"Urmas", "Kaunas", 86515150);
        companies.put(3,temp);
        temp = new Company(4,"Lifosa", "Kedainiai", 94646434);
        companies.put(4,temp);
        temp = new Company(5,"Maxima", "Kedainiai", 9686868);
        companies.put(5,temp);
        temp = new Company(6,"Maxima", "Balbieriskis", 94646768);
        companies.put(6,temp);
        //System.out.println(toJson(companies));
    }
    public String toJson(Map<Integer,Company> companies){
        Gson gson = new Gson();
        String json = gson.toJson(companies);
        return json;
    }
    public Company get(int id){
        return this.companies.get(id);
    }
    public List<Company> getAll(){
       
       return new TreeMap<Integer, Company>(companies).entrySet().stream().map(Map.Entry::getValue).collect(Collectors.toList());
    }
    public void delete(int id){
        this.companies.remove(id);
    }
    public void create(Company company){
        
       total++;
       company.setId(total);
       this.companies.put(total, company);
    }
    public void update(int id, Company company){
        company.setId(id);
        this.companies.replace(id, company);
    }
    public Map<Integer, Company> getByName(String name){
        Map<Integer, Company> returnValue = new HashMap();
        int temp;
        Iterator it = companies.entrySet().iterator();
        while (it.hasNext()) {
                Map.Entry pair = (Map.Entry)it.next();
                Company temp1 ;
                temp1 = (Company)pair.getValue();
                temp = (Integer)pair.getKey();
                if(temp1.getName().equals(name)){
                    System.out.println("PATEKAU");
                    returnValue.put(temp,temp1);
                }
        }  
        return returnValue;
    }
     public Map<Integer, Company> getByCity(String city){
        Map<Integer, Company> returnValue = new HashMap();
        int temp;
        Iterator it = companies.entrySet().iterator();
         while (it.hasNext()) {
                Map.Entry pair = (Map.Entry)it.next();
                Company temp1 ;
                temp1 = (Company)pair.getValue();
                temp = (Integer)pair.getKey();
                if(temp1.getCity().equals(city)){
                    returnValue.put(temp,temp1);
                }
            }
        return returnValue;
    }
     public int cityCount(String city){
         int count = 0;
        Iterator it = companies.entrySet().iterator();
         while (it.hasNext()) {
                Map.Entry pair = (Map.Entry)it.next();
                Company temp1 ;
                temp1 = (Company)pair.getValue();
                if(temp1.getCity().equals(city)){
                     count++;
                }
            }
         return count;
     }
     public int companyCount(String name){
         int count = 0;
        Iterator it = companies.entrySet().iterator();
         while (it.hasNext()) {
                Map.Entry pair = (Map.Entry)it.next();
                Company temp1 ;
                temp1 = (Company)pair.getValue();
                if(temp1.getName().equals(name)){
                     count++;
                }
            }
         return count;
     }
 
}
