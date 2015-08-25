package com.vengestudios.sutdstaffroommaps.generalhelpers;

/**
 * A helper class for some String operations
 */
public class StringHelper {

	/**
	 * Replaces all instances of a target String with the replacement String
	 * inside the StringBuilder
	 * @param target      The target String
	 * @param replacement The replacement String
	 * @param builder     The StringBuilder
	 */
	public static void replaceFirstInstanceInStringBuilder(String target, String replacement, StringBuilder builder) {
		int indexOfTarget = -1;
		if( ( indexOfTarget = builder.indexOf( target ) ) >= 0 ) {
			builder.replace( indexOfTarget, indexOfTarget + target.length() , replacement );
		}
	}

	/**
	 * Returns a new String with all non space whitespace removed
	 * @param string  The original String
	 * @return        The new String
	 */
	public static String removeNonSpaceWhiteSpace(String string) {
		return string.replaceAll("[\\t\\r\\n\\f]", "");
	}

}
