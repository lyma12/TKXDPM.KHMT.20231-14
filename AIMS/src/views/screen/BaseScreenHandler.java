package views.screen;


import java.io.IOException;
import java.util.Hashtable;

import controller.BaseController;
import controller.HomeControl.HomeController;
import javafx.scene.Scene;
import javafx.stage.Stage;
import views.screen.home.HomeScreenHandler;

public class BaseScreenHandler extends FXMLScreenHandler {
	private Scene scene;
	private BaseScreenHandler prev;
	protected final Stage stage;
	protected Hashtable<String, String> messages;
	private BaseController bController;
	protected HomeScreenHandler homeScreenHandler;
	protected String title;

	
	public BaseScreenHandler(String screenPath) throws IOException {
		super(screenPath);
		this.stage = new Stage();
	}
	public void setPreviousScreen(BaseScreenHandler prev) {
		this.prev = prev;
	}
	public BaseScreenHandler getPreviousScreen() {
		return this.prev;
	}

	public BaseScreenHandler(Stage stage, String screenPath) throws IOException {
		super(screenPath);
		this.stage = stage;
	}
	public void setBController(BaseController basecontroller){
		this.bController = basecontroller;
	}

	public BaseController getBController(){
		return this.bController;
	}

	public void show() {
		if (this.scene == null) {
			this.scene = new Scene(this.content);
		}
		this.stage.setTitle(this.title);
		this.stage.setScene(this.scene);
		this.stage.show();
	}
	
	public void showAndWait() {
		if (this.scene == null) {
			this.scene = new Scene(this.content);
		}
		this.stage.setScene(this.scene);
		this.stage.showAndWait();
	}
	
	public void setScreenTitle(String string) {
		this.title = string;
		this.stage.setTitle(string);
	}
	public void forward(Hashtable<String, String> messages) {
		this.messages = messages;
	}
	public void setHomeScreenHandler(HomeScreenHandler HomeScreenHandler) {
		this.homeScreenHandler = HomeScreenHandler;
	}
	public HomeScreenHandler getHomeScreenHandler() {
		return this.homeScreenHandler;
	}
	public Stage getStage() {
		return this.stage;
	}
}
