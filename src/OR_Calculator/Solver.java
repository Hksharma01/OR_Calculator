package OR_Calculator;

import java.util.Arrays;

import javax.swing.JTextField;

public class Solver {
	JTextField[] textFields;
	JTextField[][] textFieldscon;
	int m;
	double orgsol;
	
	Solver(int m,JTextField[] textFields,JTextField[][] textFieldscon){
		this.m = m;
		this.textFields = textFields;
		this.textFieldscon = textFieldscon;
		// Define the objective function coefficients 
    	double[] objectiveFunction = new double[textFields.length];
    	if(m==0) {
    		try {
    		for (int i = 0; i < textFields.length; i++) {
    		    
    		    String input = textFields[i].getText(); // Get the text from the text field
    	        double value;

    	        
    	            value = Double.parseDouble(input); // Convert the input to a double
    	            objectiveFunction[i] = value;
    	        }
    		}catch (NumberFormatException e) {
    	            System.out.println("Invalid input. Please enter a valid numeric value.");
    		}
    	}else if(m==1){
    		try {
        		for (int i = 0; i < textFields.length; i++) {
        		    
        		    String input = textFields[i].getText(); // Get the text from the text field
        	        double value;

        	        
        	            value = Double.parseDouble(input); // Convert the input to a double
        	            objectiveFunction[i] = -value;
        	        }
        		}catch (NumberFormatException e) {
        	            System.out.println("Invalid input. Please enter a valid numeric value.");
        		}
    	}
        // Define the constraint coefficients and constants
        double[][] constraints = new double[textFieldscon.length][textFieldscon[0].length];
        for (int i = 0; i < constraints.length; i++) {
            for (int j = 0; j < constraints[i].length; j++) {
             try {	
            	 String input = textFieldscon[i][j].getText(); // Get the text from the text field
    	        double value;
    	        value = Double.parseDouble(input); // Convert the input to a double
    	        constraints[i][j] = value;
    	        }
    		catch (NumberFormatException e) {
    	            System.out.println("Invalid input. Please enter a valid numeric value.");
    		}
        }
        }
    	
        // Convert the problem into standard form 
        double[][] problem = new double[constraints.length + 1][objectiveFunction.length + constraints.length + 1]; 
         
        for (int i = 0; i < constraints.length; i++) { 
            for (int j = 0; j < constraints[i].length; j++) { 
                problem[i][j] = constraints[i][j]; 
            } 
            problem[i][objectiveFunction.length + i] = 1; 
            problem[i][problem[i].length - 1] = constraints[i][constraints[i].length - 1]; 
        } 
         
        for (int i = 0; i < objectiveFunction.length; i++) { 
            problem[constraints.length][i] = objectiveFunction[i] * -1; 
        } 
        problem[constraints.length][problem[0].length - 2] = 1; 
         
        // Solve the problem using the Simplex algorithm 
        while (true) { 
            int pivotColumn = findPivotColumn(problem); 
            if (pivotColumn == -1) { 
                break; 
            } 
            int pivotRow = findPivotRow(problem, pivotColumn); 
            if (pivotRow == -1) { 
                throw new RuntimeException("Problem is unbounded"); 
            } 
            pivot(problem, pivotRow, pivotColumn); 
        } 
         
        
        orgsol = problem[problem.length - 1][problem[0].length - 1];
        
        double[] orgvar = new double[objectiveFunction.length];
        for (int i = 0; i < constraints[0].length - 1; i++) { 
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
                orgvar[i] = problem[rowWithOne][problem[0].length - 1];
            	//System.out.println("x" + (columnWithValue + 1) + ": " + problem[rowWithOne][problem[0].length - 1]); 
            } else {
            	orgvar[i] = 0;
                //System.out.println("x" + (i + 1) + ": 0"); 
            } 
        } 
        new Solution(orgsol,orgvar,objectiveFunction,constraints);
        
	}
    public static int findPivotColumn(double[][] problem) { 
        int pivotColumn = -1; 
        double min = 0; 
        for (int i = 0; i < problem[problem.length - 1].length - 2; i++) { 
            if (problem[problem.length - 1][i] < min) { 
                min = problem[problem.length - 1][i]; 
                pivotColumn = i; 
            } 
        } 
        return pivotColumn; 
    } 
    public static int findPivotRow(double[][] problem, int pivotColumn) { 
        int pivotRow = -1; 
        double min = Double.MAX_VALUE; 
        for (int i = 0; i < problem.length - 1; i++) { 
            if (problem[i][pivotColumn] > 0) { 
                double ratio = problem[i][problem[i].length - 1] / problem[i][pivotColumn]; 
                if (ratio < min) { 
                    min = ratio; 
                    pivotRow = i; 
                } 
            } 
        } 
        return pivotRow; 
    } 
    public static void pivot(double[][] problem, int pivotRow, int pivotColumn) { 
        // Divide the pivot row by the pivot element 
        double pivotElement = problem[pivotRow][pivotColumn]; 
        for (int i = 0; i < problem[pivotRow].length; i++) { 
            problem[pivotRow][i] /= pivotElement; 
        } 
        // Subtract the pivot row from all other rows 
        for (int i = 0; i < problem.length; i++) { 
            if (i != pivotRow) { 
                double factor = problem[i][pivotColumn]; 
                for (int j = 0; j < problem[i].length; j++) {
problem[i][j] -= factor * problem[pivotRow][j]; 
                } 
            } 
        } 
        
	}
    

    	
    	
    
	public static void main(String[] args) {
		
		new Solver(0,new JTextField[0],new JTextField[0][0] );
	}
}