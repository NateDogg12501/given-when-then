# given-when-then
Testing framework to easily chain given-when-then test cases

### Application Use Case
##### Entities
- Order => An order has many items. This is a simple order that might be placed at a business.
- Item => An item that might be bought on an order from a store. 

### To-Do
- [ ] App structure
    - [ ] Controllers
        - [x] Order
            - [x] getOrders
            - [x] getOrder/{id}
            - [x] createOrder
        - [ ] Item
            - [ ] getItems
            - [ ] getItem/{sku}
            - [ ] createItem
    - [ ] Persistence
    - [ ] Entities
        - [x] order
        - [x] Item
- [ ] Unit Tests
- [ ] Integration Tests
    - [ ] Orders
        - [x] get all orders
        - [x] get order with valid id
        - [x] get order with invalid id
        - [x] create order happy path
        - [x] create order with invalid item sku
        - [x] create order with no items
    - [ ] Items
        - [ ] get all items
        - [ ] get item with valid sku
        - [ ] get item with invalid sku
        - [ ] create item happy path
        - [ ] create item with negative price
        - [ ] create item with negative sku
        - [ ] create item with duplicate sku
- [ ] Smoke Tests  
- [ ] Contract Tests      
    