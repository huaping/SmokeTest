package com.cloudminds.teminal.modules;

import android.os.RemoteException;

import com.uiautomation.framework.UiAutoTestCase;

public class CommonModule {
	private UiAutoTestCase uiTest;
	public CommonModule(UiAutoTestCase ui) {
		this.uiTest = ui;
	}
	
	public void gotoHome() {
		try {
			uiTest.pressKey("back");
			uiTest.pressKey("back");
			uiTest.pressKey("back");
			uiTest.pressKey("home");
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
	}
}
