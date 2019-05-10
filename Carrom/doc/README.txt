Carrom (care-uhm)


Akshat Jain, Ayush Satyavarpu, Calix Tang
5/1/2019


Description: 
        Carrom is a game similar to pool, where there are many pieces on a board that can be knocked around by a striker piece that belongs on each players striking area when it is the player’s turn. Each piece on the board has different point values, and the players main goal is to use the striker to knock the pieces into different corner pockets, like pool, until all of the pieces are gone. If a player lands a piece, they are allowed to hit again. Then, the player with the most points wins the game. This game is networked, so each player can play on their own computer.


Instructions: 
* We wanted to bring Carrom into the spotlight as a game.
* The goal of Carrom is similar to pool: to sink as many game pieces into scoring pockets as possible through indirect contact.
* Put your striker piece on the board. 
* Use your striker piece to aim it at the pieces on the board in hopes of knocking them in to one of the pockets. The three types of pieces are black pieces, white pieces, and a red piece (the queen)
* Sinking the striker results in returning your piece of highest value and losing your next turn.
   * If you have not sunk any piece yet, you do not lose points but just lose a turn.
* Black pieces are worth 10 points,
* White pieces are worth 20 points.
* The queen is worth 50 points.
* If you manage to sink the queen, you must sink another piece immediately afterwards without fail. This can be done on the same turn. This is called “covering the queen.”
   * If you fail, the queen goes back on the board and you lose those points.
* When you sink one piece that you are supposed to sink, you get a turn immediately afterwards. This is the same case in pool.
* When all pieces are off of the board (except for the striker), points are compared and a winner is chosen.
   * Note that draws are possible!


Features list: 
Must-Haves: 
* Scoring mechanics, 10 pt for white, 20 pt for black, 50 pt for red
* Images for game pieces and boards.
* Striker (analogous to cue ball) system and penalty for sinking the striker.
* Friction to slow down game pieces. This will aid the collision system and make gameplay realistic.
* Accurate hitboxes, collision, and bouncing. This will mirror elastic collisions in real life.
* Win condition
* Menu with instructions and credits page
* First game version will be the one where each player can hit any piece to 
* Trajectory tracing like in 8-ball pool (the iMessage game). This will help the player aim.


Want-to-Haves: 
* Second game version will be a team version, where each team has an assigned color of piece that they must sink. Each piece color will be worth the same. If this version is implemented, we will add it to the instructions/description section of this readme.
* Smooth graphics
* Replayability - play again screen and reset in code
* Multiple versions of Carrom implemented (which have different rule sets)
* Networked game - where people can play together on multiple computers
* Variable flick strength, as in the player can determine how hard they “flick” the piece and how fast the resulting pieces move.
* A taunt/emote menu (networked) similar to that of Clash Royale
* You see the board from the perspective of the person playing rather than your own perspective.


Reaches: 
* A tutorial that teaches users how to play using our controls
* Implement 3D collision because in real life pieces move in x,y, and z directions. This will look like overlapping pieces.
* An AI that flicks intelligently, allowing a single-player version to be implemented
* In real Carrom, the striker must be placed in between two lines. There is a penalty for failure to place it in that range. However, as we will code Carrom, we will force the striker to move on a slider in the range. A stretch would be to allow the user to move it freely and then penalize them for moving the striker out of range. 
* Internet integration, maybe as application software
* Resizable window - where the board will resize to the dimension of the smaller length.
* Add an arcade menu, which leads to Carrom, Pool, or Air Hockey


Class list: 
* Carrom (Main)
   * This will hold the drawingsurface and keep it running for the duration of the program’s runtime.
* GameBoard extends PApplet(represents the board)
   * Has a Striker
   * ArrayList of Player
   * ArrayList of GenericGamePieces
   * It will handle the drawing of everything and the mechanics of the game
   * It will also handle turns 
   * Connects with all the networked classes
* Piece 
   * Abstract class that represents a single game piece. 
   * x, y
   * Draw method
   * Radius
   * CheckHit method (for collisions)
* Striker extends Piece
   * Overriden hit method that allows it to be struck by the player
* GenericGamePiece extends Piece
   * You can customise this through constructor to set color and value
      * Red, White, and Black
   * This will be useful if we implement multiple version with different rulesets
   * * Player
   * Represents the player, will be added to GameBoard’s ArrayList
   * Has a score 
        


Responsibilities List: 
Akshat: 
* Gameboard, Graphics
Ayush:
* Players and networking
Calix:
* Piece and collision detection




Credit List:
* Akshat
   * Wrote gameboard class, handled images and background, wrote main
* Ayush
   * Made the UML 
* Calix
   * Wrote the collision system
* https://www.real-world-physics-problems.com/physics-of-billiards.html
* https://i.stack.imgur.com/CKI4U.png 
* Our good friend Jing-Chen Peng
Processing