package ru.job4j.devops.models;

import lombok.Data;

/**
 * Хранит результат вычислений
 *
 * @author parsentev
 * @version 1.0.0
 * @since 1.0.0
 */
@Data
public class Result {
    private double value;

    /**
     * Конструктор класса
     *
     * @param value - результат вычислений
     */
    public Result(double value) {
        this.value = value;
    }
}
