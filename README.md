# Critter Chronologer

Critter Chronologer a Software as a Service application that provides a scheduling interface for a small business that takes care of animals. This Spring Boot project will allow users to create pets, owners, and employees, and then schedule events for employees to provide services for pets.


## Getting Started

### Dependencies

* [IntelliJ IDEA Community Edition](https://www.jetbrains.com/idea/download) (or Ultimate) recommended 
* [Java SE Development Kit 8+](https://www.oracle.com/technetwork/java/javase/downloads/index.html)
* [Maven](https://maven.apache.org/download.cgi)
* [MySQL Server 8](https://dev.mysql.com/downloads/mysql/) (or another standalone SQL instance)
* [Postman](https://www.getpostman.com/downloads/)

Part of this project involves configuring a Spring application to connect to an external data source. Before beginning this project, you must install a database to connect to. Here are [instructions for installing MySQL 8](https://dev.mysql.com/doc/refman/8.0/en/installing.html).

You should install the Server and Connector/J, but it is also convenient to install the Documentation and Workbench.

Alternately, you may wish to run MySQL in a docker container, using [these instructions](https://hub.docker.com/_/mysql/).

After installing the Server, you will need to create a user that your application will use to perform operations on the server. You should create a user that has all permissions on localhost using the sql command found [here](https://dev.mysql.com/doc/refman/8.0/en/creating-accounts.html).

Another SQL database may be used if desired, but do not use the H2 in-memory database as your primary datasource.

### Installation

1. Clone or download this repository.
2. Open IntelliJ IDEA.
3. In IDEA, select `File` -> `Open` and navigate to the `critter` directory within this repository. Select that directory to open.
4. The project should open in IDEA. In the project structure, navigate to `src/main/java/com.udacity.jdnd.course3.critter`. 
5. Within that directory, click on CritterApplication.java and select `Run` -> `Debug CritterApplication`. 
6. Open a browser and navigate to the url: [http://localhost:8080/test](http://localhost:8080/test)

You should see the message "Critter Starter installed successfully" in your browser.

## Testing

Once you have completed the above installation, you should also be able to run the included unit tests to verify basic functionality. To run unit tests:

### Tested Conditions
Tests will pass under the following conditions:

* `testCreateCustomer` - **UserController.saveCustomer** returns a saved customer matching the request
* `testCreateEmployee` - **UserController.saveEmployee** returns a saved employee matching the request
* `testAddPetsToCustomer` - **PetController.getPetsByOwner** returns a saved pet with the same id and name as the one saved with **UserController.savePet** for a given owner
* `testFindPetsByOwner` - **PetController.getPetsByOwner** returns all pets saved for that owner.
* `testFindOwnerByPet` - **UserController.getOwnerByPet** returns the saved owner used to create the pet.
* `testChangeEmployeeAvailability` - **UserController.getEmployee** returns an employee with the same availability as set for that employee by **UserControler.setAvailability**
* `testFindEmployeesByServiceAndTime` - **UserController.findEmployeesForService** returns all saved employees that have the requested availability and skills and none that do not
* `testSchedulePetsForServiceWithEmployee` - **ScheduleController.createSchedule** returns a saved schedule matching the requested activities, pets, employees, and date
* `testFindScheduleByEntities` - **ScheduleController.getScheduleForEmployee** returns all saved schedules containing that employee. **ScheduleController.getScheduleForPet** returns all saved schedules for that pet. **ScheduleController.getScheduleForCustomer** returns all saved schedules for any pets belonging to that owner.

### Postman
In addition to the included unit tests, a Postman collection has been added to the repo. 

1. Open Postman.
2. Select the `Import` button.
3. Import the file found in this repository under `src/main/resource/Udacity.postman_collection.json`
4. Expand the Udacity folder in postman.

Each entry in this collection contains information in its `Body` tab if necessary and all requests should function for a completed project. Depending on your key generation strategy, you may need to edit the specific ids in these requests.

## Built With

* [Spring Boot](https://spring.io/projects/spring-boot) - Framework providing dependency injection, web framework, data binding, resource management, transaction management, and more.
* [Google Guava](https://github.com/google/guava) - A set of core libraries used in this project for their collections utilities.
* [H2 Database Engine](https://www.h2database.com/html/main.html) - An in-memory database used in this project to run unit tests.
* [MySQL Connector/J](https://www.mysql.com/products/connector/) - JDBC Drivers to allow Java to connect to MySQL Server

## Section 1: Connect Application and Unit Tests to Datasources

### Connect applications to external MySQL database

src/main/resources/application.properties file contains entries specifying the datasource url and user credentials.

### Connect unit tests to internal H2 database

src/test/resources/application.properties file contains entries specifying the internal h2 url and credentials.

### Initialize DataSources from within Spring

Tables are created in a schema.sql or the application.properties file specifies an initialization mode and ddl-auto.

## Section 2: Design Data Layer
### Create Entities that represent the storage needs of the application

Each Entity should represent a single, coherent data type. This project will require Entities that represent multiple types of pets, both customer and employee users, and schedules that associate pets and employees. This will require at least three Entity classes and perhaps more, depending on which strategies for managing complex or polymorphic types are chosen.

Relationships between Entities should be clear from the Entity design. Entities that contain references to multiple objects of the same type should represent that relationship with collection classes, not by packing multiple values into a single field, such as a delimited String or bit-packed integer.

If polymorphic types are used, be sure to consider which table mapping strategy you wish to use and comment in the respective Entity classes why you’ve chosen a particular strategy.

### Create components to access the data source

The application should either use the DAO pattern or Repository pattern to isolate access to your data source. You should have one DAO or Repository for each Entity you define and that component will expose CRUD operations to the rest of the program.

A DAO or Repository can handle complex query operation, but should not combine multiple separate actions into a single call.

### Choose appropriate data types

When considering data that can be represented by a variety of types, prefer choices that maximize clarity and limit the potential for invalid data.

Date vs. DateTime vs. Time - Do not store date or time information unrelated to the requirements of that field.
Set vs. List - Select collections that match your constraints, such as uniqueness.
Enum vs. String constant vs. int constant - Where possible, prefer Enum representation in Java to maximize compiler assistance. SQL representation is up to you.
Additionally, consider both the current and long term needs of the data and choose data types that will be resilient to change without being inefficient.

Long vs Int - Generally longs for ids and ints for everything else is fine, but always consider the ranges for your values
SQL Column width - Large widths don’t cost anything if unused, so avoid imposing narrow width restrictions on fields that could occasionally be long, like name. Restrict width on fields you know will always have a finite length and increase width on fields that you want to allow more than the default (255 for hibernate).

### Set Transaction Boundaries

Individual SQL request are already transactional, but any part of your program that initiates a database request should start a transaction before taking steps that can cause failure. For most projects, a sensible transaction boundary will likely occur at the Service layer. You can begin transactions by annotating the methods @Transactional, or you can specify an entire class as @Transactional to mark all methods transactional.

If you wish to use the EntityManager to manually start and end transactions you may, but be sure to minimize the scope of each method to limit the necessity.

## Section 3: Multilayer Architecture
### Transform Entity Data into DTOs

The output from your Data layer is in a format suitable for use by the rest of the application, but not necessarily the format you want to provide to consumers of your REST endpoints.

For the purpose of this program, a number of pre-made DTOs have been provided to demonstrate the requirements of the front end without implying the structure of data on the back end. DTO needs will vary by consumer, but for the purpose of this program you will need to convert your Entity data back into the provided DTO format.

This mapping should happen either in the DTO itself or in the Controller layer, so that the Service layer does not need to know anything about DTO structure. It should not happen in the Service layer. Feel free to use Spring utilities such as BeanUtils.copyProperties to facilitate copying properties of the same name between objects.

If you’re feeling adventurous, check out the Stand Out Suggestions #2 and replace some of the DTOs with the original Entities annotated using JSONView and JSONIgnore. Many applications will create DTOs for large transformations but expose annotated Entities for scenarios where little or no transformation is necessary. Note that this approach may require updates to the unit tests.

### Separate Domain Logic from Persistence Layer

Partition your logic into these layers:

Service Layer retrieves information from one or more data sources. It can handle coordination between multiple data sources to solve multi-step problems
Data Layer modifies or returns Entities. It can join tables to aggregate data across multiple Entity types but should avoid performing multiple operations in a single request.
For example, if you wanted to modify an incoming Schedule request if it occurs on the same date as a Pet’s birthday, you would do this in the Service layer by first looking up the Pet and then modifying and saving the Schedule.

However, if you wanted to find all pets who had an event scheduled on their birthday, this would make more sense as a single query, rather than having the Service layer request all Schedules and compare dates itself.

Validation should occur in the Service layer rather than the Data layer, so rather than writing a query that will fail for invalid data, it is better to handle or throw an exception from the Service layer.

Do not put any domain logic into the Controller layer.

### DAO or Repository objects focus on their own Entities

Repositories should return objects of their own Entity type. For example, you would expect a “findPetsByOwner” query in the Pet Repository, rather than creating a method in a User or Customer Repository that looks up a customer then returns the Pets associated with it.

While the Data layer can join and sometimes even modify other tables, the primary focus should be on the Entity it manages. In this project, every method that returns an Entity or List of Entities from the data layer should exist in the DAO or Repository of the same name as that Entity.

All the requests in the Postman collection can be run and return correct information based on the data requested.
## License

This project is licensed under the MIT License - see the [LICENSE.md]()
