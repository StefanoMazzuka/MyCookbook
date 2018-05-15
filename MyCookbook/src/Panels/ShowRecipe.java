package Panels;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextArea;

import Objects.Connections;
import Objects.Ingredient;

public class ShowRecipe {

	JPanel showRecipe;
	
	public ShowRecipe(Connections conn, String recipeName) {
		
		this.showRecipe = new JPanel();
		this.showRecipe.setPreferredSize(new Dimension(400, 400));
		
		JLabel title = new JLabel(recipeName);

		title.setHorizontalAlignment(JTextField.CENTER);

		/*
		 * Load ingredients
		 */
		JPanel ingredientsList = loadIngredients(conn, recipeName);
		JScrollPane scrollPane = new JScrollPane(ingredientsList,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
	            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);	
		/*
		 * Panel Principal
		 */
		showRecipe.setLayout(new BorderLayout());
		
		showRecipe.add(title, BorderLayout.NORTH);
		showRecipe.add(scrollPane, BorderLayout.CENTER);
	}
	
	public JPanel loadIngredients(Connections conn, String recipeName) {	
		ArrayList<Ingredient> ingredientsList = conn.getIngredients(recipeName);
		String ingredients = "INGREDIENTS:" + '\n';
		for (int i = 0; i < ingredientsList.size(); i++) {
			ingredients += ((i + 1) + ") " + ingredientsList.get(i).getName() + " " +
					ingredientsList.get(i).getQuantity() + " " +
					ingredientsList.get(i).getUnits());
			ingredients += '\n';
		}
		
		ingredients += '\n' + "PREPARATION:" + '\n' + conn.getPreparation(recipeName);
		
		JTextArea ingredientsTextArea = new JTextArea();
		ingredientsTextArea.setEditable(false);
		ingredientsTextArea.setText(ingredients);
		ingredientsTextArea.setLineWrap(true);
		ingredientsTextArea.setWrapStyleWord(true);
		
		JPanel ingredientsListPanel = new JPanel(new BorderLayout());
		ingredientsListPanel.add(ingredientsTextArea, BorderLayout.CENTER);
		
		return ingredientsListPanel;
	}
	public JPanel getShowRecipe() {
		return this.showRecipe;
	}
}
