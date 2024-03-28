# ECE 651 - Spring 24 - Team Project - Evolution 2
## Deadline
- **Evolution 2 must be completed by 11:59pm on Apr 11th.** *(always check canvas deadline)*

## Before you begin. 
Be fore you begin with evolution 2, make sure you **released** and **tagged** the completion of Evolution 1.

## Request
As your initial application was a sucess, more faculty wanted to use it, thus your customer is requesting the following changes. *As you did for evol 1, clarify and validate your requirement specifications with your lead TA*.

- **Client/Server architecture**
    - The repository of student attendance should be stored in a centralized server which provide services to standalone console applications. 
    - The server will run on a linux server (VM)
    - Clients will be light-clients. I.e., no data or mayor processing should happen on the client side (input processing is ok).     
    - The data should be stored in a RDBMS. The customer currently uses MySQL, but other RDBMS can be used as far as no extra licensing is required. 
- **Multiple User registration**
    - as many different users will use your system, thus they need to authenticated. 
    - *Desired functionality for users*: Add/Remove/Update users. (groups of 3 just Add)
    - users can be facutly or students.
    - User administration will not go through client/server application, should be run on a standalone user admin app.
- **Multiple classes**
    - students may be enrolled into multiple classes.
    - each class may have one or more sections (at least one). 
    - each section will have an instructor (facutly member).
    - *Desired functionality*: 
        - Add/Remove/Update classes and sections. (groups of 3 just Add/Remove)
            - ::Danger:: Remove a class/section will be destructive. Think on UX.
        - Enroll students into classes (one particular sections). 
            - Enrollment can be manual (one by one) or batch (e.g., reading from a csv file). 
            - Groups of 3 just batch enroll.
    - Class/section management, can go through C/S or stand alone application, your decision. 
- **Faculty access (C/S)**
    - Faculty should be able to select which class to take attendance. A faculty may teach many classes/sections
        - Student names will be displayed and the instructor will record the attendance for each one. 
    - Faculty can optionally record the attendance for a previous day, or modify the attendance for a student on a paticular day in a class. 
    - Attendance report can be generated for the faculty, showing, for each student in a class the attendance participation. Tardy students will count towards 80% of participation that day. 
- **Student application (C/S)**
    - Students will also be able to login to the system. It can be the same application as faculty or a separate one.
    - They should be able to change notification preferences. E.g., for ECE551 students don't want notification but for ECE580 they need it. Notifications will go through email.
    - They should be able to obtain a summary and detailed report of attendance for each of their classes.
## Constraints
- **No frameworks** or pre-existing solutions may be used.
    - E.g., Hibernate, jakarta, etc.
- **Data Management** should be implemented by the team. Consider impact of changing the RDBMS to another or a Non-SQL storage system. *Hint: review DAO pattern*. 

## Deliverables
- **Project Management**:
    - All Relevant Sprint documentation.
    - Task breakdown, estimation, assignment and actual effort (hours). 
        - *Idealy, a spreadsheet* otherwise a form of a report.
    - *Issues*. All tasks, bugs, etc. should be tracked on GitLab.
    - *Code Review*:
        - Merge Requests. Including code reviews. 
- **Updated Requirements**
    - Clear specification of requirements, discussed with your Lead TA and approved. 
    - Use Case mapping. 
- **System Desing**:
    - Updated class diagram. 
    - Changes impact report. A summary of how the new changes impacted your previous solution. 
- **System Implementation**
    - Java implementation. 

## Submission
- All deliverables must be submitted to GitLab. 
- A Release and Tag must be created before the deadline. 
