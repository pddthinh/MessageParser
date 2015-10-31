package vn.pddthinh.parserlib.impl;

import junit.framework.TestCase;

import org.json.JSONObject;

/**
 * Emoticon testcase
 * <p/>
 * Created by pddthinh on 8/24/15.
 */
public class EmoticonTest extends TestCase {
	/**
	 * [0] - case description (index)
	 * [1] - input message
	 * [2] - expected JSON string
	 */
	String[][] mData = new String[][]{
			{"No0", "Good morning!", null},

			{"No1",
					"Good morning! (megusta) (coffee)",
					"{\"emoticons\":[\"megusta\",\"coffee\"]}"},

			{"No2",
					"Good morning! (megusta) (coffee) Good night!",
					"{\"emoticons\":[\"megusta\",\"coffee\"]}"},

			{"No3",
					"Good morning! (megusta) Good night! (coffee)",
					"{\"emoticons\":[\"megusta\",\"coffee\"]}"},

			{"No4",
					"(megusta) Good night! (coffee)",
					"{\"emoticons\":[\"megusta\",\"coffee\"]}"},

			{"No5",
					"Good morning! (megusta(coffee)) Good night!",
					"{\"emoticons\":[\"coffee\"]}"},

			{"No6",
					"() Good night! (coffee)",
					"{\"emoticons\":[\"coffee\"]}"},

			{"No7", "()()Good night!", null},

			{"No8", "(Good night!", null},

			{"No9",
					"(0123456789abcdefghy)(hehe)Good night!",
					"{\"emoticons\":[\"hehe\"]}"},

			{"No10",
					"(OK)(hehe)Good night!",
					"{\"emoticons\":[\"OK\",\"hehe\"]}"},

			{"No11",
					"(OK)",
					"{\"emoticons\":[\"OK\"]}"},
	};

	public void testParse() throws Exception {
		Emoticon em = new Emoticon();

		JSONObject ret;
		for (String[] line : mData) {
			ret = em.parse(line[1]);
			if (line[2] == null) {
				assertNull(line[0], ret);
				continue;
			}

			assertEquals(line[0], line[2], ret.toString());
		}
	}
}