# given-when-then

### Project Overview
This project is an example framework for easy to write, easy to read, given-when-then tests.

- Given => A scenario of the state of the application. For example, an item existing
- When => An action under test. For example, calling an API endpoint
- Then => An assertion for outcomes from the action, given the scenarios in place. For example, a new item being created.

These together give us a test case. 

For example:

```
**Given** An item exists with SKU 1L
**When** Calling the Get Item by SKU API endpoint
  **And** Passing the SKU 1L to the endpoint
**Then** The item with SKU 1L is returned in the response body
```

**Note** The idea of this project is that it's more a design pattern that can be applied to any application. The code is not meant to be imported and extended as much as replicated with the scenarios, actions, and assertions that make sense to the project at hand.


### Application Use Case 

To show use of the testing framework, an example application has been built. It's meant to represent an application that tracks items and orders, such as maybe a point of sale system. It contains two sets of endpoints, each containing actions for the two core entities. 

**Note** This application can be run standalone, not just tested. Endpoints can be hit through curl, postman, etc.

##### Entities
- `Item` - A general item that may be purchased. It contains a SKU (unique identifier), Description, and Price
- `Order` - An order purchased. It contains a unique ID that's auto-generated when an order is created, a list of Items purchased, and a total price.

##### Endpoints
- `Items`
  - `Get All Items` - Returns all items
  - `Get Item by Sku` - Returns a specific item, retrieved by the SKU passed to the endpoint
  - `Create an Item` - Creates an item with the supplied SKU, description, and price
- `Orders`
  - `Get All Orders` - Returns all orders, along with their items
  - `Get Order by ID` - Returns a specific order, retrieved by the ID passed to the endpoint point
  - `Create an Order` - Creates an order with the supplied Item SKUs

### Business Requirements/Test Cases

##### Orders
```
**Given** Any existing Orders
**When** Calling Get All Orders endpoint
**Then** A 200 OK response code is returned
  **And** All existing Orders are returned
  **And** Each Item for each Order is returned as part of the Order
```

```
**Given** An existing Order
**When** Calling Get Order By ID endpoint with ID from existing order
**Then** A 200 OK response code is returned
  **And** The existing Order is returned
  **And** Each Item for each Order is returned as part of the Order
```

```
**Given** Any existing Orders
**When** Calling Get Order by ID endpoint with an ID that doesn't exist
**Then** A 404 Not Found response code is returned
  **And** No Order is returned
```

```
**Given** An existing Item
**When** Calling Create Order endpoint with the SKU from the existing item
**Then** A 201 Created response code is returned
  **And** The new Order is returned
  **And** The Item for the Order is returned as part of the Order
  **And** The Order is created in the repository
```

```  
**Given** Any existing Items
**When** Calling Create Order endpoint with a SKU that doesn't exist
**Then** A 422 Unprocessable Entity response code is returned
  **And** No Order is returned
  **And** No Order is created in the repository
```

```  
**Given** Any existing Items
**When** Calling Create Order endpoint with no SKUs
**Then** A 422 Unprocessable Entity response code is returned
  **And** No Order is returned
  **And** No Order is created in the repository
```

##### Items
```
**Given** Any existing Items
**When** Calling Get All Items endpoint
**Then** A 200 OK response code is returned
  **And** All existing Items are returned
```

```
**Given** An existing Item
**When** Calling Get Item By SKU endpoint with SKU from existing Item
**Then** A 200 OK response code is returned
  **And** The existing Item is returned
```

```
**Given** Any existing Items
**When** Calling Get Item by SKU endpoint with a SKU that doesn't exist
**Then** A 404 Not Found response code is returned
  **And** No Item is returned
```

```
**Given** Any existing Items
**When** Calling Create Item with a SKU that doesn't exist
**Then** A 201 Created response code is returned
  **And** The new Item is returned
  **And** The Item is created in the repository
```

```  
**Given** Any existing Items
**When** Calling Create Item with a negative price
**Then** A 422 Unprocessable Entity response code is returned
  **And** No Item is returned
  **And** No Item is created in the repository
```

```
**Given** Any existing Items
**When** Calling Create Item with a negative SKU
**Then** A 422 Unprocessable Entity response code is returned
  **And** No Item is returned
  **And** No Item is created in the repository
```

```
**Given** An existing Item
**When** Calling Create Item with the same SKU as the existing Item
**Then** A 409 Conflict response code is returned
  **And** No Item is returned
  **And** No Item is created in the repository
```

### Integration Tests
First, we define integration tests as testing the entire application stack. The term isn't as important as the purpose/scope of the tests. We stand up the entire application but only call endpoints, thus testing anything downstream and back, and then asserting both responses and the state of the repository. We are interested in testing the application in full, with complete wiring and interaction with any dependencies it owns (repositories, etc.).

With that in mind, a `Test` is interested in setting up `Given` scenarios, performing the `When` action by calling an endpoint, then asserting the `Then` asserts to confirm the outcome of said `When` actions. 

From a technical perspective:
- `Test` - A test run using JUnit 5
- `GivenScenarioChain` - A given scenario, with any options. For example, an existing Item may have a specific price. Additionally, after each of these are done building, they'll typically fire off an action (insert into a database, etc.)
- `WhenActionChain` - A when action, with any options. For example, calling Get Item by SKU and passing the SKU. These are currently all various HTTP requests, and should return a response. The response is then utilized in certain assertions.
- `ThenAssertChain` - A then assertion, with any options. For example, confirming an Item was created in the repository and with a specific price.

This all together gives us the format 

```
given()
  .someScenario()
.when()
  .someAction()
.then()
  .someAssertion()
```

**Note** The `gwt.test.base` package contains the same test cases, but outlined per test. This is meant to ensure the core application is operating as expected and not affected as part of building the chained test framework, as well as provide a before/after view of how we can rewrite the same tests in a more flexible format.

### Lessons Learned, Notes & Thoughts
- The repos are cleaned up as part of the base integration test due to the foreign keys & mapping tables. To clean up an entity scenario required knowledge outside the specific scenario, and it quickly became too messy for a scenario cleaning up just its setup. 
- Other scenario cleanup (resetting the auto-generated ID, for example) was doable and achieved at one point, but just felt superfluous. If a test cares about it, it should set it up, if it doesn't, then it shouldn't be concerned with where it stands.
- The framework was not applied to unit tests as those tend to be much more specific. The choices and set up would probably be too much to meaningfully abstract it out to a chain


### Potential Future Features/Ideas
- [ ] Can we autowire given/when/then beans to then switch out implementations for integration, smoke, and other tests?
- [ ] Can we limit the amount of options for any particular chain? For example, the `WhenActionChain` is intended to only make one call, so we don't need to see the rest. The less options, the more guided the test creator will be, which is inline with the spirit of this project
- [ ] Feels a little "easy" making certain fields static and passing around. This also makes the tests unable to run in parallel. This should be cleaned up to allow for quick test suite executions.