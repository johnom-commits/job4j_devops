package ru.job4j.devops.models;

import lombok.Data;

/**
 * Хранит основные переменные
 *
 * @author parsentev
 * @version 1.0.0
 * @since 1.0.0
 */
@Data
public class TwoArgs {
    private double first;
    private double second;

    /**
     * Конструктор класса
     *
     * @param f - первый аргумент
     * @param s - второй аргумент
     */
    public TwoArgs(double f, double s) {
        this.first = f;
        this.second = s;
    }
}
