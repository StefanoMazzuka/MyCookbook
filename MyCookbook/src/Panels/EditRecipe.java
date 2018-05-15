package Panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.Icon;
import javax.swing.ImageIcon;
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
	private ArrayList<Ingredient> ingredientsList = new ArrayList<Ingredient>();
	private String[] units = { "Kg", "g", "L", "ml", "Cup", "Table spoon", "Tea spoon", "Unit", "Pieces"};
	private int state;

	public EditRecipe(Connections conn, String recipeName) {

		this.recipe = conn.getRecipe(recipeName);
		this.ingredientsList = conn.getIngredients(recipeName);
		conn.deleteRecipe(recipeName);
		this.state = 0;

		setResizable(false);
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
		 * Title panel.
		 */
		JLabel title = new JLabel("New Recipe");
		title.setHorizontalAlignment(JTextField.CENTER);
		JPanel titlePanel = new JPanel();
		titlePanel.setLayout(new BorderLayout());
		titlePanel.setPreferredSize(new Dimension(400, 60));
		titlePanel.add(title);

		/*
		 * Data panel is the center panel.
		 */
		JPanel recipeNamePanel = recipeNamePanel();

		/*
		 * Ingredients list on scroll panel.
		 */	
		JPanel ingredientsListPanel = ingredientListPanel();
		JScrollPane scrollIngPanel = new JScrollPane(ingredientsListPanel);
		scrollIngPanel.setPreferredSize(new Dimension(400, 165));

		/*
		 * Buttons for add and delete ingredients from the list.
		 */
		JPanel dataIngPanel = dataIngPanel(ingredientsListPanel);

		/*
		 * Ingredients Panel.
		 */
		JPanel ingredientsPanel = new JPanel();
		ingredientsPanel.add(scrollIngPanel);
		ingredientsPanel.add(dataIngPanel);

		/*
		 * Preparation Panel.
		 */
		JPanel preparationTextPanel = preparationPanel();
		JScrollPane scrollPrepPanel = new JScrollPane(preparationTextPanel);
		scrollPrepPanel.setPreferredSize(new Dimension(400, 165));

		JLabel prepaLabel = new JLabel("Preparation:");
		prepaLabel.setBounds(10, 10, 100, 100);

		JPanel preparationPanel = new JPanel(new BorderLayout());
		preparationPanel.add(prepaLabel, BorderLayout.NORTH);
		preparationPanel.add(scrollPrepPanel, BorderLayout.CENTER);	

		/*Principal Panel*/
		JPanel conteiner = new JPanel(new BorderLayout());
		conteiner.add(titlePanel, BorderLayout.NORTH);
		conteiner.add(recipeNamePanel, BorderLayout.CENTER);

		/*
		 * Buttons panel is where you find the buttons.
		 */
		JPanel buttonsPanel = buttonsPanel(conn, titlePanel, recipeNamePanel, ingredientsPanel, preparationPanel, 
				preparationTextPanel, conteiner);

		conteiner.add(buttonsPanel, BorderLayout.SOUTH);

		add(conteiner);
	}

	private JPanel recipeNamePanel() {
		JLabel recipeNameLabel = new JLabel("Recipe name:");
		JTextField recipeNameText = new JTextField(recipe.getName(), 5);
		recipeNameLabel.setBounds(50, 100, 90, 30);
		recipeNameText.setBounds(140, 100, 210, 30);

		JPanel recipeNamePanel = new JPanel();
		recipeNamePanel.setLayout(null);

		recipeNamePanel.add(recipeNameLabel);
		recipeNamePanel.add(recipeNameText);

		return recipeNamePanel;
	}
	private JPanel ingredientListPanel() {
		JTextArea ingredientsTextArea = new JTextArea();
		ingredientsTextArea.setEditable(false);
		ingredientsTextArea.setLineWrap(true);
		ingredientsTextArea.setWrapStyleWord(true);
		ingredientsTextArea.setSize(new Dimension(300, 160));

		String ingredients = "";
		for (int i = 0; i < ingredientsList.size(); i++) {
			ingredients += ((i + 1) + ") " + "  " + ingredientsList.get(i).getName() + " " +
					ingredientsList.get(i).getQuantity() + " " +
					ingredientsList.get(i).getUnits());
			ingredients += '\n';
		}

		ingredientsTextArea.setText(ingredients);

		JPanel ingredientsListPanel = new JPanel();
		ingredientsListPanel.add(ingredientsTextArea);
		ingredientsListPanel.setBackground(Color.WHITE);

		return ingredientsListPanel;
	}
	private JPanel dataIngPanel(JPanel ingredientListPanel) {

		Icon add = new ImageIcon("+.png");
		Icon del = new ImageIcon("x.png");

		JLabel quantityLabel = new JLabel("Quantity");
		JLabel unitsLabel = new JLabel("Units");
		JLabel ingredientLabel = new JLabel("Ingredient:");
		JLabel delIngredientLabel = new JLabel("Delete ingredient nº:");
		JTextField ingNameText = new JTextField("", 5);
		JTextField quantityText = new JTextField("", 5);
		JTextField delIngText = new JTextField("", 5);
		JComboBox<String> unitsCB = new JComboBox<String>(units);
		JButton addIng = new JButton("", add);
		JButton delIng = new JButton();

		addIng.setIcon(add);
		delIng.setIcon(del);

		quantityLabel.setBounds(185, 0, 65, 30);
		unitsLabel.setBounds(255, 0, 65, 30);

		ingredientLabel.setBounds(10, 30, 65, 30);
		ingNameText.setBounds(80, 30, 105, 30);
		quantityText.setBounds(190, 30, 40, 30);
		unitsCB.setBounds(235, 30, 100, 30);
		addIng.setBounds(340, 30, 30, 30);

		delIngredientLabel.setBounds(170, 65, 120, 30);
		delIngText.setBounds(295, 65, 40, 30);
		delIng.setBounds(340, 65, 30, 30);

		JPanel dataIngPanel = new JPanel();
		dataIngPanel.setLayout(null);
		dataIngPanel.setPreferredSize(new Dimension(400, 105));
		dataIngPanel.add(quantityLabel);
		dataIngPanel.add(unitsLabel);
		dataIngPanel.add(ingredientLabel);
		dataIngPanel.add(ingNameText);
		dataIngPanel.add(quantityText);
		dataIngPanel.add(unitsCB);
		dataIngPanel.add(addIng);
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
							ingredients += ((i + 1) + ") " + "  " + ingredientsList.get(i).getName() + " " +
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
							ingredients += ((i + 1) + ") " + "  " + ingredientsList.get(i).getName() + " " +
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
		JTextArea preparationTextArea = new JTextArea();
		preparationTextArea.setLineWrap(true);
		preparationTextArea.setWrapStyleWord(true);
		preparationTextArea.setSize(new Dimension(300, 160));

		preparationTextArea.setText(recipe.getPreparation());

		JPanel preparationTextPanel = new JPanel();
		preparationTextPanel.add(preparationTextArea);
		preparationTextPanel.setBackground(Color.WHITE);

		return preparationTextPanel;
	}
	private JPanel buttonsPanel(Connections conn, JPanel titlePanel, JPanel recipeNamePanel, JPanel ingredientsPanel, 
			JPanel preparationPanel, JPanel preparationTextPanel, JPanel conteiner) {
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
				JLabel title = (JLabel) titlePanel.getComponent(0);
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
						conteiner.add(ingredientsPanel, BorderLayout.CENTER);
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
						conteiner.add(preparationPanel, BorderLayout.CENTER);
						preparationPanel.setVisible(true);
						buttonsPanel.setVisible(false);
						conteiner.add(save, BorderLayout.SOUTH);
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
					MyRecipes mr = new MyRecipes(conn);
					mr.setVisible(true);
					dispose();
				}

				else if (state == 1) {
					ingredientsPanel.setVisible(false);
					conteiner.add(recipeNamePanel, BorderLayout.CENTER);
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
				JTextArea prepaTextArea = (JTextArea) preparationTextPanel.getComponent(0);

				for (int i = 0; i < ingredientsList.size(); i++) {
					conn.insertIngredient(ingredientsList.get(i));
				}
				recipe.setPreparation(prepaTextArea.getText());
				conn.insertRecipe(recipe);
				JOptionPane.showMessageDialog(null, recipe.getName() + " saved.");
				state = 0;
				MyRecipes mr = new MyRecipes(conn);
				mr.setVisible(true);
				dispose();
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
