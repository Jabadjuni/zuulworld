package zuulworld;

import java.util.Scanner;

/**
 *
 *
 */
public class Game {

    private final Parser parser;
    private Room currentRoom;
    private Player currentPlayer;
    private Creature currentCreature;
    private NPC currentNPC;

    public Game() {
        createRooms();
        newPlayer();
        parser = new Parser();
    }

    private void newPlayer() {
        Player newPlayer;
        newPlayer = new Player(20, 10);
        currentPlayer = newPlayer;

    }

    private void spawnCreatures() {
        Creature tiger, boss;

        tiger = new Creature("tiger", 20, 5);
        boss = new Creature("boss", 50, 15);

    }

    private void createRooms() {
        Room beach, jungle, river, crash, desert, village, mountain, volcano, tunnel;

        beach = new Room("at the beach where you first washed up", "beach");
        jungle = new Room("in a dense part of the jungle", "jungle");
        river = new Room("by a river in the midst of the jungle. A tiger is sleeping near the bank of the river.", "river");
        crash = new Room("at the site where the plane crashed", "crash");
        desert = new Room("in a desolate desert", "desert");
        village = new Room("at the village of a local tribe", "village");
        mountain = new Room("on the slope of a mountain", "mountain");
        volcano = new Room("inside an volcano LotR-style", "volcano");
        tunnel = new Room("inside a cave", "tunnel");

        beach.setExit("south", jungle);
        beach.setExit("west", crash);

        crash.setExit("east", beach);

        jungle.setExit("north", beach);
        jungle.setExit("west", desert);
        jungle.setExit("east", river);
        jungle.setExit("south", mountain);

        desert.setExit("east", jungle);

        mountain.setExit("north", jungle);
        mountain.setExit("south", volcano);

        volcano.setExit("north", mountain);

        river.setExit("west", jungle);
        river.setExit("south", tunnel);
        river.setExit("east", village);

        tunnel.setExit("north", river);

        village.setExit("west", river);

        currentRoom = beach;
    }

    public void play() {
        printWelcome();

        boolean finished = false;
        while (!finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing. Good bye.");
    }

    private void printWelcome() {
        System.out.println();
        System.out.println("Welcome to the Isle of Zuul!");
        System.out.println("<<<<<<<<<<<<<< Game Explanation >>>>>>>>>>>>");
        System.out.println("Type '" + CommandWord.HELP + "' if you need help.");
        System.out.println();
        System.out.println(currentRoom.getLongDescription());
    }

    private boolean processCommand(Command command) {
        boolean wantToQuit = false;

        CommandWord commandWord = command.getCommandWord();

        if (commandWord == CommandWord.UNKNOWN) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        if (commandWord == CommandWord.HELP) {
            printHelp();
        } else if (commandWord == CommandWord.ATTACK) {
            startAttack(command);
        } else if (commandWord == CommandWord.GO) {
            goRoom(command);
        } else if (commandWord == CommandWord.QUIT) {
            wantToQuit = quit(command);
        }
        else if (commandWord == CommandWord.TALK) {
            startConversation(command);
        }
        
            
        return wantToQuit;
    }

    private void printHelp() {
        System.out.println("You are lost and need to find a way off the island, and preferably soon.");
        System.out.println("You are " + currentRoom.getShortDescription());
        System.out.println();
        System.out.println(currentPlayer.getLife());
        System.out.println(currentPlayer.damageRoll());

        System.out.println("Your command words are:");

        parser.showCommands();
    }

    private void goRoom(Command command) {
        if (!command.hasSecondWord()) {
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("You wander for a while in the direction, but suddenly find yourself back where you started.");
        } else {
            
            currentRoom = nextRoom;
            System.out.println(currentRoom.getLongDescription());
            
            

        }
    }

    private void startAttack(Command command) {
        if (currentRoom.getLocation() == "river") {
            if (!command.hasSecondWord()) {
                System.out.println("Attack what?");
                return;
            }
            String target = command.getSecondWord();
            System.out.println(target);

            if (target.equals("tiger")) {
                Creature tiger;
                tiger = new Creature("tiger", 25, 5);
                currentCreature = tiger;
                System.out.println("Sasdasd");
                System.out.println(currentCreature.getLife());
            }

        } else {
            System.out.println("There is nothing to attack here.");
        }
    }
    
    private void startConversation(Command command) {
        
        if ("beach".equals(currentRoom.getLocation())) {
            if(!command.hasSecondWord()) {
                System.out.println("Talk to what?");
                return;
            }
        
        String target = command.getSecondWord();
        
        if(target.equals("mysterious")) {
            NPC mysterious;

            
            mysterious = new NPC("mysterious man", "Hello");
            currentNPC = mysterious;
            currentNPC.setWelcomeMessage("Hello!");
            System.out.println(currentNPC.getWelcomeMessage());
            currentNPC.setQuestions("Who are you?", "Where am I?", "Can you please help me get off this island?");
            currentNPC.setAnswers("I am god!", "You are on The Island of Zuul!", "You must help yourself get off this island. The village in the east will be able to help you!");
            
            currentNPC.returnQuestions();
            
            while(currentNPC.chosenAnswer!=4){
            System.out.println(currentNPC.switchAnswers());   
            }
        }
        
    }
    }

    private boolean quit(Command command) {
        if (command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        } else {
            return true;
        }
    }

}
