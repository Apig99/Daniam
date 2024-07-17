<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Create Repository</title>
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'bootstrap.min.css')}"/>
</head>
<body>
<div class="container">
    <h1>Create Repository</h1>
    <g:form action="save">
        <div class="form-group">
            <label for="name">Name</label>
            <g:textField name="name" class="form-control" value="${repository?.name}"/>
            <g:if test="${repository?.errors?.hasFieldErrors('name')}">
                <div class="alert alert-danger">
                    <g:eachError bean="${repository}" field="name">
                        <div><g:message error="${it}"/></div>
                    </g:eachError>
                </div>
            </g:if>
            <g:if test="${error}">
                <div class="alert alert-danger" role="alert">
                    ${error}
                </div>
            </g:if>
        </div>
        <g:actionSubmit class="btn btn-primary" action="save" value="Create"/>
    </g:form>
    <g:link class="btn btn-secondary" action="index">Back to List</g:link>

</div>
</body>
</html>
