<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Store Details</title>
    <link rel="stylesheet" href="${resource(dir: 'stylesheets', file: 'bootstrap.min.css')}"/>
    <script src="${resource(dir: 'javascript', file: 'jquery-3.5.1.min.js')}"></script>
    <script src="${resource(dir: 'javascript', file: 'bootstrap.min.js')}"></script>
    <script>
        $(document).ready(function() {
            $('#addProductFromRepositoryModal').on('show.bs.modal', function (event) {
                var button = $(event.relatedTarget);
                var storeId = button.data('id');
                var modal = $(this);
                modal.find('#storeId').val(storeId);

                var repositorySelect = modal.find('#repositoryId');
                var productSelect = modal.find('#productId');
                productSelect.empty().prop('disabled', true);

                repositorySelect.off('change').on('change', function() {
                    var repositoryId = $(this).val();
                    productSelect.empty();
                    if (repositoryId) {
                        $.ajax({
                            url: '${createLink(controller: "repository", action: "getProductsForRepository")}/' + repositoryId,
                            type: 'GET',
                            success: function(data) {
                                productSelect.prop('disabled', false);
                                $.each(data, function(index, product) {
                                    productSelect.append('<option value="' + product.id + '">' + product.name + '</option>');
                                });
                            },
                            error: function() {
                                productSelect.prop('disabled', true);
                            }
                        });
                    } else {
                        productSelect.prop('disabled', true);
                    }
                });

                repositorySelect.trigger('change');
            });

            $('#sellProductModal').on('show.bs.modal', function (event) {
                var button = $(event.relatedTarget);
                var storeId = button.data('id');
                var productId = button.data('product-id');
                var modal = $(this);
                modal.find('#storeId').val(storeId);
                modal.find('#productId').val(productId);
            });

            $('#returnProductModal').on('show.bs.modal', function (event) {
                var button = $(event.relatedTarget);
                var storeId = button.data('id');
                var productId = button.data('product-id');
                var modal = $(this);
                modal.find('#storeId').val(storeId);
                modal.find('#productId').val(productId);
            });
        });
    </script>
</head>
<body>
<div class="container">
    <h1>Store: ${store.name}</h1>
    <h2>Address: ${store.address}</h2>
    <h2>Products</h2>
    <table class="table table-bordered table-striped">
        <thead>
        <tr>
            <th>Product ID</th>
            <th>Product Name</th>
            <th>Quantity</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <g:each in="${store.products}" var="storeProduct">
            <tr>
                <td>${storeProduct.product.id}</td>
                <td>${storeProduct.product.name}</td>
                <td>${storeProduct.quantity}</td>
                <td>
                    <g:form action="returnProduct" method="post" style="display:inline;">
                        <g:hiddenField name="storeId" value="${store.id}"/>
                        <g:hiddenField name="productId" value="${storeProduct.product.id}"/>
                        <g:select name="repositoryId" from="${repositoryList}" optionKey="id" optionValue="name" class="form-control"/>
                        <g:actionSubmit class="btn btn-danger btn-sm" value="Return" action="returnProduct" onclick="return confirm('Are you sure you want to return this product?');"/>
                    </g:form>
                    <button class="btn btn-warning btn-sm" data-toggle="modal" data-target="#sellProductModal" data-id="${store.id}" data-product-id="${storeProduct.product.id}">Sell</button>
                </td>
            </tr>
        </g:each>
        </tbody>
    </table>
    <button class="btn btn-success" data-toggle="modal" data-target="#addProductFromRepositoryModal" data-id="${store.id}">Add Product from Repository</button>
    <g:link class="btn btn-secondary" action="index">Back to List</g:link>
</div>

<!-- Add Product from Repository -->
<div class="modal fade" id="addProductFromRepositoryModal" tabindex="-1" role="dialog" aria-labelledby="addProductFromRepositoryModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" id="addProductFromRepositoryModalLabel">Add Product from Repository</h4>
            </div>
            <div class="modal-body">
                <g:form action="addProductFromRepository" method="post">
                    <div class="form-group">
                        <label for="repositoryId">Repository</label>
                        <g:select id="repositoryId" name="repositoryId" from="${repositoryList}" optionKey="id" optionValue="name" class="form-control"/>
                    </div>
                    <div class="form-group">
                        <label for="productId">Product</label>
                        <g:select id="productId" name="productId" class="form-control" disabled="disabled" from=""/>
                    </div>
                    <div class="form-group">
                        <label for="quantity">Quantity</label>
                        <g:textField name="quantity" class="form-control"/>
                    </div>
                    <g:hiddenField name="storeId" id="storeId" value="${store.id}"/>
                    <g:actionSubmit class="btn btn-primary" action="addProductFromRepository" value="Add"/>
                </g:form>
            </div>
        </div>
    </div>
</div>

<!-- Sell Product  -->
<div class="modal fade" id="sellProductModal" tabindex="-1" role="dialog" aria-labelledby="sellProductModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" id="sellProductModalLabel">Sell Product</h4>
            </div>
            <div class="modal-body">
                <g:form action="sellProduct" method="post">
                    <div class="form-group">
                        <label for="quantity">Quantity</label>
                        <g:textField name="quantity" class="form-control"/>
                    </div>
                    <g:hiddenField name="storeId" id="storeId" value="${store.id}"/>
                    <g:hiddenField name="productId" id="productId" value=""/>
                    <g:actionSubmit class="btn btn-primary" action="sellProduct" value="Sell"/>
                </g:form>
            </div>
        </div>
    </div>
</div>

<!-- Return Product    -->
<div class="modal fade" id="returnProductModal" tabindex="-1" role="dialog" aria-labelledby="returnProductModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" id="returnProductModalLabel">Return Product to Repository</h4>
            </div>
            <div class="modal-body">
                <g:form action="returnProduct" method="post">
                    <div class="form-group">
                        <label for="repositoryId">Repository</label>
                        <g:select id="repositoryId" name="repositoryId" from="${repositoryList}" optionKey="id" optionValue="name" class="form-control"/>
                    </div>
                    <div class="form-group">
                        <label for="quantity">Quantity</label>
                        <g:textField name="quantity" class="form-control"/>
                    </div>
                    <g:hiddenField name="storeId" id="storeId" value="${store.id}"/>
                    <g:hiddenField name="productId" id="productId" value=""/>
                    <g:actionSubmit class="btn btn-primary" action="returnProduct" value="Return"/>
                </g:form>
            </div>
        </div>
    </div>
</div>
</body>
</html>
