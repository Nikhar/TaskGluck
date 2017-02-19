# TaskGluck

### **Requirements**

A redis server running on localhost.


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

