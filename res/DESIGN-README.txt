DESIGN OF THE PROGRAM:

-> The project follows a Model-View-Controller (MVC) architecture to improve maintainability of the program.

Model:
In this program, the Model includes the Portfolio class named as PortfolioModel and Stock. The Model component represents the data and business logic of the application. It contains the functionalities and entities used in it.

It gets its input from controller.

-> PortfolioModel contains constructor of the portfolio which has the following properties:
	name - Name of the portfolio which is a String. 
	stocks - Stocks in the portfolio which is an Object Stock class.
	isComplete - a boolean which represents if the portfolio is complete.

Constructor:
Initializes a PortfolioModel object with the specified name, a list of stocks and sets isComplete to false.

-> PortfolioModel class contain getters and setters for the above properties

-> PortfolioModel includes methods for the followig functionalities:
	addStock(Stock stock): Adds a stock to the portfolio if it's not marked as complete.
	
	ManualAddStock(Stock stock): Adds a stock manually to the portfolio, checking completeness before adding.
	
	removeStock(Stock stock): Removes a stock from the portfolio if it's not marked as complete.
	
	getStocks(): Returns an unmodifiable list of stocks in the portfolio.
	
	markComplete(): Marks the portfolio as complete.
	
	getCurrentPrice(Stock stock): Retrieves the current price of a stock using CSV file data.

	calculateTotalValue(String date): Calculates the total value of the portfolio on a certain date based on stock prices.
	
	saveAsCSV(String filePath): Saves the portfolio as a CSV file with stock details.

	loadFromCSV(String filePath): Loads stock data from a CSV file into the portfolio.
	
	getStockPriceOnDate(String symbol, String date): Retrieves the price of a stock on a specific date.
	
	getStockBySymbol(String symbol): Retrieves a stock from the portfolio based on its symbol.
	
	isComplete(): Returns the value of isComplete.

-> Stock Class contains the following properties:
	symbol - Symbol of stock.
	shares - number of shares of the stock.
	purchasePrice - purchase price of one share. 	
	purchaseDate - date on which stock was purchased.

-> Stock class contain getters and setters for the above properties.

-> Stock class includes methods for the followig functionalities:
	To get Stock price on a Date

-> PortfolioModel2 class extends the portfolio model.

-> PortfolioModel2 contains the following properties:
	radicalValue(String date): Gives the value of stock on the day.
	stockChart(String symbol, String date1, String date2, int scale): It gives the bar chart of the stock.
	calculateTotalInvestment(PortfolioInt portfolio, String date): It gives the total investment of the portfolio.
	BarChart(String startDate, String endDate, int scale): Used to decide the parameters in form of month, week and years.
	volumeShare(String symbol, String date, int shares) : Used to decide the 

-> Features class contains the properties and methods used to display features:

-> It impelments the FeaturesInt.

-> It has the following methods:
	determineGainOrLoss(String symbol, String date) : This method is used to determine the performance of stock on a specific date.
	getPerformanceOverNDays(String symbol, String startDate, String endDate): Method is used to get the performance of the stock over between two dates.
	calculateXDayMovingAverage(String symbol,String startDate, int numberOfDays) : Method used to calculate the average of X days.
	detectCrossovers(String symbol, String startDate, String endDate) : Used to detect the crossovers of the specific period.
	movingCrossOvers(String symbol, String startDate, String endDate, int xDays,int yDays) : Method used to calculate moving crossovers.


  
View:
The View component is responsible for presenting information to the user and handling user interactions. It handles the output of the program.

The view uses Appendable out which represents the output stream where messages and information are displayed. 

Input is not directly handled in the view class. Instead, it relies on input from the controller or other parts of the application.

Output is primarily directed to the Appendable out, which could be configured to write to different output streams as needed.

Constructor: Provides an output stream (Appendable out) to initialize the PortfolioView object.

-> The view contains methods for the followig functionalities :

	displayPortfolioComposition(PortfolioModel portfolio): Used to display the composition of a portfolio, including its name and details of stocks it contains.

	Composition(PortfolioModel portfolio): Used to display the composition of a portfolio along with details such as stock symbol, shares, purchase price, current price, and 							profit/loss.

	displayStockList(): Used to display a list of stock symbols available to user.

	displayStockDateList(String symbol) : Used to display the list of dates on which the stock price is available.

	displayStockDateList(String symbol): Displays a list of dates available for a specific stock symbol from a CSV file.

	Various methods (displayPortfolioMessage, addDisplayMessage, removeDisplayMessage, displayMarkPortfolioComplete, displayLoadCSVMessage, displayPortfolioValue, 	displayPortfolioNotExist, displayMessage, displayPromptMessage): Used to display specific messages related to portfolios, stocks, loading CSV files, etc.

	displayMenuOptions(): Displays the options available to the user to choose.

	stockChart(String Output) : Display method for displaying the stock chart.

	displayFlexiblePortfolioOptions() : This is the display menu function for the Flexible Portfolio.

	displayPortfolioTypes() : This is the display options of the start of the program.

	displayCrossovers(List<String> crossoverOpportunities) : This function is used to display the crossovers.
	
	displayGainOrLossInfo(String gainOrLoss) : This function is used to display whether there is a gain or loss.

	displayBarChart(String barChartOutput) : It is used to display the bar chart.

	displayFeatures() : This is list of functions available for the features.


Error Handling:

The view also handles various error scenarios such as file not found, empty portfolios, insufficient shares, stock not found, and more.

Controller:
The Controller component acts as an intermediary between the Model and View components. It processes user inputs, interacts with the Model to perform operations, and updates the View to reflect changes in the underlying data.

