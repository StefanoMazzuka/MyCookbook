package Panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.Icon;
import javax.swing.ImageIcon;
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
	private ArrayList<JButton> recipeButtons;
	private JPanel recipeListPanel;
	JScrollPane scrollPane;

	public MyRecipes(Connections conn) {
		
		setResizable(false);
		setSize(new Dimension(600, 400));
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
		 * Title Panel.
		 */
		JLabel title = new JLabel("My Recipes");
		title.setHorizontalAlignment(JTextField.CENTER);
		JPanel titlePanel = new JPanel();
		titlePanel.setLayout(new BorderLayout());
		titlePanel.setPreferredSize(new Dimension(400, 60));
		titlePanel.add(title);

		/*
		 * Central Panel.
		 */
		 n
		JPanel centralPanel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		
		/*
		 * Principal Panel
		 */
		JPanel conteiner = new JPanel(new BorderLayout());
		conteiner.add(titlePanel, BorderLayout.NORTH);

		/*Cargar recetas*/
		loadRecipes(conn);
		createScrollPane(conteiner);
		createShowRecipePanel(conteiner);
		

		add(conteiner);
	}
	private void loadRecipes(Connections conn) {
		recipeButtons = new ArrayList<JButton>();
		ArrayList<Recipe> recipeList = new ArrayList<Recipe>();
		recipeList = conn.getRecipes();
		JButton recipeButton;
		for (int i = 0; i < recipeList.size(); i++) {
			recipeButton = new JButton(recipeList.get(i).getName());
			
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
			this.recipeButtons.add(recipeButton);
		}
	}
	private void createScrollPane(JPanel conteiner) {
		
//		GridBagConstraints gbc = new GridBagConstraints();
//		recipeListPanel = new JPanel(new GridBagLayout());
//		recipeListPanel.setBackground(Color.WHITE);
//		JButton b;
//		int i = 0;
//		for (HashMap.Entry<JButton, JButton> entry : recipeAndDelButtons.entrySet()) {
//			b = entry.getValue();
//			gbc.gridx = 0;
//			gbc.gridy = i;
//			gbc.weightx = 10;
//			gbc.fill = GridBagConstraints.HORIZONTAL;
//			gbc.insets = new Insets(0, 10, 0, 1);
//
//			recipeListPanel.add(b, gbc);
//
//			b = entry.getKey();
//			gbc.gridx = 4;
//			gbc.gridy = i;
//			gbc.weightx = 0.5;
//			gbc.fill = GridBagConstraints.HORIZONTAL;
//			gbc.insets = new Insets(0, 0, 0, 10);
//
//			recipeListPanel.add(b, gbc);
//			i++;
//		}
//		recipeListPanel.revalidate();
//		recipeListPanel.repaint();
//		scrollPane = new JScrollPane(recipeListPanel);
//		scrollPane.revalidate();
//		scrollPane.repaint();
//		conteiner.add(scrollPane, BorderLayout.CENTER);
				
		recipeListPanel = new JPanel();
		recipeListPanel.setLayout(null);
		recipeListPanel.setBackground(Color.WHITE);
		
		JButton b;
		for (int i = 0; i < recipeButtons.size(); i++) {
			b = new JButton();
			b = recipeButtons.get(i);
			b.setBounds(10, (i * 30) + 10, 180, 30);
			
			recipeListPanel.add(b);
		}
		
		recipeListPanel.revalidate();
		recipeListPanel.repaint();
		scrollPane = new JScrollPane(recipeListPanel);
		scrollPane.revalidate();
		scrollPane.repaint();
		conteiner.add(scrollPane, BorderLayout.CENTER);
		
		JButton newRecipeButton = new JButton("New Recipe");
		
		newRecipeButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	public void updateRecipeListPanel(JButton deleteKey) {
		recipeListPanel.remove(deleteKey);
		recipeListPanel.remove(recipeAndDelButtons.get(deleteKey));	
		recipeAndDelButtons.remove(deleteKey);
		recipeListPanel.revalidate();
		recipeListPanel.repaint();	
	}
}
