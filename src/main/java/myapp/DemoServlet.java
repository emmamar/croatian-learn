package myapp;

import java.io.IOException;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
import com.google.appengine.api.utils.SystemProperty;

public class DemoServlet extends HttpServlet {
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        
        String url = null;
        try {
	    if (SystemProperty.environment.value() ==
	        SystemProperty.Environment.Value.Production) {
		    Class.forName("com.mysql.jdbc.GoogleDriver");
		    url = "jdbc:google:mysql://croatian-learn:dictionary/words?user=root";
	    } else {
		Class.forName("com.mysql.jdbc.Driver");
		url = "jdbc:mysql://127.0.0.1:3306/words?user=root";

		// Alternatively, connect to a Google Cloud SQL instance using:
		// jdbc:mysql://ip-address-of-google-cloud-sql-instance:3306/words?user=root
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	    return;
        }

        PrintWriter out = resp.getWriter();
	try {
	    Connection conn = DriverManager.getConnection(url);
            try {
                String english = req.getParameter("english");
		String nominative = req.getParameter("nominative");
                String dative = req.getParameter("dative");
                String accu = req.getParameter("accu");
                String geni = req.getParameter("geni");
                String voca = req.getParameter("voca");
                String loca = req.getParameter("loca");
                String instr = req.getParameter("instr");
                if (english == "" || nominative == "" || dative == "" || accu == "" || geni == "" || voca == "" || loca == "" || instr == "") {
		    out.println(
		        "<html><head></head><body>You are missing either a message or a name! Try again! " +
		        "Redirecting in 3 seconds...</body></html>");
		} else {
		    String statement = "INSERT INTO nouns (English, Nominative, Dative, Accusative, Genitive, Vocative, Locative, Instrumental) VALUES( ? , ? , ? , ? , ? , ? , ? , ? )";
		    PreparedStatement stmt = conn.prepareStatement(statement);
		    stmt.setString(1, english);
		    stmt.setString(2, nominative);
                    stmt.setString(3, dative);
                    stmt.setString(4, accu);
                    stmt.setString(5, geni);
                    stmt.setString(6, voca);
                    stmt.setString(7, loca);
                    stmt.setString(8, instr);
		    int success = 2;
		    success = stmt.executeUpdate();
		    if (success == 1) {
		        out.println(
			    "<html><head></head><body>Success! Redirecting in 3 seconds...</body></html>");
		    } else if (success == 0) {
		        out.println(
			    "<html><head></head><body>Failure! Please try again! " +
			    "Redirecting in 3 seconds...</body></html>");
		    }
		}
	    } finally {
	  	conn.close();
	    }
        } catch (SQLException e) {
            e.printStackTrace();
        }
	    resp.setHeader("Refresh", "3; url=/index.jsp");
    }    
}

