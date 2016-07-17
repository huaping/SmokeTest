package com.cloudminds.teminal.modules;

import android.os.RemoteException;

import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiSelector;
import com.uiautomation.framework.UiAutoTestCase;

public class CameraModule {
	private UiAutoTestCase uiTest;
	public final static int STILL_MODE = 0;
	public final static int VIDEO_MODE = 1;
	public final static int PANORAMA_MODE = 2;

	public CameraModule(UiAutoTestCase ui) {
		this.uiTest = ui;
	}

	public boolean openCamera() {
		uiTest.openApplication("org.codeaurora.snapcam",
				"com.android.camera.CameraLauncher");
		return uiTest.waitForExists(
				new UiSelector().packageName("org.codeaurora.snapcam"), 3000);
	}

	public void switchMode(int mode) throws UiObjectNotFoundException {
		uiTest.clickResourceIdMatches(".*camera_switcher");
		if (mode == STILL_MODE) {
			uiTest.clickDescription("Switch to photo");
		} else if (mode == VIDEO_MODE) {
			uiTest.clickDescription("Switch to video");
		} else if (mode == PANORAMA_MODE) {
			uiTest.clickDescription("Switch to panorama");
		}
	}

	public boolean viewThumb() throws UiObjectNotFoundException {
		// org.codeaurora.snapcam:id/preview_thumb
		uiTest.clickResourceIdMatches(".*preview_thumb");
		return uiTest.waitForExists(
				new UiSelector().packageName("com.android.gallery3d"), 2000);
	}

	/**
	 * Switch to scene
	 * 
	 * @param mode
	 *              can be Automatic, Portrait, Landscape,Sports, Flowers,
	 *            Backlight, Candlelight, Sunset, Night, Beach, Snow
	 * @throws UiObjectNotFoundException
	 * @throws RemoteException 
	 */
	public void switchScene(String mode) throws UiObjectNotFoundException, RemoteException {
		// org.codeaurora.snapcam:id/scene_mode_switcher
		// android.widget.ScrollView
		uiTest.clickResourceIdMatches(".*scene_mode_switcher");
		uiTest.scrollTo(
				new UiSelector().className("android.widget.ScrollView"),
				new UiSelector().text(mode), true);
		uiTest.clickText(mode);
		uiTest.pressKey("back");
	}
	
	/**
	 * Press menu to open camera settings
	 * @param settingName, scroll to find and click settings, like "Picture size"
	 * @param status  child of the settingsName, like HD1080
	 * @throws UiObjectNotFoundException
	 * @throws RemoteException 
	 */
	public void switchSettings(String settingName, String status) throws UiObjectNotFoundException, RemoteException{
		uiTest.clickResourceIdMatches(".*menu");
		//android.widget.ListView
		uiTest.scrollTo(new UiSelector().className("android.widget.ListView"), 
				new UiSelector().text(settingName), true);
		uiTest.clickText(settingName);
		uiTest.click(new UiSelector().resourceIdMatches(".*id/text").text(status));
		//uiTest.clickText(status);
		uiTest.sleep(500);
		uiTest.pressKey("back");
		uiTest.sleep(500);
	}
	
	/**
	 * Take picture or video
	 * @param timeOut 0 is to take picture, timeout in ms is for take video duration
	 * @throws UiObjectNotFoundException
	 */
	public void shutle(long duration) throws UiObjectNotFoundException{
		uiTest.clickResourceIdMatches(".*shutter_button");
		if (duration > 0) {
			uiTest.sleep(duration);
			uiTest.clickResourceIdMatches(".*shutter_button");
		}
	}
	
	public void deleteFileFromCamera() throws UiObjectNotFoundException, RemoteException{
		viewThumb();
		uiTest.pressKey("menu");
		UiObject obj = new UiObject(new UiSelector().text("Delete"));
		if (obj.exists()) {
			obj.clickAndWaitForNewWindow();
		} else {
			uiTest.pressKey("menu");
			obj.clickAndWaitForNewWindow();
		}
	}
	
	public boolean playVideoFromCamera(long duration) throws UiObjectNotFoundException, RemoteException{
		viewThumb();
		uiTest.pressKey("menu");
		UiObject obj = new UiObject(new UiSelector().text("Delete"));
		if (!obj.exists()){
			uiTest.pressKey("menu");
		}
		if (new UiObject(new UiSelector().text("Trim")).exists()){
			uiTest.pressKey("back");
			uiTest.clickResourceIdMatches(".*gl_root_view");
			if (new UiObject(new UiSelector().textContains("Open with")).exists()) {
				uiTest.clickText("VideoPlayer");
				uiTest.clickResourceIdMatches(".*button_always");
				uiTest.sleep(duration);
				return true;
			}
		}
		return false;
	}
	
	public void switchFilter(String mode) throws UiObjectNotFoundException, RemoteException{
	//org.codeaurora.snapcam:id/filter_mode_switcher
		uiTest.clickResourceIdMatches(".*filter_mode_switcher");
		uiTest.scrollTo(
				new UiSelector().className("android.widget.ScrollView"),
				new UiSelector().text(mode), true);
		uiTest.clickText(mode);
		uiTest.pressKey("back");
	}
	
	public void switchFrontBack() throws UiObjectNotFoundException{
		//org.codeaurora.snapcam:id/front_back_switcher
		uiTest.clickResourceIdMatches(".*front_back_switcher");
	}

}
