package vn.pddthinh.parserlib.impl;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Message element parser
 * <p/>
 * Created by pddthinh on 8/23/15.
 */
public abstract class Element {
	protected String TAG = null;
	protected EData mType = EData.NONE;

	protected String mName = null;

	/**
	 * Constructor
	 *
	 * @param type Element type @EData
	 * @param name Element name (JSON keyword)
	 */
	protected Element(EData type, String name) {
		TAG = this.getClass().getSimpleName();

		mType = type;
		mName = name;
	}

	/**
	 * Get the regex pattern of this segment
	 *
	 * @return @Pattern use to parse this element
	 */
	protected abstract Pattern getPattern();

	/**
	 * Process the parsed result
	 *
	 * @param value Element value
	 * @param data  Result JSON list (of other elements) of the input
	 */
	protected abstract void processElement(String value, JSONArray data) throws Exception;

	/**
	 * Parse input message for a specific element structure
	 *
	 * @param message Input message
	 * @return @JSONObject of result
	 */
	public JSONObject parse(String message) {
		JSONObject ret = null;

		do {
			if (message == null || message.length() == 0)
				break;

			if (mName == null || mName.length() == 0) {
				Log.e(TAG, "Element name is empty!");
				break;
			}

			Pattern sPattern = getPattern();
			if (sPattern == null) {
				Log.e(TAG, "Invalid pattern!");
				break;
			}

			Matcher matcher = sPattern.matcher(message);

			// parse the segment data
			JSONArray jsonRet = new JSONArray();

			try {
				String item;
				while (matcher.find()) {
					item = matcher.group(1);

					processElement(item, jsonRet);
				} // while

				// check for empty result
				if (jsonRet.length() == 0)
					break;

				ret = new JSONObject();
				ret.put(mName, jsonRet);
			}
			catch (Exception ex) {
				Log.e(TAG, "Parse failed: " + ex.getMessage());
				break;
			}
		}
		while (false);

		return ret;
	}
}
