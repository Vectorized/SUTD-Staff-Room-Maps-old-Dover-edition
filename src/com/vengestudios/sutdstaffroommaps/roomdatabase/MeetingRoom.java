package com.vengestudios.sutdstaffroommaps.roomdatabase;

/**
 * Represents a Meeting Room
 */
public class MeetingRoom extends Room{
	public String roomName;

	/**
	 * Constructor
	 * @param level    The level the room is in
	 * @param roomId   The id of the room
	 * @param roomName The name of the room
	 */
	public MeetingRoom(int level, String roomId, String roomName) {
		super();
		this.level = level;
		this.roomId = roomId;
		this.roomName = roomName;
	}

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("Room Type: Meeting Room\n");
		stringBuilder.append("Level: ");
		stringBuilder.append(this.level);
		stringBuilder.append("\nRoom Id: ");
		stringBuilder.append(this.roomId);
		stringBuilder.append("\nRoom Name: ");
		stringBuilder.append(this.roomName);
		stringBuilder.append("\n----------------------------------------------");
		return stringBuilder.toString();
	}

	@Override
	public String toAutoCompleteString () {
		return this.roomName;
	}

}
