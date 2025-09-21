# Grid Game - Design Documentation
## Game Overview
A 20x20 grid time based clicking game where players click to use power-ups and eliminate enemies while avoiding bombs. The game features dynamic spawning, dynamic click ranges, a win condition based on clearing all enemies and a lose condition if bomb have been clicked.
## How each class works
### Main classes
#### Main.java
- creates the game window and starts two threads
- One thread updates game logic every 300ms(as frame for games)
- another thread refreshes the display at 144fps for smooth detection
- contains the restart button
#### Stage.java
- holds the grid, enemies, bombs, power-ups
- runs the game loop (step()method) that updates all game objects
- Handles painting all elements to screen
- call functinos from others
#### Grid.java
- creates and manages the 20x20 grid of cells
- converts between column letters and numbers
- finds cells at a specific positions or mouse clicks
- use Optional<Cell>to safely handle invalid positions
#### Cell.java
- represents one square on the grid(35x35pixels)
### Game Objects(inheritance Structure)
#### Actor.java
- base class for all game pieces, include location, color, display, and paint method
#### Enemy.java
- Dangerous Red Squares
- moves randomly one cell per step in random direction(4-way)
- Play must remove all of them to win
- specifically use polygon translations to move smoothly and prevent crash
#### Bomb.java
- Warning black X mark
- spawns dynamically on grid
- full animation is 6 steps
- only explode after animation complete(ready check), gives player longer reaction time
- disappears after 30steps automatically
#### PowerUp.java
- Warm yellow up arrow
- spawns dynamically on grid
- increase player's click range when collected
- effect lasts 10 steps, then range decreases
- this effect is stackable
### Support systems
#### Spawn.java
- Randomly place bombs and powerups base on their own intervals
- checks if location is empty, prevent overlapping items
#### Click.java
- processes player clicks with current range
- cell check priority:
1. Checks bomb in current range first(lose-condition)
2. enemies(remove enemy, win condition if all enemies been removed),
3. powerups(manage click range increase timer)
#### Steppable.java
- simple but core: Any class implementing this must define what happenes each game ticks

## How inheritance contributed to good design
### 1. Actor Class Hierarchy
The `Actor` abstract class serves as the base for all game entities (Enemy, Bomb, PowerUp), providing:
- **Code Reuse**: Common fields (`color`, `loc`, `display`) and methods (`paint()`, `step()`) are defined once
- **Uniformly**: All actors can be treated uniformly when painting or stepping, despite different behaviors
- **Extensibility**: New actor types can be added easily by extending Actor

```
Actor
--- Enemy (red squares that move randomly)
--- Bomb (X marks that explode)
--- PowerUp (boost items that increase click range)
```

### 2. Steppable Interface
The `Steppable` interface ensures all game entities can participate in the game loop:
- **Contract Enforcement**: Guarantees every actor has a `step()` method
- **Flexible Implementation**: Each actor type implements stepping differently (Enemy moves, Bomb ages, PowerUp time elapsed)
- **Separation of Concerns**: Distinguishes "steppable" behavior from other actor capabilities

## How generics contributed to good design

### 1. Type-safe collections
Generics provide type-safe collections throughout the game:
- `List<Enemy>`, `List<Bomb>`, `List<PowerUp>` ensure type safety at compile time
- No casting needed when accessing elements
- Prevents accidentally adding wrong types to collections

### 2. Optional prevents null crashes
The `Grid` class uses `Optional<Cell>` for safe cell lookups:
```java
Optional<Cell> cellAtColRow(int c, int r)
Optional<Cell> cellAtPoint(Point p)
```
Benefits:
- **Explicit Null Handling**: Forces checking if a cell exists before using it
- **Prevents NullPointerException**: Common grid boundary errors are avoided
- **Clear API Contract**: Methods explicitly indicate when they might not return a value

### 3. Iterator for safe Removal
The `Click` class uses typed iterators for safe collection modification:
```java
Iterator<Enemy> enemyIt = enemies.iterator();
```
- Type-safe iteration and removal during gameplay
- No ConcurrentModificationException when removing items while iterating
- Clear intent about which collection type is being processed

## Design Benefits

### Maintainability
- **Comments, naming and good commit history**: clear comments, good naming habit and good commit history are all there to show progress and good for maintaning
- **Single Responsibility**: Each class has one clear purpose
- **Good coding principle**: common behavior in Actor prevents code duplication
- **Clear Contracts**: interfaces and abstract classes define expected behavior

### Scalability
- **Easy to Add Features**: new actor types just extend Actor
- **Modular Systems**: Spawn, Click, and Stage systems are independent
- **Type Safety**: generics catch errors at compile time, not runtime

### Safety
- **Thread Safety**: copying lists in paint() prevents concurrent modification
- **Boundary Safety**: optional prevents out-of-bounds errors
- **Type Safety**: Generic collections prevent ClassCastException

## Key Design Patterns Used
1. **Template Method**: actor defines paint structure, subclasses provide details
2. **Strategy**: each actor type implements its own step() strategy
3. **Iterator**: safe removal of game objects during gameplay
4. **MVC-like Structure**: Stage (Model), Paint (View), Click/Main (Controller)

The combination of inheritance and generics creates clean, reliable code that's easy to understand and extend.

### Personal talk about this unit
I was working overseas and travel frequently, did not have time to attend for group work hence why I do not have any groups. Working on this project is fun and allows me to read && write all my comments/commit messages from scratch again.