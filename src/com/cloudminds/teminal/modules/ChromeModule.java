package com.cloudminds.teminal.modules;

import android.os.RemoteException;

import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiSelector;
import com.uiautomation.framework.UiAutoTestCase;

public class ChromeModule {
	private UiAutoTestCase uiTest;

	public ChromeModule(UiAutoTestCase ui) {
		this.uiTest = ui;
	}

	public boolean openBrowserApp() {
		uiTest.openApplication("com.android.chrome", "org.chromium.chrome.browser.ChromeTabbedActivity");
		return uiTest.waitForExists(
				new UiSelector().packageName("com.android.chrome"), 3000);
	}
	
	public boolean openUrl(String url, String toVerify) throws UiObjectNotFoundException{
		//com.android.browser:id/url
		try {
			if (uiTest.waitForExists(new UiSelector().resourceIdMatches(".*search_box_text"), 300)) {
				uiTest.enterText(url, new UiSelector().resourceIdMatches(".*search_box_text"));
			} else {
			uiTest.enterText(url, new UiSelector().resourceIdMatches(".*id/url_bar"));
			}
			uiTest.pressKey("enter");
			if (uiTest.waitForExists(new UiSelector().text("Nope"), 6000)) {
				uiTest.clickText("Nope");
				uiTest.clickTextContains("Never");
			}
			
			return uiTest.waitForExists(new UiSelector().text(toVerify), 7000);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
	}

	public void clearPrivacy() throws RemoteException, UiObjectNotFoundException{
		uiTest.pressKey("menu");
		uiTest.scrollTo(new UiSelector().className("android.widget.ListView"),
				new UiSelector().text("Settings"), true);
		uiTest.clickText("Settings");
		uiTest.scrollTo(new UiSelector().className("android.widget.ListView"),
				new UiSelector().text("Privacy"), true);
		uiTest.clickText("Privacy");
		uiTest.scrollTo(new UiSelector().className("android.widget.ListView"), new UiSelector().text("Clear browsing data"), true);
		uiTest.clickText("Clear browsing data");
		uiTest.clickResourceIdMatches(".*button_preference");
		uiTest.pressKey("back");
		uiTest.pressKey("back");
	}
	
	public boolean webClickAndVerify(String link, String verify) {
		try {
			new UiObject(new UiSelector().descriptionContains(link)).click();
			uiTest.clickIfAvailable(new UiSelector().text("Open with"), 200);
			uiTest.clickIfAvailable(new UiSelector().text("Chrome"), 500);
			uiTest.clickIfAvailable(new UiSelector().text("Always"), 500);

			return  uiTest.waitForExists(new UiSelector().descriptionContains(verify), 6000);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	public void webBrowsingControl(){
		try {
			
			UiObject obj = new UiObject(new UiSelector().className(android.webkit.WebView.class));
			obj.pinchOut(180, 10);
			uiTest.sleep(1000);
			obj.pinchIn(80, 10);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
