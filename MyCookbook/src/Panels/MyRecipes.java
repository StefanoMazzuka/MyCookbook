package Panels;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

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
	private String recipeName;
	private ArrayList<Recipe> recipeList = new ArrayList<Recipe>();
	
	public MyRecipes(Connections conn) {

		JLabel title = new JLabel("My Recipes");
		JButton back = new JButton("Back");
		
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

		/*Cargar recetas*/
		recipeList = conn.getRecipes();
		JPanel recipeListPanel = new JPanel(new GridLayout(recipeList.size(), 2));
		JButton recipe;
		JButton delRecipe;
		for (int i = 0; i < recipeList.size(); i++) {
			recipeName = recipeList.get(i).getName();
			recipe = new JButton(recipeName);
			delRecipe = new JButton("Delete");
			delRecipe.setName(recipeName);
			recipeListPanel.add(recipe);
			recipeListPanel.add(delRecipe);
			
			recipe.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					JButton b = (JButton)e.getSource();
					ShowRecipe sr = new ShowRecipe(conn, b.getText());
					sr.setVisible(true);
				}
			});
			
			delRecipe.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					JButton b = (JButton)e.getSource();
					conn.deleteRecipe(b.getName());
					recipeListPanel.revalidate();
					recipeListPanel.repaint();
				}
			});
		}

        JScrollPane scrollPane = new JScrollPane(recipeListPanel);
		
		/*Panel Principal*/
		setLayout(new BorderLayout());
		add(title, BorderLayout.NORTH);
		add(scrollPane, BorderLayout.CENTER);
		add(back, BorderLayout.SOUTH);

		//pack();

		back.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Menu m = new Menu(conn);
				m.setVisible(true);
				setVisible(false);
			}
		});
	}
	public JPanel loadRecipes(Connections conn) {
		recipeList = conn.getRecipes();
		JPanel recipeListPanel = new JPanel(new GridLayout(recipeList.size(), 2));
		JButton recipe;
		JButton delRecipe;
		for (int i = 0; i < recipeList.size(); i++) {
			recipeName = recipeList.get(i).getName();
			recipe = new JButton(recipeName);
			delRecipe = new JButton("Delete");
			delRecipe.setName(recipeName);
			recipeListPanel.add(recipe);
			recipeListPanel.add(delRecipe);
			
			recipe.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					JButton b = (JButton)e.getSource();
					ShowRecipe sr = new ShowRecipe(conn, b.getText());
					sr.setVisible(true);
				}
			});
			
			delRecipe.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					JButton b = (JButton)e.getSource();
					conn.deleteRecipe(b.getName());
					recipeListPanel.revalidate();
					recipeListPanel.repaint();
				}
			});
		}
		return recipeListPanel;
	}
}
