rem $env:DOCKER_BUILDKIT=0
rem docker rm -f $(docker ps -aq)
FOR /f %%i IN ('docker ps -aq') DO docker rm -f %%i
FOR /f %%i IN ('docker images -aq') DO docker rmi -f %%i
cd api-gateway
call .\mvnw clean package -DskipTests

cd ../auth-service
call .\mvnw clean package -DskipTests

cd ../categoria-service
call .\mvnw clean package -DskipTests

cd ../direccion-service
call  .\mvnw clean package -DskipTests

cd ../envio-service
call .\mvnw clean package -DskipTests

cd ../pago-service
call .\mvnw clean package -DskipTests

cd ../pedido-detalle-service
call .\mvnw clean package -DskipTests

cd ../pedido-service
call .\mvnw clean package -DskipTests

cd ../producto-service
call  .\mvnw clean package -DskipTests

cd ../usuario-service
call .\mvnw clean package -DskipTests
