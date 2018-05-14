package Panels;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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

public class EditRecipe extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Recipe recipe = new Recipe();
	private JLabel title;
	private ArrayList<Ingredient> ingredientsList = new ArrayList<Ingredient>();
	private String[] units = { "Kg", "g", "L", "ml", "Cup", "Table spoon", "Tea spoon", "Unit", "Pieces"};
	private int state;

	public EditRecipe(Connections conn, String recipeName) {

		this.recipe = conn.getRecipe(recipeName);
		this.ingredientsList = conn.getIngredients(recipeName);
		conn.deleteRecipe(recipeName);
		this.state = 0;
		title = new JLabel("Edit Recipe");

		title.setHorizontalAlignment(JTextField.CENTER);

		setSize(new Dimension(400, 400));
		setLocationRelativeTo(null);
		setTitle("My Cookbook"); 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		addWindowListener(new WindowAdapter()
		{
			@Override
			public void windowClosing(WindowEvent e)
			{
				super.windowClosing(e);
				conn.close();
			}
		});

		/*
		 * Data panel is the center panel.
		 */
		JPanel recipeNamePanel = recipeNamePanel();

		/*
		 * Ingredients list on scroll panel.
		 */	
		JPanel ingredientsListPanel = ingredientListPanel();
		JScrollPane scrollIngPane = new JScrollPane(ingredientsListPanel);
		scrollIngPane.setVisible(true);

		/*
		 * Buttons for add and delete ingredients from the list.
		 */
		JPanel dataIngPanel = dataIngPanel(ingredientsListPanel);

		/*
		 * Ingredients Panel.
		 */
		JPanel ingredientsPanel = new JPanel(new GridLayout(2, 1));
		ingredientsPanel.add(scrollIngPane);
		ingredientsPanel.add(dataIngPanel);

		/*
		 * Preparation Panel.
		 */
		JPanel preparationPanel = preparationPanel();

		/*
		 * Buttons panel is where you find the buttons.
		 */
		JPanel buttonsPanel = buttonsPanel(conn, recipeNamePanel, ingredientsPanel, preparationPanel);


		/*Principal Panel*/
		setLayout(new BorderLayout());
		add(title, BorderLayout.NORTH);
		add(recipeNamePanel, BorderLayout.CENTER);
		add(buttonsPanel, BorderLayout.SOUTH);
	}

	private JPanel recipeNamePanel() {
		JLabel recipeNameLabel = new JLabel("Recipe name:");
		JTextField recipeNameText = new JTextField(recipe.getName(), 5);

		JPanel recipeNamePanel = new JPanel(new GridLayout(1, 2));
		recipeNamePanel.add(recipeNameLabel);
		recipeNamePanel.add(recipeNameText);

		return recipeNamePanel;
	}
	private JPanel ingredientListPanel() {
		JTextArea ingredientsTextArea = new JTextArea();
		ingredientsTextArea.setEditable(false);
		ingredientsTextArea.setLineWrap(true);
		ingredientsTextArea.setWrapStyleWord(true);

		String ingredients = "";
		for (int i = 0; i < ingredientsList.size(); i++) {
			ingredients += ((i + 1) + ") " + ingredientsList.get(i).getName() + " " +
					ingredientsList.get(i).getQuantity() + " " +
					ingredientsList.get(i).getUnits());
			ingredients += '\n';
		}
		ingredientsTextArea.setText(ingredients);

		JPanel ingredientsListPanel = new JPanel(new BorderLayout());
		ingredientsListPanel.add(ingredientsTextArea, BorderLayout.CENTER);

		return ingredientsListPanel;
	}
	private JPanel dataIngPanel(JPanel ingredientListPanel) {		
		JLabel empty1 = new JLabel("");
		JLabel empty2 = new JLabel("");
		JLabel empty3 = new JLabel("");
		JLabel empty4 = new JLabel("");
		JLabel empty5 = new JLabel("");
		JLabel quantityLabel = new JLabel("Quantity");
		JLabel unitsLabel = new JLabel("Units");
		JLabel ingredientLabel = new JLabel("Ingredient:");
		JLabel delIngredientLabel = new JLabel("Delete nº:");
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
		dataIngPanel.add(empty4);
		dataIngPanel.add(empty5);
		dataIngPanel.add(delIngredientLabel);
		dataIngPanel.add(delIngText);
		dataIngPanel.add(delIng);

		quantityText.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char input = e.getKeyChar();
				if (input != '.' && ((input < '0' || input > '9') && input != '\b')) {
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

		// Add button
		addIng.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (ingNameText.getText().equals("") || quantityText.getText().equals(""))
					JOptionPane.showMessageDialog(null, "Please enter a name and a quantity for the ingredient.");

				else if (!correctQuantity(quantityText.getText())) {
					JOptionPane.showMessageDialog(null, "ERROR 003: Invalid quantity.");
				}

				else {
					Ingredient ing = new Ingredient(ingNameText.getText(), 
							Double.parseDouble((quantityText.getText())), 
							unitsCB.getItemAt(unitsCB.getSelectedIndex()), recipe.getName());

					if (!ingredientsList.contains(ing)) {
						ingredientsList.add(ing);
						String ingredients = "";
						for (int i = 0; i < ingredientsList.size(); i++) {
							ingredients += ((i + 1) + ") " + ingredientsList.get(i).getName() + " " +
									ingredientsList.get(i).getQuantity() + " " +
									ingredientsList.get(i).getUnits());
							ingredients += '\n';
						}

						JTextArea ingredientsTextArea = (JTextArea) ingredientListPanel.getComponent(0);
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

						JTextArea ingredientsTextArea = (JTextArea) ingredientListPanel.getComponent(0);
						ingredientsTextArea.setText(ingredients);
						delIngText.setText("");
					}
				}
			}
		});

		return dataIngPanel;
	}
	private JPanel preparationPanel() {
		JLabel prepaLabel = new JLabel("Preparation:");
		JTextArea prepaText = new JTextArea();

		prepaText.setText(recipe.getPreparation());

		JPanel preparationPanel = new JPanel(new GridLayout(2, 1));
		preparationPanel.add(prepaLabel);
		preparationPanel.add(prepaText);

		return preparationPanel;
	}
	private JPanel buttonsPanel(Connections conn, JPanel recipeNamePanel, JPanel ingredientsPanel, JPanel preparationPanel) {
		JButton next = new JButton("Next");
		JButton back = new JButton("Back");
		JButton save = new JButton("Save");

		JPanel buttonsPanel = new JPanel(new GridLayout(1, 2));
		buttonsPanel.add(back);
		buttonsPanel.add(next);


		// Next button
		next.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JTextField recipeNameText = (JTextField) recipeNamePanel.getComponent(1);
				// TODO Auto-generated method stub
				if (state == 0) {

					if (recipeNameText.getText().equals(""))
						JOptionPane.showMessageDialog(null, "Please enter a name for the recipe.");

					else if (!conn.recipeExists(recipeNameText.getText())) {
						recipe.setName(recipeNameText.getText());
						for (int i = 0; i < ingredientsList.size(); i++) {
							ingredientsList.get(i).setRecipeName(recipe.getName());
						}
						recipeNamePanel.setVisible(false);
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
					for (int i = 0; i < ingredientsList.size(); i++) {
						conn.insertIngredient(ingredientsList.get(i));
					}
					conn.insertRecipe(recipe);
					JOptionPane.showMessageDialog(null, recipe.getName() + " saved.");
					state = 0;
					ShowRecipe sr = new ShowRecipe(conn, recipe.getName());
					sr.setVisible(true);
					dispose();
				}

				else if (state == 1) {
					ingredientsPanel.setVisible(false);
					add(recipeNamePanel, BorderLayout.CENTER);
					recipeNamePanel.setVisible(true);
					state = 0;
				}
			}
		});

		// Save button
		save.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JTextArea prepaText = (JTextArea) preparationPanel.getComponent(1);
				if (prepaText.getText().equals(""))
					JOptionPane.showMessageDialog(null, "Please enter the preparation for the recipe.");
				else {
					for (int i = 0; i < ingredientsList.size(); i++) {
						conn.insertIngredient(ingredientsList.get(i));
					}
					recipe.setPreparation(prepaText.getText());
					conn.insertRecipe(recipe);
					JOptionPane.showMessageDialog(null, recipe.getName() + " saved.");
					state = 0;
					Menu m = new Menu(conn);
					m.setVisible(true);
					dispose();
				}
			}
		});

		return buttonsPanel;
	}
	private boolean correctQuantity(String quantity) {
		char characters[] = quantity.toCharArray();
		int count = 1;
		if (characters[0] == '.')
			return false;
		for (int i = 0; i < characters.length; i++) {
			if (characters[i] == '.')
				count--;
			if (count == -1)
				return false;
		}

		return true;
	}

}
