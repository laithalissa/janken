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
   Response:
     ```
     {
       roundId: String
     }
     ```
 - GET /score?playerId=<String>&roundId=<string>
   
   Response:
   ```
   {
       score: Int,
       wins: Int,
       losses: Int
   }
   ```
   
## RUNNING

`sbt 'run -local.doc.root=src/main/html'`
