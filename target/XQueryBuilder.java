/**
 * Created by WSH on 2/17/17.
 * Team 13&14
 */

import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeProperty;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import java.util.HashMap;
import java.util.List;


public class XQueryBuilder extends XQueryBaseVisitor<MyNodeList> {
    Node dummy;

    ParseTreeProperty<MyNodeList> ctxMap = new ParseTreeProperty<>();

    //Key: variable name; Value: NodeList in a FLWRClause
    HashMap<String, MyNodeList> varMap = new HashMap<>();

    //Key: variable name; Value: filtered result in a FLWRClause
    HashMap<String, MyNodeList> resMap = new HashMap<>();

    //Key: InClause; Value: index
    HashMap<XQueryParser.InContext, Integer> inMap = new HashMap<>();


    @Override
    public MyNodeList visitQuery(@NotNull XQueryParser.QueryContext ctx) {
        //System.out.println("Visit the Query");
        return visit(ctx.getChild(0));
    }

    @Override
    public MyNodeList visitFLWRClause(@NotNull XQueryParser.FLWRClauseContext ctx) {
        //System.out.println("Visit the FLWRClause");
        HashMap<String, MyNodeList> varTemp = new HashMap<>(varMap); // backup variable map and result map
        HashMap<String, MyNodeList> resTemp = new HashMap<>(resMap);
        MyNodeList curNodeList = ctxMap.get(ctx.getParent());
        ctxMap.put(ctx, curNodeList);
        ctxMap.put(ctx.returnClause(), new MyNodeList());
        visit(ctx.forClause());
        MyNodeList result = ctxMap.get(ctx.returnClause());
        ctxMap.put(ctx, result);
        varMap = varTemp; // recover variable map and result map
        resMap = resTemp;
        return result;
    }

    @Override
    public MyNodeList visitForClause(@NotNull XQueryParser.ForClauseContext ctx) {
        //System.out.println("Visit the ForClause");
        List<XQueryParser.InContext> inList = ctx.in();
        for(int i = 0; i < inList.size(); i++) inMap.put(inList.get(i), i); // Record each nested for loop and their index
        visit(ctx.in(0));
        return new MyNodeList();
    }

    @Override
    public MyNodeList visitIn(@NotNull XQueryParser.InContext ctx) {
        //System.out.println("Visit the In");
        String Var = ctx.VAR().getText();
        MyNodeList list = visit(ctx.xq());
        XQueryParser.InContext nextIn = ((XQueryParser.ForClauseContext) ctx.getParent()).in(inMap.get(ctx) + 1);

        for (Node node : list) {
            varMap.put(Var, new MyNodeList(node));

            if (nextIn != null) {
                //System.out.println("Visit the inner In");
                visit(nextIn);

            }
            else {
                XQueryParser.LetClauseContext let =
                        ((XQueryParser.FLWRClauseContext) ctx.getParent().getParent()).letClause();

                if(let != null) visit(let);

                XQueryParser.WhereClauseContext where =
                        ((XQueryParser.FLWRClauseContext) ctx.getParent().getParent()).whereClause();

                if(where == null || (where != null && visit(where).getLength() != 0)) {
                    XQueryParser.ReturnClauseContext ret =
                            ((XQueryParser.FLWRClauseContext) ctx.getParent().getParent()).returnClause();
                    visit(ret);

                }
            }

        }

        return new MyNodeList();
    }

    @Override
    public MyNodeList visitLetClause(@NotNull XQueryParser.LetClauseContext ctx) {
        //System.out.println("Visit the LetClause");
        for(int i = 0; i < ctx.xq().size(); i++) {
            String var = ctx.VAR(i).getText();
            MyNodeList result = visit(ctx.xq(i));
            varMap.put(var, result);
        }
        return new MyNodeList();
    }
    @Override
    public MyNodeList visitSingleLetClause(@NotNull XQueryParser.SingleLetClauseContext ctx) {
        //System.out.println("Visit SingleLetClause");
        MyNodeList curNodeList = ctxMap.get(ctx.getParent());
        ctxMap.put(ctx, curNodeList);
        visit(ctx.letClause());
        MyNodeList result = visit(ctx.xq());
        ctxMap.put(ctx, result);
        return result;
    }

