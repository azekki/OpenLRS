package org.apereo.openlrs.credentials;

import org.apereo.openlrs.exceptions.NotFoundException;

public interface Credentials {
	
	String getSecretByKey(String key) throws NotFoundException;
	String getKey(String key) throws NotFoundException;
	String getUsername(String username) throws NotFoundException;
	String getPassword(String username) throws NotFoundException;
}
