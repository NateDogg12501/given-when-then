# given-when-then
Testing framework to easily chain given-when-then test cases

### Application Use Case
##### Entities
- Order => An order has many items. This is a simple order that might be placed at a business.
- Item => An item that might be bought on an order from a store. 

### To-Do
- [x] App structure
    - [x] Controllers
        - [x] Order
            - [x] getOrders
            - [x] getOrder/{id}
            - [x] createOrder
        - [x] Item
            - [x] getItems
            - [x] getItem/{sku}
            - [x] createItem
    - [x] Persistence
      - [x] Order
      - [x] Item
    - [x] Entities
        - [x] order
        - [x] Item
- [ ] Unit Tests
- [x] Base Tests
    - [x] Orders
        - [x] get all orders
        - [x] get order with valid id
        - [x] get order with invalid id
        - [x] create order happy path
        - [x] create order with invalid item sku
        - [x] create order with no items
    - [x] Items
        - [x] get all items
        - [x] get item with valid sku
        - [x] get item with invalid sku
        - [x] create item happy path
        - [x] create item with negative price
        - [x] create item with negative sku
        - [x] create item with duplicate sku
- [ ] Integration Tests
	- [x] Items
	- [x] Orders
	- [x] Clean up messaging for assertEquals(...)
	- [ ] Auto clean up repos/any resources given scenarios open should teardown themselves
	- [ ] Think about what chains should close back to original (what options are presented when drilling down)
- [ ] Smoke Tests  
- [ ] Contract Tests      
    