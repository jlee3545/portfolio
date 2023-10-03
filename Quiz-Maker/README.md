# Quiz Maker (with Leaderboard)

This version of Quiz Maker integrates a free, external database
from [https://neon.tech/](https://neon.tech/)

## Neon database setup

1. Sign up for free at [https://neon.tech/](https://neon.tech/) and create a new project
2. From the left menu, select SQL Editor
3. In the `database/` folder of this project, open the `database.sql` file
4. Copy the contents of the file into the SQL Editor
5. Click run in the SQL Editor
   1. A success message should be returned
6. Click `Tables` from the left menu and there should be a table called `leaderboard` 

## Connecting the database to this Java project

1. From the Neon dashboard, copy the connection string from the `Connection Details` card
   1. It should look something like this: `postgres://dencee:zzzyyyyyzzzz@ep-broken-flower-28995704.us-west-2.aws.neon.tech/neondb`
2. In IntelliJ, open `src/main/java/dao/NeonDbLeaderboard.java`
3. Replace the empty string in `private final String NEON_CONNECTION_STRING = "";` with your database's connection string from the Neon dashboard (step 1).
   1. **NOTE**: While it is ok to copy your connection into the project for your own testing, an *environment variable* should be used when uploading to the web/cloud, e.g. Github.
      See this article and images below:
      1. [**Adding an Environment Variable in IntelliJ**](https://www.jetbrains.com/help/objc/add-environment-variables-and-program-arguments.html)  
4. Open QuizMaker.java and run the program
   1. A player named Neo should be on the board with a score of 42. Neo was a default player added from `database.sql` when it was run in the database setup.


**Intellij environment variable setup**
![Intellij environment variable setup](intellij-environment-variable.png)

## Complete the Quiz Maker Application

* Implement the `askQuizQuestions` method in `QuizMaker.java`
* Add more questions to the `test_quiz.txt` at the root of this project


## Challenge Features

* Add a date to each entry in the leaderboard
* Create an ERD diagram for the leaderboard table  
* Create a Question table in the database and have this program read those questions from the database instead of the "test_quiz.txt" file
* Track the correctly answered question for each entry in the leaderboard
  * How would that change the ERD diagram?