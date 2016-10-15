package com.cloudminds.teminal.smoke;



import android.os.RemoteException;

import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiSelector;
import com.cloudminds.teminal.modules.CameraModule;
import com.cloudminds.teminal.modules.ChromeModule;
import com.cloudminds.teminal.modules.CommonModule;
import com.cloudminds.teminal.modules.GMSModule;
import com.cloudminds.teminal.modules.MessagingModule;
import com.cloudminds.teminal.modules.SettingsModule;
import com.cloudminds.teminal.modules.SwitchWorkspaceModule;
import com.uiautomation.framework.UiAutoTestCase;

public class SmokeTest extends UiAutoTestCase {

	CommonModule common = new CommonModule(this);
	SettingsModule settings = new SettingsModule(this);
	CameraModule camera = new CameraModule(this);
	MessagingModule msg = new MessagingModule(this);
	SwitchWorkspaceModule  workspace = new SwitchWorkspaceModule(this);
	ChromeModule chrome = new ChromeModule(this);
	GMSModule gms= new GMSModule(this);
	
	UiSelector[] watchers = {
			new UiSelector().textContains("Do you want to close it"),
			new UiSelector().textContains("Unfortunately,"),
			new UiSelector().textContains("has stopped") };

	public SmokeTest(){
		super(6000, "SMOKE TEST", true);
	}
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		registerClickUiObjectWatcher("crash_watchers", watchers,
				new UiSelector().resourceId("android:id/button1"));
		runWatchers();
		freezeRotation(true);
		
	}

	@Override
	protected void tearDown() throws Exception {
		removeWatcher("crash_watchers");
		common.gotoHome();
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
	
	public void testSendSMS(){
		msg.openMessageApp();
		msg.createNewSMS("10086", "CXYE");
		assertEquals("no reply recieved", true, msg.checkMsgContent("账户余额", 8000));
	}
	
	public void testDeleteMessage(){
		common.gotoHome();
		msg.openMessageApp();
		assertEquals("Delete failed", true, msg.deleteMsgConversationByPhoneNum( "10086"));
	}
	public void testSendMMS(){
		msg.openMessageApp();
		assertEquals("Send mms failed", true, msg.createMMS("10086", "CXYE"));
	}
	
	public void testSwitchWorkspace(){
		assertEquals("Swich workspace failed", true, workspace.setWorkspace(SwitchWorkspaceModule.PERSONAL_HOME));
	}
	
	public void testBrowser() throws RemoteException, UiObjectNotFoundException{
		chrome.openBrowserApp();
		chrome.clearPrivacy();
		chrome.openUrl("swverification.blog.sohu.com", "swverification.blog.sohu.com");
		chrome.webBrowsingControl();
		
		String[] linkStrings = { "UI Testing - uiautomator:uiautomator 是android提供的一个UI测试工具，跟Monkeyrunner不同",
				"Test Automation tools for android:NativeDriver",
				"Selenium - A web based test tool:create quick bug reproduction scripts"
			};
		
		for (int i = 0; i < linkStrings.length; i++) {
			String link = linkStrings[i].split(":")[0];
			for(String verify : linkStrings[i].split(":")[1].split(","))
			{
				System.out.println("current Link:"+link+" Verify::"+verify);
				boolean rst = chrome.webClickAndVerify(link, verify);
				chrome.webBrowsingControl();
				pressKey("back");
				if (!rst)logMessage("Open Link: " + link + "   verify:" + verify);
				sleep(1000);
			}
		}
	}
	
	public void testGoogleSearch() {
		gms.googleSearchWidget("hello google", "Hello Google - YouTube");
		
		String[] linkStrings = { "hello google:Hello Google - YouTube",
				"microsoft:Microsoft - Official Home Page",
				"facebook:Facebook"
			};
		
		for (int i = 0; i < linkStrings.length; i++) {
			String link = linkStrings[i].split(":")[0];
			for(String verify : linkStrings[i].split(":")[1].split(","))
			{
				logMessage("current Link:"+link+" Verify::"+verify);
				if (gms.googleSearchWidget(link, verify)){
					 chrome.webClickAndVerify(verify, "");
					chrome.webBrowsingControl();
				}
				try {
					pressKey("home");
				} catch (RemoteException e) {
					e.printStackTrace();
				}
				sleep(1000);
			}
		}
	}
	
}


