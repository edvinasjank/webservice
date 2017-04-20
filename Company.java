
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
    public Company(int id, String name, String city, int phoneNumber){
        this.id = id;
        this.name = name;
        this.city = city;
        this.phoneNumber = phoneNumber;
    }
    public String getName(){
        return this.name;
    }
}

