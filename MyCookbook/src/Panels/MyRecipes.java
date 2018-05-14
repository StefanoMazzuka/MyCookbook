package Panels;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import Objects.Connections;
import Objects.Recipe;

public class MyRecipes extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private HashMap<JButton, JButton> recipeAndDelButtons;
	private JPanel recipeListPanel;
	JScrollPane scrollPane;
	
	public MyRecipes(Connections conn) {

		JLabel title = new JLabel("My Recipes");
		JButton back = new JButton("Back");
		
		title.setHorizontalAlignment(JTextField.CENTER);
		
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

		/*Panel Principal*/
		setLayout(new BorderLayout());
		add(title, BorderLayout.NORTH);
	
		/*Cargar recetas*/
		loadRecipes(conn);
		createScrollPane();
		
		add(back, BorderLayout.SOUTH);

		//pack();

		back.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Menu m = new Menu(conn);
				m.setVisible(true);
				dispose();
			}
		});
	}
	private void createScrollPane() {
		recipeListPanel = new JPanel(new GridLayout(recipeAndDelButtons.size(), 2));
		for (HashMap.Entry<JButton, JButton> entry : recipeAndDelButtons.entrySet()) {	
			recipeListPanel.add(entry.getValue());
			recipeListPanel.add(entry.getKey());
		}
		recipeListPanel.revalidate();
		recipeListPanel.repaint();
		scrollPane = new JScrollPane(recipeListPanel);
		scrollPane.revalidate();
		scrollPane.repaint();
		this.add(scrollPane, BorderLayout.CENTER);
	}
	private void loadRecipes(Connections conn) {
		recipeAndDelButtons = new HashMap<JButton, JButton>();
		ArrayList<Recipe> recipeList = new ArrayList<Recipe>();
		recipeList = conn.getRecipes();
		JButton recipeButton;
		JButton delRecipeButton;
		for (int i = 0; i < recipeList.size(); i++) {
			recipeButton = new JButton(recipeList.get(i).getName());
			delRecipeButton = new JButton("Delete " + recipeList.get(i).getName());
			delRecipeButton.setName(recipeList.get(i).getName());
			
			recipeButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					JButton b = (JButton)e.getSource();
					ShowRecipe sr = new ShowRecipe(conn, b.getText());
					sr.setVisible(true);
					dispose();
				}
			});
			
			delRecipeButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					JButton b = (JButton)e.getSource();
					conn.deleteRecipe(b.getName());
					updateRecipeListPanel(b);	
				}
			});
			recipeAndDelButtons.put(delRecipeButton, recipeButton);
		}
	}
	public void updateRecipeListPanel(JButton deleteKey) {
		recipeListPanel.remove(deleteKey);
		recipeListPanel.remove(recipeAndDelButtons.get(deleteKey));	
		recipeAndDelButtons.remove(deleteKey);
		recipeListPanel.setLayout(new GridLayout(recipeAndDelButtons.size(), 2));
		recipeListPanel.revalidate();
		recipeListPanel.repaint();	
	}
}
