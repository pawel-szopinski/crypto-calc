package pl.ppsoft.crypto_calc.exception;

import io.quarkus.qute.Template;
import pl.ppsoft.crypto_calc.entity.AppError;

import javax.inject.Inject;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class NoSuchResourceMapper implements ExceptionMapper<NotFoundException> {

    @Inject
    Template error;

    @Override
    public Response toResponse(NotFoundException e) {
        return Response.status(404)
                .entity(error.data("error", new AppError(404, "Yeah... I don't have that")))
                .build();
    }
}
