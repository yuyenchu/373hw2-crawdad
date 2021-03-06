package calculator.ast;

import calculator.errors.EvaluationError;
import calculator.interpreter.Environment;
import calculator.interpreter.Interpreter;
import datastructures.concrete.DoubleLinkedList;
import datastructures.interfaces.IList;
//import misc.exceptions.NotYetImplementedException;

/**
 * Note: this file is meant for the extra credit portion of this assignment
 * focused around adding a programming language to our calculator.
 *
 * If you choose to work on this extra credit, feel free to add additional
 * control flow handlers beyond the two listed here. Be sure to register
 * each new function inside the 'Calculator' class -- see line 59.
 */
public class ControlFlowManipulators {
    /**
     * Handles AST nodes corresponding to "randomlyPick(body1, body2)"
     *
     * Preconditions:
     *
     * - Receives an operation node with the name "randomlyPick" and two (arbitrary) children
     *
     * Postcondition:
     *
     * - This method will randomly decide to evaluate and return the result of either body1 or
     *   body2 with 50% probability. If body1 is interpreted, body2 is ignored completely and vice versa.
     */
    public static AstNode handleRandomlyPick(Environment env, AstNode wrapper) {
        AstNode body1 = wrapper.getChildren().get(0);
        AstNode body2 = wrapper.getChildren().get(1);

        Interpreter interp = env.getInterpreter();
        if (Math.random() < 0.5) {
            // Note: when implementing this method, we do NOT want to
            // manually recurse down either child: we instead want the calculator
            // to take back full control and evaluate whatever the body1 or body2
            // AST nodes might be. To do so, we use the 'Interpreter' object
            // available to us within the environment.
            return interp.evaluate(env, body1);
        } else {
            return interp.evaluate(env, body2);
        }
    }

    /**
     * Handles AST nodes corresponding to "if(cond, body, else)"
     *
     * Preconditions:
     *
     * - Receives an operation node with the name "if" and three children
     *
     * Postcondition:
     *
     * - If 'cond' evaluates to any non-zero number, interpret the "body" AST node and ignore the
     *   "else" node completely.
     * - Otherwise, evaluate the "else" node.
     * - In either case, return the result of whatever AST node you ended up interpreting.
     */
    public static AstNode handleIf(Environment env, AstNode wrapper) {
        //throw new NotYetImplementedException();
        if (wrapper.getChildren().size() != 3) {
            throw new EvaluationError("Wrong number of parameter");
        }
        IList<AstNode> children = new DoubleLinkedList<>();
        children.add(wrapper.getChildren().get(0));
        Interpreter interp = env.getInterpreter();
        if (interp.evaluate(env, new AstNode("toDouble", children)).getNumericValue() != 0) {
            return interp.evaluate(env, wrapper.getChildren().get(1));
        } else {
            return interp.evaluate(env, wrapper.getChildren().get(2));
        }
    }

    /**
     * Handles AST nodes corresponding to "repeat(times, body)"
     *
     * Preconditions:
     *
     * - Receives an operation node with the name "repeat" and two children
     * - The 'times' AST node is assumed to be some arbitrary AST node that,
     *   when interpreted, will also produce an integer result.
     *
     * Postcondition:
     *
     * - Repeatedly evaluates the given body the specified number of times.
     * - Returns the result of interpreting 'body' for the final time.
     */
    public static AstNode handleRepeat(Environment env, AstNode wrapper) {
        //throw new NotYetImplementedException();
        if (wrapper.getChildren().size() != 2) {
            throw new EvaluationError("Wrong number of parameter");
        }
        IList<AstNode> children = new DoubleLinkedList<>();
        children.add(wrapper.getChildren().get(0));
        Interpreter interp = env.getInterpreter();
        int times = (int) interp.evaluate(env, new AstNode("toDouble", children)).getNumericValue();
        AstNode result = wrapper.getChildren().get(1);
        for (int i = 0; i < times; i++) {
            interp.evaluate(env, result);
        }
        return result;
    }
    
    public static AstNode handleWhile(Environment env, AstNode wrapper) {
        if (wrapper.getChildren().size() != 2) {
            throw new EvaluationError("Wrong number of parameter");
        }
        IList<AstNode> children = new DoubleLinkedList<>();
        children.add(wrapper.getChildren().get(0));
        Interpreter interp = env.getInterpreter();
        AstNode result = wrapper.getChildren().get(1);
        while ((int) interp.evaluate(env, new AstNode("toDouble", children)).getNumericValue() != 0) {
            interp.evaluate(env, result);
        }
        return result;
    }
}
