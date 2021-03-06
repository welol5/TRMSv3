# TRMSv3

## Project description.
TRMS, or Tuition Reimbursement Management System is a full-stack web application that allows employees to submit requests for reimbursements for courses, events, and certifications. These requests can then be approved or rejected by the employee's direct supervisor, department head, and a benefits coordinator while the employee is able to track the status of their requests.

## Technologies used

* Javalin
* PostgreSQL
* Java 8
* Javascript
* HTML/CSS
* Hibernate
* JDBC
* JUnit 5
* Selenium

## Features

* Employees can make claims for reimbursement from their company.
* Employees can upload files to support their claim.
* Direct Supervisiors can accept valid claims.
* Department heads can accpet valid claims even if a supervisor has not accepted it.
* Benifits coordinators can accept claims after department heads have accepted claims.
* Direct supervisiors, Department heads, and Benifits coordinators can deny claims.
* Direct supervisiors, Department heads, and Benifits coordinators can request comments on claims.
* Employees can respond to comments.
* Employees can only request up to $1000 per year.

## Getting Started

git clone https://github.com/welol5/TRMSv3.git

Setup and AWS RDS and place the link with the username and password into the hibernate.cfg.xml file.

compile the project with maven (I recomended to a JAR file).

use java -jar [jar file name] to run the program.

## Usage

Open a web browser and navigate to your url.

The port is 8080.

## Contributors

William Elliman

## License

This project uses the following license: MIT
