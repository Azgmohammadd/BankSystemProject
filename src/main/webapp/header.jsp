<%@ page import="java.util.Date" %>
<header>
    <h1>Welcome to Your Website</h1>
    <!-- Include any navigation menu, logo, or other header content here -->
    <%-- You can include Java code or variables here as well --%>
    <% Date currentDate = new Date(); %>
    <p>Current Date: <%= currentDate %></p>
</header>
