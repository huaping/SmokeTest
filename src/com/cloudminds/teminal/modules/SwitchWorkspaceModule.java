package com.cloudminds.teminal.modules;

import android.os.RemoteException;

import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiSelector;
import com.uiautomation.framework.UiAutoTestCase;

public class SwitchWorkspaceModule {

	public static final int PERSONAL_HOME = 0;
	public static final int WORK_HOME = 1;
	private UiAutoTestCase uiTest;

	public SwitchWorkspaceModule(UiAutoTestCase ui) {
		this.uiTest = ui;
	}
	
	public int  getWorkSpace() {
		//com.android.quicksearchbox:id/search_widget_text
		//com.google.android.googlequicksearchbox:id/search_edit_frame
		try {
			uiTest.pressKey("home");
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		if (uiTest.waitForExists(new UiSelector().resourceIdMatches(".*search_widget_text"), 1000)){
			return PERSONAL_HOME;
		} else if (uiTest.waitForExists(new UiSelector().resourceIdMatches(".*search_edit_frame"), 1000)){
			return WORK_HOME;
		}
		return -255;
	}
	
	public boolean setWorkspace(int workspace){
		try {
			uiTest.pressKey("home");
			UiObject obj = new UiObject(new UiSelector().resourceId("com.android.launcher3:id/layout")
					.childSelector(new UiSelector().className("android.widget.TextView").instance(3)));
			if (workspace == getWorkSpace() ){
				return true;
			}
			obj.click();
			return workspace != getWorkSpace();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}


}
