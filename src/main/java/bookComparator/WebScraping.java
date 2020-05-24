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
		
		//Imagen portada
		Elements img = d.getElementsByClass("foto");
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
			BookOffer oferta = new BookOffer(url, "Popular Libros", "EUR " + precio_final);
			libro.addOferta(oferta);
		} else {
			BookOffer oferta = new BookOffer(url, "Popular Libros", "No se han encontrado resultados");
			libro.addOferta(oferta);
		}
	}

	public void searchAgapea(Book libro) throws IOException {
		String url = "https://www.agapea.com/buscar/buscador.php?texto=" + libro.getIsbn();
		Document d = Jsoup.connect(url).timeout(6000).get();
		Elements ele = d.select("div.row-fluid");
		String precio_final = "";

		for (Element e : ele.select("div.info2")) {
			String precio = e.select("span.precio").text();

			if (precio.length() > 7)
				precio_final = (String) precio.subSequence(6, precio.length() - 1);
			else
				precio_final = (String) precio.subSequence(0, precio.length() - 1);
		}

		if (precio_final != "") {
			BookOffer oferta = new BookOffer(url, "Agapea", "EUR " + precio_final);
			libro.addOferta(oferta);
		} else {
			BookOffer oferta = new BookOffer(url, "Agapea", "No se han encontrado resultados");
			libro.addOferta(oferta);
		}
	}
}