package OR_Calculator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ORC extends JFrame implements ActionListener {
	static JTextField con , var;
	JButton  find, generate;
	static JFrame frame;
	static JScrollPane sp;
	static JTextField[] textFields;
	static JTextField[][] textFieldscon;
	JRadioButton opt1,opt2;
	ButtonGroup group;
	JPanel pp,pm;
	ORC(){
		frame = new JFrame("Senstivity Analysis in LPP");
		frame.setLayout(null);
		
		
		JPanel p1 = new JPanel(); // created heading panel
		p1.setBounds(0,0,1550,30);
		p1.setBackground(new Color(100,100,100));
		p1.setLayout(null);
		frame.add(p1);
		
		JLabel wel = new JLabel("Senstivity Analysis"); // heading set 
		wel.setFont(new Font("SANSERIF",Font.BOLD,20));
		wel.setForeground(Color.white);
		wel.setBounds(250, 0, 200, 30);
		p1.add(wel);
		
		
		JPanel p2 = new JPanel(); // created heading panel
		p2.setBounds(0,30,1550,70);
		p2.setBackground(new Color(250,250,240));
		p2.setLayout(null);
		frame.add(p2);
		
		JLabel s1 = new JLabel("Solve the Linear Programming Problem ");
		s1.setFont(new Font("SANSERIF",Font.BOLD,20));
		s1.setBounds(150, 5, 400, 30);
		p2.add(s1);
		
		JLabel s2 = new JLabel("Method : Simplex Method ");
		s2.setFont(new Font("SANSERIF",Font.PLAIN,20));
		s2.setBounds(225, 35, 400, 30);
		p2.add(s2);
		
		
		
		pp = new JPanel(); // getting no. of unknown values
		pp.setBounds(0,100,1550,65);
		pp.setBackground(new Color(230,250,240));
		pp.setLayout(null);
		frame.add(pp);
		
		
		
		JLabel s3 = new JLabel("Fill the problem here :");
		s3.setFont(new Font("SANSERIF",Font.PLAIN,20));
		s3.setBounds(10, 0, 200, 30);
		pp.add(s3);
		
		opt1 = new JRadioButton();
		opt1.setBounds(220,0,20,30);
		opt1.setFont(new Font("SAN_SERIF",Font.BOLD,16));
		opt1.setBackground(new Color(230,250,240));
		pp.add(opt1);
		
		JLabel s6 = new JLabel("Maximization");
		s6.setFont(new Font("SANSERIF",Font.PLAIN,15));
		s6.setBounds(245, 0, 90, 28);
		pp.add(s6);
		
		opt2 = new JRadioButton();
		opt2.setBounds(340,0,20,30);
		opt2.setFont(new Font("SAN_SERIF",Font.BOLD,16));
		opt2.setBackground(new Color(230,250,240));
		pp.add(opt2);
		
		group = new ButtonGroup();
		group.add(opt1);
		group.add(opt2);
		
		JLabel s7 = new JLabel("Minimization");
		s7.setFont(new Font("SANSERIF",Font.PLAIN,15));
		s7.setBounds(365, 0, 90, 28);
		pp.add(s7);
		
		JLabel s4 = new JLabel("Total no. of variable :");
		s4.setFont(new Font("SANSERIF",Font.PLAIN,15));
		s4.setBounds(10, 35, 150, 15);
		pp.add(s4);
		
		var = new JTextField();
		var.setBounds(165, 35,30,20);
		var.setFont(new Font("SAN_SERIF",Font.PLAIN,16));
		pp.add(var);
		
		JLabel s5 = new JLabel("Total no. of constraints :");
		s5.setFont(new Font("SANSERIF",Font.PLAIN,15));
		s5.setBounds(200, 35, 170, 15);
		pp.add(s5);
		
		con = new JTextField();
		con.setBounds(375, 35,30,20);
		con.setFont(new Font("SAN_SERIF",Font.PLAIN,16));
		pp.add(con);
		
		generate = new JButton("Generate");
		generate.setBounds(420, 35, 90, 20);
		generate.setBackground(new Color(173, 216, 230));
		generate.addActionListener(this);
		pp.add(generate);
		
		
	    
		frame.getContentPane().setBackground(new Color(230,250,240));
		frame.setLocation(400,100);
		frame.setSize(700,600);
		frame.setVisible(true);
	}
	public  void actionPerformed(ActionEvent ae) {
		if(ae.getSource()==generate) {
			
			String v = var.getText();
			int va =Integer.parseInt(v);
			
			String c = con.getText();
			int co =Integer.parseInt(c);
			
			JPanel pm = new JPanel(); // created main panel
			pm.setLayout(new BoxLayout(pm, BoxLayout.Y_AXIS)); // Vertical layout
			pm.setLocation(30,160);
			pm.setSize(625, 400);

			textFields = new JTextField[va]; // Array to store the text fields of variable z
			textFieldscon = new JTextField[co][va+1];
			
			JPanel ps = new JPanel();// created sub panel
			JLabel z = new JLabel("Z = ");
			ps.add(z);
			pm.add(ps);
			
			// Create 'n' JTextFields and add them to the panel
	        for (int i = 0; i < va; i++) {
	            JTextField textField = new JTextField(5); // Width of 5 characters, adjust as needed
	            textFields[i] = textField; // Store the text field in the array
	            ps.add(textField);
	            JLabel vv;
	            if(i+1!=va) {
	            	vv = new JLabel("X " + (i + 1)+" + ");
	            }else {
	            	vv = new JLabel("X " + (i + 1));
	            }
	            ps.add(vv); // Add a label for each text field
	        }
	        
	        pm.add(new JLabel("Subject to constraints : "));
	        for(int i = 0;i<co;i++) {     
	        	JPanel ps1 = new JPanel();
	        	for (int j = 0; j < va; j++) {
	        		JTextField textField = new JTextField(5); // Width of 5 characters, adjust as needed
	        		textFieldscon[i][j] = textField; // Store the text field in the array
	        		JLabel vv;
	        		if(j+1!=va) {
	        			vv = new JLabel("X " + (j + 1)+" + ");
	        		}else {
	        			vv = new JLabel("X " + (j + 1));
	        		}
	        		ps1.add(textField);
	        		ps1.add(vv); // Add a label for each text field
	        	}    
	        	ps1.add(new JLabel(" <= "));
	        	JTextField eq = new JTextField(5);
	        	textFieldscon[i][va] = eq;
        		ps1.add(eq);
        		pm.add(ps1);
	        }
	       
			
			find = new JButton("Find");
			find.setBounds(220, 290, 90, 30);
			find.setBackground(new Color(173, 216, 230));
			find.addActionListener(this);
			pm.add(find);
			
			
			sp = new JScrollPane();

			sp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);  
	        sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); 
			sp.setBounds(30,160,625,400);
			sp.getViewport().add(pm);
			frame.add(sp);
			
			
	        pm.revalidate();
	        pm.repaint();
	        
		}
		else if(ae.getSource()==find) {
			if(opt1.isSelected()) {
				new Solver(0,textFields,textFieldscon);
			}else {
				new Solver(1,textFields,textFieldscon);
			}
			frame.setVisible(false);
		}
	}


	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new ORC();
	}

}