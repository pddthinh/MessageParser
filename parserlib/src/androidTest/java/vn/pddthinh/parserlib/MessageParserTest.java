package vn.pddthinh.parserlib;

import junit.framework.TestCase;

import org.json.JSONObject;
import org.junit.Test;

/**
 * Created by pddthinh on 8/29/15.
 */
public class MessageParserTest extends TestCase {
	/**
	 * [0] - case description (index)
	 * [1] - input message
	 * [2] - expected JSON string
	 */
	String[][] mData = new String[][]{
			{"No0", null, null},
			{"No1", "", null},
			{"No2", "Hello", null},
			{"No3",
					"@bob @john (success) such a cool feature; https://twitter.com/jdorfman/status/430511497475670016",
					"{\"mentions\":[\"bob\",\"john\"]," +
							"\"emoticons\":[\"success\"]," +
							"\"links\":[" +
							"{\"url\":\"https://twitter.com/jdorfman/status/430511497475670016\"," +
							"\"title\":\"WT\"}" +
							"]" +
							"}"

			},
			{"No4",
					"Hi @bob (success) such a cool feature; https://twitter.com/jdorfman (aaa) @john @mike pls test",
					"{\"mentions\":[\"bob\",\"john\",\"mike\"]," +
							"\"emoticons\":[\"success\",\"aaa\"]," +
							"\"links\":[" +
							"{\"url\":\"https://twitter.com/jdorfman\"," +
							"\"title\":\"WT\"}" +
							"]" +
							"}"

			},
	};

	@Test
	public void testParse() throws Exception {
		MessageParser parser = new MessageParser();

		String jsonRet;
		JSONObject jExp;
		JSONObject jResult;
		for (String[] data : mData) {
			jsonRet = parser.parse(data[1]);
			if (data[2] == null) {
				assertNull(data[0], jsonRet);
				continue;
			}

			jExp = new JSONObject(data[2]);
			jResult = new JSONObject(jsonRet);

			assertEquals(data[0], jExp.toString(), jResult.toString());
		}
	}
}