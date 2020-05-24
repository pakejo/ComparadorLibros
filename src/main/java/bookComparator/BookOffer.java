package bookComparator;

public class BookOffer {
	String url;
	String site;
	String precio;

	public BookOffer(String url, String site, String precio) {
		this.url = url;
		this.site = site;
		this.precio = precio;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public void setPrecio(String precio) {
		this.precio = precio;
	}

	public String getUrl() {
		return url;
	}

	public String getSite() {
		return site;
	}

	public String getPrecio() {
		return precio;
	}
}
