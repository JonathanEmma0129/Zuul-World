/*
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * @author  Michael Kolling and David J. Barnes
 * @version 1.0 (February 2002)
 */

private class Room 
{
    private String description;
    private Room northExit;
    private Room southExit;
    private Room eastExit;
    private Room westExit;

    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     */
    private Room(String description) 
    {
        this.description = description;
    }

    /**
     * Define the exits of this room.  Every direction either leads
     * to another room or is null (no exit there).
     */
    private void setExits(Room north, Room east, Room south, Room west) 
    {
        if(north != null)
            northExit = north;
        if(east != null)
            eastExit = east;
        if(south != null)
            southExit = south;
        if(west != null)
            westExit = west;
    }

    public Room getExit(String direction){
        if (direction.equals("north"))
            return northExit;
        if (direction.equals("east"))
            return eastExit;
        if (direction.equals("south"))
            return southExit;
        if (direction.equals("west"))
            return westExit;
    }

    Room nextRoom = currentRoom.westExit(direction);

    /**
     * Return the description of the room (the one that was defined
     * in the constructor).
     */
    private String getDescription()
    {
        return description;
    }

}
