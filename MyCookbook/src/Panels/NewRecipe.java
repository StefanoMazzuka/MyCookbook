package Panels;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.table.DefaultTableModel;

import Objects.Ingredient;
import Objects.Recipe;

public class NewRecipe extends JFrame {
	private Recipe recipe = new Recipe();
	private ArrayList<Ingredient> ingredientsList = new ArrayList<Ingredient>();
	private String[] units = { "Kg", "g", "L", "ml", "Cup", "Table spoon", "Tea spoon", "Unit", "Pieces"};
	private int state;

	public NewRecipe() {
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
		JTextArea textArea = new JTextArea(5, 30);
		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
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
		JComboBox unitsCB = new JComboBox(units);
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

		pack();

		// Next button
		next.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (state == 0) {
					if (recipeNameText.getText().equals(""))
						JOptionPane.showMessageDialog(null, "Please enter a name for the recipe.");
					else {
						recipe.setName(recipeNameText.getText());
						dataPanel.setVisible(false);
						title.setText(recipeNameText.getText());
						add(dataIngPanel, BorderLayout.CENTER);
						dataIngPanel.setVisible(true);
						state = 1;
					}	
				}
				else if (state == 1) {
					if (ingNameText.getText().equals(""))
						JOptionPane.showMessageDialog(null, "Please enter a name for the ingredient.");
					else {
						dataIngPanel.setVisible(false);
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
					dataIngPanel.setVisible(false);
					add(dataPanel, BorderLayout.CENTER);
					dataPanel.setVisible(true);
					state = 0;
				}
			}
		});
		
		addIng.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				//Ingredient i = new Ingredient();
			}
		});
	}
}
