package org.acme.resource;

import java.util.List;
import java.util.Optional;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.acme.dto.CustomerDTO;
import org.acme.rest.client.ValidateService;
import org.acme.rest.client.ValidateVatResponse;
import org.acme.service.CustomerServiceImpl;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@Path("/api")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class CustomerResource {

	@Inject
	CustomerServiceImpl customerServiceImpl;

	@Inject
	@RestClient
	ValidateService validateService;

	@Path("/customers")
	@GET
	public List<CustomerDTO> getCustomers() {
		return customerServiceImpl.findAll();
	}

	@Path("/{vat}")
	@GET
	public Optional<CustomerDTO> getCustomerByVat(String vat) {
		return customerServiceImpl.findByVat(vat);
	}

	@Path("/customer")
	@POST
	public boolean saveCustomer(CustomerDTO customerDTO) {
            
            ValidateVatResponse response = validateService.checkVat("EL", customerDTO.getVat());
		if (response.getValid());
		{
			if (customerDTO == null && (customerServiceImpl.findByVat(customerDTO.getVat()).isPresent())) {
				return false;

			} else {
				customerServiceImpl.saveCustomer(customerDTO);
				return true;
			}
		}
	}
	
}
