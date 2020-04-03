public class CodeGenTask2 implements Codegen {

    public CodeGenTask2(){} // constructor

    //Program case takes in list of declarations
    public String codegen ( Program p ) throws CodegenException{
        String returns = "";
        if(p.decls.size() >= 1) {
            returns += "jal "+ p.decls.get(0).id + "_entry\n";
            returns += "li a7 10\n";
            returns += "ecall\n";
            returns += "\n";
            for( Declaration d: p.decls){
                returns += declare(d);
            }
            return returns;
        }
        else {
            throw new CodegenException("codegen Program Size < 1");
        }
    }

    // fp is equivalent to s0 register
    //Declaration case takes in a declaration
    // The args should be on the stack, and the vars should be stored in mem
    //only code needed here is a pointer in mem and id for pointer
    private String declare(Declaration dec) throws CodegenException{
        int sizeAR = (2 + dec.numOfArgs) * 4;
        String returns = "";
        returns += dec.id + "_entry:\n";
        returns += "mv s0 sp\n";
        returns += "sw ra 0(sp)\n";
        returns += "addi sp sp -4\n";
        returns += expression(dec.body);
        returns += "lw ra 4(sp)\n";
        returns += "addi sp sp " + sizeAR + "\n";
        returns += "lw s0 0(sp)\n";
        returns += "jr ra\n";
        returns += "\n";
        return returns;
    }

    //Expression case takes in Exp
    //Case statement to work out which is which
    //Each case has own function
    private String expression(Exp e) throws CodegenException{
        if(e instanceof IntLiteral){return "li a0 " + ((IntLiteral) e).n + "\n"; }
        else if (e instanceof Variable){
            return "lw a0 "+ (4*((Variable) e).x)+ "(s0)\n";}
        else if (e instanceof If){ return ifStatement((If) e);}
        else if (e instanceof Binexp){return binExpression((Binexp) e); }
        else if (e instanceof Invoke){return invokeExpression((Invoke) e); }
        // case for (E)! ?? //else if(){} // not needed
        else if (e instanceof Skip){return "nop\n";}
        else if (e instanceof Seq){return seqExpression((Seq) e);}
        else if (e instanceof While){return whileExpression((While) e);}
        else if (e instanceof RepeatUntil){return repeatUntilExpression((RepeatUntil) e);}
        else if (e instanceof Assign){return assignExpression((Assign) e);}
        else { throw new CodegenException("Expression E invalid type");}

    }

    private String assignExpression(Assign a) throws CodegenException{
        String returns = "";
        int offset = 4*a.x;
        returns += expression(a.e);
        returns += "sw a0 " + offset + "(s0)\n";
        return returns;
    }

    private String repeatUntilExpression(RepeatUntil e) throws CodegenException{
        String returns = "";
        String loopLabel = new loopLabel().s;
        String exitLabel = new exitLabel().s;
        returns += loopLabel + ":\n";
        returns += expression(e.body);
        returns += boolExpression(e.l,e.r,e.comp);
        returns += exitLabel + "\n";
        returns += "b " + loopLabel + "\n";
        returns += exitLabel + ":\n";
        return returns;
    }

    private String whileExpression(While e) throws CodegenException{
        String returns = "";
        String loopLabel = new loopLabel().s;
        String thenLabel = new thenLabel().s;
        String exitLabel = new exitLabel().s;
        returns += loopLabel + ":\n";
        returns += boolExpression(e.l,e.r,e.comp);
        returns += thenLabel + "\n";
        returns += "b " + exitLabel + "\n";
        returns += thenLabel + ":\n";
        returns += expression(e.body);
        returns += "b " + loopLabel + "\n";
        returns += exitLabel + ":\n";
        return returns;
    }

    private String seqExpression(Seq e) throws CodegenException{
        String returns = "";
        // check could not be right ??
        returns += expression(e.l);
        returns += "lw t1 0(sp)\n";
        returns += "addi sp sp -4\n";
        returns += expression(e.r);
        returns += "sw a0 4(sp)\n";
        returns += "addi sp sp 4\n";
        return returns;
    }

    private String ifStatement(If e) throws CodegenException{
        String returns = "";
        String elseBranch = new elseLabel().s;
        String thenBranch = new thenLabel().s;
        String exitLabel = new exitLabel().s;
        returns += boolExpression(e.l,e.r,e.comp);
        returns += thenBranch + "\n";
        returns += elseBranch + ":\n";
        returns += expression(e.elseBody);
        returns += "b " + exitLabel + "\n";
        returns += thenBranch + ":\n";
        returns += expression(e.thenBody);
        returns += exitLabel + ":\n";
        return returns;
    }

    private String compExpression(Comp c) throws CodegenException{
        if (c instanceof Equals){
            return "beq t1 a0 ";
        }
        else if (c instanceof Less){
            return "blt t1 a0 ";
        }
        else if (c instanceof Greater){
            return "bgt t1 a0 ";
        }
        else if (c instanceof LessEq){
            return "ble t1 a0 ";
        }
        else if(c instanceof GreaterEq){
            return "bge t1 a0 ";
        }
        else {throw new CodegenException("Comp not of form == | < | > | <= | >=");}
    }

    private String binExpression(Binexp e) throws CodegenException {
        String returns = "";
        returns += expression(e.l);
        returns += "sw a0 0(sp)\n";
        returns += "addi sp sp -4\n";
        returns += expression(e.r);
        returns += "lw t1 4(sp)\n";
        if (e.binop instanceof Plus){
            returns += "add a0 t1 a0\n";
        }
        else if (e.binop instanceof Minus){
            returns += "sub a0 t1 a0\n";
        }
        else if (e.binop instanceof Times){
            returns += "mul a0 t1 a0\n";
        }
        else { throw new CodegenException("Binop not to form +/-"); }
        returns += "addi sp sp 4\n";
        return returns;
    }

    private String invokeExpression(Invoke e) throws CodegenException{
        String returns = "";
        returns += "sw s0 0(sp)\n";
        returns += "addi sp sp -4\n";
        for(int i = e.args.size()-1; i >= 0; i--){
            // could use google import List.reverse here with foreach loop
            returns += expression(e.args.get(i));
            returns += "sw a0 0(sp)\n";
            returns += "addi sp sp -4\n";
        }
        returns += "jal " + e.name + "_entry\n";
        return returns;
    }

    private String boolExpression(Exp l, Exp r, Comp c) throws CodegenException{
        String returns = "";
        returns += expression(l);
        returns += "sw a0 0(sp)\n";
        returns += "addi sp sp -4\n";
        returns += expression(r);
        returns += "lw t1 4(sp)\n";
        returns += "addi sp sp 4\n";
        returns += compExpression(c);
        return returns;
    }

}
