# [L3S5 COO] — Projet de conception orientée objet

Travail du binôme:

- Nathan Malengé
- Théo Debeer 

-----

## STRUCTURE : 

fil.l3.coo                                                      
├── Main                                                        
├── user/                                                       
│   ├── User                                                    
│   └── InsufficientFundsException                              
├── Velo/                                                       
│   ├── Velo (abstract)                                         
│   ├── VeloClassique                                           
│   └── VeloElectrique                                          
└── station/                                                    
    └── Station  

-----

## DIAGRAMME DE CLASSES

```mermaid
classDiagram
    %% Exception class
    class InsufficientFundsException {
        <<exception>>
        +InsufficientFundsException(String message)
        +InsufficientFundsException(String message, Throwable cause)
    }
    
    %% User class
    class User {
        -int nextId$
        -int id
        -String name
        -int wallet
        +User(String name)
        +int getId()
        +String getName()
        +int getWallet()
        +void addMoney(int amount)
        +void deductMoney(int amount)
        +String toString()
    }
    
    %% Station class
    class Station {
        -int idCounter$
        -int id
        -int capacity
        -int occupiedSpaces
        +Station(int capacity)
        +int getId()
        +int getCapacity()
        +int getOccupiedSpaces()
        +int getAvailableSpaces()
        +boolean hasAvailableSpace()
        +boolean hasBikes()
        +boolean isEmpty()
        +boolean isFull()
        +boolean parkBike()
        +boolean removeBike()
        +String toString()
    }
    
    %% Abstract Velo class
    class Velo {
        <<abstract>>
        -int nextId$
        -String id
        -boolean isAvailable
        #boolean hasBasket
        #boolean hasBaggage
        +Velo()
        +Velo(boolean hasBasket, boolean hasBaggage)
        +String getId()
        +boolean isAvailable()
        +void setAvailable(boolean available)
        +boolean hasBasket()
        +void setBasket(boolean hasBasket)
        +boolean hasBaggage()
        +void setBaggage(boolean hasBaggage)
        #double getAccessoriesPrice()
        +double getPrice()*
        +String getType()*
        +String toString()
    }
    
    %% VeloClassique class
    class VeloClassique {
        -double BASE_PRICE$
        +VeloClassique()
        +VeloClassique(boolean hasBasket, boolean hasBaggage)
        +double getPrice()
        +String getType()
    }
    
    %% VeloElectrique class
    class VeloElectrique {
        -double BASE_PRICE$
        +VeloElectrique()
        +VeloElectrique(boolean hasBasket, boolean hasBaggage)
        +double getPrice()
        +String getType()
    }
    
    %% Relationships
    User ..> InsufficientFundsException : throws
    VeloClassique --|> Velo : extends
    VeloElectrique --|> Velo : extends
```