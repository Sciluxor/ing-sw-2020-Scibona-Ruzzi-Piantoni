# Santorini
<img src="https://github.com/Sciluxor/ing-sw-2020-Scibona-Ruzzi-Piantoni/blob/master/wiki-assets/gamebox.png" width="400" alt="Scatola del Gioco" align="center" /></a>


The aim of the project is to implement the [Santorini](http://www.craniocreations.it/prodotto/santorini/) board game following the architectural model Model View Controller for the realization of the model according to the object-oriented programming paradigm, in this case with Java language. The final result completely covers the rules defined by the game and allows you to interact with both a command line interface (CLI) and a graphical interface (GUI), the network has been managed with the traditional socket approach.

# Functionality

## Categories developed in the project:
1) Complete Rules
2) CLI
3) GUI
4) Socket
5) Advanced features:
    1) Multiple games
    2) Advanced gods

## The advanced features developed in detail are:

### Multiple games
Possibility to carry out multiple games simultaneously

### Advanced gods
We have implemented 5 advanced gods, in particular:
- #### N.16 Chronus
- #### N.20 Hera
- #### N.21 Hestia
- #### N.22 Hypnus
- #### N.30 Zeus

### Extra:
- Tutorial
- Chat
- Music and sounds

## Documentation
The following documentation includes the documents created for the design of the problem, the class diagrams in UML will be listed first, then the code documentation (javadoc) and finally the test coverage.

### UML
The following class diagrams represent the first, the initial model according to which the game should have been implemented, the second instead contains the diagrams of the final product.
- [UML Iniziali]()
- [UML Finali]()

### JavaDoc
The following documentation includes a description of the classes and methods used and can be consulted at the following address: [Javadoc]()

### Coverage
As for the coverage of the tests as reported by IntelliJ are:
- __model__ 100% classes, 96% method, 92% line
- __controller__ 100% classes, 96% method, 96% line

### Jars
The following jars have been used for the delivery of the project, they allow the launch of the game according to the functionalities described in the introduction. Details for how to launch the system will be defined in the section called Executing jars. The folder where the client and server software is located is located at the following address: [Jars]().

## Execution of the JARs
### Client
The client is executed by launching the desired jar to play from the command line or graphical interface. The following sections describe how to run the client in one way or another.

### CLI
For a better command-line gaming experience it is necessary to launch the client with a terminal that supports UTF-8 encoding and ANSI escapes. To launch the CLI client type the following command:
```
java -jar CLI.jar
```

### GUI
To launch the GUI client type the following command:
```
java -jar GUI.jar
```

### Server
By default the server will be accessible through port 4700 and at address 127.0.0.1 To launch the server enter the following command:
```
java -jar Server.jar
```
## Componenti del gruppo
- [__Luigi Scibona__](https://github.com/Sciluxor)
- [__Alessandro Ruzzi__](https://github.com/alexruzzi98)
- [__Edoardo Piantoni__](https://github.com/edoardopiantoni)