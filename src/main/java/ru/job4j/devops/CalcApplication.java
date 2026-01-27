package ru.job4j.devops;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Главный класс приложения калькулятора
 *
 * <p>
 *  Основной класс приложения калькулятора. Инициализирует
 *  необходимые компоненты и запускает приложение.
 * </p>
 *
 * @author parsentev
 * @version 1.0.0
 * @since 1.0.0
 */
@SpringBootApplication
public class CalcApplication {

	/**
	 * Создает новый экземпляр калькуляторного приложения.
	 * <p>
	 * Инициализирует конфигурацию и зависимости приложения.
	 * </p>
	 */
	public CalcApplication() {
	}

	/**
	 * Главный класс, запускает приложение
	 * @param args - массив строк с аргументами
	 */
	public static void main(String[] args) {
		SpringApplication.run(CalcApplication.class, args);
	}
}
