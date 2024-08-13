import random

# Constants
BOARD_SIZE = 5
SHIP_SIZES = [2, 3, 4]  # Different sizes of ships

def create_board():
    return [[' ' for _ in range(BOARD_SIZE)] for _ in range(BOARD_SIZE)]

def print_board(board, hide_ships=False):
    print("  " + " ".join(str(i) for i in range(BOARD_SIZE)))
    for row_index, row in enumerate(board):
        display_row = []
        for cell in row:
            if cell == 'S' and hide_ships:
                display_row.append(' ')
            else:
                display_row.append(cell)
        print(row_index, " ".join(display_row))

def place_ships(board, ship_sizes):
    for size in ship_sizes:
        placed = False
        while not placed:
            orientation = random.choice(['H', 'V'])
            row = random.randint(0, BOARD_SIZE - 1)
            col = random.randint(0, BOARD_SIZE - 1)

            if orientation == 'H':
                if col + size <= BOARD_SIZE and all(board[row][col + i] == ' ' for i in range(size)):
                    for i in range(size):
                        board[row][col + i] = 'S'
                    placed = True
            else:
                if row + size <= BOARD_SIZE and all(board[row + i][col] == ' ' for i in range(size)):
                    for i in range(size):
                        board[row + i][col] = 'S'
                    placed = True

def get_guess():
    while True:
        try:
            row = int(input("Enter row (0-4): "))
            col = int(input("Enter column (0-4): "))
            if 0 <= row < BOARD_SIZE and 0 <= col < BOARD_SIZE:
                return row, col
            else:
                print("Invalid input. Please enter values between 0 and 4.")
        except ValueError:
            print("Invalid input. Please enter integers.")

def main():
    board = create_board()
    ship_sizes = SHIP_SIZES
    place_ships(board, ship_sizes)
    hits = {size: 0 for size in ship_sizes}
    total_ships = sum(ship_sizes)
    total_hits = 0
    attempts = 0
    max_attempts = 10

    print("Welcome to Battleship!")
    print_board([[' ']*BOARD_SIZE for _ in range(BOARD_SIZE)])  # Show empty board

    while attempts < max_attempts and total_hits < total_ships:
        print(f"Attempt {attempts + 1} of {max_attempts}")
        row, col = get_guess()

        if board[row][col] == 'S':
            print("Hit!")
            board[row][col] = 'H'
            total_hits += 1
        elif board[row][col] == ' ':
            print("Miss!")
            board[row][col] = 'M'
        else:
            print("Already guessed that location!")

        attempts += 1
        print_board(board, hide_ships=True)

    print("Game Over!")
    print_board(board)
    print(f"Total hits: {total_hits}")

if __name__ == "__main__":
    main()
