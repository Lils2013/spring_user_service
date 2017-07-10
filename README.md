# spring_user_service

To build and run the project, open it in an IDE, preferably IntelliJ, and run 'Application.main()' in src/main/java/alexander/tsoy/Application.java.

The tests are in src/test/java/alexander/tsoy.

To create a new user:
```
curl -i -X POST -H "Content-Type:application/json" -d "{  \"firstName\" : \"Alexander\",  \"lastName\" : \"Tsoy\", \"birthDate\" : \"29.10.1994\", \"email\" : \"Lils2010@gmail.com\",  \"password\" : \"qwerty\"}" http://localhost:8080/people
```
To search for a user by email:

```
curl http://localhost:8080/people/search/findByEmail?email=Lils2010@gmail.com
```

To delete a user:

```
curl -X DELETE http://localhost:8080/people/1
```

