package text;

/* Class: TextBasedRPG
 * Version: 0.1.5
 * Programmer: Jack Kaback
 * Start Date: 04/25/2014
 * Description: "Text based rpg"
 * Revisions: 11/17/2014 by Jack Kaback
 */ 

/*(1) V 0.0.1 added promptPlayed and get instructions
 *(2) V 0.0.15 added primitive movePlayer
 *(3) V 0.0.2 added primitive randomized cites and improved movePlayer
 *(4) V 0.0.3 added assorted int arrays including stats, cities(x/y), inventory and location.
 *(5) V 0.0.35 Started the monster stat randomizer, battles and improved player's stats
 *(6) V 0.0.4 added the coin flipping for luck stat
 *(7) V 0.0.5 added magic spells, names, some casts, and added a magic stat. (magic is currently based on speed)
 *(8) V 0.0.51 added explosion and ice cast information and renamed a spell to check stats rather than to add to one, noticed bug with fireball and explosion
 *(9) V 0.0.52 added some of the code for running, made it so cities can spawn  from -10 to 10 and makes sure that two don't exist at the same spot
 *(10)V 0.0.525 added information to getInventory (collect information) and added the getItemName and useItem
 *(11)V 0.0.53 fixed explosion and added to its information, added to getMonsterStats, minimized stat reduction of defense to 5, at the moment due to a lack of stat building using explosion causes defense to go up
 *(12)V 0.0.54 added some of the instructions, enhanced a failed run to cause you to get lost, added the permanent luck booster for triple crit points in a row which also lowers the monster's luck (minimum of 1)
 *(13)V 0.0.541 added full text for many options, added more information to the instructions, added another prompt for stats based instructions
 *(14)V 0.0.542 added the final boss "sheBeast", randomized it's location and made sure it doesn't exist on a city. Also added some stats for it, made prompts for it being close, and a specially made method for fighting it.
 *(15)V 0.0.55 added the prompt for while in cites to, rest, shop, get quests, or leave. Also fixed issue of monster attacks occurring in a city
 *(16)V 0.0.6 added 'cody' the shoe box to cites, he is fully implemented. Added some questions for determining stats
 *(17)V 0.0.63 Finished the "goat", added in the rest of the run option, added in some items and functionality in combat
 *(18)V 0.0.65 Added in the framework for two more "events", the gypsy, and the random girl/loch ness monster, added in the option to choice what shop you go to. all events(at the moment; cody, the gypsy, loch ness monster) can only occur once
 *(19)V 0.0.651 Started town crier, it WILL tell the events of your fabricated story for a change in karma
 *(21)V 0.0.72 Added credits, added people to the town bar (one drunk who speaks gibberish, one Walter White, one king Arthur.)
 *(22)V 0.1.0 Added monster "AI" 
 *(23)V 0.1.3 Started monster names
 *(24)V 0.1.31 Fixed a bug for magic by monsters added more monsters
 *(25)V 0.1.4 Added Moss people random event
 *(26)V 0.1.41 Made named cities and made max cities 15
 *(27)V 0.1.5 No longer needs to change length for every new monster type. made it easy to call anywhere
 */
import java.util.Scanner;
public class TextbasedRPG {
	//Add reference to red v blue
	//Add random rare axe salesman.
	//Add eggroll weapon, medium stats
	//Make the best weapon bade from dragon bones, and the best shield be made of spider webs, best helmet is the mask of the loch ness monster 
	//add feature to tell at the bartender the events. Allow embellishment for both. Limit to happen once each for crying and bartender stories
	public static void main(String[] args) {
		Scanner keyin = new Scanner(System.in);
		boolean notDone = true;
		boolean played;

		//[0] is level, [1] is health, [2] is magic, [3] is strength, [4] is defense, [5] is luck, [6] is speed , [7] is quests done, [8] is exp, [9] is max health, [10] is karma
		int [] stats = {0,20,50,0,0,0,0,0,0,20,0};

		int [] citesX = new int[15];

		int [] citesY = new int[15];

		int [] sheBeast = new int [2];

		int [] inventory = new int [200];


		int [] equipment = new int [7];
		//0 is the number of the item relating to the sword, 1 is shield, 2 is gauntlets, 3 boots, 4 chest, 5 pants, 6 helmet

		int [] location = {0,0};
		boolean [] magic = new boolean [5];
		magic [4] = true;
		//magic [2] = true;
		inventory [0] = 30;

		while(notDone){
			//playCredits(keyin);
			played = promptPlayed(keyin);
			randomizeCites(citesX, citesY);
			randomizesheBeast(sheBeast, citesX, citesY);

			if(played == true){
				getStats(keyin,stats);
			}

			else if(played == false){
				getInstructions(keyin);
				randomizeStats(stats, keyin);
			}

			movePlayer(stats, equipment, magic, location, citesX, citesY, inventory , sheBeast, keyin);
		}

	}

	public static boolean isNumeric(String checkString) { 
		int iii = 0;

		if (checkString == null) {
			return false;
		}

		for ( ;iii < checkString.length(); iii++) { 			
			if (checkString.charAt(iii) < '0' || checkString.charAt(iii) > '9') { //this checks if the input is only numbers
				return false; 
			}
		}
		return true;
	}

	public static void counter (int max){
		int ii = 0;

		while(true){
			ii++;
			if(ii == max){
				break;
			}
		}			
	}

	public static boolean promptPlayed(Scanner keyin){
		String input;
		while(true){
			System.out.println("Welcome to\n" +
					"Super Turbo Text RPG Extreme Awesome Remix Championship Edition EX Beta Prime\n" + //Fuck you games with exesivly long names
					"Have you played before? (Y/N)");
			input = keyin.nextLine();
			input = input.toLowerCase();

			if(input.equals("") || input.equals(null)){
				System.out.print("Entry is required");
			}
			if(input.equals("y") || input.equals("1") || input.equals("yes")){
				return true;
			}
			else if(input.equals("n") || input.equals("2") || input.equals("no")){
				return false;
			}
			else {
				System.out.println("Entry must be (Y/N)");
			}
		}
	}

	public static void getStats(Scanner keyin, int[] stats){//write stat checker
		String input;
		int temp;
		for(int ii = 0; ii < stats.length; ii++){
			System.out.println("What is the value of stat " + ii);
			input = keyin.nextLine();

			if(isNumeric(input)){
				temp = Integer.parseInt(input);
				stats [ii] = temp;

			}
			else {
				System.out.println("Entry must be numeric");
				ii--; continue;
			}
		}
	}

	public static void getInstructions(Scanner keyin){//TODO
		String input;

		while(true){
			System.out.println("Would you like instructions? (Y/N)");
			input = keyin.nextLine();
			input = input.toLowerCase();

			if(input.equals("") || input.equals(null)){
				System.out.print("Entry is required");
			}
			if(input.equals("y") || input.equals("1") || input.equals("yes")){//finish writing this, add instructions about cities, make stats another prompt

				System.out.println("This is a text based RPG. All most all of the actions can be done by the number pad, the word\n" +
						"or phrase you choose, or through the option that is in parenthises. The goal is to level up enough to slay\n" +
						"the sheBeast on the far end of the world. At any time to can type (INFO) to get information about\n" +
						"your options, what they do, and posible consiquences. Note not all of the chioces you can do\n" +
						"are displayed so look around and some cannot be done twice. The hidden options might have consiquences.");
				while(true){
					System.out.println("Would you like instructions on your stats? ");
					input = keyin.nextLine();
					input = input.toLowerCase();

					if(input.equals("") || input.equals(null)){
						System.out.print("Entry is required");
						continue;
					}
					if(input.equals("y") || input.equals("1") || input.equals("yes")){
						System.out.println( 
								"Your Stats are your level (this increases with battles and compleating quests),\n" +
										"your current health (this can be reduced through battles or increased through spells and\n" +
										"resting), your current mana (used for casting spells), your strength (used for the standard\n" +
										"attack and can be increased through weapons and leveling), your defence (which reduces damage\n" +
										"and can be increased through armor and leveling), your luck (which has amazing effects if is \n" +
								"high)");
						break;
					}
					else if(input.equals("n") || input.equals("2") || input.equals("no")){
						break;
					}
					break;
				}
			}
			else if(input.equals("n") || input.equals("2") || input.equals("no")){
				break;
			}
			else {
				System.out.println("Entry must be (Y/N)");
				continue;
			}
		}
	}

	public static void randomizeCites (int[] X, int [] Y){
		X[0] = 0;
		Y[0] = 0;
		while(true){
			for(int ii = 1; ii < X.length; ii++){

				X[ii] = (int) (Math.random() * 21) - 10;
				Y[ii] = (int) (Math.random() * 21) - 10;
			}

			for(int jj = 0; jj < X.length; jj++){
				for(int ii = 0; ii < X.length; ii++){
					//System.out.println(X[ii] + " " + Y[ii]);
					if (ii == jj) {
						continue;
					}
					if (X[ii] == X [jj] && Y [ii] == Y [jj]){
						X[ii] = (int) (Math.random() * 21) - 10;
						Y[ii] = (int) (Math.random() * 21) - 10;
						jj = 0;
						ii = 0;
					}
					//System.out.println(X[jj] + " " + Y[jj]);
				}
			}
			break;
		}
	}