    @Override
    public MyNodeList visitWhereClause(@NotNull XQueryParser.WhereClauseContext ctx) {
        //System.out.println("Visit the WhereClause");
        MyNodeList condResult = visit(ctx.cond());
        if(condResult != null && condResult.getLength() != 0) return condResult;
        else return new MyNodeList();
    }

    @Override
    public MyNodeList visitReturnClause(@NotNull XQueryParser.ReturnClauseContext ctx) {
        //System.out.println("Visit the ReturnClause");
        if(ctxMap.get(ctx) == null) ctxMap.put(ctx, new MyNodeList());
        MyNodeList curNodeList = ctxMap.get(ctx);
        MyNodeList result = XMLBasicOperator.concat(curNodeList, visit(ctx.xq()));
        ctxMap.put(ctx, result);
        //for(Node n: result) System.err.println(n.getNodeName());
        return result;
    }

    @Override
    public MyNodeList visitSingleVariable(@NotNull XQueryParser.SingleVariableContext ctx) {
        //System.out.println("Visit the Single Variable");
        MyNodeList result = varMap.get(ctx.VAR().getText());
        ctxMap.put(ctx, result);
        return result;
    }

    @Override
    public MyNodeList visitParentheseXq(@NotNull XQueryParser.ParentheseXqContext ctx) {
        //System.out.println("Visit the (xq)");
        MyNodeList curNodeList = ctxMap.get(ctx.getParent());
        ctxMap.put(ctx, curNodeList);
        MyNodeList result = visit(ctx.xq());
        ctxMap.put(ctx, result);
        return result;
    }

    @Override
    public MyNodeList visitCommaXq(@NotNull XQueryParser.CommaXqContext ctx) {
        //System.out.println("Visit the xq,xq");
        MyNodeList curNodeList = ctxMap.get(ctx.getParent());
        ctxMap.put(ctx, curNodeList);
        MyNodeList leftResult = visit(ctx.xq(0));
        MyNodeList rightResult = visit(ctx.xq(1));
        MyNodeList result = XMLBasicOperator.concat(leftResult, rightResult);
        ctxMap.put(ctx, result);
        return result;
    }

    @Override
    public MyNodeList visitXqSSlashRp(@NotNull XQueryParser.XqSSlashRpContext ctx) {
        //System.out.println("Visit the xq/rp");
        MyNodeList curNodeList = ctxMap.get(ctx.getParent());
        ctxMap.put(ctx, curNodeList);
        MyNodeList leftResult = visit(ctx.xq());
        ctxMap.put(ctx, leftResult);
        MyNodeList rightResult = visit(ctx.rp());
        MyNodeList result = XMLBasicOperator.deleteDuplicate(rightResult);
        ctxMap.put(ctx, result);
        return result;
    }

    @Override
    public MyNodeList visitXqDSlashRp(@NotNull XQueryParser.XqDSlashRpContext ctx) {
        //System.out.println("Visit the xq//rp");
        MyNodeList curNodeList = ctxMap.get(ctx.getParent());
        ctxMap.put(ctx, curNodeList);
        MyNodeList leftResult = visit(ctx.xq());
        leftResult = XMLBasicOperator.concat(leftResult, XMLBasicOperator.getAllDescendant(leftResult));
        ctxMap.put(ctx, leftResult);
        MyNodeList rightResult = visit(ctx.rp());
        MyNodeList result = XMLBasicOperator.deleteDuplicate(rightResult);
        ctxMap.put(ctx, result);
        return result;
    }

