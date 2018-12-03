import java.io.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.input.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.image.*;
import javafx.stage.Stage;

public class VendingGUI extends Application {

	private static Coin[] coins;
	private VendingMachine machine 	= new VendingMachine();
	private Stage primaryStage 		= new Stage();
	private Stage secondaryStage 	= new Stage();
	private Product[] products;
		
	public VendingGUI() throws IOException {
		coins = Reader.currencyReader("Money.txt");
	}
	
	@Override
	public void start(Stage primaryStage) {
        	  
		Scene scene = new Scene(getMenuPane());
		primaryStage.setTitle("Vending Menu");
		primaryStage.setScene(scene); 
		primaryStage.show();	
	}
	
	public GridPane getMenuPane() {
		GridPane gPane = new GridPane();
		gPane.setAlignment(Pos.CENTER);
		gPane.setPadding(new Insets(16.5, 17.5, 19.5, 20.5));
		gPane.setPrefHeight(175);
		gPane.setPrefWidth(325);
		gPane.setHgap(45.5);
		gPane.setVgap(25.5);
		
		RadioButton rbShowProducts 	= new RadioButton("Show Products");
		RadioButton rbInsertCoins 	= new RadioButton("Insert Coins");
		RadioButton rbBuy 			= new RadioButton("Buy");
		RadioButton rbReturnCoins 	= new RadioButton("Return Coins");
		RadioButton rbOpFuntions 	= new RadioButton("Operator Functions");
		RadioButton rbQuit 			= new RadioButton("Quit");
		
		gPane.add(rbShowProducts,0,0);
		gPane.add(rbInsertCoins,0,1);
		gPane.add(rbBuy,0,2);
		gPane.add(rbReturnCoins,1,0);
		gPane.add(rbOpFuntions,1,1);
		gPane.add(rbQuit,1,2);
	
		ToggleGroup group = new ToggleGroup(); //This group ensures the radio buttons stay pushed
		rbShowProducts.setToggleGroup(group);  //untill the next scene is launched.
		rbInsertCoins.setToggleGroup(group);
		rbBuy.setToggleGroup(group);
		rbReturnCoins.setToggleGroup(group);
		rbOpFuntions.setToggleGroup(group);
		rbQuit.setToggleGroup(group);
		
		//Show Products menu button
		rbShowProducts.setOnAction(e -> {
			if (rbShowProducts.isSelected()) {
				Scene showProductsScene = new Scene(getShowItemsPane());
				primaryStage.setTitle("Show Products");
				primaryStage.setScene(showProductsScene);
				primaryStage.show();
				rbShowProducts.setSelected(false); //This unchecks the radio button once the next scene is shown.
			}
		});
		//Insert Coins menu button
		rbInsertCoins.setOnAction(e -> {
			if (rbInsertCoins.isSelected()) {
				Scene insertCoinsScene = new Scene(getInsertCoinsPane());
				primaryStage.setTitle("Insert Coins");
				primaryStage.setScene(insertCoinsScene);
				primaryStage.show();
				rbInsertCoins.setSelected(false);
			}
		});
		//Buy Product menu button
		rbBuy.setOnAction(e -> {
			if (rbBuy.isSelected()) {
				Scene buyScene = new Scene(getBuyPane());
				primaryStage.setTitle("Buy Product");
				primaryStage.setScene(buyScene);
				primaryStage.show();
				rbBuy.setSelected(false);
			}
		});
		//Return Coins menu button
		rbReturnCoins.setOnAction(e -> {
			if (rbReturnCoins.isSelected()) {
				Scene returnCoinsScene = new Scene(getReturnCoinsPane(false));
				primaryStage.setTitle("Return Coins");
				primaryStage.setScene(returnCoinsScene);
				primaryStage.show();
				rbReturnCoins.setSelected(false);
			}
		});
		//Operator Functions Menu Button
		rbOpFuntions.setOnAction(e -> {
			if (rbOpFuntions.isSelected()) {
				Scene loginScene = new Scene(getLoginPane());
				primaryStage.setTitle("Login...");
				primaryStage.setScene(loginScene);
				primaryStage.show();
				rbOpFuntions.setSelected(false);
			}
		});
		//Quit Menu Button
		rbQuit.setOnAction(e -> {
			if (rbQuit.isSelected()) {
				Scene quitScene = new Scene(getQuitPane());
				primaryStage.setTitle("Ad\u00ED"+"os CowBoy!");
				primaryStage.setScene(quitScene);
				primaryStage.show();
				writeFiles(); // this writes all our files before quitting the application.
			}
		});
		return gPane;
	}
////////////////////////////////////////////////////////////////////////////////////

