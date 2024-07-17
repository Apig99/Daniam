// This is a manifest file that'll be compiled into application.js.
//
// Any JavaScript file within this directory can be referenced here using a relative path.
//
// You're free to add application-wide JavaScript to this file, but it's generally better
// to create separate JavaScript files as needed.
//
//= require jquery-3.5.1.min
//= require popper.min
//= require bootstrap
//= require_self
$(document).ready(function() {
    function loadProducts() {
        $.getJSON("/product/index", function(data) {
            var rows = "";
            data.each(function(product) {
                rows += "<tr>" +
                    "<td>" + product.id + "</td>" +
                    "<td>" + product.name + "</td>" +
                    "<td>" + product.price + "</td>" +
                    "<td>" + product.productionDate + "</td>" +
                    "<td>" + (product.deadline || "") + "</td>" +
                    "<td><button class='editProduct' data-id='" + product.id + "'>Edit</button> " +
                    "<button class='deleteProduct' data-id='" + product.id + "'>Delete</button></td>" +
                    "</tr>";
            });
            $("#productTable tbody").html(rows);
        });
    }
    loadProducts();

    $("#createProduct").click(function() {
        $("#productForm")[0].reset();
        $("#productId").val("");
        $("#productModal").show();
    });

    $("#cancelProduct").click(function() {
        $("#productModal").hide();
    });

    $("#productForm").submit(function(event) {
        event.preventDefault();
        var productData = $(this).serialize();
        $.post("/product/save", productData, function() {
            loadProducts();
            $("#productModal").hide();
        });
    });

    $(document).on("click", ".editProduct", function() {
        var productId = $(this).data("id");
        $.getJSON("/product/show/" + productId, function(product) {
            $("#productId").val(product.id);
            $("#productName").val(product.name);
            $("#productPrice").val(product.price);
            $("#productProductionDate").val(product.productionDate);
            $("#productDeadline").val(product.deadline);
            $("#productModal").show();
        });
    });

    $(document).on("click", ".deleteProduct", function() {
        if (confirm("Are you sure you want to delete this product?")) {
            var productId = $(this).data("id");
            $.post("/product/delete/" + productId, function() {
                loadProducts();
            });
        }
    });
});

$(document).ready(function() {
    function loadRepositories() {
        $.getJSON("/repository/index", function(data) {
            var rows = "";
            data.each(function(repository) {
                rows += "<tr>" +
                    "<td>" + repository.id + "</td>" +
                    "<td>" + repository.name + "</td>" +
                    "<td><button class='editRepository' data-id='" + repository.id + "'>Edit</button> " +
                    "<button class='deleteRepository' data-id='" + repository.id + "'>Delete</button></td>" +
                    "</tr>";
            });
            $("#repositoryTable tbody").html(rows);
        });
    }

    loadRepositories();

    $("#createRepository").click(function() {
        $("#repositoryForm")[0].reset();
        $("#repositoryId").val("");
        $("#repositoryModal").show();
    });

    $("#cancelRepository").click(function() {
        $("#repositoryModal").hide();
    });

    $("#repositoryForm").submit(function(event) {
        event.preventDefault();
        var repositoryData = $(this).serialize();
        $.post("/repository/save", repositoryData, function() {
            loadRepositories();
            $("#repositoryModal").hide();
        });
    });

    $(document).on("click", ".editRepository", function() {
        var repositoryId = $(this).data("id");
        $.getJSON("/repository/show/" + repositoryId, function(repository) {
            $("#repositoryId").val(repository.id);
            $("#repositoryName").val(repository.name);
            $("#repositoryModal").show();
        });
    });

    $(document).on("click", ".deleteRepository", function() {
        if (confirm("Are you sure you want to delete this repository?")) {
            var repositoryId = $(this).data("id");
            $.post("/repository/delete/" + repositoryId, function() {
                loadRepositories();
            });
        }
    });
});

$(document).ready(function() {
    function loadProducts() {
        $.getJSON("/product/index", function(data) {
            var rows = "";
            data.forEach(function(product) {
                rows += "<tr>" +
                    "<td>" + product.id + "</td>" +
                    "<td>" + product.name + "</td>" +
                    "<td>" + product.price + "</td>" +
                    "<td>" + product.productionDate + "</td>" +
                    "<td>" + (product.deadline || "") + "</td>" +
                    "<td><button class='editProduct' data-id='" + product.id + "'>Edit</button> " +
                    "<button class='deleteProduct' data-id='" + product.id + "'>Delete</button></td>" +
                    "</tr>";
            });
            $("#productTable tbody").html(rows);
        });
    }

    function loadRepositories() {
        $.getJSON("/repository/index", function(data) {
            var rows = "";
            data.forEach(function(repository) {
                rows += "<tr>" +
                    "<td>" + repository.id + "</td>" +
                    "<td>" + repository.name + "</td>" +
                    "<td><button class='editRepository' data-id='" + repository.id + "'>Edit</button> " +
                    "<button class='deleteRepository' data-id='" + repository.id + "'>Delete</button></td>" +
                    "</tr>";
            });
            $("#repositoryTable tbody").html(rows);
        });
    }

    function loadStores() {
        $.getJSON("/store/index", function(data) {
            var rows = "";
            data.forEach(function(store) {
                rows += "<tr>" +
                    "<td>" + store.id + "</td>" +
                    "<td>" + store.name + "</td>" +
                    "<td>" + store.address + "</td>" +
                    "<td><button class='editStore' data-id='" + store.id + "'>Edit</button> " +
                    "<button class='deleteStore' data-id='" + store.id + "'>Delete</button></td>" +
                    "</tr>";
            });
            $("#storeTable tbody").html(rows);
        });
    }

    loadProducts();
    loadRepositories();
    loadStores();

    // Store functions
    $("#createStore").click(function() {
        $("#storeForm")[0].reset();
        $("#storeId").val("");
        $("#storeModal").show();
    });

    $("#cancelStore").click(function() {
        $("#storeModal").hide();
    });

    $("#storeForm").submit(function(event) {
        event.preventDefault();
        var storeData = $(this).serialize();
        $.post("/store/save", storeData, function() {
            loadStores();
            $("#storeModal").hide();
        });
    });

    $(document).on("click", ".editStore", function() {
        var storeId = $(this).data("id");
        $.getJSON("/store/show/" + storeId, function(store) {
            $("#storeId").val(store.id);
            $("#storeName").val(store.name);
            $("#storeAddress").val(store.address);
            $("#storeModal").show();
        });
    });

    $(document).on("click", ".deleteStore", function() {
        if (confirm("Are you sure you want to delete this store?")) {
            var storeId = $(this).data("id");
            $.post("/store/delete/" + storeId, function() {
                loadStores();
            });
        }
    });
});



