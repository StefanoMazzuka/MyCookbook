package Panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class Test extends JFrame {

	public Test() {
		
		setResizable(false);
		setSize(new Dimension(400, 400));
		setLocationRelativeTo(null); 
		setTitle("My Cookbook"); 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel left = new JPanel();
		left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
		left.setBackground(Color.WHITE);
		left.setAlignmentX(Component.RIGHT_ALIGNMENT);
		
		JLabel l = new JLabel("2");
		left.add(Box.createRigidArea(new Dimension(0, 3)));
//		l.setAlignmentX(Component.RIGHT_ALIGNMENT);
		left.add(l);
		
		left.add(Box.createRigidArea(new Dimension(0,4)));
		JLabel l2 = new JLabel("2");
//		l2.setAlignmentX(Component.RIGHT_ALIGNMENT);
		left.add(l2);

		left.add(Box.createRigidArea(new Dimension(0,4)));
		JLabel l3 = new JLabel("2asdasdsdad as");
//		l3.setAlignmentX(Component.RIGHT_ALIGNMENT);
		left.add(l3);
		
		JPanel right = new JPanel();
		right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));
		right.setBackground(Color.GREEN);
		
		JButton b = new JButton();
		b.setBorder(BorderFactory.createEmptyBorder(10,10, 10, 10));
		b.setAlignmentX(Component.LEFT_ALIGNMENT);
		right.add(b);
		
		
		right.add(Box.createRigidArea(new Dimension(50, 1)));
		JButton b2 = new JButton();
		b2.setBorder(BorderFactory.createEmptyBorder(10,10, 10, 10));
		b2.setAlignmentX(Component.LEFT_ALIGNMENT);
		right.add(b2);
		
		right.add(Box.createRigidArea(new Dimension(0, 1)));
		JButton b3 = new JButton();
		b3.setBorder(BorderFactory.createEmptyBorder(10,10, 10, 10));
		b3.setAlignmentX(Component.LEFT_ALIGNMENT);
		right.add(b3);
		right.add(Box.createRigidArea(new Dimension(100, 1)));
		
		JPanel conteiner = new JPanel(new BorderLayout());
		conteiner.add(left, BorderLayout.CENTER);
		conteiner.add(right, BorderLayout.EAST);
		
		add(conteiner);
	}
}