	public BorderPane getBuyPane(){
		BorderPane pane 	= new BorderPane();
		GridPane buttonPane = new GridPane();
		Button btPurchase 	= new Button("Purchase");
		Button btClose	 	= new Button("Back");
		pane.setPrefHeight(175);
		pane.setPrefWidth(320);
		buttonPane.add(btPurchase, 3, 0);
		buttonPane.add(btClose, 0, 0);
		pane.setBottom(buttonPane);
		products = machine.getProductTypes(false); // loads the products array with the current stock.
		
		if(products.length>0) { // if there are no products.
			String[] productInfo = new String[products.length]; // next few lines fill productInfo for ComboBox
			for(int i=0; i<products.length; i++)
				productInfo[i] = "Product: " + products[i].getDescription() + 
								 ", Price: $" + String.format("%1.2f", products[i].getPrice());

			ObservableList<String> items = FXCollections.observableArrayList(productInfo);
			ComboBox<String> cbo 		 = new ComboBox<>();
			TextArea textArea 			 = new TextArea();
			textArea.setPrefWidth(320);
			textArea.setPrefHeight(125);
			textArea.setEditable(false); // this and the following two lines prevent users editing textArea
			textArea.setMouseTransparent(true);
			textArea.setFocusTraversable(false);
			textArea.setText(machine.getCurrentCredit());
			BorderPane paneForComboBox 	 = new BorderPane();
			paneForComboBox.setLeft(new Label("Select Product to Buy: "));
			paneForComboBox.setRight(cbo);
			pane.setTop(paneForComboBox);
			cbo.setValue("Product..."); // default display value for comboBox
			cbo.setPrefWidth(200);
			cbo.getItems().addAll(items); 
			pane.setCenter(textArea);
			
			cbo.setOnMouseClicked(e -> textArea.setText(machine.getCurrentCredit()));
			btPurchase.setOnAction(e -> {
				int indx = items.indexOf(cbo.getValue());
				try{
					textArea.setText(machine.buyProduct(products[indx]) + 
									 "\n" + machine.getCurrentCredit() +
									 "\n\n" + "This machine does not give change.");
				}
				catch(NullPointerException except) {
					textArea.setText("No Options Currently Available");
				}
				catch (VendingException ex) {
					textArea.setText(ex.getMessage());
				}
				cbo.setValue("Product...");
			});
		}else{
			Label message = new Label("No products in stock.");
			pane.setCenter(message);
		}
		btClose.setOnAction(e->primaryStage.hide()); // close current stage
		return pane;	
	}
////////////////////////////////////////////////////////////////////////////////////
	
	public BorderPane getShowItemsPane(){
		
		BorderPane pane = new BorderPane();
		Button btClose  = new Button("Back");
		pane.setPrefHeight(175);
		pane.setPrefWidth(320);
		pane.setBottom(btClose);
	
		products = machine.getProductTypes(false); // loads the products array with the current stock.
		
		if(products.length>0){
			String[] productNames = new String[products.length];
			for(int i=0; i<products.length; i++){
					productNames[i] = products[i].getDescription();
				}
				
			String[] productDescriptions = new String[products.length];	
			for(int i=0; i<products.length; i++){
					productDescriptions[i] = products[i].toString();
				}
			
			ComboBox<String> cbo = new ComboBox<>();
			TextArea textArea 	 = new TextArea();
			textArea.setPrefHeight(125);
			textArea.setPrefWidth(320);
			textArea.setEditable(false);
			textArea.setMouseTransparent(true);
			textArea.setFocusTraversable(false);
			BorderPane paneForComboBox = new BorderPane();
			paneForComboBox.setLeft(new Label("Select a product to view: "));
			paneForComboBox.setRight(cbo);
			pane.setTop(paneForComboBox);
			cbo.setPrefWidth(100);
			cbo.setValue("Product...");
			
			ObservableList<String> items = FXCollections.observableArrayList(productNames);
			cbo.getItems().addAll(items); 
			pane.setCenter(textArea);
			
			// Display the selected product
			cbo.setOnAction(e -> textArea.setText(productDescriptions[(items.indexOf(cbo.getValue()))]));
		}else{
			Label message = new Label("No products in stock.");
			pane.setCenter(message);
		}
		btClose.setOnAction(e->primaryStage.hide());
		return pane;	
	}
////////////////////////////////////////////////////////////////////////////////////
	
