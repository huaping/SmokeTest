package com.cloudminds.teminal.modules;

import com.android.uiautomator.core.UiSelector;
import com.uiautomation.framework.UiAutoTestCase;

public class GMSModule {
	private UiAutoTestCase uiTest;

	public GMSModule(UiAutoTestCase ui) {
		this.uiTest = ui;
	}

	public boolean googleSearchWidget(String toSearch, String toVerify){
		try {
			uiTest.click(new UiSelector().resourceIdMatches(".*search_edit_frame"));
			uiTest.enterText(toSearch, new UiSelector().resourceIdMatches(".*search_box"));
			uiTest.pressKey("enter");
			boolean result =  uiTest.waitForExists(new UiSelector().descriptionContains(toVerify), 6000);
			if(!result)uiTest.logMessage("Verify failed: " + toVerify);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
