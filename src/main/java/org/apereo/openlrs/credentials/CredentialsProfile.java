package org.apereo.openlrs.credentials;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("!aws")
public class CredentialsProfile implements Credentials{
	
	@Value("${auth.basic.username}")
	private String username;
	@Value("${auth.basic.password}")
	private String password;
	
	@Value("${auth.oauth.key}")
	private String key;
	@Value("${auth.oauth.secret}")
	private String secret;
	
	@Override
	public String getSecretByKey(String key) {
		return secret;
	}

	@Override
	public String getKey(String key) {
		return key;
	}

	@Override
	public String getUsername(String username) {
		return username;
	}

	@Override
	public String getPassword(String username) {
		return password;
	}

}