Attributes:
	portfolios: A Map that stores portfolio names as keys and PortfolioModel objects as values.
	flexiblePortfolios :  A Map that stores flexible portfolio names as keys and PortfolioModel objects as values.
	portfolio: An instance of PortfolioInt representing the portfolio.
	portfolioView: An instance of PortfolioView responsible for displaying information to the user.
	scanner: A Scanner object used for user input.
	features : An instance of the features class.
	in: A Readable object for input.

Constructors:
	PortfolioController(PortfolioInt portfolio, PortfolioView view, Readable in): 
	Initializes the portfolios map, sets the portfolio, in, scanner, and portfolioView objects.

It includes methods as follows:
	
	choosePortfolio() : Entry point of the program where we choose which portfolio to operate.

	createPortfolio(): Prompts the user for a portfolio name and creates a new portfolio if the name is valid and not already existing.

	getPortfolio(String portfolioName): Retrieves the portfolio object with the given name.

	start(): Used to create and operate inflexible portfolio.
	
	priceManually(): Allows users to input the stock price manually.

	provideDate(): Displays a list of dates for a given stock symbol.

	providePortfolioManually(): Loads a CSV file manually for portfolio operations.

	performManually(String name, String filepath): Performs operations on the loaded CSV file.
	
	TotalValueOnGivenDate(): Calculates and displays the total value of a portfolio on a given date.
	
	addStockToPortfolio(): User method to add stock to a portfolio.

	removeStockFromPortfolio(): User method to remove stock from a portfolio.

	markPortfolioAsComplete(): Marks a portfolio as complete.

	savePortfolio(): Saves a portfolio as a CSV file.

	loadCSV(String portfolioName): Loads a portfolio from a CSV file.
	
	doesPortfolioExist(String portfolioName): Checks if a portfolio exists.

	getSymb(): Retrieves a list of stock symbols.

	saveCSV(String portfolioName): Saves the portfolio as a CSV file.	
	
	features() : Used to select which feature you want to see.

	flexible() : Used to select operate flexible portfolio.
	
-> It contains private methods which handles the user input and processes the input in the respective model or view. Some of the functionalities are :
	

	validatePortfolioName(String input): Validates the input for a portfolio name.
	
	promptForPortfolioName(): Prompts the user to enter a valid portfolio name.
	
	getValidStockSymbolFromUser(): Prompts the user to enter a valid stock symbol.
	
	getValidStockPriceFromUser(): Prompts the user to enter a valid stock price.

	getValidNumberOfSharesFromUser(): Prompts the user to enter a valid number of shares.

	promptForDate(String promptMessage): Prompts the user to enter a valid date.

	isValidDateFormat(String date): Checks if a date string is in a valid format.

	getValidEndDate(LocalDate startDate, String promptMessage) : Used to get a valid end date from user.

	sellStock() : Used to sell stock and remove it from portfolio using model.

	purchaseStock : Used to purchase stock and add to portfolio using model.

	costOfPortfolio() : Used to calculate the money invested in portfolio.

	displayBarChart() : Used to display the bar chart of portfolio.

	stockChart() : Used to display the bar chart of stock.
	
-> Also it contains helper methods which handles user input and call methods in model and view accordinly.
	
	determineCrossovers() : Helper to determine the crossovers.

	movingCrossovers() : Helper to determine the moving crossovers.

	determineMovingAverage() : Helper to determine the moving average.

	determinePerformanceOnDay() : Helper to determine performance on a day.

	determinePerformanceOverTime() : Helper to determine the performance over time.

	addStockToPortfolio(String portfolioName, String symbol, int shares): Adds shares of a stock to the specified portfolio.
	
	removeStockFromPortfolio(String portfolioName, String symbolToRemove, int sharesToRemove): Removes shares of a stock from the specified portfolio.
	
	getTotalPortfolioValue(String portfolioName, String date): Calculates the total value of a portfolio on a specific date.

	displayPortfolioComposition(String portfolioName): Displays the composition of a portfolio.

	portfolioComposition(String portfolioName): Displays the composition of a portfolio.

	saveAsCSV(String filePath, PortfolioModel portfolio): Saves a portfolio as a CSV file.
	
	loadFromCSV(String filePath): Loads data from a CSV file.
	
	ListSymbol(): Displays a list of stock symbols.

	viewPortfolio(): Displays the composition of a portfolio.

	createPortfolio1(String portfolioName): Creates a new portfolio with the provided name if it doesn't already exist.

-> Error handling: 
The validate methods handles error for invalid inputs. Also File exceptions and I/O exceptions are handled.


->GUI VIew :

The GUI View is where all the code for GUI exist. The GUIView consists of helper methods for the following. It also contains methods to display the values on the GUI named displayOutput
and displayOutputList which displays messages on the screen.

-> GUI Controller : 

This is the controller which handles the GUIView and model. It consists of certain methods which are used to display and make the GUI interactive.

-> Abstract Controller :

This is the abstract controller class which contains the list of portfolios so that it can be accessed using both text and GUI. 


Changes that were made and their justification:



-> We added a new controller classes named GUIController & GUIView which is supposed to controll and handle the operations of the GUI. As GUI is completly different from text based interface we decided to keep it seperate from the other text CLI classes.

->it is better to keep it seperate that makes it more maintainable and robust.

->coupling is reduce by doing so.

-> We added an abstract class named AbstractController which contains the list of the flexible and inflexible portfolios list as we wanted the same list to be accessable from both text and      	from GUI.

-> We added new methods in the PortfolioModelSec to implement the functionality of dollar Cost Average as was asked. We just extened the class and did not change any method thus,following the SOLID principle.

-> We solved the errors which was given in the last assignment.

-> We have made sure many of the flaws which were highlighted in the assignment 5 are been removed.

  




    