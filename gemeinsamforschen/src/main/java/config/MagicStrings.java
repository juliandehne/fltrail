package config;


/**
 * contains the main configuration for the project which are defined in
 * evidenceserver.properties
 */
public class MagicStrings {


	public static final boolean CREATETESTDB = false;
	public static final String DB = "db";

	public static final String GROUPRULESPATH = PropUtil.getProp("groupRulesPath");
	public static String GERMANMODELLOCATION2 = PropUtil.getProp("stanfordlibrarypath");

	// optional properties
	public static String GERMANETPATH = PropUtil.getProp("germanetpath");
	public static String GERMANETFREQUENCIESPATH = PropUtil.getProp("germanetfrequencies");

	public static final String thesaurusLogin = PropUtil
			.getProp("thesaurusLogin");
	public static final String thesaurusPassword = PropUtil
			.getProp("thesaurusPassword");
	public static final String thesaurusDatabaseName = PropUtil
			.getProp("thesaurusDatabaseName");
	public static final String thesaurusDatabaseUrl = PropUtil
			.getProp("thesaurusDatabaseUrl");

	public static final String loggingDBName = PropUtil.getProp("loggingDBName");
	public static final String testDBName = PropUtil.getProp("testDBName");



}