	public static void randomizeStats(int[] stats, Scanner keyin){ //TODO
		stats[0] = 1;
		String input;
		int[] flipped = new int [10];
		int temp;
		int iii = 0;

		for(int ii = 0; ii < flipped.length; ii++){
			temp = (int) (Math.random() * 2);
			if(temp == 0){
				flipped[ii] = 0;
			}
			else if(temp == 1){
				flipped[ii] = 1;
			}
			else {
				ii--; continue;
			}
		}
		System.out.println("These series of questions will deturmine your stats");
		System.out.println("A coin is flipped 10 times");
		while(iii < flipped.length){
			System.out.println("What is the face of the coin now? (type t/h for every flip)");
			input = keyin.nextLine();
			input = input.toLowerCase();

			if(!input.equals("t") && !input.equals("1") && !input.equals("h") && !input.equals("2") && !input.equals("tails") && !input.equals("heads")){
				System.out.println("Entry must be T/H");
				continue;
			}

			else if(((input.equals("t") || input.equals("1") || input.contains("tails")) && flipped[iii] == 0) ||
					((input.equals("h") || input.equals("2") || input.contains("heads")) && flipped[iii] == 1)){
				stats[5]++;
			}

			iii++;
		}

		if(stats[5] == 0){
			stats[5] = 1;
		}

		System.out.println("Your luck is " + stats[5]);
		while(true){
			System.out.println("You come across a giant, it looks pretty tough.\n" +
					"Do you (a)ttack it, use (m)agic, or (r)un away?");
			input = keyin.nextLine();
			input = input.toLowerCase();

			if(input.equals("") || input.equals(null)){
				System.out.println("You must make a choice.");
				continue;
			}

			else if(input.contains("attack") || input.equals("1") || input.equals("a")){
				stats[3] += 5;
				stats[6] += 2;
				stats[4] += 1;
				break;
			}

			else if(input.contains("magic") || input.contains("use") || input.equals("m") || input.equals("2")){
				stats [6] += 5;
				stats [3] += 3;
				stats [2] += 100;
				break;
			}

			else if(input.contains("run") || input.equals("r") || input.equals("3")){
				stats [6] += 8;
				break;
			}

			else if(input.equals("info")){
				System.out.println("These determine your stats based on your choice.");
				continue;
			}

			else{
				System.out.println("Entry must be A/M/R");
				continue;
			}
		}

		while(true){
			System.out.println("The giant attacks. What do you do?\n" +
					"(B)lock, (d)odge, or cast a protection (s)pell?");
			input = keyin.nextLine();
			input = input.toLowerCase();

			if(input.equals("") || input.equals(null)){
				System.out.println("You must make a choice");
				continue;
			}

			else if(input.contains("block") || input.equals("b") || input.equals("1")){
				stats [4] += 6;
				stats [6] += 2;
				stats [1] += 20;
				stats [9] += 20;
				break;
			}
			else if(input.contains("dodge") || input.equals("d") || input.equals("2")){
				stats [6] += 8;
				stats [5]++;
				stats [1] += 10;
				stats [9] += 10;
				if(stats [5] > 10){
					stats [5] = 10;
				}
				break;				
			}
			else if(input.contains("cast") || input.contains("spell") || input.equals("c") || input.equals("s") || input.equals("3")){
				stats [6] += 8;
				stats [2] += 100;
				stats [1] += 5;
				stats [9] += 5;
				break;
			}
			else if(input.equals("info")){
				System.out.println("These determine your stats based on your choice.");
				continue;
			}
			else {
				System.out.println("Entry must be B/D/C");
			}
		}

		while(true){
			System.out.println("You see a man beeing mugged in an alley.What do you do?\n" +
					"(D)efend the man, (m)ug him too, or (w)alk away?");
			input = keyin.nextLine();
			input = input.toLowerCase();

			if (input.equals(null) || input.equals("")){
				System.out.println("You must make a choice.");
				continue;
			}
			else if(input.equals("1") || input.contains("defend") || input.equals("d")){
				stats [4] += 5;
				stats [3] += 3;
				stats [9] += 20;
				stats [1] += 20;
				stats [10]++;
				break;
			}

			else if(input.contains("mug") || input.equals("m") || input.equals("2")){
				stats [3] += 8;
				stats [9] += 20;
				stats [1] += 20;
				stats [10]--;
				break;
			}

			else if(input.contains("walk") || input.equals("w") || input.equals("3")){
				stats [6] += 8;
				stats [10]--;
				break;
			}

			else if (input.equals("info")){
				System.out.println("These determine your stats based on your choice.");
				continue;
			}

			else {
				System.out.println("Entry must be D/M/W");
			}
		}

		while(true){
			System.out.println("Someone stole your sweetrole. What do you do?\n" +
					"(A)ttack them, (l)et them have it, or (p)oisin a drink you give them?");
			input = keyin.nextLine();
			input = input.toLowerCase();

			if(input.equals("") || input.equals(null)){
				System.out.println("You must make a choice.");
				continue;
			}

			else if(input.equals("a") || input.contains("attack") || input.equals("1")){
				stats [3] += 8;
				stats [9] += 10;
				stats [1] += 10;
				break;
			}

			else if(input.contains("let") || input.contains("give") || input.equals("2")){
				stats [2] += 100;
				stats [10]++;
				stats [5] ++;
				if(stats[5] > 10){
					stats[5] = 10;
				}
				stats [6] += 8;
				break;
			}

			else if (input.contains("poisin") || input.equals("p") || input.equals("3") || input.contains("drink")){

				stats [10] -= 5;
				stats [2] += 200;
				stats [9] += 100;
				stats [1] += 100;
				stats [4] += 8;
				break;	
			}

			else if (input.equals("info")){
				System.out.println("These determine your stats based on your choice.");
				continue;
			}

			else {
				System.out.println("You must make a choice");
			}
		}

		System.out.println("Your stats have been made, you are now ready to begin your journey.\n" +
				"I hope you made the right choices.");
	}

	public static String getCityName (int ii){
		String [] city;
		city = new String [] {"John", "New Mombasa", "Seattle", "Seoul", "Yemen",
				"Vvardenfell", "Los Santos", "Shermer", "Mordor", "Rapture",
				"Podgorica", "Ljubljana", "Elvenwood", "Megaton", "Hanover"};

		return city [ii];
	}

