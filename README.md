# 🎟️🚶🏽‍♀️‍➡️ QUADRA FÁCIL - api de reserva de quadras esportivas.

> **quadra fácil** é uma `API` que têm como foco simplificar as reservas de quadras esportivas. Com essa API o responsável pelo espaço consegue cadastrar o endereço, horário de abertura e fechamento, regras para as reservas e etc.

## O que essa API permite:

### Quadra (`/quadra`):
- [X] registrar quadra
- [ ] buscar quadra (`/id`)
- [X] buscar por quadras (`/email`)
- [X] buscar por quadras
- [X] editar quadra
- [X] deletar quadra (`/id`)

### Gestor (`/gestor`):
- [X] registrar gestor
- [X] buscar gestor (`/email`)
- [X] buscar por gestores
- [X] editar gestor (`/id` & `{body}`)
- [X] deletar gestor (`/id`)

### Reserva (`/reserva`):
- [ ] criar uma reserva
- [ ] buscar por reserva (`/id`)
- [ ] buscar por reservas
- [ ] editar reserva (`/id`)
- [ ] deletar reserva (`/id`)

### Funcionamento: (`/funcionamento`):
- [X] registrar funcionamento (`/quadra_id` & `[{funcionamento}]`)
- [X] buscar por funcionamento (`/quadra_id`)
- [X] editar funcionamento (`{body}`)
- [X] editar disponibilidade (`/id/atualizar-disponibilidade?disponibilidade=boolean`)
- [X] deletar funcionamento (`/id`)

### Responsavel (`/responsavel`):
- [X] registrar responsável
- [X] buscar por responavel (`/cpf`)
- [X] editar responsável (`/cpf` & `{body}`)
- [X] deletar responsável (`/cpf`)
