import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class DiceGame {

  @SuppressWarnings({ "resource", "rawtypes" })
  public static void main(String[] args) {
    Scanner userInput = new Scanner(System.in);
    String continueGame = "y";
    ArrayList<String> playerNames = new ArrayList<>();
    ArrayList<Double> playerScores = new ArrayList<>();

    System.out.println("Welcome to the Dice Game");
    System.out.println(
        "\nSpecial Inputs:\nundo - undo dice input\nedit - edit current scores\nback - redo the previous players turn (only works within current round)");
    System.out.println("-----\nSetup\n-----");
    System.out.print("What is your money multiplier? (base is $1): ");

    double moneyMultiplier;
    while (true) {
      try {
        moneyMultiplier = userInput.nextDouble();
        break;
      } catch (Exception e) {
        System.out.print("Please enter a number: ");
        userInput.next();
      }
    }

    System.out.print("How many players?: ");

    int numberOfPlayers;
    while (true) {
      try {
        numberOfPlayers = userInput.nextInt();
        break;
      } catch (Exception e) {
        System.out.print("Please enter a whole number: ");
        userInput.next();
      }
    }

    System.out.println("\nEnter the players names: ");

    int i;
    for (i = 0; i < numberOfPlayers; ++i) {
      System.out.print("Name " + (i + 1) + ": ");
      playerNames.add(userInput.next());
    }

    for (i = 0; i < numberOfPlayers; ++i) {
      playerScores.add(0.0);
    }

    System.out.print("\nDo you want to enter scores? ('y' or 'n'): ");

    while (true) {
      String enterScoresChoice = userInput.next();
      if (enterScoresChoice.equals("y") || enterScoresChoice.equals("n")) {
        String playerName;
        if (enterScoresChoice.equals("y")) {
          for (i = 0; i < playerScores.size(); ++i) {
            playerName = playerNames.get(i);
            System.out.print("Please enter the score for " + playerName + ": ");
            playerScores.set(i, userInput.nextDouble());
          }

          System.out.println("Here are the scores in the order that you entered them:\n" + playerScores + "\n");
        }

        System.out.print("Do you want to count your dice manually? ('y' or 'n'): ");

        while (true) {
          String diceManualChoice = userInput.next();
          if (diceManualChoice.equals("y") || diceManualChoice.equals("n")) {
            while (continueGame.equals("y")) {
              i = 1;

              for (int j = 0; j < 5; ++j) {
                ArrayList<ArrayList<Double>> enteredDice = new ArrayList<>();

                for (i = 0; i < numberOfPlayers; ++i) {
                  enteredDice.add(new ArrayList<>());

                  for (j = 0; j < numberOfPlayers; ++j) {
                    enteredDice.get(i).add(0.0);
                  }
                }

                System.out.println("\n-------");
                System.out.println("Round " + i);
                System.out.println("-------\n");

                for (i = 0; i < playerNames.size(); ++i) {
                  for (j = 0; j < numberOfPlayers; ++j) {
                    enteredDice.get(i).set(j, playerScores.get(j));
                  }

                  double gameResult = playRound(i, moneyMultiplier, playerNames.get(i), userInput, numberOfPlayers,
                      diceManualChoice);
                  if (gameResult == -2.01821332189E8) {
                    System.out.println("\n----\nUNDO\n----\n");
                    --i;
                  } else if (gameResult == -2.0182132188E7) {
                    --i;
                    System.out.println("\n---------");
                    System.out.println("Edit Mode");
                    System.out.println("---------\n");
                    System.out.print("Do you want to edit all scores? ('y' or 'n'): ");

                    while (true) {
                      String editAllScoresChoice = userInput.next();
                      if (editAllScoresChoice.equals("y") || editAllScoresChoice.equals("n")) {
                        if (editAllScoresChoice.equals("y")) {
                          for (i = 0; i < playerNames.size(); ++i) {
                            String currentPlayer = playerNames.get(i);
                            System.out.println(currentPlayer + ": $" + playerScores.get(i));
                            playerName = playerNames.get(i);
                            System.out.print("What do you want to add to " + playerName
                                + "'s score?: ");
                            playerScores.set(i, playerScores.get(i) + userInput.nextDouble());
                            currentPlayer = playerNames.get(i);
                            System.out.println(
                                currentPlayer + "'s score is now " + playerScores.get(i) + "\n");
                          }
                        } else {
                          System.out.print(
                              "Please enter the name whose score you want to change: ");

                          while (true) {
                            try {
                              editAllScoresChoice = userInput.next();
                              i = playerNames.indexOf(editAllScoresChoice);
                              break;
                            } catch (Exception e) {
                              System.out.println("Please enter a name in use: ");
                            }
                          }

                          System.out.print("What do you want to add to " + editAllScoresChoice
                              + "'s score? (This will affect all players scores accordingly): ");
                          double scoreChange = userInput.nextDouble();

                          for (int k = 0; k < playerNames.size(); ++k) {
                            if (k == i) {
                              playerScores.set(k, playerScores.get(k) + scoreChange);
                            } else {
                              playerScores.set(k, playerScores.get(k)
                                  + -1.0 * (scoreChange / (double) (numberOfPlayers - 1)));
                            }
                          }

                          System.out.println("Current Scores: ");
                          Iterator currentScoresIterator = playerNames.iterator();

                          while (currentScoresIterator.hasNext()) {
                            String currentPlayerScore = (String) currentScoresIterator.next();
                            System.out.println(
                                currentPlayerScore + ": $" + playerScores.get(playerNames.indexOf(currentPlayerScore)));
                          }

                          System.out.println();
                        }

                        System.out.println("\n------------");
                        System.out.println("Done Editing");
                        System.out.println("------------\n");
                        break;
                      }

                      System.out.print("Please enter 'y' or 'n': ");
                    }
                  } else {
                    String currentPlayerScore;
                    Iterator iterator;
                    if (gameResult == -2.0182132187E7) {
                      System.out.println("\n----\nBack\n----\n");
                      if (i - 1 > -1) {
                        --i;

                        for (j = 0; j < numberOfPlayers; ++j) {
                          playerScores.set(j,
                              enteredDice.get(i).get(j));
                        }
                      }

                      System.out.println("Current Scores: ");
                      iterator = playerNames.iterator();

                      while (iterator.hasNext()) {
                        currentPlayerScore = (String) iterator.next();
                        System.out.println(
                            currentPlayerScore + ": $" + playerScores.get(playerNames.indexOf(currentPlayerScore)));
                      }

                      --i;
                      System.out.println();
                    } else {
                      for (j = 0; j < playerScores.size(); ++j) {
                        if (j == i) {
                          playerScores.set(j,
                              playerScores.get(j) + gameResult * (double) (numberOfPlayers - 1));
                        } else {
                          playerScores.set(j, playerScores.get(j) + -1.0 * gameResult);
                        }
                      }

                      System.out.println("Current Scores: ");
                      iterator = playerNames.iterator();

                      while (iterator.hasNext()) {
                        currentPlayerScore = (String) iterator.next();
                        System.out.println(
                            currentPlayerScore + ": $" + playerScores.get(playerNames.indexOf(currentPlayerScore)));
                      }

                      System.out.println();
                    }
                  }
                }

                ++i;
              }

              System.out.print("Do you want to play again? (Enter 'y' or 'n'): ");

              while (true) {
                continueGame = userInput.next();
                if (continueGame.equals("y") || continueGame.equals("n")) {
                  break;
                }

                System.out.print("Please enter 'y' or 'n': ");
              }
            }

            return;
          }

          System.out.print("Please enter 'y' or 'n': ");
        }
      }

      System.out.print("Please enter 'y' or 'n': ");
    }
  }

  public static double playRound(int currentRound, double moneyMultiplier, String playerName, Scanner userInput,
      int numberOfPlayers, String diceManualChoice) {
    int diceSum = 0;
    double playerScore = 0.0;
    if (diceManualChoice.equals("n")) {
      System.out.println(playerName + ", please enter your dice rolls for round " + currentRound);

      label126: for (int diceRoll = 0; diceRoll < 5; ++diceRoll) {
        System.out.print("Dice " + (diceRoll + 1) + ": ");

        while (true) {
          try {
            while (true) {
              int rollValue = userInput.nextInt();
              if (rollValue > 0 && rollValue < 7) {
                diceSum += rollValue;
                continue label126;
              }

              System.out.print("Please enter a number between 1 and 6: ");
            }
          } catch (Exception e) {
            switch (userInput.next()) {
              case "undo":
                return -2.01821332189E8;
              case "edit":
                return -2.0182132188E7;
              case "back":
                return -2.0182132187E7;
              default:
                System.out.print("Please enter a whole number: ");
            }
          }
        }
      }
    } else {
      System.out.print(playerName + ", Please enter the sum of your dice rolls for round " + currentRound + ": ");

      label142: while (true) {
        try {
          while (true) {
            diceSum = userInput.nextInt();
            if (diceSum > 4 && diceSum < 31) {
              break label142;
            }

            System.out.print("Please enter a number between 5 and 30: ");
          }
        } catch (Exception e) {
          Object userChoice = userInput.next();
          if (userChoice.equals("edit")) {
            return -2.0182132188E7;
          }

          if (userChoice.equals("back")) {
            return -2.0182132187E7;
          }

          System.out.print("Please enter a whole number: ");
        }
      }
    }

    System.out.println("\nYou rolled " + diceSum);
    int outcome;
    if (diceSum <= 17) {
      outcome = 11 - diceSum;
    } else {
      outcome = diceSum - 24;
    }

    System.out.println("Your outcome is " + outcome);
    if (outcome == 0) {
      return 0.0;
    } else {
      System.out.print("\nPlease enter the number of " + Math.abs(outcome) + "'s you got: ");

      int numOfOutcome;
      label103: while (true) {
        try {
          while (true) {
            numOfOutcome = userInput.nextInt();
            if (numOfOutcome > -1) {
              break label103;
            }

            System.out.print("Please enter a number greater or equal to '0': ");
          }
        } catch (Exception e) {
          switch (userInput.next()) {
            case "undo":
              return -2.01821332189E8;
            case "edit":
              return -2.0182132188E7;
            case "back":
              return -2.0182132187E7;
            default:
              System.out.print("Please enter a whole number: ");
              userInput.next();
          }
        }
      }

      if (numOfOutcome > 10) {
        playerScore += (double) (5 * outcome);
        playerScore += (double) (10 * outcome);
        playerScore += (double) ((numOfOutcome - 10) * outcome * 4);
      } else if (numOfOutcome > 5) {
        playerScore += (double) (5 * outcome);
        playerScore += (double) ((numOfOutcome - 5) * outcome * 2);
      } else {
        playerScore += (double) (numOfOutcome * outcome);
      }

      playerScore *= moneyMultiplier;
      double totalScore = playerScore * (double) (numberOfPlayers - 1);
      if (outcome > 0) {
        System.out.println("\nYou gained $" + playerScore + " from everybody else");
        System.out.println("That is $" + totalScore + " in total (:\n");
      } else {
        System.out.println("\nYou lost $" + playerScore + " to everybody else");
        System.out.println("That is $" + totalScore + " in total :(\n");
      }

      return playerScore;
    }
  }
}