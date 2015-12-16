package org.apereo.openlrs.credentials;

import org.apache.log4j.Logger;
import org.apereo.openlrs.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("aws")
public class CredentialsAws implements Credentials{

	private Logger log = Logger.getLogger(CredentialsAws.class);
	@Autowired private CredentialRepository credentialRepository;

	@Override
	public String getSecretByKey(String key) {
		Credential cred = credentialRepository.findByKey(key);
		verifyValidCred(cred, key);
		return cred.getSecret();
	}

	@Override
	public String getKey(String key) {
		Credential cred = credentialRepository.findByKey(key);
		verifyValidCred(cred, key);
		return cred.getKey();
	}

	@Override
	public String getUsername(String username) {
		Credential cred = credentialRepository.findByKey(username);
		verifyValidCred(cred, username);
		return cred.getKey();
	}

	@Override
	public String getPassword(String username) {
		Credential cred = credentialRepository.findByKey(username);
		verifyValidCred(cred, username);
		return cred.getSecret();
	}
	
	private void verifyValidCred(Credential credential, String key){
		if(credential == null){
			throw new NotFoundException(String.format("A credential for the key: %s does not exist", key));
		}
	}
}
