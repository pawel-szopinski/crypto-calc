FROM registry.access.redhat.com/ubi8/ubi-minimal:latest
WORKDIR /work/
RUN chown 1001 /work \
    && chmod "g+rwX" /work \
    && chown 1001:root /work
COPY --chown=1001:root target/*-runner /work/application

# Not supported by Heroku
#EXPOSE 8080

USER 1001

CMD ["./application", "-Dquarkus.http.host=0.0.0.0", "-Dquarkus.http.port=${PORT:8080}"]
