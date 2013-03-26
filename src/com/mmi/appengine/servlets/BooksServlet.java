/**
 * 
 */
package com.mmi.appengine.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.books.Books;

/**
 * @author erickguevara
 *
 */
public class BooksServlet extends HttpServlet {

	private static final Logger log = Logger.getLogger(BooksServlet.class.getName());
	static Books books = null;

	/** Global instance of the HTTP transport. */
	private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();

	/** Global instance of the JSON factory. */
	private static final JsonFactory JSON_FACTORY = new JacksonFactory();

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		String userId = "104374180721134943602"; //getUserId(request);

		books = new Books.Builder(HTTP_TRANSPORT, JSON_FACTORY, new HttpRequestInitializer() {
			public void initialize(HttpRequest request) throws IOException {}
		}).setApplicationName("books-api").build();
		
		Books.Bookshelves.Volumes.List haveReadList = books.bookshelves().volumes().list(userId, "4");

		haveReadList.setMaxResults((long) 100);
		haveReadList.setFields("items(saleInfo/retailPrice/amount,volumeInfo(authors,imageLinks/thumbnail,pageCount,title)),totalItems");
		haveReadList.set("callback", new String("jsonpcallback"));
		
		
		response.setContentType("application/json");
		
		
		PrintWriter out = response.getWriter();
		out.println(haveReadList.executeUnparsed().parseAsString());
		out.close();

	}	




}
