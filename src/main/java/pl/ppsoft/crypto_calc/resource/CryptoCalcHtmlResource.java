package pl.ppsoft.crypto_calc.resource;

import io.quarkus.qute.Template;
import pl.ppsoft.crypto_calc.entity.AppError;
import pl.ppsoft.crypto_calc.service.CryptoCalcService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/")
@Produces(MediaType.TEXT_HTML)
public class CryptoCalcHtmlResource {

    @Inject
    CryptoCalcService cryptoCalcService;

    @Inject
    Template wallet;

    @Inject
    Template error;

    @GET
    @Path("wallet")
    public Response getTicker(@QueryParam("locale") String locale,
                              @QueryParam("symbol-invested") String symbolInvested,
                              @QueryParam("crypto") List<String> crypto,
                              @QueryParam("no-rounding") boolean noRounding,
                              @QueryParam("name") String name) {
        try {
            return Response
                    .ok(wallet.data(
                            "wallet", cryptoCalcService.getWalletOverview(symbolInvested, crypto, noRounding, locale),
                            "name", name))
                    .build();
        } catch (WebApplicationException e) {
            return Response.status(418) //for some reason when 400 is used, HTML is displayed as plain text
                    .entity(error.data("error", new AppError(e.getResponse().getStatus(), e.getMessage())))
                    .build();
        } catch (Exception e) {
            return Response.serverError()
                    .entity(error.data("error", new AppError(500, e.getMessage())))
                    .build();
        }
    }
}