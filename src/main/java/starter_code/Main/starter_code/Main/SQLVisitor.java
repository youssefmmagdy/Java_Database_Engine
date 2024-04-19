// Generated from C:/Users/ahmos/IdeaProjects/Java_Database_Engine/src/main/java/starter_code/Main/SQL.g4 by ANTLR 4.13.1
package starter_code.Main.starter_code.Main;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link SQLParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface SQLVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link SQLParser#parse}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParse(SQLParser.ParseContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#create}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCreate(SQLParser.CreateContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#closercreate}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClosercreate(SQLParser.ClosercreateContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#index}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIndex(SQLParser.IndexContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#closerindex}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCloserindex(SQLParser.CloserindexContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#insert}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInsert(SQLParser.InsertContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#wrapper}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWrapper(SQLParser.WrapperContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#closerinsert}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCloserinsert(SQLParser.CloserinsertContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#delete}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDelete(SQLParser.DeleteContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#closerdelete}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCloserdelete(SQLParser.CloserdeleteContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#update}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUpdate(SQLParser.UpdateContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#closerupdate}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCloserupdate(SQLParser.CloserupdateContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#cattrname}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCattrname(SQLParser.CattrnameContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#updateinput}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUpdateinput(SQLParser.UpdateinputContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#updatevalue}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUpdatevalue(SQLParser.UpdatevalueContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#select}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSelect(SQLParser.SelectContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#closerselect}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCloserselect(SQLParser.CloserselectContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#name}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitName(SQLParser.NameContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#indexname}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIndexname(SQLParser.IndexnameContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitType(SQLParser.TypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#attribute}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAttribute(SQLParser.AttributeContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#attrname}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAttrname(SQLParser.AttrnameContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#columns}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitColumns(SQLParser.ColumnsContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#oper}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOper(SQLParser.OperContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#value}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitValue(SQLParser.ValueContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#inbetweenoperand}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInbetweenoperand(SQLParser.InbetweenoperandContext ctx);
}