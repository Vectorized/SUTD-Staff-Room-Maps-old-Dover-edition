package com.vengestudios.sutdstaffroommaps;

import com.vengestudios.sutdstaffroommaps.R;
import com.vengestudios.sutdstaffroommaps.apphelpers.LevelHTMLGetter;
import com.vengestudios.sutdstaffroommaps.apphelpers.RoomFileUpdater;
import com.vengestudios.sutdstaffroommaps.apphelpers.SuggestionsHTMLPreparer;
import com.vengestudios.sutdstaffroommaps.generalhelpers.AssetFileReader;
import com.vengestudios.sutdstaffroommaps.generalhelpers.FocusHelper;
import com.vengestudios.sutdstaffroommaps.generalhelpers.StringHelper;
import com.vengestudios.sutdstaffroommaps.roomdatabase.MeetingRoom;
import com.vengestudios.sutdstaffroommaps.roomdatabase.Room;
import com.vengestudios.sutdstaffroommaps.roomdatabase.RoomDatabase;
import com.vengestudios.sutdstaffroommaps.roomdatabase.StaffRoom;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnLongClickListener;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

/**
 * The Main Activity of the application.
 *
 * This app is an hybrid app - part of it is WebView based, part of it is
 * Java based.
 *
 * So, not all the code is in Java. The JavaScript files in the asset folder
 * generates the HTML layouts for the maps.
 *
 * P.S. Pardon me if you find that the code was not well written at parts.
 * Much of it was sort of hacked together over a weekend due to some
 * very tight deadlines. Future ISTD folks beware.
 *
 * Anything... WhatsApp me - Kang Yue Sheng Benjamin (96270682)
 */
@SuppressLint({ "SetJavaScriptEnabled", "NewApi" })
public class MainActivity extends Activity {

    private static final Boolean CLEAR_PREF_FILES = false; // For testing

    // General UI Elements
    //
    private WebView               mapView;
    private Button                searchButton;
    private AutoCompleteTextView  searchField;
    private TextView              roomFileVersionText;
    private ImageView             descriptionOverlay;
    private ImageView             descriptionOverlay2;
    private TextView              roomIdText;
    private TextView              staffNameText;

    private Button                staffNameScrollActivator;
    private TextView              staffTitleText;
    private TextView              meetingRoomNameText;

    private ImageView             searchClearButtonImage;
    private Button                searchClearButton;

    private Button[]              levelButtonsVertical;
    private Button[]              levelButtonsHorizontal;
    private TextView              levelButtonsTextVertical;
    private TextView              levelButtonsTextHorizontal;

    private int                   selectedLevel;

    private boolean               mapViewZoomEnabled;

    private RoomDatabase          roomDatabase;

    private RoomFileUpdater       roomFileUpdater;

    private boolean               searchFieldIsFocused;
    private boolean               searchClearButtonVisible;
    private int                   searchFieldTextLength;

    // The UI elements for the tutorial
    // Do note that the tutorial arrows are images
    // that contain both the arrows and the text
    //
    private RelativeLayout        tutorialLayout;
    private ImageView             tutorialUpArrow;
    private ImageView             tutorialDownArrowVert;
    private ImageView             tutorialdownArrowHori;
    private boolean               tutorialArrowsVisible;
    private boolean               tutorialArrowsExist;

    private ImageView             suggestionFadeGradientView;
    private boolean               suggestionFadeGradientViewVisible;

    private static final int SUGGESTIVE_SEARCH_MIN_SEARCH_LENGTH = 3;

    private static final boolean SHOW_ROOM_FILE_VERSION = false; // For testing

    private static final String PREFS_NAME        = "sutd_map_prefs_001";
    private static final String DEFAULT_ROOM_FILE = "room_file.txt";

    private MainActivity mainActivity;

    // Gets called when the application first starts
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        getWindow().setBackgroundDrawable(null);

        mainActivity = this;

