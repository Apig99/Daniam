<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Show Product</title>
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'bootstrap.min.css')}"/>
</head>
<body>
<div class="container">
    <h1>Product Details</h1>
    <table class="table table-bordered">
        <tr>
            <th>ID</th>
            <td>${product.id}</td>
        </tr>
        <tr>
            <th>Name</th>
            <td>${product.name}</td>
        </tr>
        <tr>
            <th>Price</th>
            <td>${product.price}</td>
        </tr>
        <tr>
            <th>Production Date</th>
            <td><g:formatDate date="${product.productionDate}" format="yyyy-MM-dd"/></td>
        </tr>
        <tr>
            <th>Expiration Date</th>
            <td><g:formatDate date="${product.expirationDate}" format="yyyy-MM-dd"/></td>
        </tr>
    </table>
    <g:link class="btn btn-primary" action="edit" id="${product.id}">Edit</g:link>
    <g:link class="btn btn-danger delete-button" action="delete" id="${product.id}">Delete</g:link>
    <g:link class="btn btn-secondary" action="index">Back to List</g:link>
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
