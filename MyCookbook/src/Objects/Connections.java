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
			File.separator + "MyCookbookDB.sqlite";
	private Connection connec;
	private PreparedStatement st;
	private ResultSet list;

	public boolean connect() {
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		try {
			connec = DriverManager.getConnection("jdbc:sqlite:" + url);
			if (connec != null) {
				JOptionPane.showMessageDialog(null, "Connection Success.");
				return true;
			}
		} catch (Exception  e) {
			JOptionPane.showMessageDialog(null, "ERROR 001: Connection Fails.");
			Logger.getLogger(Connections.class.getName()).log(Level.SEVERE, null, e);
			return false;
		}
		return false;
	}

	public void insertRecipe(Recipe recipe) {
		try {
			st = connec.prepareStatement("INSERT INTO Recipes VALUES('" 
					+ recipe.getName() + "', '" 
					+ recipe.getPreparation() + "')");
			st.executeUpdate();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e);
			Logger.getLogger(Connections.class.getName()).log(Level.SEVERE, null, e);
		}
	}

	public boolean recipeExists(String recipeName) {
		try {
			st = connec.prepareStatement("SELECT * FROM Recipes WHERE name = '" + recipeName + "'");
			list = st.executeQuery();
			if (list.next()) {
				JOptionPane.showMessageDialog(null, "ERROR 002: The recipe alrady exists.");
				return true;
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e);
			Logger.getLogger(Connections.class.getName()).log(Level.SEVERE, null, e);
			return false;
		}
		return false;
	}

	public ArrayList<Recipe> getRecipes() {
		ArrayList<Recipe> recipeList = new ArrayList<>();
		try {
			st = connec.prepareStatement("SELECT * FROM Recipes");
			list = st.executeQuery();
			Recipe recipe;
			while (list.next()) {
				recipe = new Recipe();
				recipe.setName(list.getString("name"));
				recipe.setPreparation(list.getString("preparation"));
				recipeList.add(recipe); 
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e);
			Logger.getLogger(Connections.class.getName()).log(Level.SEVERE, null, e);
		}
		return recipeList;
	}

	public Recipe getRecipe(String recipeName) {
		Recipe recipe = new Recipe();
		try {
			st = connec.prepareStatement("SELECT * FROM Recipes WHERE name = '" + recipeName + "'");
			list = st.executeQuery();
			while (list.next()) {
				recipe.setName(list.getString("name"));
				recipe.setPreparation(list.getString("preparation"));
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e);
			Logger.getLogger(Connections.class.getName()).log(Level.SEVERE, null, e);
		}
		return recipe;
	}
	
	public void deleteRecipe(String recipeName) {
		try {
			st = connec.prepareStatement("DELETE FROM Recipes WHERE name = '" + recipeName + "'");
			st.executeUpdate();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
		try {
			st = connec.prepareStatement("DELETE FROM Ingredients WHERE recipeName = '" + recipeName + "'");
			st.executeUpdate();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
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
			JOptionPane.showMessageDialog(null, e);
			Logger.getLogger(Connections.class.getName()).log(Level.SEVERE, null, e);
		}
	}

	public ArrayList<Ingredient> getIngredients(String recipeName) {
		ArrayList<Ingredient> ingredientList = new ArrayList<>();
		try {
			st = connec.prepareStatement("SELECT * FROM Ingredients WHERE recipeName = '" + recipeName + "'");
			list = st.executeQuery();
			Ingredient ingredient;
			while (list.next()) {
				ingredient = new Ingredient(list.getString("name"), list.getInt("quantity"), list.getString("units"), list.getString("recipeName"));
				ingredientList.add(ingredient);
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e);
			Logger.getLogger(Connections.class.getName()).log(Level.SEVERE, null, e);
		}
		return ingredientList;
	}

	public void deleteIngredient(int idIngredient) {
		try {
			st = connec.prepareStatement("DETELE FROM ingredient WHERE idIngredient = " + idIngredient);
			st.executeUpdate();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}

	public String getPreparation(String recipeName) {
		String preparation = "";
		try {
			st = connec.prepareStatement("SELECT preparation FROM recipes WHERE name = '" + recipeName + "'");
			list = st.executeQuery();
			preparation = list.getString("preparation");
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e);
			Logger.getLogger(Connections.class.getName()).log(Level.SEVERE, null, e);
		}
		return preparation;
	}
	
	public void close() {
		try {
			connec.close();
			JOptionPane.showMessageDialog(null, "Disconnection Success.");
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e);
			Logger.getLogger(Connections.class.getName()).log(Level.SEVERE, null, e);
		}
	}
}
