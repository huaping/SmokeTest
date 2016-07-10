package com.cloudminds.teminal.smoke;

import com.cloudminds.teminal.modules.CommonModule;
import com.uiautomation.framework.UiAutoTestCase;

public class SmokeTest extends UiAutoTestCase {
	
	CommonModule common= new CommonModule(this);
	
	public void testBrowser(){
		common.gotoHome();
	}

	
}
