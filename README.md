# Congestion Tax Calculator

Welcome the Volvo Cars Congestion Tax Calculator assignment.

This repository contains a developer [assignment](ASSIGNMENT.md) used as a basis for candidate intervew and evaluation.

Clone this repository to get started. Due to a number of reasons, not least privacy, you will be asked to zip your solution and mail it in, instead of submitting a pull-request. In order to maintain an unbiased reviewing process, please ensure to **keep your name or other Personal Identifiable Information (PII) from the code**.


I have developed Spring Boot Application and tested through Postman.

You can use the below curl for testing the output

curl --location 'localhost:8080/calculateTax' \
--header 'Content-Type: application/json' \
--data '{

        "vehicle": "Car",
    
    "dates": [
        "2013-07-02T07:05:30",
        "2013-07-08T15:30:45",
        "2013-01-02T07:59:00"
    ]
}'

Also Created Constant class and if I have more time, I would have written properties file.

If I have more time, I would have done Exception handling, Unit test cases and some code refactoring

Tax exempted Vehicles: Only these vehicles will be free of Tax.

- Emergency vehicles
- Busses
- Diplomat vehicles
- Motorcycles
- Military vehicles
- Foreign vehicles
