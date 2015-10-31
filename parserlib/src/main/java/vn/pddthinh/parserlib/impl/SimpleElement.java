package vn.pddthinh.parserlib.impl;

import android.util.Log;

import org.json.JSONArray;

/**
 * Simple element handler
 * <p/>
 * Created by pddthinh on 8/23/15.
 */
public abstract class SimpleElement extends Element {
	protected SimpleElement(EData type, String name) {
		super(type, name);
	}

	@Override
	protected void processElement(String value, JSONArray data) throws Exception {
		do {
			if (value == null || value.length() == 0) {
				Log.w(TAG, "Ignored empty element ...");
				break;
			}

			// just save value into the element list
			data.put(value);
		}
		while (false);
	}
}
