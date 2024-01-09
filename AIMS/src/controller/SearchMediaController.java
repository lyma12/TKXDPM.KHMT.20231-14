package controller;

import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;

import controller.HomeControl.HomeController;
import entity.media.Suggestion;
import entity.media.media;

public class SearchMediaController extends HomeController {
	
	private Suggestion suggestion;
	
	public Suggestion getSuggestion() {
		this.suggestion = new Suggestion();
		return this.suggestion;
	}
	
	public List<media> search(String query, int offset) throws SQLException {
		String sql = "Select * From Media where title like " + query + 
				this.suggestion.getQuery()
				+ " limit 6 offset " + offset + ";";
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
