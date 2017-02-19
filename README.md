# TaskGluck

### **Features**

1. You can start a one player(vs AI) or a two player game session.
2. You can join an existing game as second player if slot is available (and you know the game id).
3. Resume the game at any later point in time.
4. Multiple concurrent games possible.
5. Customizable board.

### **Requirements**

A redis server running on localhost.

### **How to run this code**

1. Clone this repo
2. In the folder, run the following commands:

> mvn clean package

> java -jar target/connectfour-0.0.1-SNAPSHOT.jar server configuration.yml

The webservice should now be live at http://localhost:9000/

### **APIs to run game**

* Create a new game:

*URL*: localhost:9000/game/join

*HTTP Method*: POST

*Request Payload*:

```json
{
	"newGame": true,
	"username": "1",
	"name": "Test",
	"twoPlayer": true
}
```
*Note*: Set twoPlayer field to false to play against AI.

*Response payload*:

```json
{
  "id": "607369",
  "status": "In Progress",
  "board": [
    "......",
    "......",
    "......",
    "......",
    "......",
    "......",
    "......"
  ]
}
```
* Join an existing game as Player Two:

*URL*: localhost:9000/game/join

*HTTP Method*: POST

*Request Payload*:

```json
{
	"gameId": "607369",
	"username": "2",
	"name": "Test"
}
```

*Response Payload*:

```json
{
  "id": "607369",
  "status": "Succesfully joined as player two",
  "board": [
    "......",
    "......",
    "......",
    "......",
    "......",
    "......",
    "......"
  ]
}
```

* Make a move as one of the players

*URL*: localhost:9000/game/move

*HTTP Method*: POST

*Request Payload*:

```json
{
	"gameId": "607369",
	"username": "1",
	"column": "5"
}
```

*Response Payload*:

```json
{
  "id": "607369",
  "status": "IN PROGRESS",
  "board": [
    "......",
    "......",
    "......",
    "......",
    "....1.",
    "....2.",
    "2..211"
  ]
}
```

