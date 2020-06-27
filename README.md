# Santorini

Scopo del progetto è quello di implementare il gioco da tavola [Santorini](http://www.craniocreations.it/prodotto/santorini/) seguendo il pattern architetturale Model View Controller per la realizzazione del modello secondo il paradigma di programmazione orientato agli oggetti, in questo caso con linguaggio Java. Il risultato finale copre completamente le regole definite dal gioco e permette di interagirci sia con una interfaccia da linea di comando (CLI) che grafica (GUI), la rete è stata gestita con il tradizionale approccio delle socket.

## Funzionalità

### Categorie Sviluppate nel progetto:
1) **Regole Complete**
2) **CLI**
3) **GUI**
4) **Socket**
5) **Funzionalità Avanzate:**
    1) **Partite Multiple**
    2) **Divinità Avanzate**

## Le funzionalità avanzate sviluppate in dettaglio sono:

### Partite Multiple
Possibilità di effettuare partite multiple

### Divinità avanzate
#### N.16 Chronus
#### N.20 Hera
#### N.21 Hestia
#### N.22 Hypnus
#### N.30 Zeus

### Extra:
Chat

## Documentazione
La seguente documentazione comprende i documenti realizzati per la progettazione del problema, verranno prima elencati i diagrammi delle classi in UML poi la documentazione del codice (javadoc) ed infine il coverage dei test.

### UML
I seguenti diagrammi delle classi rappresentano il primo, il modello secondo il quale il gioco dovrebbe essere stato implementato, il secondo contiene invece i diagrammi del prodotto finale nelle parti critiche riscontrate.
- [UML Iniziali]()
- [UML Finali]()

### JavaDoc
La seguente documentazione include una descrizione delle classi e dei metodi utilizzati e può essere consultata al seguente indirizzo: [Javadoc]()

### Coverage
Per quanto riguarda il coverage dei test come riportato da IntelliJ risultano:
- __model:__ 100% classes, 96% line covered
- __controller:__ 100% classes, 92% line covered

### Jars
I seguenti jar sono stati utilizzati per la consegna del progetto, permettono il lancio del gioco secondo le funzionalità descritte nell'introduzione. I dettagli per come lanciare il sistema saranno definiti nella sezione chiamata __Esecuzione dei jar__. La cartella in cui si trovano il software del client e del server si trova al seguente indirizzo: [Jars](https://github.com/Sciluxor/ing-sw-2020-Scibona-Ruzzi-Piantoni/tree/master/Jars).

## Esecuzione dei JAR
### Client
Il client viene eseguito lanciando il jar desiderato per giocare da linea di comando o interfaccia grafica. Le seguenti sezioni descrivono come eseguire il client in un modo o nell'altro.
#### CLI
Per una migliore esperienza di gioco da linea di comando è necessario lanciare il client con un terminale che supporti la codifica UTF-8 e gli ANSI escape. 
Per lanciare il client CLI digitare il seguente comando:
```
java -jar CLI.jar
```
#### GUI
Per lanciare il client GUI digitare il seguente comando:
```
java -jar GUI.jar
```
### Server
Di default il server sarà accessibile attraverso la porta 4700 e all'indirizzo 127.0.0.1
Per lanciare il server digitare il seguente comando:
```
java -jar Server.jar
```
