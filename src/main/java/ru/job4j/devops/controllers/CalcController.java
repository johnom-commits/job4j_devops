package ru.job4j.devops.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.job4j.devops.models.Result;
import ru.job4j.devops.models.TwoArgs;

/**
 * Контроллер приложения
 *
 * @author parsentev
 * @version 1.0.0
 * @since 1.0.0
 */
@RestController
@RequestMapping("calc")
public class CalcController {

    /**
     * Конструктор класса
     */
    public CalcController() {
    }

    /**
     * Пост метод для вычисления суммы двух чисел
     *
     * @param twoArgs - содержит значения двух переменных, сумму которых вычисляем
     * @return - возвращаем сумму двух чисел
     */
    @PostMapping("summarise")
    public ResponseEntity<Result> summarise(@RequestBody TwoArgs twoArgs) {
        var result = twoArgs.getFirst() + twoArgs.getSecond();
        return ResponseEntity.ok(new Result(result));
    }

    /**
     * Пост метод для вычисления произведения двух чисел
     *
     * @param twoArgs - содержит значения двух переменных, произведение которых вычисляем
     * @return - возвращаем произведение двух чисел
     */
    @PostMapping("times")
    public ResponseEntity<Result> times(@RequestBody TwoArgs twoArgs) {
        var result = twoArgs.getFirst() * twoArgs.getSecond();
        return ResponseEntity.ok(new Result(result));
    }
}
