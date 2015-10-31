package vn.pddthinh.parserlib.impl;

import junit.framework.TestCase;

import org.json.JSONObject;

/**
 * Created by pddthinh on 8/27/15.
 */
public class LinkTest extends TestCase {
	/**
	 * [0] - case description (index)
	 * [1] - input message
	 * [2] - expected JSON string
	 */
	String[][] mData = new String[][]{
			{"No0-normal",
					"http://www.google.com.vn",
					"{\"links\":[{\"title\":\"WT\"," +
							"\"url\":\"http:\\/\\/www.google.com.vn\"}]}"},

			{"No1", "no link", null},

			{"No2-string end",
					"Olympics are starting soon; ftp://www.nbcolympics.com ",
					"{\"links\":[{\"title\":\"WT\"," +
							"\"url\":\"ftp:\\/\\/www.nbcolympics.com\"}]}"},

			{"No3-between string with params",
					"Olympics are starting soon; https://www.nbcolympics.com/?a=x&b=c bla bla bla...",
					"{\"links\":[{\"title\":\"WT\"," +
							"\"url\":\"https:\\/\\/www.nbcolympics.com\\/?a=x&b=c\"}]}"},

			{"No4-no http",
					"Olympics are starting soon; www.nbcolympics.com hihih",
					"{\"links\":[{\"title\":\"WT\"," +
							"\"url\":\"www.nbcolympics.com\"}]}"},

			{"No5-http + IP",
					"Olympics are starting soon; http://10.1.2.3 hihih",
					"{\"links\":[{\"title\":\"WT\"," +
							"\"url\":\"http:\\/\\/10.1.2.3\"}]}"},

			{"No6-http + IP + params",
					"Olympics are starting soon; http://10.1.2.3/abc.php?x=10&_bc hihihi",
					"{\"links\":[{\"title\":\"WT\"," +
							"\"url\":\"http:\\/\\/10.1.2.3\\/abc.php?x=10&_bc\"}]}"},
	};

	public void testParse() throws Exception {
		Link link = new Link();

		JSONObject ret;
		for (String[] data : mData) {
			ret = link.parse(data[1]);
			if (data[2] == null) {
				assertNull(data[0], ret);
				continue;
			}

			assertEquals(data[0], data[2], ret.toString());
		}
	}
}