	public static void movePlayer(int [] stats, int [] equipment, boolean [] magic, int [] location, int [] X, int [] Y, int [] inventory, int [] sheBeast, Scanner keyin){//TODO
		String input;
		int battle;
		int scoutGirl;
		String eventStory;
		boolean city  = false;
		//0 is the shoe box, 1 is the gypsy tent woman, 2 is random child, 3 is undefined , 4 is colony of moss people
		boolean [] events = new boolean [5];
		for(int ii = 0; ii < events.length; ii++){
			events[ii] = false;
		}
		int [] quest = new int [5];
		//[0] is x, [1] is y, [2] is xp, [3] is gold, [4] is item
		while(true){

			city = false;
			for(int ii = 0; ii < X.length; ii++){
				if(X[ii] == location[0] && Y[ii] == location[1]){
					System.out.println("You're in the city " + getCityName(ii));
					city = true; 
					continue;
				}
				else if(X[ii]== location[0] + 1 && Y[ii] == location[1]){
					System.out.println("You can see the city of " + getCityName(ii) + " to the North");
				}
				else if(X[ii]== location[0] - 1 && Y[ii] == location[1]){
					System.out.println("You can see the city of " + getCityName(ii) + " to the South");
				}
				else if(X[ii]== location[0] && Y[ii] == location[1] + 1 ){
					System.out.println("You can see the city of " + getCityName(ii) + " to the East");
				}
				else if(X[ii]== location[0] && Y[ii] == location[1] - 1){
					System.out.println("You can see the city of " + getCityName(ii) + " to the West");
				}
			}
			if (city == true){
				while(true){
					System.out.println("Would you like to got to a shop, rest, ask around, talk to the town crier, or leave?");
					input = keyin.nextLine();
					input = input.toLowerCase();

					if (input.equals("") || input.equals(null)){
						System.out.println("Entry is required");
						continue;
					}

					else if (input.contains("shop") || input.equals("s") || input.equals("1")){
						shop(stats, equipment, inventory, magic, events, keyin);
						continue;
					}

					else  if (input.contains("rest") || input.equals("r") || input.equals("2")){
						if(inventory[0] >= 10){
							stats [1] = stats [9];
							stats [2] += 100;
							inventory [0] -=10;
							System.out.println("You rested comfortably for the night at a hotel.");
						}
						else{
							System.out.println("You need more money to rest at a hotel.");
						}
						continue;
					}

					else if (input.contains("ask") || input.equals("a") || input.equals("3")){
						while(true){
							System.out.println("You wonder into a bar, the bartened asks you what you want.\n" +
									"Ask for advice, see if any one needs work, tip the bartender, talk to the drunks, or leave");
							input = keyin.nextLine();
							input = input.toLowerCase();

							if(input.equals("") || input.equals(null)){
								System.out.println("Entry is required.");
								continue;
							}

							else if((input.contains("ask") || input.contains("advice") || input.contains("1") || 
									input.equals("a") || input.contains("get")) && !input.contains("drink")){
								getAdvice(quest, stats, X , Y, keyin);
								continue;
							}

							else if(input.contains("see") || input.contains("work") || input.contains("2") 
									|| input.equals("w") || input.equals("s")){
								getQuest(quest, stats, X, Y, keyin);
								//if the location matches a city make it a delivery, if not make it a monster fight
								continue;
							}

							else if(input.contains("tip") || input.equals("t") || input.contains("3")){
								if(inventory [0] > 0){
									while(true){
										int money;
										System.out.println("Who much would you like to give him? (" + inventory [0] + ")");
										input = keyin.nextLine();
										input = input.toLowerCase();
										if (input.equals("leave") || input.equals("l")){
											break;
										}
										if(!isNumeric(input)){
											System.out.println("Entry must be numeric or 'leave'");
											continue;
										}

										else{
											money = Integer.parseInt(input);
										}

										if(money > inventory [0]){
											System.out.println("You don't have that much money.");
											continue;
										}

										else if(money == 0){
											System.out.println("You should give the man somethng.");
										}

										else{
											System.out.println("You give the bartender " + money + " gold.\n" +
													"He thanks you and hands you a beer on the house.");
											stats [2] += 10;
											inventory [0] -= money;
											money = money % 5;
											stats [10] += money;
										}

									}
								}

								else{
									System.out.println("You don't have any money.");
								}

							}

							else if(input.equals("steal") ||  input.equals("mug") || input.equals("rob") ||
									input.equals("steal from bartender") || input.equals("mug bartender") || input.equals("rob bartender")){
								boolean called = false;
								stats [10] -= 5;
								if(stats [3] > 20){
									System.out.println("You managed to steal " + (200 * stats [5]) + " gold");	
									inventory [0] += 200 * stats [5];
								}
								else{
									System.out.println("The bartender managed to over power you while they called the guards");
									called = true;
								}
								if(stats[6] < 20 || called){
									System.out.println("You didn't get away from the guards");
									playBattle(stats, magic, inventory, location, keyin, false, true);

									if(stats[1] <= 0){
										return;
									}
								}
							}

							else if(input.contains("talk") || input.equals("4") || input.contains("drunks")){
								talkDrunks(keyin);
							}

							else if(input.equals("leave") || input.equals("l") || input.equals("5")){
								break;
							}

							else if(input.equals("info")){

								System.out.println("You can ask for advice on your quest, see if anyone needs work done,\n" +
										"or tip the bartender.");
								continue;
							}

							else if(input.contains("buy") || input.contains("drink") || input.contains("get")){
								if(inventory [0] < 3){
									System.out.println("You don't have enough money for a drink");
									continue;
								}

								else{
									inventory [0] -= 3;
									inventory [3] += 1;
									System.out.println("You buy a drink, you take a sniff and it seems to be familiar.");
									continue;
								}

							}
						}

						//if the location matches a city make it a delivery, if not make it a monster fight
						continue;
					}

					else if ((input.equals("f") || input.contains("find") || input.contains("cody") || 
							input.equals("c") || input.contains("look") ||
							input.contains("search") || input.contains("box") || input.contains("shoe") || 
							input.contains("ass") || input.contains("prick") || input.contains("cunt")) && !events[0]){
						System.out.println("You come across a strange and bloody shoe box.\n" +
								"This shoe box has a coin slot in it and is making strange noises.");
						boolean givenMoney = false;
						events [0] = true;
						while(true){

							System.out.println("What would you like to do with it?\n" +
									"(P)ut in money, (S)teal from it, (A)ttack it, (L)eave it");
							input = keyin.nextLine();
							input = input.toLowerCase();

							if (input.equals("") || input.equals(null)){
								System.out.println("Entry is required");
								continue;
							}

							else if (input.contains("put") || input.equals("p") || input.contains("money") || 
									input.equals("m") || input.equals("1") || input.contains("give") || input.equals("g")){
								int money;
								while(true){
									System.out.println("How much would you like to give it or would you like to leave it? (" + inventory[0] + ")");
									input = keyin.nextLine();
									input = input.toLowerCase();
									if (input.equals("leave") || input.equals("l")){
										break;
									}
									if (input.equals("info")){
										System.out.println("It is a good idea to give the box at least something.");
										continue;
									}

									if(!isNumeric(input)){
										System.out.println("Entry must be numeric or 'leave'");
										continue;
									}
									money = Integer.parseInt(input);

									if(money > inventory[0]){
										System.out.println("You don't have that much money");
										continue;
									}

									else if (money <= inventory[0]){
										if(money == 0){
											System.out.println("You should give the box something");
											continue;
										}
										System.out.println("You give the shoe box " + money + " gold.\n" +
												"It seems pleased by this.");
										givenMoney = true;
										inventory [0] -= money;
										int temp;
										while(true){
											temp = (int) (Math.random() * money);
											temp++;
											if(temp != 117 && temp < 200){
												break;
											}
										}

										if(stats[5] > 4){
											inventory[temp]++;
											System.out.println("The box opened up and gave you " + getItemName(temp) + " " + temp);
										}
										break;
									}

								}

							}

							else if(input.equals("s") || input.contains("steal") || input.equals("2") ||
									input.contains("rob") || input.contains("take")){
								System.out.println("The box glows red as you try to pry it open");
								if(stats[3] > 100){
									System.out.println("but with your strength you manage to rip it open, and you feel winded but gold flys\n" +
											"all over the place. You manage to get some.");
									inventory [0] += 100;
									stats[6] -= 10;
									if (stats[6] < 5){
										stats [6] = 5;
									}
									if (stats[5] < 5){
										System.out.println("As you were picking up your prize, a poor villager inadvertantly knocks you unconsious.");
										stats[1] -= 20;
										if (stats[1] <= 0){
											return;
										}
									}
									break;
								}
								else {
									System.out.println("The box gets angry you can feel it ooze sticky acid\n" +
											"Your hands get stuck to it. You han feel the burning through\n" +
											"your armor.");
									stats [3] -= 5;
									stats [1] -= (stats [9] * .1);
									stats [9] -= (stats [9] * .1);
									if(stats[2] <= 0){
										System.out.println("As the box takes your life you can feel it consume you.\n" +
												"The last thought that goes through your head is ''How did I let a box kill me?''");
										return;
									}
								}
							}

							else if(input.contains("attack") || input.equals("a") || input.equals("3")){
								if(stats[6] > 100){
									System.out.println("You managed to swing your weapon faster than the box could\n" +
											"cast anything, and in the process ripped it open. But the box was\n" +
											"stronger than you thought, leaving you feeling week.");
									inventory[0] += 100;
									stats [3] -= 5;
									break;
								}

								else{
									System.out.println("As you swing your weapon the box protects itself\n");
									if(stats[3] < 50){
										System.out.println("The box easily over powers you and bashes your weapon across your face");
										stats [1] -= (stats [9] * .5);
										stats [9] -= (stats [9] * .5);
										if(stats [1] <=0){
											System.out.println("This box, this evil box, has killed you.\n" +
													"You don't know how or what, but you know you did something wrong");
											return;
										}
									}
									else if(stats[3] < 75){
										System.out.println("Try as you might, you can't quite over power the box's magic.\n" +
												"The box does push your weapon out of your hand then curses you.");
										stats[5] -= stats[5] * .5;
									}

									else {
										System.out.println("The box pushes back your weapon but you're stronger than it.\n" +
												"The force your blade towards the box and thrust your weapon\n" +
												"through the box. As you do you hear it scream. You regret your decision");
										stats[5] -=2;
										if (stats [5] < 3){
											stats[5] = 3;
										}
									}
								}
							}

							else if(input.equals("l") || input.contains("leave") || input.equals("6")){
								if(!givenMoney){
									System.out.println("As you leave having not given it any money you feel at a loss.\n" +
											"You don't know how or what but something about you doesn't feel right.");
									stats[5] -= stats[5] * .5;
									if(stats[5] < 3){
										stats[5] = 3;
									}
									inventory [0] = 0;
									stats [9]  -= stats[9] * .1;
									if(stats[1] > stats[9]){
										stats[1] = stats[9];
									}
									break;
								}

								else{
									System.out.println("You feel releved, you don't know why but you do.\n" +
											"As you turn the box is glowing a plesant color.");

									stats [5]++;
									if (stats [5] > 10){
										stats [5] = 10;
									}
									stats [9] += stats[9] * .1;
									stats [1] = stats [9];
									stats [3] += stats[3] * .1;

									break;
								}
							}

							else if(input.equals("info")){
								System.out.println("The recommended course of action is to give the box some\n" +
										"money then to leave. Attacking it isn't advised unless you\n" +
										"are strong and fast. Stealing isn't recommend unless you are\n" +
										"really strong. Seriously just give it some money and go away.");
							}
						}
					}

					else if(input.contains("town") || input.contains("crier") || input.contains("talk")
							|| input.equals("4")){
						boolean doneEvent = false;
						for(int ii = 0; ii < events.length; ii++){
							if(events[ii]){
								doneEvent = true;
								break;
							}
						}

						if(!doneEvent){
							System.out.println("I'm sorry sir, it doesn't look like anyone has heard of you.");
						}

						else{
							eventStory = townCrier(events, stats, inventory, keyin);
							System.out.println(eventStory);
						}
					}

					else if (input.contains("leave") || input.equals("l") || input.equals("5")){
						break;
					}

					else if (input.contains("info") || input.equals("i")){
						System.out.println("The shop allowes you to buy things for your inventory or new spells\n" +
								"Resting refills your hp and restores some mana at the cost of 10 gold\n" +
								"Asking around town will get you a quest\n" +
								"Leaving allowes you to continue on your journey");
						continue;
					}

					else{
						System.out.println("Entry must be S/R/A/L");
					}
				}
			}
			else if (city == false){
				scoutGirl = (int) (Math.random() * 1000);
				battle = (int) (Math.random() * 8);

				if(scoutGirl == 666 && !events[2]){

					events[2] = true;

					System.out.println("As you were exploring you came across a little girl near a lake.\n" +
							"She offers you a cookie in exchange for 'tree fiddy'.\n" +
							"You are puzzled as to why a girl of no more than five is out here.\n" +
							"Or why she wants 'tree fiddy' for a cookie.");

					while(true){//TODO
						boolean treeFiddy = false;
						System.out.println("Do you buy the cookie for 'tree fiddy'?");
						input = keyin.nextLine();
						input = input.toLowerCase();

						if (input.equals("") || input.equals(null)){
							System.out.println("You must make a choice.");
							continue;
						}

						else if((input.contains("y") || input.equals("1")) && inventory [0] >= 350){
							System.out.println("You reach into your bag looking for 'tree fiddy'.\n" +
									"Before you can, you look up and see the little girl isn't\n" +
									"really a little girl but is rather 8 stories tall and is the\n" +
									"loch ness monster. It looks down on with its large red glowing\n" +
									"eyes. You timidly ask what it wants. And in a dark brown\n" +
									"voice it says 'I need about tree fiddy.'");
							while (true){
								System.out.println("Are you going to give it 'tree fiddy', attack it, or leave?");	

								input = keyin.nextLine();
								input = input.toLowerCase();

								if(input.equals("") || input.equals(null)){
									System.out.println("You need to make a choice");
									continue;
								}

								else if(input.contains("give") || input.contains("tree fiddy") || input.contains("three fifty") ||
										input.contains("350") || input.equals("1")){
									if(inventory[0] < 350){
										System.out.println("Since you don't have 'tree fiddy' you give him all you've got");
										inventory [0] = 0;
									}

									else{
										inventory [0] -= 350;
										System.out.println("You give the monster 350 and it smiles.");
										treeFiddy = true;
									}
								}

								else if (input.contains("attack") || input.equals("2")){
									playBattle(stats, magic, inventory, location, keyin, true, true);

									if(stats[1] <= 0){
										return;
									}

									System.out.println("After defeating the loch ness monster, you realize it\n" +
											"was only a mask stapled to a log.");
									break;
								}

								else if(input.contains("leave") || input.equals("3")){
									if(treeFiddy){
										stats [5]++;
										if(stats [5] > 10){
											stats[5] = 10;
										}
										stats [3] += 4;
										stats [4] += 4;
									}
								}

							}
							break;
						}

						else if(input.contains("n") || input.equals("2") || inventory [0] < 350){
							if(inventory[0] < 350){
								System.out.println("Since you don't have 'tree fiddy'");
							}
							System.out.println("You politly decline and turn around. As you casually\n" +
									"walk away the girl's neck stretches around to stop you on\n" +
									"your path. It was then you realized she wasn't a little\n" +
									"girl but rather an 8 story tall monster from the Mesozoic\n" +
									"era. You ask the monster what it wants and it replys: 'I\n" +
									"want tree fiddy'");

							while(true){
								System.out.println("Do you attack the monster, or leave");
								if(inventory [0] >= 350){
									System.out.print(", or give it money");
								}

								input = keyin.nextLine();
								input = input.toLowerCase();

								if (input.contains("attack") || input.equals("a") || input.equals("1")){

									playBattle(stats, magic, inventory, location, keyin, true, true);

									if(stats[1] <= 0){
										return;
									}

									System.out.println("After defeating the loch ness monster, you realize it\n" +
											"was only a mask stapled to a log.");
									break;

								}

								else if(input.contains("leave") || input.equals("l") || input.equals("2")){
									break;
								}
							}
						}

						break;

					}
					//TODO
				}

				else if (scoutGirl == 550 && !events[4]){
					events[4] = true;
					mossCulture(stats, inventory, equipment, magic, events, keyin);
				}

				else if (battle == 2){
					playBattle(stats, magic, inventory, location, keyin, false, false);
					if(stats[1] <= 0){
						return;
					}
				}
			}

			if(sheBeast[0] == location[0] && sheBeast[1] == location[1]){
				System.out.println("You found the sheBeast");
				playBattle(stats, magic, inventory, location, keyin, true, false);

				if(stats[1] <= 0){
					return;
				}
			}
			else if(sheBeast[0] == location[0] + 1 && sheBeast [1] == location[1]){
				System.out.println("You can feel the sheBeast's presents to the north");
			}
			else if(sheBeast[0] == location[0] - 1 && sheBeast [1] == location[1]){
				System.out.println("You can feel the sheBeast's presents to the south");
			}
			else if(sheBeast[0] == location[0] && sheBeast [1] == location[1] + 1){
				System.out.println("You can feel the sheBeast's presents to the east");
			}
			else if(sheBeast[0] == location[0] && sheBeast [1] == location[1] - 1){
				System.out.println("You can feel the sheBeast's presents to the west");
			}

			System.out.println("You can move (N)orth, (S)outh, (E)ast, or (W)est");
			input = keyin.nextLine();
			input = input.toLowerCase();

			if ((input.equals("n") || input.equals("1") || input.contains("north")) && 
					!input.contains("east") && !input.contains("west") && !input.contains("south")){
				location[0]++;
				System.out.println("You move North");
			}
			else if ((input.equals("s") || input.equals("2") || input.contains("south")) && 
					!input.contains("east") && !input.contains("north") && !input.contains("west")){
				location[0]--;
				System.out.println("You move south");
			}
			else if ((input.equals("e") || input.equals("3") || input.contains("east"))&& 
					!input.contains("west") && !input.contains("north") && !input.contains("south")){
				location[1]++;
				System.out.println("You move East");
			}
			else if ((input.equals("w") || input.equals("4") || input.contains("west")) && 
					!input.contains("east") && !input.contains("north") && !input.contains("south")){
				location[1]--;
				System.out.println("You move West");
			}
			else if(input.equals("i") || input.equals("info")){
				System.out.println("Here you can move around,  you can move; North, South, East, or West\n" +
						"Cities give you protection and you can learn new skills, get quests, or rest\n" +
						"while you're there.");
				continue;
			}
			else {
				System.out.println("Entry must be N/E/S/W");
				continue;
			}

			//System.out.println(location[0] + "  " + location[1]);

		}
	}