    @Override
    public MyNodeList visitConstructor(@NotNull XQueryParser.ConstructorContext ctx) {
        //System.out.println("Visit constructor");
        MyNodeList curNodeList = ctxMap.get(ctx.getParent());
        ctxMap.put(ctx, curNodeList);
        String tagName = ctx.TAGNAME(0).getText();
        MyNodeList list = visit(ctx.xq());
        if(list.getLength() != 0) {
            MyNodeList result = XMLBasicOperator.makeElement(list, tagName);
            ctxMap.put(ctx, result);
            return result;
        }
        else {
            MyNodeList result = new MyNodeList();
            ctxMap.put(ctx, result);
            return result;
        }
    }

    @Override
    public MyNodeList visitValueEqualCondition(@NotNull XQueryParser.ValueEqualConditionContext ctx) {
        //System.out.println("Visit the xq=xq");
        MyNodeList leftResult = visit(ctx.xq(0));
        if(ctx.xq(1) instanceof XQueryParser.StringConstantContext) {
            String content = ctx.xq(1).getText();
            content = content.substring(1, content.length() - 1);
            for(Node n: leftResult) {
                if(n.getNodeValue().equals(content)) {
                    return new MyNodeList(dummy);
                }
            }
            return new MyNodeList();
        }
        MyNodeList rightResult = visit(ctx.xq(1));
        if(XMLBasicOperator.existValueEqual(leftResult, rightResult)) return new MyNodeList(dummy);
        else return new MyNodeList();

    }

    @Override
    public MyNodeList visitIdEqualCondition(@NotNull XQueryParser.IdEqualConditionContext ctx) {
        //System.out.println("Visit the xq==xq");
        MyNodeList curNodeList = ctxMap.get(ctx.getParent());
        ctxMap.put(ctx, curNodeList);
        MyNodeList leftResult = visit(ctx.xq(0));
        MyNodeList rightResult = visit(ctx. xq(1));
        if(XMLBasicOperator.existIdEqual(leftResult, rightResult)) {
            return new MyNodeList(dummy);
        }
        else return new MyNodeList();
    }

    @Override
    public MyNodeList visitEmptyCondition(@NotNull XQueryParser.EmptyConditionContext ctx) {
        //System.out.println("Visit the empty(xq)");
        MyNodeList curNodeList = ctxMap.get(ctx.getParent());
        ctxMap.put(ctx, curNodeList);
        MyNodeList result = visit(ctx.xq());
        if(result.getLength() == 0) return new MyNodeList(dummy);
        else return new MyNodeList();
    }

    @Override
    public MyNodeList visitSomeCondition(@NotNull XQueryParser.SomeConditionContext ctx) {
        //System.out.println("Visit the some()");
        HashMap<String, MyNodeList> varTemp = new HashMap<>(varMap);
        HashMap<String, MyNodeList> resTemp = new HashMap<>(resMap);
        MyNodeList curNodeList = ctxMap.get(ctx.getParent());
        ctxMap.put(ctx, curNodeList);
        for(int i = 0; i < ctx.xq().size(); i++) {
            MyNodeList r = visit(ctx.xq(i));
            varMap.put(ctx.VAR(i).getText(), r);
            //System.err.println(ctx.VAR(i).getText());
            //for(Node n: visit(ctx.xq(i))) System.err.println(n.getNodeName());
        }
        //System.err.println(varMap.get(ctx.VAR(0).getText()).getLength());
        MyNodeList result = visit(ctx.cond());

        ctxMap.put(ctx, result);
        varMap = varTemp;
        resMap = resTemp;
        return result;
    }

    @Override
    public MyNodeList visitParentheseCondition(@NotNull XQueryParser.ParentheseConditionContext ctx) {
        //System.out.println("Visit the (cond)");
        MyNodeList result = visit(ctx.cond());
        ctxMap.put(ctx, result);
        return result;
    }

