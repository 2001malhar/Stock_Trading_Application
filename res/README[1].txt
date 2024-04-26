Features :
1. Create Portfolio:
Status: Complete
It is used to create a portfolio with a unique name alphanumeric name. It checks if there are duplicate names & prompts the user accordingly.

2. Add Stock to Portfolio:
Status: Complete
It is used to add stocks to the portfolio by entering the stock symbol and the number of shares. It checks if the portfolio is complete or not and if not, it retrieves stock information from a CSV file and updates the portfolio accordingly.

3. Remove Stock from Portfolio:
Status: Complete
It is used to remove stocks from their portfolio by specifying the stock symbol and the number of shares to remove. It checks if the portfolio is complete or not and if not it checks that the entered number of shares to remove is in range then it removes the shares and adjusts the portfolio accordingly.

4. Display Portfolio:
Status: Complete
It is used to display the composition of their portfolio, including the list of stocks in it, the number of shares for each stock, and the purchase price and date. If there are no stocks it prints a message accordingly.

5. Mark Portfolio as Complete:
Status: Complete
it is used to mark their portfolio as complete, indicating that no further modifications will be made to it. If the function is already complete it prints the message accordingly.

6. Calculate Total Value on Given Date:
Status: Complete
It is used to calculate the total value of their portfolio on a specific date. It retrieves stock prices from CSV files and calculates the total value accordingly.

7. Save Portfolio as CSV:
Status: Complete
it is used to save their portfolio to a CSV file. It writes portfolio information, including stock symbols, shares, purchase prices, and dates, to the CSV file.

8. Load Portfolio from CSV:
Status: Complete
It is used to load a previously saved portfolio from a CSV file. It reads portfolio information from the CSV file and uses the portfolio accordingly.

9. List Symbol:
Status: Complete
It prints out the list of symbols of available stocks that can be added to the portfolio.

10. Composition of portfolio:
Status: Complete
It gives the composition of portfolio along with the profit or loss of portfolio if the portfolio is made on a different date. If portfolio is made on a certain date and if the user views composition on the same date the portfolio will not show the profit or loss. 

11. Handling Valid Portfolio Name from user:
Status: Complete
It is used to take a valid portfolio name from the user where the name must be have atleast an alphabet or a number. If the portfolio name is blank or is not alphanumeric it does not allow user to go ahead with the code.

12. Handling Valid number of Shares from user:
Status: Complete
It is used to take a valid number of shares from user where the user cannot add negative number or 0 as a number of shares. It will not allow user to move forward with the program.

13. Handling Stock symbol input from user:
Status: Complete
It is used to take a valid stock symbol from user should enter value in it. It will not allow user to move forward with the program if it is empty.

14. Total Value of Portfolio on a given Date:
Status: Complete
It is used to take a valid date in (YYYY-MM-DD) format from user and it caluculates the total value of the portfolio on that date. It gives 0 if we enter date before purchase date.

15. List Date;
Status: Complete
It prints out the dates on which the value of the stock is available.

16. Enter Price Manually:
Status: Complete
It allows users to manually enter Stock price.

17. Provide Portfolio Manually:
Status : Incomplete
It allows user to enter a portfolio manually.


flexible portfolio features:

18. Purchase Stock:
Status : Complete
It allows user to purchase stocks on a specified date and add it to portfolio.

19. Sell Stock:
Status : Complete
It allows user to sell stocks on a specified date. It also restricts user from selling more stocks than purchased and also to sell before purchase date. 

20. Total value invested:
Status : Complete
It allows user to know how much money is invested in the portfolio. It gives 0 if we enter date before purchase date.

21. Determine the performance of stock on a day:
Status : Complete
It shows how the stock has performed on the date. It shows the gain or loss on the day.

22. Determine the performance of stock over a period of time.
Status : Complete
It shows how the stock has performed over the time period. It shows the gain or loss on the day. 

23. Determine x-days moving average.
Status : Complete
It shows the average on the last x days. It shows the gain or loss on the day. 

24. Determine which days are crossovers.
Status : Complete and Incomplete
It shows the crossovers of a stock over a period of time. Shows the buy and sell opportunities. The only exception here is that this will only work if the start date and end date are present in the API call of the stock.

25. Moving crossovers.
Status : Complete and Incomplete
It shows the moving crossovers of a stock over a period of time using the average of x and y days. Shows the buy and sell opportunities.The only exception here is that this will only work if the start date and end date are present in the API call of the stock.


26. Bar chart 
Status : InComplete and Complete
It shows the bar graph of the portfolio between the dates provided by user. It adjust dates weeks and months according to dates provided by user. 
But it takes long time to show the graph for some stocks if we enter dates that are in year 2023 and before.

