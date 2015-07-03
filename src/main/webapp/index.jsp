<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="java.sql.*" %>
<%@ page import="com.google.appengine.api.utils.SystemProperty" %>

<html>
  <head>
    <title>Learn Croatian!</title>
    <script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
  </head>
  <body>

<%
String url = null;
if (SystemProperty.environment.value() ==
    SystemProperty.Environment.Value.Production) {
  Class.forName("com.mysql.jdbc.GoogleDriver");
  url =
    "jdbc:google:mysql://croatian-learn:dictionary/words?user=root";
} else {
  //Class.forName("com.mysql.jdbc.Driver");
  //url = "jdbc:mysql://173.194.226.42:3306?user=root";
}


Connection conn = DriverManager.getConnection(url);
ResultSet rs = conn.createStatement().executeQuery(
    "SELECT English, Nominative, Dative, Accusative, Genitive, Vocative, Locative, Instrumental FROM nouns");

%>
<table style="width:100%">
<%
while (rs.next()) {
    String english = rs.getString("English");
    String nominative = rs.getString("Nominative");
    String dative = rs.getString("Dative");
    String accu = rs.getString("Accusative");
    String geni = rs.getString("Genitive");
    String voca = rs.getString("Vocative");
    String loca = rs.getString("Locative");
    String instr = rs.getString("Instrumental");
 %>
<tr>
  <td><%= english %></td>
  <td><%= nominative %></td>
  <td><%= dative %></td>
  <td><%= accu %></td>
  <td><%= voca %></td>
  <td><%= loca %></td>
  <td><%= instr %></td>
  
</tr>
<%
}
conn.close();
%>
</table>
    <div>
        <p> Welcome to my page dedicated to helping you learn croatian!</p>


<p><strong>Add a word!</strong></p>
<form action="/demo" method="post">
    <div>English: <input type="text" name="english"></input></div>
    <div>Nominative: <input type="text" name="nominative"></input></div>
    <div>Dative: <input type="text" name="dative"></input></div>
    <div>Accusative: <input type="text" name="accu"></input></div>
    <div>Genitive: <input type="text" name="geni"></input></div>
    <div>Vocative: <input type="text" name="voca"></input></div>
    <div>Locative: <input type="text" name="loca"></input></div>
    <div>Instrumental: <input type="text" name="instr"></input></div>
    <div><input type="submit" value="Add word" /></div>
</form>
  </body>
</html>
