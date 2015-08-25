package com.vengestudios.sutdstaffroommaps.generalhelpers;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

/**
 * A helper class to set the focus for Views
 */
public class FocusHelper {

	/**
	 * Releases the focus on a view
	 * @param view
	 */
    public static void releaseFocus(View view) {
        ViewParent parent = view.getParent();
        ViewGroup group = null;
        View child = null;
        while (parent != null) {
            if (parent instanceof ViewGroup) {
                group = (ViewGroup) parent;
                for (int i = 0; i < group.getChildCount(); i++) {
                    child = group.getChildAt(i);
                    if(child != view && child.isFocusable())
                        child.requestFocus();
                }
            }
            parent = parent.getParent();
        }
    }
}