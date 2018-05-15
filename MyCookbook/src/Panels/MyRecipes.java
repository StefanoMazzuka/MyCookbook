package Panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
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

	private ArrayList<JButton> recipeButtons;
	private JPanel showRecipe;
	JPanel showRecipeWithEdit;
	private JPanel recipeListPanel;
	private JPanel centralPanel;
	private GridBagConstraints gbc1;
	private GridBagConstraints gbc2;
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
		recipeListPanel = createRecipeListPanel(conn);
		
		showRecipe = new JPanel();
		showRecipe.setBackground(Color.WHITE);
		
		showRecipeWithEdit = new JPanel(new BorderLayout());
		
		centralPanel = new JPanel(new GridBagLayout());
		
		gbc1 = new GridBagConstraints();
		gbc1.gridx = 0;
		gbc1.gridy = 0;
		gbc1.weightx = 2;
		gbc1.weighty = 1;
		gbc1.fill = GridBagConstraints.BOTH;
		centralPanel.add(recipeListPanel, gbc1);
		
		gbc2 = new GridBagConstraints();
		gbc2.gridx = 1;
		gbc2.gridy = 0;
		gbc2.weightx = 4;
		gbc2.weighty = 1;
		gbc2.fill = GridBagConstraints.BOTH;
		centralPanel.add(showRecipeWithEdit, gbc2);
		
		/*
		 * Principal Panel
		 */
		JPanel conteiner = new JPanel(new BorderLayout());
		conteiner.add(titlePanel, BorderLayout.NORTH);
		conteiner.add(centralPanel, BorderLayout.CENTER);

		add(conteiner);
	}
	
	private void loadRecipes(Connections conn) {
		recipeButtons = new ArrayList<JButton>();
		ArrayList<Recipe> recipeList = new ArrayList<Recipe>();
		recipeList = conn.getRecipes();
		JButton recipeButton;
		for (int i = 0; i < recipeList.size(); i++) {
			recipeButton = new JButton(recipeList.get(i).getName());
			recipeButton.setName(recipeList.get(i).getName());
			
			recipeButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					JButton b = (JButton)e.getSource();
					String recipeName = b.getText();
					ShowRecipe sr = new ShowRecipe(conn, recipeName);
					showRecipeWithEdit.removeAll();
					showRecipe = new JPanel();
					showRecipe = sr.getShowRecipe();
					showRecipe.setVisible(true);
					
					JButton edit = new JButton("Edit");
					
					edit.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							// TODO Auto-generated method stub
							EditRecipe er = new EditRecipe(conn, recipeName);
							er.setVisible(true);
							dispose();
						}
					});		
					
					JButton delete = new JButton("Delete");
					
					delete.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							// TODO Auto-generated method stub
							
							for (int j = 0; j < recipeButtons.size(); j++) {
								if (recipeButtons.get(j).getName().equals(recipeName))
									recipeButtons.remove(j);
							}
							
							conn.deleteRecipe(recipeName);	
							
							showRecipeWithEdit.removeAll();
							showRecipeWithEdit.revalidate();
							showRecipeWithEdit.repaint();
							recipeListPanel.removeAll();
							recipeListPanel = createRecipeListPanel(conn);	
							centralPanel.removeAll();
							centralPanel.add(recipeListPanel, gbc1);
							centralPanel.add(showRecipeWithEdit, gbc2);
							centralPanel.revalidate();
							centralPanel.repaint();
						}
					});
					
					showRecipeWithEdit.add(showRecipe, BorderLayout.CENTER);
					
					JPanel buttons = new JPanel(new GridLayout(1, 2));
					buttons.add(delete);
					buttons.add(edit);
					
					showRecipeWithEdit.add(buttons, BorderLayout.SOUTH);
					showRecipeWithEdit.revalidate();
					showRecipeWithEdit.repaint();
					recipeListPanel.removeAll();
					recipeListPanel = createRecipeListPanel(conn);	
					centralPanel.removeAll();
					centralPanel.add(recipeListPanel, gbc1);
					centralPanel.add(showRecipeWithEdit, gbc2);
					centralPanel.revalidate();
					centralPanel.repaint();
				}
			});
			this.recipeButtons.add(recipeButton);
		}
	}
	private JPanel createRecipeListPanel(Connections conn) {
		
		loadRecipes(conn);
		
		showRecipe = new JPanel();
		showRecipe.setBackground(Color.BLUE);
		
		JPanel recipesPanel = new JPanel();
		recipesPanel.setLayout(null);
		recipesPanel.setBackground(Color.WHITE);
		
		JButton b;
		for (int i = 0; i < recipeButtons.size(); i++) {
			b = new JButton();
			b = recipeButtons.get(i);
			b.setBounds(10, (i * 30) + 10, 190, 30);
			
			recipesPanel.add(b);
		}
		
		JScrollPane scrollPane = new JScrollPane(recipesPanel);
		scrollPane.setPreferredSize(new Dimension(200, 400));
		
		JButton newRecipeButton = new JButton("New Recipe");
		
		newRecipeButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				NewRecipe nR = new NewRecipe(conn);
				nR.setVisible(true);
				setVisible(false);
				dispose();
			}
		});
		
		JPanel recipeListPanel = new JPanel(new BorderLayout());
		recipeListPanel.add(scrollPane, BorderLayout.CENTER);
		recipeListPanel.add(newRecipeButton, BorderLayout.SOUTH);
		
		return recipeListPanel;
	}
}
