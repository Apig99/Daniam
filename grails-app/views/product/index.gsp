<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Product List</title>
    <link rel="stylesheet" href="${resource(dir: 'stylesheets', file: 'bootstrap.min.css')}"/>
</head>
<body>
<div class="container">
    <h1>Product List</h1>
    <table class="table table-bordered table-striped">
        <thead>
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Price</th>
            <th>Production Date</th>
            <th>Expiration Date</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <g:each in="${productList}" var="product">
            <tr>
                <td>${product.id}</td>
                <td>${product.name}</td>
                <td>${product.price}</td>
                <td><g:formatDate date="${product.productionDate}" format="yyyy-MM-dd"/></td>
                <td><g:formatDate date="${product.expirationDate}" format="yyyy-MM-dd"/></td>
                <td>
                    <g:link class="btn btn-primary" action="edit" id="${product.id}">Edit</g:link>
                    <g:link class="btn btn-danger delete-button" action="delete" id="${product.id}">Delete</g:link>
                </td>
            </tr>
        </g:each>
        </tbody>
    </table>
    <g:link class="btn btn-success" action="create">Create New Product</g:link>
    <g:link class="btn btn-primary" controller="repository" action="index">Go to Repositories</g:link>
    <g:link class="btn btn-danger" controller="store" action="index">Go to Stores</g:link>

</div>
<script>
    $(document).ready(function() {
        $('.delete-button').on('click', function(e) {
            e.preventDefault();
            var link = $(this).attr('href');
            if (confirm('Are you sure you want to delete this product?')) {
                window.location.href = link;
            }
        });
    });
</script>
</body>
</html>
