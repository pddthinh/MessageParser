package vn.pddthinh.parserlib.impl;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.regex.Pattern;

/**
 * Link (URL) element handler
 * <p/>
 * Created by pddthinh on 8/23/15.
 */
public class Link extends Element {
	private static final String KEY_URL = "url";
	private static final String KEY_TITLE = "title";

	public Link() {
		super(EData.URL, "links");
	}

	protected Pattern getPattern() {
		// copied from SO, also had some modifies :)
		return Pattern.compile("^?(((https?|ftp|gopher|telnet|file)://|www\\.)[\\w\\d\\.#@%/;$()~_?\\+-=\\\\&]*)$?");
	}

	@Override
	protected void processElement(String value, JSONArray data) throws Exception {
		do {
			if (value == null || value.length() == 0) {
				Log.w(TAG, "Ignore empty element!");
				break;
			}

			// fetch the website title
			String title = getSiteTitle(value);

			// compile the json object
			JSONObject link = new JSONObject();
			link.put(KEY_URL, value);
			link.put(KEY_TITLE, title);

			data.put(link);
		}
		while (false);
	}

	/**
	 * Fetch the website title
	 *
	 * @param url Website URL
	 */
	private String getSiteTitle(String url) throws Exception {
		// demo string only
		return "WT";
	}
}
