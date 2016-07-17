package com.cloudminds.teminal.modules;

import android.os.RemoteException;

import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiSelector;
import com.uiautomation.framework.UiAutoTestCase;

public class SettingsModule {
	private UiAutoTestCase uiTest;

	public SettingsModule(UiAutoTestCase ui) {
		this.uiTest = ui;
	}

	public void OpenSettingsApp() {
		uiTest.openApplication("com.android.settings", ".Settings");
	}

	public void scrollToSettingItem(String item)
			throws UiObjectNotFoundException {
		uiTest.scrollTo(
				new UiSelector().className("android.widget.ScrollView"),
				new UiSelector().text(item), true);
		uiTest.clickText(item);
	}

	public boolean openWiFi() throws UiObjectNotFoundException {
		OpenSettingsApp();
		scrollToSettingItem("WLAN");
		if (isWiFiEnabled()) {
			return true;
		}
		uiTest.click(new UiSelector().className("android.widget.Switch"));
		return isWiFiEnabled();
	}

	public boolean isWiFiEnabled() throws UiObjectNotFoundException {
		return new UiObject(new UiSelector().className("android.widget.Switch"))
				.isChecked();
	}

	public boolean addWifi(String ssid, String password)
			throws UiObjectNotFoundException, RemoteException {
		openWiFi();
		uiTest.pressKey("menu");
		uiTest.clickText("Add network");
		uiTest.enterText(ssid,
				new UiSelector().resourceId("com.android.settings:id/ssid"));
		uiTest.clickResourceId("com.android.settings:id/security");
		uiTest.clickTextContains("WPA");
		uiTest.enterText(password,
				new UiSelector().resourceId("com.android.settings:id/password"));
		uiTest.clickResourceId("android:id/button1");
		return true;
	}

	public void adjustBrightLevelLow() throws UiObjectNotFoundException {
		OpenSettingsApp();
		scrollToSettingItem("Display");
		uiTest.clickText("Brightness level");
		uiTest.click(new UiSelector().className("android.widget.SeekBar"),
				"tl");
	}

	public void disableAutoLight() throws UiObjectNotFoundException {
		if (!uiTest.waitForExists(new UiSelector().text("Sleep"), 2000)) {
			OpenSettingsApp();
			scrollToSettingItem("Display");
		}
		if (new UiObject(new UiSelector().className("android.widget.Switch"))
				.isChecked()) {
			uiTest.clickClass("android.widget.Switch", 0);
		}
	}

	public void displaySleep(String time) throws UiObjectNotFoundException {
		if (!uiTest.waitForExists(new UiSelector().text("Sleep"), 2000)) {
			OpenSettingsApp();
			scrollToSettingItem("Display");
		}
		uiTest.clickText("Sleep");
		uiTest.clickText(time);
	}

	public void enableBatteryStatus() throws UiObjectNotFoundException {
		OpenSettingsApp();
		scrollToSettingItem("Status bar");
		UiObject obj = new UiObject(
				new UiSelector().resourceId("android:id/checkbox"));
		if (obj.isChecked()) {
			return;
		}
		obj.click();
	}

	public void screenLockToNone() throws UiObjectNotFoundException {
		OpenSettingsApp();
		scrollToSettingItem("Security");
		uiTest.clickText("Screen lock");
		uiTest.clickText("None");
	}

	public void unknownSourceEnable() throws UiObjectNotFoundException {
		OpenSettingsApp();
		scrollToSettingItem("Security");
		uiTest.scrollTo(new UiSelector().className("android.widget.ListView"),
				new UiSelector().text("Unknown sources"), true);
		if (!new UiObject(new UiSelector().className("android.widget.Switch"))
				.isChecked())
			uiTest.clickText("Unknown sources");
	}

	public void openQuickSettings() throws UiObjectNotFoundException {
		uiTest.openQuickSettings();
		if (uiTest.waitForExists(new UiSelector().text("Auto-rotate"), 300)) {
			uiTest.clickText("Auto-rotate");
		}
	}

	public void enableLocation(boolean enable) throws UiObjectNotFoundException {
		OpenSettingsApp();
		scrollToSettingItem("Location");

		UiObject obj = new UiObject(
				new UiSelector().className("android.widget.Switch"));

		if (enable) {
			if (!obj.isChecked()) {
				obj.click();
			}
		} else {
			if (obj.isChecked()) {
				obj.click();
			}
		}
	}

	public void enableDeveloperOptions() throws UiObjectNotFoundException,
			RemoteException {
		OpenSettingsApp();
		scrollToSettingItem("About phone");
		uiTest.scrollTo(new UiSelector().className("android.widget.ListView"),
				new UiSelector().text("Build number"), true);
		for (int i = 0; i < 7; i++) {
			uiTest.clickText("Build number");
		}
		uiTest.pressKey("back");
		uiTest.clickText("Developer options");
		UiObject obj = new UiObject(new UiSelector().className(
				"android.widget.Switch").instance(1));
		if (!obj.isChecked()) {
			obj.click();
		}
	}
}
