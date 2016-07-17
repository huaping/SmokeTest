package com.cloudminds.teminal.smoke;



import android.os.RemoteException;

import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiSelector;
import com.cloudminds.teminal.modules.CameraModule;
import com.cloudminds.teminal.modules.CommonModule;
import com.cloudminds.teminal.modules.SettingsModule;
import com.uiautomation.framework.UiAutoTestCase;

public class SmokeTest extends UiAutoTestCase {

	CommonModule common = new CommonModule(this);
	SettingsModule settings = new SettingsModule(this);
	CameraModule camera = new CameraModule(this);
	UiSelector[] watchers = {
			new UiSelector().textContains("Do you want to close it"),
			new UiSelector().textContains("Unfortunately,"),
			new UiSelector().textContains("has stopped") };

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		registerClickUiObjectWatcher("crash_watchers", watchers,
				new UiSelector().resourceId("android:id/button1"));
		runWatchers();
	}

	@Override
	protected void tearDown() throws Exception {
		removeWatcher("crash_watchers");
		super.tearDown();
	}

	public void PrepareEnvironment() throws UiObjectNotFoundException,
			RemoteException {
		common.gotoHome();
		// settings.openQuickSettings();
		settings.enableDeveloperOptions();
		settings.enableBatteryStatus();
		settings.disableAutoLight();
		settings.adjustBrightLevelLow();
		settings.displaySleep("Never");
		settings.enableLocation(false);
		settings.openQuickSettings();
	}

	public void testCameraTakePicture() throws RemoteException,
			UiObjectNotFoundException {
		camera.openCamera();
		camera.switchMode(CameraModule.STILL_MODE);
		camera.shutle(0);
	}

	public void testCameraTakeVideo() throws UiObjectNotFoundException {
		camera.openCamera();
		camera.switchMode(CameraModule.VIDEO_MODE);
		camera.shutle(60000);
	}

	public void testCameraTakeScenePicture() throws UiObjectNotFoundException,
			RemoteException {
		camera.openCamera();
		camera.switchMode(CameraModule.STILL_MODE);
		String[] scene = { "Automatic", "Portrait", "Landscape", "Sports",
				"Flowers", "Backlight", "Candlelight", "Sunset", "Night",
				"Beach", "Snow" };
		for (int i = 0; i < scene.length; i++) {
			camera.switchScene(scene[i]);
			camera.shutle(0);
			sleep(500);
		}
	}

	public void testCameraFilter() throws RemoteException,
			UiObjectNotFoundException {
		camera.openCamera();
		camera.switchMode(CameraModule.STILL_MODE);
		camera.switchScene("Automatic");
		String[] filter = { "Mono", "Sepia", "Negative", "Solarize",
				"Posterize", "Aqua", "Emboss", "Sketch", "Neon", "None" };
		for (int i = 0; i < filter.length; i++) {
			camera.switchFilter(filter[i]);
			camera.shutle(0);
			sleep(500);
		}
	}

	public void testCameraPictureSettings() throws UiObjectNotFoundException,
			RemoteException {
		camera.openCamera();
		camera.openCamera();
		camera.switchMode(CameraModule.STILL_MODE);
		camera.switchScene("Automatic");

		String[] settings = { "Flash mode:Auto,Off", "Store location:On,Off",
				"Picture size:12M pixels,8M pixels,5M pixels,3M pixels,HD1080,2M pixels,1.3M pixels",
				"Picture quality:Jpeg 55%,Jpeg 65%,Jpeg 75%,Jpeg 85%,Jpeg 95%,Jpeg 100%,Super fine,Fine,Normal",
				"Countdown timer:2 seconds,10 seconds,Off","Continuous Shot:On,Off",
				"Face Detection:On,Off", "Select ISO:ISO100,ISO1600,Auto","Exposure:-2,+2,0",
				"White balance:Incandescent,Cloudy,Auto"};
		
		for (int i = 0; i < settings.length; i++) {
			String set = settings[i].split(":")[0];
			for(String str : settings[i].split(":")[1].split(","))
			{
				System.out.println("current mode:"+set+" value:"+str);
				camera.switchSettings(set, str);
				sleep(300);
				camera.shutle(0);
				sleep(1000);
			}
			
		}
	}

	public void testTakescreen(){
		fail();
	}
}
