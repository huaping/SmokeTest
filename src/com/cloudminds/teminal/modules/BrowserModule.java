package com.cloudminds.teminal.modules;

import android.os.RemoteException;

import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiSelector;
import com.uiautomation.framework.UiAutoTestCase;

public class BrowserModule {
	private UiAutoTestCase uiTest;

	public BrowserModule(UiAutoTestCase ui) {
		this.uiTest = ui;
	}

	public boolean openBrowserApp() {
		uiTest.openApplication("com.android.swe.browser", "com.android.browser.BrowserActivity");
		return uiTest.waitForExists(
				new UiSelector().packageName("com.android.swe.browser"), 3000);
	}
	
	public boolean openUrl(String url, String toVerify) throws UiObjectNotFoundException{
		//com.android.browser:id/url
		uiTest.enterText(url, new UiSelector().resourceIdMatches(".*id/url"));
		return uiTest.waitForExists(new UiSelector().text(toVerify), 7000);
	}

	public void clearPrivacy() throws RemoteException, UiObjectNotFoundException{
		uiTest.pressKey("menu");
		uiTest.scrollTo(new UiSelector().className("android.widget.ListView"),
				new UiSelector().text("Settings"), true);
		uiTest.clickText("Settings");
		uiTest.scrollTo(new UiSelector().className("android.widget.ListView"),
				new UiSelector().text("Privacy & security"), true);
		uiTest.clickText("Privacy & security");
		uiTest.clickText("Clear stored data");
		if (uiTest.waitForExists(new UiSelector().text("Clear selected items"), 3000)){
			uiTest.clickResourceIdMatches(".*button1");
		}
	}
}
