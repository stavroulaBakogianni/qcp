package org.acme.rest.client;

import java.util.Set;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import org.acme.dto.CustomerDTO;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/{countryCode}")
@RegisterRestClient
public interface ValidateService {

	@GET
	@Path("/{vat}")
	Boolean checkVat(@QueryParam("vat") String vat);

}