	public static void mossCulture (int [] stats, int [] inventory, int [] equipment, boolean [] magic, boolean [] events, Scanner keyin){//add store for here and make it so they give you max health and heal you
		System.out.println("You encounter some people composed of mostly moss. One of them approches you./n" +
				"/He talks to you.");
		String input;//add pet bearshark to tribe, add option to pet it, named Jennifer 
		boolean bearshark = false;

		while(true){
			System.out.println("'Hola. ¿Cómo éstas?'");
			input = keyin.nextLine();
			input = input.toLowerCase();

			if (input.equals(null) || input.equals("")){
				System.out.println("'¿Qué?'");
				continue;
			}

			else if (input.contains("good") || input.contains("bien") || input.contains("well")){
				System.out.println("'¡Bueno!'");
				break;
			}

			else if (input.contains("bad") || input.contains("mal")){
				System.out.println("'No es bueno.'");
				break;
			}

			else if (input.contains("ok") || input.contains("mas yo menos") || input.contains("así así")){
				System.out.println("Sorry.");
				break;
			}

			else {
				System.out.println("'¿Qué?'");
				continue;
			}
		}

		while(true){
			System.out.println("Would you like to accept our gift to you?");
			input = keyin.nextLine();
			input = input.toLowerCase();

			if (input.equals(null) || input.equals("")){
				System.out.println("Well?");
			}

			else if (input.equals("y") || input.contains("yes") || input.contains("yea") || input.contains("yeah")){
				stats [9] += stats [9] * .1;
				stats [1] = stats [9];
				System.out.println("The group encloses you and they wrap their vines around you.\n" +
						"They start glowing. You wonder what these clorphylic freaks are\n" +
						"doing. After that ordeal is over you feal refreshed.");
				break;
			}

			else if (input.equals("n") || input.contains("no")  || input.contains("nope")){
				System.out.println("The old sage looks displeased by your response.");
				break;
			}
		}

		System.out.println("'Bienvenue sur notre étang. Tu peut rester ici pour aussi\n" +
				"longtemps que tu le suhaite.'");//welcome to our pond. You are welcome to stay as long as you want.

		while(true){
			String bearShark;
			if(bearshark){
				bearShark = "bearshark,";
			}

			else{
				bearShark = "giant thing,";
			}

			System.out.println("You look around the pond and see some shops, a " + bearShark + "\n" +
					"a group of moss people, and a comforting spot next to the pond. Where\n" +
					"would you like to go?");
			input = keyin.nextLine();
			input = input.toLowerCase();

			if (input.equals(null) || input.equals("")){
				System.out.println("You stand there like a fool.");
				continue;
			}

			else if (input.equals("1") || input.equals("s") || input.contains("shop")){
				shop(stats, inventory, equipment, magic, events, keyin);
				continue;
			}

			else if (input.contains("bear") || input.contains("shark") || input.contains("giant") ||
					input.contains("thing") || input.equals("2")){
				mingleBearshark(keyin);
				bearshark = true;
				continue;
			}

			else if (input.equals("3") || input.contains("people") || input.contains("group") || input.contains("moss")){
				mingleMossPeople(keyin);
				continue;
			}

			else if (input.contains("pond") || input.equals("4") || input.equals("p") || input.contains("spot")){
				minglePond(keyin);
			}

			else if (input.equals("leave")){
				break;
			}
		}

		System.out.println("As you leave the moss people bid you fairwell.\n" +
				"As you leave you don't think you'll be able to come back here again.");
	}

	public static void minglePond(Scanner keyin){//TODO
		String input;
		while(true){
			System.out.println("You walk up to the pond and a moss man walks up to you.\n" +
					"You ask him how long the pond has been here. He then looks\n" +
					"you right in the eye and says 'Ce n'est pas un bassin,\n" +
					"il s'agit d'un sandwich. Qu'est-ce qui tu faire croire le contraire?'\n" +
					"Do you point out that it is clearly not a sandwich?");
			//This is not a pond, it is a sandwich. What would make you think otherwise?
			input = keyin.nextLine();
			input = input.toLowerCase();

			if (input.equals(null) || input.equals("")){
				System.out.println("He looks at you with disgust.");
				continue;
			}

			else if (input.equals("y") || input.contains("yes") || input.contains("yea")){
				String gibberish1 = getDrunkWords(20).toUpperCase();
				String gibberish2 = getDrunkWords(20).toUpperCase();

				System.out.println("He looks at you with disgust and shouts'" +
						gibberish1 + "\n" + gibberish2 + "'");
				break;
			}

			else if (input.equals("n") || input.contains("no")){
				String gibberish1 = getDrunkWords(20);
				String gibberish2 = getDrunkWords(20);

				System.out.println("'" + gibberish1 +"\n" +
						gibberish2 + "'");
				break;
			}

			else {
				System.out.println("He looks at you bankly");
			}
		}

		System.out.println("You can tell this man is quite insane");

		while(true){

			System.out.println("Would you like to continue this conversation?");
			input = keyin.nextLine();
			input = input.toLowerCase();

			if (input.equals("") || input.equals(null)){
				System.out.println("He gazes at you with his head tilted.");
				continue;
			}

			else if (input.equals("n") || input.contains("no")){
				System.out.println("You leave the weird man to his insanity.");
				return;
			}

			else if (input.equals("y") || input.contains("yes")){
				System.out.println("You're not sure who's more insane. He or you.");
				break;
			}

			else if (input.equals("info")){
				System.out.println("It might be interesting to talk to him more, but also a bad idea.");
			}

			else {
				System.out.println("He gazes at you with his head tilted wondering what you'll do.");
			}
		}
		boolean [] questions = {false, false, false, false};
		while(true){
			boolean done = false;
			for(int ii = 0; ii <= questions.length; ii++){

				if (questions[ii] = false){
					done = false;
					break;
				}
			}


			if (done) {
				break;
			}
			System.out.println("Would you ask him about? (W)ho he is, what he (d)oes, his (s)tory, why he is (h)ere.");
			input = keyin.nextLine();
			input = input.toLowerCase();

			if (input.equals("") || input.equals(null)){
				System.out.println("You now wonder what you're doing. SAY SOMETHING!");
				continue;
			}

			else if (input.contains("who") || input.equals("1") || input.equals("w")){

				if(questions[0]){
					System.out.println("You already asked who he is and you're scared of his\n" +
							"new answer he might have");
				}

				else {
					questions[0] = true;
					System.out.println("'Yo soy el gran Oso Calabacín.'");//I am the great Bear Zucchini
				}

			}

			else if (input.equals("2") || input.contains("do") || input.equals("d")){

				if(questions[1]){
					System.out.println("You already asked him once and you weren't sure\n" +
							"what he said so you decide to not ask again");
				}

				else {
					questions[1] = true;
					System.out.println(getDrunkWords(15));//what he does is irrelevant
				}
			}

			else if (input.equals("3") || input.equals("s") || input.contains("story")){//TODO

				if (questions[2]){
					System.out.println("You've asked him before and it didn't make sence so you don't ask again.");
				}

				else {//add crazy story
					System.out.println("01001001 00100000 01110111 01100001 01110011 00100000 01100010\n" + //I was born in a barn 62 years ago
							"01101111 01110010 01101110 00100000 01101001 01101110 00100000\n" +
							"01100001 00100000 01100010 01100001 01110010 01101110 00100000\n" +
							"00110110 00110010 00100000 01111001 01100101 01100001 01110010\n" +
							"01110011 00100000 01100001 01100111 01101111. -- / -=-- - --=- =\n" + //I left my house when i was 6
							"/ == =-== / ---- == --= --- - / -== ---- - =- / -- / -== -= --- / =----\n" +
							"Dos años más tarde, a los seis años, me mudé a mi primer apartamento.\n" + //two years later, at 6, I got my 1st apt
							"By the time I was old enough to walk, at age 20, I left my apartment, and here you see me.");
					questions[2] = true;
				}
			}

			else if (input.equals("4") || input.equals("h") || input.contains("here")){

				if (questions[3]){
					System.out.println("You don't want to hear what interpretation of reality he has this time");
				}

				else {//I am the king of the lake and I protect it from fish.
					questions[3] = true;
					System.out.println("Ja sam kralj od jezera I štitim ga od ribe.");
				}
			}

			else if (input.equals("leave")){
				System.out.println("You decided to leave before this man finishes his ramblings.\n" +
						"That's probably a good idea");
				break;
			}
		}

		System.out.println("After having exhausting your questions and only getting\n" +
				"gibberish in return. You decide to leave this insane man\n" +
				"to his ramblings.");
	}

	public static void talkConfident(Scanner keyin){//TODO

	}

	public static void talkMud(Scanner keyin){//TODO

	}

	public static void talkConfused(Scanner keyin){//TODO

	}

	public static void mingleMossPeople (Scanner keyin){
		String input;
		System.out.println("You enter the main area of the moss village, and\n" +
				"you come across a few moss people that look at you\n" +
				"peculiarly. You see three moss people, one standing confidently,\n" +
				"the next is down in the mud, and the last is visibly confused.\n");

		while(true){
			System.out.print("Whom would you like to talk to?");
			input = keyin.nextLine();
			input = input.toLowerCase();

			if(input.equals(null) || input.equals("")){
				System.out.println("They look at you and notice your silence.\n" +
						"They ask you to leave.");
				return;
			}

			else if(input.equals("1") || input.contains("standing") || input.contains("confident")){
				talkConfident(keyin);
				System.out.println("After finishing with him you return to the group");
			}

			else if (input.equals("2") || input.contains("down") || input.contains("mud") || 
					input.contains("next")){
				talkMud(keyin);
				System.out.println("After talking to it you return to the clique");
			}

			else if (input.equals("3") || input.contains("last") || input.contains("confused") || input.contains("visibly")){
				talkConfused(keyin);
				System.out.println("After talking to that thing you return to the circle");
			}

			else if (input.contains("leave")){
				System.out.println("You decide to leave this area.");
				return;
			}
		}
	}

	public static void mingleBearshark (Scanner keyin){//TODO

	}

	public static int getMonsterAttack(int [] player, int [] monster, int health){
		//1 is attack, 2 is magic, 3 is heal
		int ii;
		if(health > 3 * monster[1]){
			ii = (int) (Math.random() * 6);

			if(ii == 3){
				return 3;
			}
			else{

				ii = (int)(Math.random() * 2);

				if(10 * player[1] <= player [9] || ii == 0){
					return 1;
				}

				else {
					return 2;
				}
			}
		}

		else {
			ii = (int)(Math.random() * 2);
			if(10 * player[1] <= player [9] || ii == 0){
				return 1;
			}

			else {
				return 2;
			}
		}
	}