	public BorderPane getInsertCoinsPane(){
		
		BorderPane pane 	= new BorderPane();
		GridPane buttonPane = new GridPane();
		Button btInsert 	= new Button("Insert");
		Button btClose	 	= new Button("Back");
		pane.setPrefHeight(175);
		pane.setPrefWidth(320);
		buttonPane.add(btInsert, 3, 0);
		buttonPane.add(btClose, 0, 0);
		pane.setBottom(buttonPane);
		
		if(coins.length>1 && coins[0].getValue()>0){	
		
			String[] coinNames = new String[coins.length];
			for(int i=0; i<coins.length; i++)
				coinNames[i] = coins[i].getName();
			ObservableList<String> items = FXCollections.observableArrayList(coinNames);
			
			ComboBox<String> cbo 		= new ComboBox<>();
			BorderPane paneForComboBox  = new BorderPane();
			TextArea textArea 			= new TextArea();
			textArea.setPrefHeight(125);
			textArea.setPrefWidth(320);
			textArea.setEditable(false);
			textArea.setMouseTransparent(true);
			textArea.setFocusTraversable(false);
			paneForComboBox.setLeft(new Label("Select a coin to add: "));
			paneForComboBox.setRight(cbo);
			pane.setTop(paneForComboBox);
			pane.setCenter(textArea);
			cbo.setPrefWidth(175);
			cbo.setValue("Coin...");
			cbo.getItems().addAll(items); 
			
			cbo.setOnMouseClicked(e -> textArea.clear());
			btInsert.setOnAction(e -> {
				int indx = items.indexOf(cbo.getValue());
				if(indx>=0){
					try{	
						textArea.setText("Added: " + coinNames[indx] + "\n" + machine.addCoin(coins[indx]));
					}
					catch(NullPointerException ex) {
						textArea.setText("No Options Currently Available");
					}
					cbo.setValue("Coin...");
				}else
					textArea.setText("No coin chosen!");
			});
		}else{
			Label message = new Label("No Coins listed.");
			pane.setCenter(message);
		}
		btClose.setOnAction(e->primaryStage.hide());
		return pane;	
	
		
	}
////////////////////////////////////////////////////////////////////////////////////

	public GridPane getReturnCoinsPane(Boolean operator) {
		
		GridPane pane 	= new GridPane();
		pane.setAlignment(Pos.CENTER);
		pane.setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
		pane.setPrefWidth(320);
		pane.setPrefHeight(175);
		pane.setHgap(5.5);
		pane.setVgap(5.5);
		
		Button btReturn = new Button("Click to return coins");
		Button btClose 	= new Button("Back");
		TextArea ta 	= new TextArea();
		GridPane.setHalignment(btClose, HPos.RIGHT);
		pane.add(btReturn, 2, 0);
		pane.add(btClose, 0, 1);
		ta.setPrefHeight(125);
		ta.setPrefWidth(200);
		ta.setEditable(false);
		ta.setMouseTransparent(true);
		ta.setFocusTraversable(false);
		btReturn.setOnAction(e -> ta.setText(machine.removeMoney(operator)));
		pane.add(ta, 0, 0);
		pane.setColumnSpan(ta, 2);
		
		btClose.setOnAction(e -> {
			if(operator)
				secondaryStage.hide();
			else
				primaryStage.hide();
		});
		
		return pane;
	}
////////////////////////////////////////////////////////////////////////////////////
		
	public GridPane getLoginPane(){
	
		TextField tfUserName 	 = new TextField();
		PasswordField tfPassWord = new PasswordField();
		TextArea output 		 = new TextArea();
		output.setPrefHeight(35);
		output.setPrefWidth(155);
		output.setEditable(false);
		output.setMouseTransparent(true);
		output.setFocusTraversable(false);
		
		GridPane pane 			= new GridPane();
		pane.setAlignment(Pos.CENTER);
		pane.setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
		pane.setHgap(5.5);
		pane.setVgap(5.5);
		
		pane.add(output, 0, 3);
		pane.setColumnSpan(output,2);
		pane.add(new Label("User Name:"), 0, 0);
		pane.add(tfUserName, 1, 0);
		pane.add(new Label("Password:"), 0, 1); 
		pane.add(tfPassWord, 1, 1);
		Button btLogin = new Button("Login...");
		pane.add(btLogin, 1, 2);
		Button btClose = new Button("Back");
		pane.add(btClose, 0, 2);
		GridPane.setHalignment(btLogin, HPos.RIGHT);
			
		//What happens when the button is pressed...
		btLogin.setOnAction(e->{
			String userName = tfUserName.getText();
			String password = tfPassWord.getText();
								
			if(machine.login(userName,password)){
				Scene opScene = new Scene(getOperatorMenuPane());
				primaryStage.setTitle("Operator Menu");
				primaryStage.setScene(opScene);
				primaryStage.centerOnScreen();
				primaryStage.show();
			}else {
				output.setText("Login Failed");
			}							
		});
		
		btClose.setOnAction(e->primaryStage.hide());
		
		return pane;
	}	
////////////////////////////////////////////////////////////////////////////////////	
	
