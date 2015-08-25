package com.vengestudios.sutdstaffroommaps.apphelpers;

import java.util.ArrayList;

import com.vengestudios.sutdstaffroommaps.generalhelpers.AssetFileReader;
import com.vengestudios.sutdstaffroommaps.generalhelpers.StringHelper;
import com.vengestudios.sutdstaffroommaps.roomdatabase.Room;
import com.vengestudios.sutdstaffroommaps.roomdatabase.RoomDatabase;

import android.content.Context;

/**
 * A helper class to get the HTML for the auto-correct suggestion page.
 */
public class SuggestionsHTMLPreparer {

	private static final String SUGGESTION_LINK_LEFT  = "<div class=\"suggestion\" onclick=\"search(this.innerHTML);\">";
	private static final String SUGGESTION_LINK_RIGHT = "</div>";
	private static final String SUGGESTIONS_FILENAME  = "suggestions.html";
	private static final String NO_RESULTS_FILENAME   = "no_results.html";

	/**
	 * Creates and return the HTML for the suggestion page due to a search term
	 * that does not yield exact matches
	 * @param context       The application's context
	 * @param roomDatabase  The RoomDatabase holding the room data
	 * @param searchString  The search String used
	 * @return              The HTML to be displayed in the WebView
	 */
	public static String getSuggestionHTML (Context context, RoomDatabase roomDatabase, String searchString) {
		StringBuilder suggestionsPageHTML = AssetFileReader.getStringBuilderFromFile(context, SUGGESTIONS_FILENAME);
		ArrayList<Room> closestMatches = roomDatabase.getClosestRoomMatches(searchString);
		StringBuilder suggestionLinks = new StringBuilder();
		for (int i=0; i<closestMatches.size(); ++i) {
			Room room = closestMatches.get(i);
			suggestionLinks.append(SUGGESTION_LINK_LEFT);
			suggestionLinks.append(room.toAutoCompleteString());
			suggestionLinks.append(SUGGESTION_LINK_RIGHT);
		}
		StringHelper.replaceFirstInstanceInStringBuilder("#SUGGESTIONS#", suggestionLinks.toString(), suggestionsPageHTML);
		return suggestionsPageHTML.toString();
	}

	/**
	 * Creates and return the HTML for the no results page due to a search term
	 * that does not yield exact matches and is too short to get suggestions for
	 * @param context  The application's context
	 * @return         The HTML to be displayed in the WebView
	 */
	public static String getNoResultsHTML (Context context) {
		StringBuilder noResultsPageHTML = AssetFileReader.getStringBuilderFromFile(context, NO_RESULTS_FILENAME);
		return noResultsPageHTML.toString();
	}

}
