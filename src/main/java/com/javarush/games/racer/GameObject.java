package com.javarush.games.racer;

import com.javarush.engine.cell.*;
import org.apache.commons.lang3.tuple.Pair;


public class GameObject {
    public int x; // Координаты объекта на игровом поле
    public int y;  // Координаты объекта на игровом поле
    public int width; // Размеры объекта (по матрице)
    public int height; // Размеры объекта (по матрице)
    public int[][] matrix;  // "Форма" или "графика" объекта (цвета)

    public GameObject(Pair<Integer, Integer> coordinates) {
        this.x = coordinates.getLeft();
        this.y = coordinates.getRight();
        //Конструктор только с координатами — без формы (может использоваться для логических объектов, например, точек).
    }

    public GameObject(int x, int y, int[][] matrix) {
        this.x = x;
        this.y = y;
        this.matrix = matrix;
        width = matrix[0].length;
        height = matrix.length;
        //Конструктор с координатами и формой — основной для визуальных объектов.
    }

    public void draw(Game game) {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int colorIndex = matrix[j][i];
                game.setCellColor(x + i, y + j, Color.values()[colorIndex]);
            }
        }
       /* Отрисовывает объект на игровом поле.

                Для каждой точки matrix[j][i] берётся цвет и вызывается game.setCellColor(...).

        Color.values()[colorIndex] — получает соответствующий цвет из enum Color. */
    }

    public boolean isCollisionPossible(GameObject otherGameObject) {
        if (x > otherGameObject.x + otherGameObject.width || x + width < otherGameObject.x) {
            return false;

        }

        if (y > otherGameObject.y + otherGameObject.height || y + height < otherGameObject.y) {
            return false;
        }
        return true;
        //Быстрая проверка — могут ли объекты в принципе столкнуться.
        //Используется bounding box (рамка): если границы объектов не пересекаются, то объекты не столкнулись.
    }

    public boolean isCollision(GameObject gameObject) {
        if (!isCollisionPossible(gameObject)) {
            return false;
        }

        for (int carX = 0; carX < gameObject.width; carX++) {
            for (int carY = 0; carY < gameObject.height; carY++) {
                if (gameObject.matrix[carY][carX] != 0) {
                    if (isCollision(carX + gameObject.x, carY + gameObject.y)) {
                        return true;
                    }
                }
            }
        }
        return false;
        //Полная проверка на фактическое столкновение по форме.
        //Сначала вызывает isCollisionPossible() — если false, значит сразу false.
        //Если рамки пересекаются, сравнивает непрозрачные пиксели (matrix != 0).
        //Если координаты пикселей двух объектов совпадают — это столкновение.
    }

    private boolean isCollision(int x, int y) {
        for (int matrixX = 0; matrixX < width; matrixX++) {
            for (int matrixY = 0; matrixY < height; matrixY++) {
                if (matrix[matrixY][matrixX] != 0 && matrixX + this.x == x && matrixY + this.y == y) {
                    return true;
                }
            }
        }
        return false;
        //Проверяет: есть ли в этом пикселе x, y хоть один непрозрачный пиксель объекта.
    }
}