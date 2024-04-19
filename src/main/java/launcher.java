//import org.antlr.v4.runtime.CharStream;
//import org.antlr.v4.runtime.CommonTokenStream;
//import org.antlr.v4.runtime.tree.ParseTree;
//
//import java.io.IOException;
//
//import static org.antlr.v4.runtime.CharStreams.fromFileName;
//
//public class launcher {
//
//
//    public static void main(String[] args) {
//        try{
//            String source = "src/main/java/test.txt";
//            CharStream cs = fromFileName(source);
//            SQLLexer lexer = new SQLLexer(cs);
//            CommonTokenStream token = new CommonTokenStream(lexer);
//            SQLParser parser = new SQLParser(token);
//            ParseTree tree = parser.parse();
//
//            myVisitor visitor = new myVisitor();
//            visitor.visit(tree);
//
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//
//
//    }
//}