27. Stock Chart.
Status : Complete 
It shows the stock graph of the portfolio between the dates provided by user. It adjust dates weeks and months according to dates provided by user. 

28.Dollar Cost
status: Complete
you can create the portfolio for the dollar cst either by directly giving any name to a portfolio it will ask for all information like stock symbol,weight,frequency , investment amt,etc.
or load existing portfolio and call the dollar cost or create a flexible portfolio and call. the dollar cost in such a case it asks for weight ,amt , frequecy. make sure weight is 100. greater or less than 100 percent will not work.

28. Load Dollar Cost
status: Complete
It is used to load the existing dollar cost portfolios, once loaded it will check the date if the end date is in the future then based on the frequency it will update the dollar cost. The exception is the it will not work if the end date is left null. It is persist the portfolio everytime it is been called for the future dates working purpose.

29. list of Dollar cost
status: Complete 
It is used to show the list of Dollar cost portfolios we have. 








GUI features.

-> The GUI part is implemented using various Jframe things like JPanel,JButton,JTextField,JComboBox, JMenuBar,JMenu, JMenuItems & JFileChooser

It contains the following features :

1. Create Portfolio:
Status: Complete
It is used to create a portfolio with a unique name aphanumeric name using GUI. It checks if there are duplicate names & prompts the user accordingly.

2. Purchase Stock:
Status : Complete
It allows user to purchase stocks on a specified date and add it to portfolio using GUI.

3. Sell Stock:
Status : Complete
It allows user to sell stocks on a specified date using GUI. It also restricts user from selling more stocks than purchased and also to sell before purchase date. 

4. Save Portfolio as CSV:
Status: Complete
it is used to save their portfolio to a CSV file using GUI . It writes portfolio information, including stock symbols, shares, purchase prices, and dates, to the CSV file.

5. Load Portfolio from CSV:
Status: Complete
It is used to load a previously saved portfolio from a CSV file using GUI. It reads portfolio information from the CSV file and uses the portfolio accordingly.

6. Calculate Total Value on Given Date:
Status: Complete
It is used to calculate the total value of their portfolio on a specific date using GUI. It retrieves stock prices from CSV files and calculates the total value accordingly.

7. Total value invested:
Status : Complete
It allows user to know how much money is invested in the portfolio using GUI. It gives 0 if we enter date before the purchase date.

8. 28. Dollar Cost Average
Status: Complete and Incomplete
It allows the user to invest in stocks at regular time intervals and in different weights using GUI. The exception is that it will not work if the end date is left null.

9. Determine the performance of the stock on a day:
Status: Complete
It shows how the stock has performed on the date. It shows the gain or loss on the day using GUI.

10. Determine the performance of stock over a period of time.
Status: Complete
It shows how the stock has performed over the time period. It shows the gain or loss on the day using GUI. 

11. Determine the x-days moving average.
Status: Complete
It shows the average on the last x days. It shows the gain or loss on the day using GUI. 

12. Determine which days are crossovers.
Status: Complete and Incomplete
It shows the crossovers of a stock over a period of time. Shows the buy and sell opportunities using GUI. The only exception here is that this will only work if the start date and end date are present in the API call of the stock.

13. Moving crossovers.
Status : Complete and Incomplete
It shows the moving crossovers of a stock over a period of time using the average of x and y days. Shows the buy and sell opportunities using GUI.The only exception here is that this will only work if the start date and end date are present in the API call of the stock.


14. Bar chart 
Status : InComplete and Complete
It shows the bar graph of the portfolio between the dates provided by user using GUI. It adjust dates weeks and months according to dates provided by user. 
But it takes long time to show the graph for some stocks if we enter dates that are in year 2023 and before.

15. Stock Chart.
Status : Complete 
It shows the stock graph of the portfolio between the dates provided by user using GUI. It adjust dates weeks and months according to dates provided by user. 


16. Load Dollar Cost
status: Complete
It is used to load the existing dollar cost portfolios, once loaded it will check the date if the end date is in the future then based on the frequency it will update the dollar cost. 


(important files in program are apitime,snp500,portfolios,historical,dollar. Make sure they are not deleted.)



Dollar cost Example
= option 2
option 10  
give frequency
add stock symbl
add start end date (you can have end date as future date if you want)
etner the investment amount 
you can also create the flexible portfolio and call dollarcost in that case it directly ask for the weights of stock in the portfolio.
in any case it will buy shares for you which will be save
it can be loaded if required through option number 11 in the flexible portfolio. 
in future if the days are greater then the frequency then it will updated the file to the latest 
shares bought informations. 


