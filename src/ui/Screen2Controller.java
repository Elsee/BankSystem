package ui;

import environment.Screens.ScreenTransition;
import environment.ViewController;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.util.Duration;

public class Screen2Controller extends ViewController {

	private @FXML Button backButton;

	@FXML
	void back() {
		transitionTo("screen1");
	}

	@Override
	protected void willAppear(ScreenTransition transition) {
		backButton.setTranslateY(-stage.getHeight());
		transition.proceed();
	}

	@Override
	protected void appear(ScreenTransition transition) {

		TranslateTransition translate = new TranslateTransition(Duration.seconds(1), backButton);
		translate.setToY(20);
		translate.setOnFinished(transition::proceed);
		translate.play();

	}

	@Override
	protected void disappear(ScreenTransition transition) {

		TranslateTransition translate = new TranslateTransition(Duration.seconds(1), backButton);
		translate.setToY(-stage.getHeight());
		translate.setOnFinished(transition::proceed);
		translate.play();

	}

}
