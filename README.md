# Задача №1: Заказ доставки карты (изменение даты)

[![Java CI with Gradle](https://github.com/olegbogatencko-coder/card-delivery-selenide/actions/workflows/gradle.yml/badge.svg)](https://github.com/olegbogatencko-coder/card-delivery-selenide/actions/workflows/gradle.yml)

## Описание

Автотесты на Selenide для проверки функции перепланирования встречи с представителем банка при заказе доставки карты.

## Найденный баг

При повторном заполнении формы теми же данными, но с изменённой датой, приложение не показывает модальное окно «Необходимо подтверждение».

Подробности в [Issue #1](../../issues/1).

## Технологии

- Java 21
- Selenide 7.0.4
- JUnit 5
- Faker
- Lombok
- Gradle

## Запуск приложения

java -jar artifacts/app-replan-delivery.jar

## Запуск тестов

./gradlew test

## Артефакты

- Скриншоты и HTML-отчёты после тестов: `build/reports/tests/`
- Тестируемое приложение: `artifacts/app-replan-delivery.jar`