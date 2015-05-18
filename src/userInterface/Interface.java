package userInterface;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.text.JTextComponent;
import javax.swing.JToggleButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.AbstractListModel;

import java.awt.Choice;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.PrintStream;

import javax.swing.JFileChooser;
import javax.swing.JTextArea;
import javax.swing.JScrollBar;
import javax.swing.SwingConstants;
public class Interface {

	private JFrame frame;
	private JTextField textField;
	private JTextField textField_1;
	protected String[] arguments;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextArea textArea;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Interface window = new Interface();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Interface() {
		 initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * @return 
	 */
	@SuppressWarnings("unchecked")
	private String[] initialize() {
		
		String [] arguments = new String[5];
		frame = new JFrame();
		frame.setBounds(100, 100, 640, 480);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btnTrain = new JButton("Train");
		
		btnTrain.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooserTrain = new JFileChooser();
				int returnValue = fileChooserTrain.showOpenDialog(null);
		        if (returnValue == JFileChooser.APPROVE_OPTION) {
		          File selectedFileTrain = fileChooserTrain.getSelectedFile();
		          textField_2.setText(selectedFileTrain.getAbsolutePath()); 
		        }
		      }
		});
		btnTrain.setBounds(6, 6, 117, 29);
		frame.getContentPane().add(btnTrain);
		
		JButton btnData = new JButton("Data");
		btnData.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooserData = new JFileChooser();
				int returnValue = fileChooserData.showOpenDialog(null);
		        if (returnValue == JFileChooser.APPROVE_OPTION) {
		        	File selectedFileData = fileChooserData.getSelectedFile();
		          textField_3.setText(selectedFileData.getAbsolutePath());
		        }
			}
		});
		btnData.setBounds(6, 38, 117, 29);
		frame.getContentPane().add(btnData);
		
		textField = new JTextField();
		textField.setBounds(6, 159, 117, 28);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JLabel lblRandomRestarts = new JLabel("Number Restarts");
		lblRandomRestarts.setBounds(13, 141, 110, 16);
		frame.getContentPane().add(lblRandomRestarts);
		
		textField_1 = new JTextField();
		textField_1.setBounds(6, 207, 117, 28);
		frame.getContentPane().add(textField_1);
		textField_1.setColumns(10);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(129, 6, 193, 28);
		frame.getContentPane().add(textField_2);
		
		textField_3 = new JTextField();
		textField_3.setColumns(10);
		textField_3.setBounds(129, 38, 193, 28);
		frame.getContentPane().add(textField_3);
		
		JTextArea textArea = new JTextArea();
		textArea.setBounds(6, 0, 574, 417);
		textArea.setEditable(false);
		frame.getContentPane().add(textArea);
		
		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setBounds(135, 74, 499, 378);
		frame.getContentPane().add(scrollPane);
		
		
		PrintStream printStream = new PrintStream(new CustomOutputStream(textArea));
		System.setOut(printStream);
        System.setErr(printStream);
		
		
		JLabel lblNumberInstances = new JLabel("Number Instances");
		lblNumberInstances.setBounds(6, 191, 122, 16);
		frame.getContentPane().add(lblNumberInstances);
		
		JList<String> list_1 = new JList<String>();
		list_1.setVisibleRowCount(2);
		list_1.setToolTipText("");
		list_1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list_1.setModel(new AbstractListModel() {
			String[] values = new String[] {"LL", "MDL"};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		list_1.setSelectedIndex(0);
		list_1.setBounds(6, 74, 117, 34);
		frame.getContentPane().add(list_1);
		
		JButton btnRun = new JButton("RUN");
		btnRun.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textArea.setText(null);
				if(textField_2.getText().isEmpty() || textField_3.getText().isEmpty() || textField.getText().isEmpty()) System.out.println("Please Input Files and Number of Random Restart!");
				else{
					if(textField_1.getText().isEmpty()) Main.main(new String[] {textField_2.getText(), textField_3.getText(), list_1.getSelectedValue(), textField.getText()});
					else {
						final String var = textField_1.getText();
						Main.main(new String[] {textField_2.getText(), textField_3.getText(), list_1.getSelectedValue(), textField.getText(), var});
					}
				}
				
				
				
					
				
			}
		});
		btnRun.setBounds(325, 6, 193, 60);
		frame.getContentPane().add(btnRun);
		
		JLabel lblNewLabel = new JLabel("POO Project");
		lblNewLabel.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel.setBounds(6, 247, 117, 16);
		frame.getContentPane().add(lblNewLabel);
		
		
		
		
		
		
		return arguments;
	
		
	}
}
