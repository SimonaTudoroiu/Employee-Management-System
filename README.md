# Employee-Management-System

## **Overview**
The Employee Management System is a platform designed to efficiently manage employees, their activities, departments, and performance within an organization. It simplifies administrative tasks and helps streamline operations.

## **Technologies**
This REST API for the Employee Management System uses the following technologies:
* Spring Boot for the REST API
* MySql for the database
* Spring JPA and Hibernate for database manipulation
* JUnit and Mockito for unit tests
* SpringBootTest for application startup check
* SpringDoc OpenAPI for Swagger documentation


## **Business Requirements**
* Employees can create their profiles.
* Employees can update their information.
* Employees can be assigned to a specific department.
* Employees can be assigned to one or more projects.
* Employees can apply for leaves, which will later be approved or rejected.
* Attendance can be tracked daily for each employee.
* Performance reviews can be added by other employees periodically.
* Employees can view their performance reviews.
* Employees can view their leave history.
* If an employee is fired, their profile can be deleted.


## **MVP Features**
1. *Employee Profile Management*
    * CRUD operations for employee profiles.
    * Employees can update their phone number and their role, in case of promotion.
2. *Department Management*
    * Employees are assigned to a department when their profile is created.
    * Projects are assigned to a department when the project is created.
3. *Project Assignment*
    * Create projects and assign teams to them.
    * Remove an employee from a project.
4. *Leave management*
    * Apply for a leave with the reason and the period of leave.
    * Approve or reject the leave requests.
5. *Performance Review*
    * Employee can review their colleagues with comments and a score from 1 to 5.
    * Employees can view their performance reviews.


## **Entities and Relationships**
* *Employee*
    * Fields: `id`, `name`, `email`, `phone`, `role`, `departmentId`, `joinDate`.
    * Relationships:
        * Belongs to one *Department*
        * Has many *Attendance*, *Leave*, *Review* and *Project*
* *Department*
    * Fields: `id`, `name`, `deadline`, `departmentId`.
    * Relationships:
        * Has many *Employee* and *Project*
* *Project*
    * Fields: `id`, `name`, `deadline`, `departmentId`.
    * Relationships:
        * Belongs to one *Department*
        * Has many *Employee*
* *Attendance*
    * Fields: `id`, `employeeId`, `date`, `checkIn`, `checkOut`.
    * Relationships:
        * Belongs to one *Employee*
* *Leave*
    * Fields: `id`, `employeeId`, `startDate`, `endDate`, `reason`, `status` (PENDING, APPROVED, REJECTED).
    * Relationships:
        * Belongs to one *Employee*
* *Review*
    * Fields: `id`, `employeeId`, `date`, `comments`, `score`.
    * Relationships:
        * Belongs to one *Employee*

## **REST Endpoints**
* *Employee*:
    * `POST /employees`: Create a new employee.
    * `PUT /employees/{id}`: Update employee details.
    * `GET /employees`: Get all employees
    * `GET /employees/{id}`: Get employee details by ID.
    * `DELETE /employees/{id}`: Remove an employee.
* *Department*:
    * `POST /departments`: Add a new department.
    * `GET /departments`: Gel all departments
    * `GET /departments/{id}/employees`: Get employees in a department.
    * `DELETE /departments/{id}`: Remove a department
* *Project*
    * `POST /projects`: Add a new project.
    * `GET /projects`: Get all projects
    * `POST /projects/{projectId}/assign`: Assign a list of employees to a project
    * `GET /projects/{projectId}/employees`: Get assigned employees to a project
    * `DELETE /projects/{projectId}/employees/{employeeId}`: Remove an employee from a project
* *Attendance*
    * `POST /attendance/checkin`: Employee check-in.
    * `POST /attendance/checkout`: Employee check-out.
    * `GET /attendance/{employeeId}`: Get attendance records for an employee.
* *Leave*
    * `POST /leaves`: Apply for leave.
    * `PUT /leaves/{id}/approve`: Approve a leave request.
    * `PUT /leaves/{id}/reject`: Reject a leave request.
    * `GET /leaves/{departmentId}`: Get all leaves for a department
* *Review*
    * `POST /reviews`: Add a performance review.
    * `GET /reviews/{employeeId}`: Get performance reviews for an employee.


## **Entity Relationship Diagram**

![Employee_Management_System_ERD drawio](https://github.com/user-attachments/assets/6e7ea0c5-8a55-4454-9395-3433609719c8)
