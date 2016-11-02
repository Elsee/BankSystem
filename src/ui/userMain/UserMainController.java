package ui.userMain;

import environment.DataAccess;
import environment.Screens.ScreenTransition;
import environment.ViewController;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.TitledPane;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class UserMainController extends ViewController {

//	private @FXML Button backButton;
	private ObservableList<Account> accountsCollection;

	@FXML
	Accordion accountsAccordion;
	@FXML
	TitledPane account_pane;
	@FXML
	Text transaction_date;
	@FXML
	Text transaction_description;
	@FXML
	Text transaction_sum;

	@FXML
	protected void init() {
		try {
			List<Account> accounts = data.getAllAccounts();
			accountsCollection = FXCollections.observableArrayList(accounts);
			TitledPane[] tps = new TitledPane[accountsCollection.size()];
			for (int i = 0; i < accountsCollection.size(); i++) {
				String accountTitle;
				accountTitle = accounts.get(i).getNumString() + " " + accounts.get(i).getBalance();
				AnchorPane accounContent = new AnchorPane();
				tps[i] = new TitledPane(accountTitle, accounContent);
			}
			accountsAccordion.getPanes().addAll(tps);
			accountsAccordion.setExpandedPane(tps[0]);
		} catch (SQLException e) {
			e.printStackTrace();
		}
//		firstNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
//		lastNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
//		cityColumn.setCellFactory(TextFieldTableCell.forTableColumn());
	}

	@FXML
	void back() {
		transitionTo("screen1");
	}

	@FXML
	void transactionForm() {
		transitionTo("transactionForm");
	}

//	@Override
//	protected void willAppear(ScreenTransition transition) {
//		backButton.setTranslateY(-stage.getHeight());
//		transition.proceed();
//	}
//
//	@Override
//	protected void appear(ScreenTransition transition) {
//
//		TranslateTransition translate = new TranslateTransition(Duration.seconds(1), backButton);
//		translate.setToY(20);
//		translate.setOnFinished(transition::proceed);
//		translate.play();
//
//	}
//
//	@Override
//	protected void disappear(ScreenTransition transition) {
//
//		TranslateTransition translate = new TranslateTransition(Duration.seconds(1), backButton);
//		translate.setToY(-stage.getHeight());
//		translate.setOnFinished(transition::proceed);
//		translate.play();
//
//	}

}
