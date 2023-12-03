# TKXDPM.KHMT.20231-14

## Thành viên nhóm


| Name             | Role        |
| :--------------- | :---------- |
| Mã Thiên Lý      | Team Leader |
| Nguyễn Đức Long  | Member      |
| Trần Văn Long    | Member      |
| Vũ Bá Lượng      | Member      |

## Nội dung báo cáo

<details>
<<<<<<< HEAD
  <summary>Week 1: 27/11/2023 - 02/12/2023 </summary>
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
>>>>>>> main
