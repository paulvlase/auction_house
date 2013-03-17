package config;

import java.util.HashMap;

/**
 * Configurations for GuiConfig.
 * 
 * @author Paul Vlase <vlase.paul@gmail.com>
 * @author Ghennadi Procopciuc
 */
public class GuiConfig {

	/* GUI languages */
	enum Language {
		ENGLISH, ROMANIAN
	};

	public static final String		APPLICATION_NAME				= "Auction House";

	/* All available languages */
	public static final int			ENGLISH_LANGUAGE				= 0;
	public static final int			ROMANIAN_LANGUAGE				= 1;

	public static int				CURRENT_LANGUAGE				= ENGLISH_LANGUAGE;

	/* Login form */
	private static String			LOGIN_TITLE_VALUES[]			= {
			APPLICATION_NAME + " : Login", APPLICATION_NAME + " : Autentificare" };
	private static String			USERNAME_VALUES[]				= { "Username", "Utilizator" };
	private static String			PASSWORD_VALUES[]				= { "Password", "Parolă" };
	private static String			ROLES_VALUES[]					= { "Role", "Rol" };
	private static String			BUYER_VALUES[]					= { "Buyer", "Cumpărător" };
	private static String			SELLER_VALUES[]					= { "Seller", "Furnizor" };
	private static String			SIGN_IN_VALUES[]				= { "Sign in", "Autentificare" };
	private static String			LANGUAGE_VALUES[]				= { "Language", "Limbă" };
	private static String			ENGLISH_VALUES[]				= { "English", "Engleză" };
	private static String			ROMANIAN_VALUES[]				= { "Romanian", "Română" };
	private static String			EMPTY_USERNAME_ERROR_VALUES[]	= {
			"Username field is empty, please fill it with a valid username.",
			"Nu ați introdus niciun utilizator, introduce-ți unul." };
	private static String			EMPTY_USERNAME_VALUES[]			= { "Empty username",
			"Lipsa utilizatorului"									};
	private static String			EMPTY_PASSWORD_ERROR_VALUES[]	= {
			"Password field is empty, please fill it with your password.",
			"Nu ați introdus nici o parolă."						};
	private static String			EMPTY_PASSWORD_VALUES[]			= { "Empty password",
			"Lipsa parolei"										};

	public static final String[][]	LANGUAGES						= { ENGLISH_VALUES,
			ROMANIAN_VALUES										};

	/* Login form */
	public static final int			USERNAME						= 0;
	public static final int			PASSWORD						= 1;
	public static final int			ROLE							= 2;
	public static final int			BUYER							= 3;
	public static final int			SELLER							= 4;
	public static final int			SIGN_IN							= 5;
	public static final int			LANGUAGE						= 6;
	public static final int			ENGLISH							= 7;
	public static final int			ROMANIAN						= 8;
	public static final int			EMPTY_USERNAME_ERROR			= 9;
	public static final int			EMPTY_USERNAME					= 10;
	public static final int			EMPTY_PASSWORD_ERROR			= 11;
	public static final int			EMPTY_PASSWORD					= 12;
	public static final int			LOGIN_TITLE						= 13;

	private static final String		values[][]						= { USERNAME_VALUES,
			PASSWORD_VALUES, ROLES_VALUES, BUYER_VALUES, SELLER_VALUES, SIGN_IN_VALUES,
			LANGUAGE_VALUES, ENGLISH_VALUES, ROMANIAN_VALUES, EMPTY_USERNAME_ERROR_VALUES,
			EMPTY_USERNAME_VALUES, EMPTY_PASSWORD_ERROR_VALUES, EMPTY_PASSWORD_VALUES,
			LOGIN_TITLE_VALUES										};

	public static int getLanguage() {
		return CURRENT_LANGUAGE;
	}

	public static void setLanguage(int language) {
		CURRENT_LANGUAGE = language;
	}

	public static String getValue(int key) {
		if (values[key].length <= CURRENT_LANGUAGE) {
			System.err.println("No value availaible for : '" + values[key] + "' in "
					+ CURRENT_LANGUAGE + " language");
			System.exit(1);
		}

		return values[key][CURRENT_LANGUAGE];
	}
}
