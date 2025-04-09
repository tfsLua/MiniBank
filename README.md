# MiniBank 2025
Java RESTful API.

## Diagrama de Classes

```mermaid
classDiagram
    class Usuario {
        +UUID id
        +String nome
        +String cpf
        +String email
        +String senha
        +String endereco
    }

    class Cliente {
        +Int numeroConta
        +Decimal saldo
    }

    class Gerente {
        +String login
        +String senha
    }

    class Transferencia {
        +UUID id
        +UUID origemId
        +UUID destinoId
        +Decimal valor
        +DateTime data
    }

    class Emprestimo {
        +UUID id
        +UUID clienteId
        +Decimal valor
        +String motivo
        +Enum status
        +Date dataSolicitacao
    }

    Usuario <|-- Cliente : herda
    Usuario <|-- Gerente : herda

    Cliente "1" --> "0..*" Transferencia : envia
    Cliente "1" --> "0..*" Transferencia : recebe

    Cliente "1" --> "0..*" Emprestimo : solicita
    Gerente "1" --> "0..*" Emprestimo : avalia

  ```
