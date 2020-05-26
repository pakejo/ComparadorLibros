package bookComparator;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebScraping {

	public void searchPopularLibros(Book libro) throws IOException {
		Document d;
		String url = "https://www.popularlibros.com/busqueda/listaLibros.php?tipoBus=full&difusa=N&tipoArticulo=&palabrasBusqueda="
				+ libro.getIsbn() + "&boton=";
		d = Jsoup.connect(url).timeout(6000).get();
		Elements ele = d.select("div.container");
		
		//Autor y titulo
		for (Element e : ele.select("dl.dublincore")) 
		{
			if (libro.getTitulo() == "")
				libro.setTitulo(e.select("dd.title").text());

			if (libro.getAutores() == "")
				libro.setAutor(e.select("dd.creator").text());
		}
		
		// Imagen portada
		Elements img = d.getElementsByClass("foto");
		
		if (libro.getImage() == "")
			libro.setImage(img.attr("src"));
		
		//Precio
		String precio_final = "";
		
		for (Element e : ele.select("div.botones")) {
			String precio = e.select("p.precio").text();

			if (precio.length() > 7)
				precio_final = precio.split("€")[1];
			else
				precio_final = (String) precio.subSequence(0, precio.length() - 2);

		}
		if (precio_final != "") {
			
			String urlLibro = "";
			
			// Editorial
			urlLibro = d.getElementsByClass("portada").select("a").attr("href");
			d = Jsoup.connect("https://www.popularlibros.com" + urlLibro).timeout(6000).get();
			
			if (libro.getEditorial() == "")
			{
				libro.setEditorial(d.getElementsByClass("editorial").get(0).text());				
			}
			
			BookOffer oferta = new BookOffer("https://www.popularlibros.com" + urlLibro, "Popular Libros", precio_final + " €");
			libro.addOferta(oferta);
			
		} else {
			BookOffer oferta = new BookOffer(url, "Popular Libros", "No se han encontrado resultados");
			libro.addOferta(oferta);
		}
		
		
	}
	
	public void buscarAgapea(Book libro) throws IOException {
		
		// Cogemos primera pagina
		String url = "https://www.agapea.com/buscar/buscador.php?texto=" + libro.getIsbn();
		Document d = Jsoup.connect(url).timeout(6000).get();
		String e = "";

		String encontrado = d.select("h3").get(1).text();
		String mensaje_error = "No se han encontrado resultados para " + libro.getIsbn();
		
		if (encontrado.equals(mensaje_error))
		{
			BookOffer oferta = new BookOffer(url, "Agapea", "No se han encontrado resultados");
			libro.addOferta(oferta);	
		}
		else
		{
			e = "https://www.agapea.com" + d.select("figure").first().child(0).attr("href");
			d = Jsoup.connect(e).timeout(6000).get();
			
			if (e != "")
			{			
				// Cogemos imagen
				if (libro.getImage() == "")
				{
					Elements img = d.getElementsByClass("portada");
					libro.setImage(img.attr("src"));			
				}
				
				// Cogemos titulo
				
				if (libro.getTitulo() == "")
				{
					Elements titulo = d.getElementsByClass("span9");
					libro.setTitulo(titulo.get(1).select("h1").text());			
				}
				
				// Cogemos editorial
				
				if (libro.getEditorial() == "")
				{
					Elements editorial = d.getElementsByClass("span12");
					libro.setEditorial(editorial.get(2).getAllElements().get(19).text());			
				}
				
				// Cogemos el precio
				Elements precio = d.getElementsByClass("precio");
				String price = precio.get(0).getAllElements().get(0).text().split("€")[0];
				
				if (price != "") {
					BookOffer oferta = new BookOffer(e, "Agapea", price + " €");
					libro.addOferta(oferta);
				} else {
					BookOffer oferta = new BookOffer(e, "Agapea", "No se han encontrado resultados");
					libro.addOferta(oferta);
				}	
			}
			else
			{
				BookOffer oferta = new BookOffer(e, "Agapea", "No se han encontrado resultados");
				libro.addOferta(oferta);
			}
		}
	}
	
	public void buscarFnac(Book libro) throws IOException
	{
		// Cogemos primera pagina
		String url = "https://www.fnac.es/SearchResult/ResultList.aspx?SCat=0%211&Search=" + libro.getIsbn() + "&sft=1&sa=0";
		Document d = Jsoup.connect(url).timeout(6000).get();
		
		String existe = d.getElementsByClass("Search-titleCount").get(0).text();
		
		if(existe.equals("(0)"))
		{
			BookOffer oferta = new BookOffer(url, "Fnac", "No se han encontrado resultados");
			libro.addOferta(oferta);
		}
		else
		{			
			// Cogemos pagina del libro
			String url2 = d.getElementsByClass("Article-title").get(0).attr("href");
			d = Jsoup.connect(url2).timeout(6000).get();
			
			//Cogemos la imagen
			if(libro.getImage() == "")
				libro.setImage(d.getElementsByClass("f-productVisuals-mainMedia").get(0).attr("src"));
			
			//Cogemos el titulo
			if(libro.getTitulo() == "")
				libro.setTitulo(d.getElementsByClass("f-productHeader-Title").get(0).text());
			
			//Cogemos el autor
			if(libro.getAutores() == "")
				libro.setAutor(d.getElementsByClass("Feature-desc").get(0).getAllElements().get(2).text());
			
			//Cogemos editorial
			if(libro.getEditorial() == "")
				libro.setEditorial(d.getElementsByClass("Feature-desc").get(1).getAllElements().get(2).text());
			
			//Cogemos el precio
			String price = d.getElementsByClass("f-priceBox-price--reco").get(0).text();
			
			if (price != "") {
				BookOffer oferta = new BookOffer(url2, "Fnac", price);
				libro.addOferta(oferta);
			} else {
				BookOffer oferta = new BookOffer(url2, "Fnac", "No se han encontrado resultados");
				libro.addOferta(oferta);
			}	
		}
		
	}
}







