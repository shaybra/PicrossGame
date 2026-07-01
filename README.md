# Picross Game

A 5x5 Picross (nonogram) puzzle game written in Java Swing, with an optional
chat/leaderboard server so players can share boards and compete on score and time.

By Mohammed Chaaban and Garrick Weiler.

## Requirements

- JDK 8 or newer
- `make` (optional, you can also compile with `javac` directly)

## Building

```sh
make
```

This compiles all sources into the `build/` directory.

## Running

Run the game (from the repository root, so the images in `images/` can be found):

```sh
make run
# or
java -cp build Game
```

Run the server (defaults to port 61001):

```sh
make server
# or with a specific port
make server PORT=32150
# or
java -cp build PiccrossServer [port]
```

## Playing

- Click a cell to reveal it. Correct cells turn green, wrong ones turn red.
- Use **Mark** to flag cells you believe are empty, and **Check** to switch back.
- The hints above and beside the grid show the runs of filled cells in each
  column and row.
- **Game > New** starts a new random puzzle, **Reset** restarts the current one.

## Multiplayer / Chat

1. Start the server (see above).
2. In the game, choose **Network > New Connection** and enter the server
   address, port, and your name.
3. Open the chat with the **Chat** button. Available commands:
   - `/help` - list commands
   - `/name` - change your name
   - `/who` - list connected users
   - `/get` - fetch the current challenge board and leaderboard
   - `/bye` - disconnect

Finishing a game while connected lets you send your board, score, and time to
the server for others to try and beat.

## Cleaning

```sh
make clean
```
