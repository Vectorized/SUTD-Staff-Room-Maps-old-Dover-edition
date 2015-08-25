package com.vengestudios.sutdstaffroommaps.roomdatabase;

import android.annotation.SuppressLint;
import android.util.SparseArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.vengestudios.sutdstaffroommaps.generalhelpers.CustomSorts;
import com.vengestudios.wordsimilarity.WordSimilarityCalculator;


/**
 * A class used to hold and manage the data of the rooms
 * to facilitate searching
 */
@SuppressLint({ "UseSparseArrays", "DefaultLocale" })
public class RoomDatabase {

	ArrayList<Room> rooms;
	SparseArray<HashMap<String, Room>>roomSparseArray;

	public static final int NUMBER_OF_CLOSEST_SUGGESTIONS = 10;

	public static final String ROOM_REGEX_STRING = "(([Mm][Rr])|([Ss])|([Rr]))[0-9]{1,2}";
	public static final String POSSIBLE_LEVEL_STRING = "(?<!\\d)\\d(?!\\d)";

	public static final String MEETING_ROOM_LEVEL_PREFIX = "MEETING_ROOM_LEVEL_";
	public static final String STAFF_ROOM_LEVEL_PREFIX = "STAFF_ROOM_LEVEL_";

	/**
	 * Factory Constructor
	 *
	 * Attempts to parse the room text file string into an instance of
	 * a RoomDatabase and return it.
	 *
	 * @param roomTextFileString
	 * @return The corresponding RoomDatabase instance if the parsing
	 * has no errors, else null
	 */
	public static RoomDatabase createRoomDatabase(String roomTextFileString) {
		try {
			ArrayList<Room> preRooms = new ArrayList<Room>();
			SparseArray<HashMap<String,Room>> preRoomHashmap = new SparseArray<HashMap<String,Room>>();
			for (int i=2; i<=4; ++i) {
				HashMap<String,Room> levelMap = new HashMap<String, Room>();
				preRoomHashmap.put(i, levelMap);

				String startIndexString = STAFF_ROOM_LEVEL_PREFIX+i+"[";
				int startIndex = roomTextFileString.indexOf(startIndexString);
				int startIndexOffset = startIndexString.length();
				if (startIndex!=-1) {
					int endIndex = roomTextFileString.indexOf("]", startIndex+1);
					String roomString = roomTextFileString.substring(startIndex+startIndexOffset, endIndex);
					String [] lines = roomString.split("\\|\\|");
					for (int j=0; j<lines.length; ++j) {
						String line = lines[j];
						String [] attributes = line.split("\\|");
						if (attributes.length>=3) {
							StaffRoom staffRoom = new StaffRoom(i, attributes[0], attributes[1], attributes[2]);
							preRooms.add(staffRoom);
							levelMap.put(attributes[0], staffRoom);
						}
					}
				}

				startIndexString = MEETING_ROOM_LEVEL_PREFIX+i+"[";
				startIndex = roomTextFileString.indexOf(startIndexString);
				startIndexOffset = startIndexString.length();
				if (startIndex!=-1) {
					int endIndex = roomTextFileString.indexOf("]", startIndex+1);
					String roomString = roomTextFileString.substring(startIndex+startIndexOffset, endIndex);
					String [] lines = roomString.split("\\|\\|");
					for (int j=0; j<lines.length; ++j) {
						String line = lines[j];
						String [] attributes = line.split("\\|");
						MeetingRoom meetingRoom = new MeetingRoom(i, attributes[0], attributes[1]);
						preRooms.add(meetingRoom);
						levelMap.put(attributes[0], meetingRoom);
					}
				}
			}
			return new RoomDatabase(preRooms, preRoomHashmap);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Private Constructor
	 *
	 * Creates an instance of RoomDatabase.
	 *
	 * @param rooms           An ArrayList of all the rooms.
	 * @param roomSparseArray A SparseArray of the RoomId Strings to Room relationships
	 *                        for the different floors
	 */
	private RoomDatabase(ArrayList<Room> rooms, SparseArray<HashMap<String, Room>> roomSparseArray) {
		this.rooms = rooms;
		this.roomSparseArray = roomSparseArray;
	}

	/**
	 * @return The String representation of this RoomDatabase
	 */
	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		for (int i=0; i<rooms.size(); ++i) {
			Room room = rooms.get(i);
			stringBuilder.append(room+"\n");
		}
		return stringBuilder.toString();
	}

	/**
	 * @return An array of Strings to be passed into an ArrayAdapter for an
	 *         AutoCompleteTextView
	 */
	public String[] getAutoCompleteList() {
		int roomCount = rooms.size();
		ArrayList<String> autoCompleteArrayList = new ArrayList<String>();

		for (int i=0; i<roomCount; ++i) {
			Room room = rooms.get(i);
			if (room instanceof StaffRoom) {
				StaffRoom staffRoom = (StaffRoom) room;
				if (staffRoom.occupied == true) {
					autoCompleteArrayList.add(staffRoom.staffName);
				}
			}
		}
		for (int i=0; i<roomCount; ++i) {
			Room room = rooms.get(i);
			if (room instanceof MeetingRoom) {
				MeetingRoom meetingRoom = (MeetingRoom) room;
				autoCompleteArrayList.add(meetingRoom.toAutoCompleteString());
			}
		}

		for (int i=0; i<roomCount; ++i) {
			Room room = rooms.get(i);
			if (room instanceof StaffRoom) {
				StaffRoom staffRoom = (StaffRoom) room;
				autoCompleteArrayList.add(staffRoom.toAutoCompleteString());
			}
		}

		int finalArraySize = autoCompleteArrayList.size();
		String[] autoCompleteArray = new String[finalArraySize];
		for (int i=0; i<finalArraySize; ++i) {
			autoCompleteArray[i] = autoCompleteArrayList.get(i);
		}
		return autoCompleteArray;
	}

	/**
	 * @param searchString The search String
	 * @return The corresponding Room if a definite match is available,
	 *         else null
	 */
	public Room getDefiniteSearchMatch(String searchString) {
		Room searchResult = getRoomWithName(searchString);
		if (searchResult != null) { return searchResult; }
		Matcher m1 = Pattern.compile(ROOM_REGEX_STRING).matcher(searchString);
		if (m1.find()) {
			String possibleRoomId = m1.group();
			Matcher m2 = Pattern.compile(POSSIBLE_LEVEL_STRING).matcher(searchString.substring(m1.end()));
			if (m2.find()){
				String possibleLevel = m2.group();
				return getRoomInLevelWithId(
						Integer.parseInt(possibleLevel), possibleRoomId.toUpperCase());
			}
		}
		return null;
	}

	/**
	 * @param level
	 * @param roomId
	 * @return The Room in the level with the RoomId if there is one,
	 *         else null
	 */
	private StaffRoom getRoomInLevelWithId(int level, String roomId) {
		for (int i=0; i<rooms.size(); ++i) {
			Room room = rooms.get(i);
			if (room instanceof StaffRoom) {
				StaffRoom staffRoom = (StaffRoom) room;
				if (staffRoom.roomId.equalsIgnoreCase(roomId) && staffRoom.level==level) {
					return staffRoom;
				}
			}
		}
		return null;
	}

	/**
	 * @param name The name of the Room
	 * @return The corresponding Room with the name if there is one,
	 *         else null
	 */
	private Room getRoomWithName(String name) {
		for (int i=0; i<rooms.size(); ++i) {
			Room room = rooms.get(i);
			if (room instanceof StaffRoom) {
				StaffRoom staffRoom = (StaffRoom) room;
				if (staffRoom.staffName.equalsIgnoreCase(name)) {
					return staffRoom;
				}
			} else if (room instanceof MeetingRoom) {
				MeetingRoom meetingRoom = (MeetingRoom) room;
				if (meetingRoom.roomName.equalsIgnoreCase(name)) {
					return room;
				}
			}
		}
		return null;
	}

	/**
	 * Using the level and roomId as hashing keys,
	 * returns the Room without iterative search
	 * @param level
	 * @param roomId
	 * @return The corresponding Room
	 */
	public Room getRoomInLevelWithIdForKeys(int level, String roomId) {
		return roomSparseArray.get(level).get(roomId);
	}

	/**
	 * @param searchString
	 * @return An ArrayList of Rooms that matches the search string the closest
	 */
	public ArrayList<Room> getClosestRoomMatches(String searchString) {
		searchString = searchString.trim();
		String searchStringLower = searchString.toLowerCase();
		String searchStringUpper = searchString.toUpperCase();
		int roomCount = rooms.size();

		for (int i=0; i<rooms.size(); ++i) {
			Room room = rooms.get(i);
			if (room instanceof StaffRoom) {
				StaffRoom staffRoom = (StaffRoom) room;
				int nameSimilarityScore = (staffRoom.occupied) ?
						WordSimilarityCalculator.phraseSimilarity(searchStringLower, staffRoom.staffName.toLowerCase()) : 0;
				int locationSimilarityScore =
						Math.max( WordSimilarityCalculator.wordSimilarity(searchStringUpper, staffRoom.getRoomIdString()),
								  WordSimilarityCalculator.wordSimilarity(searchStringUpper, staffRoom.getLevelLocationString()));
				if (locationSimilarityScore>nameSimilarityScore) {
					staffRoom.wordSimilarity = locationSimilarityScore;
				} else {
					staffRoom.wordSimilarity = nameSimilarityScore;
				}
			} else if (room instanceof MeetingRoom) {
				MeetingRoom meetingRoom = (MeetingRoom) room;
				int similarityScore = WordSimilarityCalculator.wordSimilarity(searchStringLower, meetingRoom.roomName.toLowerCase());
				meetingRoom.wordSimilarity = similarityScore;
			}
		}

		int k = roomCount-NUMBER_OF_CLOSEST_SUGGESTIONS;
		CustomSorts.quickPartialSortToKthAsec(rooms, k-1);

		ArrayList<Room> closestMatches = new ArrayList<Room>(NUMBER_OF_CLOSEST_SUGGESTIONS);
		for (int i=k; i<roomCount; ++i) {
			closestMatches.add(rooms.get(i));
		}
		CustomSorts.insertionSortDesc(closestMatches);
		return closestMatches;

	}

}
