package OR_Calculator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Solution extends JFrame implements ActionListener {
	double orgsol;
	double[]orgvar;
	double objective[];
	double constraint[][];
	JFrame frame;
	JPanel sa;
	JButton close,back;
	Solution(double orgsol,double[]orgvar,double objective[],double constraint[][]){
		this.orgsol = orgsol;
		this.orgvar = orgvar;
		this.objective = objective;
		this.constraint = constraint;
		frame = new JFrame("Senstivity Analysis in LPP Solution");
		frame.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		JLabel oos = new JLabel("Optimal solution: "+orgsol);
		oos.setFont(new Font("SAN_SERIF",Font.PLAIN,15));
		frame.add(oos);
		
		frame.add(Box.createRigidArea(new Dimension(1000,0)));
		
		
		JLabel odv = new JLabel("Decision Variables : ");
		odv.setFont(new Font("SAN_SERIF",Font.PLAIN,15));
		frame.add(odv);
		
		frame.add(Box.createRigidArea(new Dimension(1000,0)));
		
		for(int i = 0;i<orgvar.length;i++) {
			JLabel varp = new JLabel("x" + (i + 1) + ": "+orgvar[i]+"  ,");
			varp.setFont(new Font("SAN_SERIF",Font.PLAIN,15));
			frame.add(varp);
		}
		
		frame.add(Box.createRigidArea(new Dimension(1000,0)));
		
		
		JLabel sss = new JLabel("Senstivity Analysis : ");
		sss.setFont(new Font("SAN_SERIF",Font.PLAIN,15));
		frame.add(sss);
		
		frame.add(Box.createRigidArea(new Dimension(1000,0)));
		
		sa = new JPanel();
		sa.setLayout(new BoxLayout(sa, BoxLayout.Y_AXIS)); // Vertical layout
		
		for (int i = 0; i < constraint.length; i++) {
            constraint[i][constraint[0].length-1] += 1;
            // Solve the modified linear programming problem
        	double[] sovv = Solv(i,objective,constraint);
        	double slack = 0.0;
        	double sum = 0.0;
        	for(int j = 0;j<objective.length;j++) {
        		sum+=constraint[i][j]*orgvar[j];
        		
        	}
        	slack = constraint[i][objective.length]-sum-1;
        	JLabel sap = new JLabel("c" + (i + 1) + ":-->  " + " Shadow Price : "+ 
        	(sovv[objective.length]-orgsol)+"  Slack : "+slack);
        	sap.setFont(new Font("SAN_SERIF",Font.PLAIN,15));
    		sa.add(sap);
        }
		sa.setBackground(new Color(230,250,240));
		
		frame.add(sa);
		frame.add(Box.createRigidArea(new Dimension(1000,0)));
		close = new JButton("Close");
		//.setBounds(220, 290, 90, 30);
		close.setBackground(new Color(173, 216, 230));
		close.addActionListener(this);
		
		
		back = new JButton("Back");
		//.setBounds(220, 290, 90, 30);
		back.setBackground(new Color(173, 216, 230));
		back.addActionListener(this);
		
		
		frame.add(back);
		frame.add(close);
		
		
		frame.getContentPane().setBackground(new Color(230,250,240));
		frame.setLocation(400,100);
		frame.setSize(700,600);
		frame.setVisible(true);
	}
	public  void actionPerformed(ActionEvent ae) {
		if(ae.getSource()==close) {
			System.exit(0);
		}else if(ae.getSource()==back) {
			new orc1();
			frame.setVisible(false);
		}
	}
	public static double[] Solv(int cn,double objective[],double constraint[][]){
    	// Convert the problem into standard form 
		
        double[][] problem = new double[constraint.length + 1][objective.length + constraint.length + 1]; 
         
        for (int i = 0; i < constraint.length; i++) { 
            for (int j = 0; j < constraint[i].length; j++) { 
                problem[i][j] = constraint[i][j]; 
            } 
            problem[i][objective.length + i] = 1; 
            problem[i][problem[i].length - 1] = constraint[i][constraint[i].length - 1]; 
        } 
         
        for (int i = 0; i < objective.length; i++) { 
            problem[constraint.length][i] = objective[i] * -1; 
        } 
        problem[constraint.length][problem[0].length - 2] = 1; 
         
        // Solve the problem using the Simplex algorithm 
        while (true) { 
            int pivotColumn = Solver.findPivotColumn(problem); 
            if (pivotColumn == -1) { 
                break; 
            } 
            int pivotRow = Solver.findPivotRow(problem, pivotColumn); 
            if (pivotRow == -1) { 
                throw new RuntimeException("Problem is unbounded"); 
            } 
            Solver.pivot(problem, pivotRow, pivotColumn); 
        } 
         
        
        double[] solvedsa = new double[objective.length+1];
        solvedsa[objective.length]=problem[problem.length - 1][problem[0].length - 1];
        for (int i = 0; i < constraint[0].length - 1; i++) { 
            int rowWithOne = -1; 
            int columnWithValue = -1; 
            for (int j = 0; j < problem.length; j++) { 
                if (problem[j][i] == 1) { 
                    rowWithOne = j; 
                } 
                if (problem[j][i] != 0) { 
                    columnWithValue = i; 
                } 
            }
            
            if (rowWithOne != -1 && columnWithValue != -1) { 
                //System.out.println("x" + (columnWithValue + 1) + ": " + problem[rowWithOne][problem[0].length - 1]);
            	solvedsa[i]=problem[rowWithOne][problem[0].length - 1];
            } else { 
            	solvedsa[i]=0;
                //System.out.println("x" + (i + 1) + ": 0"); 
            } 
            
        }
        
        return solvedsa;
	}
	
	
	public static void main(String[] args) {
		new Solution(0.0,new double[0],new double[0],new double[0][0]);
	}

}