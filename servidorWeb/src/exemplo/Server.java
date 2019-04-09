package exemplo;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.caelum.agenda.dao.ContatoDao;
import br.com.caelum.agenda.modelo.Contato;

@WebServlet("/servlet1")
public class Server extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	@Override
	public void service(HttpServletRequest request, HttpServletResponse response) 
		throws IOException, ServletException{
			
			PrintWriter out = response.getWriter();
		
			String name = request.getParameter("name");
	        String password = request.getParameter("password");
	        String email = request.getParameter("email");
	        String dateText = request.getParameter("bday");
	        Calendar birthday = null;
			
	        if (name != null) {
	        	
		        try {
		        	Date date = new SimpleDateFormat("yyyy-MM-dd")
                            .parse(dateText);
		        	birthday = Calendar.getInstance();
		        	birthday.setTime(date);

	            } catch (ParseException e) {
	                out.println("Erro de conversão da data");
	                return;
	            }
	        	
	        	try {
	        		Contato contato = new Contato();
		        	
		        	contato.setNome(name);
		        	contato.setPassword(password);
		        	contato.setEmail(email);
		        	contato.setBirthday(birthday);
		        	
		        	ContatoDao dao = new ContatoDao();
		        	dao.adiciona(contato);
		        	
	        	} catch (Error e) {
	        		out.println("Na criação do contato");
	                return;
	        	}
	        	
	        	
	        	out.println("<html>");
	        	out.println("<body>");
	        	out.println("<h1>Welcome: " + name + "</h1>");
	        	out.println("</body>");
	        	out.println("</html>");
	        } else {
	        	response.setContentType("text/html");
		        RequestDispatcher rd = null;
		        rd = request.getRequestDispatcher("/index.html");
	            rd.include(request, response);
	            
	            out.close();
	        }
		
	}
}
