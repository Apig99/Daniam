<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Repository Details</title>
    <link rel="stylesheet" href="${resource(dir: 'stylesheets', file: 'bootstrap.min.css')}"/>
    <script src="${resource(dir: 'javascript', file: 'jquery-3.5.1.min.js')}"></script>
    <script src="${resource(dir: 'javascript', file: 'bootstrap.min.js')}"></script>
    <script>
        $(document).ready(function() {
            $('#addProductModal').on('show.bs.modal', function (event) {
                var button = $(event.relatedTarget);
                var repositoryId = button.data('id');
                var modal = $(this);
                modal.find('#repositoryId').val(repositoryId);
            });
            $('#moveProductModal').on('show.bs.modal', function (event) {
                var button = $(event.relatedTarget);
                var repositoryId = button.data('id');
                var productId = button.data('product-id');
                var modal = $(this);
                modal.find('#sourceRepositoryId').val(repositoryId);
                modal.find('#productId').val(productId);
            });
        });
    </script>
</head>
<body>
<div class="container">
    <h1>Repository: ${repository.name}</h1>
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
        <g:each in="${repository.products}" var="repositoryProduct">
            <tr>
                <td>${repositoryProduct.product.id}</td>
                <td>${repositoryProduct.product.name}</td>
                <td>${repositoryProduct.quantity}</td>
                <td>
                    <g:form action="removeProduct" method="post" style="display:inline;">
                        <g:hiddenField name="repositoryId" value="${repository.id}"/>
                        <g:hiddenField name="productId" value="${repositoryProduct.product.id}"/>
                        <g:actionSubmit class="btn btn-danger btn-sm" value="Remove" action="removeProduct" onclick="return confirm('Are you sure you want to remove this product?');"/>
                    </g:form>
                    <button class="btn btn-primary btn-sm" data-toggle="modal" data-target="#moveProductModal" data-id="${repository.id}" data-product-id="${repositoryProduct.product.id}">Move</button>
                </td>
            </tr>
        </g:each>
        </tbody>
    </table>
    <button class="btn btn-success" data-toggle="modal" data-target="#addProductModal" data-id="${repository.id}">Add Product</button>
    <g:link class="btn btn-secondary" action="index">Back to List</g:link>
</div>

<!--ADDDDDDD-->
<div class="modal fade" id="addProductModal" tabindex="-1" role="dialog" aria-labelledby="addProductModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" id="addProductModalLabel">Add Product to Repository</h4>
            </div>
            <div class="modal-body">
                <g:form action="addProduct">
                    <div class="form-group">
                        <label for="productId">Product</label>
                        <g:select name="productId" from="${productList}" optionKey="id" optionValue="name" class="form-control"/>
                    </div>
                    <div class="form-group">
                        <label for="quantity">Quantity</label>
                        <g:textField name="quantity" class="form-control"/>
                    </div>
                    <g:hiddenField name="repositoryId" id="repositoryId" value="${repository.id}"/>
                    <g:actionSubmit class="btn btn-primary" action="addProduct" value="Add"/>
                </g:form>
            </div>
        </div>
    </div>
</div>

<!--MOVVVEEEEEE-->

<div class="modal fade" id="moveProductModal" tabindex="-1" role="dialog" aria-labelledby="moveProductModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" id="moveProductModalLabel">Move Product to Another Repository</h4>
            </div>
            <div class="modal-body">
                <g:form action="moveProduct">
                    <div class="form-group">
                        <label for="targetRepositoryId">Target Repository</label>
                        <g:select name="targetRepositoryId" from="${repositoryList}" optionKey="id" optionValue="name" class="form-control"/>
                    </div>
                    <div class="form-group">
                        <label for="quantity">Quantity</label>
                        <g:textField name="quantity" class="form-control"/>
                    </div>
                    <g:hiddenField name="sourceRepositoryId" id="sourceRepositoryId" value=""/>
                    <g:hiddenField name="productId" id="productId" value=""/>
                    <g:actionSubmit class="btn btn-primary" action="moveProduct" value="Move"/>
                </g:form>
            </div>
        </div>
    </div>
</div>

</body>
</html>