        if (CLEAR_PREF_FILES) {
            clearPrefFiles(); // Clears the preferences files
                              // for testing purposes if needed
        }

        // Creates references to all the UI elements
        mapView             = (WebView)              findViewById(R.id.mapView);
        searchButton        = (Button)               findViewById(R.id.searchButton);
        searchField         = (AutoCompleteTextView) findViewById(R.id.searchField);
        roomFileVersionText = (TextView)             findViewById(R.id.level_text);

        descriptionOverlay       = (ImageView) findViewById(R.id.description_overlay);
        descriptionOverlay2      = (ImageView) findViewById(R.id.description_overlay2);
        roomIdText               = (TextView)  findViewById(R.id.room_id);
        staffNameText            = (TextView)  findViewById(R.id.staff_name);
        staffNameScrollActivator = (Button)    findViewById(R.id.scroll_staff_name);
        staffTitleText           = (TextView)  findViewById(R.id.staff_title);
        meetingRoomNameText      = (TextView)  findViewById(R.id.meeting_room_name);

        levelButtonsVertical       = new Button[5];
        levelButtonsVertical[2]    = (Button)   findViewById(R.id.level_2_btn_v);
        levelButtonsVertical[3]    = (Button)   findViewById(R.id.level_3_btn_v);
        levelButtonsVertical[4]    = (Button)   findViewById(R.id.level_4_btn_v);
        levelButtonsTextVertical   = (TextView) findViewById(R.id.level_text_v);
        levelButtonsHorizontal     = new Button[5];
        levelButtonsHorizontal[2]  = (Button)   findViewById(R.id.level_2_btn_h);
        levelButtonsHorizontal[3]  = (Button)   findViewById(R.id.level_3_btn_h);
        levelButtonsHorizontal[4]  = (Button)   findViewById(R.id.level_4_btn_h);
        levelButtonsTextHorizontal = (TextView) findViewById(R.id.level_text_h);

        searchClearButton      = (Button)    findViewById(R.id.searchClearButton);
        searchClearButtonImage = (ImageView) findViewById(R.id.searchClearTabber);

        tutorialLayout         = (RelativeLayout) findViewById(R.id.tutorial);
        tutorialUpArrow        = (ImageView)      findViewById(R.id.up_arrow);
        tutorialDownArrowVert  = (ImageView)      findViewById(R.id.down_arrow_v);
        tutorialdownArrowHori  = (ImageView)      findViewById(R.id.down_arrow_h);

        suggestionFadeGradientView = (ImageView) findViewById(R.id.fader);

        // Setup all the UI elements
        suggestionFadeGradientViewVisible = true;
        hideSuggestionPageFader();

        tutorialArrowsExist   = true;
        tutorialArrowsVisible = true;
        mapViewZoomEnabled    = false;

        roomFileVersionText.setVisibility(SHOW_ROOM_FILE_VERSION ? View.VISIBLE : View.INVISIBLE);

        hideSearchClearButton();
        setUpUIForOrientationOnStartup();
        hideDescriptionViews();
        initializeRoomDataBase(true);
        refreshAutoComplete();
        setupSearchField();

        // Sets HTML that displays a blank page to the map's WebView
        String customHtml = AssetFileReader.getStringFromFile(this, "main.html");
        setHTMLToWebView(customHtml);

