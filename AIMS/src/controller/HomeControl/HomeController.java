package controller.HomeControl;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import controller.BaseController;
import entity.media.Book;
import entity.media.media;
import javafx.stage.Stage;
import views.screen.BaseScreenHandler;


public class HomeController extends BaseController {

	public List<media> getAllMedia() throws SQLException { // Functional Cohesion
		return new media().getAllMedia();
	}

	public List<String> getAllTypeMedia() throws SQLException { // Functional Cohesion
		return new media().getTypeMedia();
	}

	public List<media> getListMediaByType(String type) throws SQLException { // Functional Cohesion
		return new media().getMediaByType(type);
	}
}
