package bookComparator;

import java.util.ArrayList;
import com.amazon.paapi5.v1.ApiClient;
import com.amazon.paapi5.v1.ApiException;
import com.amazon.paapi5.v1.ErrorData;
import com.amazon.paapi5.v1.Item;
import com.amazon.paapi5.v1.PartnerType;
import com.amazon.paapi5.v1.SearchItemsRequest;
import com.amazon.paapi5.v1.SearchItemsResource;
import com.amazon.paapi5.v1.SearchItemsResponse;
import com.amazon.paapi5.v1.api.DefaultApi;

public class API_Amazon {

	private ApiClient client;
	private DefaultApi api;
	private String partnerTag;

	public API_Amazon() {
		client = new ApiClient();
		// Add your credentials
		// Please add your access key here
		// Please add your access key here
		client.setAccessKey("AKIAJ2M7Z62GFWKITXBA");
		// Please add your secret key here
		client.setSecretKey("ASJo63DLv+0IaNwTp1j4ytzQvkUaM+AXNlbAOBVa");

		// Enter your partner tag (store/tracking id)
		partnerTag = "ikor0b-21";

		/*
		 * PAAPI Host and Region to which you want to send request. For more details
		 * refer:
		 * https://webservices.amazon.com/paapi5/documentation/common-request-parameters
		 * .html#host-and-region
		 */
		client.setHost("webservices.amazon.es");
		client.setRegion("eu-west-1");

		api = new DefaultApi(client);
	}

	public Book consultas(String isbn) {
		// Request initialization
		Book book = new Book();
		/*
		 * Choose resources you want from SearchItemsResource enum For more details,
		 * refer: https://webservices.amazon.com/paapi5/documentation/search-items.html#
		 * resources-parameter
		 */
		ArrayList<SearchItemsResource> searchItemsResources = new ArrayList<SearchItemsResource>();
		searchItemsResources.add(SearchItemsResource.ITEMINFO_TITLE);
		searchItemsResources.add(SearchItemsResource.OFFERS_LISTINGS_PRICE);
		searchItemsResources.add(SearchItemsResource.ITEMINFO_BYLINEINFO);
		searchItemsResources.add(SearchItemsResource.IMAGES_PRIMARY_LARGE);

		/*
		 * Specify the category in which search request is to be made For more details,
		 * refer:
		 * https://webservices.amazon.com/paapi5/documentation/use-cases/organization-of
		 * -items-on-amazon/search-index.html
		 */
		String searchIndex = "All";

		// Specify keywords
		String keywords = isbn;

		// Sending the request
		SearchItemsRequest searchItemsRequest = new SearchItemsRequest().partnerTag(partnerTag).keywords(keywords)
				.searchIndex(searchIndex).resources(searchItemsResources).partnerType(PartnerType.ASSOCIATES);

		// Forming the request
		SearchItemsResponse response;
		try {
			response = api.searchItems(searchItemsRequest);

			System.out.println("API called successfully");
			System.out.println("Complete response: " + response);

			String url = "";
			String precio = "";

			// Parsing the request
			if (response.getSearchResult() != null) {
				Item item = response.getSearchResult().getItems().get(0);

				if (item != null) {
					if (item.getDetailPageURL() != null) {
						url = item.getDetailPageURL();
					}
					if (item.getItemInfo() != null && item.getItemInfo().getTitle() != null
							&& item.getItemInfo().getTitle().getDisplayValue() != null) {
						book.setTitulo(item.getItemInfo().getTitle().getDisplayValue());
					}
					if (item.getOffers() != null && item.getOffers().getListings() != null
							&& item.getOffers().getListings().get(0).getPrice() != null
							&& item.getOffers().getListings().get(0).getPrice().getDisplayAmount() != null) {
						precio = item.getOffers().getListings().get(0).getPrice().getDisplayAmount();
					}
				}

				String autor = item.getItemInfo().getByLineInfo().getContributors().get(0).getName();
				System.out.println(autor);
				String editorial = item.getItemInfo().getByLineInfo().getManufacturer().getDisplayValue();
				System.out.println(editorial);
				String imagen = item.getImages().getPrimary().getLarge().getURL();
				System.out.println(imagen);
				
				book.setAutor(autor);
				book.setEditorial(editorial);
				book.setImage(imagen);
			}

			book.addOferta(new BookOffer(url, "Amazon", precio));

			if (response.getErrors() != null) {
				System.out.println("Printing errors:\nPrinting Errors from list of Errors");
				for (ErrorData error : response.getErrors()) {
					System.out.println("Error code: " + error.getCode());
					System.out.println("Error message: " + error.getMessage());
				}
			}
			
		} catch (ApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return book;
	}

}
