package com.vengestudios.sutdstaffroommaps.roomdatabase;

/**
 * An abstract class that represents a Room
 */
public abstract class Room implements Comparable<Room> {

	public String roomId;
	public int level;
	public int wordSimilarity;

	/**
	 * Constructor
	 */
	public Room() {
		wordSimilarity = -1;
	}

	/**
	 * @return The auto-complete search string for this room
	 */
	public abstract String toAutoCompleteString();

	@Override
	public int compareTo(Room o) {
		return wordSimilarity-o.wordSimilarity;
	}

}
