package vn.pddthinh.parserlib.impl;

import java.util.regex.Pattern;

/**
 * Mention element handler
 * <p/>
 * Created by pddthinh on 8/23/15.
 */
public class Mention extends SimpleElement {
	public Mention() {
		super(EData.MENTION, "mentions");
	}

	@Override
	protected Pattern getPattern() {
		// syntax: find group of characters between @ and a non-word or at end of string
		return Pattern.compile("@([\\w]*?)(\\W|$)");
	}
}
