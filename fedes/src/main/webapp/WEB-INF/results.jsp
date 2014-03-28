<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Fedes Results</title>
</head>
<body>
          Here are the wonderful results:
          <%
              out.print(request.getAttribute("results").toString());
          %>

</body>
</html>
