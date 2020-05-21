package com.qa.sf.keyword.tests;

import org.testng.annotations.Test;

import com.qa.sf.keyword.engine.KeyWordEngine;

public class LoginTest {

	public KeyWordEngine keyWordEngine;

	@Test
	public void loginTest() {

		keyWordEngine = new KeyWordEngine();
		keyWordEngine.startExecution("lead");

	}
	
/*	@Test
	public void forgotYourPasswordTest() {

		keyWordEngine = new KeyWordEngine();
		keyWordEngine.startExecution("forgotYourPassword");

	}*/

}
