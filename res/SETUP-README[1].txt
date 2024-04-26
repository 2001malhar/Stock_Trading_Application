Setup-read me text

(Important- When running the jar file it is important that it is run in the same directory where the resource folder is located as it is dependent on the file stored in the resources.)

IMPORTANT

-> whenever save option is used in GUI , user must provide the same name as of the portfolio that are created and worked on the GUI otherwise file may not be save. 
->Sometime  API calls might fail the main call because of the internet connection or any other cause , if that occurs just rerun the program. 








      
To create a portfolio with different stocks and query their value on a specific date, follow these instructions:


Run the JAR file from the command line using the following command in the folder NearComp location:
Copy code
java -jar ("nameOfJar").jar

As the handin server takes only 8MB as the maximum size for the code we have deleted the historical data of the ticker symbol we fetched.
so when you run the jar it will take 5 to 8 minutes to download and store the historical data at the resources for using it in the 
operation of the interface.



Once the program starts, you will see a menu with various options. Here's how to perform the required tasks:

you will see 8 options which are the following :

Option 1 - To operate inflexible portfolio.

Option 2 - To operate flexible portfolio.

Option 3 - To list the ticker symbols supported by the program.

Option 4 - To get the dates on which the value of stock is available.

Option 5 - To get list of features.

Option 6 - If there is a stock which is in AlphaVantage and not in program you can use this option to call that stock. 

Option 7 - Exit

If option 1 is selected then user will see the following options.

Option 1 - To create a new portfolio.

Option 2 - To add stocks to a portfolio. Follow the prompts to enter the portfolio name, stock symbol, and number of shares.

Option 3 - To remove stocks from a portfolio. Follow the prompts to enter the portfolio name, stock symbol, and number of shares to remove.

Option 4 - To mark a portfolio as complete. This indicates that no more stocks will be added or removed from the portfolio.

Option 5 - To view the composition of a portfolio. Follow the prompts to enter the portfolio name.

Option 6 - To save a portfolio as a CSV file. Follow the prompts to enter the portfolio name.

Option 7 - To load a portfolio from a CSV file. Follow the prompts to enter the portfolio name.

Option 8 - To obtain the composition of the portfolio. Follow the steps to enter the portfolio name.

Option 9 - To obtain the total value of a portfolio on a specific date. Follow the prompts to enter the portfolio name and the date in the format yyyy-mm-dd.

Option 10 - To upload a manual file.

Option 11. Follow the steps and enter the symbol for the stock

Option 12. Back to Main menu.

If option 2 is selected then user will see the following options.

Option 1 - To create a new flexible portfolio.

Option 2 - To purchase stocks and add to portfolio. Follow the prompts to enter the portfolio name, stock symbol, and number of shares.

Option 3 - To sell stocks from a portfolio. Follow the prompts to enter the portfolio name, stock symbol, and number of shares to remove.

Option 4 - To calculate money invested in Portfoltio. Follow the prompts to enter the portfolio name, and date.

Option 5 - To view the composition of a portfolio. Follow the prompts to enter the portfolio name.

Option 6 - To save a portfolio as a CSV file. Follow the prompts to enter the portfolio name.

Option 7 - To load a portfolio from a CSV file. Follow the prompts to enter the portfolio name.

Option 8 - To obtain the composition of the portfolio. Follow the steps to enter the portfolio name.

Option 9 - To obtain the total value of a portfolio on a specific date. Follow the prompts to enter the portfolio name and the date in the format yyyy-mm-dd.

Option 10 - To do dollar cost functionality.Follow the steps Give name of Portfolio, frequency in which you want, symbol, percentage of the stock to invest, then when you are finished entering stock give done as a command. Here note that it will throw error if the weights in not equal 100. Then give amount. Then start date and end date.

Option 11 - To load a dollar cost portfolio. Follow the steps and enter the name of Portfolio.

Option 12 - This is to view the dollar cost portfolios.

Option 13 - Go to main menu.


If option 5 is selected then user will see the following options.

Option 1 - Determine the performance of stock on a day. Follow the prompts and enter symbol of stock and date.

Option 2 - . Determine the performance of stock over a period of time. Follow the prompts and enter symbol of stock start and end date.

Option 3 - Determine x-days moving average. Follow the prompts and enter symbol of stock start and days.

Option 4 - Determine which days are crossovers. Follow the prompts and enter symbol of stock start and end date.

Option 5 - Determine Moving crossovers. Follow the prompts and enter symbol of stock start and end date and x-days and y-days .

Option 6 - Bar chart. Follow the prompts and enter name of portfolio start and end date.

Option 7 - To load a portfolio from a CSV file. Follow the prompts and enter symbol of stock start and end date..

Option 8 - Back to Main menu

If option 3 is selected then user will see List of Symbols available.

If option 4 is selected then user will see list of dates on which the symbol is available.

If option 6 is selected then user will be able to get the symbol which is not available in program right now.

To enter the stock with a manual price, select 13. Follow along and enter the portfolio name, stock symbol, number of shares, and the manual price.

To exit the program, select option 14.

GUI functionality:

The GUI is almost interactive but there are some things which are necessary to know.
-> First of all creating or loading a portfolio is necessay other wise the user will not be able to name the portfolio from the drop down in other functions. 

->Next, It is necessary to select the dropdown even if your desired value appears on the dropdown. If user does not select it the program will not work.
Next in the functions of Determine which days are crossovers, Moving Crossovers, InComplete and Complete the start date and end date must be the values on which data is available.

->Next in the dollar cost average the end date must be declared user will not be able to put null in the end date.

->As the GUI is suppose to simplify user experience we have not concocted it with all of our program methods and have tried to keep it as simple as possible. in future we can add more options if required to it.



steps-
 
-> create the portfolio 
->buy stocks 
->you can save it by giving the file name  similar to portfolio 
->you can also load it on the upper left corner you will see option file which allows to save load and exit.


-> for the dollar cost you can create a portfolio then you can add stocks and do passive investment as you required. 
while the text based has both the feature of creating a new portfolio and then using it or using the existing portfolio. but we have restricted it currently while keeping in mind the complexity of GUI. 



