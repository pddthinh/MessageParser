package vn.pddthinh.parserlib.impl;

import android.util.Log;

import org.json.JSONArray;

import java.util.regex.Pattern;

/**
 * Emoticon element handler
 * <p/>
 * Created by pddthinh on 8/23/15.
 */
public class Emoticon extends SimpleElement {
	public Emoticon() {
		super(EData.EMOTICON, "emoticons");
	}

	@Override
	protected Pattern getPattern() {
		// syntax: find anything between the ( and ) character
		return Pattern.compile("\\(([^\\(\\)]*?)\\)");
	}

	@Override
	protected void processElement(String value, JSONArray data) throws Exception {
		do {
			if (value == null)
				break;

			// check the correctness of parsed element
			int length = value.length();
			if (length <= 0 || length > 15) {
				Log.w(TAG, "Element length is incorrect!");
				break;
			}

			// normal processing
			super.processElement(value, data);
		}
		while (false);
	}
}