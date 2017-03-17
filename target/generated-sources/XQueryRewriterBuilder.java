/**
 * Created by WSH on 3/6/17.
 */

import java.util.*;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeProperty;

public class XQueryRewriterBuilder extends XQueryBaseVisitor<MyNodeList> {

    boolean joinable = false;

    HashMap<String, Partition> pMap = new HashMap<>(); // var --> Partition

    ParseTreeProperty<String> content = new ParseTreeProperty<>(); // Store context as String

    HashMap<Partition, List<Partition>> joinMap = new HashMap<>();

    @Override
    public MyNodeList visitQuery(@NotNull XQueryParser.QueryContext ctx) {

        visit(ctx.getChild(0));
        String rewrite = content.get(ctx.getChild(0));
        content.put(ctx, rewrite);
        return null;
    }

    @Override
    public MyNodeList visitFLWRClause(@NotNull XQueryParser.FLWRClauseContext ctx) {

        HashMap<Partition, String> map = new HashMap<>();

        visit(ctx.forClause());
        visit(ctx.whereClause());
        visit(ctx.returnClause());

        if(joinMap.isEmpty()) {
            joinable = false;
            return null;
        }
        else joinable = true;

        String cur = new String();
        for(Partition p1: joinMap.keySet()) {
            List<Partition> joinList = joinMap.get(p1);
            for(Partition p2: joinList) {
                String s1 = map.containsKey(p1)? map.get(p1): p1.rewrite();
                String s2 = map.containsKey(p2)? map.get(p2): p2.rewrite();
                String att1 = p1.att(p2);
                String att2 = p2.att(p1);
                StringBuilder rewrite = new StringBuilder();
                rewrite.append("join(").append(System.getProperty("line.separator"));
                rewrite.append(s1 + "," + System.getProperty("line.separator"));
                rewrite.append(System.getProperty("line.separator"));
                rewrite.append(s2 + "," + System.getProperty("line.separator"));
                rewrite.append(att1 + "," + att2 + ")");
                map.put(p1, rewrite.toString());
                map.put(p2, rewrite.toString());
                cur = rewrite.toString();
            }
        }

        StringBuilder join = new StringBuilder();
        join.append("for $tuple in "  + cur);

        if(content.get(ctx.whereClause()).length() != 0) {
            join.append(System.getProperty("line.separator"));
            join.append("where " + content.get(ctx.whereClause()));
        }
        join.append(System.getProperty("line.separator"));
        join.append("return " + content.get(ctx.returnClause()));
        content.put(ctx, join.toString());

        return null;
    }

    @Override
    public MyNodeList visitForClause(@NotNull XQueryParser.ForClauseContext ctx) {

        List<XQueryParser.InContext> InList = ctx.in();

        for(XQueryParser.InContext in: InList) {
            String var = in.VAR().getText();
            XQueryParser.XqContext xq = in.xq();
            String text = xq.getText();

            // Assign this InClause to a existed partition
            if(text.startsWith("$")) {
                int end = text.indexOf("/");
                String pre = text.substring(0, end);
                if(pMap.containsKey(pre)) {
                    pMap.get(pre).InList.add(in); // Get the corresponding partition
                    pMap.put(var, pMap.get(pre));
                }
            }

            // Initialize a partition
            else {
                Partition p = new Partition();
                p.InList.add(in);
                pMap.put(var, p);
            }
        }
        return null;
    }

    @Override
    public MyNodeList visitWhereClause(@NotNull XQueryParser.WhereClauseContext ctx) {
        visit(ctx.cond());
        String rewrite = content.get(ctx.cond());
        content.put(ctx, rewrite);
        return null;
    }

    @Override
    public MyNodeList visitReturnClause(@NotNull XQueryParser.ReturnClauseContext ctx) {
        visit(ctx.xq());
        String rewrite = content.get(ctx.xq());
        content.put(ctx, rewrite);
        return null;
    }

    @Override
    public MyNodeList visitParentheseXq(@NotNull XQueryParser.ParentheseXqContext ctx) {
        visit(ctx. xq());
        String rewrite = content.get(ctx.xq());
        rewrite = "(" + rewrite + ")";
        content.put(ctx, rewrite);
        return null;
    }

    @Override
    public MyNodeList visitXqSSlashRp(@NotNull XQueryParser.XqSSlashRpContext ctx) {
        visit(ctx.xq());
        String rewrite = content.get(ctx.xq());
        rewrite = rewrite + "/" + ctx.rp().getText();
        content.put(ctx, rewrite);
        return null;
    }

