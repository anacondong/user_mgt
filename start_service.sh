echo "Building inprogress..."
./gradlew build -x test

echo "Running User Management service.."

java -jar build/libs/user_mgt-0.0.1-SNAPSHOT.jar