	public static void monsterMove(int [] player, int [] monster, int attack, boolean [] magic, Scanner keyin){//TODO
		//[0] is level, [1] is health, [2] is magic, [3] is strength, [4] is defense, [5] is luck, [6] is speed , [7] is quests done, [8] is exp, [9] is max health, [10] is karma
		//[0] is level, [1] is health, [2] is magic, [3] is strength, [4] is defense, [5] is luck, [6] is speed, [7] is exp, [8] gold given, [9] is item given
		String input;
		int crit;
		int damage;
		double temp;
		temp = (Math.random() * monster [5]);
		temp = temp / player [5];
		if(attack == 1){
			System.out.println("The monster lifts its weapon.");

			while(true){
				System.out.println("Would you like to (b)lock, (d)odge, or use (m)agic to defend yourself?");
				input = keyin.nextLine();
				input = input.toLowerCase();

				if(input.equals("") || input.equals(null)){
					System.out.println("You must make a choice.");
					continue;
				}

				else if(input.contains("block") || input.equals("b") || input.equals("1")){
					crit = (int) (Math.random() * 20) -  monster [5] + player [5];

					damage = (int) ((monster [3] - 2 * player [4]) * temp / player [4]);
					if (damage <= 0){
						damage = 1;
					}

					if (crit <= 1){
						damage += 2 * damage;
						System.out.println("It got a major critical hit");
					}

					else if (crit <= 2){
						damage += damage;
						System.out.println("It got a critical hit");
					}

					player [1] -= damage;
					System.out.println("You were hit for " + damage + " hp");
					System.out.println("You still have " + player[1] + " hp left");

					break;
				}

				else if(input.contains("dodge") || input.equals("2") || input.equals("d")){
					crit = (int) (Math.random() * 20) -  monster [5] + player [5];
					if(player [6] > monster [6]){

						if(player [5] > monster [5]){
							System.out.println("You evaded the attack completly");
							break;
						}

						else if(player [5] == monster [5]){
							damage = (int) ((monster [3] - player [4]) * temp) / 2;

							if(damage <= 0){
								damage = 1;
							}

							if (crit <= 1){
								damage += 2 * damage;
								System.out.println("It got a major critical hit");
							}

							else if (crit <= 2){
								damage += damage;
								System.out.println("It got a critical hit");
							}

							System.out.println("You avoided any major damage but were still hit for " + damage);
							player [1] -= damage;
							break;
						}

						else {
							damage = (int) ((monster [3] - player [4]) * temp);

							if(damage <= 0){
								damage = 1;
							}

							if (crit <= 1){
								damage += 2 * damage;
								System.out.println("It got a major critical hit");
							}

							else if (crit <= 2){
								damage += damage;
								System.out.println("It got a critical hit");
							}

							System.out.println("You couldn't avoid the attack and were hit for " + damage);
							player [1] -= damage;
							break;
						}
					}

					else if(player [6] == monster[6]){

						if(player [5] > monster [5]){
							damage = (int) ((monster [3] - player [4]) * temp) / 2;

							if(damage <= 0){
								damage = 1;
							}

							if (crit <= 1){
								damage += 2 * damage;
								System.out.println("It got a major critical hit");
							}

							else if (crit <= 2){
								damage += damage;
								System.out.println("It got a critical hit");
							}

							System.out.println("You avoided any major damage but were still hit for " + damage);
							player [1] -= damage;
							break;
						}

						else if (player [5] == monster [5]){
							damage = (int) ((monster [3] - player [4]) * temp);

							if(damage <= 0){
								damage = 1;
							}

							if (crit <= 1){
								damage += 2 * damage;
								System.out.println("It got a major critical hit");
							}

							else if (crit <= 2){
								damage += damage;
								System.out.println("It got a critical hit");
							}

							System.out.println("You couldn't avoid the attack and were hit for " + damage);
							player [1] -= damage;
							break;
						}

						else {
							temp = temp * 1.5;
							damage = (int) ((monster [3] - player [4]));

							if(damage <= 0){
								damage = 1;
							}

							if (crit <= 1){
								damage += 2 * damage;
								System.out.println("It got a major critical hit");
							}

							else if (crit <= 2){
								damage += damage;
								System.out.println("It got a critical hit");
							}

							System.out.println("You could barely avoid damage and took " + damage + " damage");
							player [1] -= damage;
							break;
						}
					}

					else {

						if (player [5] > monster [5]){
							damage = (int) ((monster [3] - player [4]) * temp);

							if(damage <= 0){
								damage = 1;
							}

							if (crit <= 1){
								damage += 2 * damage;
								System.out.println("It got a major critical hit");
							}

							else if (crit <= 2){
								damage += damage;
								System.out.println("It got a critical hit");
							}

							System.out.println("You couldn't avoid the attack and were hit for " + damage);
							player [1] -= damage;
							break;
						}

						else if (player [5] == monster [5]){
							temp = temp * 1.5;
							damage = (int) ((monster [3] - player [4]));

							if(damage <= 0){
								damage = 1;
							}

							if (crit <= 1){
								damage += 2 * damage;
								System.out.println("It got a major critical hit");
							}

							else if (crit <= 2){
								damage += damage;
								System.out.println("It got a critical hit");
							}

							System.out.println("You could barely avoid damage and took " + damage + " damage");
							player [1] -= damage;
							break;
						}

						else {
							temp = temp * 2;
							damage = (int) ((monster [3] - player [4]));

							if(damage <= 0){
								damage = 1;
							}

							if (crit <= 1){
								damage += 2 * damage;
								System.out.println("It got a major critical hit");
							}

							else if (crit <= 2){
								damage += damage;
								System.out.println("It got a critical hit");
							}

							System.out.println("You managed to roll into the attack and were hit for " + damage);
							player [1] -= damage;
							break;
						}
					}
				}

				else if (input.contains("magic") || input.equals("m") || input.equals("3")){
					crit = (int) (Math.random() * 20) -  monster [5] + player [5];
					if (player [2] >= 100){
						player [2] -= 100;
						System.out.println("You had enough magic to block all damage.");
						break;
					}

					else if(player [2] > 0){
						int ii = player [2];

						damage = (int) ((monster [3] - player [4]));
						damage = damage * ii;
						damage = damage / 100;

						if(damage <= 0){
							damage = 1;
						}

						if (crit <= 1){
							damage += 2 * damage;
							System.out.println("It got a major critical hit");
						}

						else if (crit <= 2){
							damage += damage;
							System.out.println("It got a critical hit");
						}

						System.out.println("You managed to block " + ii +  "% of the damage\n" +
								"but you still took " + damage + " damage.");
						player [1] -= damage;
						break;
					}

					else {

						damage = (int) ((monster [3] - player [4]));

						if(damage <= 0){
							damage = 1;
						}

						if (crit <= 1){
							damage += 2 * damage;
							System.out.println("It got a major critical hit");
						}

						else if (crit <= 2){
							damage += damage;
							System.out.println("It got a critical hit");
						}

						System.out.println("Since you don't have magic you took the full force of the attack");
						player [1] -= damage;
						break;
					}
				}

				else if(input.contains("info")){
					System.out.println("Blocking will always reduce damage but never remove it.\n" +
							"Dodging can remove all damage, some of it, none of it, or make it worse.\n" +
							"Magic can remove 0 to 100% of the damage, if you have 100 mana you won't be damaged.");
				}

				else {
					damage = (int) ((monster [3] - player [4]) * temp * 3);
					System.out.println("Since you couldn't think straight you got smashed in the face.");
					player [1] -= damage;
					break;
				}
			}
		}

		else if(attack == 2){//TODO
			int ii;

			/*for (ii = 0; ii <= magic.length; ii++){

				if(magic [ii] = false){
					break;
				}
			This didn't work and bugged out
			}*/

			while(true){
				ii = (int) Math.random() * magic.length;
				if(magic[ii]){
					break;
				}
			}
			//castSpell(monster, ii, player);//write a different spell set for monsters
		}

		else {
			System.out.println("The monster healed");
			monster [1] += 10 * monster [0];
		}
	}


	public static String [] monsterNamesPlural(){
		String [] a = new String [] {"the ", "the", "a " , "a ", "a ", 
				"a " , "some " , "a " , "a " , "a " , 
				getDrunkWords(5), "a ", "a " , "a ", "a ",
				"some ", "a ", "A ", "a ", "some "};
		return a;
	}

	public static String [] monsterNames(){
		String [] b = new String []  {"shebeast", "loch ness monster", "guard", "mob of bunnies" , "Gryffin",
				"goblin" , "rotting fish" , "thief", "canibal", "wolf",
				"MISSINGNO", "lost soul", "giant spider", "dragon" , "giant",
				"dwarf on a unicycle", "moss person", "BEARSHARK", "Hobo weilding an eggroll", "mailbox"};
		return b;

	}

	
	public static String monsterName(boolean sheBeast, boolean thief, boolean needBE){
		int ii;
		String [] monsterNamesLength = monsterNames();
		String [] bes = monsterNamesPlural();

		if(sheBeast){
			if(thief){
				ii = 1;//loch
			}

			else {
				ii = 0;//sheBeast
			}
		}

		else if(thief){

			ii = 2;//guard
		}

		else {
			ii = (int) (Math.random() * (monsterNamesLength.length - 3));

			ii += 3;
		}

		if(needBE){
			return bes[ii] + monsterNamesLength[ii];
		}

		else{
			return monsterNamesLength[ii];
		}
	}

	public static void playBattle(int [] player, boolean [] magic, int [] inventory, int [] location, Scanner keyin, boolean sheBeast, boolean theif){//TODO
		String name;
		name = monsterName (sheBeast, theif, false);
		System.out.println("You encountered " + name); 

		//[0] is level, [1] is health, [2] is magic, [3] is strength, [4] is defense, [5] is luck, [6] is speed, [7] is exp, [8] gold given, [9] is item given
		int [] monsterStats = new int [10];
		boolean [] monsterMagic;
		if(!sheBeast && !theif){//normal fight
			getMonsterStats(monsterStats, player);
			monsterMagic = new boolean [5];
			for(int ii = 0; ii < monsterStats[0]; ii++){
				if(ii == 5){
					break;
				}
				monsterMagic[4 - ii] = true;

			}
		}
		else if(sheBeast && !theif){//the final boss
			getsheBeastStats(monsterStats);
			monsterMagic = new boolean [10];
			for(int ii = 0; ii < monsterMagic.length; ii++){
				monsterMagic[ii] = true;
			}
		}

		else if(sheBeast && theif){//the loch ness monster, TODO
			//getLochStats(monsterStats); //write that
			monsterMagic = new boolean [5];
			for(int ii = 0; ii < monsterMagic.length; ii++){
				monsterMagic[ii] = true;
			}
		}

		else if(!sheBeast && theif){//stealing
			//getCopStats(monsterStats); //write that
			monsterMagic = new boolean [5];
			for(int ii = 0; ii < monsterMagic.length; ii++){
				monsterMagic[ii] = true;
			}
		}

		else {
			getMonsterStats(monsterStats, player);
			monsterMagic = new boolean [5];
			for(int ii = 0; ii < monsterStats[0]; ii++){
				if(ii == 5){
					break;
				}
				monsterMagic[4 - ii] = true;
			}
		}
		String input;
		String run = "You got away sussesfully";
		int damage;
		int crit;
		int monsterAttack;
		int monsterHealthStart = monsterStats [1];
		int crits = 0;
		double temp;
		temp = (Math.random() * player[5]);
		temp = temp / monsterStats[5];
		while(true){

			if(monsterStats[1] <= 0){
				System.out.println("You killed the " + name);
				break;
			}

			monsterAttack = getMonsterAttack(player, monsterStats, monsterHealthStart);

			monsterMove(player, monsterStats, monsterAttack, monsterMagic, keyin);

			if(player [1] <= 0){
				System.out.println("You died.");
				break;
			}

			System.out.println("What do you want to do?\n" +
					"(A)ttack, (M)agic, (I)nventory, (R)un");

			input = keyin.nextLine();
			input = input.toLowerCase();

			temp = (int) (Math.random() * player[5]);
			temp = temp / monsterStats[5];

			if (input.equals("a") || input.equals("1") || input.contains("attack")){
				crit = (int) (Math.random() * 20) -  player[5] + monsterStats[5];

				damage = (int) ((player[3] - monsterStats[4]) * temp);
				if (damage <= 0){
					damage = 1;
				}
				if (crit <= 1){
					damage += 2 * damage;
					crits += 2;
					System.out.println("You got a major critical hit");
				}
				else if (crit <= 2){
					damage += damage;
					crits++;
					System.out.println("You got a critical hit");
				}
				else {
					crits = 0;
				}
				if (crits >= 3){
					crits -= 3;
					player [5]++;
					if (player  [5] > 10) {
						player [5] = 10;
					}
					monsterStats [5]--;
					if (monsterStats [5] < 1){
						monsterStats [5] = 1;
					}
				}
				monsterStats[1] -= damage;
				System.out.println("You hit the " + name + " for " + damage + " hp");
				System.out.println("The "+ name + " still has " + monsterStats[1] + " hp left");
				continue;
			}
			else if(input.equals("m") || input.equals("2") || input.contains("magic")){
				while(true){
					String fail = "You need more magic to cast that spell";
					System.out.println("What spell would you like to use? (" + player[2] +")");

					for(int ii = 0; ii < magic.length; ii++){
						if(magic[ii] == true){
							System.out.print((ii+1) + " " + getSpell(ii) + "   ");
						}
					}
					input = keyin.nextLine();
					input = input.toLowerCase(); //finish writting magic
					if((input.equals("1") || input.equals("f") || input.contains("fire")) && magic[0] == true){
						if(player[2] >= 20){
							castSpell(player, 0, monsterStats, name);
							break;
						}
						else {
							System.out.println(fail);
							continue;
						}
					}
					else if((input.equals("2") || input.equals("i") || input.contains("ice")) && magic[1] == true){
						if(player[2] >= 15){
							castSpell(player, 1, monsterStats, name);
							break;
						}
						else{
							System.out.println(fail);
							continue;
						}
					}
					else if((input.equals("3") || input.equals("e") || input.contains("explosion")) && magic[2] == true){
						if(player[2] >= 25){
							castSpell(player, 2, monsterStats, name);
							break;
						}
						else{
							System.out.println(fail);
							continue;
						}
					}
					else if((input.equals("4") || input.equals("s") || input.contains("stat")) && magic[3] == true){
						if(player[2] >= 15){
							castSpell(player, 3, monsterStats, name);
							break;
						}
						else{
							System.out.println(fail);
							continue;
						}
					}
					else if((input.equals("5") || input.equals("h") || input.contains("heal")) && magic[4] == true){
						if(player[2] >= 10){
							castSpell(player, 4, monsterStats, name);
							break;
						}
						else{
							System.out.println(fail);
							continue;
						}
					}
					else if (input.equals("info")){
						System.out.println("The first number is the number to press to use the spell, the second in () is the cost to use\n" +
								"Your mana is dispalyed in ()  next to the prompt" +
								"Fire does a good amout of damage\n" +
								"Ice does a small amount of damage but slowes down the enemy (good for fleeing)\n" +
								"Explosion does less damage than fire but weakens the enemy's defence\n" +
								"Stats checks the enemy stats (not using this once make it free the rest of the fight)\n" +
								"Healing returns 1/10th of your health\n" +
								"type exit to do something other than magic");
						continue;
					}
					else if(input.contains("exit")){
						break;
					}
					else{
						System.out.println("Entry must be F/I/E/S/H");
						continue;
					}
				}
				continue;
			}

			else if(input.equals("i") || input.equals("3") || input.contains("inventory")){
				openInventory(inventory, player, monsterStats, keyin, name);
				continue;
			}

			else if (input.equals("r") || input.equals("4") || input.contains("run")){
				if(player [6] > monsterStats [6] && player [5] > monsterStats [5]){
					System.out.println(run);
					break;
				}

				else if((player [6] >= monsterStats [6] && player [5] <= monsterStats [5]) || 
						(player [6] <= monsterStats [6] && player [5] >= monsterStats [5])){
					System.out.println("Try as you might the monster can't be shaken, and in the\n" +
							"chase you lost where you were going.");
					location[0] = (int) (Math.random() * 21) - 10;
					location[1] = (int) (Math.random() * 21) - 10;
				}

				else if(player [6] <  monsterStats [6] && player [5] < monsterStats [5]){
					System.out.println("You failed to run away, got lost, and took some damage");
					int iii = player [9] / 10;
					location[0] = (int) (Math.random() * 21) - 10;
					location[1] = (int) (Math.random() * 21) - 10;
					player [1] -= iii;
				}
				continue;
			}
			else if(input.equals("info")){
				System.out.println("You can attack the enemy with your weapon and deal damage based on your attack and luck\n" +
						"and the enemy's luck and defence\n" +
						"You can use a spell\n" +
						"You can use something in your inventory\n" +
						"Or if the situation seems unwinable or you don't feel like fighting you can try to run");
				continue;
			}

		}
		if(monsterStats[1] <= 0){
			inventory [0] += monsterStats[8];
			inventory [monsterStats[9]]++;
			player [8] += monsterStats[7];
			while(true){
				double jj = player [0];
				jj = Math.pow(1.5, jj);
				jj = jj * 100;
				Math.round(jj);
				if(player[8]  >= jj){
					player [8] -= jj;
					playerLevel(player, keyin);
				}

				else{
					break;
				}
			}
			if(sheBeast && !theif){
				playCredits(keyin);
			}
		}

	}

