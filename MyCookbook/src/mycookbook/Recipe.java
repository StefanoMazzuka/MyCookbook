/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mycookbook;

/**
 *
 * @author McPuto
 */
public class Recipe {

    private String name;
    private String preparation;
    
    public Recipe(String name, String preparation) {
        this.name = name;
        this.preparation = preparation;
    }
    
    public String getName() {
        return name;
    }

    public String getPreparation() {
        return preparation;
    }  
}
