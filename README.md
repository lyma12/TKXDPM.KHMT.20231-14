# TKXDPM.KHMT.20231-14

## Thành viên nhóm

=======
| Name             | Role        |
| :--------------- | :---------- |
| Mã Thiên Lý      | Team Leader |
| Nguyễn Đức Long  | Member      |
| Trần Văn Long    | Member      |
| Vũ Bá Lượng      | Member      |

## Nội dung báo cáo

<details>
  <summary>W11: 26/11/2023~02/12/2023 </summary>
<br>
<details>
<summary>Mã Thiên Lý</summary>
<br>

- Assigned tasks:
  - Thực hiện tìm coupling các class: CartMedia, Cart, BaseController, Invoice, order, orderMedia, BaseScreenHandler.

- Implementation details:
  - Pull Request(s): https://github.com/lyma12/TKXDPM.KHMT.20231-14/pull/3
  - Specific implementation details:
    - CartMedia: sử dụng content coupling
    - Cart: sử dụng content coupling
    - BaseController: sử dụng content coupling, data coupling
    - Invoice
    - order: sử dụng content coupling, common coupling
    - orderMedia: 
    - BaseScreenHandler

</details>

<details>
<summary>Nguyễn Đức Long 20194100</summary>
<br>

- Assigned Task
  - Tìm coupling trong code liên quan đến đặt hàng
- Implementation details
  - Pull request: https://github.com/lyma12/TKXDPM.KHMT.20231-14/pull/2
  - Specific implementation details: Tìm coupling trong class PlaceOrderController, RushOrderController, views.screen.shippingInfo, views.screen.invoice</p>
</details> 

<details>
    <summary>Vũ Bá Lượng 20194109</summary>
<br>

- Nhiệm vụ bài tập:
    - Chỉ ra các loại coupling cho các phương thức trong controller **HomeController.java**, **ViewCartController.java** và  **views.screen.home**, **views.screen.cart** 

