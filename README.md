# TKXDPM.KHMT.20231-14

## Thành viên nhóm


| Họ và tên      | Vai trò     |
| :------------- | :---------- |
| Lê Thanh Giang | Team Leader |
| Nguyễn Văn A   | Member      |
| Nguyễn Văn B   | Member      |
| Vũ Bá Lượng    | Member      |

## Nội dung báo cáo

<details>
    <summary>W11: 26/11/2023~02/12/2023 </summary>
<br>
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
