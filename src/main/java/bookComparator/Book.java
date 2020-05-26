package bookComparator;

import java.util.ArrayList;

public class Book {
	private String isbn;
	private String titulo;
	private String editorial;
	private String autores;
	private String image;
	private String url;
	private String mejorPrecio;
	private int posMejorPrecio;
	private ArrayList<BookOffer> ofertas;

	public Book() {
		super();
		this.isbn = "";
		this.titulo = "";
		this.editorial = "";
		this.autores = "";
		this.image = "";
		this.url = "";
		this.setMejorPrecio("");
		this.setPosMejorPrecio(0);
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

	public void buscarMejorPrecio()
	{
		double mejorOferta = 1000.0;
		int cont = 0;
		
		for(BookOffer oferta : this.ofertas)
		{
			if (oferta.getPrecio() != "No se han encontrado resultados")
			{				
				String val = oferta.getPrecio().split("�")[0].replace(",", ".");
				
				if (oferta.getSite().equals("Fnac"))
					val = val.substring(0, val.length());
				else
					val = val.substring(0, val.length() - 1);
				
				double valNum = Double.valueOf(val);
				
				if (mejorOferta > valNum)
				{
					mejorOferta = valNum;
					this.setPosMejorPrecio(cont);
				}
				
				cont++;
			}
			else
				cont++;
		}
		
		this.setMejorPrecio(String.valueOf(mejorOferta));
	}

	public String getMejorPrecio() {
		return mejorPrecio;
	}

	public void setMejorPrecio(String mejorPrecio) {
		this.mejorPrecio = mejorPrecio;
	}

	public int getPosMejorPrecio() {
		return posMejorPrecio;
	}

	public void setPosMejorPrecio(int posMejorPrecio) {
		this.posMejorPrecio = posMejorPrecio;
	}
	
	
}
