package bookComparator;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "ComparadorLibros", urlPatterns = { "/books" })

public class ComparadorLibros extends HttpServlet {

	public API_Amazon amazonApi = new API_Amazon();
	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

		Book book = new Book();
		String isbn = request.getParameter("isbn");

		try {
			book = amazonApi.consultas(isbn);

			book.setIsbn(isbn);
			WebScraping web = new WebScraping();
			web.searchPopularLibros(book);
			web.searchAgapea(book);

			request.setAttribute("book", book);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/book.jsp");
			dispatcher.forward(request, response);
		} catch (ServletException e) {

		}

		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");

	}
}