	public static void playerLevel(int [] player , Scanner keyin){//TODO
		//[0] is level, [1] is health, [2] is magic, [3] is strength, [4] is defense, [5] is luck
		//[6] is speed , [7] is quests done, [8] is exp, [9] is max health, [10] is karma
		System.out.println("You leveled up.");
		player [0] ++;
		String input;
		String point;
		
		int temp = player [0] % 5;
		
		int ii = 8;
		
		if(temp == 0 || temp == 5){
			ii += 4;
		}
		
		
		while(true){

			if(ii == 0){
				break;
			} 

			else if(ii == 1){
				point = "point";
			}

			else {
				point = "points";
			}

			System.out.println("What would you like to improve? You have (" + ii + ") " + point + " left\n" +
					"(St)rength, (d)efense, (sp)eed, max (h)ealth?");
			input = keyin.nextLine();
			input = input.toLowerCase();

			if(input.equals(null) || input.equals("")){
				System.out.println("Make a choice.");
				continue;
			}

			else if(input.equals("1") || input.contains("strength") || input.equals("st")){
				player [3]++;
				ii--;
			}

			else if(input.equals("2") || input.equals("d") || input.contains("defense")){
				player [4] ++;
				ii--;
			}

			else if(input.equals("3") || input.contains("speed") || input.equals("sp")){
				player [6] ++;
				ii--;
			}

			else if(input.equals("4") || input.contains("health") || input.contains("max") || 
					input.equals("h") || input.equals("m")){
				player [9] += 10;
				ii--;
			}

			else if(input.contains("info")){
				System.out.println("You leveled up, so you get to improve your sklls here.\n" +
						"Strength improves your damage.\n" +
						"Defence reduces the damage done to you." +
						"Speed improves your dodging, running away, and spell casting.\n" +
						"You can place 8 points on skills.");
			}
		}
	}

	public static void getMonsterStats(int [] monster,int [] player){//TODO
		//Monster   [0] is level, [1] is health, [2] is magic, [3] is strength, [4] is defense, 
		//Monster   [5] is luck, [6] is speed, [7] is exp given, [8] gold given, [9] is item given
		//Player    [0] is level, [1] is health, [2] is magic, [3] is strength, [4] is defense, 
		//Player    [5] is luck, [6] is speed , [7] is quests done, [8] is exp, [9] is max health, [10] is karma

		int temp2;
		temp2 = (int) (Math.random()  *  player [5]);
		temp2++;

		monster [0] = player [0];
		monster [1] = (int) (((player [0] * player [0])* player [1] + 1) / (Math.pow(temp2, .5)));

		temp2 = (int) (Math.random()  *  player [4]);
		temp2++;

		monster [2] = player [2] * player [0];

		monster [3] = (int) ((1 + monster [0]) * ((player [3]) / temp2) + 1);

		temp2 = (int) (Math.random()  *  player [4]);
		temp2++;

		monster [4] = player [4] / temp2;

		monster [5] = 10 - player[5];
		if(monster[5] < 3){
			monster[5] = 3;
		}

		temp2 = (int) (Math.random()  *  player [5]) / 2;
		temp2++;

		monster [6] = (monster[5] / temp2)  *  player [4];

		monster [7] = (monster [3] + monster [4] + monster [6]) * (player [5] / monster [5]);
		monster [8] = monster [7] * (player [5] / monster [5]);
	}

	public static String getSpell (int ii){
		String [] spells = {"Fire (20)" , "Ice (15)", "Explosion (25)", "Stat finder (15)" ,"Healing (10)"};
		return spells[ii];
	}

	public static void castSpell(int [] player, int spellNumber, int [] monster, String name){//TODO
		double temp;
		double temp2;
		temp = player [6] * player [0]; 
		if(spellNumber == 0){ //fix
			player[2] -= 20;
			temp2 = monster [4] / player [5] * monster [5];

			temp = temp / temp2;
			temp2 = (Math.random() * player[5]);
			temp2++;
			temp2 = temp2 / monster[5];

			temp = temp * temp2;
			System.out.println("Your fireball did " + temp + " damage");
			monster [1] -= temp;
		}

		else if(spellNumber == 1){
			player [2] -= 15;
			monster [1] -= 5;
			monster [6] = monster [6] / 2;
			System.out.println("Your ice bomb slowed the " + name + " and dealt 5 damage");

		}
		else if(spellNumber == 2){ //fix
			player [2] -= 25;
			monster [4] = monster [4] / 2;
			temp2 = monster [1] / 5;
			temp = temp / monster [6];
			temp = temp * temp2;/*

			player[2] -= 25;
			temp2 = monster [4] / player [5] * monster [5];

			temp = temp / temp2;
			temp2 = (Math.random() * player[5]);
			temp2++;
			temp2 = temp2 / monster[5];

			temp = temp * temp2;*/
			if(monster [4] >= 5){
				System.out.println("Your explosion did " + temp + " damage and lowered the " + name + "'s defence");
			}
			else{
				System.out.println("Your explosion did " + temp + " damage, but it's defence couldn't get weaker");
				monster [4] = 5;
			}
			monster [1] -= temp;
		}
		else if(spellNumber == 3){
			player [2] -= 15;

		}
		else if(spellNumber == 4){
			player [2] -= 10;
			double tempH;
			tempH = .10 * player [9];
			player [1] += tempH;
			if(player [1] > player [9]){
				player [1] = player [9];
			}
		}
	}

	public static void openInventory(int [] inventory, int [] player, int [] monster, Scanner keyin, String name){//TODO
		String item;
		String input;
		int number;
		while(true){
			for(int ii = 0; ii < inventory.length; ii++){
				if(inventory[ii] > 0){
					item = getItemName(ii);
					System.out.println((ii + 1) + " " + item + " (" + inventory [ii] + ")");
				}
			}

			input = keyin.nextLine();
			if(!isNumeric(input)){
				continue;
			}



			number = Integer.parseInt(input);
			number--;
			if(inventory[number] > 0){
				inventory[number]--;
				useItem(number , player, monster, name);
				break;
			}

			else{
				System.out.println("You don't have anything in that slot");
				continue;
			}

		}
	}

	public static String getItemName (int ii){//TODO
		String [] item;
		item = new String []  {"Gold" , "Weak potion" , "Weak mana" ,  "Weak duality potion" , "Standard potion" ,
				"Standard mana" , "Standard duality potion" , "Strong potion" , "Strong mana" , "Strong duality" ,
				"Max potion" , "Max mana" , "Max duality", "Small bomb", "Large bomb",
				"Small Cluster bomb", "Large CLuster bomb"};
		return item [ii];
	}

	public static void useItem(int ii , int [] player, int [] monster, String name){//TODO
		if(ii == 0){
			int temp;
			temp = (int) (Math.random() * 3);
			if (temp == 0){
				System.out.println("You look carfully at your gold and decide to eat it.");
				return;
			}
			else if(temp == 1){
				System.out.println("You look at your money and forget what you're doing.");
				return;
			}
			else if(temp == 2){
				System.out.println("Your money has nothing to do with this fight so you drop the coin.");
				return;
			}
		}
		else if(ii == 1){
			System.out.println("You use a weak potion.");
			player [1] += player [9] * .1;
			if (player [1] > player [9]){
				player [1] = player [9];
			}
			return;
		}

		else if(ii == 2){
			System.out.println("You used a weak mana potion.");
			player [2] += 50;
			return;
		}

		else if(ii == 3){
			System.out.println("You used a weak duality potion.");
			player [2] += 50;
			player [1] += player [9] * .1;
			if (player [1] > player [9]){
				player [1] = player [9];
			}
			return;
		}

		else if(ii == 4){
			System.out.println("You used a standard potion.");
			player [1] += player [9] * .2;
			if (player [1] > player [9]){
				player [1] = player [9];
			}
			return;
		}

		else if(ii == 5){
			System.out.println("You used a standard mana potion.");
			player [2] += 100;
			return;
		}
		else if(ii == 6){
			System.out.println("You used a standard duality potion.");
			player [2] += 100;
			player [1] += player [9] * .2;
			if (player [1] > player [9]){
				player [1] = player [9];
			}
			return;
		}

		else if(ii == 7){
			System.out.println("You used a strong potion");
			player [1] += player [9] * .5;
			if (player [1] > player [9]){
				player [1] = player [9];
			}
			return;
		}

		else if(ii == 8){
			System.out.println("You used a strong mana potion.");
			player [2] += 200;
			return;
		}

		else if(ii == 9){
			System.out.println("You used a strong duality potion");
			player [2] += 200;
			player [1] += player [9] * .5;
			if (player [1] > player [9]){
				player [1] = player [9];
			}
			return;
		}

		else if(ii == 10){
			System.out.println("You used a max potion");
			player [1] = player [9];
			return;
		}

		else if(ii == 11){
			System.out.println("You used a max mana potion");
			player [2] += player [2] + 500;
			return;
		}

		else if(ii == 12){
			System.out.println("You used a max duality potion");
			player [2] += player [2] + 500;
			player [1] = player [9];
			return;
		}

		else if(ii == 13){
			monster [1] -= 100;
			System.out.println("You threw a small bomb at the " + name +". Dealing 100 damage.");//write in the method to accept the monster's name
			return;
		}

		else if(ii == 14){
			monster [1] -= 2000;
			System.out.println("You threw a large bomb. Dealing 2000 damage.");
			return;
		}

		else if (ii == 15){
			monster [1] -= player [5] * 50;
			System.out.println("You threw a small cluster bomb dealing " + (player [5] * 50) + " damage.");
			return;
		}

		else if(ii == 16){
			int jj = player [5] * 500;
			monster [1] -= jj;
			System.out.println("You threw a large cluster bomb dealing " + jj + " damage.");
			return;
		}
	}

