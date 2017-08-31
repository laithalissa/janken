# janken
Rock-Paper-Scissors

## API

 - PUT /newplayer
   Response:
   ```
    {
        playerId: Int
    }
    ```

 - POST /makemove
   Request:
     ```
     {
        playerId: Int,
        move: Int
     }
     ```
 - GET /score?playerId=<Int>
   Response:
   ```
   {
       score: Int,
       wins: Int,
       losses: Int
   }
