<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Error Page</title>

    <link rel="stylesheet" href="css/bootstrap.min.css" />
    <link rel="stylesheet" href="css/error.css" />
</head>
<body>
<div class="container">
    <h1>Oops! Something went wrong.</h1>
    <p>We're sorry, but an unexpected error occurred.</p>
    <p>Please try again later.</p>
    <p>If the problem persists, please contact support.</p>
    <div>
        <h2>Error Details:</h2>
        <p>${error}</p>
    </div>
</div>
</body>
</html>
