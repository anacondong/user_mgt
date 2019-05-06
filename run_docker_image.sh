docker-compose -f docker-compose.yml down

echo "Building the service.."
./gradlew build -x test

docker stop users

docker rm users

docker build -f Dockerfile -t users .

echo "starting the service"
docker-compose -f docker-compose.yml -p ecommerce_service up
