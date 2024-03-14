/**
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text-based adventure game.  
 *
 * @author  Michael Kolling and David J. Barnes
 * @version 1.0 (February 2002)
 */

public class Game {
    private Parser parser;
    private Room currentRoom;

    /**
     * Create the game and initialise its internal map.
     */
    public Game() {
        createRooms();
        parser = new Parser();
    }

    /**upstairs Exit
     * Create all the rooms and link their exits together.
     */
    private void createRooms() {
        Room fuera, conferencias, bar, lab, oficina, cafeteria, gimnasio, bodega, estacionamiento;

        // create the rooms
        fuera = new Room("fuera de la entrada principal de la universidad");
        conferencias = new Room("en una sala de conferencias");
        bar = new Room("en el bar del campus");
        lab = new Room("en un laboratorio de computacion");
        oficina = new Room("en la oficina de administración informatica");
        cafeteria = new Room("en la cafeteria de la universidad");
        gimnasio = new Room("en el gimnasio de la universidad");
        bodega = new Room("en en el bodega universidad");
        estacionamiento = new Room("en el estacionamiento de la universidad");

        // initialise room exits
        fuera.setExits(null, conferencias, lab, bar, cafeteria, estacionamiento);
        conferencias.setExits(null, null, null, fuera, null, null);
        bar.setExits(null, fuera, null, null, null, null);
        lab.setExits(fuera, oficina, null, null, null, null);
        oficina.setExits(null, null, null, lab, null, null);
        cafeteria.setExits(null, gimnasio, null, null, null, fuera);
        gimnasio.setExits(null, null, null, cafeteria, null, null);
        bodega.setExits(null, null, estacionamiento, null, null, null);
        estacionamiento.setExits(bodega, null, null, null, fuera, null);

        //north-east-south-west--up-down 

        currentRoom = fuera;  // start game fuera
    }

    /**
     * Main play routine. Loops until end of play.
     */
    public void play() {
        printWelcome();

        // Enter the main command loop. Here we repeatedly read commands and
        // execute them until the game is over.

        boolean finished = false;
        while (!finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Gracias por jugar. Adios.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome() {
        System.out.println();
        System.out.println("Bienvenido al mundo de Zuul!");
        System.out.println("Escriba help si necesita ayuda.");
        System.out.println();
        System.out.println("Estas " + currentRoom.getDescription());
        System.out.println("Salidas disponibles: north, east, south, west, up, down");
    }

    /**
     * Given a command, process (that is: execute) the command.
     * If this command ends the game, true is returned, otherwise false is
     * returned.
     */
    private boolean processCommand(Command command) {
        boolean wantToQuit = false;

        if (command.isUnknown()) {
            System.out.println("No se a que te refieres...");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("help"))
            printHelp();
        else if (commandWord.equals("go"))
            goRoom(command);
        else if (commandWord.equals("quit"))
            wantToQuit = quit(command);

        return wantToQuit;
    }

    // Implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the
     * command words.
     */
    private void printHelp() {
        System.out.println("Estas perdido. Usted esta solo. tu deambulas");
        System.out.println("alrededor de la universidad.");
        System.out.println();
        System.out.println("Tus palabras de comando son:");
        System.out.println("   go quit help");
    }

    /**
     * Try to go to one direction. If there is an exit, enter
     * the new room, otherwise print an error message.
     */
    private void goRoom(Command command) {
        if (!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go a donde?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = null;
        if (direction.equals("north"))
            nextRoom = currentRoom.northExit;
        else if (direction.equals("east"))
            nextRoom = currentRoom.eastExit;
        else if (direction.equals("south"))
            nextRoom = currentRoom.southExit;
        else if (direction.equals("west"))
            nextRoom = currentRoom.westExit;
        else if (direction.equals("up"))
            nextRoom = currentRoom.upExit;
        else if (direction.equals("down"))
            nextRoom = currentRoom.downExit;

        if (nextRoom == null)
            System.out.println("No hay puerta!");
        else {
            currentRoom = nextRoom;
            System.out.println("Estas " + currentRoom.getDescription());
            System.out.println("Salidas disponibles: north, east, south, west, up, down");
        }
    }

    /**
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game. Return true, if this command
     * quits the game, false otherwise.
     */
    private boolean quit(Command command) {
        if (command.hasSecondWord()) {
            System.out.println("Quit que?");
            return false;
        } else
            return true;  // signal that we want to quit
    }
}