	public static void randomizesheBeast(int [] sheBeast, int [] X, int [] Y){
		sheBeast [0] = (int) (Math.random() * 21) - 10;
		sheBeast [1] = (int) (Math.random() * 21) - 10;
		for(int ii = 0; ii < X.length; ii++){
			if((sheBeast[0] == X[ii]) && (sheBeast[1] == Y[ii])){
				sheBeast [0] = (int) (Math.random() * 21) - 10;
				sheBeast [1] = (int) (Math.random() * 21) - 10;
				ii = 0;
			}
		}
	}

	public static void getsheBeastStats(int [] sheBeast){//TODO
		//[0] is level, [1] is health, [2] is magic, [3] is strength, [4] is defense
		//[5] is luck, [6] is speed, [7] is exp, [8] gold given, [9] is item given
		sheBeast [0] = 20;
		sheBeast [1] = 100000000;
		sheBeast [2] = 200000000;
		sheBeast [3] = 500;
		sheBeast [4] = 1000;
		sheBeast [5] = 10;
		sheBeast [6] = 100;
		sheBeast [7] = 1000;
		sheBeast [8] = 10000;
		sheBeast [9] = 117; //let this be some badass weapon or armor


	}

	public static void shop(int [] player, int [] equipment, int [] inventory, boolean [] magic, boolean [] events, Scanner keyin){//TODO
		String input;
		while(true){
			System.out.println("What shop would you like to visit; Blacksmith, spell crafter, apothecary, or tailor?");
			input = keyin.nextLine();
			input = input.toLowerCase();

			if(input.equals("") || input.equals(null)){
				System.out.println("Entry is required.");
				continue;
			}

			else if(input.contains("black") || input.contains("smith") || input.equals("b") || input.equals("1")){
				shopBlackSmith(player, inventory, equipment, keyin);
				continue;

			}

			else if(input.contains("spell") || input.contains("crafter") || input.equals("s") || input.equals("2")){
				shopSpells(magic, player, inventory, keyin);
				continue;

			}

			else if(input.contains("apothecary") || input.contains("potions") || input.equals("3")){
				shopApothecary(inventory, keyin);
				continue;
			}

			else if(input.contains("tailor") || input.equals("4")){
				shopTailor(player, inventory, equipment, keyin);
				continue;
			}

			else if(input.contains("leave") || input.equals("l")){
				break;
			}

			else if(input.contains("info")){
				System.out.println("The blacksmith will sell you weapons and high quality armor.\n" +
						"The spell crafter can sell you new spells and increase the capacity of your spells.\n" +
						"The apothicary can sell you potions.\n" +
						"The tailor can sell you low quality armor.");
				continue;
			}

			else if((input.contains("tent") && input.contains("black")) || input.contains("wonder")
					|| input.contains("explore") || (input.contains("look") || input.contains("around") && input.contains("corner"))){
				if(!events [1]){
					System.out.println("As you meander around the other shops you see an old tent.\n" +
							"You wonder inside and an old gypsy woman greets you. She supprises you\n" +
							"with stories of your past.  You querry you old woman for what she knows.\n" +
							"All she does in reply is blow some dust in your face.");
					player [5]++;
					if(player[5] > 10){
						player [5] = 10;
					}
					player [3] += 5;
					events[1] = true;

					while(true){
						System.out.println("As you recover from the effects of the dust you try to do something.\n" +
								"What do you do? Attack her, ask her a question, pay her for her service, or leave?");
						input = keyin.nextLine();
						input = input.toLowerCase();

						if(input.equals("") || input.equals(null)){
							System.out.println("You must make a choice.");
							continue;
						}

						else if(input.contains("attack")){
							//TODO
						}

						else if(input.contains("ask")){
							//TODO
						}

						else if(input.contains("pay")){
							//TODO
						}

						else if(input.contains("leave")){
							System.out.println("You decide to leave this old hag to her business.");
							break;
						}
					}

					continue;
				}

				else{
					System.out.println("You try looking for that wierd gypsy woman again\n" +
							"but as you look you see a wall where her tent was.");
					continue;
				}
			}

			else{
				System.out.println("You must make a choice");
			}
		}
	}

	public static void getAdvice (int [] quest, int [] player, int [] X, int [] Y, Scanner keyin){//TODO

	}

	public static void getQuest (int [] quest, int [] player, int [] X, int [] Y, Scanner keyin){//TODO

	}

	public static void shopBlackSmith(int [] player, int [] inventory, int [] equip, Scanner keyin){//TODO

	}

	public static void shopSpells(boolean [] magic, int [] player, int [] inventory, Scanner keyin){//TODO
		String input;
		int spellsAvalible = 0;
		System.out.println("An odd gypsy woman offers to teach you a spell\n" +
				"for 20 gold." + " (" + inventory[0] + ")");
		if(inventory[0] >= 20){
			while(true){
				for(int ii = 0; ii < magic.length; ii++){
					if(!magic[ii]){
						spellsAvalible++;
						System.out.println("Would you like to learn: " + getSpell(ii) + " (" + (ii+1) +")");
					}
				}
				if(spellsAvalible == 0){
					System.out.println("She realized she can't teach you anymore spells.");
					break;
				}


				input = keyin.nextLine();
				input = input.toLowerCase();

				if(input.equals(null) || input.equals("")){
					System.out.println("The strange woman stares at you wondering if you can speak.");
				}

				else if(input.contains("info")){
					System.out.println("For 20 gold a spell she'll teach you a new one.\n" +
							"Fire does a good amount of damage.\n" +
							"Ice does a small amount of damage but slows the enemy.\n" +
							"Explosion does a fair amount of damage and weaken armor.\n" +
							"Stats finds the enemy's stats.\n" +
							"Health restores 10% of your health.");
				}

				else if(input.contains("leave")){
					break;
				}

				else if((input.contains("fire") || input.equals("1") || input.equals("f")) 
						&& !magic[0]){
					System.out.println("She shows you how to make fire from your hands\n" +
							"and tells you 'First shalt thou make the Holy Fireball.\n" +
							"Then shalt thou count to three, no more, no less. Three\n" +
							"shall be the number thou shalt count, and the number of\n" +
							"the counting shall be three. Four shalt thou not count,\n" +
							"neither count thou two, excepting that thou then proceed\n" +
							"to three. Five is right out. Once the number three,\n" +
							"being the third number, be reached, then lobbest thou\n" +
							"thy Holy Fireball of Antioch towards thy foe,\n" +
							"who, being naughty in My sight, shall snuff it.'");
					magic[0] = true;
					inventory[0] -= 20;
				}

				else if((input.contains("ice") || input.equals("2") || input.equals("i")) 
						&& !magic[1]){
					System.out.println("She shows you how to freeze a target of your\n" +
							"choosing. You have little to no difficulty doing\n" +
							"same.");
					magic[1] = true;
					inventory[0] -= 20;
				}

				else if((input.contains("explosion") || input.equals("3") || input.equals("e")) 
						&& !magic[2] && magic[0]){
					magic [2] = true;
					System.out.println("She tells you to heat up the fireball until \n" +
							"it's unstable then to toss it.");
				}

				else if((input.contains("explosion") || input.equals("3") || input.equals("e")) 
						&& !magic[2] && !magic[0]){
					System.out.println("She tells you to learn that spell you need to\n" +
							"know another. She offers to teach you both for 30 gold.");
					while(true){
						if(inventory [0] >= 30){
							System.out.println("Would you like to take up her offer?");
							input = keyin.nextLine();
							input = input.toLowerCase();

							if(input.equals(null) || input.equals("")){
								System.out.println("Being silent doesn't work anymore. She knows\n" +
										"you can talk.");
							}

							else if(input.contains("info")){
								System.out.println("This is a good idea to take up this offer.");
							}

							else if(input.equals("y") || input.contains("yes")){
								System.out.println("She shows you how to make fire from your hands\n" +
										"and tells you 'First shalt thou make the Holy Fireball.\n" +
										"Then shalt thou count to three, no more, no less. Three\n" +
										"shall be the number thou shalt count, and the number of\n" +
										"the counting shall be three. Four shalt thou not count,\n" +
										"neither count thou two, excepting that thou then proceed\n" +
										"to three. Five is right out. Once the number three,\n" +
										"being the third number, be reached, then lobbest thou\n" +
										"thy Holy Fireball of Antioch towards thy foe,\n" +
										"who, being naughty in My sight, shall snuff it.'\n" +
										"She then tells you to heat up the fireball until \n" +
										"it's unstable then to toss it.");
								magic [0] = true;
								magic [2] = true;
								inventory  [0] -= 30;
								break;
							}

							else if(input.equals("n") || input.contains("yes")){
								System.out.println("You politly decline her offer.");
								break;
							}
						}

						else{
							System.out.println("You decline because you don't have\n" +
									"enough money.");
							break;
						}
					}
				}

				else if((input.contains("stats") || input.equals("4") || input.equals("s"))
						&& !magic [3]){
					System.out.println("She shows you how to focus your magic\n" +
							"in ypur eyes to see what someone has for stats.");
					magic [3] = true;
					inventory [0] -= 20;
				}

				else if((input.contains("health") || input.contains("heal") || input.equals("5") || 
						input.equals("h")) && !magic[4]){

				}
			}
		}

		else{
			System.out.println("Since you don't have enough money you decline her\n" +
					"offer.");
		}
		//magic = new boolean [magic.length + 1];

	}

	public static void shopApothecary(int [] inventory , Scanner keyin){//TODO

	}

	public static void shopTailor(int [] player, int [] inventory, int [] equip, Scanner keyin){//TODO

	}

