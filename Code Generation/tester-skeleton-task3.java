import javax.swing.*;
import java.util.List;
import java.util.ArrayList;
import static java.util.Arrays.asList;

class DoesItCompile {
	public void f() {
		try {
			Codegen codegen = Task3.create();

			Declaration declFirst = new Declaration("first",
					0,
					new IntLiteral(0));
			Declaration decl = new Declaration("f",
					3,
					new If(new Variable(1),
							new Less(),
							new Variable(2),
							new Variable(3),
							new IntLiteral(0)));
			Program p = new Program(new ArrayList<Declaration>(asList(declFirst, decl)));
			String result1 = codegen.codegen(p);
			CodegenException codegenException = new CodegenException("msg");
			String tmp = decl.id;
			int n = decl.numOfArgs;
			Exp exp = decl.body;
			IntLiteral intLiteral = new IntLiteral(5);
			n = intLiteral.n;
			Variable variable = new Variable(7);
			n = variable.x;
			Comp comp = new Equals();
			If ifAST = new If(intLiteral, comp, variable, intLiteral, variable);
			exp = ifAST.l;
			comp = ifAST.comp;
			exp = ifAST.r;
			exp = ifAST.thenBody;
			exp = ifAST.elseBody;
			Binop binop = new Plus();
			Binexp binexp = new Binexp(variable, binop, intLiteral);
			exp = binexp.l;
			comp = ifAST.comp;
			exp = binexp.r;
			Invoke invoke = new Invoke("name", new ArrayList<Exp>());
			tmp = invoke.name;
			List<Exp> l = invoke.args;
			While whileAST = new While(intLiteral, comp, variable, binexp);
			exp = whileAST.l;
			comp = whileAST.comp;
			exp = whileAST.r;
			exp = whileAST.body;
			RepeatUntil repeatAST = new RepeatUntil(binexp, intLiteral, comp, variable);
			exp = repeatAST.l;
			comp = repeatAST.comp;
			exp = repeatAST.r;
			exp = repeatAST.body;
			Assign assign = new Assign(7, exp);
			int x = assign.x;
			exp = assign.e;
			Seq seq = new Seq(exp, exp);
			exp = seq.l;
			exp = seq.r;
			//	    Break breakAST = new Break ();
			//	    Continue continueAST = new Continue ();
			comp = new Less();
			comp = new LessEq();
			comp = new Equals();
			comp = new Greater();
			comp = new GreaterEq();
			binop = new Plus();
			binop = new Minus();
			binop = new Times();
			binop = new Div();
		} catch (CodegenException e) {
			String tmp = e.msg;
		}
	}

}
class Main{
	public static void main(String[] args) throws CodegenException {
		Codegen codegen = Task3.create ();

//		Declaration declFirst = new Declaration ( "first",
////				0,
////				new IntLiteral ( 0 ) );
////		Declaration decl = new Declaration ( "f",
////				3,
////				new If ( new Variable ( 1 ),
////						new Less (),
////						new Variable ( 2 ),
////						new Variable( 3 ),
////						new IntLiteral ( 0 ) ) );
////		Program p = new Program ( new ArrayList<Declaration> ( asList ( declFirst, decl ) ) );
////		String result1 = codegen.codegen ( p );
////		System.out.println("result1: ");
////		System.out.println(result1);

		Declaration declFirst = new Declaration ( "first",
				0,
				new Invoke("f", asList(new IntLiteral(100))) );

		Declaration decl = new Declaration ("f",
				1,
				new While(
					new Variable(1),
					new GreaterEq(),
					new IntLiteral(0),
					new Seq(
						new RepeatUntil(
								new Assign(1,
										new Binexp(
											new Variable(1),
											new Div(),
											new IntLiteral(2)
										)),
								new Variable(1),
								new Greater(),
								new IntLiteral(25)
						),
						new Break()
					)
				)

				);
		Program p = new Program ( new ArrayList<Declaration> ( asList ( declFirst, decl ) ) );
		String result2 = codegen.codegen ( p );
		System.out.println("result2: ");
		System.out.println(result2);


	}
}


