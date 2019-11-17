package token;

import java.util.ArrayList;

public class TokensGerados {
	private static ArrayList<Token> tokens;
	
	public ArrayList<Token> getTokensGerados() {
		return this.tokens;
	}
	
	public static void setTokens(Token token) {
		tokens.add(token);
	}

}
