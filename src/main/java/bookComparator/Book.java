package bookComparator;

import java.util.ArrayList;

public class Book {
	private String isbn;
	private String titulo;
	private String editorial;
	private String autores;
	private String image;
	private String url;
	private ArrayList<BookOffer> ofertas;

	public Book() {
		super();
		this.isbn = "";
		this.titulo = "";
		this.editorial = "";
		this.autores = "";
		this.image = "";
		this.url = "";
		this.ofertas = new ArrayList<BookOffer>();
	}

	// Métodos SET
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public void setEditorial(String editorial) {
		this.editorial = editorial;
	}

	public void setAutor(String autor) {
		this.autores = autor;
	}

	public void addOferta(BookOffer oferta) {
		this.ofertas.add(oferta);
	}

	// Métodos GET
	public String getIsbn() {
		return isbn;
	}

	public String getTitulo() {
		return titulo;
	}

	public String getAutores() {
		return autores;
	}

	public String getImage() {
		return image;
	}

	public String getUrl() {
		return url;
	}

	public String getEditorial() {
		return editorial;
	}

	public ArrayList<BookOffer> getOfertas() {
		return ofertas;
	}

	public int numberMatching(String name) {
		String lowname = name.toLowerCase();
		String[] parts = lowname.split(" ");
		int num_palabras = parts.length;
		int matchs = 0;
		String lownombre = isbn.toLowerCase();
		for (int i = 0; i < num_palabras; ++i) {
			if (lownombre.contains(parts[i]))
				matchs++;
		}
		return matchs;
	}

	public boolean nameMatching(String name) {
		String[] parts = name.split(" ");
		int num_palabras = parts.length;

		return numberMatching(name) > num_palabras / 2 + 1;
	}
}
