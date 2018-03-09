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
public class Ingredient {

    private String name;
    private int quantity;
    private int units;
    private int recipeId;
    
    Ingredient(String name, int quantity, int units, int recipeId) {
        this.name = name;
        this.quantity = quantity;
        this.units = units;
        this.recipeId = recipeId;
    }
    
    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getUnits() {
        return units;
    }
    
    public int getRecipeId() {
        return recipeId;
    }
}

