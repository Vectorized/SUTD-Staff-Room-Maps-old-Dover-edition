package com.vengestudios.sutdstaffroommaps.apphelpers;

import com.vengestudios.sutdstaffroommaps.generalhelpers.AssetFileReader;
import com.vengestudios.sutdstaffroommaps.generalhelpers.StringHelper;

import android.content.Context;
import android.util.SparseArray;

/**
 * A helper class to get the HTML for the maps for the different levels
 */
public class LevelHTMLGetter {
	private static final SparseArray<String> LEVEL_MAP_HTML;

	private static final String SEARCH_RESULT_ZOOM_LEVEL;
	private static final String NORMAL_ZOOM_LEVEL;

	private static final String ZOOM_NEEDLE;
	private static final String ROOM_ID_NEEDLE;
	private static final String LEVEL_NEEDLE;
	private static final String MAP_HTML_NEEDLE;

	private static final String MAP_FILENAME;

	static {
		LEVEL_MAP_HTML = new SparseArray<String>();
		LEVEL_MAP_HTML.put(2, "<img src=\"images/l2.svg\" width=\"1150\" height=\"298\"/>");
		LEVEL_MAP_HTML.put(3, "<img src=\"images/l3.svg\" width=\"1151\" height=\"435\"/>");
		LEVEL_MAP_HTML.put(4, "<img src=\"images/l4.svg\" width=\"1150\" height=\"435\"/>");

		SEARCH_RESULT_ZOOM_LEVEL 	= "2";
		NORMAL_ZOOM_LEVEL 			= "1";

		ZOOM_NEEDLE 			= "#ZOOM";
		ROOM_ID_NEEDLE 			= "#ROOM_ID#";
		LEVEL_NEEDLE 			= "#LEVEL#";
		MAP_HTML_NEEDLE 		= "#MAP_HTML#";

		MAP_FILENAME 			= "map.html";
	}

	/**
	 * Creates and return the HTML to be displayed in a WebView for the map
	 * after searching for a room
	 * @param context The application context
	 * @param level   The level
	 * @param roomId  The room to put the map pointer on
	 *                (The room found for the search)
	 * @return The HTML for the map
	 */
	public static String getHTMLforLevelWithRoom(Context context, int level, String roomId) {
		StringBuilder floorHTMLStringBuilder
			= AssetFileReader.getStringBuilderFromFile(context, MAP_FILENAME);
		StringHelper.replaceFirstInstanceInStringBuilder(
				ZOOM_NEEDLE, SEARCH_RESULT_ZOOM_LEVEL, floorHTMLStringBuilder);
		StringHelper.replaceFirstInstanceInStringBuilder(
				ROOM_ID_NEEDLE, roomId, floorHTMLStringBuilder);
		StringHelper.replaceFirstInstanceInStringBuilder(
				LEVEL_NEEDLE, ""+level, floorHTMLStringBuilder);
		StringHelper.replaceFirstInstanceInStringBuilder(
				MAP_HTML_NEEDLE, LEVEL_MAP_HTML.get(level), floorHTMLStringBuilder);
		return floorHTMLStringBuilder.toString();
	}

	/**
	 * Creates and return the HTML to be displayed in a WebView for the map
	 * @param context The application context
	 * @param level   The level
	 * @return The HTML for the map
	 */
	public static String getHTMLforLevel(Context context, int level) {
		StringBuilder floorHTMLStringBuilder
			= AssetFileReader.getStringBuilderFromFile(context, MAP_FILENAME);
		StringHelper.replaceFirstInstanceInStringBuilder(
				ZOOM_NEEDLE, NORMAL_ZOOM_LEVEL, floorHTMLStringBuilder);
		StringHelper.replaceFirstInstanceInStringBuilder(
				LEVEL_NEEDLE, ""+level, floorHTMLStringBuilder);
		StringHelper.replaceFirstInstanceInStringBuilder(
				MAP_HTML_NEEDLE, LEVEL_MAP_HTML.get(level), floorHTMLStringBuilder);
		return floorHTMLStringBuilder.toString();
	}

}
