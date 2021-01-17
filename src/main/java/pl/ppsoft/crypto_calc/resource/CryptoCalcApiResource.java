package pl.ppsoft.crypto_calc.resource;

import pl.ppsoft.crypto_calc.entity.AppError;
import pl.ppsoft.crypto_calc.service.CryptoCalcService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/api/wallet")
@Produces(MediaType.APPLICATION_JSON)
public class CryptoCalcApiResource {

    @Inject
    CryptoCalcService cryptoCalcService;

    @GET
    public Response getTicker(@QueryParam("symbol-invested") String symbolInvested,
                              @QueryParam("crypto") List<String> crypto,
                              @QueryParam("no-rounding") boolean noRounding) {
        try {
            return Response.ok(cryptoCalcService.getWalletOverview(symbolInvested, crypto, noRounding)).build();
        } catch (WebApplicationException e) {
            return Response.status(e.getResponse().getStatus())
                    .entity(new AppError(e.getResponse().getStatus(), e.getMessage()))
                    .build();
        } catch (Exception e) {
            return Response.serverError()
                    .entity(new AppError(500, e.getMessage()))
                    .build();
        }
    }
}
