import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

/**
 * Created by WSH on 3/14/17.
 */
public class XQueryJoinDetector {

    /**
     * Detect if query contains join keyword
     * @param XPathExpression
     * @return True: has join; False: no join
     */
    public static boolean detect(String XPathExpression) {
        try {
            System.out.println("Detecting your input.");
            ANTLRInputStream input = new ANTLRInputStream(XPathExpression);
            XQueryLexer lexer = new XQueryLexer(input);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            XQueryParser parser = new XQueryParser(tokens);
            ParseTree tree = parser.query();
            XQueryJoinDetectorBuilder XQuery = new XQueryJoinDetectorBuilder();
            XQuery.visit(tree);
            boolean result = XQuery.map.get(tree);
            if(result) System.err.println("No need to rewrite");
            else System.err.println("May need to rewrite");
            System.out.println("Detecting finished");
            return result;

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error: " + e.getMessage());
        }
        return false;
    }
}
