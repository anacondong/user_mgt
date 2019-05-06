FROM java:8
EXPOSE 8090
ADD build/libs/user_mgt-0.0.1-SNAPSHOT.jar user_mgt-0.0.1-SNAPSHOT.jar
ENTRYPOINT exec java -jar user_mgt-0.0.1-SNAPSHOT.jar
