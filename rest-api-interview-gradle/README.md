Running app

bck - gradle build; gradle bootRun --warning-mode=all
frontend - npm ci; npm run start

--

Create image

via dockerfile or jib maven plugin(better) :)

--

Swagger UI je zde:

http://localhost:8080/swagger-ui.html

Step by step:

1) http://localhost:8080/swagger-ui.html
2) jwt-authentication-controller, post -> authenticate -> ivosahlik, dummy
   request body
   {
   "username": "ivosahlik",
   "password": "dummy"
   }
3) token put to swagger authorize –> you can say here (allowed) io.swagger.v3.oas.models.security.SecurityScheme
4) you can try other requests get, post, put, delete without frontend
5) frontend http://localhost:3000, there is used corsy -> cz.ivosahlik.interview.Application.corsConfigurer


H2 DB:

http://localhost:8080/h2-console



