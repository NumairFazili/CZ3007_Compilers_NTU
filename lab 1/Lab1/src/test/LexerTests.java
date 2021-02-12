package test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.StringReader;

import lexer.Lexer;

import org.junit.Test;

import frontend.Token;
import frontend.Token.Type;
import static frontend.Token.Type.*;

/**
 * This class contains unit tests for your lexer. Currently, there is only one test, but you
 * are strongly encouraged to write your own tests.
 */
public class LexerTests {
	// helper method to run tests; no need to change this
	private final void runtest(String input, Token... output) {
		Lexer lexer = new Lexer(new StringReader(input));
		int i=0;
		Token actual, expected;
		try {
			do {
				assertTrue(i < output.length);
				expected = output[i++];
				try {
					actual = lexer.nextToken();
					assertEquals(expected, actual);
				} catch(Error e) {
					if(expected != null)
						fail(e.getMessage());
					return;
				}
			} while(!actual.isEOF());
		} catch (IOException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	/** Example unit test. */
	@Test
	public void testKWs() {
		// first argument to runtest is the string to lex; the remaining arguments
		// are the expected tokens
		runtest("module false return while",
				new Token(MODULE, 0, 0, "module"),
				new Token(FALSE, 0, 7, "false"),
				new Token(RETURN, 0, 13, "return"),
				new Token(WHILE, 0, 20, "while"),
				new Token(EOF, 0, 25, ""));
	}

	@Test
	public void testStringLiteralWithDoubleQuote() {
		runtest("\"\"\"",
				new Token(STRING_LITERAL, 0, 0, ""),
				(Token)null);
	}

	@Test
	public void testStringLiteral() {
		runtest("\"\\n\"", 
				new Token(STRING_LITERAL, 0, 0, "\\n"),
				new Token(EOF, 0, 4, ""));

	}
	
	@Test
	public void testStringLiteralBackSlash() {
		runtest("\"\\\"", 
				new Token(STRING_LITERAL, 0, 0, "\\"),
				new Token(EOF, 0, 3, ""));
	}
	
	@Test
	public void testWhileLoop() {
		runtest("number=0 \nwhile (number == 100) \nnumber=number+1",
				new Token(ID, 0, 0, "number"),
				new Token(EQL, 0, 6, "="),
				new Token(INT_LITERAL, 0, 7, "0"),
				new Token(WHILE, 1, 0, "while"),
				new Token(LPAREN, 1, 6, "("),
				new Token(ID, 1, 7, "number"),
				new Token(EQEQ, 1, 14, "=="),
				new Token(INT_LITERAL, 1, 17, "100"),
				new Token(RPAREN, 1, 20, ")"),
				new Token(ID, 2, 0, "number"),
				new Token(EQL, 2, 6, "="),
				new Token(ID, 2, 7, "number"),
				new Token(PLUS, 2, 13, "+"),
				new Token(INT_LITERAL, 2, 14, "1"),
				new Token(EOF, 2, 15, ""));
	}
	
	
	@Test
	public void testifElse() {
		runtest("if number <= 100 \nreturn true \nelse return false",
				new Token(IF, 0, 0, "if"),
				new Token(ID, 0, 3, "number"),
				new Token(LEQ, 0, 10, "<="),
				new Token(INT_LITERAL, 0, 13, "100"),
				new Token(RETURN , 1, 0, "return"),
				new Token(TRUE , 1, 7, "true"),
				new Token(ELSE , 2, 0, "else"),
				new Token(RETURN , 2, 5, "return"),
				new Token(FALSE , 2, 12, "false"),
				new Token(EOF, 2,17 , ""));
	}
	
	
	@Test
	public void testKeywords1() {
		runtest("boolean break else if import int public return true type \nvoid",
				new Token(BOOLEAN, 0, 0, "boolean"),
				new Token(BREAK, 0, 8, "break"),
				new Token(ELSE, 0, 14, "else"),
				new Token(IF, 0, 19, "if"),
				new Token(IMPORT, 0, 22, "import"),
				new Token(INT, 0, 29, "int"),
				new Token(PUBLIC, 0, 33, "public"),
				new Token(RETURN, 0, 40, "return"),
				new Token(TRUE, 0, 47, "true"),
				new Token(TYPE, 0, 52, "type"),
				new Token(VOID, 1, 0, "void"),
				new Token(EOF, 1, 4, ""));
	}
	
	@Test
	public void testKeywords2() {
		runtest("boolean \nbreak \nelse \nif \nimport \n",
				new Token(BOOLEAN, 0, 0, "boolean"),
				new Token(BREAK, 1, 0, "break"),
				new Token(ELSE, 2, 0, "else"),
				new Token(IF, 3, 0, "if"),
				new Token(IMPORT, 4, 0, "import"),
				new Token(EOF, 5, 0, ""));
	}
	
	@Test
	public void testPunctuation() {
		runtest("(\n[\n{\n;\n,",
				new Token(LPAREN, 0, 0, "("),
				new Token(LBRACKET, 1, 0, "["),
				new Token(LCURLY, 2, 0, "{"),
				new Token(SEMICOLON, 3, 0, ";"),
				new Token(COMMA, 4, 0, ","),
				new Token(EOF, 4, 1, ""));
	}
	
	@Test
	public void testOperators() {
		runtest("/\n==\n=\n>=\n>\n<=\n<\n-\n!=\n+\n*",
				new Token(DIV, 0, 0, "/"),
				new Token(EQEQ, 1, 0, "=="),
				new Token(EQL, 2, 0, "="),
				new Token(GEQ, 3, 0, ">="),
				new Token(GT, 4, 0, ">"),
				new Token(LEQ, 5, 0, "<="),
				new Token(LT, 6, 0, "<"),
				new Token(MINUS, 7, 0, "-"),
				new Token(NEQ, 8, 0, "!="),
				new Token(PLUS, 9, 0, "+"),
				new Token(TIMES, 10, 0, "*"),
				new Token(EOF, 10, 1, ""));
	}
}
