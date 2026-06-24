# Pizzeria

## 1. Contexto o dominio del sistema
Sistema de gestión para una pizzería basado en arquitectura de microservicios. Permite administrar usuarios, direcciones, categorías, productos, pedidos, detalles de pedido, envíos y pagos, con un API Gateway que centraliza el acceso a los servicios.

## 2. Integrantes
- Luis Galaz
- Adrian Flores

## 3. Microservicios implementados
- api-gateway
- auth-service
- usuario-service
- direccion-service
- categoria-service
- producto-service
- pedido-service
- pedido-detalle-service
- envio-service
- pago-service

## 4. Responsabilidades de cada microservicio
- api-gateway: expone un punto único de entrada y enruta solicitudes a los microservicios correspondientes.
- auth-service: gestión de autenticación y autorización.
- usuario-service: administración de usuarios del sistema.
- direccion-service: administración de direcciones asociadas a usuarios.
- categoria-service: gestión de categorías de productos.
- producto-service: gestión de productos disponibles y su información.
- pedido-service: creación y administración de pedidos.
- pedido-detalle-service: detalle de los ítems incluidos en cada pedido.
- envio-service: gestión de envíos asociados a pedidos.
- pago-service: manejo de pagos y estados de transacción.

## 5. Rutas principales del Gateway
El gateway corre en el puerto 9090 y enruta las peticiones mediante los siguientes path principales:
- /usuarios/** → usuario-service
- /direcciones/** → direccion-service
- /categorias/** → categoria-service
- /productos/** → producto-service
- /pedidos/** → pedido-service
- /pedido-detalles/** → pedido-detalle-service
- /envios/** → envio-service
- /pagos/** → pago-service
- /auth/** → auth-service

## 6. Enlaces Swagger
- Gateway: http://localhost:9090/swagger-ui/index.html
- Usuario Service: http://localhost:9091/swagger-ui/index.html
- Dirección Service: http://localhost:9092/swagger-ui/index.html
- Categoría Service: http://localhost:9093/swagger-ui/index.html
- Producto Service: http://localhost:9094/swagger-ui/index.html
- Pedido Service: http://localhost:9095/swagger-ui/index.html
- Pedido Detalle Service: http://localhost:9096/swagger-ui/index.html
- Envío Service: http://localhost:9097/swagger-ui/index.html
- Pago Service: http://localhost:9098/swagger-ui/index.html
- Auth Service: http://localhost:9099/swagger-ui/index.html

## 7. Instrucciones de ejecución local
1. Abrir una terminal en la raíz del proyecto.
2. Ejecutar cada microservicio con Maven:
   - ./mvnw spring-boot:run
   - .\mvnw spring-boot:run
3. Iniciar en este orden recomendado:
   1. api-gateway
   2. auth-service
   3. usuario-service
   4. direccion-service
   5. categoria-service
   6. producto-service
   7. pedido-service
   8. pedido-detalle-service
   9. envio-service
   10. pago-service
4. Acceder al gateway en: http://localhost:9090

## 8. Instrucciones de despliegue remoto
- Preparar los servicios para su despliegue en un entorno remoto como Docker, Render, Railway, AWS o Azure.
- Configurar los puertos expuestos y las variables de entorno requeridas.
- Desplegar primero el servicio de autenticación y el gateway, seguido de los servicios de negocio.
- Actualizar las URLs internas de los microservicios para apuntar al entorno remoto.
- Verificar la conectividad entre servicios y la disponibilidad del gateway.

## 9. Variables de entorno requeridas
- SERVER_PORT=
- SPRING_DATASOURCE_URL=jdbc:mysql://host:3306/db_nombreMicroservicio
- SPRING_DATASOURCE_USERNAME=root
- SPRING_DATASOURCE_PASSWORD=
- SPRING_SECURITY_OAUTH2_RESOURCESERVER_JWT_SECRET_KEY=esta_es_mi_compleja_clave_secreta

## 10. Notas adicionales
- El gateway actual usa una clave JWT de ejemplo para recursos protegidos.
- Para entornos reales, reemplazar secretos y credenciales por valores seguros.