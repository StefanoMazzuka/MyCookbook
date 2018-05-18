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

import javax.swing.BoxLayout;
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
import javax.swing.SwingConstants;

import Objects.Connections;
import Objects.Ingredient;
import Objects.Recipe;

public class EditRecipe extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Recipe recipe = new Recipe();
	JPanel titlePanel;
	private JPanel ingredientsListPanel;
	private JPanel ingredientsPanel;
	private JScrollPane scrollIngPanel;
	private JPanel dataIngPanel;
	JPanel buttonsPanel;
	private JPanel conteiner;
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
		titlePanel = new JPanel();
		titlePanel.setLayout(new BorderLayout());
		titlePanel.setPreferredSize(new Dimension(400, 60));
		titlePanel.add(title);

		JPanel recipeNamePanel = recipeNamePanel();

		/*
		 * Ingredients list on scroll panel.
		 */	
		ingredientsListPanel = new JPanel();
		dataIngPanel = dataIngPanel();

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
		conteiner = new JPanel(new BorderLayout());
		conteiner.add(titlePanel, BorderLayout.NORTH);
		conteiner.add(recipeNamePanel, BorderLayout.CENTER);

		/*
		 * Buttons panel is where you find the buttons.
		 */
		buttonsPanel = buttonsPanel(conn, titlePanel, recipeNamePanel,
				preparationPanel, preparationTextPanel);

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
	private void printIngredientListPanel() {	
		ingredientsListPanel.removeAll();
		ingredientsListPanel = new JPanel();
		ingredientsListPanel.setLayout(new BoxLayout(ingredientsListPanel, BoxLayout.Y_AXIS));
		ingredientsListPanel.setBackground(Color.WHITE);

		Icon del = new ImageIcon(NewRecipe.class.getResource("/Resources/x.png"));		
		JButton b;
		for (int i = 0; i < ingredientsList.size(); i++) {
			Ingredient ing = ingredientsList.get(i);
			b = new JButton((i + 1) + ") " + ing.getName() + " " + ing.getQuantity() + " " + ing.getUnits() + " ");
			b.setIcon(del);
			b.setVerticalTextPosition(SwingConstants.CENTER);
			b.setHorizontalTextPosition(SwingConstants.LEFT);
			b.setOpaque(false);
			b.setContentAreaFilled(false);
			b.setBorderPainted(false);

			b.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					ingredientsList.remove(ing);
					printIngredientListPanel();
				}
			});

			ingredientsListPanel.add(b);
		}

		scrollIngPanel = new JScrollPane(ingredientsListPanel);
		scrollIngPanel.setPreferredSize(new Dimension(400, 165));
		ingredientsPanel = new JPanel(new BorderLayout());
		ingredientsPanel.add(scrollIngPanel, BorderLayout.CENTER);
		ingredientsPanel.add(dataIngPanel, BorderLayout.SOUTH);
		conteiner.remove(1);
		conteiner.add(titlePanel, BorderLayout.NORTH);
		conteiner.add(ingredientsPanel, BorderLayout.CENTER);
		conteiner.add(buttonsPanel, BorderLayout.SOUTH);
		conteiner.revalidate();
		conteiner.repaint();
	}
	private JPanel dataIngPanel() {
		Icon add = new ImageIcon(NewRecipe.class.getResource("/Resources/+.png"));

		JLabel quantityLabel = new JLabel("Quantity");
		JLabel unitsLabel = new JLabel("Units");
		JLabel ingredientLabel = new JLabel("Ingredient:");
		JTextField ingNameText = new JTextField("", 5);
		JTextField quantityText = new JTextField("", 5);
		JComboBox<String> unitsCB = new JComboBox<String>(units);
		JButton addIng = new JButton(add);

		quantityLabel.setBounds(185, 0, 65, 30);
		unitsLabel.setBounds(255, 0, 65, 30);

		ingredientLabel.setBounds(10, 30, 65, 30);
		ingNameText.setBounds(80, 30, 105, 30);
		quantityText.setBounds(190, 30, 40, 30);
		unitsCB.setBounds(235, 30, 100, 30);
		addIng.setBounds(340, 30, 30, 30);

		JPanel dataIngPanel = new JPanel();
		dataIngPanel.setLayout(null);
		dataIngPanel.setPreferredSize(new Dimension(400, 75));
		dataIngPanel.add(quantityLabel);
		dataIngPanel.add(unitsLabel);
		dataIngPanel.add(ingredientLabel);
		dataIngPanel.add(ingNameText);
		dataIngPanel.add(quantityText);
		dataIngPanel.add(unitsCB);
		dataIngPanel.add(addIng);

		quantityText.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char input = e.getKeyChar();
				if (input != '.' && ((input < '0' || input > '9') && input != '\b')) {
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

				else if (9999 < Integer.parseInt(quantityText.getText()) || 
						0 > Integer.parseInt(quantityText.getText())) {
					JOptionPane.showMessageDialog(null, "ERROR 004: Invalid quantity.");
				}
				
				else {
					Ingredient ing = new Ingredient(ingNameText.getText(), 
							Double.parseDouble((quantityText.getText())), 
							unitsCB.getItemAt(unitsCB.getSelectedIndex()), recipe.getName());

					if (!ingredientsList.contains(ing)) {							
						ingredientsList.add(ing);
						printIngredientListPanel();
						ingNameText.setText("");
						quantityText.setText("");
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
	private JPanel buttonsPanel(Connections conn, JPanel titlePanel, JPanel recipeNamePanel, 
			JPanel preparationPanel, JPanel preparationTextPanel) {
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
						
						recipe.setName(recipeNameText.getText());
						recipeNamePanel.setVisible(false);
						title.setText(recipeNameText.getText());
						printIngredientListPanel();
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