	public GridPane getOperatorMenuPane(){
		
		GridPane gPane = new GridPane();
		gPane.setAlignment(Pos.CENTER);
		gPane.setPrefHeight(175);
		gPane.setPrefWidth(320);
		gPane.setPadding(new Insets(16.5, 17.5, 19.5, 20.5));
		gPane.setHgap(45.5);
		gPane.setVgap(25.5);
		
		RadioButton rbWithdrawCoins = new RadioButton("Withdraw Coins");
		RadioButton rbRestock 		= new RadioButton("Restock");
		RadioButton rbAddProduct	= new RadioButton("Add Product");
		RadioButton rbQuit 			= new RadioButton("Back");
		
		gPane.add(rbWithdrawCoins,0,0);
		gPane.add(rbRestock,0,1);
		gPane.add(rbAddProduct,1,0);
		gPane.add(rbQuit,1,1);
	
		ToggleGroup group = new ToggleGroup();
		rbWithdrawCoins.setToggleGroup(group);
		rbRestock.setToggleGroup(group);
		rbAddProduct.setToggleGroup(group);
		rbQuit.setToggleGroup(group);
		
		rbWithdrawCoins.setOnAction(e->{
			if (rbWithdrawCoins.isSelected()) {
				Scene returnCoinsScene = new Scene(getReturnCoinsPane(true));
				secondaryStage.setTitle("Operator Coin Withdrawl");
				secondaryStage.setScene(returnCoinsScene);
				secondaryStage.show();
				rbWithdrawCoins.setSelected(false);
			}
		});
		
		rbRestock.setOnAction(e -> {
			if (rbRestock.isSelected()) {
				Scene restockScene = new Scene(getRestockPane());
				secondaryStage.setTitle("Restock");
				secondaryStage.setScene(restockScene);
				secondaryStage.show();
				rbRestock.setSelected(false);
			}
		});

		rbAddProduct.setOnAction(e -> {
			if (rbAddProduct.isSelected()) {
				Scene scene = new Scene(getAddStockPane());
				secondaryStage.setTitle("Add Stock");
				secondaryStage.setScene(scene);
				secondaryStage.show();
				rbAddProduct.setSelected(false);
			}
		});
		
		rbQuit.setOnAction(e -> {
			if (rbQuit.isSelected()) {
				primaryStage.hide();
			}
		});
		
		return gPane;
	}
////////////////////////////////////////////////////////////////////////////////////

	public GridPane getRestockPane() {
		
		products 		 = machine.getProductTypes(false); // loads the products array with the current stock.
		GridPane pane 	 = new GridPane();
		TextArea ta 	 = new TextArea();
		Button btClose 	 = new Button("Back");
		Button btRestock = new Button("Restock Product");
		ta.setPrefWidth(150);
		ta.setPrefHeight(100);	
		ta.setEditable(false);
		ta.setMouseTransparent(true);
		ta.setFocusTraversable(false);
		
		
		if(products.length>0){
			String[] productNames = new String[products.length];
			for(int i=0; i<products.length; i++)
					productNames[i] = products[i].getDescription();
				
			String[] productDescriptions = new String[products.length];	
			for(int i=0; i<products.length; i++)
					productDescriptions[i] = products[i].toString();
			
			ObservableList<String> items = FXCollections.observableArrayList(productNames);
			
			ComboBox<String> cbo = new ComboBox<>();
			TextField tfQuantity = new TextField();
			cbo.setPrefWidth(150);
			cbo.setValue("Product...");
			cbo.getItems().addAll(items);

			pane.setAlignment(Pos.CENTER);
			pane.setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
			pane.setHgap(5.5);
			pane.setVgap(5.5);
			pane.add(cbo, 1, 0);
			pane.add(new Label("Quantity:"), 0, 1); 
			pane.add(tfQuantity, 1, 1);
			pane.add(ta, 0,2);
			pane.setColumnSpan(ta,2);
			pane.add(btRestock, 1, 3);
			pane.add(btClose, 0, 3);
			GridPane.setHalignment(btRestock, HPos.RIGHT);
			
			cbo.setOnMouseClicked(e -> ta.clear());
			tfQuantity.setOnMouseClicked(e -> ta.clear());
			btRestock.setOnAction(e -> {
				
				try{
					
					if(Validator.verifyInt(tfQuantity.getText())) {
						int q = Integer.parseInt(tfQuantity.getText());
						if(!(q > 0)) {
							ta.setText("\nInvalid Quantity");
						}
						else {
							ta.setText(machine.addProduct(products[items.indexOf(cbo.getValue())], q) + "\n" +
							(products[items.indexOf(cbo.getValue())] + " : " + q + " Added"));
						}
					}
					else
						ta.setText("\nInvalid Quantity");
				}
				catch(NullPointerException ex) {
					ta.setText("No Options Currently Available");
				}
				tfQuantity.clear();
				cbo.setValue("Product...");
			});
			
		}
		btClose.setOnAction(e -> secondaryStage.hide());
		return pane;
	}
////////////////////////////////////////////////////////////////////////////////////
	
