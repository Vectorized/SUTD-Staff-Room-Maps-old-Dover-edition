package com.vengestudios.sutdstaffroommaps.roomdatabase;

/**
 * Represents a Staff Room
 */
public class StaffRoom extends Room {

	public String staffName;
	public String staffTitle;
	public boolean hasTitle;
	public boolean occupied;

	/**
	 * Constructor
	 * @param level      The level the room is in
	 * @param roomId     The id of the room
	 * @param staffName  The name of the room's occupant
	 * @param staffTitle The title of the room's occupant
	 */
	public StaffRoom(int level, String roomId, String staffName, String staffTitle) {
		this(level, roomId, staffName, staffTitle, !staffName.equals("0"), !staffTitle.equals("0"));
	}

	/**
	 * Constructor
	 * @param level      The level the room is in
	 * @param roomId     The id of the room
	 * @param staffName  The name of the room's occupant
	 * @param staffTitle The title of the room's occupant
	 * @param occupied   Whether the room has an occupant
	 * @param hasTitle   Whether the room's occupant has a title
	 */
	public StaffRoom(int level, String roomId, String staffName, String staffTitle, boolean occupied, boolean hasTitle) {
		super();
		this.level 		= level;
		this.roomId 	= roomId;
		this.staffName 	= staffName;
		this.staffTitle = staffTitle;
		this.occupied 	= occupied;
		this.hasTitle	= hasTitle;
	}

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("Room Type: Staff Room\n");
		stringBuilder.append("Level: ");
		stringBuilder.append(this.level);
		stringBuilder.append("\nRoom Id: ");
		stringBuilder.append(this.roomId);
		stringBuilder.append("\nStaff Name: ");
		stringBuilder.append(this.staffName);
		stringBuilder.append("\nStaff Title: ");
		stringBuilder.append(this.staffTitle);
		stringBuilder.append("\n----------------------------------------------");
		return stringBuilder.toString();
	}

	@Override
	public String toAutoCompleteString() {
		if (!occupied) return this.roomId + " - Level " + this.level + " (Unoccupied)";
		return this.roomId + " - Level " + this.level + " (" + this.staffName +")";
	}

	/**
	 * @return The String representation of the room's Id
	 */
	public String getRoomIdString() {
		return this.roomId;
	}

	/**
	 * @return The String representation of the room's level
	 */
	public String getLevelLocationString() {
		return "LEVEL" + this.level;
	}

}
