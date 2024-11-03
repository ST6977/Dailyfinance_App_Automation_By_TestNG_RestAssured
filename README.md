## Project Scenario:
-  Visit the site https://dailyfinance.roadtocareer.net/. Register a new user (e.g. yourvalidgmailuser+1@gmail.com). Assert the congratulations email is received.
-  Now click on the reset password link. Write 2 negative test case and assert. 
-  Now Input valid gmail account you have registered and and click on send reset link button
-  Now retrieve password reset mail from your gmail and set new password
-  Now login with the new password to ensure login successful
-  Add random 2 items and assert 2 items are showing on the item list
-  Now go to user profile and update user gmail with a new gmail
-  Now logout and login with the updated gmail account. Assert that using new email login is successful and using previous email login is failed.
-  Now logout again and login with the admin account. Admin credential must be sent from the terminal securely.
-  Search by the updated gmail and Assert that updated user email is showing on admin dashboard.

  ## Technology Used:
   - Rest Assured
  - Java
  - TestNG
  - Selenium
  - intellij idea
  - Allure

## How to run this project:
 - Clone the project
 - Open the project from IntellIJ,Do Setup
 - Hit this command: gradle clean test -Pemail="admin@test.com" -Ppassword="admin123" to run the suite.

## Allure Report Overview:
![overview2](https://github.com/user-attachments/assets/08e648a3-a2e9-4f31-abd1-05569b1fb473)

## Allure Report Behaviour:
![Behaviour](https://github.com/user-attachments/assets/0c868638-a61c-41ee-a945-edc75c745271)


Full Project Record: [video](https://screenrec.com/share/BSvsCERKgu)


TestCase Link:[Link](https://docs.google.com/spreadsheets/d/1V3VoDhZMRgSHlfrbjrUAm0nvmYCIoSZ7/edit?usp=sharing&ouid=112686928694089024606&rtpof=true&sd=true)


