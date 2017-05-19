# TASK 3

## How to run both services with authorizacion:
    1.Go to task3 folder 
    2.Download docker-compose.yml 
    3.Run:   docker-compose up -d
## Registration
    /register 
    
    {	
        "username": "Jonas123",
        "password": "yourpassword",
        "permissions": "admin"
    }
    {	
        "username": "Jonas1234",
        "password": "yourpassword",
        "permissions": "user"
    }
    
## Login
    login as admin:
    /login  
    
    {	
        "username": "Arnas",
        "password": "arnas123"
    }
    
    login as user:
    /login  
    
    {	
        "username": "Bena",
        "password": "benas123"
    }
    