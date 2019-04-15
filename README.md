# BGreen

Application that allows you to log your activities, tracks how much CO2 you save and compares it to you friends'.

## Demo

[![IMAGE ALT TEXT HERE](https://lh3.googleusercontent.com/PcjrymGMJmSYWtw22DU57JV5wa3QQQ__bKR_6vgKPTdWSEI5MR9S8a48Q2a7rHxDMNf7lt-Df1T1K2m2hFeF1yPigqK5zujtE9hZS74pU-ydkgM_O3LBxTGs3NUTF9Kt8KXjayAuEg=w600-h315-p-k-no)](https://photos.google.com/share/AF1QipMYJApd1qjWiTkRwDzW9Fm3ITjfdzs9FR76aocXBflN-sMW8E9khhG3PJPqQCQbnw/photo/AF1QipNqlKzRQgTulM41eiDMHU2Dn4KhK7UBM96HyUGZ?key=dmJUQ0d4ZE1yVWJLTHlwcUhzQWRLd1o3bFdBX2Rn)

## Installation instructions

##### Database setup:

1. Open the file `server/src/main/resources/application.properties`
2. Replace `{database}`, `{user}` and `{password}` with the name of the `database`, your PostgreSQL `user` and `password`

##### Commands for running the project:

1. Run the server <br>
    1.1 `cd server/` <br>
    1.2 `mvn spring-boot:run`
2. Run the client <br>
    1.1 `cd client/` <br>
    1.2 `mvn spring-boot:run`


`mvn clean install` -> Installs dependencies and executes unit tests

`mvn checkstyle:check` - Checks the code style for both client and server

To run any of the commands above for specific part (client or server), switch to the corresponding directory using `cd clien/t` or `cd server/` before executing the actual command

## Run tests

##### Tests ensure 70% branch coverage. Run them using this command:

1. 'mvn clean install'