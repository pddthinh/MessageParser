package vn.pddthinh.parserlib;

import org.json.JSONObject;

import java.util.Iterator;

import vn.pddthinh.parserlib.impl.EData;
import vn.pddthinh.parserlib.impl.Element;
import vn.pddthinh.parserlib.impl.Emoticon;
import vn.pddthinh.parserlib.impl.Link;
import vn.pddthinh.parserlib.impl.Mention;

/**
 * Message parser handler
 * Created by pddthinh on 8/23/15.
 */
public class MessageParser {
	/**
	 * Parse input message for supported elements in JSON format
	 *
	 * @param message Input message
	 * @return JSON format of elements
	 */
	public String parse(String message) {
		String strJson = null;

		try {
			strJson = parseJSON(message).toString().replaceAll("\\\\/", "/");
		}
		catch (Exception ex) {
		}

		return strJson;
	}

	/**
	 * Parse input message to get JSON data
	 *
	 * @param message Input message
	 * @return @JSONObject
	 */
	public JSONObject parseJSON(String message) {
		JSONObject ret = null;

		do {
			if (message == null || message.length() == 0)
				break;

			JSONObjEx jRet = new JSONObjEx();

			JSONObject eRet;
			Element element;
			for (EData e : EData.values()) {
				switch (e) {
					case EMOTICON:
						element = new Emoticon();
						break;

					case MENTION:
						element = new Mention();
						break;

					case URL:
						element = new Link();
						break;

					default:
						element = null;
						break;
				} // switch
				if (element == null)
					continue;

				// parse the element
				eRet = element.parse(message);
				if (eRet == null)
					continue;

				jRet.copyFrom(eRet);
			}
			if (jRet.length() == 0)
				break;

			try {
				ret = new JSONObject(jRet.toString());
			}
			catch (Exception ex) {
			}
		}
		while (false);

		return ret;
	}

	/**
	 * Wrapper JSONObject
	 */
	private class JSONObjEx extends JSONObject {
		/**
		 * Copy properties from other JSONObject
		 *
		 * @param from External JSONObject
		 * @return this object
		 */
		JSONObjEx copyFrom(JSONObject from) {
			do {
				if (from == null)
					break;

				try {
					String key;
					Iterator<String> keys = from.keys();
					while (keys.hasNext()) {
						key = keys.next();

						put(key, from.get(key));
					}
				}
				catch (Exception ex) {
				}
			}
			while (false);

			return this;
		}
	}
}
