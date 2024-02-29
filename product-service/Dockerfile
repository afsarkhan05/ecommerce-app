FROM afsarkhan05/openjdk-17-custom:17

ARG JAR_NAME
ARG MAIN_CLASS
ARG SERVICE_NAME

ENV MAIN_CLASS=${MAIN_CLASS}

ENV LOG_PATH=/home/appmgr/${SERVICE_NAME}/logs/
ENV JAR_PATH=/home/appmgr/${JAR_NAME}


ENV JAVA_OPTS=" --add-opens=java.base/java.nio=ALL-UNNAMED "

USER root

RUN mkdir -p $LOG_PATH

ADD --chown=appmgr:appmgr target/${JAR_NAME} /home/appmgr
ADD --chown=appmgr:appmgr ./start.sh /home/appmgr

RUN chmod 755 /home/appmgr/start.sh

ENTRYPOINT /home/appmgr/start.sh