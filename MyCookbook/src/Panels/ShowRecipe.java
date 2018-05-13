package Panels;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
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

		/*Buttons panel*/
		JPanel buttonsPanel = new JPanel(new GridLayout(1, 2));
		JButton back = new JButton("Back");
		JButton edit = new JButton("Edit");
		buttonsPanel.add(back);
		buttonsPanel.add(edit);
		
		/*Panel Principal*/
		setLayout(new BorderLayout());
		
		add(title, BorderLayout.NORTH);
		add(scrollPane, BorderLayout.CENTER);
		add(buttonsPanel, BorderLayout.SOUTH);

		pack();
		
		back.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				MyRecipes mr = new MyRecipes(conn);
				mr.setVisible(true);
				dispose();
			}
		});
		
		edit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				EditRecipe er = new EditRecipe(conn);
				er.setVisible(true);
				setVisible(false);
			}
		});
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