    @Override
    public MyNodeList visitXqDSlashRp(@NotNull XQueryParser.XqDSlashRpContext ctx) {
        visit(ctx.xq());
        String rewrite = content.get(ctx.xq());
        rewrite = rewrite + "//" + ctx.rp().getText();
        content.put(ctx, rewrite);
        return null;
    }

    @Override
    public MyNodeList visitConstructor(@NotNull XQueryParser.ConstructorContext ctx) {
        String tagName = ctx.TAGNAME(0).getText();
        visit(ctx.xq());
        String rewrite = content.get(ctx.xq());
        rewrite = ("<" + tagName + ">{") + rewrite + ("}</" + tagName + ">");
        content.put(ctx, rewrite);
        return null;
    }
    @Override
    public MyNodeList visitCommaXq(@NotNull XQueryParser.CommaXqContext ctx) {
        visit(ctx.xq(0));
        visit(ctx.xq(1));
        String xq1 = content.get(ctx.xq(0));
        String xq2 = content.get(ctx.xq(1));
        String rewrite = xq1 + ", " + xq2;
        content.put(ctx, rewrite);
        return null;
    }

    @Override
    public MyNodeList visitSingleVariable(XQueryParser.SingleVariableContext ctx) {
        String var = ctx.VAR().getText();
        String rewrite = new String();
        if(pMap.get(var).join != null || pMap.get(var).joined != null) { // This variable join or is joined to another partition
            rewrite  = "$tuple/" + var.substring(1) + "/*";
        }
        content.put(ctx, rewrite);
        return null;
    }


    @Override
    public MyNodeList visitValueEqualCondition(XQueryParser.ValueEqualConditionContext ctx) {
        String xq1 = ctx.xq(0).getText();
        String xq2 = ctx.xq(1).getText();
        StringBuilder rewrite = new StringBuilder();

        // This condition means to join two partitions
        if(xq1.indexOf("$") != -1 && xq2.indexOf("$") != -1) {
            Partition p1 = pMap.get(xq1);
            Partition p2 = pMap.get(xq2);
            p1.join = p2;
            p2.joined = p1;
            if(p1.attribute.containsKey(p2)) p1.attribute.get(p2).add(xq1.substring(1));
            else {
                p1.attribute.put(p2, new ArrayList<>());
                p1.attribute.get(p2).add(xq1.substring(1));
            }
            if(p2.attribute.containsKey(p1)) p2.attribute.get(p1).add(xq2.substring(1));
            else {
                p2.attribute.put(p1, new ArrayList<>());
                p2.attribute.get(p1).add(xq2.substring(1));
            }
            //if(!joinList.contains(p1)) joinList.add(p1);
            if(joinMap.containsKey(p1) && joinMap.containsKey(p2)) {
                if(!joinMap.get(p1).contains(p2) && !joinMap.get(p2).contains(p1)) joinMap.get(p1).add(p2);
            }
            else if(joinMap.containsKey(p1) && !joinMap.containsKey(p2)) {
                if(!joinMap.get(p1).contains(p2)) joinMap.get(p1).add(p2);
            }
            else if(!joinMap.containsKey(p1) && joinMap.containsKey(p2)) {
                if(!joinMap.get(p2).contains(p1)) joinMap.get(p2).add(p1);
            }
            else {
                joinMap.put(p1, new ArrayList<>());
                joinMap.get(p1).add(p2);
            }
        }

        else pMap.get(xq1).CondList.add(ctx); // Assign this condition to corresponding partition
        content.put(ctx, rewrite.toString());
        return null;
    }

    public MyNodeList visitAndCondition(XQueryParser.AndConditionContext ctx) {
        visit(ctx.cond(0));
        visit(ctx.cond(1));
        String s1 = content.get(ctx.cond(0));
        String s2 = content.get(ctx.cond(1));
        StringBuilder rewrite = new StringBuilder();
        if(s1.length() != 0 && s2.length() != 0) {
            rewrite.append(s1).append(" and ").append(s2);
        }
        else if(s1.length() == 0) {
            rewrite.append(s2);
        }
        else if(s2.length() == 0) {
            rewrite.append(s1);
        }
        content.put(ctx, rewrite.toString());
        return null;
    }
}
