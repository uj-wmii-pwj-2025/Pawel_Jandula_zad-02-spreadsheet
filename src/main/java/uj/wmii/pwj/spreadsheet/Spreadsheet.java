package uj.wmii.pwj.spreadsheet;

public class Spreadsheet {
    private String[][] resultSheet;
    private String[][] originalSheet;

    public String[][] calculate(String[][] input) {
        if (input == null)
            return new String[0][0];

        int rows = input.length;
        int cols = input[0].length; 
        
        resultSheet = new String[rows][cols];
        originalSheet = input; 

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                SolveCell(i, j); 
            }
        }
        return resultSheet;
    }

    private String SolveCell(int i, int j) {
        if (resultSheet[i][j] != null) {
            return resultSheet[i][j];
        }
        String pom = originalSheet[i][j];
        String SolvedValue;
        if (pom.startsWith("=")) {
            SolvedValue = SolveExpresion(pom.substring(1));
        } 
        else if (pom.startsWith("$")) {
            SolvedValue = ReferenceToCell(pom.substring(1));
        } 
        else {
            SolvedValue = pom;
        }
        resultSheet[i][j] = SolvedValue;
        
        return SolvedValue;
    }

    private String ReferenceToCell(String ref) {
        int col = ref.charAt(0) - 'A';
        int row = Integer.parseInt(ref.substring(1))- 1;
        return SolveCell(row, col); 
    }

    private String SolveExpresion(String expr) {
        int openParen = expr.indexOf('(');
        int closeParen = expr.lastIndexOf(')');

    
        String WhichFormula = expr.substring(0, openParen);
        
        String paramsStr = expr.substring(openParen + 1, closeParen);

        String[] params = paramsStr.split(",");
        
        int val1 = Integer.parseInt(GradeToken(params[0]));
        int val2 = Integer.parseInt(GradeToken(params[1]));

        int result;
        
        switch (WhichFormula) {
            case "ADD":
                result = val1 + val2;
                break;
            case "SUB":
                result = val1 - val2;
                break;
            case "MUL":
                result = val1 * val2;
                break;
            case "DIV":
                result = val1 / val2; 
                break;
            case "MOD":
                result = val1 % val2;
                break;
            default:
                return "zła formuła";
        }
        
        return Integer.toString(result);
    }


    private String GradeToken(String token) {
        if (token.startsWith("$")) {
            return ReferenceToCell(token.substring(1));
        } 
        else {
            return token;
        }
    }
}