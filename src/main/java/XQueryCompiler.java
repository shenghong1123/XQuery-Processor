/**
 * Created by junya on 2/14/2017.
 */


import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;


public class XQueryCompiler {

    public static MyNodeList parse(String XPathExpression) {
        try {
            System.out.println("Parsing your input.");
            ANTLRInputStream input = new ANTLRInputStream(XPathExpression);
            XQueryLexer lexer = new XQueryLexer(input);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            XQueryParser parser = new XQueryParser(tokens);
            ParseTree tree = parser.query();
            XQueryBuilder XQuery = new XQueryBuilder();
            MyNodeList expr = XQuery.visit(tree);
            System.out.println("Parsing finished");
            return expr;

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error: " + e.getMessage());
        }
        return null;
    }
}
