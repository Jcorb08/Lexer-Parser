
import java.util.*;

class LexerTask2 implements Lexer {
    
    private String input;
    private int currentPos;
    private final String stopperNotation = " ;()=<>,{}:+*-/\\";
    
    public List<Token> lex (String input) 
            throws LexicalException, Task2Exception {
        List<Token> listOfTokens = new ArrayList<Token>();
        currentPos = 0;
        // added to avoid nullpointererror!
        this.input = input + " ";
        try {
            listOfTokens = lexR(listOfTokens);
        } catch(LexicalException e){
                System.out.println("Input not valid");
                throw new LexicalException("Input not valid");
        }
        return listOfTokens;
    }
    
    private String integerR(String i){
        if(Character.isDigit(input.charAt(currentPos))){
            i = String.valueOf(input.charAt(currentPos));
            currentPos += 1;
            i = String.valueOf(i) + String.valueOf(integerR(i));
            return i;
        }
        return "";
    }
    
    private String identifierR(String s){
        if(!stopperNotation.contains(
                Character.toString(input.charAt(currentPos)))){
            s = "" + input.charAt(currentPos);
            currentPos += 1;
            s += identifierR(s);
            return s;
        } 
        return "";
    }
    
    private List<Token> lexR(List<Token> l) throws LexicalException {
        
        // Stop recursion due to end of input
        if(currentPos >= input.length()){
            return l;
        }
        // White space blank 32
        if((char) 32 == input.charAt(currentPos)){
            currentPos += 1;
            l = lexR(l);
        }
        // White space \n 10
        else if ("\n".equals(input.charAt(currentPos)+
                input.charAt(currentPos+1))){
            currentPos += 2;
            l = lexR(l);
        }
        // White space \f 12
        else if ("\f".equals(input.charAt(currentPos)+
                input.charAt(currentPos+1))){
            currentPos += 2;
            l = lexR(l);
        }
        // White space \r 13
        else if ("\r".equals(input.charAt(currentPos)+
                input.charAt(currentPos+1))){
            currentPos += 2;
            l = lexR(l);
        }
        // White space \t 9
        else if ("\t".equals(input.charAt(currentPos)+
                input.charAt(currentPos+1))){
            currentPos += 2;
            l = lexR(l);
        }
        // T_SemiColon - ;
        else if (";".charAt(0) == (input.charAt(currentPos))){
            l.add(new T_Semicolon());
            currentPos += 1;
            l = lexR(l);
        } 
        // T_LeftBracket (
        else if ("(".charAt(0) == (input.charAt(currentPos))){
            l.add(new T_LeftBracket());
            currentPos += 1;
            l = lexR(l);
        }
        // T_RightBracket )
        else if (")".charAt(0) == (input.charAt(currentPos))){
            l.add(new T_RightBracket());
            currentPos += 1;
            l = lexR(l);
        }
        // T_Comma ,
        else if (",".charAt(0) == (input.charAt(currentPos))){
            l.add(new T_Comma());
            currentPos += 1;
            l = lexR(l);
        }
        // T_LeftCurlyBracket {
        else if ("{".charAt(0) == (input.charAt(currentPos))){
            l.add(new T_LeftCurlyBracket());
            currentPos += 1;
            l = lexR(l);
        }
        // T_RightCurlyBracket }
        else if ("}".charAt(0) == (input.charAt(currentPos))){
            l.add(new T_RightCurlyBracket());
            currentPos += 1;
            l = lexR(l);
        }
        // T_Plus +
        else if ("+".charAt(0) == (input.charAt(currentPos))){
            l.add(new T_Plus());
            currentPos += 1;
            l = lexR(l);
        }
        // T_Times *
        else if ("*".charAt(0) == (input.charAt(currentPos))){
            l.add(new T_Times());
            currentPos += 1;
            l = lexR(l);
        }
        // T_Minus -
        else if ("-".charAt(0) == (input.charAt(currentPos))){
            l.add(new T_Minus());
            currentPos += 1;
            l = lexR(l);
        }
        // T_Div /
        else if ("/".charAt(0) == (input.charAt(currentPos))){
            l.add(new T_Div());
            currentPos += 1;
            l = lexR(l);
        }
        // T_Equal ==
        else if ("==".equals("" + input.charAt(currentPos)+
                input.charAt(currentPos+1))){
            l.add(new T_Equal());
            currentPos += 2;
            l = lexR(l);
        }
        // T_EqualDefines = 
        else if("=".charAt(0) == input.charAt(currentPos)){
            l.add(new T_EqualDefines());
            currentPos += 1;
            l = lexR(l);
        }
        // T_Assign := 
        else if(":=".equals("" + input.charAt(currentPos) + 
                input.charAt(currentPos+1))){
            l.add(new T_Assign());
            currentPos += 2;
            l = lexR(l);
        }
        // T_LessEq <= 
        else if("<=".equals("" + input.charAt(currentPos) + 
                input.charAt(currentPos+1))){
            l.add(new T_LessEq());
            currentPos += 2;
            l = lexR(l);
        }
        // T_GreaterEq >= 
        else if(">=".equals("" + input.charAt(currentPos) + 
                input.charAt(currentPos+1))){
            l.add(new T_GreaterEq());
            currentPos += 2;
            l = lexR(l);
        }
        // T_LessThan < 
        else if("<".charAt(0) == input.charAt(currentPos)){
            l.add(new T_LessThan());
            currentPos += 1;
            l = lexR(l);
        }
        // T_GreaterThan > 
        else if(">".charAt(0) == input.charAt(currentPos)){
            l.add(new T_GreaterThan());
            currentPos += 1;
            l = lexR(l);
        }
        // T_Integer [0-9]+
        else if(Character.isDigit(input.charAt(currentPos))){
            int currentInt = Integer.valueOf(integerR(""));
            l.add(new T_Integer(currentInt));
            l = lexR(l);
        }
        else if(Character.isLowerCase(input.charAt(currentPos))){
            String s = identifierR("");
            if(null == s){
                l.add(new T_Identifier(s));
            }
            else 
            switch (s) {
                case "def":
                    l.add(new T_Def());
                    break;
                case "skip":
                    l.add(new T_Skip());
                    break;
                case "if":
                    l.add(new T_If());
                    break;
                case "then":
                    l.add(new T_Then());
                    break;
                case "else":
                    l.add(new T_Else());
                    break;
                case "while":
                    l.add(new T_While());
                    break;
                case "do":
                    l.add(new T_Do());
                    break;
                case "repeat":
                    l.add(new T_Repeat());
                    break;
                case "until":
                    l.add(new T_Until());
                    break;
                case "break":
                    l.add(new T_Break());
                    break;
                case "continue":
                    l.add(new T_Continue());
                    break;
                default:
                    l.add(new T_Identifier(s));
                    break;
            }
            l = lexR(l);
        }
        else {
            throw new LexicalException("Input not valid");
        }
        return l; 
    }
            
}