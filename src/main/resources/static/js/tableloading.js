refreshClients()
refreshProducts()

function refreshClients() {
    $('#clients-table').empty();
    let container = $('#clients-container')
    container.addClass("loading");
    $.get("/rest/clients", function (clients) {
        if (clients) {
            for (let i = 0; i < clients.length; i++) {
                $('#clients-table').append(`<tr onclick='updateData(${clients[i].id})'><td>${clients[i].id} </td><td>${clients[i].firstName}</td><td>${clients[i].lastName} </td><td>${clients[i].balance} </td><td><button class="btn btn-sm btn-outline-danger" type="button" onClick="removeClient(${clients[i].id})">Remove</button></td></tr>`);
            }

        }
    });
    container.removeClass("loading");
}

function refreshProducts() {
    $('#products-table').empty();
    let container = $('#products-container')
    container.addClass("loading");
    $.get("/rest/products", function (products) {
        if (products) {
            for (let i = 0; i < products.length; i++) {
                $('#products-table').append(`<tr onclick='updateData(${products[i].id})'><td>${products[i].id} </td><td>${products[i].name} </td><td>${products[i].price} </td><td><button class="btn btn-sm btn-outline-danger" type="button" onClick="removeProduct(${products[i].id})">Remove</button></td></tr>`);
            }
        }
    });
    container.removeClass("loading");
}

function addClient() {
    $(".is-invalid").removeClass("is-invalid");
    let form = $("#client-form")
    form.validate({
        errorElement: "small",
        wrapper: "div",
        errorPlacement: function (error, element) {
            offset = element.offset();
            element.addClass("is-invalid");
            error.insertAfter(element);
            error.addClass('red');
        }, rules: {
            balance: {step: false}
        }
    });
    if (form.valid()) {
        $.post("/rest/clients", form.serialize(), function () {
            refreshClients()
        }).fail(function (params) {
            let message = "Adding client was not successfull due to following problems:\n\n";
            params = JSON.parse(params.responseText);
            for (let [key, msg] of Object.entries(params)) {
                $('#' + key).addClass("is-invalid")
                message = message + key + ": " + msg + "\n"
            }
            alert(message)
        });
    }

}

function addProduct() {
    $(".is-invalid").removeClass("is-invalid");
    let form = $("#product-form")
    form.validate({
        errorElement: "small",
        wrapper: "div",
        errorPlacement: function (error, element) {
            offset = element.offset();
            element.addClass("is-invalid");
            error.insertAfter(element);
            error.addClass('red');
        },
        rules: {
            price: {step: false}
        }
    });
    if (form.valid()) {
        $.post("/rest/products", form.serialize(), function () {
            refreshProducts()
        }).fail(function (params) {
            let message = "Adding product was not successfull due to following problems:\n\n";
            params = JSON.parse(params.responseText);
            for (let [key, msg] of Object.entries(params)) {
                $('#' + key).addClass("is-invalid")
                message = message + key + ": " + msg + "\n"
            }
            alert(message)
        });
    }
}

function removeClient(id) {
    if (confirm("Are you sure you want to remove Client #" + id + "?")) {
        $.ajax({
            url: '/rest/clients/' + id,
            type: 'DELETE',
            success: function () {
                refreshClients()
            },
            error: function () {
                alert("Unable to remove client")
            }
        });
    }
}

function removeProduct(id) {
    if (confirm("Are you sure you want to remove Product #" + id + "?")) {
        $.ajax({
            url: '/rest/products/' + id,
            type: 'DELETE',
            success: function () {
                refreshProducts()
            },
            error: function () {
                alert("Unable to remove product")
            }
        });
    }
}

function displayAll() {
    refreshClients();
    refreshProducts();
    $(".red").remove();
    $(".is-invalid").removeClass("is-invalid");
    $(".error").removeClass("error");
}