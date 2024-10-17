package org.acme.resource;

import static com.arjuna.ats.arjuna.logging.tsLogger.logger;
import java.util.List;
import java.util.Optional;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
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
    public Response getCustomers() {
        List<CustomerDTO> customers = customerServiceImpl.findAll();
        if (customers.isEmpty()) {
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        return Response.ok(customers).build();
    }

    @Path("/{vat}")
    @GET
    public Response getCustomerByVat(@PathParam("vat") String vat) {
        try {
            Optional<CustomerDTO> customer = customerServiceImpl.findByVat(vat);
            if (customer.isPresent()) {
                return Response.ok(customer.get()).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Customer with VAT " + vat + " not found.")
                        .build();
            }
        } catch (Exception e) {
            logger.error("Error while fetching customer by VAT: {}", e.getMessage(), e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("An error occurred while fetching the customer.")
                    .build();
        }
    }

    @Path("/customer")
    @POST
    public Response saveCustomer(CustomerDTO customerDTO) {
        if (customerDTO == null || customerDTO.getVat() == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Customer data or VAT number is missing.")
                    .build();
        }

        try {
            ValidateVatResponse vatResponse = validateService.checkVat("EL", customerDTO.getVat());
            
            System.out.println("VAT validation response: " + vatResponse);

            if (customerServiceImpl.findByVat(customerDTO.getVat()).isPresent()) {
                return Response.status(Response.Status.CONFLICT)
                        .entity("Customer with this VAT already exists.")
                        .build();
            }

            if (!vatResponse.getValid()) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Invalid VAT number.")
                        .build();
            }

            customerServiceImpl.saveCustomer(customerDTO);
            return Response.status(Response.Status.CREATED)
                    .entity("Customer saved successfully.")
                    .build();

        } catch (Exception e) {
            logger.error("Error while saving customer: {}", e.getMessage(), e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("An error occurred while saving the customer.")
                    .build();
        }
    }
}
