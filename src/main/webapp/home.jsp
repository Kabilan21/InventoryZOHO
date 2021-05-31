<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">

    <title>Inventory</title>
</head>

<body class="container" style="padding-top:10px;">
    <h2>
        Welcome <%=request.getAttribute("staff")%>
    </h2>

    <nav class="navbar navbar-light bg-light" style="border-radius: 10px;">
        <button class="btn btn-primary" onclick="showAllProduct()">View Product</button>
        <button class="btn btn-primary" onclick="setAddView()">Add Product</button>
        <button class="btn btn-primary" onclick="getDispatched()">Dispatched Timeline</button>
        <button class="btn btn-primary" onclick="getReceived()">Received Timeline</button>

        <div style=" padding: 5px;margin-top: 10px;" class="form-inline">
            <input class="form-control" id="searchText" type="search" placeholder="Search Products">
            <button class="btn btn-primary" onclick="searchProduct()" style="margin-left: 10px;">Search</button>
        </div>
    </nav>

    <div id="response" hidden></div>

    <div id="addition_section" hidden>
        <form onsubmit="return addProduct();">
            <h5 style="padding-top:10px;">Enter product details:</h5>
            <input class="form-control col-4" required="true" style="margin:5px;" id="product_name" type="text"
                placeholder="Name">
            <input class="form-control col-4" required="true" style="margin:5px;" id="product_price" type="number"
                placeholder="Price">
            <input class="form-control col-4" required="true" style="margin:5px;" id="product_quantity" type="number"
                placeholder="Quantity">
            <textarea class="form-control col-5" style="margin:5px;" id="product_description" type="text"
                placeholder="Description"></textarea>
            <button class="btn btn-primary" style="margin:5px;" type="submit">Add Product</button>
        </form>


    </div>

    <div id="update_section" hidden>
        <form onsubmit="return updateProduct();">
            <h5 style="padding-top:10px;">Update product details:</h5>
            <input class="form-control col-4" required="true" style="margin:5px;" id="uproduct_name" type="text"
                placeholder="Name">
            <input class="form-control col-4" required="true" style="margin:5px;" id="uproduct_price" type="number"
                placeholder="Price">
            <input class="form-control col-4" hidden style="margin:5px;" id="uproduct_quantity" type="number"
                placeholder="Quantity">
            <textarea class="form-control col-5" style="margin:5px;" id="uproduct_description" type="text"
                placeholder="Description"></textarea>
            <button class="btn btn-primary" style="margin:5px;" onclick="updateProduct()">Update Product</button>
            <button class="btn btn-primary" style="margin:5px;" onclick="removeProduct()">Remove Product</button>
        </form>
        <div class="form-inline">
            <input class="form-control col-4" style="margin:5px;" id="a_stock" type="number" placeholder="Stock">
            <button class="btn btn-primary" onclick="stockAdd()">Add Stocks</button>
        </div>
        <br>
        <div class="form-inline">
            <input class="form-control col-4" style="margin:5px;" id="d_stock" type="number" placeholder="Stock">
            <button class="btn btn-primary" onclick="stockRemove()">Dispatch Stocks</button>
        </div>
        <br>
        <br>


    </div>


</body>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script>

    var currentProduct = -1;
    var currentQuantity = 0;
    var additionSection = document.getElementById("addition_section");
    var updateSection = document.getElementById("update_section");
    var response = document.getElementById('response');
    var addProductView = document.getElementById('addProductView');
    var uname = document.getElementById("uproduct_name");
    var uprice = document.getElementById("uproduct_price");
    var uquantity = document.getElementById("uproduct_quantity");
    var udescription = document.getElementById("uproduct_description");


    function getDispatched() {
        additionSection.hidden = true;
        updateSection.hidden = true;
        $.ajax({
            url: "dispatch",
            method: "GET",
            success: function (data) {
                response.innerHTML = data;
                response.hidden = false;

            }
        });
    }

    function getReceived() {
        response.hidden = false;
        additionSection.hidden = true;
        updateSection.hidden = true;
        $.ajax({
            url: "receive",
            method: "GET",
            success: function (data) {
                response.innerHTML = data;
            }
        });
    }

    function setAddView() {
        response.hidden = true;
        additionSection.hidden = false;
        updateSection.hidden = true;

    }

    function setCurrentProduct(id, name, price, quantity, description) {

        response.hidden = true;
        additionSection.hidden = true;
        updateSection.hidden = false;

        currentQuantity = quantity;
        currentProduct = id;
        uname.value = name;
        uprice.value = price;
        uquantity.value = quantity;
        udescription.value = description;
        console.log(currentProduct, uname.value, uprice.value, uquantity.value, udescription.value);
    }

    function showAllProduct() {
        response.hidden = false;
        additionSection.hidden = true;
        updateSection.hidden = true;
        console.log("hello");
        $.ajax({
            url: "product",
            method: "GET",
            success: function (data) {
                response.innerHTML = data;
            }
        });
    }

    function searchProduct() {
        response.hidden = false;
        additionSection.hidden = true;
        updateSection.hidden = true;
        var searchText = document.getElementById("searchText");
        console.log(searchText.value);
        $.ajax({
            url: "searchProduct",
            method: "POST",
            data: { "searchText": searchText.value },
            success: function (data) {
                response.innerHTML = data;
            }
        });

    }

    function addProduct() {
        var name = document.getElementById("product_name");
        var price = document.getElementById("product_price");
        var quantity = document.getElementById("product_quantity");
        var description = document.getElementById("product_description");
        $.ajax({
            url: "product",
            method: "POST",
            data: { "name": name.value, "price": price.value, "quantity": quantity.value, "description": description.value },
            success: function (data) {
                response.innerHTML = data;
                response.hidden = false;
            }
        });
        return false;
    }


    function stockAdd() {
        var stock = document.getElementById("a_stock");
        $.ajax({
            url: "product/update/stock",
            method: "POST",
            data: { "id": currentProduct, "status": 1, "quantity": stock.value },
            success: function (data) {
                response.innerHTML = data;
                response.hidden = false;
            }
        });
    }

    function stockRemove() {
        var stock = document.getElementById("d_stock");
        $.ajax({
            url: "product/update/stock",
            method: "POST",
            data: { "id": currentProduct, "status": 0, "quantity": stock.value },
            success: function (data) {
                response.innerHTML = data;
                response.hidden = false;
            }
        });
    }

    function updateProduct() {
        $.ajax({
            url: "product/update",
            method: "POST",
            data: { "name": uname.value, "id": currentProduct, "price": uprice.value, "description": udescription.value, "id": currentProduct, "delete": 0 },
            success: function (data) {

                response.innerHTML = data;
                response.hidden = false;
            }
        });
        return false;
    }

    function removeProduct() {
        $.ajax({
            url: "product/update",
            method: "POST",
            data: { "id": currentProduct, "delete": 1 },
            success: function (data) {
                response.innerHTML = data;
                response.hidden = false;
            }
        });
        return false;
    }



    $(document).ready(function () {
        showAllProduct();
    });

</script>

</html>