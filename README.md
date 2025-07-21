# 🛠️ Service Request Handling System

### Developed by:

**Admin Module**: _K Manikanta Reddy_

**Employee Module**: _M Lahari_

**Department Module**: _D Bhagath Krishna Vamsi_


# 📖 Project Overview
The Service Request Handling System is a centralized platform that enables employees to raise service tickets, admins to assign and manage them, and departments to act on those tickets. The system supports:

Ticket creation, tracking, and cancellation by employees

Ticket assignment and oversight by admins

Ticket resolution, commenting, and closing by departments

This microservice-style project promotes modular responsibility with secure login for different user roles.

# ⚙️ Modules & Responsibilities
## 🧑‍💼 Employee Module (Lahari)
Login

Raise a service request

View my tickets

Cancel my ticket

Provide feedback on resolved ticket

## 🧑‍💻 Department Module (Bhagath)
View assigned tickets

Accept ticket

Update ticket status

Comment on tickets

Close tickets with reason

## 👨‍💼 Admin Module (Manikanta)
Admin login

View all employee tickets

View tickets by employee

Assign ticket to department

Cancel any ticket on behalf of user

# 🔐 Authentication Endpoints
## 🧑 Employee Login
**POST**  ``` /employee/login```

json
```
{
  "email": "employee@example.com",
  "password": "emppass123"
}
```
## 👨 Admin Login
**POST** ```/admin/login```

json
```
{
  "email": "admin@example.com",
  "password": "adminpass"
}
```
# 🎫 Employee Services
## Raise a New Ticket
**POST** ```/raiseTicket```

json
```
{
  "employeeId": 101,
  "title": "System crash",
  "description": "My desktop crashes frequently"
}
```

## View My Tickets
GET ```/viewMyTickets/{id}```



## Cancel My Ticket
**POST** ```/cancleMyTicket/{id}```


## Submit Feedback
**POST** ``` /giveFeedback```

json
```
{
  "ticketId": 4,
  "feedbackText": "Issue resolved quickly, thanks!"
}
```
# 🛠️ Department Services
## View Tickets by Department
GET ```/department/tickets/{departmentId}```

## Accept a Ticket
PUT ```/department/ticket/accept/{ticketId}```

## Update Ticket Status
PUT ```/department/ticket/status```

Request JSON:

json
```
{
  "ticketId": 101,
  "status": "IN_PROGRESS"
}
```
## Add Comment to Ticket
**POST** ```/department/ticket/comment```

json
```
{
  "ticketId": 102,
  "commenterName": "IT Department",
  "commentText": "Issue diagnosed, working on a solution."
}
```
## Close Ticket
PUT ```/department/ticket/close```

json
```
{
  "ticketId": 103,
  "reason": "Issue fixed and confirmed by user."
}
```
# 🛡️ Admin Services
## View Tickets by Employee
GET ```/admin/employee/tickets/{id}```

## View All Employee Tickets
GET ```/admin/employees/tickets```

## Assign Ticket to Department
**POST** ```/admin/assign-ticket```

json
```
{
  "ticketId": 3,
  "departmentId": 2
}
```
## Cancel a Ticket
**POST** ```/admin/ticket/cancel```

json
```
{
  "ticketId": 5,
  "cancelReason": "User requested cancellation"
}
```

## Activity Diagram 
```mermaid
flowchart TD

    A([Start]) --> B[Login Page]
    B --> C{Are credentials valid?}
    C -- Yes --> D[Go to Dashboard]
    C -- No --> B

    D --> E[Submit Request Form]
    E --> F{Is form valid?}
    F -- No --> E
    F -- Yes --> G[Save Request]
    G --> H[Set Status: New]

    H --> I[Admin reviews request]
    I --> J{Assign or Reject?}
    J -- Assign --> K[Assign to Technician]
    K --> L[Set Status: Assigned]
    J -- Reject --> M[Set Status: Rejected]

    L --> N[Technician starts work]
    N --> O[Set Status: In Progress]
    O --> P[Technician adds resolution]
    P --> Q[Set Status: Resolved]

    Q --> R[Admin/User closes request]
    R --> S[Set Status: Closed]
    S --> T([End])

    M --> T


    R --> S[Set Status: Closed]
    S --> T([End])

    M --> T
```






