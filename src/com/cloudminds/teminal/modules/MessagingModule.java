package com.cloudminds.teminal.modules;

import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiSelector;
import com.uiautomation.framework.UiAutoTestCase;

public class MessagingModule {
	private UiAutoTestCase uiTest;
	public MessagingModule(UiAutoTestCase ui) {
		this.uiTest = ui;
	}
	
	/**
	 * Open messaging application
	 * @return  True is open successfully. 
	 */
	public boolean openMessageApp(){
		try {
			uiTest.openApplication("com.android.mms", ".ui.ConversationList");
			return uiTest.waitForExists(new UiSelector().resourceIdMatches(".*floating_action_button"), 2500);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean createNewSMS(String phoneNum, String content){
		try {
			uiTest.clickResourceIdMatches(".*floating_action_button");
			uiTest.enterText(phoneNum, new UiSelector().resourceIdMatches(".*recipients_editor"));
			uiTest.enterText(content, new UiSelector().resourceIdMatches(".*embedded_text_editor"));
			uiTest.clickResourceIdMatches(".*send_button_sms");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean checkMsgContent(String content, long timeout){
		return uiTest.waitForExists(new UiSelector().textContains(content), timeout);
	}
	
	public boolean deleteMsgConversationByPhoneNum(String phoneNum){
		try {
			uiTest.scrollTo(new UiSelector().resourceId("android:id/list"), new UiSelector().textContains(phoneNum), true);
			uiTest.longClick(new UiSelector().textContains(phoneNum));
			uiTest.clickResourceIdMatches(".*delete");
			uiTest.clickResourceId("android:id/button1");
			return ! uiTest.waitForExists(new UiSelector().textContains(phoneNum), 2000);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean createMMS(String phoneNum, String content){
		try {
			uiTest.clickResourceIdMatches(".*floating_action_button");
			uiTest.enterText(phoneNum, new UiSelector().resourceIdMatches(".*recipients_editor"));
			uiTest.enterText(content, new UiSelector().resourceIdMatches(".*embedded_text_editor"));
			uiTest.clickResourceIdMatches(".*add_attachment_first");
			uiTest.click(new UiSelector().resourceIdMatches(".*attachment_selector_text").instance(1));//click capture picture
			uiTest.waitForExists(new UiSelector().resourceIdMatches(".*shutter_button"), 2000);
			uiTest.clickResourceIdMatches(".*shutter_button");
			uiTest.clickResourceIdMatches(".*btn_done");
			uiTest.waitForExists(new UiSelector().resourceIdMatches(".*send_button_mms"), 2000);
			uiTest.clickResourceIdMatches(".*send_button_mms");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	
}
