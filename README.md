# TASK 3

## How to run both services with authorizacion:
    1.Go to task3 folder 
    2.Download docker-compose.yml 
    3.Run:   docker-compose up -d
## Registration
    /register 
    ```
    {	
        "username": "Jonas123",
        "password": "yourpassword",
        "permissions": "user/admin"
    }
    ```
## Login
    /login
    ```
    {	
        "username": "Jonas123",
        "password": "yourpassword"
    }
    ```
    
# TASK 2

## How to run both services:
    Download docker-compose.yml and run:   docker-compose up -d
    
**POST example:**
```
{	
    "name": "MIF",
    "city": "Vilnius",
    "phoneNumber": 86515150,
    "ownerName": "Dalia",
    "ownerSurname": "Grybauskaite",
    "ownerGender": "female",
    "ownerAddress": "Didlaukio 65"
}
```

# TASK 1  

## Run from hub.docker.com:
    docker run -d -p 80:80 zenka/webservice:1

## 1. Build docker:
    docker build -t companies/rest:1 .

## 2. Run docker:
    docker run -d -p 80:80 companies/rest:1
    
## 3. Now you can access web service with /companies
    
## GET:    
    /companies                      -   get all companies.
    /companies/:id                  -   get company, with id parameter.
    /companies/city/:city           -   get companies, with parameter city .
    /companies/name/:name           -   get companies, with parameter name.
    /companies/cityCount/:city      -   get companies count, with parameter city.
    /companies/companyCount/:name   -   get companies count, with parameter name.
## DELETE:    
    /companies/:id                  -   delete company, with id parameter.
## POST:
    /companies/:id                  -   post new company, with id parameter.
## PUT:
    /companies/:id                  -   update all object, with id parameter.
## PATCH:
    /companies/:id                  -   update object field's, with id parameter.    

**POST example:**
```
{	
    "name": "MIF",
    "city": "Vilnius",
    "phoneNumber": 864123050
}
```