        setupMapViewPart1(); // Sets up the WebView that displays the map

    }

    /**
     * Setups the search field for the following functionality:
     *  - Showing/hiding the clear button when a user types in some characters
     *  - Allowing the user to perform a search by directly clicking on an
     *    auto-complete option
     *  - Showing/hiding the tutorial arrows when the search field is unpressed/pressed
     */
    public void setupSearchField() {

    	searchField.setOnEditorActionListener(new OnEditorActionListener() {
    		@Override
    		public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
    			if (actionId == EditorInfo.IME_ACTION_DONE) {
    				searchButtonClicked(searchButton);
    			}
    			return false;
    		}
    	});

    	/**
    	 * Defines the function called for clicking on an auto-complete
    	 * search term for the search field
    	 */
    	class SearchFieldSelectItemListener implements OnItemClickListener {
    		@Override
    		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
    				long arg3) {
    			mainActivity.unfocusSearchField();
    			mainActivity.performSearch();
    		}
    	}
    	searchField.setOnItemClickListener(new SearchFieldSelectItemListener());

    	/**
    	 * Defines the function called for clicking on the search field.
    	 * Responsible for:
    	 *  - Hiding/showing the clear button for the search field.
    	 *  - Hiding/showing the tutorial arrows if needed.
    	 */
    	class SearchFieldOnFocusChangeListener implements OnFocusChangeListener {
    		@Override
    		public void onFocusChange(View v, boolean hasFocus) {
    			if (hasFocus) {
    				mainActivity.searchFieldIsFocused = true;
    				if (mainActivity.searchFieldTextLength>0) {
    					mainActivity.showSearchClearButton();
    				}
    				hideTutorialArrows();
    			} else {
    				mainActivity.searchFieldIsFocused = false;
    				mainActivity.hideSearchClearButton();
    				showTutorialArrows();
    			}
    		}
    	}
    	searchField.setOnFocusChangeListener(new SearchFieldOnFocusChangeListener());

    	/**
    	 * Defines the function called for changing the text in the search field
    	 * via typing.
    	 * Responsible for:
    	 *  - Hiding/showing the clear button for the search field.
    	 */
    	class SearchFieldTextChangeListener implements TextWatcher {
    		@Override
    		public void afterTextChanged(Editable s) {
    			int textLength = s.length();
    			mainActivity.searchFieldTextLength = textLength;
    			if (textLength>0 && mainActivity.searchFieldIsFocused) {
    				mainActivity.showSearchClearButton();
    			} else {
    				mainActivity.hideSearchClearButton();
    			}
    		}
    		@Override
    		public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
    		@Override
    		public void onTextChanged(CharSequence s, int start, int before, int count) {}
    	}
    	searchField.addTextChangedListener(new SearchFieldTextChangeListener());
    }

    /**
     * For testing.
     * Clears the preferences files used for storing the
     * latest room file and the version number of the latest room file
     */
    public void clearPrefFiles(){
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        Editor settingsEditor = settings.edit();
        settingsEditor.clear();
        settingsEditor.commit();
    }

    /**
     * Rolls back the room file stored in the preferences back the the
     * original room file provided in the assets folder.
     * Called when an badly formed room file has been downloaded from
     * the server. (Maybe due to error on the server side or whatever reasons)
     */
    public void rollbackToDefaultRoomDatabase(){
        clearPrefFiles();
        roomDatabase = RoomDatabase.createRoomDatabase(StringHelper.removeNonSpaceWhiteSpace(
                AssetFileReader.getStringFromFile(this, DEFAULT_ROOM_FILE)));
    }

    /**
     * Initializes the room database
     * @param tryToUpdate  Whether to try and download an update from the server
     */
    public void initializeRoomDataBase(boolean tryToUpdate) {
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        String roomFileString = settings.getString("room_file_string", "");
        String roomFileVersionString = settings.getString("room_file_version_string", "1.0");

        if (roomFileString.length()==0){
            roomDatabase = RoomDatabase.createRoomDatabase(
            		StringHelper.removeNonSpaceWhiteSpace(
            				AssetFileReader.getStringFromFile(this, DEFAULT_ROOM_FILE)));
            roomFileVersionString = "1.0";
        } else {
            roomDatabase = RoomDatabase.createRoomDatabase(
            		StringHelper.removeNonSpaceWhiteSpace(roomFileString));
            if (roomDatabase==null) {
                rollbackToDefaultRoomDatabase();
            }
        }
        roomFileVersionText.setText(roomFileVersionString);

        if (tryToUpdate) {
            roomFileUpdater = new RoomFileUpdater(this);
            roomFileUpdater.updateRoomFile(roomFileVersionString);
        }
    }

    /**
     * Initializes the room database
     * @param roomFileString         The contents of the room file to be parsed
     * @param roomFileVersionString  The version of the room file
     */
    public void initializeRoomDataBaseWith(String roomFileString, String roomFileVersionString) {
        String finalString = StringHelper.removeNonSpaceWhiteSpace(roomFileString);
        roomDatabase = RoomDatabase.createRoomDatabase(finalString);
        if (roomDatabase==null) {
            initializeRoomDataBase(false);
            refreshAutoComplete();
            return;
        }
        refreshAutoComplete();

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);

        Editor settingsEditor = settings.edit();
        settingsEditor.putString("room_file_string", finalString);
        settingsEditor.putString("room_file_version_string", roomFileVersionString);
        settingsEditor.commit();

        roomFileVersionText.setText(roomFileVersionString);
    }

    /**
     * Refreshes the auto-complete entries for the search field
     */
    public void refreshAutoComplete(){
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, roomDatabase.getAutoCompleteList());
        searchField.setAdapter(adapter);
        searchField.setThreshold(1);
    }

    /**
     * For testing.
     * Used for performing some code when the test button is clicked.
     * This function is linked from the layout XML.
     * @param view
     */
    public void button1Clicked(View view) {
        displayLandmarkDescription("Lift at Level 2");
    }

    /**
     * For testing.
     * Used for performing some code when the test button is clicked.
     * This function is linked from the layout XML.
     * @param view
     */
    public void button2Clicked(View view) {
        String customHtml = AssetFileReader.getStringFromFile(
        		this, "second_level.html");
        setHTMLToWebView(customHtml);
        this.hideDescriptionViews();
    }

    /**
     * Forces the staff name to scroll marquee style.
     * This function is linked from the layout XML.
     * @param view
     */
    public void scrollStaffName(View view) {
        staffNameText.requestFocus();
    }

    /**
     * Performs a search and then changes the "MapView" accordingly
     */
    public void performSearch() {
        String searchString = this.searchField.getText().toString();
        Room searchResultRoom = this.roomDatabase.getDefiniteSearchMatch(searchString);

        removeTutorialArrows();
        if (searchResultRoom!=null) {
            this.enableZoomOnMapView();
            this.hideSuggestionPageFader();
            this.displayRoomDescription(searchResultRoom);
            this.setHTMLToWebView(LevelHTMLGetter.getHTMLforLevelWithRoom(
            		this, searchResultRoom.level, searchResultRoom.roomId));
            this.setSelectedLevel(searchResultRoom.level);
            this.searchField.setText(searchResultRoom.toAutoCompleteString());
        } else {
            if (searchString.length()>=SUGGESTIVE_SEARCH_MIN_SEARCH_LENGTH) {
                this.hideDescriptionViews();
                this.showSuggestionPageFader();
                disableZoomOnMapView();
                resetLevelButtonsStates();
                this.setHTMLToWebView(SuggestionsHTMLPreparer.getSuggestionHTML(
                		this, roomDatabase, searchString));
            } else {
                this.hideDescriptionViews();
                this.hideSuggestionPageFader();
                disableZoomOnMapView();
                resetLevelButtonsStates();
                this.setHTMLToWebView(SuggestionsHTMLPreparer.getNoResultsHTML(this));
            }
        }
    }

    /**
     * Called when the search button is clicked.
     * This function is linked from the layout XML.
     * @param view
     */
    public void searchButtonClicked(View view) {
        unfocusSearchField();
        performSearch();
    }

    /**
     * Breaks focus from the search field, so that the software
     * keyboard will go into hiding
     */
    public void unfocusSearchField(){
        InputMethodManager inputManager =
        		(InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(),
        InputMethodManager.HIDE_NOT_ALWAYS);
        FocusHelper.releaseFocus(this.searchField);
    }

    /**
     * Sets a HTML String to the "MapView".
     * @param htmlString
     */
    public void setHTMLToWebView(String htmlString) {
        this.mapView.loadDataWithBaseURL(
        		"file:///android_asset/", htmlString, "text/html", "utf-8", null);
    }

    /**
     * Setups the preliminary settings for the "MapView"
     */
    public void setupMapViewPart1() {
        WebSettings webSettings = this.mapView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setMinimumFontSize(1);
        setupMapViewSpecials();
        //webSettings.setRenderPriority(RenderPriority.HIGH);
        MapViewJavaScriptInterface jsInterface = new MapViewJavaScriptInterface();
        mapView.addJavascriptInterface(jsInterface, "jsInterface");
        mapView.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return true;
            }
        });
        mapView.setLongClickable(false);
    }

    /**
     * Setups some optimization for the "MapView".
     * Sort of an Android "hack".
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void setupMapViewSpecials(){
        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) return;
        mapView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
    }

    /**
     * Enables the zoom functionality for the "MapView"
     */
    public void enableZoomOnMapView() {
        if (mapViewZoomEnabled) return;
        WebSettings webSettings = this.mapView.getSettings();
        webSettings.setBuiltInZoomControls(true);
        mapViewZoomEnabled = true;
    }

    /**
     * Disable the zoom functionality for the "MapView".
     * Called when we want to display something other the a floor map
     * (e.g. the auto-correct suggestions page)
     */
    public void disableZoomOnMapView(){
        WebSettings webSettings = this.mapView.getSettings();
        webSettings.setBuiltInZoomControls(false);
        mapViewZoomEnabled = false;
    }

    /**
     * Hides the fading alpha gradient at the bottom of the screen
     * when the suggestion page is being displayed
     */
    public void hideSuggestionPageFader(){
        if (!suggestionFadeGradientViewVisible) return;
        suggestionFadeGradientView.setVisibility(View.INVISIBLE);
        suggestionFadeGradientViewVisible = false;
    }

    /**
     * Shows the fading alpha gradient at the bottom of the screen
     * when the suggestion page is being displayed
     */
    public void showSuggestionPageFader(){
        if (suggestionFadeGradientViewVisible) return;
        suggestionFadeGradientView.setVisibility(View.VISIBLE);
        suggestionFadeGradientViewVisible = true;
    }

    /**
     * Setups and show the UI Elements used to display the information
     * of a currently selected room
     * @param room  The currently selected room
     */
    public void displayRoomDescription(Room room) {
        if (room instanceof StaffRoom) {
            displayStaffRoomDescription(
            		room.roomId, ((StaffRoom) room).staffName, ((StaffRoom) room).staffTitle);
        } else if (room instanceof MeetingRoom) {
            displayMeetingRoomDescription(((MeetingRoom) room).roomName);
        }
    }

    /**
     * Setups and show the UI Elements used to display the information
     * of a currently selected staff room
     * @param roomId     The ID of the staff room
     * @param staffName  The name of the staff room's occupant
     * @param staffTitle The title of the staff room's occupant
     */
    public void displayStaffRoomDescription(String roomId, String staffName, String staffTitle) {
        descriptionOverlay      .setVisibility(View.VISIBLE);
        roomIdText              .setVisibility(View.VISIBLE);
        staffNameText           .setVisibility(View.VISIBLE);
        staffNameScrollActivator.setVisibility(View.VISIBLE);
        staffTitleText          .setVisibility(View.VISIBLE);

        descriptionOverlay2     .setVisibility(View.INVISIBLE);
        meetingRoomNameText     .setVisibility(View.INVISIBLE);

        roomIdText.setText(roomId);
        staffNameText.setText(staffName.equals("0")?"<Unoccupied>":staffName);
        staffTitleText.setText(staffTitle.equals("0")?"-":staffTitle);
        staffNameText.requestFocus();
    }

    /**
     * Setups and show the UI Elements used to display the information
     * of a currently selected meeting room
     * @param roomName The name of the meeting room
     */
    public void displayMeetingRoomDescription(String roomName) {
        this.descriptionOverlay      .setVisibility(View.INVISIBLE);
        this.roomIdText              .setVisibility(View.INVISIBLE);
        this.staffNameText           .setVisibility(View.INVISIBLE);
        this.staffNameScrollActivator.setVisibility(View.INVISIBLE);
        this.staffTitleText          .setVisibility(View.INVISIBLE);

        this.descriptionOverlay2     .setVisibility(View.VISIBLE);
        this.meetingRoomNameText     .setVisibility(View.VISIBLE);

        meetingRoomNameText.setText(roomName);
    }

    /**
     * Setups and show the UI Elements used to display the information
     * of a currently selected landmark. A landmark is anything that
     * is not considered a room. (e.g. lift, toilet)
     * @param landmarkName
     */
    public void displayLandmarkDescription(String landmarkName) {
        this.displayMeetingRoomDescription(landmarkName);
    }

    /**
     * Hide the UI Elements used for displaying the information
     * of the selected room
     */
    public void hideDescriptionViews(){
        this.descriptionOverlay      .setVisibility(View.INVISIBLE);
        this.descriptionOverlay2     .setVisibility(View.INVISIBLE);
        this.roomIdText              .setVisibility(View.INVISIBLE);
        this.staffNameText           .setVisibility(View.INVISIBLE);
        this.staffNameScrollActivator.setVisibility(View.INVISIBLE);
        this.staffTitleText          .setVisibility(View.INVISIBLE);
        this.meetingRoomNameText     .setVisibility(View.INVISIBLE);
    }

    /**
     * Called when the search field is pressed.
     * This function is linked from the layout XML.
     */
    public void searchFieldPressed(View view) {
        hideDescriptionViews();
    }

    /**
     * Called when the level 2 button is pressed.
     * This function is linked from the layout XML.
     */
    public void level2Pressed(View view){
        if (this.selectedLevel==2) return;
        enableZoomOnMapView();
        setHTMLToWebView(LevelHTMLGetter.getHTMLforLevel(this, 2));
        hideDescriptionViews();
        hideSuggestionPageFader();
        setSelectedLevel(2);
        removeTutorialArrows();
    }

    /**
     * Called when the level 3 button is pressed.
     * This function is linked from the layout XML.
     */
    public void level3Pressed(View view){
        if (this.selectedLevel==3) return;
        enableZoomOnMapView();
        setHTMLToWebView(LevelHTMLGetter.getHTMLforLevel(this, 3));
        hideDescriptionViews();
        hideSuggestionPageFader();
        setSelectedLevel(3);
        removeTutorialArrows();
    }

    /**
     * Called when the level 4 button is pressed.
     * This function is linked from the layout XML.
     */
    public void level4Pressed(View view){
        if (this.selectedLevel==4) return;
        enableZoomOnMapView();
        setHTMLToWebView(LevelHTMLGetter.getHTMLforLevel(this, 4));
        hideDescriptionViews();
        hideSuggestionPageFader();
        setSelectedLevel(4);
        removeTutorialArrows();
    }

    /**
     * Sets the selected level and change the states of
     * the level buttons to reflect the selected level
     * @param selectedLevel
     */
    public void setSelectedLevel (int selectedLevel) {
        this.selectedLevel=selectedLevel;
        for (int i=2; i<=4; ++i) {
            if (i!=selectedLevel) {
                levelButtonsVertical[i].setTextAppearance(this, R.style.LevelButtonUnselected);
                levelButtonsHorizontal[i].setTextAppearance(this, R.style.LevelButtonUnselected);
            } else {
                levelButtonsVertical[i].setTextAppearance(this, R.style.LevelButtonSelected);
                levelButtonsHorizontal[i].setTextAppearance(this, R.style.LevelButtonSelected);
            }
        }
    }

    /**
     * Reset the level buttons to their original states.
     * Called when we want to display a auto-correct suggestions page
     * instead of a map
     */
    public void resetLevelButtonsStates () {
        this.selectedLevel = 0;
        for (int i=2; i<=4; ++i) {
            levelButtonsVertical[i].setTextAppearance(this, R.style.LevelButtonUnselected);
            levelButtonsHorizontal[i].setTextAppearance(this, R.style.LevelButtonUnselected);
        }
    }

    /**
     * Setups the UI for portrait mode
     */
    public void toggleVerticalLayout(){
        levelButtonsTextVertical.setVisibility(View.VISIBLE);
        levelButtonsTextHorizontal.setVisibility(View.INVISIBLE);
        for (int i=2; i<=4; ++i) {
            levelButtonsVertical[i].setVisibility(View.VISIBLE);
            levelButtonsHorizontal[i].setVisibility(View.INVISIBLE);
        }
        if (tutorialArrowsVisible) {
            this.tutorialdownArrowHori.setVisibility(View.INVISIBLE);
            this.tutorialDownArrowVert.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Setups the UI for landscape mode
     */
    public void toggleHorizontalLayout(){
        levelButtonsTextVertical.setVisibility(View.INVISIBLE);
        levelButtonsTextHorizontal.setVisibility(View.VISIBLE);
        for (int i=2; i<=4; ++i) {
            levelButtonsVertical[i].setVisibility(View.INVISIBLE);
            levelButtonsHorizontal[i].setVisibility(View.VISIBLE);
        }
        if (tutorialArrowsVisible) {
            this.tutorialdownArrowHori.setVisibility(View.VISIBLE);
            this.tutorialDownArrowVert.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * Sets up the UI for the device's orientation on
     * the start of the application
     */
    public void setUpUIForOrientationOnStartup(){
    	int screenOrientation = getResources().getConfiguration().orientation;

        if (screenOrientation==Configuration.ORIENTATION_LANDSCAPE){
            toggleHorizontalLayout();
        } else if (screenOrientation==Configuration.ORIENTATION_PORTRAIT) {
            toggleVerticalLayout();
        }
    }

    /**
     * Called when the screen configuration has changed.
     * Used to change the layout between portrait and
     * landscape modes
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            toggleHorizontalLayout();
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            toggleVerticalLayout();
        }
    }

    /**
     * Hides the search clear button
     */
    public void hideSearchClearButton(){
        this.searchClearButtonVisible = false;
        this.searchClearButton.setVisibility(View.INVISIBLE);
        this.searchClearButtonImage.setVisibility(View.INVISIBLE);
    }

    /**
     * Shows the search clear button
     */
    public void showSearchClearButton(){
        if (searchClearButtonVisible) return;
        this.searchClearButtonVisible = true;
        this.searchClearButton.setVisibility(View.VISIBLE);
        this.searchClearButtonImage.setVisibility(View.VISIBLE);
    }

    /**
     * Clears the search field when the search clear
     * button is clicked
     */
    public void searchClearButtonClicked(View view) {
        this.searchField.setText("");
    }

    /**
     * Hide the tutorial arrows
     */
    public void hideTutorialArrows() {
        if (tutorialArrowsExist==false) return;
        if (tutorialArrowsVisible==false) return;
        AlphaAnimation animation = new AlphaAnimation(1.0f, 0.0f);
        animation.setStartOffset(100);
        animation.setDuration(300);
        animation.setFillBefore(true);
        animation.setFillAfter(true);
        tutorialLayout.clearAnimation();
        tutorialLayout.startAnimation(animation);
        tutorialArrowsVisible = false;
    }

    /**
     * Show the tutorial arrows
     */
    public void showTutorialArrows() {
        if (tutorialArrowsExist == false) return;
        if (tutorialArrowsVisible == true) return;
        AlphaAnimation animation = new AlphaAnimation(0.0f, 1.0f);
        animation.setStartOffset(1100);
        animation.setDuration(300);
        animation.setFillBefore(true);
        animation.setFillAfter(true);
        tutorialLayout.clearAnimation();
        tutorialLayout.startAnimation(animation);
        tutorialArrowsVisible = true;
    }

    /**
     * Removes the tutorial arrows, they will be actually
     * removed from the view hierarchy
     */
    public void removeTutorialArrows() {
        if (tutorialArrowsExist == false) return;
        tutorialLayout.clearAnimation();
        tutorialLayout.setVisibility(View.INVISIBLE);
        tutorialLayout.removeAllViewsInLayout();
        tutorialUpArrow.setImageBitmap(null);
        tutorialdownArrowHori.setImageBitmap(null);
        tutorialDownArrowVert.setImageBitmap(null);
        tutorialArrowsExist = false;
    }

    /**
     * The JavaScript interface used to facilitate passing of messages from
     * the "MapView"'s JavaScript to the Java code
     */
    private class MapViewJavaScriptInterface {
        private final Handler handler = new Handler();

        private class InterfaceRunnable implements Runnable {
            int level;
            String actionString;
            int actionType;

            public InterfaceRunnable(int level, String actionString, int actionType) {
                this.level = level;
                this.actionString = actionString;
                this.actionType = actionType;
            }

            public void run() {
                if (this.actionType==0) {
                    Room room = mainActivity.roomDatabase.getRoomInLevelWithIdForKeys(level, actionString);
                    if (room==null) {
                        StaffRoom unoccupiedRoom = new StaffRoom(level, actionString, "0", "0");
                        mainActivity.displayRoomDescription(unoccupiedRoom);
                    } else {
                        mainActivity.displayRoomDescription(
                        		mainActivity.roomDatabase.getRoomInLevelWithIdForKeys(level, actionString));
                    }
                } else if (actionType==1){
                    mainActivity.displayLandmarkDescription(actionString);
                } else if (actionType==2) {
                    mainActivity.searchField.setText(actionString);
                    mainActivity.performSearch();
                }
            }
        }

        @JavascriptInterface
        public void displayRoomDescription(int level, String roomId){
            handler.post(new InterfaceRunnable(level, roomId,0));
            mainActivity.mapView.postDelayed(MapRefresher.getInstance(mainActivity), 500);
        }

        @JavascriptInterface
        public void displayLandmarkDescription(int level, String landmarkName){
            handler.post(new InterfaceRunnable(level, landmarkName,1));
            mainActivity.mapView.postDelayed(MapRefresher.getInstance(mainActivity), 500);
        }

        @JavascriptInterface
        public void activateSearch(String searchTerm){
            handler.post(new InterfaceRunnable(0, searchTerm,2));
        }
    }

    /**
     * An Android "hack" to force the "MapView" to refresh several times
     * after clicking on a room. This is to clear the clicked effect on
     * the JavaScript links, which does not automatically clears
     * on some versions of Android.
     */
    static class MapRefresher implements Runnable {
        private int timesLeftToRun;
        private MainActivity mainActivity;
        private static MapRefresher instance = null;
        public static MapRefresher getInstance(MainActivity activity) {
            if(instance == null) {
                instance = new MapRefresher(activity);
            }
            instance.resetTimesToRun();
            return instance;
        }
        private MapRefresher(MainActivity activity) {
            resetTimesToRun();
            mainActivity = activity;
        }
        private void resetTimesToRun(){
            timesLeftToRun = 3;
        }
        @Override
        public void run () {
            mainActivity.mapView.invalidate();
            if (timesLeftToRun>0) {
                mainActivity.mapView.postDelayed(this, 500);
            }
            timesLeftToRun--;
        }
    }

}

