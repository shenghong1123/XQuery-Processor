/**
 * Created by WSH on 3/6/17.
 */
import java.util.*;

public class Partition {

    public List<XQueryParser.InContext> InList;
    public List<XQueryParser.CondContext> CondList; // Filter before join
    public MyNodeList tupleList;
    public Partition join; // another partition this join with
    public Partition joined;
    public HashMap<Partition, List<String>> attribute; // Partition --> attribute list
    public Partition parent;
    public String r;
    public int rank;

    public Partition() {
        InList = new ArrayList<>();
        CondList = new ArrayList();
        tupleList = new MyNodeList();
        join = null;
        joined = null;
        attribute = new HashMap<>();
        parent = this;
        r = null;
        rank = 0;
    }
    public String rewrite() {
        if(this.r != null) return r;
        StringBuilder rewrite = new StringBuilder();
        rewrite.append("     for ");
        List<String> varList = new ArrayList<>();
        for(int i = 0; i < InList.size(); i++) {
            XQueryParser.InContext in = InList.get(i);
            String var = in.VAR().getText();
            String xq = in.xq().getText();
            varList.add(var);
            if(i == 0 && i != InList.size() - 1) rewrite.append(var).append(" in ").append(xq).append(", ").append(System.getProperty("line.separator"));
            else if(i == 0 && i == InList.size() - 1) rewrite.append(var).append(" in ").append(xq).append(System.getProperty("line.separator"));
            else if(i != InList.size() - 1) rewrite.append("         " + var).append(" in ").append(xq).append(", ").append(System.getProperty("line.separator"));
            else rewrite.append("         " + var).append(" in ").append(xq).append(System.getProperty("line.separator"));

        }
        if(CondList.size() != 0) {
            rewrite.append("     where ");
            for(int i = 0; i < CondList.size(); i++) {
                XQueryParser.CondContext cond = CondList.get(i);
                String s = condRewriter(cond);
                if(i != CondList.size() - 1) rewrite.append(s).append(" and ");
                else rewrite.append(s);
            }
            rewrite.append(System.getProperty("line.separator"));
        }

        rewrite.append("     return ").append("<tuple>{");
        for(int i = 0; i < varList.size(); i++) {
            String var = varList.get(i);
            String att = var.substring(1);
            rewrite.append("<" + att + ">{").append(var).append("}</" + att + ">");
            if(i != varList.size() - 1) rewrite.append(",");
        }
        rewrite.append("}</tuple>");
        this.r = rewrite.toString();
        return rewrite.toString();
    }

    public String att(Partition p) {
        List<String> attList = this.attribute.get(p);
        StringBuilder rewrite = new StringBuilder();
        rewrite.append("[");
        if(attList != null) {
            for(int i = 0; i < attList.size(); i++) {
                rewrite.append(attList.get(i));
                if(i != attList.size() - 1) rewrite.append(", ");
            }
        }
        rewrite.append("]");
        return rewrite.toString();
    }

    public String condRewriter(XQueryParser.CondContext cond) {
        String s1 = ((XQueryParser.ValueEqualConditionContext)cond).xq(0).getText();
        String s2 = ((XQueryParser.ValueEqualConditionContext)cond).xq(1).getText();
        String res = s1 + " eq " + s2;

        return res;
    }

}
