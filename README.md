# marketplacedemo

SpringBoot project

Features (web page):

- Display available users (clients) and products in tables (data is stored in database).
- Add new clients and products or delete them.
- Client and server side validation for added records (with displayed error message).
- Purchases:
    - To add product to a user you use \<select\> elements under the tables for selecting client and product, then
      press 'Buy'.
    - If purchase was successful, success alert is displayed. Otherwise, alert contains error message (like not enough
      money).
    - To see what products certain client has you click on client's row in 'Clients' table. Then client's row is
      highlighted  
      and 'Products' table displays the products that selected client has. 'Remove' button changes to 'Refund' and if
      pressed,   
      removes the product from client and increases his balance by product's price.
    - To see who bought certain product, similarly click on the product row in 'Products' table.
    - 'Refresh and display all' button refreshes both tables displaying all clients and products available (not just
      for  
      selected row). It also resets 'Refund' buttons to 'Remove' (that remove records from DB).

Validation notes:

Client:

- firstName - only letters, 2 or more (`\p{L}{2,}`)
- lastName - only letters, 2 or more (`\p{L}{2,}`)
- balance - min 0 for web, no limits for backend (still won't allow to buy products with price higher than available
  positive amount of money)

Product:

- name - letters, digits, spaces and dashes, 1 or more symbol (`^([\p{L}\d]+[ -]?)*[\p{L}\d]+$`)
- price - number, 0 or higher

![img.png](program_screenshot.png)