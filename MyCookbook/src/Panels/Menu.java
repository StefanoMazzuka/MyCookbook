package Panels;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Objects.Connections;

public class Menu extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Menu(Connections conn) {
	
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

		
		JLabel menu = new JLabel("MENU");	
		JButton myRecipes = new JButton("My Recipes");
		JButton newRecipe = new JButton("New Recipe");
		
		menu.setBounds(183, 100, 34, 30);
		myRecipes.setBounds(145, 140, 110, 30);
		newRecipe.setBounds(145, 171, 110, 30);
		
		JPanel menuPanel = new JPanel();
		menuPanel.setLayout(null);
		menuPanel.add(menu);
		menuPanel.add(myRecipes);
		menuPanel.add(newRecipe);
		
		add(menuPanel);

		myRecipes.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				MyRecipes mR = new MyRecipes(conn);
				mR.setVisible(true);
				setVisible(false);
			}
		});

		newRecipe.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				NewRecipe nR = new NewRecipe(conn);
				nR.setVisible(true);
				setVisible(false);
			}
		});
	}
}
