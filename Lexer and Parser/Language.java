// The class LangImplement that implements the interface Language
// Used also to decide if input string is in language

class LangImplement implements Language {
    
    public Matrix2D language;
    
    public LangImplement (Matrix2D matrix){
        language = matrix;
    }
    
    public Boolean decide (int [] input) throws Task1Exception {
        int currentState = language.initialState();
        Boolean accept = true;
        for(int i = 0; i < input.length; i++){
            currentState = language.nextState(currentState, input[i]);
        }   
        if (currentState != language.terminalState()){
            accept = false;
        }
        return accept;
    }   
    
} 