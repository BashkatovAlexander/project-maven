package com.javarush.games.racer;

public class FinishLine extends GameObject {

    boolean isVisible = false;
    //Поле isVisible указывает, появилась ли финишная черта на экране.

    public FinishLine() {
        super(RacerGame.ROADSIDE_WIDTH, -1 * ShapeMatrix.FINISH_LINE.length, ShapeMatrix.FINISH_LINE);
    }
    /*Конструктор вызывает родительский конструктор GameObject, устанавливая:
x = RacerGame.ROADSIDE_WIDTH — ставим финишную черту посередине дороги;
y = -ShapeMatrix.FINISH_LINE.length — рисуем выше экрана, чтобы потом она «въехала» вниз;
matrix = ShapeMatrix.FINISH_LINE — графика объекта.*/

    public void show() {
        isVisible = true;
    }
    //Активирует финишную черту — теперь она начнёт двигаться и будет участвовать в логике игры.

    public void move(int boost) {
        if (!isVisible) {
            return;
        }
        y += boost;
    }
    //Передвигает финишную черту вниз по экрану со скоростью boost, но только если она уже показана.

    public boolean isCrossed(PlayerCar player) {
        return y > player.y;
    }
    //Если y финишной черты больше y машины игрока — значит, она уже ниже на экране, т.е. игрок её пересёк.
}
