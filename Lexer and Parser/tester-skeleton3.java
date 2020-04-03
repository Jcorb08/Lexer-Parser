import java.util.*;
import java.util.ArrayList;
import static java.util.Arrays.asList;

class Main {

    public void g () {
	
	SyntaxException se = new SyntaxException ( "" );
	String s = se.msg;
	Task3Exception te = new Task3Exception ( "" );
	s = te.msg;
	
	IntLiteral intLiteral = new IntLiteral ( 5 );
	int n = intLiteral.n;
	
	Block block = new Block ( new ArrayList<Exp> ( asList ( intLiteral ) ) );
	List <Exp> le = block.exps;
	
       	Skip skip = new Skip ();

	BlockExp blockExp = new BlockExp ( block );
	
    } 

    void f () {	
	try {
	    Parser p = Task3.create ();
	    List<Token> tl = null;
	    p.parse ( tl ); }
	catch ( Exception e ) {} }
    
    public static void main ( String [] args ) {
	System.out.println ( "\n" );
	System.out.println ( "   Hello I'm the compilation tester for Task 3." );
	System.out.println ( "   WARNING: I only test if the submission can be compiled." ); 
	System.out.println ( "   WARNING: I do NOT test if the right functionality is implemented." );
	System.out.println ( "\n" ); 
    
        Parser p = Task3.create();
        List<Token> test1 = new ArrayList<Token>();
        test1.add(new T_LeftCurlyBracket());
        test1.add(new T_Integer(2));
        test1.add(new T_Semicolon());
        test1.add(new T_Skip());
        test1.add(new T_Semicolon());
        test1.add(new T_Integer(4));
        test1.add(new T_RightCurlyBracket());
        try{
            Block b1 = p.parse(test1);
            System.out.println(Arrays.toString(b1.exps.toArray()));
            
        }catch (Exception e){
            System.out.println(e);
        }
        List<Token> test2 = new ArrayList<Token>();
        test2.add(new T_LeftCurlyBracket());
        test2.add(new T_Integer(1));
        test2.add(new T_Semicolon());
        test2.add(new T_LeftCurlyBracket());
        test2.add(new T_Integer(2));
        test2.add(new T_Semicolon());
        test2.add(new T_Skip());
        test2.add(new T_Semicolon());
        test2.add(new T_Integer(3));
        test2.add(new T_RightCurlyBracket());
        test2.add(new T_Semicolon());
        test2.add(new T_Integer(4));
        test2.add(new T_RightCurlyBracket());
        try{
            Block b2 = p.parse(test2);
            System.out.println(Arrays.toString(b2.exps.toArray()));
            
        }catch (Exception e){
            System.out.println(e);
        }
        List<Token> test3 = new ArrayList<Token>();
        test3.add(new T_LeftCurlyBracket());
        test3.add(new T_LeftCurlyBracket());
        test3.add(new T_LeftCurlyBracket());
        test3.add(new T_LeftCurlyBracket());
        test3.add(new T_Integer(17));
        test3.add(new T_RightCurlyBracket());
        test3.add(new T_RightCurlyBracket());
        test3.add(new T_RightCurlyBracket());
        test3.add(new T_RightCurlyBracket());
        try{
            Block b3 = p.parse(test3);
            System.out.println(Arrays.toString(b3.exps.toArray()));
            
        }catch (Exception e){
            System.out.println(e);
        }
        List<Token> test4 = new ArrayList<Token>();
        test4.add(new T_LeftCurlyBracket());
        
        try{
            Block b4 = p.parse(test4);
            System.out.println(Arrays.toString(b4.exps.toArray()));
            
        }catch (Exception e){
            System.out.println(e);
        }
    
    } 

    
}

