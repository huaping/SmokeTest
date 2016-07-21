package com.cloudminds.teminal.modules;

import android.os.RemoteException;

import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiSelector;
import com.uiautomation.framework.UiAutoTestCase;

public class EmailModule {
	private UiAutoTestCase uiTest;

	public EmailModule(UiAutoTestCase ui) {
		this.uiTest = ui;
	}

	public boolean openEmailApp() {
		uiTest.openApplication("com.android.email", ".activity.Welcome");
		return uiTest.waitForExists(
				new UiSelector().resourceIdMatches(".*conversation_list_view"),
				3000);
	}

	/***
	 * Navigatio to Inbox,Starred, Unread,Drafts, Outbox,Sent,Trash
	 * 
	 * @param where
	 *            Inbox,Starred, Unread,Drafts, Outbox,Sent,Trash
	 * @return true is to that
	 * @throws UiObjectNotFoundException
	 * @throws RemoteException
	 */
	public boolean emailNavigation(String where)
			throws UiObjectNotFoundException, RemoteException {
		emailGotoMain();
		if (uiTest.waitForExists(
				new UiSelector().resourceIdMatches(".*mail_toolbar")
						.childSelector(new UiSelector().text(where)), 100)) {
			System.out.println("Already in " + where);
			return true;
		}
		uiTest.clickDescription("Navigate up");
		uiTest.click(new UiSelector().resourceIdMatches(".*name").text(where));
		return uiTest.waitForExists(
				new UiSelector().resourceIdMatches(".*mail_toolbar")
						.childSelector(new UiSelector().text(where)), 100);
	}

	public boolean emailGotoMain() throws RemoteException {
		int status = emailUiStatus();
		if (status == 6) {
			uiTest.pressKey("back");

		}
		if (emailUiStatus() != 255) {
			openEmailApp();
		}
		return emailUiStatus() == 255;
	}

	/**
	 * 0 -- inbox 2 -- starred 3 --- unread 4 --- Sent 5 --- Trash 6 --- a
	 * openned mail conversation 255 -- for main navigation UI, which can setup
	 * a new mail
	 * 
	 * @return
	 */
	private int emailUiStatus() {
		if (uiTest.waitForExists(
				new UiSelector().resourceIdMatches(".*compose_button"), 500)) {
			return 255;
		} else if (uiTest.waitForExists(
				new UiSelector().resourceIdMatches(".*mail_toolbar")
						.childSelector(new UiSelector().text("Inbox")), 100)) {
			return 0;
		} else if (uiTest.waitForExists(
				new UiSelector().resourceIdMatches(".*mail_toolbar")
						.childSelector(new UiSelector().text("Starred")), 100)) {
			return 1;
		} else if (uiTest.waitForExists(
				new UiSelector().resourceIdMatches(".*mail_toolbar")
						.childSelector(new UiSelector().text("Unread")), 100)) {
			return 2;
		} else if (uiTest.waitForExists(
				new UiSelector().resourceIdMatches(".*mail_toolbar")
						.childSelector(new UiSelector().text("Outbox")), 100)) {
			return 3;
		} else if (uiTest.waitForExists(
				new UiSelector().resourceIdMatches(".*mail_toolbar")
						.childSelector(new UiSelector().text("Sent")), 100)) {
			return 4;
		} else if (uiTest.waitForExists(
				new UiSelector().resourceIdMatches(".*mail_toolbar")
						.childSelector(new UiSelector().text("Trash")), 100)) {
			return 5;
		} else if (uiTest.waitForExists(
				new UiSelector().resourceIdMatches(".*content_pane"), 100)) {
			return 6;
		} else
			return -1;
	}

	boolean openEmailSubject(String title) throws UiObjectNotFoundException {
		if (uiTest.waitForExists(
				new UiSelector().resourceIdMatches(".*conversation_list_view"),
				2000)) {
			uiTest.click(new UiSelector().descriptionContains(title));
		}
		return uiTest.waitForExists(
				new UiSelector().resourceIdMatches(".*subject_and_folder_view")
						.descriptionContains(title), 2000);
	}

	public boolean forwardEmail(String emailAddr)
			throws UiObjectNotFoundException {
		uiTest.click(new UiSelector().resourceIdMatches(".overflow"));
		uiTest.clickText("Forward");
		if (!uiTest
				.waitForExists(new UiSelector().textStartsWith("Fwd:"), 1000))
			return false;
		uiTest.enterText(emailAddr,
				new UiSelector().resourceId("com.android.email:id/to"));
		uiTest.sleep(500);
		uiTest.clickResourceId("com.android.email:id/send");
		uiTest.sleep(2000);
		return !uiTest.waitForExists(
				new UiSelector().resourceId("com.android.email:id/to"), 1000);
	}

	public boolean deleteAllSentEmail() throws RemoteException,
			UiObjectNotFoundException {
		if (emailNavigation("Sent")) {
			if (uiTest.waitForExists(
					new UiSelector().resourceIdMatches(".*empty_view"), 300)) {
				return true;
			}
			int tryTimes = 1;
			UiObject obj = new UiObject(new UiSelector().className(
					"android.widget.ListView").childSelector(
					new UiSelector().className("android.widget.FrameLayout")));
			while (obj.exists() && tryTimes < 100) {
				obj.longClick();
				uiTest.click(new UiSelector().resourceIdMatches(".*delete"));
				tryTimes++;
			}
			return uiTest.waitForExists(
					new UiSelector().resourceIdMatches(".*empty_view"), 500);
		}
		return false;
	}

	public void setEmailAccount() throws UiObjectNotFoundException {
		if (openEmailApp())
			return;
		String username = "cloudminds001@sina.com", password = "q111111";
		uiTest.enterText(username,
				new UiSelector().resourceIdMatches(".*account_email"));
		uiTest.clickResourceIdMatches(".*manual_setup");
		uiTest.clickText("Personal (POP3)");
		uiTest.enterText(password,
				new UiSelector().resourceIdMatches(".*regular_password"));
		uiTest.clickResourceIdMatches(".*next");
		uiTest.enterText("pop.sina.com",
				new UiSelector().resourceIdMatches(".*account_server"));
		uiTest.scrollTo(new UiSelector().className("com.android.ListView"),
				new UiSelector().text("STARTTLS"), true);
		uiTest.clickText("STARTTLS");
		uiTest.clickText("None");
		uiTest.clickResourceIdMatches(".*next");
		uiTest.sleep(5000);
		uiTest.enterText("smtp.sina.com",
				new UiSelector().resourceIdMatches(".*account_server"));
		uiTest.scrollTo(new UiSelector().className("com.android.ListView"),
				new UiSelector().text("STARTTLS"), true);
		uiTest.clickText("STARTTLS");
		uiTest.clickText("None");
		uiTest.clickResourceIdMatches(".*next");
		if (uiTest
				.waitForExists(new UiSelector().text("Account options"), 5000)) {
			uiTest.clickResourceIdMatches(".*.*next");
			uiTest.waitForExists(
					new UiSelector().resourceIdMatches(".*account_name"), 3000);
			uiTest.enterText("cloudminds",
					new UiSelector().resourceIdMatches(".*account_name"));
			uiTest.clickResourceIdMatches(".*next");
			uiTest.sleep(3000);
		}
		uiTest.waitForExists(
				new UiSelector().resourceIdMatches(".*conversation_list_view"),
				3000);
	}
}
