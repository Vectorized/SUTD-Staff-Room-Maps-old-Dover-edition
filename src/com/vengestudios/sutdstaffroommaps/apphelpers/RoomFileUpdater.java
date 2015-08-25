package com.vengestudios.sutdstaffroommaps.apphelpers;

import com.loopj.android.http.*;
import com.vengestudios.sutdstaffroommaps.MainActivity;
import com.vengestudios.sutdstaffroommaps.generalhelpers.Version;

/**
 * A class to update the local preferences file to store the latest
 * room data
 */
public class RoomFileUpdater {

	private MainActivity mainActivity;
	private Version roomFileVersion;

	/**
	 * Constructor
	 *
	 * Creates an instance of RoomFileUpdater
	 *
	 * @param mainActivity  The main activity
	 */
	public RoomFileUpdater(MainActivity mainActivity) {
		this.mainActivity = mainActivity;
	}

	public void updateRoomFile(String roomFileVersionString){
		roomFileVersion = new Version(roomFileVersionString);
        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://r-factorblog.com/sutd_staff_room_app/room_file_version_info.txt", new GetRoomFileVersionHandler(this));
	}

	/**
	 * An handler to execute code after receiving a successful HTTP response from
	 * the server to get the latest version number of the room data file
	 */
	private static class GetRoomFileVersionHandler extends AsyncHttpResponseHandler {

		private RoomFileUpdater fileUpdater;

		public GetRoomFileVersionHandler (RoomFileUpdater fileUpdater) {
			this.fileUpdater = fileUpdater;
		}

		@Override
		public void onSuccess(String response) {
			String whiteSpaceRemovedResponse = response.replaceAll("\\s", "");
			Version serverRoomFileVersion = new Version(whiteSpaceRemovedResponse);
			if(serverRoomFileVersion.compareTo(fileUpdater.roomFileVersion)>0) {
		        AsyncHttpClient client = new AsyncHttpClient();
		        client.get("http://r-factorblog.com/sutd_staff_room_app/room_file.txt",
		        		new GetRoomFileHandler(this.fileUpdater, whiteSpaceRemovedResponse));
			}
		}
	}

	/**
	 * An handler to execute code after receiving a successful HTTP response from
	 * the server to get the room data file
	 */
	private static class GetRoomFileHandler extends AsyncHttpResponseHandler {

		private RoomFileUpdater fileUpdater;
		private String     		serverRoomFileVersionString;

		public GetRoomFileHandler (RoomFileUpdater fileUpdater, String serverRoomFileVersionString) {
			this.fileUpdater = fileUpdater;
			this.serverRoomFileVersionString = serverRoomFileVersionString;
		}

		@Override
		public void onSuccess(String response) {
			fileUpdater.mainActivity.initializeRoomDataBaseWith(response, serverRoomFileVersionString);
		}
	}

}




