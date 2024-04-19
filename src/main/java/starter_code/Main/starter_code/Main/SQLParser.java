// Generated from C:/Users/ahmos/IdeaProjects/Java_Database_Engine/src/main/java/starter_code/Main/SQL.g4 by ANTLR 4.13.1
package starter_code.Main.starter_code.Main;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue"})
public class SQLParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.13.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, T__16=17, 
		T__17=18, T__18=19, T__19=20, T__20=21, T__21=22, T__22=23, T__23=24, 
		T__24=25, T__25=26, T__26=27, T__27=28, T__28=29, SCOL=30, COMMA=31, OPAREN=32, 
		CPAREN=33, ID=34, INT=35, DOUBLE=36, STRING=37, SPACE=38;
	public static final int
		RULE_parse = 0, RULE_create = 1, RULE_closercreate = 2, RULE_index = 3, 
		RULE_closerindex = 4, RULE_insert = 5, RULE_wrapper = 6, RULE_closerinsert = 7, 
		RULE_delete = 8, RULE_closerdelete = 9, RULE_update = 10, RULE_closerupdate = 11, 
		RULE_cattrname = 12, RULE_updateinput = 13, RULE_updatevalue = 14, RULE_select = 15, 
		RULE_closerselect = 16, RULE_name = 17, RULE_indexname = 18, RULE_type = 19, 
		RULE_attribute = 20, RULE_attrname = 21, RULE_columns = 22, RULE_oper = 23, 
		RULE_value = 24, RULE_inbetweenoperand = 25;
	private static String[] makeRuleNames() {
		return new String[] {
			"parse", "create", "closercreate", "index", "closerindex", "insert", 
			"wrapper", "closerinsert", "delete", "closerdelete", "update", "closerupdate", 
			"cattrname", "updateinput", "updatevalue", "select", "closerselect", 
			"name", "indexname", "type", "attribute", "attrname", "columns", "oper", 
			"value", "inbetweenoperand"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'CREATE TABLE'", "'PRIMARY KEY'", "'CREATE INDEX'", "'ON'", "'INSERT INTO'", 
			"'VALUES'", "'DELETE FROM'", "'WHERE'", "'AND'", "'UPDATE'", "'SET'", 
			"'='", "'SELECT * FROM'", "'INT'", "'DOUBLE'", "'STRING'", "'!='", "'>'", 
			"'>='", "'<'", "'<='", "'And'", "'and'", "'OR'", "'Or'", "'or'", "'XOR'", 
			"'Xor'", "'xor'", "';'", "','", "'('", "')'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, "SCOL", "COMMA", "OPAREN", "CPAREN", 
			"ID", "INT", "DOUBLE", "STRING", "SPACE"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "SQL.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public SQLParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ParseContext extends ParserRuleContext {
		public CreateContext create() {
			return getRuleContext(CreateContext.class,0);
		}
		public TerminalNode EOF() { return getToken(SQLParser.EOF, 0); }
		public IndexContext index() {
			return getRuleContext(IndexContext.class,0);
		}
		public InsertContext insert() {
			return getRuleContext(InsertContext.class,0);
		}
		public DeleteContext delete() {
			return getRuleContext(DeleteContext.class,0);
		}
		public UpdateContext update() {
			return getRuleContext(UpdateContext.class,0);
		}
		public SelectContext select() {
			return getRuleContext(SelectContext.class,0);
		}
		public ParseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_parse; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SQLVisitor ) return ((SQLVisitor<? extends T>)visitor).visitParse(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ParseContext parse() throws RecognitionException {
		ParseContext _localctx = new ParseContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_parse);
		try {
			setState(70);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__0:
				enterOuterAlt(_localctx, 1);
				{
				setState(52);
				create();
				setState(53);
				match(EOF);
				}
				break;
			case T__2:
				enterOuterAlt(_localctx, 2);
				{
				setState(55);
				index();
				setState(56);
				match(EOF);
				}
				break;
			case T__4:
				enterOuterAlt(_localctx, 3);
				{
				setState(58);
				insert();
				setState(59);
				match(EOF);
				}
				break;
			case T__6:
				enterOuterAlt(_localctx, 4);
				{
				setState(61);
				delete();
				setState(62);
				match(EOF);
				}
				break;
			case T__9:
				enterOuterAlt(_localctx, 5);
				{
				setState(64);
				update();
				setState(65);
				match(EOF);
				}
				break;
			case T__12:
				enterOuterAlt(_localctx, 6);
				{
				setState(67);
				select();
				setState(68);
				match(EOF);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class CreateContext extends ParserRuleContext {
		public NameContext name() {
			return getRuleContext(NameContext.class,0);
		}
		public TerminalNode OPAREN() { return getToken(SQLParser.OPAREN, 0); }
		public List<AttributeContext> attribute() {
			return getRuleContexts(AttributeContext.class);
		}
		public AttributeContext attribute(int i) {
			return getRuleContext(AttributeContext.class,i);
		}
		public ClosercreateContext closercreate() {
			return getRuleContext(ClosercreateContext.class,0);
		}
		public List<TerminalNode> COMMA() { return getTokens(SQLParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(SQLParser.COMMA, i);
		}
		public CreateContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_create; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SQLVisitor ) return ((SQLVisitor<? extends T>)visitor).visitCreate(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CreateContext create() throws RecognitionException {
		CreateContext _localctx = new CreateContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_create);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(72);
			match(T__0);
			setState(73);
			name();
			setState(74);
			match(OPAREN);
			setState(75);
			attribute();
			setState(76);
			match(T__1);
			setState(81);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(77);
				match(COMMA);
				setState(78);
				attribute();
				}
				}
				setState(83);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(84);
			closercreate();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ClosercreateContext extends ParserRuleContext {
		public TerminalNode CPAREN() { return getToken(SQLParser.CPAREN, 0); }
		public TerminalNode SCOL() { return getToken(SQLParser.SCOL, 0); }
		public ClosercreateContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_closercreate; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SQLVisitor ) return ((SQLVisitor<? extends T>)visitor).visitClosercreate(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ClosercreateContext closercreate() throws RecognitionException {
		ClosercreateContext _localctx = new ClosercreateContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_closercreate);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(86);
			match(CPAREN);
			setState(87);
			match(SCOL);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class IndexContext extends ParserRuleContext {
		public IndexnameContext indexname() {
			return getRuleContext(IndexnameContext.class,0);
		}
		public NameContext name() {
			return getRuleContext(NameContext.class,0);
		}
		public TerminalNode OPAREN() { return getToken(SQLParser.OPAREN, 0); }
		public AttrnameContext attrname() {
			return getRuleContext(AttrnameContext.class,0);
		}
		public CloserindexContext closerindex() {
			return getRuleContext(CloserindexContext.class,0);
		}
		public IndexContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_index; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SQLVisitor ) return ((SQLVisitor<? extends T>)visitor).visitIndex(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IndexContext index() throws RecognitionException {
		IndexContext _localctx = new IndexContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_index);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(89);
			match(T__2);
			setState(90);
			indexname();
			setState(91);
			match(T__3);
			setState(92);
			name();
			setState(93);
			match(OPAREN);
			setState(94);
			attrname();
			setState(95);
			closerindex();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class CloserindexContext extends ParserRuleContext {
		public TerminalNode CPAREN() { return getToken(SQLParser.CPAREN, 0); }
		public TerminalNode SCOL() { return getToken(SQLParser.SCOL, 0); }
		public CloserindexContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_closerindex; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SQLVisitor ) return ((SQLVisitor<? extends T>)visitor).visitCloserindex(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CloserindexContext closerindex() throws RecognitionException {
		CloserindexContext _localctx = new CloserindexContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_closerindex);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(97);
			match(CPAREN);
			setState(98);
			match(SCOL);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class InsertContext extends ParserRuleContext {
		public NameContext name() {
			return getRuleContext(NameContext.class,0);
		}
		public List<TerminalNode> OPAREN() { return getTokens(SQLParser.OPAREN); }
		public TerminalNode OPAREN(int i) {
			return getToken(SQLParser.OPAREN, i);
		}
		public List<AttrnameContext> attrname() {
			return getRuleContexts(AttrnameContext.class);
		}
		public AttrnameContext attrname(int i) {
			return getRuleContext(AttrnameContext.class,i);
		}
		public List<TerminalNode> CPAREN() { return getTokens(SQLParser.CPAREN); }
		public TerminalNode CPAREN(int i) {
			return getToken(SQLParser.CPAREN, i);
		}
		public List<WrapperContext> wrapper() {
			return getRuleContexts(WrapperContext.class);
		}
		public WrapperContext wrapper(int i) {
			return getRuleContext(WrapperContext.class,i);
		}
		public CloserinsertContext closerinsert() {
			return getRuleContext(CloserinsertContext.class,0);
		}
		public List<TerminalNode> COMMA() { return getTokens(SQLParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(SQLParser.COMMA, i);
		}
		public InsertContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_insert; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SQLVisitor ) return ((SQLVisitor<? extends T>)visitor).visitInsert(this);
			else return visitor.visitChildren(this);
		}
	}

	public final InsertContext insert() throws RecognitionException {
		InsertContext _localctx = new InsertContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_insert);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(100);
			match(T__4);
			setState(101);
			name();
			setState(102);
			match(OPAREN);
			setState(103);
			attrname();
			setState(108);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(104);
				match(COMMA);
				setState(105);
				attrname();
				}
				}
				setState(110);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(111);
			match(CPAREN);
			setState(112);
			match(T__5);
			setState(113);
			match(OPAREN);
			setState(114);
			wrapper();
			setState(115);
			match(CPAREN);
			setState(123);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(116);
				match(COMMA);
				setState(117);
				match(OPAREN);
				setState(118);
				wrapper();
				setState(119);
				match(CPAREN);
				}
				}
				setState(125);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(126);
			closerinsert();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class WrapperContext extends ParserRuleContext {
		public List<ValueContext> value() {
			return getRuleContexts(ValueContext.class);
		}
		public ValueContext value(int i) {
			return getRuleContext(ValueContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(SQLParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(SQLParser.COMMA, i);
		}
		public WrapperContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_wrapper; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SQLVisitor ) return ((SQLVisitor<? extends T>)visitor).visitWrapper(this);
			else return visitor.visitChildren(this);
		}
	}

	public final WrapperContext wrapper() throws RecognitionException {
		WrapperContext _localctx = new WrapperContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_wrapper);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(128);
			value();
			setState(133);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(129);
				match(COMMA);
				setState(130);
				value();
				}
				}
				setState(135);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class CloserinsertContext extends ParserRuleContext {
		public TerminalNode SCOL() { return getToken(SQLParser.SCOL, 0); }
		public CloserinsertContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_closerinsert; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SQLVisitor ) return ((SQLVisitor<? extends T>)visitor).visitCloserinsert(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CloserinsertContext closerinsert() throws RecognitionException {
		CloserinsertContext _localctx = new CloserinsertContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_closerinsert);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(136);
			match(SCOL);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class DeleteContext extends ParserRuleContext {
		public NameContext name() {
			return getRuleContext(NameContext.class,0);
		}
		public CloserdeleteContext closerdelete() {
			return getRuleContext(CloserdeleteContext.class,0);
		}
		public List<UpdateinputContext> updateinput() {
			return getRuleContexts(UpdateinputContext.class);
		}
		public UpdateinputContext updateinput(int i) {
			return getRuleContext(UpdateinputContext.class,i);
		}
		public DeleteContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_delete; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SQLVisitor ) return ((SQLVisitor<? extends T>)visitor).visitDelete(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DeleteContext delete() throws RecognitionException {
		DeleteContext _localctx = new DeleteContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_delete);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(138);
			match(T__6);
			setState(139);
			name();
			setState(149);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__7) {
				{
				setState(140);
				match(T__7);
				setState(141);
				updateinput();
				setState(146);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__8) {
					{
					{
					setState(142);
					match(T__8);
					setState(143);
					updateinput();
					}
					}
					setState(148);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			setState(151);
			closerdelete();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class CloserdeleteContext extends ParserRuleContext {
		public TerminalNode SCOL() { return getToken(SQLParser.SCOL, 0); }
		public CloserdeleteContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_closerdelete; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SQLVisitor ) return ((SQLVisitor<? extends T>)visitor).visitCloserdelete(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CloserdeleteContext closerdelete() throws RecognitionException {
		CloserdeleteContext _localctx = new CloserdeleteContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_closerdelete);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(153);
			match(SCOL);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class UpdateContext extends ParserRuleContext {
		public NameContext name() {
			return getRuleContext(NameContext.class,0);
		}
		public List<UpdateinputContext> updateinput() {
			return getRuleContexts(UpdateinputContext.class);
		}
		public UpdateinputContext updateinput(int i) {
			return getRuleContext(UpdateinputContext.class,i);
		}
		public CattrnameContext cattrname() {
			return getRuleContext(CattrnameContext.class,0);
		}
		public ValueContext value() {
			return getRuleContext(ValueContext.class,0);
		}
		public CloserupdateContext closerupdate() {
			return getRuleContext(CloserupdateContext.class,0);
		}
		public List<TerminalNode> COMMA() { return getTokens(SQLParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(SQLParser.COMMA, i);
		}
		public UpdateContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_update; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SQLVisitor ) return ((SQLVisitor<? extends T>)visitor).visitUpdate(this);
			else return visitor.visitChildren(this);
		}
	}

	public final UpdateContext update() throws RecognitionException {
		UpdateContext _localctx = new UpdateContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_update);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(155);
			match(T__9);
			setState(156);
			name();
			setState(157);
			match(T__10);
			setState(158);
			updateinput();
			setState(163);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(159);
				match(COMMA);
				setState(160);
				updateinput();
				}
				}
				setState(165);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(166);
			match(T__7);
			setState(167);
			cattrname();
			setState(168);
			match(T__11);
			setState(169);
			value();
			setState(170);
			closerupdate();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class CloserupdateContext extends ParserRuleContext {
		public TerminalNode SCOL() { return getToken(SQLParser.SCOL, 0); }
		public CloserupdateContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_closerupdate; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SQLVisitor ) return ((SQLVisitor<? extends T>)visitor).visitCloserupdate(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CloserupdateContext closerupdate() throws RecognitionException {
		CloserupdateContext _localctx = new CloserupdateContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_closerupdate);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(172);
			match(SCOL);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class CattrnameContext extends ParserRuleContext {
		public TerminalNode STRING() { return getToken(SQLParser.STRING, 0); }
		public CattrnameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_cattrname; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SQLVisitor ) return ((SQLVisitor<? extends T>)visitor).visitCattrname(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CattrnameContext cattrname() throws RecognitionException {
		CattrnameContext _localctx = new CattrnameContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_cattrname);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(174);
			match(STRING);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class UpdateinputContext extends ParserRuleContext {
		public AttrnameContext attrname() {
			return getRuleContext(AttrnameContext.class,0);
		}
		public UpdatevalueContext updatevalue() {
			return getRuleContext(UpdatevalueContext.class,0);
		}
		public UpdateinputContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_updateinput; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SQLVisitor ) return ((SQLVisitor<? extends T>)visitor).visitUpdateinput(this);
			else return visitor.visitChildren(this);
		}
	}

	public final UpdateinputContext updateinput() throws RecognitionException {
		UpdateinputContext _localctx = new UpdateinputContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_updateinput);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(176);
			attrname();
			setState(177);
			match(T__11);
			setState(178);
			updatevalue();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class UpdatevalueContext extends ParserRuleContext {
		public TerminalNode INT() { return getToken(SQLParser.INT, 0); }
		public TerminalNode STRING() { return getToken(SQLParser.STRING, 0); }
		public TerminalNode DOUBLE() { return getToken(SQLParser.DOUBLE, 0); }
		public UpdatevalueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_updatevalue; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SQLVisitor ) return ((SQLVisitor<? extends T>)visitor).visitUpdatevalue(this);
			else return visitor.visitChildren(this);
		}
	}

	public final UpdatevalueContext updatevalue() throws RecognitionException {
		UpdatevalueContext _localctx = new UpdatevalueContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_updatevalue);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(180);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 240518168576L) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class SelectContext extends ParserRuleContext {
		public NameContext name() {
			return getRuleContext(NameContext.class,0);
		}
		public CloserselectContext closerselect() {
			return getRuleContext(CloserselectContext.class,0);
		}
		public List<ColumnsContext> columns() {
			return getRuleContexts(ColumnsContext.class);
		}
		public ColumnsContext columns(int i) {
			return getRuleContext(ColumnsContext.class,i);
		}
		public List<InbetweenoperandContext> inbetweenoperand() {
			return getRuleContexts(InbetweenoperandContext.class);
		}
		public InbetweenoperandContext inbetweenoperand(int i) {
			return getRuleContext(InbetweenoperandContext.class,i);
		}
		public SelectContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_select; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SQLVisitor ) return ((SQLVisitor<? extends T>)visitor).visitSelect(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SelectContext select() throws RecognitionException {
		SelectContext _localctx = new SelectContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_select);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(182);
			match(T__12);
			setState(183);
			name();
			setState(194);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__7) {
				{
				setState(184);
				match(T__7);
				setState(185);
				columns();
				setState(191);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 1069548032L) != 0)) {
					{
					{
					setState(186);
					inbetweenoperand();
					setState(187);
					columns();
					}
					}
					setState(193);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			setState(196);
			closerselect();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class CloserselectContext extends ParserRuleContext {
		public TerminalNode SCOL() { return getToken(SQLParser.SCOL, 0); }
		public CloserselectContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_closerselect; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SQLVisitor ) return ((SQLVisitor<? extends T>)visitor).visitCloserselect(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CloserselectContext closerselect() throws RecognitionException {
		CloserselectContext _localctx = new CloserselectContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_closerselect);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(198);
			match(SCOL);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class NameContext extends ParserRuleContext {
		public TerminalNode STRING() { return getToken(SQLParser.STRING, 0); }
		public NameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_name; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SQLVisitor ) return ((SQLVisitor<? extends T>)visitor).visitName(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NameContext name() throws RecognitionException {
		NameContext _localctx = new NameContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_name);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(200);
			match(STRING);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class IndexnameContext extends ParserRuleContext {
		public TerminalNode STRING() { return getToken(SQLParser.STRING, 0); }
		public IndexnameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_indexname; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SQLVisitor ) return ((SQLVisitor<? extends T>)visitor).visitIndexname(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IndexnameContext indexname() throws RecognitionException {
		IndexnameContext _localctx = new IndexnameContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_indexname);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(202);
			match(STRING);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class TypeContext extends ParserRuleContext {
		public TypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_type; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SQLVisitor ) return ((SQLVisitor<? extends T>)visitor).visitType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeContext type() throws RecognitionException {
		TypeContext _localctx = new TypeContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_type);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(204);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 114688L) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class AttributeContext extends ParserRuleContext {
		public AttrnameContext attrname() {
			return getRuleContext(AttrnameContext.class,0);
		}
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public AttributeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_attribute; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SQLVisitor ) return ((SQLVisitor<? extends T>)visitor).visitAttribute(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AttributeContext attribute() throws RecognitionException {
		AttributeContext _localctx = new AttributeContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_attribute);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(206);
			attrname();
			setState(207);
			type();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class AttrnameContext extends ParserRuleContext {
		public TerminalNode STRING() { return getToken(SQLParser.STRING, 0); }
		public AttrnameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_attrname; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SQLVisitor ) return ((SQLVisitor<? extends T>)visitor).visitAttrname(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AttrnameContext attrname() throws RecognitionException {
		AttrnameContext _localctx = new AttrnameContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_attrname);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(209);
			match(STRING);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ColumnsContext extends ParserRuleContext {
		public AttrnameContext attrname() {
			return getRuleContext(AttrnameContext.class,0);
		}
		public OperContext oper() {
			return getRuleContext(OperContext.class,0);
		}
		public ValueContext value() {
			return getRuleContext(ValueContext.class,0);
		}
		public ColumnsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_columns; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SQLVisitor ) return ((SQLVisitor<? extends T>)visitor).visitColumns(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ColumnsContext columns() throws RecognitionException {
		ColumnsContext _localctx = new ColumnsContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_columns);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(211);
			attrname();
			setState(212);
			oper();
			setState(213);
			value();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class OperContext extends ParserRuleContext {
		public OperContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_oper; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SQLVisitor ) return ((SQLVisitor<? extends T>)visitor).visitOper(this);
			else return visitor.visitChildren(this);
		}
	}

	public final OperContext oper() throws RecognitionException {
		OperContext _localctx = new OperContext(_ctx, getState());
		enterRule(_localctx, 46, RULE_oper);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(215);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 4067328L) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ValueContext extends ParserRuleContext {
		public TerminalNode INT() { return getToken(SQLParser.INT, 0); }
		public TerminalNode DOUBLE() { return getToken(SQLParser.DOUBLE, 0); }
		public TerminalNode STRING() { return getToken(SQLParser.STRING, 0); }
		public ValueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_value; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SQLVisitor ) return ((SQLVisitor<? extends T>)visitor).visitValue(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ValueContext value() throws RecognitionException {
		ValueContext _localctx = new ValueContext(_ctx, getState());
		enterRule(_localctx, 48, RULE_value);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(217);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 240518168576L) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class InbetweenoperandContext extends ParserRuleContext {
		public InbetweenoperandContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_inbetweenoperand; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SQLVisitor ) return ((SQLVisitor<? extends T>)visitor).visitInbetweenoperand(this);
			else return visitor.visitChildren(this);
		}
	}

	public final InbetweenoperandContext inbetweenoperand() throws RecognitionException {
		InbetweenoperandContext _localctx = new InbetweenoperandContext(_ctx, getState());
		enterRule(_localctx, 50, RULE_inbetweenoperand);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(219);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 1069548032L) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\u0004\u0001&\u00de\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002"+
		"\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007\u0002"+
		"\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b\u0007\u000b\u0002"+
		"\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0002\u000f\u0007\u000f"+
		"\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011\u0002\u0012\u0007\u0012"+
		"\u0002\u0013\u0007\u0013\u0002\u0014\u0007\u0014\u0002\u0015\u0007\u0015"+
		"\u0002\u0016\u0007\u0016\u0002\u0017\u0007\u0017\u0002\u0018\u0007\u0018"+
		"\u0002\u0019\u0007\u0019\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000"+
		"\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000"+
		"\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000"+
		"\u0001\u0000\u0001\u0000\u0003\u0000G\b\u0000\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0005\u0001"+
		"P\b\u0001\n\u0001\f\u0001S\t\u0001\u0001\u0001\u0001\u0001\u0001\u0002"+
		"\u0001\u0002\u0001\u0002\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003"+
		"\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0004\u0001\u0004"+
		"\u0001\u0004\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005"+
		"\u0001\u0005\u0005\u0005k\b\u0005\n\u0005\f\u0005n\t\u0005\u0001\u0005"+
		"\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005"+
		"\u0001\u0005\u0001\u0005\u0001\u0005\u0005\u0005z\b\u0005\n\u0005\f\u0005"+
		"}\t\u0005\u0001\u0005\u0001\u0005\u0001\u0006\u0001\u0006\u0001\u0006"+
		"\u0005\u0006\u0084\b\u0006\n\u0006\f\u0006\u0087\t\u0006\u0001\u0007\u0001"+
		"\u0007\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0005\b\u0091\b"+
		"\b\n\b\f\b\u0094\t\b\u0003\b\u0096\b\b\u0001\b\u0001\b\u0001\t\u0001\t"+
		"\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0005\n\u00a2\b\n\n\n"+
		"\f\n\u00a5\t\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\u000b"+
		"\u0001\u000b\u0001\f\u0001\f\u0001\r\u0001\r\u0001\r\u0001\r\u0001\u000e"+
		"\u0001\u000e\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f"+
		"\u0001\u000f\u0001\u000f\u0005\u000f\u00be\b\u000f\n\u000f\f\u000f\u00c1"+
		"\t\u000f\u0003\u000f\u00c3\b\u000f\u0001\u000f\u0001\u000f\u0001\u0010"+
		"\u0001\u0010\u0001\u0011\u0001\u0011\u0001\u0012\u0001\u0012\u0001\u0013"+
		"\u0001\u0013\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0015\u0001\u0015"+
		"\u0001\u0016\u0001\u0016\u0001\u0016\u0001\u0016\u0001\u0017\u0001\u0017"+
		"\u0001\u0018\u0001\u0018\u0001\u0019\u0001\u0019\u0001\u0019\u0000\u0000"+
		"\u001a\u0000\u0002\u0004\u0006\b\n\f\u000e\u0010\u0012\u0014\u0016\u0018"+
		"\u001a\u001c\u001e \"$&(*,.02\u0000\u0004\u0001\u0000#%\u0001\u0000\u000e"+
		"\u0010\u0002\u0000\f\f\u0011\u0015\u0002\u0000\t\t\u0016\u001d\u00d1\u0000"+
		"F\u0001\u0000\u0000\u0000\u0002H\u0001\u0000\u0000\u0000\u0004V\u0001"+
		"\u0000\u0000\u0000\u0006Y\u0001\u0000\u0000\u0000\ba\u0001\u0000\u0000"+
		"\u0000\nd\u0001\u0000\u0000\u0000\f\u0080\u0001\u0000\u0000\u0000\u000e"+
		"\u0088\u0001\u0000\u0000\u0000\u0010\u008a\u0001\u0000\u0000\u0000\u0012"+
		"\u0099\u0001\u0000\u0000\u0000\u0014\u009b\u0001\u0000\u0000\u0000\u0016"+
		"\u00ac\u0001\u0000\u0000\u0000\u0018\u00ae\u0001\u0000\u0000\u0000\u001a"+
		"\u00b0\u0001\u0000\u0000\u0000\u001c\u00b4\u0001\u0000\u0000\u0000\u001e"+
		"\u00b6\u0001\u0000\u0000\u0000 \u00c6\u0001\u0000\u0000\u0000\"\u00c8"+
		"\u0001\u0000\u0000\u0000$\u00ca\u0001\u0000\u0000\u0000&\u00cc\u0001\u0000"+
		"\u0000\u0000(\u00ce\u0001\u0000\u0000\u0000*\u00d1\u0001\u0000\u0000\u0000"+
		",\u00d3\u0001\u0000\u0000\u0000.\u00d7\u0001\u0000\u0000\u00000\u00d9"+
		"\u0001\u0000\u0000\u00002\u00db\u0001\u0000\u0000\u000045\u0003\u0002"+
		"\u0001\u000056\u0005\u0000\u0000\u00016G\u0001\u0000\u0000\u000078\u0003"+
		"\u0006\u0003\u000089\u0005\u0000\u0000\u00019G\u0001\u0000\u0000\u0000"+
		":;\u0003\n\u0005\u0000;<\u0005\u0000\u0000\u0001<G\u0001\u0000\u0000\u0000"+
		"=>\u0003\u0010\b\u0000>?\u0005\u0000\u0000\u0001?G\u0001\u0000\u0000\u0000"+
		"@A\u0003\u0014\n\u0000AB\u0005\u0000\u0000\u0001BG\u0001\u0000\u0000\u0000"+
		"CD\u0003\u001e\u000f\u0000DE\u0005\u0000\u0000\u0001EG\u0001\u0000\u0000"+
		"\u0000F4\u0001\u0000\u0000\u0000F7\u0001\u0000\u0000\u0000F:\u0001\u0000"+
		"\u0000\u0000F=\u0001\u0000\u0000\u0000F@\u0001\u0000\u0000\u0000FC\u0001"+
		"\u0000\u0000\u0000G\u0001\u0001\u0000\u0000\u0000HI\u0005\u0001\u0000"+
		"\u0000IJ\u0003\"\u0011\u0000JK\u0005 \u0000\u0000KL\u0003(\u0014\u0000"+
		"LQ\u0005\u0002\u0000\u0000MN\u0005\u001f\u0000\u0000NP\u0003(\u0014\u0000"+
		"OM\u0001\u0000\u0000\u0000PS\u0001\u0000\u0000\u0000QO\u0001\u0000\u0000"+
		"\u0000QR\u0001\u0000\u0000\u0000RT\u0001\u0000\u0000\u0000SQ\u0001\u0000"+
		"\u0000\u0000TU\u0003\u0004\u0002\u0000U\u0003\u0001\u0000\u0000\u0000"+
		"VW\u0005!\u0000\u0000WX\u0005\u001e\u0000\u0000X\u0005\u0001\u0000\u0000"+
		"\u0000YZ\u0005\u0003\u0000\u0000Z[\u0003$\u0012\u0000[\\\u0005\u0004\u0000"+
		"\u0000\\]\u0003\"\u0011\u0000]^\u0005 \u0000\u0000^_\u0003*\u0015\u0000"+
		"_`\u0003\b\u0004\u0000`\u0007\u0001\u0000\u0000\u0000ab\u0005!\u0000\u0000"+
		"bc\u0005\u001e\u0000\u0000c\t\u0001\u0000\u0000\u0000de\u0005\u0005\u0000"+
		"\u0000ef\u0003\"\u0011\u0000fg\u0005 \u0000\u0000gl\u0003*\u0015\u0000"+
		"hi\u0005\u001f\u0000\u0000ik\u0003*\u0015\u0000jh\u0001\u0000\u0000\u0000"+
		"kn\u0001\u0000\u0000\u0000lj\u0001\u0000\u0000\u0000lm\u0001\u0000\u0000"+
		"\u0000mo\u0001\u0000\u0000\u0000nl\u0001\u0000\u0000\u0000op\u0005!\u0000"+
		"\u0000pq\u0005\u0006\u0000\u0000qr\u0005 \u0000\u0000rs\u0003\f\u0006"+
		"\u0000s{\u0005!\u0000\u0000tu\u0005\u001f\u0000\u0000uv\u0005 \u0000\u0000"+
		"vw\u0003\f\u0006\u0000wx\u0005!\u0000\u0000xz\u0001\u0000\u0000\u0000"+
		"yt\u0001\u0000\u0000\u0000z}\u0001\u0000\u0000\u0000{y\u0001\u0000\u0000"+
		"\u0000{|\u0001\u0000\u0000\u0000|~\u0001\u0000\u0000\u0000}{\u0001\u0000"+
		"\u0000\u0000~\u007f\u0003\u000e\u0007\u0000\u007f\u000b\u0001\u0000\u0000"+
		"\u0000\u0080\u0085\u00030\u0018\u0000\u0081\u0082\u0005\u001f\u0000\u0000"+
		"\u0082\u0084\u00030\u0018\u0000\u0083\u0081\u0001\u0000\u0000\u0000\u0084"+
		"\u0087\u0001\u0000\u0000\u0000\u0085\u0083\u0001\u0000\u0000\u0000\u0085"+
		"\u0086\u0001\u0000\u0000\u0000\u0086\r\u0001\u0000\u0000\u0000\u0087\u0085"+
		"\u0001\u0000\u0000\u0000\u0088\u0089\u0005\u001e\u0000\u0000\u0089\u000f"+
		"\u0001\u0000\u0000\u0000\u008a\u008b\u0005\u0007\u0000\u0000\u008b\u0095"+
		"\u0003\"\u0011\u0000\u008c\u008d\u0005\b\u0000\u0000\u008d\u0092\u0003"+
		"\u001a\r\u0000\u008e\u008f\u0005\t\u0000\u0000\u008f\u0091\u0003\u001a"+
		"\r\u0000\u0090\u008e\u0001\u0000\u0000\u0000\u0091\u0094\u0001\u0000\u0000"+
		"\u0000\u0092\u0090\u0001\u0000\u0000\u0000\u0092\u0093\u0001\u0000\u0000"+
		"\u0000\u0093\u0096\u0001\u0000\u0000\u0000\u0094\u0092\u0001\u0000\u0000"+
		"\u0000\u0095\u008c\u0001\u0000\u0000\u0000\u0095\u0096\u0001\u0000\u0000"+
		"\u0000\u0096\u0097\u0001\u0000\u0000\u0000\u0097\u0098\u0003\u0012\t\u0000"+
		"\u0098\u0011\u0001\u0000\u0000\u0000\u0099\u009a\u0005\u001e\u0000\u0000"+
		"\u009a\u0013\u0001\u0000\u0000\u0000\u009b\u009c\u0005\n\u0000\u0000\u009c"+
		"\u009d\u0003\"\u0011\u0000\u009d\u009e\u0005\u000b\u0000\u0000\u009e\u00a3"+
		"\u0003\u001a\r\u0000\u009f\u00a0\u0005\u001f\u0000\u0000\u00a0\u00a2\u0003"+
		"\u001a\r\u0000\u00a1\u009f\u0001\u0000\u0000\u0000\u00a2\u00a5\u0001\u0000"+
		"\u0000\u0000\u00a3\u00a1\u0001\u0000\u0000\u0000\u00a3\u00a4\u0001\u0000"+
		"\u0000\u0000\u00a4\u00a6\u0001\u0000\u0000\u0000\u00a5\u00a3\u0001\u0000"+
		"\u0000\u0000\u00a6\u00a7\u0005\b\u0000\u0000\u00a7\u00a8\u0003\u0018\f"+
		"\u0000\u00a8\u00a9\u0005\f\u0000\u0000\u00a9\u00aa\u00030\u0018\u0000"+
		"\u00aa\u00ab\u0003\u0016\u000b\u0000\u00ab\u0015\u0001\u0000\u0000\u0000"+
		"\u00ac\u00ad\u0005\u001e\u0000\u0000\u00ad\u0017\u0001\u0000\u0000\u0000"+
		"\u00ae\u00af\u0005%\u0000\u0000\u00af\u0019\u0001\u0000\u0000\u0000\u00b0"+
		"\u00b1\u0003*\u0015\u0000\u00b1\u00b2\u0005\f\u0000\u0000\u00b2\u00b3"+
		"\u0003\u001c\u000e\u0000\u00b3\u001b\u0001\u0000\u0000\u0000\u00b4\u00b5"+
		"\u0007\u0000\u0000\u0000\u00b5\u001d\u0001\u0000\u0000\u0000\u00b6\u00b7"+
		"\u0005\r\u0000\u0000\u00b7\u00c2\u0003\"\u0011\u0000\u00b8\u00b9\u0005"+
		"\b\u0000\u0000\u00b9\u00bf\u0003,\u0016\u0000\u00ba\u00bb\u00032\u0019"+
		"\u0000\u00bb\u00bc\u0003,\u0016\u0000\u00bc\u00be\u0001\u0000\u0000\u0000"+
		"\u00bd\u00ba\u0001\u0000\u0000\u0000\u00be\u00c1\u0001\u0000\u0000\u0000"+
		"\u00bf\u00bd\u0001\u0000\u0000\u0000\u00bf\u00c0\u0001\u0000\u0000\u0000"+
		"\u00c0\u00c3\u0001\u0000\u0000\u0000\u00c1\u00bf\u0001\u0000\u0000\u0000"+
		"\u00c2\u00b8\u0001\u0000\u0000\u0000\u00c2\u00c3\u0001\u0000\u0000\u0000"+
		"\u00c3\u00c4\u0001\u0000\u0000\u0000\u00c4\u00c5\u0003 \u0010\u0000\u00c5"+
		"\u001f\u0001\u0000\u0000\u0000\u00c6\u00c7\u0005\u001e\u0000\u0000\u00c7"+
		"!\u0001\u0000\u0000\u0000\u00c8\u00c9\u0005%\u0000\u0000\u00c9#\u0001"+
		"\u0000\u0000\u0000\u00ca\u00cb\u0005%\u0000\u0000\u00cb%\u0001\u0000\u0000"+
		"\u0000\u00cc\u00cd\u0007\u0001\u0000\u0000\u00cd\'\u0001\u0000\u0000\u0000"+
		"\u00ce\u00cf\u0003*\u0015\u0000\u00cf\u00d0\u0003&\u0013\u0000\u00d0)"+
		"\u0001\u0000\u0000\u0000\u00d1\u00d2\u0005%\u0000\u0000\u00d2+\u0001\u0000"+
		"\u0000\u0000\u00d3\u00d4\u0003*\u0015\u0000\u00d4\u00d5\u0003.\u0017\u0000"+
		"\u00d5\u00d6\u00030\u0018\u0000\u00d6-\u0001\u0000\u0000\u0000\u00d7\u00d8"+
		"\u0007\u0002\u0000\u0000\u00d8/\u0001\u0000\u0000\u0000\u00d9\u00da\u0007"+
		"\u0000\u0000\u0000\u00da1\u0001\u0000\u0000\u0000\u00db\u00dc\u0007\u0003"+
		"\u0000\u0000\u00dc3\u0001\u0000\u0000\u0000\nFQl{\u0085\u0092\u0095\u00a3"+
		"\u00bf\u00c2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}