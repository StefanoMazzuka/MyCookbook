package Panels;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextArea;

import Objects.Connections;
import Objects.Ingredient;

public class ShowRecipe extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public ShowRecipe(Connections conn, String recipeName) {			

		JLabel title = new JLabel(recipeName);

		title.setHorizontalAlignment(JTextField.CENTER);

		setSize(new Dimension(400, 400));
		setLocationRelativeTo(null); 
		setTitle("My Cookbook"); 

		/*Load ingredients*/
		JPanel ingredientsList = loadIngredients(conn, recipeName);
		JScrollPane scrollPane = new JScrollPane(ingredientsList,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
	            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		/*Panel Principal*/
		setLayout(new BorderLayout());
		add(title, BorderLayout.NORTH);
		add(scrollPane, BorderLayout.CENTER);

		pack();
	}
	public JPanel loadIngredients(Connections conn, String recipeName) {	
		ArrayList<Ingredient> ingredientsList = conn.getIngredient(recipeName);
		String ingredients = "";
		for (int i = 0; i < ingredientsList.size(); i++) {
			ingredients += ((i + 1) + ") " + ingredientsList.get(i).getName() + " " +
					ingredientsList.get(i).getQuantity() + " " +
					ingredientsList.get(i).getUnits());
			ingredients += '\n';
		}
		
		ingredients += '\n' + "PREPARATION: " + '\n' + conn.getPreparation(recipeName);
		
		JTextArea ingredientsTextArea = new JTextArea();
		ingredientsTextArea.setEditable(false);
		ingredientsTextArea.setText(ingredients);
		ingredientsTextArea.setLineWrap(true);
		ingredientsTextArea.setWrapStyleWord(true);
		
		JPanel ingredientsListPanel = new JPanel(new BorderLayout());
		ingredientsListPanel.add(ingredientsTextArea, BorderLayout.CENTER);
		
		return ingredientsListPanel;
	}
}
