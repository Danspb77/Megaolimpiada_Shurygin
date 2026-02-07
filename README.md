# Megaolimpiada_Shurygin (Android / Compose)

Приложение на Android (Kotlin + Jetpack Compose) с архитектурой MVVM и ручной DI. Данные о матчах берутся из API sstats.

## Требования

1. **Android Studio** (рекомендуется Giraffe или новее)
2. **JDK 11** (обычно уже есть в составе Android Studio)
3. **Android SDK** со следующими параметрами:
   - `compileSdk = 36`
   - `targetSdk = 35`
   - `minSdk = 24`
4. **Доступ в интернет** (для загрузки данных)

## Клонирование проекта

```bash
git clone https://github.com/Danspb77/Megaolimpiada_Shurygin
cd <ПАПКА_ПРОЕКТА>
```

## Установка зависимостей

Все зависимости управляются через Gradle.

1. Откройте проект в Android Studio.
2. Нажмите **Sync Now**, если появится уведомление.
3. Дождитесь завершения синхронизации Gradle.

Зависимости скачаются автоматически.

## Запуск приложения

1. Создайте или выберите эмулятор/устройство.
2. Нажмите **Run** в Android Studio.

## Примечания

- UI полностью на **Jetpack Compose**.
- Архитектура: **MVVM** (ViewModel + Repository) и ручная DI.

## Решение проблем

**Gradle Sync не проходит**
- Проверьте доступ в интернет
- Убедитесь, что в `settings.gradle.kts` есть `google()` и `mavenCentral()`

**Ошибка JDK**
- В Android Studio: `Settings > Build, Execution, Deployment > Build Tools > Gradle`
- Выберите JDK 11

**Списки пустые**
- Проверьте доступ в интернет на устройстве/эмуляторе

