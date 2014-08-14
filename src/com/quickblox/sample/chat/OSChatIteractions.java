package com.quickblox.sample.chat;

import com.quickblox.internal.core.helper.StringifyArrayList;
import com.quickblox.module.users.model.QBUser;

public class OSChatIteractions {
	
	/**
	 * O password do CHAT deve ter no minimo 8 caracteres
	 * A tag deve ser alphanumerica e deve iniciar com uma letra e deve ter de 3 a 15 chars.
	 * COMPANY:2
	 */


	public static final String APP_ID = "13174";
	public static final String AUTH_KEY = "tGAmNfQOaw-ewJj";
	public static final String AUTH_SECRET = "nqk3c6KT5DeeyE4";
	public static final String COMPANY = "tag1"; // leonvian 1234
	
	public static QBUser createQBUser() {
		QBUser qbUser = new QBUser("andre", "12345678");
		StringifyArrayList<String> tags = new StringifyArrayList<String>();
		//tags.add(COMPANY);
		//qbUser.setTags(tags);
		
		return qbUser;
	}

}