	public static String townCrier (boolean [] events, int [] player, int [] inventory, Scanner keyin){//TODO
		String input;
		String theReturn = "Ladies and gentlemen I would like to tell you the tale of a young hero.\n" +
				"Of whom ";
		while(true){
			System.out.println("The town crier asks you for an adventure for you to share for a small  price. (10)");
			if(inventory [0] < 10){
				System.out.println("Since you don't have the funds to pay for him you politly decline;");
				break;
			}
			System.out.println("What one would you like to share");
			if(events [0]){
				System.out.println("Your events with the shoe box of death?");
			}

			if(events[1]){
				System.out.println("The events with the strange gypsy woman?");
			}

			if(events[2]){
				System.out.println("An encounter with the loch ness monster?");
			}

			if(events[3]){
				System.out.println("?");
			}

			if(events[4]){
				System.out.println("The nearly extinct tribe of moss people?");
			}

			input = keyin.nextLine();
			input = input.toLowerCase();

			if(input.equals("") || input.equals(null)){
				System.out.println("You need to choose.");
				continue;
			}

			else if((input.contains("shoe") || input.contains("box") || input.contains("cody") ||
					input.contains("death")) && events[0]){//TODO
				theReturn += "meet an evil cursed box.";
				inventory [0] -= 10;

				while(true){
					System.out.println("The crier asks 'What did you do when you encountered the box?'\n"
							+ "(G)ive it money, (S)teal from it, (A)ttack it, or (L)eft it");
					input = keyin.nextLine();
					input = input.toLowerCase();

					if(input.equals("") || input.equals(null)){
						System.out.println("The crier looks at you and asks you to answer the question.");
					}

					else if(input.contains("gave") || input.contains("give") || input.contains("money") ||
							input.equals("1") || input.equals("g")){
						while(true){
							System.out.println("'How much did you give the strange box?'");
							input = keyin.nextLine();
							if(!isNumeric(input)){
								System.out.println("'I don't believe you gave it " + input + " gold'");
								continue;
							}

							else{
								theReturn += "The adventurer gave the box " + input + " gold.\n" +
										"We can only what wonders the box did for the adventurer.";
								break;
							}
						}
						break;
					}

					else if(input.contains("steal") || input.contains("stole") || input.contains("took") 
							|| input.contains("take") || input.contains("taken")){
						theReturn += "So the adventurer trys his might at the box's strength.\n";
						while(true){
							System.out.println("'Were you strong enough to pry it open?'");
							input = keyin.nextLine();
							input = input.toLowerCase();

							if(input.equals("") || input.equals(null)){
								System.out.println("'Well? Are you awake?'");
								continue;
							}

							else if(input.contains("yes") || input.contains("yea") || input.contains("affermative") || input.contains("indeed")){
								System.out.println("'That must have been remarkably painfull.'");
								theReturn += "And the adventurer with his great strength mmanages to\n" +
										"rip the box to shreads and collects their reward.";
								break;

							}

							else if(input.contains("no") || input.contains("nah") || input.contains("negative")){
								System.out.println("'That must have been remarkably painfull.'");
								theReturn += "And the adventurer fails horrinly. For the box\n" +
										"was just too strong and in its defence oozed acid all\n" +
										"over the adventurer's gauntlets.";
								break;

							}

						}
					}

					else if(input.contains("leave") || input.contains("left") || input.equals("4")){
						theReturn += " Apon meeting the box the adventurer\n" +
								"had passed it off as nothing and had left with a curse and without their wallet.";
						break;
					}
				}
				break;
			}

			else if((input.contains("gypsy") || input.contains("woman") || input.contains("strange")) && events[1]){//TODO
				System.out.println("");
				inventory[0] -= 10;
			}

			else if(events[2] && (input.contains("loch") || input.contains("ness") || input.contains("monster"))){//TODO
				inventory [0] -= 10;
			}

			else if(events[4] && (input.contains("moss")) || input.contains("people") || input.contains("tribe") || input.contains("extinct")){//TODO
				inventory [0] -= 10;
			}
		}
		return theReturn;
	}


	public static void playCredits (Scanner keyin){//write the credits. add timer between parts of credits and require enter to continue, add people's specific inputs to the credits
		System.out.println("You won!");
		keyin.nextLine();
		counter(100000000);
		System.out.println("Programming: Jack and Talons");
		keyin.nextLine();
		counter(100000000);
		System.out.println("Translation:\n" +
				"French: Micheal and Matt C.\n" +
				"Spanish: Jack\n" +
				"German: Rachel");
		keyin.nextLine();
		counter(100000000);
		System.out.println("Ideas:\n" +
				"The Evil shoebox: Cody\n" +
				"Random axe salesman: Dennis ''The Paw'' Salo (he wanted to be known as 'the paw' then complained about the names stuipdity)\n" +//add
				"Quoting the politically correct beggar: Brandon\n" + //add
				"Bunny hordes: Matt P.\n" + 
				"Antique road show, punny axe salesman, make moss people speak multiple languages: Michael");//add
		keyin.nextLine();
		counter(1000000000);
		System.out.println("Testers:\n" +
				"");
	}

	public static void talkDrunks(Scanner keyin){//TODO, add in the coconut scene from monty python
		String input;
		int drinks = 0;
		System.out.println("You pull up to a table full of locals. You see three people sitting\n" +
				"there. One clearly can't handle their alcohol, the next is only a little buzzed,\n" +
				"the last seems odd from the start.");

		while(true){
			System.out.println("Which character would you like to talk to?");
			input = keyin.nextLine();
			input = input.toLowerCase();

			if (input.equals(null) || input.equals("")){
				System.out.println("You should pick.");
			}

			else if(input.contains("info")){
				System.out.println("Each of the three men will give you something\n" +
						"interesting to talk about.");
			}

			else if(input.contains("leave")){
				System.out.println("You get up from the table.");
				return;
			}

			else if(input.contains("can't") || input.contains("alcohol") || input.equals("1") ||
					input.contains("handle")){
				System.out.println("You try to talk to the drunk and he replys\n" + 
						"'" + getDrunkWords(15) + "'");
				while(true){
					System.out.println("You can tell he is clearly drunk. He hands you a\n" +
							"large beer, do you drink it?");
					input = keyin.nextLine();
					input = input.toLowerCase();

					if(input.equals(null) || input.equals("")){
						System.out.println("Well do you?");
					}

					else if(input.contains("yes") || input.equals("y")){
						drinks ++;
						System.out.println("You tell him you are greatful. he replys \n'" 
								+ getDrunkWords(20) + "'");
						break;
					}

					else if(input.contains("no") || input.equals("n")){
						String reply = getDrunkWords(30);
						reply = reply.toUpperCase();
						System.out.println("He gets angry that you don't drink and says\n'"
								+ reply +"'");
						break;
					}

					else if(input.contains("info")){
						System.out.println("Getting drunk could impair your ability to talk and think.");
					}

					else{
						System.out.println("He isn't sure if you want one.");
					}
				}
				String statement;
				if(drinks == 1){
					statement = "Yous try to talks to zee man anth ses what he hath to say.";
				}

				else{
					statement = "You try to talk to the man and see what he has to say.";
				}

				System.out.println(statement);
				System.out.println("'" + getDrunkWords(50) + "'");
				while(true){
					if(drinks == 1){
						statement = "Youv try to athk hims a question.\n";
					}

					else{
						statement = "You try to ask him a question.\n";
					}

					statement += "What he's doing here, what does he do, or what his story is.";

					System.out.println(statement);
					input = keyin.nextLine();
					input = input.toLowerCase();

					System.out.println("'" + getDrunkWords(50) + "\n" +
							getDrunkWords(50) + "\n" + 
							getDrunkWords(60) + "'");
					break;
				}
				System.out.println("After realizing he can't talk you stop talking to him.");
			}

			else if(input.contains("little") || input.contains("buzzed") || input.equals("2")){
				System.out.println("You try to talk to the man. You ask his name and he says\n" +
						"'I ams Walter H" + getDrunkWords(7) + " Blanco but you an call me Walt.'");
				while(true){
					System.out.println("What would you like to ask about Walt.\n" +
							"His occupation, his family, his employees/coworkers.");
					input = keyin.nextLine();
					input = input.toLowerCase();

					if (input.equals(null) || input.equals("")){
						System.out.println("He gives you an evil, cold hearted stare.");
					}

					else if (input.contains("occupation") || input.contains("job") || input.equals("o") || input.equals("1")){
						System.out.println("'I? I work at a laundromat. I used to work at\n" +
								"a car wash and at a high school.'");
					}

					else if (input.contains("family") || input.equals("f") || input.equals("2")){
						System.out.println("I have a son with Cerebral palsy, a wife, a toddler,\n" +
								"a theving sister, and an FBI brother in law; they\n" +
								"all hate me.");
					}

					else if (input.contains("employees") || input.contains("coworkers") || input.equals("e") ||
							input.equals("c") || input.equals("3")){
						System.out.println("'I don't really want to talk about them,'");
					}

					else if(input.contains("leave")){
						break;
					}

					else if(input.contains("info")){
						System.out.println("This man has something sinister about him.");
					}

					else {
						System.out.println("He looks at you with a deathly glare");
					}
				}
			}

			else if (input.contains("odd") || input.contains("start") || input.equals("3")){
				System.out.println("You notice that the odd man is banging two halves of coconut together.");
				while(true){
					System.out.println("Ask him where he got them, ask him why he's using them, ask him who he is.");
					input = keyin.nextLine();
					input = input.toLowerCase();

					if(input.equals(null) || input.equals("")){
						System.out.println("Are you going to ask him something?");
					}

					else if(input.contains("where") || input.equals("1") || input.contains("got")){
						System.out.println("'I found them'\n" +
								"You reply: 'Found them? In Mercia? The coconut's tropical!'\n" +
								"He states: 'What do you mean?'\n" +
								"You say: 'Well, this is a temperate zone'\n" +
								"He disputes: 'The swallow may fly south with the sun or the house martin or the plover may\n" +
								"seek warmer climes in winter, yet these are not strangers to our land?'\n" +
								"You question: 'Are you suggesting coconuts migrate?'\n" +
								"He disputes: 'Not at all. They could be carried.'\n" +
								"You utter: 'What? A swallow carrying a coconut?'\n" +
								"He exclaims 'It could grip it by the husk!'\n" +
								"You remark: 'It's not a question of where he grips it! It's a simple question of weight\n" +
								"ratios! A five ounce bird could not carry a one pound coconut. Listen. In order to\n" +
								"maintain air-speed velocity, a swallow needs to beat its wings forty-three times every second, right?'\n" +
								"He looks puzzled and doesn't reply.");
					}

					else if(input.contains("why") || input.contains("use") || input.contains("using") || input.equals("2")){
						System.out.println("He looks at you puzzled, as if you're the crazy one.");
					}

					else if(input.contains("who") || input.equals("3")){
						System.out.println("The odd man claims: 'It is I, Arthur, son of Uther Pendragon, from the castle\n" +
								"of Camelot. King of the Britons, defeater of the Saxons, Sovereign of all England!'\n" +
								"You dismiss the man as crazy,");//add the conversation for the politially pesant here
					}

					else if(input.contains("info")){
						System.out.println("The man is crazy but nothing will come of asking him questions.");
					}

					else if(input.contains("leave")){
						break;
					}

					else{
						System.out.println("The man looks at you quizzically.");
					}
				}
			}
		}
	}

	public static String getDrunkWords(int characters){
		String words = "";
		int random;
		for(int ii = 1; ii <= characters; ii++){
			random = (int) (Math.random() * 27);
			if(random == 0){words += "a";}
			else if(random == 1){words += "b";}
			else if(random == 2){words += "c";}
			else if(random == 3){words += "d";}
			else if(random == 4){words += "e";}
			else if(random == 5){words += "f";}
			else if(random == 6){words += "g";}
			else if(random == 7){words += "h";}
			else if(random == 8){words += "i";}
			else if(random == 9){words += "j";}
			else if(random == 10){words += "k";}
			else if(random == 11){words += "l";}
			else if(random == 12){words += "m";}
			else if(random == 13){words += "n";}
			else if(random == 14){words += "o";}
			else if(random == 15){words += "p";}
			else if(random == 16){words += "q";}
			else if(random == 17){words += "r";}
			else if(random == 18){words += "s";}
			else if(random == 19){words += "t";}
			else if(random == 20){words += "u";}
			else if(random == 21){words += "v";}
			else if(random == 22){words += "w";}
			else if(random == 23){words += "x";}
			else if(random == 24){words += "y";}
			else if(random == 25){words += "z";}
			else if(random == 26){words += " ";}
		}
		return words;
	}
}