package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;

import controller.HomeControl.HomeController;
import entity.media.Suggestion;
import entity.media.media;
import entity.user.User;
import javafx.stage.Stage;
import views.screen.BaseScreenHandler;
import views.screen.search.SearchScreen;

public class SearchMediaController extends HomeController {
	
	private Suggestion suggestion;
	
	
	public void processSearch(Stage stage, BaseScreenHandler prev, String text, User user) throws SQLException, IOException {
		suggestion = new Suggestion();
		List<String> list =  media.getTypeMedia();
		SearchScreen searchScreen = new SearchScreen(stage, suggestion, list, text, user);
		this.display(searchScreen, prev, "Search Screen");
		searchScreen.search();
	}
	
	public List<media> search(String query, int offset) throws SQLException {
		String sql = "Select * From Media where title like " + query + 
					" and price <= " + this.suggestion.getMaxPrice() +
					" and price >= " + this.suggestion.getMinPrice();
		if(this.suggestion.getRushOrder()) {
			sql += " and support_rush_order = true ";
		}
		if(this.suggestion.getType() != null && !this.suggestion.getType().isBlank()) {
			sql += " and type = '" + this.suggestion.getType() + "' ";
		}
		sql += " limit 6 offset " + offset + ";";
		return media.searchMedia(sql);
	}
	public void sortByTitle(List<media> l) {
		Comparator<media> mediaTitleComparator = new Comparator<media>() {
            @Override
            public int compare(media m1, media m2) {
                return m1.getTitle().compareToIgnoreCase(m2.getTitle());
            }
        };
        l.sort(mediaTitleComparator);
	}
	public void sortByDate(List<media> l){
		Comparator<media> mediaTitleComparator = new Comparator<media>() {
            @Override
            public int compare(media m1, media m2) {
                return m1.getTitle().compareToIgnoreCase(m2.getTitle());
            }
        };
        l.sort(mediaTitleComparator);
	}
	public void sortByPrice(List<media> l){
		Comparator<media> mediaTitleComparator = new Comparator<media>() {
            @Override
            public int compare(media m1, media m2) {
            	int priceComparison = m1.getPrice() - m2.getPrice();
                if (priceComparison != 0) {
                    return priceComparison;
                }
                return 0;
            }
        };
        l.sort(mediaTitleComparator);
	}

}
