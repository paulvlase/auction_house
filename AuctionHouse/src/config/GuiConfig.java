package config;

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
	private static String			LOG_IN_VALUES[]					= { "Log in", "Autentificare" };
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

	/* GuiImpl */
	private static final String		WRONG_USR_PASS_VALUES[]			= {
			"Wrong username or password", "Nume utilizator sau parolă greșită" };

	/* MainWindow */
	private static final String		MENU_VALUES[]					= { "Menu", "Meniu" };
	private static final String		ADD_SERVICE_VALUES[]			= { "Add service",
			"Adaugă serviciu"										};
	private static final String		PROFILE_VALUES[]				= { "Profile", "Profil" };
	private static final String		LOGOUT_VALUES[]					= { "Logut", "Deautentificare" };
	private static final String		EXIT_VALUES[]					= { "Exit", "Ieșire" };
	private static final String		SERVICE_VALUES[]				= { "Service", "Serviciu" };
	private static final String		OFFER_MADE_VALUES[]				= { "Offer made", "Ofertă" };
	private static final String		TIME_VALUES[]					= { "Time", "Timp" };
	private static final String		PRICE_VALUES[]					= { "Price", "Preț" };
	private static final String		STATUS_VALUES[]					= { "Status", "Status" };

	/* Profile editor */
	private static String			PROFILE_TITLE_VALUES[]			= {
			APPLICATION_NAME + " : Profile", APPLICATION_NAME + " : Profil" };
	private static final String		CHANGE_PICTURE_VALUES[]			= {
			"Press image to change your profile picture.", "Apasă pe imagine pentru a o schimba." };
	private static final String		FIRST_NAME_VALUES[]				= { "First name", "Prenume" };
	private static final String		LAST_NAME_VALUES[]				= { "Last name", "Nume" };
	private static final String		NEW_PASSWORD_VALUES[]			= { "New password",
			"Parola noua"											};
	private static final String		LOCATION_VALUES[]				= { "Location", "Locație" };
	private static final String		CANCEL_VALUES[]					= { "Cancel", "Anulează" };
	private static final String		OK_VALUES[]						= { "OK", "OK" };
	private static final String		PASSWORD_ERROR_VALUES[]			= {
			"If you want to change the password you should complete both password fields.",
			"Pentru a schimba parola, trebuie să completați ambele câmpuri" };
	private static final String		PASSWORD_MATCH_ERROR_VALUES[]	= { "Passwords do not match",
			"Parolele nu coincid"									};
	private static final String		FILE_CHOOSER_VALUES[]			= { "JPG & GIF Images",
			"Imagini JPG & GIF"									};

	/* Login form */
	public static final int			USERNAME						= 0;
	public static final int			PASSWORD						= 1;
	public static final int			ROLE							= 2;
	public static final int			BUYER							= 3;
	public static final int			SELLER							= 4;
	public static final int			LOG_IN							= 5;
	public static final int			LANGUAGE						= 6;
	public static final int			ENGLISH							= 7;
	public static final int			ROMANIAN						= 8;
	public static final int			EMPTY_USERNAME_ERROR			= 9;
	public static final int			EMPTY_USERNAME					= 10;
	public static final int			EMPTY_PASSWORD_ERROR			= 11;
	public static final int			EMPTY_PASSWORD					= 12;
	public static final int			LOGIN_TITLE						= 13;

	/* GuiImpl */
	public static final int			WRONG_USR_PASS					= 14;

	/* MainWindow */
	public static final int			MENU							= 15;
	public static final int			ADD_SERVICE						= 16;
	public static final int			PROFILE							= 17;
	public static final int			LOG_OUT							= 18;
	public static final int			EXIT							= 19;
	public static final int			SERVICE							= 20;
	public static final int			OFFER_MADE						= 21;
	public static final int			TIME							= 22;
	public static final int			PRICE							= 23;
	public static final int			STATUS							= 24;

	/* Profile editor */
	public static final int			CHANGE_PICTURE					= 25;
	public static final int			FIRST_NAME						= 26;
	public static final int			LAST_NAME						= 27;
	public static final int			NEW_PASSWORD					= 28;
	public static final int			LOCATION						= 29;
	public static final int			CANCEL							= 30;
	public static final int			OK								= 31;
	public static final int			PROFILE_TITLE					= 32;
	public static final int			PASSWORD_ERROR					= 33;
	public static final int			PASSWORD_MATCH_ERROR			= 34;
	public static final int			FILE_CHOOSER_TILE				= 35;

	/* Other settings for Profile editor */
	public static final int			AVATAR_WIDTH					= 100;
	public static final int			AVATAR_HEIGHT					= 100;
	public static final String		DEFAULT_AVATAR					= "/resources/images/default_avatar.png";

	private static final String		values[][]						= { USERNAME_VALUES,
			PASSWORD_VALUES, ROLES_VALUES, BUYER_VALUES, SELLER_VALUES, LOG_IN_VALUES,
			LANGUAGE_VALUES, ENGLISH_VALUES, ROMANIAN_VALUES, EMPTY_USERNAME_ERROR_VALUES,
			EMPTY_USERNAME_VALUES, EMPTY_PASSWORD_ERROR_VALUES, EMPTY_PASSWORD_VALUES,
			LOGIN_TITLE_VALUES, WRONG_USR_PASS_VALUES, MENU_VALUES, ADD_SERVICE_VALUES,
			PROFILE_VALUES, LOGOUT_VALUES, EXIT_VALUES, SERVICE_VALUES, OFFER_MADE_VALUES,
			TIME_VALUES, PRICE_VALUES, STATUS_VALUES, CHANGE_PICTURE_VALUES, FIRST_NAME_VALUES,
			LAST_NAME_VALUES, NEW_PASSWORD_VALUES, LOCATION_VALUES, CANCEL_VALUES, OK_VALUES,
			PROFILE_TITLE_VALUES, PASSWORD_ERROR_VALUES,PASSWORD_MATCH_ERROR_VALUES, FILE_CHOOSER_VALUES };

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