## State Diagram
```mermaid
stateDiagram-v2
    [*] --> New

    New --> Assigned: Admin assigns
    New --> Rejected: Rejected by Admin

    Assigned --> InProgress: Technician starts work
    Assigned --> Rejected: Rejected by Technician

    InProgress --> Resolved: Technician resolves request
    InProgress --> Rejected: Work blocked / Invalid request

    Resolved --> Closed: Closed by User/Admin

    Closed --> [*]
    Rejected --> [*]
```




## UML Use Case Diagram

```mermaid
%% Mermaid UML Use Case Diagram
%% Paste into GitHub README.md

%%{init: {'theme': 'default'}}%%
graph TD
    actorUser(User)
    actorAdmin(Admin)
    actorTech(Technician)

    UC1[Submit Service Request]
    UC2[View Request Status]
    UC3[Login]
    UC4[Logout]

    UC5[View All Requests]
    UC6[Assign Technician]
    UC7[Reject Request]

    UC8[Update Request Status]
    UC9[Add Resolution Note]
    UC10[Close Request]

    %% User actions
    actorUser --> UC1
    actorUser --> UC2
    actorUser --> UC3
    actorUser --> UC4

    %% Admin actions
    actorAdmin --> UC3
    actorAdmin --> UC4
    actorAdmin --> UC5
    actorAdmin --> UC6
    actorAdmin --> UC7

    %% Technician actions
    actorTech --> UC3
    actorTech --> UC4
    actorTech --> UC8
    actorTech --> UC9
    actorTech --> UC10
```




## UML Class Diagram

```mermaid

classDiagram
    class User {
        +int userID
        +string userName
        +string email
        +string password
        +string role
        +login()
        +logout()
    }

    class Admin {
        +assignToDepartment(requestID, departmentID)
        +rejectRequest(requestID)
        +viewAllRequests()
    }

    class Employee {
        +createRequest(title, description, category)
        +viewRequestStatus(requestID)
        +giveFeedback(requestID, feedback)
    }

    class Department {
        +int departmentID
        +string departmentName
        +acceptRequest(requestID)
        +updateStatus(requestID, newStatus)
        +closeRequest(requestID)
    }

    class IT {
        +handleITSpecificIssue(requestID)
    }

    class NonIT {
        +handleNonITSpecificIssue(requestID)
    }

    class HRFinance {
        +handleHRFinanceIssue(requestID)
    }

    class ServiceRequest {
        +int requestID
        +string title
        +string description
        +enum category  <<IT, Non-IT, HR, Finance>>
        +enum status    <<Pending, Accepted, In Progress, Closed, Rejected>>
        +datetime createdAt
        +int createdBy
        +submit()
    }

    class RequestAssignment {
        +int assignmentID
        +int requestID
        +int departmentID
        +datetime assignedAt
    }

    class RequestUpdate {
        +int updateID
        +int requestID
        +int updatedBy
        +string updateNote
        +enum status
        +datetime updatedAt
    }

    class Feedback {
        +int feedbackID
        +int requestID
        +int userID
        +string comment
        +datetime submittedAt
    }

    %% Inheritance
    Department <|-- IT
    Department <|-- NonIT
    Department <|-- HRFinance

    %% Relationships
    User <|-- Admin
    User <|-- Employee
    User "1" --> "many" ServiceRequest : creates
    ServiceRequest "1" --> "0..1" RequestAssignment : assigned to
    RequestAssignment "1" --> "1" Department : handled by
    ServiceRequest "1" --> "many" RequestUpdate : has
    RequestUpdate "1" --> "1" User : updated by
    ServiceRequest "1" --> "0..1" Feedback : receives
    Feedback "1" --> "1" User : given by
```


