package pl.ppsoft.crypto_calc.resource;

import org.jboss.resteasy.specimpl.ResponseBuilderImpl;
import pl.ppsoft.crypto_calc.entity.Error;
import pl.ppsoft.crypto_calc.service.CryptoCalcService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/api/crypto-calc")
@Consumes(MediaType.TEXT_PLAIN)
@Produces(MediaType.APPLICATION_JSON)
public class CryptoCalcResource {

    @Inject
    CryptoCalcService cryptoCalcService;

    @GET
    @Path("/")
    public Response getTicker(@QueryParam("symbol-invested") String symbolInvested,
                              @QueryParam("crypto") List<String> crypto) {
        try {
            return Response.ok(cryptoCalcService.getWalletOverview(symbolInvested, crypto)).build();
        } catch (WebApplicationException e) {
            return new ResponseBuilderImpl()
                    .entity(new Error(e.getResponse().getStatus(), e.getMessage()))
                    .status(e.getResponse().getStatus())
                    .build();
        }
    }
}
