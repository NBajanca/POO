package userInterface;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
 

public class SwingInterface{
	private JPanel jp;
	
	//Train
	private JPanel jp_train = new JPanel();
	private JLabel label_train = new JLabel("Train File: ");
	private JTextField text_train = new JTextField();
	
	//Test
	private JPanel jp_test = new JPanel();
	private JLabel label_test = new JLabel("Test File: ");
	private JTextField text_test = new JTextField();
	
	//Score
	private JPanel jp_score = new JPanel();
	private JLabel label_score = new JLabel("Score: ");
	private JTextField text_score = new JTextField();
	
	//Random Restarts
	private JPanel jp_randrest = new JPanel();
	private JLabel label_randrest = new JLabel("Max Random Restarts: ");
	private JTextField text_randrest = new JTextField();
	
	//Var
	private JPanel jp_var = new JPanel();
	private JLabel label_var = new JLabel("Var to discover ");
	private JTextField text_var = new JTextField();
	
	private JButton button = new JButton("Discover the Future!"); 
 
    public SwingInterface(JPanel p) {
    	this.jp = p;
    	
    	//Train
    	jp_train.setLayout(new GridLayout(1,2));
    	jp.add(jp_train);
    	jp_train.add(label_train);
    	jp_train.add(text_train);
    	
    	//Test
    	jp_test.setLayout(new GridLayout(1,2));
    	jp.add(jp_test);
    	jp_test.add(label_test);
    	jp_test.add(text_test);
    	
    	//Score
    	jp_score.setLayout(new GridLayout(1,2));
    	jp.add(jp_score);
    	jp_score.add(label_score);
    	jp_score.add(text_score);
    	
    	//Random Restarts
    	jp_randrest.setLayout(new GridLayout(1,2));
    	jp.add(jp_randrest);
    	jp_randrest.add(label_randrest);
    	jp_randrest.add(text_randrest);
    	
    	//Var
    	jp_var.setLayout(new GridLayout(1,2));
    	jp.add(jp_var);
    	jp_var.add(label_var);
    	jp_var.add(text_var);
    	
    	jp.add(button);
    	button.addActionListener(new Handler());
    }
    
    private class Handler implements ActionListener {
    	 public void actionPerformed(ActionEvent e) {
    		 String train = text_train.getText();
    		 String test = text_test.getText();
    		 String score = text_score.getText();
    		 String randrest = text_randrest.getText();
    		 String var = text_var.getText();
    		
    		 System.out.println("Paramaters: " + train + " " +  test + " " + score + " " + randrest + " " + var);
    		 Main.main(new String[] {train, test, score, randrest, var});
    	 }
    } 
 
    public static void main(String[] args) {
    	JFrame frame = new JFrame("Object Oriented Programming 2014/15 - Learning dynamic Bayesian networks");
		// Add a window listner for close button
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// This is an empty content area in the frame
    	JPanel p = new JPanel(); 
		frame.setContentPane(p);
		SwingInterface app = new SwingInterface(p);
		p.setLayout(new GridLayout(6,1));
		p.setPreferredSize(new Dimension(500, 300));
		frame.pack();
		frame.setVisible(true);
    }
}