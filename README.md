# Santorini
<img src="https://github.com/Sciluxor/ing-sw-2020-Scibona-Ruzzi-Piantoni/blob/master/wiki-assets/gamebox.png" width="400" alt="Scatola del Gioco" align="center" /></a>

Scopo del progetto è quello di implementare il gioco da tavola [Santorini](http://www.craniocreations.it/prodotto/santorini/) seguendo il pattern architetturale Model View Controller per la realizzazione del modello secondo il paradigma di programmazione orientato agli oggetti, in questo caso con linguaggio Java. Il risultato finale copre completamente le regole definite dal gioco e permette di interagirci sia con una interfaccia da linea di comando (CLI) che grafica (GUI), la rete è stata gestita con il tradizionale approccio delle socket.

@@ -16,14 +17,15 @@ Scopo del progetto è quello di implementare il gioco da tavola [Santorini](http
## Le funzionalità avanzate sviluppate in dettaglio sono:

### Partite Multiple
Possibilità di effettuare contemporaneamente partite multiple

### Divinità avanzate
Abbbiamo implementato 5 divinità avanzate, in particolare:
- #### N.16 Chronus
- #### N.20 Hera
- #### N.21 Hestia
- #### N.22 Hypnus
- #### N.30 Zeus

### Extra:
Chat
@@ -32,7 +34,7 @@ Chat
La seguente documentazione comprende i documenti realizzati per la progettazione del problema, verranno prima elencati i diagrammi delle classi in UML poi la documentazione del codice (javadoc) ed infine il coverage dei test.

### UML
I seguenti diagrammi delle classi rappresentano il primo, il modello iniziale secondo il quale il gioco dovrebbe essere stato implementato, il secondo contiene invece i diagrammi del prodotto finale.
- [UML Iniziali]()
- [UML Finali]()

@@ -67,3 +69,7 @@ Per lanciare il server digitare il seguente comando:
```
java -jar Server.jar
```
## Componenti del gruppo
- [__Luigi Scibona__](https://github.com/Scilux)
- [__Alessandro Ruzzi__](https://github.com/alexruzzi98)
- [__Edoardo Piantoni__](https://github.com/edoardopiantoni)