- Công việc chi tiết:
    - Pull Request(s): [https://github.com/lyma12/TKXDPM.KHMT.20231-14/pull/1]()
    - Mô tả công việc cụ thể:
        - **HomeController.java**
            - Cả 3 phương thức **getAllMedia()**, **getAllTypeMedia()**, **getListMediaByType(String type)** đều sử dụng **data couping** 
        - **ViewCartController**
            - Phương thức **checkAvailabilityOfProduct()** sử dụng **control coupling**
            - Phương thức **getCartSubtotal()** sử dụng **data coupling**
        - **views.screen.home**
            - **Group_Media.java**
                - **Group_Media()** sử dụng **control coupling**
                - **setGroupInfor()** chủ yếu sử dụng **control coupling** và **data coupling**
            - **HomeScreenHandler.java**
                - **HomeScreenHandler()** sử dụng **data coupling**
                - **getBController()** sử dụng **data coupling**
                - **show()** sử dụng **data coupling**
                - **initialize()** sử dụng cả **data coupling** và **control coupling**
            - **MediaHandler.java**
                - **MediaHandler()** sử dụng cả **data coupling** và **control coupling** 
                - **setMediaInfor()** sử dụng **data coupling**
        - **views.screen.cart**
            - **CartScreenHandler.java**
                - **CartScreenHandler()** sử dụng cả **data coupling** và **control coupling**
                - **requestToPlaceOrder()** sử dụng **control coupling**
                - **getBController()** sử dụng **data coupling**
                - **requestToViewCart()** sử dụng cả **data coupling** và **control coupling**
                - **displayCart()** sử dụng cả **data coupling** và **control coupling**
                - **updateCart()** sử dụng cả **data coupling** và **control coupling**
                - **updateCartAmount()** sử dụng **data coupling**
            - **MediaHandler.java**
                - **MediaHandler()** sử dụng cả **data coupling** và **control coupling**
                - **setMedia()** sử dụng **data coupling**
                - **setMediaInfor()** sử dụng cả **data coupling** và **control coupling**
                - **initializeSpinner()** sử dụng cả **data coupling**, **control coupling** và **content coupling**
</details>

</details>

---

<details>
  <summary>W12: 04/12/2023~10/12/2023 </summary>
<br>
<details>
<summary>Mã Thiên Lý</summary>
<br>

- Assigned tasks:
  - Thực hiện tìm cohesion các class: CartMedia, Cart, BaseController, Invoice, order, orderMedia, BaseScreenHandler.

- Implementation details:
  - Pull Request(s): https://github.com/lyma12/TKXDPM.KHMT.20231-14/pull/5
  - Specific implementation details:
    - CartMedia: functional cohesion
    - Cart: functional cohesion
    - BaseController: functional cohesion
    - Invoice: functional cohesion
    - order: functional cohesion
    - orderMedia: functional cohesion
    - BaseScreenHandler: functional cohesion
<br>
</details>  
<details>
    <summary>Nguyễn Đức Long 20194100</summary>

    - Nhiệm vụ bài tập: 
- Chỉ ra các loại cohesion cho các phương thức trong controller **PlaceOrderController.java**, **RushOrderController.java**

    - Công việc chi tiết:
- Pull Request(s): [https://github.com/lyma12/TKXDPM.KHMT.20231-14/pull/7]()
    - Mô tả công việc cụ thể:
        - **PlaceOrderController.java**
            - phương thức **placeOrder** 
                - 4 phương thức **setPreviousScreen**, **setHomeScreenHandler**, **setScreenTitle**, **setBController** trong phương thức **PlaceOrder** liên kết theo loại communication cohesion vì đều hoạt động trên data cartScreen
                - 2 phương thức **createOrder** và **setOrder** liên kết theo loại sequential cohesion vì data order của phương thức createOrder và đầu vào cho phương thức setOrder
                - Phương thức **printStackTrace** liên kết theo loại coincidental cohesion vì chúng chỉ có tác dụng nhằm debug
                - Các phương thức còn lại đều là functional cohesion
            - phương thức **createOrder**: liên kết theo loại sequential cohesion với đầu ra của phương thức **orderMedia** là đầu vào
            phương thức **getlstOrderMedia.add**
            - phương thức **createInvoice**: functional cohesion
            - phương thức **processDeliveryInfo**: 
                - phương thức **LOGGER** liên kết theo loại coincidental cohesion vì chúng chỉ có tác dụng nhằm debug
                - các phương thức **setPreviousScreen**, **setHomeScreenHandler**, **setScreenTitle**, **setBController**, **show** đều hoạt động trên 1 dữ liệu là invoiceScreen -> communicational cohesion
                - còn lại là functional cohesion
            - phương thức **getCartSubtotal**: functional cohesion
            - phương thức **calculateShippingFee**: functional cohesion
            - phương thức **confirmInvoice**: functional cohesion
        - **RushOrderController.java**
            - Phương thức **checkMediaSupportRushOrder**: coincidental cohesion vì nó là 1 hàm Math.random không có liên kết gì với các phương thức khác
            - Phương thức **checkDeliveryToRushOrder**: functional cohesion
            - Phương thức **requestPlaceRushOrder**:
                - phương thức **setBController**, **setPreviousScreen**, **setHomeScreenHandler**, **setScreenTitle**, **showAndWait**: communication cohesion vì cùng hoạt động trên 1 dữ liệu là rushOrderScreen
                - phương thức **printStackTrace**: liên kết theo loại coincidental cohesion vì chúng chỉ có tác dụng nhằm debug
            - Phương thức **calculateShippingFee**: functional cohesion
        - **views.screen.invoice**: đều là functional cohesion
        - **views.screen.shippingInfo**: đều là functional cohesion
<br>
</details>
<details>
    <summary>Trần Văn Long 20200372</summary>
- Assigned tasks: thực hiện tìm và phân loại cohesion các class liên quan đến thanh toán: PaymentController, các class trong VNPaySubsystem, InternetBankingInterface, views.screen.payment
  

- Implementation details:
    - Pull Request(s): https://github.com/lyma12/TKXDPM.KHMT.20231-14/pull/9
    - Specific implementation details:
        - VNPaySubsystemController: communicational cohesion
        - VNPaySubsystem.Request: không
        - PaymentController: functional cohesion
        - PaymentScreenHandler: informational cohesion
        - ResultScreenHandler: coincidental cohension
        - InternetBankingInterface: không

<br>
</details>
<details>
    <summary>Vũ Bá Lượng 20194109</summary>
<br>

- Nhiệm vụ bài tập:
    - Chỉ ra các loại cohesion cho các phương thức trong controller **HomeController.java**, **ViewCartController.java** và  **views.screen.home**, **views.screen.cart** 

- Công việc chi tiết:
    - Pull Request(s): [https://github.com/lyma12/TKXDPM.KHMT.20231-14/pull/8]()
    - Mô tả công việc cụ thể:
        - **HomeController.java** 
            - Cả 3 phương thức **getAllMedia()**, **getAllTypeMedia()**, **getListMediaByType(String type)** đều sử dụng **functional cohesion** 
        - **ViewCartController**
            - Phương thức **checkAvailabilityOfProduct()**, **getCartSubtotal()** đều sử dụng **functional cohesion**
        - **views.screen.home**
            - **Group_Media.java**
                - **Group_Media()** sử dụng **procedural cohesion**
                - **setGroupInfor()** chủ yếu sử dụng **functional cohesion**
            - **HomeScreenHandler.java** và **MediaHandler.java** đều sử dụng **functional cohesion**
        - **views.screen.cart**
            - **CartScreenHandler.java**
                - **CartScreenHandler()** sử dụng **Logical Cohesion**
                - **requestToPlaceOrder()** sử dụng **Procedural Cohesion**
                - **getBController()** sử dụng **Functional Cohesion**
                - **requestToViewCart()** sử dụng **Procedural Cohesion**
                - **displayCart()** sử dụng **Procedural Cohesion**
                - **updateCart()** sử dụng **Functional Cohesion**
                - **updateCartAmount()** sử dụng **Procedural Cohesion**
            - **MediaHandler.java**
                - **MediaHandler()** sử dụng **Functional Cohesion**
                - **setMedia()** sử dụng **Functional Cohesion**
                - **setMediaInfor()** sử dụng **Procedural Cohesion**
                - **initializeSpinner()** sử dụng **Procedural Cohesion**
</details>
</details>

<details>
  <summary>W13: 11/12/2023~17/12/2023 </summary>
<br>
<details>
    <summary>Nguyễn Đức Long 20194100</summary>

    - Nhiệm vụ bài tập: 
- Chỉ ra các vi phạm liên quan tới quy tắc thiết kế SRP và đề xuất chỉnh sửa

    - Công việc chi tiết:
- Pull Request(s): [https://github.com/lyma12/TKXDPM.KHMT.20231-14/pull/10]()
    - Mô tả công việc cụ thể:
        - **PlaceOrderController.java**
            - phương thức **validateDeliveryInfo** nên tách riêng ra 1 class khác (**validateDeliveryInfo.java**) cùng với các phương thức con của nó (**validatePhoneNumber, validateName, validateAddress**) được khai báo bên trong. Mục đính là để tránh việc kiểm tra thông tin vận chuyển thực thi trong class PlaceController, vì nếu có thay đổi trong logic kiểm tra thông tin thì class PlaceOrderController cũng sẽ phải thay đổi một cách không đáng có (vi phạm nguyên tắc thiết kế SRP)
            - phương thức **createOrder** nên đưa vào trong class **order.java** để tránh việc khai báo logic thực thi việc tạo order trong class PlaceOrderController sẽ gây ra vi phậm nguyên tắc thiết kế SRP (cách xử lý đề xuất sẽ tương tự như phương thức **createInvoice**)
            - phương thức **getCartSubtotal**: chỉ được khai báo mà không hỗ trợ bất kì điều gì tới logic trong class PlaceOrderController. Vì vậy nên bỏ nó đi ở class này và khai báo trong class nào thực sự cần sử dụng tới phương thức này (dựa vào entity Cart để gọi phương thức xử lý tương ứng)
        - **RushOrderController.java**
            - phương thức **validateDeliveryInfo** nên tách riêng ra 1 class khác (**validateDeliveryInfo.java**) cùng với các phương thức con của nó (**validateDistrict, validateHour**) được khai báo bên trong. Mục đính là để tránh việc kiểm tra thông tin vận chuyển nhanh thực thi trong class RushOrderController, vì nếu có thay đổi trong logic kiểm tra thông tin thì class RushOrderController cũng sẽ phải thay đổi một cách không đáng có (vi phạm nguyên tắc thiết kế SRP)
<br>
</details>
<details>
<summary>Mã Thiên Lý</summary>
<br>

- Assigned tasks:
  - Kiểm tra code có vi phạm nguyên tắc L: liskov substitution principle trong SOLID không?
  - Nếu có hãy sửa 

- Implementation details:
  - Pull Request(s): https://github.com/lyma12/TKXDPM.KHMT.20231-14/pull/11
  - Specific implementation details:
    + Không tìm được vi phạm nguyên tắc LSP
    + Các đối tượng của class con như paymentcontroller, placeordercontroller, viewcartcontroller, rushordercontroller, homecontroller có thể thay thể đối tượng lớp cha basecontroller mà không làm mất đi tính đúng đúng của chương trình.
    + Tương tự đối với các class con của baseScreenHandler.

</details>
---

<details>
  <summary>W16: 01/01/2024~707/01/2024 </summary>
<br>
<details>
<summary>Mã Thiên Lý</summary>
<br>

- Assigned tasks:
  - Thực hiện báo cáo final report
  - code use case tìm kiếm, đăng nhập
  - Tạo data base
  - Các tài liệu: Activity diagram, Sequence diagram, Class diagram liên quan đến use case tìm kiếm và đăng nhập và  ER diagram, logical data diagram.
  - Quay video demo luồng chính.
- Implementation details:
  - Pull Request(s): https://github.com/lyma12/TKXDPM.KHMT.20231-14/pull/5
  - Specific implementation details:
    - CartMedia: functional cohesion
    - Cart: functional cohesion
    - BaseController: functional cohesion
    - Invoice: functional cohesion
    - order: functional cohesion
    - orderMedia: functional cohesion
    - BaseScreenHandler: functional cohesion
<br>
</details>  
