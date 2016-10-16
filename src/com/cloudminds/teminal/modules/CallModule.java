package com.cloudminds.teminal.modules;


import com.android.uiautomator.core.UiSelector;
import com.uiautomation.framework.UiAutoTestCase;

public class CallModule {
	private UiAutoTestCase uiTest;
	public CallModule(UiAutoTestCase ui) {
		this.uiTest = ui;
	}
	
	public boolean  getOnCallStatus() {
		String line = uiTest.runCmdAndVerify("dumpsys telephony.registry", "mCallState=2");
		if (line != null) {
			return true;
		}
		return false;
	}
	
	public boolean dialNumer(String  phoneNum){
		try {
			uiTest.openApplication("com.android.dialer", ".DialtactsActivity");
			uiTest.clickResourceIdMatches(".*floating_action_button");
			uiTest.enterText(phoneNum, new UiSelector().resourceIdMatches(".*digits"));
			uiTest.sleep(500);
			uiTest.clickIfAvailable(new UiSelector().resourceIdMatches(".*dialpad_floating_action_button"), 1000);
			uiTest.sleep(5000);
			return getOnCallStatus();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
}
