
import lombok.Data;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author edvinas
 */
@Data
public class Company {
      private int id;
    private String name;
    private String city;
    private int phoneNumber;
    private int ownerId;
    public Company(int id, String name, String city, int phoneNumber, int ownerId){
        this.id = id;
        this.name = name;
        this.city = city;
        this.phoneNumber = phoneNumber;
        this.ownerId = ownerId;
    }
    Company(){
        
    }
    public String getName(){
        return this.name;
    }
}

