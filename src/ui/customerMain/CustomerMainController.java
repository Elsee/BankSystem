package ui.customerMain;

import environment.ViewController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import ui.employeeMain.CustomerAccount;
import ui.employeeMain.CustomerI;

import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerMainController extends ViewController {

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
		int ix = accountsTable.getSelectionModel().getSelectedIndex();
		CustomerAccount selesctedAccount = accounts.get(ix);
		ArrayList<Integer> selectedAccId = new ArrayList<>();
		selectedAccId.add(selesctedAccount.getAid());
		transitionTo("transactionsList", selectedAccId);
	}

	@FXML
	void spendings()
	{
		ArrayList<String> accountNum = new ArrayList<>();
		accountNum.add("2931297219618678");
		transitionTo("spendings", accountNum);
	}
}
