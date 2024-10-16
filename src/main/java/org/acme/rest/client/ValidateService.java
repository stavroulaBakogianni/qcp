package org.acme.rest.client;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/checkVat")
@RegisterRestClient
public interface ValidateService {

    @GET
    @Path("/{countryCode}/{vatNumber}")
    ValidateVatResponse checkVat(@PathParam("countryCode") String countryCode, @PathParam("vatNumber") String vatNumber);
}