    @Override
    public MyNodeList visitOrCondition(@NotNull XQueryParser.OrConditionContext ctx) {
        //System.out.println("Visit the cond or cond");
        MyNodeList curNodeList = ctxMap.get(ctx.getParent());
        ctxMap.put(ctx, curNodeList);
        MyNodeList leftResult = visit(ctx.cond(0));
        MyNodeList rightResult = visit(ctx.cond(1));
        if(leftResult.getLength() != 0 || rightResult.getLength() != 0) return XMLBasicOperator.concat(leftResult, rightResult);
        else {
            MyNodeList result = new MyNodeList();
            ctxMap.put(ctx, result);
            return result;
        }
    }

    @Override
    public MyNodeList visitAndCondition(@NotNull XQueryParser.AndConditionContext ctx) {
        //System.out.println("Visit the cond and cond");
        MyNodeList curNodeList = ctxMap.get(ctx.getParent());
        ctxMap.put(ctx, curNodeList);
        MyNodeList leftResult = visit(ctx.cond(0));
        MyNodeList rightResult = visit(ctx.cond(1));
        if(leftResult.getLength() != 0 && rightResult.getLength() != 0) return new MyNodeList(dummy);
        else return new MyNodeList();
    }

    @Override
    public MyNodeList visitNotCondition(@NotNull XQueryParser.NotConditionContext ctx) {
        //System.out.println("Visit the not cond");
        MyNodeList curNodeList = ctxMap.get(ctx.getParent());
        ctxMap.put(ctx, curNodeList);
        MyNodeList result = visit(ctx.cond());
        if(result.getLength() == 0) return new MyNodeList(dummy);

        else return new MyNodeList();
    }

    @Override
    public MyNodeList visitAbsolutePath(@NotNull XQueryParser.AbsolutePathContext ctx) {
        return visit(ctx.getChild(0));
    }

    @Override
    public MyNodeList visitSSlashAp(@NotNull XQueryParser.SSlashApContext ctx) {
        //System.out.println("Visit SSlashAP");
        String filename = ctx.FILENAME().getText();
        filename = filename.substring(5, filename.length() - 2);
        filename = "/Users/WSH/Desktop/" + filename;
        Document doc = XMLBasicOperator.DOMParser(filename);
        MyNodeList root = XMLBasicOperator.getRootElement(doc);
        dummy = root.item(0);
        ctxMap.put(ctx, root);
        return visit(ctx.rp());
    }

    @Override
    public MyNodeList visitDSlashAp(@NotNull XQueryParser.DSlashApContext ctx) {
        //System.out.println("Visit DSlashAp");
        String filename = ctx.FILENAME().getText();
        filename = filename.substring(5, filename.length() - 2);
        filename = "/Users/WSH/Desktop/" + filename;
        Document doc = XMLBasicOperator.DOMParser(filename);
        MyNodeList root = XMLBasicOperator.getRootElement(doc);
        dummy = root.item(0);
        MyNodeList descendant = XMLBasicOperator.getAllDescendant(root);
        MyNodeList result = XMLBasicOperator.concat(root, descendant);
        ctxMap.put(ctx, result);
        MyNodeList newResult = visit(ctx.rp());
        return newResult;
    }

    @Override
    public MyNodeList visitParentheseRp(@NotNull XQueryParser.ParentheseRpContext ctx) {
        //System.out.println("Visit ParentheseRp");
        MyNodeList curNodeList = ctxMap.get(ctx.getParent());
        ctxMap.put(ctx, curNodeList);
        MyNodeList result = visit(ctx.rp());
        ctxMap.put(ctx, result);
        return result;
    }

    @Override
    public MyNodeList visitSSlashRp(@NotNull XQueryParser.SSlashRpContext ctx) {
        //System.out.println("Visit SSlashRp");
        MyNodeList curNodeList = ctxMap.get(ctx.getParent());
        ctxMap.put(ctx, curNodeList);
        //System.out.println(curNodeList.getLength());
        MyNodeList leftResult = visit(ctx.rp(0));
        ctxMap.put(ctx, leftResult);
        //System.out.println(leftResult.getLength());
        MyNodeList rightResult = visit(ctx.rp(1));
        MyNodeList result = XMLBasicOperator.deleteDuplicate(rightResult);
        ctxMap.put(ctx, result);
        return result;
    }

