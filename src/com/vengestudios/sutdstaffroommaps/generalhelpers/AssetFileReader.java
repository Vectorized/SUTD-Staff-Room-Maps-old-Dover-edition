package com.vengestudios.sutdstaffroommaps.generalhelpers;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.content.Context;

/**
 * A helper class to perform some String operations dealing with files stored
 * in the assets folder.
 */
public class AssetFileReader {

	/**
	 * Get a StringBuilder that contains the text of a file in the assets folder
	 * @param context  The application's context
	 * @param fileName The name of the file in the assets folder
	 * @return         The file's text as a StringBuilder
	 */
	public static StringBuilder getStringBuilderFromFile(Context context, String fileName) {
		StringBuilder stringBuilder = new StringBuilder();
	    InputStream fIn = null;
	    InputStreamReader isr = null;
	    BufferedReader input = null;
	    try {
	        fIn = context.getResources().getAssets()
	                .open(fileName);
	        isr = new InputStreamReader(fIn);
	        input = new BufferedReader(isr);
	        String line = "";
	        while ((line = input.readLine()) != null) {
	            stringBuilder.append(line);
	        }
	    } catch (Exception e) {
	        e.getMessage();
	    } finally {
	        try {
	            if (isr != null)
	                isr.close();
	            if (fIn != null)
	                fIn.close();
	            if (input != null)
	                input.close();
	        } catch (Exception e2) {
	            e2.getMessage();
	        }
	    }
	    return stringBuilder;
	}

	/**
	 * Get a String that contains the text of a file in the assets folder
	 * @param context  The application's context
	 * @param fileName The name of the file in the assets folder
	 * @return         The file's text as a String
	 */
	public static String getStringFromFile(Context context, String fileName) {
		return getStringBuilderFromFile(context, fileName).toString();
	}

}
