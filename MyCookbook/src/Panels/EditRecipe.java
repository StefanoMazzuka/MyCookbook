package Panels;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
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
	
	public EditRecipe(Connections conn) {
		JLabel title = new JLabel("New Recipe");
		
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

		JPanel ingredientsPanel = new JPanel(new GridLayout(2, 1));
		ingredientsPanel.add(scrollPane);
		ingredientsPanel.add(dataIngPanel);
	}
}
