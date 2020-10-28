// This file contains material supporting section 3.7 of the textbook:// "Object Oriented Software Engineering" and is issued under the open-source// license found at www.lloseng.com package client;import ocsf.client.*;import common.*;import java.io.*;import java.lang.ArrayIndexOutOfBoundsException;;/** * This class overrides some of the methods defined in the abstract * superclass in order to give more functionality to the client. * * @author Dr Timothy C. Lethbridge * @author Dr Robert Lagani&egrave; * @author Fran&ccedil;ois B&eacute;langer * @version July 2000 */public class ChatClient extends AbstractClient{  //Instance variables **********************************************    /**   * The interface type variable.  It allows the implementation of    * the display method in the client.   */  ChatIF clientUI;     /**   * Hook method called after the connection has been closed.   * The default implementation does nothing. The method   * may be overriden by subclasses to perform special processing   * such as cleaning up and terminating, or attempting to   * reconnect.   */  protected void connectionClosed() {	  clientUI.display("Connection closed correctly");  }  /**   * Hook method called each time an exception is thrown by the   * client's thread that is waiting for messages from the server.   * The method may be overridden by subclasses.   *   * @param exception the exception raised.   */  protected void connectionException(Exception exception) {	  clientUI.display("Lost connection with the server");	  quit();  }    /**   * Hook method called after a connection has been established.   * The default implementation does nothing.   * It may be overridden by subclasses to do anything they wish.   */  protected void connectionEstablished() {	  clientUI.display("Connection established successfully");  }     //Constructors ****************************************************    /**   * Constructs an instance of the chat client.   *   * @param host The server to connect to.   * @param port The port number to connect on.   * @param clientUI The interface type variable.   */    public ChatClient(String host, int port, ChatIF clientUI)     throws IOException   {    super(host, port); //Call the superclass constructor    this.clientUI = clientUI;    openConnection();  }    //Instance methods ************************************************      /**   * This method handles all data that comes in from the server.   *   * @param msg The message from the server.   */  public void handleMessageFromServer(Object msg)   {	  String[] msgSplit = ((String) msg).split(" ");		if(((String) msg).startsWith("#")){			if(((String) msg).startsWith("#quit")){				quit();			}			else if(((String) msg).startsWith("#logoff")) {				try			    {			      closeConnection();			    }			    catch(IOException e) {}			}		}		else {		    clientUI.display(msg.toString());		}  }  /**   * This method handles all data coming from the UI               *   * @param message The message from the UI.       */  public void handleMessageFromClientUI(String message)  {	  // The client enters a command	  if(message.startsWith("#")) {		  String[] msgSplit = message.split(" ");		  if(message.startsWith("#quit")) {			  try			    {			      sendToServer(message);			    }			    catch(IOException e)			    {			      clientUI.display			        ("Could not send message to server.  Terminating client.");			      quit();			    }		  }		  else if(message.startsWith("#logoff")) {			  try			    {			      sendToServer(message);			    }			    catch(IOException e)			    {			      clientUI.display			        ("Could not send message to server.  Terminating client.");			      quit();			    }			}		  else if(message.startsWith("#sethost")) {				if(isConnected()) {					clientUI.display			        ("Can't use this command, you already are connected");				} 				else {					try {						setHost(msgSplit[1]);					}					catch(ArrayIndexOutOfBoundsException e) {						clientUI.display("Please respect the format #sethost <hostname>");					}				}			}		  else if(message.startsWith("#setport")) {				if(isConnected()) {					clientUI.display			        ("Can't use this command, you already are connected");				} 				else {					try {						setPort(Integer.parseInt(msgSplit[1]));						clientUI.display("New port is: " + Integer.parseInt(msgSplit[1]));					}					catch(ArrayIndexOutOfBoundsException e) {						clientUI.display("Please respect the format #setport <port>");					}					catch(NumberFormatException e) {						clientUI.display("Please respect the format #setport <port> (the port is a number)");					}				}			}		  else {			  clientUI.display		        ("Unknown command");		  }	  }	  else 	  {		  try		    {		      sendToServer(message);		    }		    catch(IOException e)		    {		      clientUI.display		        ("Could not send message to server.  Terminating client.");		      quit();		    }	  }      }    /**   * This method terminates the client.   */  public void quit()  {    try    {      closeConnection();    }    catch(IOException e) {}    System.exit(0);  }}//End of ChatClient class