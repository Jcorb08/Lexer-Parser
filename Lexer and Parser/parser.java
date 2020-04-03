
import java.util.*;


class ParseLanguage implements Parser {
    
    public Block parse (List<Token> input) 
            throws SyntaxException, Task3Exception{
        Token[] inputs = new Token[input.size()];
        inputs = input.toArray(inputs);
        Block block =  new Block(new ArrayList<Exp>());
        try{
            block = blockCFG(block, inputs);
        }catch (SyntaxException e){
            throw new SyntaxException("Cannot be parsed! Rules not followed.");
        }catch (Exception e){
            throw new Task3Exception("Other error!");
        }
        return block;
    }
    
    private Block eCFG (Block block, Token[] input) throws SyntaxException{
        
        T_Integer intObj = new T_Integer(0);
        T_LeftCurlyBracket bracketObj = new T_LeftCurlyBracket();
        T_Skip skipObj = new T_Skip();
        
        if (input[0].getClass().equals(intObj.getClass())){
            T_Integer intLit = (T_Integer) input[0]; 
            block.exps.add(new IntLiteral(intLit.n));
            return block;
        }
        else if (input[0].getClass().equals(bracketObj.getClass())){
            Block innerBlock = new Block(new ArrayList<Exp>());
            innerBlock = blockCFG(innerBlock, input);
            block.exps.add(new BlockExp(innerBlock));
            return block;
        }
        else if (input[0].getClass().equals(skipObj.getClass())){
            block.exps.add(new Skip());
            return block;
        }
        else{
            throw new SyntaxException("E error. Not a Block, Integer or Skip!");
        }
    }
    
    
    
    private Block eneCFG (Block block, Token[] input) throws SyntaxException{
        
        T_Semicolon semiColon = new T_Semicolon();
        T_RightCurlyBracket rBracket = new T_RightCurlyBracket();
        T_LeftCurlyBracket lBracket = new T_LeftCurlyBracket();
        int semi = -1;
        int findingBracket = 0;
        
        for (int i = 0; i < input.length; i++) {
            // If found find another bracket
            if(input[i].getClass().equals(lBracket.getClass())){
                findingBracket += 1;
            }
            //Else if not trying to find left bracket and semicolon carry on as normal
            else if((input[i].getClass().equals(semiColon.getClass())) &&
                    (findingBracket == 0)){
                semi = i;
                break;
            } 
            //Else if it finds a right bracket it might not be the specific one 
            else if(input[i].getClass().equals(rBracket.getClass())){
                findingBracket -= 1;
            }
        }
        
        if(semi != -1){
            
            Token[] subInput = Arrays.copyOfRange(input, 0, semi);
            block = eCFG(block, subInput);
            input = Arrays.copyOfRange(input, semi+1, input.length);
            return eneCFG(block, input);
        }
        else{
            return eCFG(block, input);
        }
    }
    
    private Block blockCFG (Block block, Token[] input) throws SyntaxException{
        
        T_LeftCurlyBracket leftB = new T_LeftCurlyBracket();
        T_RightCurlyBracket rightB = new T_RightCurlyBracket();
        
        if ((input[0].getClass().equals(leftB.getClass())) && 
                (input[input.length-1].getClass().equals(rightB.getClass()))){
            // Get rid of the brackets - and take just the ENE between
            input = Arrays.copyOfRange(input, 1, input.length-1);
            return eneCFG(block, input);
        }
        else{
            throw new SyntaxException("Block error");
        }
               
    }
    
}