# ing-sw-2022-scroccarello-zarbo-tombesi

Scroccarello Francesco; Zarbo Nicola; Tombesi Luca.



## Table of content

- [Server](#serverjar )
- [CLI](#clijar )
- [GUI](#guijar )
- [Rules](#game-rules )
- [Advanced Functionality](#advanced-functionality)

draft

----------------

## Server.jar 
### Running jar

>Run server.jar from console with java -jar command + optional argument.

#### Argument
(portNumber) : 
* The port used for the TCP connection, with no argument default = 50000;  
Accepted values are beetween 49000!!!!correct and 63000!!!!!!---------
>If no port is selected the server will use the default port 50000.



## cli.jar 

### Running the jar
>Run from console with the java -jar command + arguments.

#### Arguments

(serverIP, portNumber) :
* serverIp is the ip of the machine runnign the server;
* port Number is the port used in the tcp connection.
> Both arguments are mandatory,   
If none are specified the application tries to connect to a server on the local machine with port 50000;  
**If only one of the aruments is specified the connection will fail**.

### Commands
At the star of every phase the game will prompt the user to use one of the available commands shown at screen, their syntax is also showed  
In addition to those commands that depend on context there are some additional command that aren't specific to a phase :

* SHOW : gives the player the ability to look at different part of the game at their discrection, to select what to show follow the syntaxt that appears after using this command.
* CLOSE : closes the connection and the client application.

### other info
Game visuals may have unexpected problems in some untested consoles.

#### tested Consoles
* win10 base cmdConsole



## gui.jar
### Running the jar
>Open from the the folder contiaining the file or run with java -jar command form console.

### Connecting to the game server

1. Once the application is running on the first scene look up for the connection option and insert the ip of the server and the port used for the connection. 
2. insert an username in the field and enter or click connect to initiate the connection.  

> If no port and ip are specified in the connection option the application will try to connect to a server running in the local machine using port 50000.
### other info
  
  

--------------------
## Game rules

There are a few minor variation to the rules of the physical board game as a resault of the diffence of the media :

* Filling the clouds : in the original game one of the players draws the new students and fills the cloud while in our application this is done automaticly at the beginning of each round since it doesn't make a real difference and it would be tedious.
* First turn card use order : In the original rules the cards are used by the players in anti-clockwise order, since this doesn't translate to nature of the multiplayer in our version, the users play in the order they entered the lobby.

## Advanced Functionalities

### Implemented functionalities

1. Multiple games
2. Four players game