    @Override
    public MyNodeList visitDSlashRp(@NotNull XQueryParser.DSlashRpContext ctx) {
        //System.out.println("Visit DSlashRp");
        MyNodeList curNodeList = ctxMap.get(ctx.getParent());
        ctxMap.put(ctx, curNodeList);
        MyNodeList leftResult = visit(ctx.rp(0));
        leftResult = XMLBasicOperator.concat(leftResult, XMLBasicOperator.getAllDescendant(leftResult));
        ctxMap.put(ctx, leftResult);
        MyNodeList rightResult = visit(ctx.rp(1));
        MyNodeList result = XMLBasicOperator.deleteDuplicate(rightResult);
        ctxMap.put(ctx, result);
        return result;
    }

    @Override
    public MyNodeList visitCommaRp(@NotNull XQueryParser.CommaRpContext ctx) {
        //System.out.println("Visit CommaRp");
        MyNodeList curNodeList = ctxMap.get(ctx.getParent());
        ctxMap.put(ctx, curNodeList);
        MyNodeList leftResult = visit(ctx.getChild(0));
        MyNodeList rightResult = visit(ctx.getChild(2));
        MyNodeList result = XMLBasicOperator.concat(leftResult, rightResult);
        ctxMap.put(ctx, result);
        return result;
    }

    @Override
    public MyNodeList visitRpWithFilter(@NotNull XQueryParser.RpWithFilterContext ctx) {
        //System.out.println("Visit RpWithFilter");
        MyNodeList curNodeList = ctxMap.get(ctx.getParent());
        ctxMap.put(ctx, curNodeList);
        MyNodeList leftResult = visit(ctx.rp());
        ctxMap.put(ctx, leftResult);
        MyNodeList result = visit(ctx.f());
        if(result.getLength() == 0) {
            ctxMap.put(ctx, result);
            return result;
        }
        else return leftResult;
    }

    @Override
    public MyNodeList visitSDot(@NotNull XQueryParser.SDotContext ctx) {
        //System.out.println("Visit SDot");
        MyNodeList curNodeList = ctxMap.get(ctx.getParent());
        ctxMap.put(ctx, curNodeList);
        return curNodeList;
    }

    @Override
    public MyNodeList visitDDot(@NotNull XQueryParser.DDotContext ctx) {
        //System.out.println("Visit DDot");
        MyNodeList curNodeList = ctxMap.get(ctx.getParent());
        MyNodeList result = XMLBasicOperator.getAllParents(curNodeList);
        ctxMap.put(ctx, result);
        return result;
    }

    @Override
    public MyNodeList visitTagName(@NotNull XQueryParser.TagNameContext ctx) {
        //System.out.println("Visit TagName");
        MyNodeList curNodeList = ctxMap.get(ctx.getParent());
        //System.out.println(curNodeList.getLength());
        String tagName = ctx.TAGNAME().getText();
        MyNodeList result = XMLBasicOperator.getChildrenByTag(curNodeList, tagName);
        ctxMap.put(ctx, result);
        return result;
    }

    @Override
    public MyNodeList visitText(@NotNull XQueryParser.TextContext ctx) {
        //System.out.println("Visit Text");
        MyNodeList curNodeList = ctxMap.get(ctx.getParent());
        MyNodeList result = XMLBasicOperator.getText(curNodeList);
        ctxMap.put(ctx, result);
        return result;
    }

    @Override
    public MyNodeList visitWildCard(@NotNull XQueryParser.WildCardContext ctx) {
        //System.out.println("Visit WildCard");
        MyNodeList curNodeList = ctxMap.get(ctx.getParent());
        MyNodeList result = XMLBasicOperator.getAllChildren(curNodeList);
        ctxMap.put(ctx, result);
        return result;
    }

