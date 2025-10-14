# breakout

## CLASSES:  
- main
- Player
- Game
- Ball
- Paddle
- Block


## CONTENTS:  
- [ ] main:  
  - none(probably), call new game  

- [ ] Game:  

- [ ] Ball:
  - radius: int
  - myXCoordinate: int
  - myYCoordinate: int
  - myXVelocity: int
  - myYVelocity: int  
 
  + [ ] ballCollision()  
    returns: none  
    invert velocity according to directionality of surface  
    position ball at edge of colliding object (paddles, walls, blocks)  

  + [ ] move()  
    updates position of ball based on velocities  

- [ ] Paddle:
  - PADDLEVELOCITY: int
  - MYYCOORDINATES: int
  - myXcoordinates: int  
 
  + [ ] keyInput()  
    returns: none  
    registers directional inputs and calls move()  
    
  + [ ] move()  
    returns: none  
    updates myXcoordinates a set amount  
    
- [ ] Block:
  - HEIGHT: int  
  - WIDTH: int  
  - myXCoordinate: int  
  - myYCoordinate: int<br/><br/>
  + [ ] blockCollision()  
    returns: none  
    deletes block when collision is detected and calls ballCollision()


# Breakout Part 2 UML

- [ ] DuplicatePowerUp
  -Makes a second ball

- [ ] ObstacleObject
  -myXCoordinates: int
  -myYCoordinates: int

3 levels ideas:
  -Blocks w/ 1-2 life on outside, increase life as you go through layers
  -"Hallway" made of blocks w/ 5 life, hallway breaks then all other blocks are available
