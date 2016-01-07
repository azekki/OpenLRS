package org.apereo.openlrs.credentials;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Profile;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
@ConditionalOnProperty(name="auth.source",havingValue="Database")
public interface CredentialRepository extends CrudRepository<Credential, Integer> {
	Credential findByKey(String key);
}
