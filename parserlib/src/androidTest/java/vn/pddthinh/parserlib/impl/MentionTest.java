package vn.pddthinh.parserlib.impl;

import junit.framework.TestCase;

import org.json.JSONObject;
import org.junit.Test;

/**
 * Created by pddthinh on 8/26/15.
 */
public class MentionTest extends TestCase {
	/**
	 * [0] - case description (index)
	 * [1] - input message
	 * [2] - expected JSON string
	 */
	String[][] mData = new String[][]{
			{"No0", "Good morning!", null},

			{"No1",
					"@ Good @# morning @#Thinh! (megusta) (coffee)",
					null},

			{"No2",
					"@Thinh Good morning! (megusta) (coffee) Good night!",
					"{\"mentions\":[\"Thinh\"]}"},

			{"No3",
					"@Thinh-#- Good morning! (megusta) (coffee) Good night!",
					"{\"mentions\":[\"Thinh\"]}"},

			{"No4",
					"@Thinh",
					"{\"mentions\":[\"Thinh\"]}"},

			{"No5",
					"1@Thinh",
					"{\"mentions\":[\"Thinh\"]}"},

			{"No6",
					" @Thinh",
					"{\"mentions\":[\"Thinh\"]}"},

			{"No7",
					"@Thinh1",
					"{\"mentions\":[\"Thinh1\"]}"},

			{"No8",
					"@Thinh ",
					"{\"mentions\":[\"Thinh\"]}"},

			{"No9",
					"@Thinh @Thinh hurry up!",
					"{\"mentions\":[\"Thinh\",\"Thinh\"]}"},

			// NOTE: due to the demo scope so I do not support this case! It is possible to give result [Thinh, Mike]
			{"No***",
					"Hello @Thinh@Mike Good morning! (megusta) (coffee) Good night!",
					"{\"mentions\":[\"Thinh\"]}"},
	};

	@Test
	public void testParse() throws Exception {
		Mention mt = new Mention();

		JSONObject ret;
		for (String[] line : mData) {
			ret = mt.parse(line[1]);
			if (line[2] == null) {
				assertNull(line[0], ret);
				continue;
			}

			assertEquals(line[0], line[2], ret.toString());
		}
	}
}