public class Level_3 extends Level {
    public Level_3() {
        this.ROWS = 8; // two more rows than Level 2

        // Top rows: tough bricks (health 4)
        for (int i = 0; i < 2; i++) {
            makeRow(0, i, COLUMNS, blocks, 4);
        }

        // Middle rows: alternating gaps, medium health (3)
        for (int i = 2; i < 5; i++) {
            int y = i;
            int yCoord = TOP_PADDING + ((Block.SIZE_Y + BLOCK_PAD) * y);
            for (int col = 0; col < COLUMNS; col++) {
                if ((i + col) % 2 == 0) { // alternating pattern
                    int xCoord = SIDE_PADDING + ((Block.SIZE_X + BLOCK_PAD) * col);
                    Block b = new Block(xCoord, yCoord, 3);
                    blocks.add(b);
                    root.getChildren().add(b.asNode());
                }
            }
        }

        // Bottom rows: easier (health 2), full rows again
        for (int i = 5; i < ROWS; i++) {
            makeRow(0, i, COLUMNS, blocks, 2);
        }

        // Add a few obstacles near the center and sides
        makeObstacle(2, 6);
        makeObstacle(3, 6);
        makeObstacle(5, 6);
        makeObstacle(6, 6);
        makeObstacle(4, 7);

        // Add paddle and ball at bottom
        addPaddle();
        addBall();
    }
}
