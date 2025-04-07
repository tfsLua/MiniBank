# MiniBank 2025
Java RESTful API.

## Diagrama de Classes

```mermaid
classDiagram
    class User {
        +String name
    }

    class Account {
        +String number
        +String agency
        +double balance
        +double limit
    }

    class Feature {
        +String icon
        +String description
    }

    class Card {
        +String number
        +double limit
    }

    class News {
        +String icon
        +String description
    }

    User "1" *-- "1" Account : A cabeça da
    User "1" *-- "N" Feature : has many
    User "1" *-- "1" Card : has
    User "1" *-- "N" News : has many
  ```
