# given-when-then
Testing framework to easily chain given-when-then test cases

### Application Use Case
##### Entities
- Order => An order has many items. This is a simple order that might be placed at a business.
- Item => An item that might be bought on an order from a store. 

### To-Do
- [ ] App structure
    - [x] Controllers
        - [x] Order
            - [x] getOrders
            - [x] getOrder/{id}
            - [x] createOrder
        - [x] Item
            - [x] getItems
            - [x] getItem/{sku}
            - [x] createItem
    - [ ] Persistence
      - [ ] Order
      - [x] Item
    - [ ] Entities
        - [x] order
        - [x] Item
- [ ] Unit Tests
- [ ] Integration Tests
    - [ ] Orders
        - [ ] get all orders
        - [ ] get order with valid id
        - [ ] get order with invalid id
        - [ ] create order happy path
        - [ ] create order with invalid item sku
        - [ ] create order with no items
    - [x] Items
        - [x] get all items
        - [x] get item with valid sku
        - [x] get item with invalid sku
        - [x] create item happy path
        - [x] create item with negative price
        - [x] create item with negative sku
        - [x] create item with duplicate sku
- [ ] Smoke Tests  
- [ ] Contract Tests      
    