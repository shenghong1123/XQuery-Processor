// Generated from /Users/WSH/IdeaProjects/XQuery/src/main/java/XQuery.g4 by ANTLR 4.6
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link XQueryParser}.
 */
public interface XQueryListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link XQueryParser#query}.
	 * @param ctx the parse tree
	 */
	void enterQuery(XQueryParser.QueryContext ctx);
	/**
	 * Exit a parse tree produced by {@link XQueryParser#query}.
	 * @param ctx the parse tree
	 */
	void exitQuery(XQueryParser.QueryContext ctx);
	/**
	 * Enter a parse tree produced by the {@code CommaXq}
	 * labeled alternative in {@link XQueryParser#xq}.
	 * @param ctx the parse tree
	 */
	void enterCommaXq(XQueryParser.CommaXqContext ctx);
	/**
	 * Exit a parse tree produced by the {@code CommaXq}
	 * labeled alternative in {@link XQueryParser#xq}.
	 * @param ctx the parse tree
	 */
	void exitCommaXq(XQueryParser.CommaXqContext ctx);
	/**
	 * Enter a parse tree produced by the {@code XqDSlashRp}
	 * labeled alternative in {@link XQueryParser#xq}.
	 * @param ctx the parse tree
	 */
	void enterXqDSlashRp(XQueryParser.XqDSlashRpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code XqDSlashRp}
	 * labeled alternative in {@link XQueryParser#xq}.
	 * @param ctx the parse tree
	 */
	void exitXqDSlashRp(XQueryParser.XqDSlashRpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ParentheseXq}
	 * labeled alternative in {@link XQueryParser#xq}.
	 * @param ctx the parse tree
	 */
	void enterParentheseXq(XQueryParser.ParentheseXqContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ParentheseXq}
	 * labeled alternative in {@link XQueryParser#xq}.
	 * @param ctx the parse tree
	 */
	void exitParentheseXq(XQueryParser.ParentheseXqContext ctx);
	/**
	 * Enter a parse tree produced by the {@code XqSSlashRp}
	 * labeled alternative in {@link XQueryParser#xq}.
	 * @param ctx the parse tree
	 */
	void enterXqSSlashRp(XQueryParser.XqSSlashRpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code XqSSlashRp}
	 * labeled alternative in {@link XQueryParser#xq}.
	 * @param ctx the parse tree
	 */
	void exitXqSSlashRp(XQueryParser.XqSSlashRpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Constructor}
	 * labeled alternative in {@link XQueryParser#xq}.
	 * @param ctx the parse tree
	 */
	void enterConstructor(XQueryParser.ConstructorContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Constructor}
	 * labeled alternative in {@link XQueryParser#xq}.
	 * @param ctx the parse tree
	 */
	void exitConstructor(XQueryParser.ConstructorContext ctx);
	/**
	 * Enter a parse tree produced by the {@code SingleLetClause}
	 * labeled alternative in {@link XQueryParser#xq}.
	 * @param ctx the parse tree
	 */
	void enterSingleLetClause(XQueryParser.SingleLetClauseContext ctx);
	/**
	 * Exit a parse tree produced by the {@code SingleLetClause}
	 * labeled alternative in {@link XQueryParser#xq}.
	 * @param ctx the parse tree
	 */
	void exitSingleLetClause(XQueryParser.SingleLetClauseContext ctx);
	/**
	 * Enter a parse tree produced by the {@code StringConstant}
	 * labeled alternative in {@link XQueryParser#xq}.
	 * @param ctx the parse tree
	 */
	void enterStringConstant(XQueryParser.StringConstantContext ctx);
	/**
	 * Exit a parse tree produced by the {@code StringConstant}
	 * labeled alternative in {@link XQueryParser#xq}.
	 * @param ctx the parse tree
	 */
	void exitStringConstant(XQueryParser.StringConstantContext ctx);
	/**
	 * Enter a parse tree produced by the {@code AbsolutePath}
	 * labeled alternative in {@link XQueryParser#xq}.
	 * @param ctx the parse tree
	 */
	void enterAbsolutePath(XQueryParser.AbsolutePathContext ctx);
	/**
	 * Exit a parse tree produced by the {@code AbsolutePath}
	 * labeled alternative in {@link XQueryParser#xq}.
	 * @param ctx the parse tree
	 */
	void exitAbsolutePath(XQueryParser.AbsolutePathContext ctx);
	/**
	 * Enter a parse tree produced by the {@code SingleVariable}
	 * labeled alternative in {@link XQueryParser#xq}.
	 * @param ctx the parse tree
	 */
	void enterSingleVariable(XQueryParser.SingleVariableContext ctx);
	/**
	 * Exit a parse tree produced by the {@code SingleVariable}
	 * labeled alternative in {@link XQueryParser#xq}.
	 * @param ctx the parse tree
	 */
	void exitSingleVariable(XQueryParser.SingleVariableContext ctx);
	/**
	 * Enter a parse tree produced by the {@code JoinClause}
	 * labeled alternative in {@link XQueryParser#xq}.
	 * @param ctx the parse tree
	 */
	void enterJoinClause(XQueryParser.JoinClauseContext ctx);
	/**
	 * Exit a parse tree produced by the {@code JoinClause}
	 * labeled alternative in {@link XQueryParser#xq}.
	 * @param ctx the parse tree
	 */
	void exitJoinClause(XQueryParser.JoinClauseContext ctx);
	/**
	 * Enter a parse tree produced by the {@code FLWRClause}
	 * labeled alternative in {@link XQueryParser#xq}.
	 * @param ctx the parse tree
	 */
	void enterFLWRClause(XQueryParser.FLWRClauseContext ctx);
	/**
	 * Exit a parse tree produced by the {@code FLWRClause}
	 * labeled alternative in {@link XQueryParser#xq}.
	 * @param ctx the parse tree
	 */
	void exitFLWRClause(XQueryParser.FLWRClauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link XQueryParser#attlist}.
	 * @param ctx the parse tree
	 */
	void enterAttlist(XQueryParser.AttlistContext ctx);
	/**
	 * Exit a parse tree produced by {@link XQueryParser#attlist}.
	 * @param ctx the parse tree
	 */
	void exitAttlist(XQueryParser.AttlistContext ctx);
	/**
	 * Enter a parse tree produced by {@link XQueryParser#forClause}.
	 * @param ctx the parse tree
	 */
	void enterForClause(XQueryParser.ForClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link XQueryParser#forClause}.
	 * @param ctx the parse tree
	 */
	void exitForClause(XQueryParser.ForClauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link XQueryParser#in}.
	 * @param ctx the parse tree
	 */
	void enterIn(XQueryParser.InContext ctx);
	/**
	 * Exit a parse tree produced by {@link XQueryParser#in}.
	 * @param ctx the parse tree
	 */
	void exitIn(XQueryParser.InContext ctx);
	/**
	 * Enter a parse tree produced by {@link XQueryParser#letClause}.
	 * @param ctx the parse tree
	 */
	void enterLetClause(XQueryParser.LetClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link XQueryParser#letClause}.
	 * @param ctx the parse tree
	 */
	void exitLetClause(XQueryParser.LetClauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link XQueryParser#whereClause}.
	 * @param ctx the parse tree
	 */
	void enterWhereClause(XQueryParser.WhereClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link XQueryParser#whereClause}.
	 * @param ctx the parse tree
	 */
	void exitWhereClause(XQueryParser.WhereClauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link XQueryParser#returnClause}.
	 * @param ctx the parse tree
	 */
	void enterReturnClause(XQueryParser.ReturnClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link XQueryParser#returnClause}.
	 * @param ctx the parse tree
	 */
	void exitReturnClause(XQueryParser.ReturnClauseContext ctx);
	/**
	 * Enter a parse tree produced by the {@code SomeCondition}
	 * labeled alternative in {@link XQueryParser#cond}.
	 * @param ctx the parse tree
	 */
	void enterSomeCondition(XQueryParser.SomeConditionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code SomeCondition}
	 * labeled alternative in {@link XQueryParser#cond}.
	 * @param ctx the parse tree
	 */
	void exitSomeCondition(XQueryParser.SomeConditionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code EmptyCondition}
	 * labeled alternative in {@link XQueryParser#cond}.
	 * @param ctx the parse tree
	 */
	void enterEmptyCondition(XQueryParser.EmptyConditionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code EmptyCondition}
	 * labeled alternative in {@link XQueryParser#cond}.
	 * @param ctx the parse tree
	 */
	void exitEmptyCondition(XQueryParser.EmptyConditionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ParentheseCondition}
	 * labeled alternative in {@link XQueryParser#cond}.
	 * @param ctx the parse tree
	 */
	void enterParentheseCondition(XQueryParser.ParentheseConditionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ParentheseCondition}
	 * labeled alternative in {@link XQueryParser#cond}.
	 * @param ctx the parse tree
	 */
	void exitParentheseCondition(XQueryParser.ParentheseConditionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code NotCondition}
	 * labeled alternative in {@link XQueryParser#cond}.
	 * @param ctx the parse tree
	 */
	void enterNotCondition(XQueryParser.NotConditionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code NotCondition}
	 * labeled alternative in {@link XQueryParser#cond}.
	 * @param ctx the parse tree
	 */
	void exitNotCondition(XQueryParser.NotConditionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code IdEqualCondition}
	 * labeled alternative in {@link XQueryParser#cond}.
	 * @param ctx the parse tree
	 */
	void enterIdEqualCondition(XQueryParser.IdEqualConditionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code IdEqualCondition}
	 * labeled alternative in {@link XQueryParser#cond}.
	 * @param ctx the parse tree
	 */
	void exitIdEqualCondition(XQueryParser.IdEqualConditionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code OrCondition}
	 * labeled alternative in {@link XQueryParser#cond}.
	 * @param ctx the parse tree
	 */
	void enterOrCondition(XQueryParser.OrConditionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code OrCondition}
	 * labeled alternative in {@link XQueryParser#cond}.
	 * @param ctx the parse tree
	 */
	void exitOrCondition(XQueryParser.OrConditionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ValueEqualCondition}
	 * labeled alternative in {@link XQueryParser#cond}.
	 * @param ctx the parse tree
	 */
	void enterValueEqualCondition(XQueryParser.ValueEqualConditionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ValueEqualCondition}
	 * labeled alternative in {@link XQueryParser#cond}.
	 * @param ctx the parse tree
	 */
	void exitValueEqualCondition(XQueryParser.ValueEqualConditionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code AndCondition}
	 * labeled alternative in {@link XQueryParser#cond}.
	 * @param ctx the parse tree
	 */
	void enterAndCondition(XQueryParser.AndConditionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code AndCondition}
	 * labeled alternative in {@link XQueryParser#cond}.
	 * @param ctx the parse tree
	 */
	void exitAndCondition(XQueryParser.AndConditionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code SSlashAp}
	 * labeled alternative in {@link XQueryParser#ap}.
	 * @param ctx the parse tree
	 */
	void enterSSlashAp(XQueryParser.SSlashApContext ctx);
	/**
	 * Exit a parse tree produced by the {@code SSlashAp}
	 * labeled alternative in {@link XQueryParser#ap}.
	 * @param ctx the parse tree
	 */
	void exitSSlashAp(XQueryParser.SSlashApContext ctx);
	/**
	 * Enter a parse tree produced by the {@code DSlashAp}
	 * labeled alternative in {@link XQueryParser#ap}.
	 * @param ctx the parse tree
	 */
	void enterDSlashAp(XQueryParser.DSlashApContext ctx);
	/**
	 * Exit a parse tree produced by the {@code DSlashAp}
	 * labeled alternative in {@link XQueryParser#ap}.
	 * @param ctx the parse tree
	 */
	void exitDSlashAp(XQueryParser.DSlashApContext ctx);
	/**
	 * Enter a parse tree produced by the {@code SDot}
	 * labeled alternative in {@link XQueryParser#rp}.
	 * @param ctx the parse tree
	 */
	void enterSDot(XQueryParser.SDotContext ctx);
	/**
	 * Exit a parse tree produced by the {@code SDot}
	 * labeled alternative in {@link XQueryParser#rp}.
	 * @param ctx the parse tree
	 */
	void exitSDot(XQueryParser.SDotContext ctx);
	/**
	 * Enter a parse tree produced by the {@code AttName}
	 * labeled alternative in {@link XQueryParser#rp}.
	 * @param ctx the parse tree
	 */
	void enterAttName(XQueryParser.AttNameContext ctx);
	/**
	 * Exit a parse tree produced by the {@code AttName}
	 * labeled alternative in {@link XQueryParser#rp}.
	 * @param ctx the parse tree
	 */
	void exitAttName(XQueryParser.AttNameContext ctx);
	/**
	 * Enter a parse tree produced by the {@code TagName}
	 * labeled alternative in {@link XQueryParser#rp}.
	 * @param ctx the parse tree
	 */
	void enterTagName(XQueryParser.TagNameContext ctx);
	/**
	 * Exit a parse tree produced by the {@code TagName}
	 * labeled alternative in {@link XQueryParser#rp}.
	 * @param ctx the parse tree
	 */
	void exitTagName(XQueryParser.TagNameContext ctx);
	/**
	 * Enter a parse tree produced by the {@code SSlashRp}
	 * labeled alternative in {@link XQueryParser#rp}.
	 * @param ctx the parse tree
	 */
	void enterSSlashRp(XQueryParser.SSlashRpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code SSlashRp}
	 * labeled alternative in {@link XQueryParser#rp}.
	 * @param ctx the parse tree
	 */
	void exitSSlashRp(XQueryParser.SSlashRpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code RpWithFilter}
	 * labeled alternative in {@link XQueryParser#rp}.
	 * @param ctx the parse tree
	 */
	void enterRpWithFilter(XQueryParser.RpWithFilterContext ctx);
	/**
	 * Exit a parse tree produced by the {@code RpWithFilter}
	 * labeled alternative in {@link XQueryParser#rp}.
	 * @param ctx the parse tree
	 */
	void exitRpWithFilter(XQueryParser.RpWithFilterContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ParentheseRp}
	 * labeled alternative in {@link XQueryParser#rp}.
	 * @param ctx the parse tree
	 */
	void enterParentheseRp(XQueryParser.ParentheseRpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ParentheseRp}
	 * labeled alternative in {@link XQueryParser#rp}.
	 * @param ctx the parse tree
	 */
	void exitParentheseRp(XQueryParser.ParentheseRpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code DDot}
	 * labeled alternative in {@link XQueryParser#rp}.
	 * @param ctx the parse tree
	 */
	void enterDDot(XQueryParser.DDotContext ctx);
	/**
	 * Exit a parse tree produced by the {@code DDot}
	 * labeled alternative in {@link XQueryParser#rp}.
	 * @param ctx the parse tree
	 */
	void exitDDot(XQueryParser.DDotContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Text}
	 * labeled alternative in {@link XQueryParser#rp}.
	 * @param ctx the parse tree
	 */
	void enterText(XQueryParser.TextContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Text}
	 * labeled alternative in {@link XQueryParser#rp}.
	 * @param ctx the parse tree
	 */
	void exitText(XQueryParser.TextContext ctx);
	/**
	 * Enter a parse tree produced by the {@code DSlashRp}
	 * labeled alternative in {@link XQueryParser#rp}.
	 * @param ctx the parse tree
	 */
	void enterDSlashRp(XQueryParser.DSlashRpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code DSlashRp}
	 * labeled alternative in {@link XQueryParser#rp}.
	 * @param ctx the parse tree
	 */
	void exitDSlashRp(XQueryParser.DSlashRpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code WildCard}
	 * labeled alternative in {@link XQueryParser#rp}.
	 * @param ctx the parse tree
	 */
	void enterWildCard(XQueryParser.WildCardContext ctx);
	/**
	 * Exit a parse tree produced by the {@code WildCard}
	 * labeled alternative in {@link XQueryParser#rp}.
	 * @param ctx the parse tree
	 */
	void exitWildCard(XQueryParser.WildCardContext ctx);
	/**
	 * Enter a parse tree produced by the {@code CommaRp}
	 * labeled alternative in {@link XQueryParser#rp}.
	 * @param ctx the parse tree
	 */
	void enterCommaRp(XQueryParser.CommaRpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code CommaRp}
	 * labeled alternative in {@link XQueryParser#rp}.
	 * @param ctx the parse tree
	 */
	void exitCommaRp(XQueryParser.CommaRpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Filter}
	 * labeled alternative in {@link XQueryParser#f}.
	 * @param ctx the parse tree
	 */
	void enterFilter(XQueryParser.FilterContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Filter}
	 * labeled alternative in {@link XQueryParser#f}.
	 * @param ctx the parse tree
	 */
	void exitFilter(XQueryParser.FilterContext ctx);
	/**
	 * Enter a parse tree produced by the {@code NotFilter}
	 * labeled alternative in {@link XQueryParser#f}.
	 * @param ctx the parse tree
	 */
	void enterNotFilter(XQueryParser.NotFilterContext ctx);
	/**
	 * Exit a parse tree produced by the {@code NotFilter}
	 * labeled alternative in {@link XQueryParser#f}.
	 * @param ctx the parse tree
	 */
	void exitNotFilter(XQueryParser.NotFilterContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ValueEqualFilter}
	 * labeled alternative in {@link XQueryParser#f}.
	 * @param ctx the parse tree
	 */
	void enterValueEqualFilter(XQueryParser.ValueEqualFilterContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ValueEqualFilter}
	 * labeled alternative in {@link XQueryParser#f}.
	 * @param ctx the parse tree
	 */
	void exitValueEqualFilter(XQueryParser.ValueEqualFilterContext ctx);
	/**
	 * Enter a parse tree produced by the {@code IdEqualFilter}
	 * labeled alternative in {@link XQueryParser#f}.
	 * @param ctx the parse tree
	 */
	void enterIdEqualFilter(XQueryParser.IdEqualFilterContext ctx);
	/**
	 * Exit a parse tree produced by the {@code IdEqualFilter}
	 * labeled alternative in {@link XQueryParser#f}.
	 * @param ctx the parse tree
	 */
	void exitIdEqualFilter(XQueryParser.IdEqualFilterContext ctx);
	/**
	 * Enter a parse tree produced by the {@code FilterOrFilter}
	 * labeled alternative in {@link XQueryParser#f}.
	 * @param ctx the parse tree
	 */
	void enterFilterOrFilter(XQueryParser.FilterOrFilterContext ctx);
	/**
	 * Exit a parse tree produced by the {@code FilterOrFilter}
	 * labeled alternative in {@link XQueryParser#f}.
	 * @param ctx the parse tree
	 */
	void exitFilterOrFilter(XQueryParser.FilterOrFilterContext ctx);
	/**
	 * Enter a parse tree produced by the {@code RpFilter}
	 * labeled alternative in {@link XQueryParser#f}.
	 * @param ctx the parse tree
	 */
	void enterRpFilter(XQueryParser.RpFilterContext ctx);
	/**
	 * Exit a parse tree produced by the {@code RpFilter}
	 * labeled alternative in {@link XQueryParser#f}.
	 * @param ctx the parse tree
	 */
	void exitRpFilter(XQueryParser.RpFilterContext ctx);
	/**
	 * Enter a parse tree produced by the {@code FilterAndFilter}
	 * labeled alternative in {@link XQueryParser#f}.
	 * @param ctx the parse tree
	 */
	void enterFilterAndFilter(XQueryParser.FilterAndFilterContext ctx);
	/**
	 * Exit a parse tree produced by the {@code FilterAndFilter}
	 * labeled alternative in {@link XQueryParser#f}.
	 * @param ctx the parse tree
	 */
	void exitFilterAndFilter(XQueryParser.FilterAndFilterContext ctx);
}