package org.apereo.openlrs.credentials;

import org.springframework.context.annotation.Profile;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
@Profile("aws")
public interface CredentialRepository extends CrudRepository<Credential, Integer> {
	Credential findByKey(String key);
}
