class Main {

    public void g1 ( Matrix2D matrix2D ) {
	
	Task1Exception te = new Task1Exception ( "" );
	String s = te.msg;

	int n = matrix2D.initialState ();
	n = matrix2D.terminalState ();
	n = matrix2D.nextState ( 0, 1 );

	try {
	    Language language = Task1.create ( matrix2D );
	    int [] input = new int [] { 0, 0, 1, 555 };
	    Boolean b = language.decide ( input ); }
	catch ( Exception e ) {} }
    
    public static void main ( String [] args ) {
	System.out.println ( "\n" );
	System.out.println ( "   Hello I'm the compilation tester for Task 1." );
	System.out.println ( "   WARNING: I only test if the submission can be compiled." ); 
	System.out.println ( "   WARNING: I do NOT test if the right functionality is implemented." );
	System.out.println ( "\n" ); 
        
        Language lang1 = Task1.create(new BinaryNumbers());
        int [] input = new int [] {};
        try {
            System.out.println(lang1.decide(input));
            System.out.println("Should be true");
            
        } catch (Task1Exception e){System.out.println("caught in main");}
        
        input = new int [] {0};
        try {
            System.out.println(lang1.decide(input));
            System.out.println("Should be true");
            
        } catch (Task1Exception e){System.out.println("caught in main");}
        
        input = new int [] {1};
        try {
            System.out.println(lang1.decide(input));
            System.out.println("Should be true");
            
        } catch (Task1Exception e){System.out.println("caught in main");}
        
        input = new int [] {0,0,1};
        try {
            System.out.println(lang1.decide(input));
            System.out.println("Should be true");
            
        } catch (Task1Exception e){System.out.println("caught in main");}
        
        Language lang2 = Task1.create(new BinaryNumbersDivisibleBy4());
        input = new int[] {};
        try {
            System.out.println(lang2.decide(input));
            System.out.println("Should be true");
            
        } catch (Task1Exception e){System.out.println("caught in main");}
        
        input = new int[] {0};
        try {
            System.out.println(lang2.decide(input));
            System.out.println("Should be true");
            
        } catch (Task1Exception e){System.out.println("caught in main");}
        
        input = new int[] {1};
        try {
            System.out.println(lang2.decide(input));
            System.out.println("Should be false");
            
        } catch (Task1Exception e){System.out.println("caught in main");}
        
        input = new int[] {0,0,1,1,1,1,0,1,1,0,0,0};
        try {
            System.out.println(lang2.decide(input));
            System.out.println("Should be true");
            
        } catch (Task1Exception e){System.out.println("caught in main");}
        
    }
}

class BinaryNumbers implements Matrix2D {
    private int [] [] fsaTable = new int [] [] { { 0, 0 } };	
    public int initialState () { return 0; };
    public int terminalState () { return 0; };
    public int nextState ( int currentState, int character ) {
	return fsaTable [ currentState ] [ character ]; } }

class BinaryNumbersDivisibleBy4 implements Matrix2D {
    private int [] [] fsaTable
        = new int [] [] { { 0, 1 }, { 2, 1 }, { 0, 1 } };
    public int initialState () { return 0; };
    public int terminalState () { return 0; };
    public int nextState ( int currentState, int character ) {
	return fsaTable [ currentState ] [ character ]; } }

