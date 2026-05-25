# Pizzeria
ejecutar en todos los ms: ./mvnw spring-boot:run
Gateway : http://localhost:9090



CUIDADO
Revisado // pedido (cambio en repository metodo query en consulta SELECT: total --> montoTotal)
                -> dependecia repetida (cambiado)

Revisar // direccion 
            -> dependecia repetida (cambiado)
            -> dependecia repetida OTRA VEZ

Revisar // pedido-detalle (cambio en properties: hibernate desactivado)
                (cambio en repository metodo BigDecimal: @Param pedidoId --> pedido_id)
                --> dependencia repetida

Revisar // pago (cambio en properties spring.datasource.url: bd_direccion --> bd_pago)
            --> dependencia repetida

Revisar // direccion (cambio a properties y webClient: usuario.service.url --> usuario.service-url)
            --> problema de comunicacion con usuario-service(SOLUCIONADO)

Revisar // ms comunicados Error 503 (cambiar properties como ms direccion)
                --> pedido POST (503 Service Unavailable)(haciendo uso de test RequestMapping)
                --> envio PUT (500 Internal Server Error)


ORDEN DE INICIACION
0- cd api-gateway
1- cd usuario-service
2- cd direccion-service
3- cd categoria-service
4- cd producto-service
5- cd pedido-service
6- cd pedido-detalle-service
7- cd envio-service
8- cd pago-service