# Peer-Review 1: UML

**Nicola Zarbo, Francesco Scroccarello, Luca Tombesi**
Gruppo GC62

Valutazione del diagramma UML delle classi del gruppo **GC7**
## Lati positivi

- L'utilizzo per di json per il setup è una buona idea per usare meno codice per generare gli oggetti della partita 
- Avere discard pile come oggetti per tenere conto delle carte usate può essere utile.
- I metodi di check all'interno di matchManager rendono più semplice la gestione di eccezioni causate da input errati dei giocatori.

## Lati negativi

- Dall'uml si denota poco uso di incapsulamento di oggetti, così facendo per esempio, per accedere a coin, alle card o alla dashboard di un giocatore bisogna ogni volta prima confrontare l'attributo mago del giocatore con quello dell'oggetto in questione. 
- Non è chiaro dove siano tenuti gli studenti che dovrebbero essere sulle isole e nuvole, stessa cosa per le torri che dovrebbero essere in dashboard e island. 
- I metodi della classe matchManager, vedendo l'uml restituiscono tutti tipi void e molto spesso non richiedono alcun parametro specifico. Forse questo aspetto è dovuto al fatto che l'uml che ci è stato consegnato è ancora una bozza e quindi non è ben definito



## Confronto tra le architetture

- Nel nostro progetto abbiamo dovuto codificare il setup della partita (nella classe static setup) gestendo le variazioni di regole in base a parametri in input, invece l'utilizzare json con tutte le informazioni necessarie in base al set di regole permette di avere un codice più leggero in cambio della gestione di lettura e interpretazione di json esterni.

- Per istanziare gli oggetti vengono usati dei builder void con la reference in cui sarà salvato l'oggetto ricevuta come parametro, mentre noi usiamo quasi solo dei normali costruttori. 

- Le maggior parte delle classi sono più simili a dei bean, senza metodi interni se non adder per quelli tenuti in liste,mentre nella nostra architettura abbiamo inserito parte della logica negli oggetti stessi, per esempio dashboard gestisce l'inserimento di studenti e professori nelle corrette posizioni all'interno della sua classe.
