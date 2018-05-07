package Panels;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import Objects.Connections;
import Objects.Ingredient;
import Objects.Recipe;

public class NewRecipe extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Recipe recipe = new Recipe();
	private ArrayList<Ingredient> ingredientsList = new ArrayList<Ingredient>();
	private String[] units = { "Kg", "g", "L", "ml", "Cup", "Table spoon", "Tea spoon", "Unit", "Pieces"};
	private int state;

	public NewRecipe(Connections conn) {
		JLabel title = new JLabel("New Recipe");
		JLabel recipeNameLabel = new JLabel("Enter recipe name:");
		JTextField recipeNameText = new JTextField("", 5);
		JButton next = new JButton("Next");
		JButton back = new JButton("Back");
		JButton save = new JButton("Save");
		this.state = 0;

		title.setHorizontalAlignment(JTextField.CENTER);

		setSize(new Dimension(400, 400));
		setLocationRelativeTo(null); 
		setTitle("My Cookbook"); 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel dataPanel = new JPanel(new GridLayout(1, 2));
		dataPanel.add(recipeNameLabel);
		dataPanel.add(recipeNameText);

		JPanel buttonsPanel = new JPanel(new GridLayout(1, 2));
		buttonsPanel.add(back);
		buttonsPanel.add(next);

		/*Panel 2*/
		JTextArea ingredientsTextArea = new JTextArea();
		ingredientsTextArea.setEditable(false);
		ingredientsTextArea.setLineWrap(true);
		ingredientsTextArea.setWrapStyleWord(true);
		JPanel ingredientsJPanel = new JPanel(new BorderLayout());
		ingredientsJPanel.add(ingredientsTextArea, BorderLayout.CENTER);
		JScrollPane scrollPane = new JScrollPane(ingredientsJPanel);
		scrollPane.setVisible(true);
		JLabel empty1 = new JLabel("");
		JLabel empty2 = new JLabel("");
		JLabel empty3 = new JLabel("");
		JLabel empty4 = new JLabel("");
		JLabel empty5 = new JLabel("");
		JLabel quantityLabel = new JLabel("Quantity");
		JLabel unitsLabel = new JLabel("Units");
		JLabel ingredientLabel = new JLabel("Ingredient:");
		JLabel delIngredientLabel = new JLabel("Delete ingredient nº:");
		JTextField ingNameText = new JTextField("", 5);
		JTextField quantityText = new JTextField("", 5);
		JTextField delIngText = new JTextField("", 5);
		JComboBox<String> unitsCB = new JComboBox<String>(units);
		JButton addIng = new JButton("Add");
		JButton delIng = new JButton("Delete");
		JPanel dataIngPanel = new JPanel(new GridLayout(3, 5));
		dataIngPanel.add(empty1);
		dataIngPanel.add(empty2);
		dataIngPanel.add(quantityLabel);
		dataIngPanel.add(unitsLabel);
		dataIngPanel.add(empty3);
		dataIngPanel.add(ingredientLabel);
		dataIngPanel.add(ingNameText);
		dataIngPanel.add(quantityText);
		dataIngPanel.add(unitsCB);
		dataIngPanel.add(addIng);
		dataIngPanel.add(delIngredientLabel);
		dataIngPanel.add(delIngText);
		dataIngPanel.add(delIng);
		dataIngPanel.add(empty4);
		dataIngPanel.add(empty5);

		quantityText.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char input = e.getKeyChar();
				if ((input < '0' || input > '9') && input != '\b') {
					e.consume();
				}
			}
		});

		delIngText.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char input = e.getKeyChar();
				if ((input < '0' || input > '9') && input != '\b') {
					e.consume();
				}
			}
		});

		JPanel ingredientsPanel = new JPanel(new GridLayout(2, 1));
		ingredientsPanel.add(scrollPane);
		ingredientsPanel.add(dataIngPanel);

		/*Panel 3*/
		JPanel preparationPanel = new JPanel(new GridLayout(2, 1));
		JLabel prepaLabel = new JLabel("Preparation:");
		JTextArea prepaText = new JTextArea();
		preparationPanel.add(prepaLabel);
		preparationPanel.add(prepaText);

		/*Panel Principal*/
		setLayout(new BorderLayout());
		add(title, BorderLayout.NORTH);
		add(dataPanel, BorderLayout.CENTER);
		add(buttonsPanel, BorderLayout.SOUTH);

		//pack();

		// Next button
		next.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (state == 0) {
					if (recipeNameText.getText().equals(""))
						JOptionPane.showMessageDialog(null, "Please enter a name for the recipe.");
					else if (conn.recipeExists(recipeNameText.getText())) {
						JOptionPane.showMessageDialog(null, "The recipe alrady exists.");
					}
					else {
						recipe.setName(recipeNameText.getText());
						dataPanel.setVisible(false);
						title.setText(recipeNameText.getText());
						add(ingredientsPanel, BorderLayout.CENTER);
						ingredientsPanel.setVisible(true);
						state = 1;
					}	
				}
				else if (state == 1) {
					if (ingredientsList.isEmpty())
						JOptionPane.showMessageDialog(null, "Please enter some ingredients.");
					else {
						ingredientsPanel.setVisible(false);
						title.setText(recipeNameText.getText());
						add(preparationPanel, BorderLayout.CENTER);
						preparationPanel.setVisible(true);
						buttonsPanel.setVisible(false);
						add(save, BorderLayout.SOUTH);
					}
				}
			}
		});

		// Back button
		back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (state == 0) {
					Menu m = new Menu();
					m.setVisible(true);
					setVisible(false);
				}
				else if (state == 1) {
					ingredientsPanel.setVisible(false);
					add(dataPanel, BorderLayout.CENTER);
					dataPanel.setVisible(true);
					state = 0;
				}
			}
		});

		// Add button
		addIng.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (ingNameText.getText().equals("") || quantityText.getText().equals(""))
					JOptionPane.showMessageDialog(null, "Please enter a name and a quantity for the ingredient.");

				else {
					Ingredient ing = new Ingredient(ingNameText.getText(), 
							Double.parseDouble((quantityText.getText())), 
							unitsCB.getItemAt(unitsCB.getSelectedIndex()), recipe.getName());

					if (!ingredientsList.contains(ing)) {
						ingredientsList.add(ing);
						String ingredients = "";
						for (int i = 0; i < ingredientsList.size(); i++) {
							ingredients += ((i + 1) + ". " + ingredientsList.get(i).getName() + " " +
									ingredientsList.get(i).getQuantity() + " " +
									ingredientsList.get(i).getUnits());
							ingredients += '\n';
						}

						ingredientsTextArea.setText(ingredients);
						ingNameText.setText("");
						quantityText.setText("");
					}
				}
			}
		});

		// Delete button
		delIng.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (delIngText.getText().equals(""))
					JOptionPane.showMessageDialog(null, "Please enter the ingredient number to remove.");
				else {
					if ((Integer.parseInt(delIngText.getText()) - 1) >= 0 && 
							(Integer.parseInt(delIngText.getText()) - 1) < ingredientsList.size()) {
						ingredientsList.remove(Integer.parseInt(delIngText.getText()) - 1);
						String ingredients = "";
						for (int i = 0; i < ingredientsList.size(); i++) {
							ingredients += ((i + 1) + ". " + ingredientsList.get(i).getName() + " " +
									ingredientsList.get(i).getQuantity() + " " +
									ingredientsList.get(i).getUnits());
							ingredients += '\n';
						}

						ingredientsTextArea.setText(ingredients);
						delIngText.setText("");
					}
				}
			}
		});
		
		// Save button
		save.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (prepaText.getText().equals(""))
					JOptionPane.showMessageDialog(null, "Please enter the preparation for the recipe.");
				else {
					for (int i = 0; i < ingredientsList.size(); i++) {
						conn.insertIngredient(ingredientsList.get(i));
					}
					recipe.setPreparation(prepaText.getText());
					conn.insertRecipe(recipe);
					JOptionPane.showMessageDialog(null, recipe.getName() + " save.");
					state = 0;
					Menu m = new Menu();
					m.setVisible(true);
					setVisible(false);
					conn.cerrar();
				}
			}
		});
	}
}
