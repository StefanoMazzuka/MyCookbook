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

import Objects.Connections;
import Objects.Recipe;

public class MyRecipes extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public MyRecipes(Connections conn) {

		JLabel title = new JLabel("My Recipes");
		JButton back = new JButton("Back");

		title.setHorizontalAlignment(JTextField.CENTER);

		setSize(new Dimension(400, 400));
		setLocationRelativeTo(null); 
		setTitle("My Cookbook"); 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		/*Cargar recetas*/
		JPanel recipeList = loadRecipes(conn);
        JScrollPane scrollPane = new JScrollPane(recipeList);
		
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
				Menu m = new Menu();
				m.setVisible(true);
				setVisible(false);
			}
		});
	}
	public JPanel loadRecipes(Connections conn) {
		ArrayList<Recipe> recipeList = conn.getRecipes();
		JPanel recipeListPanel = new JPanel(new GridLayout(recipeList.size(), 1));
		JButton b;
		for (int i = 0; i < recipeList.size(); i++) {
			b = new JButton(recipeList.get(i).getName());
			recipeListPanel.add(b);
		}
		return recipeListPanel;
	}
}
