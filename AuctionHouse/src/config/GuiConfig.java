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

	public static final String		APPLICATION_NAME					= "Auction House";

	/* All available languages */
	public static final int			ENGLISH_LANGUAGE					= 0;
	public static final int			ROMANIAN_LANGUAGE					= 1;

	public static int				CURRENT_LANGUAGE					= ENGLISH_LANGUAGE;

	/* Login form */
	private static String			LOGIN_TITLE_VALUES[]				= {
			APPLICATION_NAME + " : Login", APPLICATION_NAME + " : Autentificare" };
	private static String			USERNAME_VALUES[]					= { "Username",
			"Utilizator"												};
	private static String			PASSWORD_VALUES[]					= { "Password", "Parolă" };
	private static String			ROLES_VALUES[]						= { "Role", "Rol" };
	private static String			BUYER_VALUES[]						= { "Buyer", "Cumpărător" };
	private static String			SELLER_VALUES[]						= { "Seller", "Furnizor" };
	private static String			LOG_IN_VALUES[]						= { "Log in",
			"Autentificare"											};
	private static String			LANGUAGE_VALUES[]					= { "Language", "Limbă" };
	private static String			ENGLISH_VALUES[]					= { "English", "Engleză" };
	private static String			ROMANIAN_VALUES[]					= { "Romanian", "Română" };
	private static String			EMPTY_USERNAME_ERROR_VALUES[]		= {
			"Username field is empty, please fill it with a valid username.",
			"Nu ați introdus niciun utilizator, introduce-ți unul."	};
	private static String			EMPTY_USERNAME_VALUES[]				= { "Empty username",
			"Lipsa utilizatorului"										};
	private static String			EMPTY_PASSWORD_ERROR_VALUES[]		= {
			"Password field is empty, please fill it with your password.",
			"Nu ați introdus nici o parolă."							};
	private static String			EMPTY_PASSWORD_VALUES[]				= { "Empty password",
			"Lipsa parolei"											};

	public static final String[][]	LANGUAGES							= { ENGLISH_VALUES,
			ROMANIAN_VALUES											};

	/* GuiImpl */
	private static final String		WRONG_USR_PASS_VALUES[]				= {
			"Wrong username or password", "Nume utilizator sau parolă greșită" };

	/* MainWindow */
	private static String			BUYER_TITLE_VALUES[]				= {
			APPLICATION_NAME + " : Buyer", APPLICATION_NAME + " : Cumpărător" };
	private static String			SELLER_TITLE_VALUES[]				= {
			APPLICATION_NAME + " : Seller", APPLICATION_NAME + " : Vânzător" };
	private static final String		MENU_VALUES[]						= { "Menu", "Meniu" };
	private static final String		ADD_SERVICE_VALUES[]				= { "Add service",
			"Adaugă serviciu"											};
	private static final String		PROFILE_VALUES[]					= { "Profile", "Profil" };
	private static final String		LOGOUT_VALUES[]						= { "Logut",
			"Deautentificare"											};
	private static final String		EXIT_VALUES[]						= { "Exit", "Ieșire" };
	private static final String		SERVICE_VALUES[]					= { "Service", "Serviciu" };
	private static final String		OFFER_STATUS_VALUES[]				= { "Offer status",
			"Status ofertă"											};
	private static final String		TIME_VALUES[]						= { "Time", "Timp" };
	private static final String		PRICE_VALUES[]						= { "Price", "Preț" };
	private static final String		STATUS_VALUES[]						= { "Status", "Status" };
	private static final String		LAUNCH_OFFER_VALUES[]				= { "Launch Offer Request",
			"Lansează cerere ofertă"									};
	private static final String		DROP_OFFER_VALUES[]					= { "Drop Offer Request",
			"Renunță la ofertă"										};
	private static final String		ACCEPT_OFFER_VALUES[]				= { "Accept Offer",
			"Acceptă ofertă"											};
	private static final String		REFUSE_OFFER_VALUES[]				= { "Refuse Offer",
			"Refuză oferta"											};
	private static final String		MAKE_OFFER_VALUES[]					= { "Make Offer",
			"Fă o ofertă"												};
	private static final String		DROP_AUCTION_VALUES[]				= { "Drop auction",
			"Renunță la licitație"										};
	private static final String		REMOVE_SERIVCE_VALUES[]				= { "Remove Service",
			"Șterge serviciu"											};

	/* Profile editor */
	private static String			PROFILE_TITLE_VALUES[]				= {
			APPLICATION_NAME + " : Profile", APPLICATION_NAME + " : Profil" };
	private static final String		CHANGE_PICTURE_VALUES[]				= {
			"Press image to change your profile picture.", "Apasă pe imagine pentru a o schimba." };
	private static final String		FIRST_NAME_VALUES[]					= { "First name", "Prenume" };
	private static final String		LAST_NAME_VALUES[]					= { "Last name", "Nume" };
	private static final String		NEW_PASSWORD_VALUES[]				= { "New password",
			"Parola noua"												};
	private static final String		LOCATION_VALUES[]					= { "Location", "Locație" };
	private static final String		CANCEL_VALUES[]						= { "Cancel", "Anulează" };
	private static final String		OK_VALUES[]							= { "OK", "OK" };
	private static final String		PASSWORD_ERROR_VALUES[]				= {
			"If you want to change the password you should complete both password fields.",
			"Pentru a schimba parola, trebuie să completați ambele câmpuri" };
	private static final String		PASSWORD_MATCH_ERROR_VALUES[]		= {
			"Passwords do not match", "Parolele nu coincid"			};
	private static final String		FILE_CHOOSER_VALUES[]				= { "JPG & GIF Images",
			"Imagini JPG & GIF"										};

	/* AddNewService */
	private static final String		NAME_VALUES[]						= { "Name", "Nume" };
	private static final String		EMPTY_SERVICE_NAME_VALUES[]			= { "Empty service name",
			"Nume serviciu necompletat"								};
	private static final String		EMPTY_SERVICE_NAME_ERROR_VALUES[]	= {
			"Service name seems to be empty, please complete it.",
			"Numele serviciului nu a fost completat."					};
	private static final String		SERVICE_TWICE_ERROR_VALUES[]		= {
			"Entered service seems to be already in your list of services",
			"Serviciul introdus pare a fi prezent in lista ta de servicii" };
	private static final String		PRICE_ERROR_VALUES[]				= {
			"Price should be a number.", "Prețul trebuie să fie un număr" };

	/* MyTableCellRender */
	private static final String		DONE_VALUES[]						= { "Done", "Terminat" };
	private static final String		CANCELED_VALUES[]					= { "Canceled", "Anulat" };

	/* NewPrice */
	private static final String		NEW_PRICE_VALUES[]					= { "New price", "Preț nou" };

	/* Service */
	private static final String		INACTIVE_VALUES[]					= { "Inactive", "Inactiv" };
	private static final String		ACTIVE_VALUES[]						= { "Active", "Activ" };
	private static final String		TRANSFER_STARTED_VALUES[]			= { "Transfer started",
			"Transfer a început"										};
	private static final String		TRANSFER_IN_PROGRESS_VALUES[]		= { "Transfer in prgress",
			"Transfer în progres"										};
	private static final String		TRANSFER_COMPLETE_VALUES[]			= { "Transfer complete",
			"Transfer complet"											};

	/* Login form */
	public static final int			USERNAME							= 0;
	public static final int			PASSWORD							= 1;
	public static final int			ROLE								= 2;
	public static final int			BUYER								= 3;
	public static final int			SELLER								= 4;
	public static final int			LOG_IN								= 5;
	public static final int			LANGUAGE							= 6;
	public static final int			ENGLISH								= 7;
	public static final int			ROMANIAN							= 8;
	public static final int			EMPTY_USERNAME_ERROR				= 9;
	public static final int			EMPTY_USERNAME						= 10;
	public static final int			EMPTY_PASSWORD_ERROR				= 11;
	public static final int			EMPTY_PASSWORD						= 12;
	public static final int			LOGIN_TITLE							= 13;

	/* GuiImpl */
	public static final int			WRONG_USR_PASS						= 14;

	/* MainWindow */
	public static final int			MENU								= 15;
	public static final int			ADD_SERVICE							= 16;
	public static final int			PROFILE								= 17;
	public static final int			LOG_OUT								= 18;
	public static final int			EXIT								= 19;
	public static final int			SERVICE								= 20;
	public static final int			OFFER_STATUS						= 21;
	public static final int			TIME								= 22;
	public static final int			PRICE								= 23;
	public static final int			STATUS								= 24;
	public static final int			BUYER_TITLE							= 25;
	public static final int			SELLER_TITLE						= 26;
	public static final int			LAUNCH_OFFER						= 27;
	public static final int			DROP_OFFER							= 28;
	public static final int			ACCEPT_OFFER						= 29;
	public static final int			REFUSE_OFFER						= 30;
	public static final int			MAKE_OFFER							= 31;
	public static final int			DROP_AUCTION						= 32;
	public static final int			REMOVE_SERVICE						= 33;

	/* Profile editor */
	public static final int			CHANGE_PICTURE						= 34;
	public static final int			FIRST_NAME							= 35;
	public static final int			LAST_NAME							= 36;
	public static final int			NEW_PASSWORD						= 37;
	public static final int			LOCATION							= 38;
	public static final int			CANCEL								= 39;
	public static final int			OK									= 40;
	public static final int			PROFILE_TITLE						= 41;
	public static final int			PASSWORD_ERROR						= 42;
	public static final int			PASSWORD_MATCH_ERROR				= 43;
	public static final int			FILE_CHOOSER_TILE					= 44;

	/* AddNewService */
	public static final int			NAME								= 45;
	public static final int			EMPTY_SERVICE_NAME					= 46;
	public static final int			EMPTY_SERVICE_NAME_ERROR			= 47;
	public static final int			SERVICE_TWICE_ERROR					= 48;
	public static final int			PRICE_ERROR							= 49;

	/* MyTableCellRender */
	public static final int			DONE								= 50;
	public static final int			CANCELED							= 51;

	/* NewPrice */
	public static final int			NEW_PRICE							= 52;

	/* Service */
	public static final int			INACTIVE							= 53;
	public static final int			ACTIVE								= 54;
	public static final int			TRANSFER_STARTED					= 55;
	public static final int			TRANSFER_IN_PROGRESS				= 56;
	public static final int			TRANSFER_COMPLETE					= 57;

	/* Other settings for Profile editor */
	public static final int			AVATAR_WIDTH						= 100;
	public static final int			AVATAR_HEIGHT						= 100;
	public static final String		DEFAULT_AVATAR						= "resources/images/default_avatar.png";

	public static final String		AUCTION_ICON64						= "resources/images/auction64x64.png";
	public static final String		AUCTION_ICON32						= "resources/images/auction32x32.png";
	public static final String		AUCTION_ICON16						= "resources/images/auction16x16.png";

	/* Other settings for MainWindow */
	public static final int			MINI_AVATAR_WIDTH					= 40;
	public static final int			MINI_AVATAR_HEIGHT					= 40;
	public static final String		ADD_ICON16							= "resources/images/add16x16.png";
	public static final String		ADD_ICON32							= "resources/images/add32x32.png";
	public static final String		ADD_ICON48							= "resources/images/add48x48.png";
	public static final String		LAUNCH_OFFER_ICON					= "resources/images/launch_offer.png";
	public static final String		DROP_OFFER_ICON						= "resources/images/drop_offer.png";
	public static final String		LOGOUT_ICON							= "resources/images/logout.png";
	public static final String		PROFILE_ICON16						= "resources/images/profile16x16.png";
	public static final String		PROFILE_ICON32						= "resources/images/profile32x32.png";
	public static final String		PROFILE_ICON48						= "resources/images/profile48x48.png";
	public static final String		EXIT_ICON							= "resources/images/exit.png";
	public static final String		REFUSE_OFFER_ICON					= "resources/images/refuse_offer.png";
	public static final String		ACCEPT_OFFER_ICON					= "resources/images/accept_offer.png";
	public static final String		MAKE_OFFER_ICON						= "resources/images/make_offer.png";
	public static final String		DROP_AUCTION_ICON					= "resources/images/drop_offer.png";

	private static final String		values[][]							= { USERNAME_VALUES,
			PASSWORD_VALUES, ROLES_VALUES, BUYER_VALUES, SELLER_VALUES, LOG_IN_VALUES,
			LANGUAGE_VALUES, ENGLISH_VALUES, ROMANIAN_VALUES, EMPTY_USERNAME_ERROR_VALUES,
			EMPTY_USERNAME_VALUES, EMPTY_PASSWORD_ERROR_VALUES, EMPTY_PASSWORD_VALUES,
			LOGIN_TITLE_VALUES, WRONG_USR_PASS_VALUES, MENU_VALUES, ADD_SERVICE_VALUES,
			PROFILE_VALUES, LOGOUT_VALUES, EXIT_VALUES, SERVICE_VALUES, OFFER_STATUS_VALUES,
			TIME_VALUES, PRICE_VALUES, STATUS_VALUES, BUYER_TITLE_VALUES, SELLER_TITLE_VALUES,
			LAUNCH_OFFER_VALUES, DROP_OFFER_VALUES, ACCEPT_OFFER_VALUES, REFUSE_OFFER_VALUES,
			MAKE_OFFER_VALUES, DROP_AUCTION_VALUES, REMOVE_SERIVCE_VALUES, CHANGE_PICTURE_VALUES,
			FIRST_NAME_VALUES, LAST_NAME_VALUES, NEW_PASSWORD_VALUES, LOCATION_VALUES,
			CANCEL_VALUES, OK_VALUES, PROFILE_TITLE_VALUES, PASSWORD_ERROR_VALUES,
			PASSWORD_MATCH_ERROR_VALUES, FILE_CHOOSER_VALUES, NAME_VALUES,
			EMPTY_SERVICE_NAME_VALUES, EMPTY_SERVICE_NAME_ERROR_VALUES, SERVICE_TWICE_ERROR_VALUES,
			PRICE_ERROR_VALUES, DONE_VALUES, CANCELED_VALUES, NEW_PRICE_VALUES, INACTIVE_VALUES,
			ACTIVE_VALUES, TRANSFER_STARTED_VALUES, TRANSFER_IN_PROGRESS_VALUES,
			TRANSFER_COMPLETE_VALUES									};

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
