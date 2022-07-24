refreshClients()
refreshProducts()

//highlighting selected table row
$('table tbody').on('mousedown', 'tr', function () {
    $(".highlight").removeClass('highlight')
    $(this).addClass('highlight')
})

function refreshClients() {
    $('#clients-table').empty();
    let container = $('#clients-container')
    container.addClass("loading");
    let select = $('#client-select')
    select.empty()
    $.get("/rest/clients", function (clients) {
        if (clients) {
            for (const client of clients) {
                $('#clients-table').append(`<tr onclick='displayClientProducts(${client.id})'><td>${client.id} </td><td>${client.firstName}</td><td>${client.lastName} </td><td>${client.balance} </td><td><button class="btn btn-sm btn-outline-danger" type="button" onClick="removeClient(${client.id})">Remove</button></td></tr>`);
                select.append(`<option value="${client.id}">#${client.id} - ${client.firstName} ${client.lastName} [${client.balance}]</option>`);
            }
        }
    }).fail(function () {
        select.append(`<option selected>Unable to load clients data.</option>`);
        alert("Unable to load clients data. Please check your internet connection and make sure that server is online.")
    });
    container.removeClass("loading");
}

function highlightRow(clientId, productId) {
    $('.highlight').removeClass('highlight');
    if (clientId) {
        $("#clients-table tr").filter(function () {
            return $(this).children(':first').text() == clientId;
        }).closest("tr").addClass('highlight')
    }
    if (productId) {
        $("#products-table tr").filter(function () {
            return $(this).children(':first').text() == productId;
        }).closest("tr").addClass('highlight')
    }

}

function displayClientProducts(id, productId) {
    $('#products-table').empty();
    let container = $('#products-container')
    container.addClass("loading");

    $.get("/rest/purchases/client/" + id, function (products) {
        if (products) {
            for (let i = 0; i < products.length; i++) {
                $('#products-table').append(`<tr onclick='displayProductCustomers(${products[i].id})'><td>${products[i].id} </td><td>${products[i].name} </td><td>${products[i].price} </td><td><button class="btn btn-sm btn-outline-warning" type="button" onClick="refundClient(${id},${products[i].id})">Refund</button></td></tr>`);
            }
        }
    }).fail(function () {
        alert("Unable to load clients data. Please check your internet connection and make sure that server is online.")
    }).always(function () {
        highlightRow(id, productId)
    });
    container.removeClass("loading");
}

function refreshProducts() {
    let table = $('#products-table')
    table.empty();
    let container = $('#products-container')
    container.addClass("loading");
    let select = $('#product-select')
    select.empty()
    $.get("/rest/products", function (products) {
        if (products) {
            for (const product of products) {
                table.append(`<tr onclick='displayProductCustomers(${product.id})'><td>${product.id} </td><td>${product.name} </td><td>${product.price} </td><td><button class="btn btn-sm btn-outline-danger" type="button" onClick="removeProduct(${product.id})">Remove</button></td></tr>`);
                select.append(`<option value="${product.id}">#${product.id} - ${product.name} [${product.price}]</option>`);
            }
        }
    }).fail(function () {
        select.append(`<option selected>Unable to load products data.</option>`);
        alert("Unable to load products data. Please check your internet connection and make sure that server is online.")
    });
    container.removeClass("loading");
}

function displayProductCustomers(id) {
    $('#clients-table').empty();
    let container = $('#clients-container')
    container.addClass("loading");
    $.get("/rest/purchases/product/" + id, function (clients) {
        if (clients) {
            for (let i = 0; i < clients.length; i++) {
                $('#clients-table').append(`<tr onclick='displayClientProducts(${clients[i].id})'><td>${clients[i].id}</td><td>${clients[i].firstName}</td><td>${clients[i].lastName}</td><td>${clients[i].balance}</td><td><button class="btn btn-sm btn-outline-warning" type="button" onClick="refundClient(${clients[i].id},${id})">Refund</button></td></tr>`);
            }
        }
    }).fail(function () {
        alert("Unable to load products data. Please check your internet connection and make sure that server is online.")
    });
    container.removeClass("loading");
}

function addClient() {
    $(".is-invalid").removeClass("is-invalid");
    let form = $("#client-form")
    form.validate({
        errorElement: "small", wrapper: "div", errorPlacement: function (error, element) {
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
        errorElement: "small", wrapper: "div", errorPlacement: function (error, element) {
            offset = element.offset();
            element.addClass("is-invalid");
            error.insertAfter(element);
            error.addClass('red');
        }, rules: {
            price: {step: false}
        }
    });
    if (form.valid()) {
        $.post("/rest/products", form.serialize(), function () {
            refreshProducts()
            refreshProductsList()
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
            url: '/rest/clients/' + id, type: 'DELETE', success: function () {
                refreshClients()
            }, error: function (request, status, error) {
                alert("Unable to remove client!\n" + request.responseText)
            }
        });
    }
}

function removeProduct(id) {
    if (confirm("Are you sure you want to remove Product #" + id + "?")) {
        $.ajax({
            url: '/rest/products/' + id, type: 'DELETE', success: function () {
                refreshProducts()
            }, error: function (request, status, error) {
                alert("Unable to remove product!\n" + request.responseText)
            }
        });
    }
}

function makePurchase() {
    let clientId = $("#client-select option:selected").val()
    let productId = $("#product-select option:selected").val()
    $.post("/rest/purchases", {clientId: clientId, productId: productId}, function () {
        refreshClients()
        alert("Purchase successful!")
        displayClientProducts(clientId, productId)
    }).fail(function (params) {
        let message = "Purchase was not successfull due to following problems:\n\n" + params.responseText;
        alert(message)
    }).always(function () {
        highlightRow(clientId, productId)
    });
}

function refundClient(clientId, productId) {
    if (confirm("Are you sure you want to refund customer #" + clientId + " for product #" + productId + "?")) {
        $.ajax({
            url: '/rest/purchases/',
            type: 'DELETE',
            data: {clientId: clientId, productId: productId},
            success: function () {
                displayClientProducts(clientId)
                refreshClients()
            },
            error: function (request, status, error) {
                alert("Unable to refund client!\n" + request.responseText)
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

function fixTableLength() {
    let clientContainer = $('#clients-container')
    let productContainer = $('#products-container')
    if (clientContainer.hasClass('fix_height') && productContainer.hasClass('fix_height')) {
        $('.fix_height').removeClass('fix_height');
        $('#fixing_button').text('Fix tables height')
    } else {
        clientContainer.addClass('fix_height')
        productContainer.addClass('fix_height')
        $('#fixing_button').text('Unfix tables height')
    }

}