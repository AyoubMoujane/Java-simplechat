// This file contains material supporting section 3.7 of the textbook:// "Object Oriented Software Engineering" and is issued under the open-source// license found at www.lloseng.com import java.io.*;import java.util.Scanner; import java.util.InputMismatchException;import client.*;import common.*;/** * This class constructs the UI for a chat client.  It implements the * chat interface in order to activate the display() method. * Warning: Some of the code here is cloned in ServerConsole  * * @author Fran&ccedil;ois B&eacute;langer * @author Dr Timothy C. Lethbridge   * @author Dr Robert Lagani&egrave;re * @version July 2000 */public class ClientConsole implements ChatIF {  //Class variables *************************************************    /**   * The default port to connect on.   */  final public static int DEFAULT_PORT = 5555;    //Instance variables **********************************************    /**   * The instance of the client that created this ConsoleChat.   */  ChatClient client;    //Constructors ****************************************************  /**   * Constructs an instance of the ClientConsole UI.   *   * @param host The host to connect to.   * @param port The port to connect on.   */  public ClientConsole(String host, int port)   {    try     {      client= new ChatClient(host, port, this);    }     catch(IOException exception)     {      System.out.println("Error: Can't setup connection!"                + " Terminating client.");      System.exit(1);    }  }    //Instance methods ************************************************    /**   * This method waits for input from the console.  Once it is    * received, it sends it to the client's message handler.   */  public void accept()   {//		  client.sendToServer("#login " + client.getName());    try    {      BufferedReader fromConsole =         new BufferedReader(new InputStreamReader(System.in));      String message;      while (true)       {        message = fromConsole.readLine();        client.handleMessageFromClientUI(message);      }    }     catch (Exception ex)     {      System.out.println        ("Unexpected error while reading from console!");    }  }  /**   * This method overrides the method in the ChatIF interface.  It   * displays a message onto the screen.   *   * @param message The string to be displayed.   */  public void display(String message)   {    System.out.println("> " + message);  }    public void setName(String name) {	  client.setName(name);  }    //Class methods ***************************************************    /**   * This method is responsible for the creation of the Client UI.   *   * @param args[0] The host to connect to.   */  public static void main(String[] args)   {    String host = "";    int port = 0;  //The port number        // Allowing the client to select a port    Scanner scan = new Scanner(System.in);    System.out.println("Please enter a port number, enter 0 to use default port");    try {        int i = scan.nextInt();        if (i == 0) {        	port = DEFAULT_PORT;        } else {            port = i;        }    }    catch(InputMismatchException e) {    	System.out.println("Invalid port number, using default 5555");    	port = DEFAULT_PORT;    }        // Here the client should be connected, we let him enter his name    System.out.println("Please choose a name");    String name;    name=scan.next();    try    {      host = args[0];    }    catch(ArrayIndexOutOfBoundsException e)    {      host = "localhost";    }    ClientConsole chat= new ClientConsole(host, port);    chat.setName(name);    chat.accept();  //Wait for console data  }}//End of ConsoleChat class