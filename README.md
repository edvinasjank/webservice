# How to run:

## 1. Build docker:
    docker build -t companies/rest:1 .

## 2. Run coker:
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
    /companies                      -   post new company.
## PUT:
    /companies/:id                  -   update company, with id parameter.
    


**POST example:**

{	
    "name": "MIF",
    "city": "Vilnius",
    "phoneNumber": 80123050
}
