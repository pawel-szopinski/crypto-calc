package pl.ppsoft.crypto_calc.client;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@RegisterRestClient(configKey = "bitbay-api")
@Path("/API/Public")
@Consumes(MediaType.TEXT_PLAIN)
@Produces(MediaType.APPLICATION_JSON)
public interface BitBayClient {

    @GET
    @Path("/{currencyPair}/ticker.json")
    Response getTickerForCurrencyPair(@PathParam("currencyPair") String currencyPair);
}
