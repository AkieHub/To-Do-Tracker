From openjdk
WORKDIR user/lib

ENV MONGO_DATABASE=trackerdb
ENV MONGO_URL=mongodb://localhost:27017/trackerdb

ADD ./target/BEJ_C2_S4_REST_API_MongoDB_PC_4.1-0.0.1-SNAPSHOT.jar  /usr/lib/BEJ_C2_S4_REST_API_MongoDB_PC_4.1-0.0.1-SNAPSHOT.jar

#run the jar file
ENTRYPOINT ["java","-jar","taskService-0.0.1-SNAPSHOT.jar"]