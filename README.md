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
</details>
<details>
    <summary>Nguyễn Đức Long 20194100</summary>

    - Nhiệm vụ bài tập: 
- Chỉ ra các loại cohesion cho các phương thức trong controller **PlaceOrderController.java**, **RushOrderController.java**

    - Công việc chi tiết:
- Pull Request(s): [https://github.com/lyma12/TKXDPM.KHMT.20231-14/pull/6]()
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
        - **views.screen.shippingInfo**: đều là fucntional cohesion
<br>
</details>
<details>
    <summary>Trần Văn Long 20200372</summary>
<br>
</details>
<details>
    <summary>Vũ Bá Lượng 20194109</summary>
<br>
</details>