    @Override
    public MyNodeList visitAttName(@NotNull XQueryParser.AttNameContext ctx) {
        //System.out.println("Visit AttName");
        MyNodeList curNodeList = ctxMap.get(ctx.getParent());
        String attName = ctx.ATTNAME().getText();
        attName = attName.substring(1);
        MyNodeList result = XMLBasicOperator.hasAttName(curNodeList, attName);
        ctxMap.put(ctx, result);
        return result;
    }

    @Override
    public MyNodeList visitFilter(@NotNull XQueryParser.FilterContext ctx) {
        //System.out.println("Visit Filter");
        MyNodeList curNodeList = ctxMap.get(ctx.getParent());
        ctxMap.put(ctx, curNodeList);
        MyNodeList result = visit(ctx.f());
        ctxMap.put(ctx, result);
        return result;
    }

    @Override
    public MyNodeList visitRpFilter(@NotNull XQueryParser.RpFilterContext ctx) {
        //System.out.println("Visit RpFilter");
        MyNodeList curNodeList = ctxMap.get(ctx.getParent());
        ctxMap.put(ctx, curNodeList);
        MyNodeList result = visit(ctx.rp());
        ctxMap.put(ctx, result);
        return result;
    }

    @Override
    public MyNodeList visitNotFilter(@NotNull XQueryParser.NotFilterContext ctx) {
        MyNodeList curNodeList = ctxMap.get(ctx.getParent());
        ctxMap.put(ctx, curNodeList);
        MyNodeList result = visit(ctx.f());
        if(result.getLength() == 0) {
            return curNodeList;
        }
        else {
            result = new MyNodeList();
            ctxMap.put(ctx, result);
            return result;
        }
    }

    @Override
    public MyNodeList visitValueEqualFilter(@NotNull XQueryParser.ValueEqualFilterContext ctx) {
        MyNodeList curNodeList = ctxMap.get(ctx.getParent());
        ctxMap.put(ctx, curNodeList);
        MyNodeList leftResult = visit(ctx.rp(0));
        MyNodeList rightResult = visit(ctx. rp(1));
        if(XMLBasicOperator.existValueEqual(leftResult, rightResult)) {
            return curNodeList;
        }
        else {
            MyNodeList result = new MyNodeList();
            ctxMap.put(ctx, result);
            return result;
        }
    }

    @Override
    public MyNodeList visitIdEqualFilter(@NotNull XQueryParser.IdEqualFilterContext ctx) {
        MyNodeList curNodeList = ctxMap.get(ctx.getParent());
        ctxMap.put(ctx, curNodeList);
        MyNodeList leftResult = visit(ctx.rp(0));
        MyNodeList rightResult = visit(ctx. rp(1));
        if(XMLBasicOperator.existIdEqual(leftResult, rightResult)) {
            return curNodeList;
        }
        else {
            MyNodeList result = new MyNodeList();
            ctxMap.put(ctx, result);
            return result;
        }
    }

    @Override
    public MyNodeList visitFilterOrFilter(@NotNull XQueryParser.FilterOrFilterContext ctx) {
        MyNodeList curNodeList = ctxMap.get(ctx.getParent());
        ctxMap.put(ctx, curNodeList);
        MyNodeList leftResult = visit(ctx.f(0));
        MyNodeList rightResult = visit(ctx.f(1));
        if(leftResult.getLength() != 0 || rightResult.getLength() != 0) return curNodeList;
        else {
            MyNodeList result = new MyNodeList();
            ctxMap.put(ctx, result);
            return result;
        }
    }

    @Override
    public MyNodeList visitFilterAndFilter(@NotNull XQueryParser.FilterAndFilterContext ctx) {
        MyNodeList curNodeList = ctxMap.get(ctx.getParent());
        ctxMap.put(ctx, curNodeList);
        MyNodeList leftResult = visit(ctx.f(0));
        MyNodeList rightResult = visit(ctx.f(1));
        if(leftResult.getLength() != 0 && rightResult.getLength() != 0) return curNodeList;
        else {
            MyNodeList result = new MyNodeList();
            ctxMap.put(ctx, result);
            return result;
        }
    }
}
