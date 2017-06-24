import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeProperty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by WSH on 3/14/17.
 */
public class XQueryJoinDetectorBuilder extends XQueryBaseVisitor<MyNodeList> {

    ParseTreeProperty<Boolean> map = new ParseTreeProperty<>();

    @Override
    public MyNodeList visitQuery(@NotNull XQueryParser.QueryContext ctx) {
        map.put(ctx, false);
        visit(ctx.getChild(0));
        boolean b = map.get(ctx.getChild(0));
        map.put(ctx, b);
        return null;
    }

    @Override
    public MyNodeList visitFLWRClause(@NotNull XQueryParser.FLWRClauseContext ctx) {
        visit(ctx.forClause());
        boolean b = map.get(ctx.forClause());
        map.put(ctx, b);
        return null;
    }

    @Override
    public MyNodeList visitForClause(@NotNull XQueryParser.ForClauseContext ctx) {
        visit(ctx.in(0));
        boolean b;
        if(map.get(ctx.in(0)) != null) b = map.get(ctx.in(0));
        else b = false;
        map.put(ctx, b);
        return null;
    }

    @Override
    public MyNodeList visitIn(@NotNull XQueryParser.InContext ctx) {
        visit(ctx.xq());
        boolean b;
        if(map.get(ctx.xq()) != null) b = map.get(ctx.xq());
        else b = false;
        map.put(ctx, b);
        return null;
    }
    @Override
    public MyNodeList visitJoinClause(@NotNull XQueryParser.JoinClauseContext ctx) {
        map.put(ctx, true);
        return null;
    }

    @Override
    public MyNodeList visitParentheseXq(@NotNull XQueryParser.ParentheseXqContext ctx) {
        visit(ctx.xq());
        boolean b;
        if(map.get(ctx.xq()) != null) b = map.get(ctx.xq());
        else b = false;
        map.put(ctx, b);
        return null;
    }


    @Override
    public MyNodeList visitConstructor(@NotNull XQueryParser.ConstructorContext ctx) {
        visit(ctx.xq());
        boolean b;
        if(map.get(ctx.xq()) != null) b = map.get(ctx.xq());
        else b = false;
        map.put(ctx, b);
        return null;
    }

    @Override
    public MyNodeList visitCommaXq(@NotNull XQueryParser.CommaXqContext ctx) {
        visit(ctx.xq(0));
        visit(ctx.xq(1));
        boolean a = false, b = false;
        if(map.get(ctx.xq(0)) != null) a = map.get(ctx.xq(0));
        if(map.get(ctx.xq(1)) != null) b = map.get(ctx.xq(1));
        map.put(ctx, a||b);
        return null;
    }



}
