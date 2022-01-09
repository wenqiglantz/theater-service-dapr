FROM mcr.microsoft.com/mssql/server:2017-CU20-ubuntu-16.04

# Create a app directory
RUN mkdir -p /usr/src/app
WORKDIR /usr/src/app

# Bundle app source
COPY . /usr/src/app

# Grant permissions for the scripts to be executable
RUN chmod +x /usr/src/app/theater-service-db-app/entrypoint.sh
RUN chmod +x /usr/src/app/theater-service-db-app/db-configure.sh

ENTRYPOINT ["./theater-service-db-app/entrypoint.sh"]

# Tail the setup logs to trap the process
CMD ["tail -f /dev/null"]

HEALTHCHECK --interval=15s CMD /opt/mssql-tools/bin/sqlcmd -U sa -P $SA_PASSWORD -Q "select 1" && grep -q "MSSQL CONFIG COMPLETE" ./config.log