	public GridPane getAddStockPane(){
		TextField tfDescription = new TextField();
		TextField tfQuantity 	= new TextField();
		TextField tfPrice 		= new TextField();
		TextArea ta 			= new TextArea();
		GridPane pane 			= new GridPane();
		Button btAdd 			= new Button("Add Product");
		Button btClose 			= new Button("Back");
		ta.setPrefWidth(150);
		ta.setPrefHeight(50);
		ta.setEditable(false);
		ta.setMouseTransparent(true);
		ta.setFocusTraversable(false);

		pane.setAlignment(Pos.CENTER);
		pane.setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
		pane.setHgap(5.5);
		pane.setVgap(5.5);
		pane.setColumnSpan(ta, 2);
		pane.add(new Label("Description:"), 0, 0);
		pane.add(tfDescription, 1, 0);
		pane.add(new Label("Quantity:"), 0, 1); 
		pane.add(tfQuantity, 1, 1);
		pane.add(new Label("Price:"), 0, 2);
		pane.add(tfPrice, 1, 2);
		pane.add(ta, 0, 3);
		pane.add(btAdd, 1, 4);
		pane.add(btClose, 0, 4);
		GridPane.setHalignment(btAdd, HPos.RIGHT);
		
		tfDescription.setOnMouseClicked(e -> ta.clear());
		tfQuantity.setOnMouseClicked(e -> ta.clear());
		tfPrice.setOnMouseClicked(e -> ta.clear());
		
		btAdd.setOnAction(e -> {
			String description 	= tfDescription.getText();
			String quantityStr 	= tfQuantity.getText();
			String priceStr 	= tfPrice.getText();
			tfDescription.clear();
			tfQuantity.clear();
			tfPrice.clear();
			if(Validator.verifyDouble(priceStr) && Validator.verifyInt(quantityStr)){
					double price = Double.parseDouble(priceStr); int quantity = Integer.parseInt(quantityStr);
					if(price > 0 && quantity > 0){
						if(!(machine.containsProduct(price, description)))
							ta.setText(machine.addProduct(new Product(description, price), quantity));
						else
							ta.setText("Product Already In Vending Machine.\nPlease Select \"R)estock\" Option"); 
					}
					else 
						ta.setText("Invaldid Input");
				}
				else 
					ta.setText("Invaldid Input");
		});
		btClose.setOnAction(e -> secondaryStage.hide());
		return pane;						
	}
////////////////////////////////////////////////////////////////////////////////////

	public GridPane getQuitPane(){
		GridPane pane 	= new GridPane();
		Button btQuit 	= new Button("Quit");
		Label l1 		= new Label("Returning Unused Coins:\n" + machine.removeMoney(false) + "\n" +
									"Writing files to memory...\n");
		pane.add(l1,0,0);
		pane.setPrefWidth(320);
		pane.setPrefHeight(175);
		pane.add(btQuit,0,1);
		pane.setColumnSpan(l1, 2);
		pane.setAlignment(Pos.CENTER);
		pane.setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
		pane.setHgap(5.5);
		pane.setVgap(5.5);
		btQuit.setOnAction(e -> Platform.exit());
		return pane;
	}
////////////////////////////////////////////////////////////////////////////////////
	
	public void writeFiles() {
		try{
		Writer.stockToFile("Stock.txt", machine.getStock());
		Writer.coinsToFile("Money.txt", machine.getCoins());
		}catch(IOException e){
			System.err.println("An IOException was caught :"+e.getMessage());
		}
	
	}
////////////////////////////////////////////////////////////////////////////////////
		
}