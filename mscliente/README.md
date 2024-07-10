GET
localhost:8081/api/clientes

POST
localhost:8081/api/clientes

PUT
localhost:8081/api/clientes/12

1) Testa se campos estão em branco
2) valida cep 
3) preenche campos cidade,uf, etc caso estejam em branco

** com compila e já sobe o docker
- docker-compose up --build
 
** apenas compilar no docker
- docker-compose build

** apenas compilar local
- mvn package -DskipTests