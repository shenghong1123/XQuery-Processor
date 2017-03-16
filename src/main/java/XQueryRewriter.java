/**
 * Created by WSH on 3/6/17.
 */

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

public class XQueryRewriter {

    public static String rewrite(String XPathExpression) {
        try {
            System.out.println("Rewriting your input.");
            ANTLRInputStream input = new ANTLRInputStream(XPathExpression);
            XQueryLexer lexer = new XQueryLexer(input);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            XQueryParser parser = new XQueryParser(tokens);
            ParseTree tree = parser.query();
            XQueryRewriterBuilder XQuery = new XQueryRewriterBuilder();
            XQuery.visit(tree);
            if(XQuery.joinable == false) {
                System.out.println("No implicit join");
                return new String();
            }
            String rewrite = XQuery.content.get(tree);
            System.out.println("Rewriting finished");
            return rewrite;

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error: " + e.getMessage());
        }
        return null;
    }
}
