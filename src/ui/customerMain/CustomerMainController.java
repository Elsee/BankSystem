package ui.customerMain;

import environment.ViewController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import ui.employeeMain.CustomerAccount;

import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerMainController extends ViewController {

//	private @FXML Button backButton;
	private ObservableList<Account> accountsCollection;
	private ObservableList<Transaction> transactionsCollection;

	ArrayList<CustomerAccount> accounts = new ArrayList<>();

	@FXML
	TableView accountsTable;


	@FXML
	protected void init() {
		try {
			if (this.getParam() != null) {
				String personId = (int)this.getParam().get(0) + "";
				int custId = this.data.getCustomerId(personId);
				accounts = this.data.getAccounts(custId);
				ObservableList searchedAccounts = FXCollections.observableArrayList(accounts);
				accountsTable.setItems(searchedAccounts);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
/*		try {
			List<Account> accounts = data.getAllAccounts();
			accountsCollection = FXCollections.observableArrayList(accounts);
			TitledPane[] tps = new TitledPane[accountsCollection.size()];
			List<Transaction> transactions = data.getAllTransactions();
			transactionsCollection = FXCollections.observableArrayList(transactions);

			for (int i = 0; i < accountsCollection.size(); i++) {
				String accountTitle;
				String accountNum = accounts.get(i).getAccount_id()+"";

				accountTitle = accountNum.substring(0,4)+" "+
						accountNum.substring(4,8)+" "+
						accountNum.substring(8,12)+" "+
						accountNum.substring(12,15) +"		" + accounts.get(i).getBalance();
				AnchorPane accountContent = new AnchorPane();
				for (int j=0; j < transactionsCollection.size(); j++) {
					HBox box = new HBox();
					Text tTime = new Text();
					tTime.setText(transactions.get(j).getTime());
					box.getChildren().setAll(tTime);
					accountContent.getChildren().add(box);
				}
				tps[i] = new TitledPane(accountTitle, accountContent);
			}
			accountsAccordion.getPanes().addAll(tps);
			accountsAccordion.setExpandedPane(tps[0]);
		} catch (SQLException e) {
			e.printStackTrace();
		}*/
	}

	@FXML
	void back() {
		transitionTo("login");
	}

	@FXML
	void transactionForm() {
		transitionTo("transactionForm", accounts);
	}

	@FXML
	void viewTransactions() {
		transitionTo("transactionsList");
	}

	@FXML
	void spendings() {
		transitionTo("spendings");
	}
}
