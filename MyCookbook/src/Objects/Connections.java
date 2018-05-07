/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Objects;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

/**
 *
 * @author McPuto
 */
public class Connections {
    private final String url = 
    		System.getProperty("user.dir") + 
    		File.separator + "SQLite" + 
    		File.separator + "MyCookbookDB.sqlite";
    private Connection connec;
    private PreparedStatement st;
    private ResultSet list;
    
    public void connect() {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        try {
            connec = DriverManager.getConnection("jdbc:sqlite:" + url);
            if (connec != null) System.out.println("Connection Success");
        } catch (Exception  e) {
            Logger.getLogger(Connections.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    public void insertRecipe(Recipe recipe) {
        try {
            st = connec.prepareStatement("INSERT INTO Recipes VALUES('" 
                    + recipe.getName() + "', '" 
                    + recipe.getPreparation() + "')");
            st.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(Connections.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    public boolean recipeExists(String recipeName) {
        boolean exists = false;
    	try {
            st = connec.prepareStatement("SELECT * FROM Recipes Where name = '" +
            		recipeName + "'");
            list = st.executeQuery();
            if (list.next())
            	exists = true;
            
        } catch (SQLException e) {
             Logger.getLogger(Connections.class.getName()).log(Level.SEVERE, null, e);
        }
        return exists;
    }
    
    public ArrayList<Recipe> getRecipes() {
    ArrayList<Recipe> recipeList = new ArrayList<>();
        try {
            st = connec.prepareStatement("SELECT * FROM recipes");
            list = st.executeQuery();
            Recipe recipe;
            while (list.next()) {
                recipe = new Recipe();
                recipe.setName(list.getString("name"));
                recipe.setPreparation(list.getString("preparation"));
                recipeList.add(recipe);
            }
        } catch (SQLException e) {
             Logger.getLogger(Connections.class.getName()).log(Level.SEVERE, null, e);
        }
        return recipeList;
    }
    
    public void deleteRecipe(int idRecipe) {
        try {
            st = connec.prepareStatement("DETELE FROM recipes WHERE idRecipe = " + idRecipe);
            st.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    public void insertIngredient(Ingredient ingredient) {
        try {
            st = connec.prepareStatement("INSERT INTO Ingredients VALUES('" 
                    + ingredient.getName() + "', '" 
                    + ingredient.getQuantity() + "', '" 
                    + ingredient.getUnits()+ "', '" 
                    + ingredient.getRecipeName()+ "')");
            st.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(Connections.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    public ArrayList<Ingredient> getIngredient() {
    ArrayList<Ingredient> ingredientList = new ArrayList<>();
        try {
            st = connec.prepareStatement("SELECT * FROM ingredients");
            list = st.executeQuery();
            Ingredient ingredient;
            while (list.next()) {
                ingredient = new Ingredient(list.getString("name"), list.getInt("quantity"), list.getString("units"), list.getString("recipeName"));
                ingredientList.add(ingredient);
            }
        } catch (SQLException e) {
             Logger.getLogger(Connections.class.getName()).log(Level.SEVERE, null, e);
        }
        return ingredientList;
    }
    
    public void deleteIngredient(int idIngredient) {
        try {
            st = connec.prepareStatement("DETELE FROM ingredient WHERE idIngredient = " + idIngredient);
            st.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    public void cerrar() {
        try {
            connec.close();
        } catch (SQLException e) {
            Logger.getLogger(Connections.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
