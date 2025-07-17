# Service-Request-Handling-Project

manikanta_work123

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
    }

    class ServiceRequest {
        +int requestID
        +string title
        +string description
        +enum status
        +datetime createdAt
        +int userID
    }

    class RequestAssignment {
        +int assignmentID
        +int requestID
        +int technicianID
        +datetime assignedAt
    }

    class Technician {
        +int technicianID
        +string techName
    }

    class Admin {
        +int adminID
        +string adminName
    }

    class RequestUpdate {
        +int updateID
        +int requestID
        +int updatedBy
        +string updateNote
        +enum status
        +datetime updatedAt
    }

    %% Relationships
    User <|-- Technician
    User <|-- Admin
    User "1" --> "many" ServiceRequest : creates
    ServiceRequest "1" --> "0..1" RequestAssignment : has
    RequestAssignment "1" --> "1" Technician : assigned to
    ServiceRequest "1" --> "many" RequestUpdate : has
    RequestUpdate "1" --> "1" User : updated by
```




## 🧱 UML Class Diagram with Methods – Service Request System

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
        +viewRequests()
    }

    class Admin {
        +int adminID
        +string adminName
        +assignTechnician(requestID, techID)
        +rejectRequest(requestID)
        +viewAllRequests()
    }

    class Technician {
        +int technicianID
        +string techName
        +updateStatus(requestID, status)
        +addResolution(requestID, note)
        +closeRequest(requestID)
    }

    class ServiceRequest {
        +int requestID
        +string title
        +string description
        +enum status
        +datetime createdAt
        +int userID
        +submit()
        +updateStatus(newStatus)
    }

    class RequestAssignment {
        +int assignmentID
        +int requestID
        +int technicianID
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

    %% Relationships
    User <|-- Technician
    User <|-- Admin
    User "1" --> "many" ServiceRequest : creates
    ServiceRequest "1" --> "0..1" RequestAssignment : has
    RequestAssignment "1" --> "1" Technician : assigned to
    ServiceRequest "1" --> "many" RequestUpdate : has
    RequestUpdate "1" --> "1" User : updated by
```








## 🗄️ SQL Schema – Service Request Handling System

```sql
-- Users Table
CREATE TABLE Users (
    UserID INT PRIMARY KEY AUTO_INCREMENT,
    UserName VARCHAR(100),
    Role ENUM('User', 'Admin', 'Technician'),
    Email VARCHAR(100),
    Password VARCHAR(100)
);

-- Service Requests Table
CREATE TABLE ServiceRequests (
    RequestID INT PRIMARY KEY AUTO_INCREMENT,
    UserID INT,
    Title VARCHAR(100),
    Description TEXT,
    Status ENUM('New', 'Assigned', 'In Progress', 'Resolved', 'Closed', 'Rejected'),
    CreatedAt DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (UserID) REFERENCES Users(UserID)
);

-- Request Assignments Table
CREATE TABLE RequestAssignments (
    AssignmentID INT PRIMARY KEY AUTO_INCREMENT,
    RequestID INT,
    TechnicianID INT,
    AssignedAt DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (RequestID) REFERENCES ServiceRequests(RequestID),
    FOREIGN KEY (TechnicianID) REFERENCES Users(UserID)
);

-- Request Updates Table
CREATE TABLE RequestUpdates (
    UpdateID INT PRIMARY KEY AUTO_INCREMENT,
    RequestID INT,
    UpdatedBy INT,
    UpdateNote TEXT,
    Status ENUM('Assigned', 'In Progress', 'Resolved', 'Closed'),
    UpdatedAt DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (RequestID) REFERENCES ServiceRequests(RequestID),
    FOREIGN KEY (UpdatedBy) REFERENCES Users(UserID)
);

