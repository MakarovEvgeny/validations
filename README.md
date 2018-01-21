Приложение для учета бизнес-проверок сохраняемых данных в БД, некая база знаний. Ключевые особенности:
* Конкурентное редактирование
* Обязательность аутентификации для редактирования данных
* Ведение истории изменений

### Настройка БД
Для создания/пересоздания таблиц необходимо выполнить команду:
```
mvn initialize -Pdb
```
Хост, порт, наименование БД, схему, реквизиты доступа отличные от тех что поумолчанию, можно указать с помощью параметров, например:
```
mvn initialize -Pdb -Dhost=172.0.0.1 -Dport=1234 -Ddatabase=dbname \
-Dschema=notpublic -Dusername=username -Dpassword=password
```

### Запуск приложения
```
mvn clean spring-boot:run -Papp
```

или
```
mvn clean package -Papp
...
java -jar ./target/validations-0.1.jar
```

В этом случае приложение будет доступно по адресу [https://localhost](https://localhost). При этом для https будет использован самоподписанный сертификат. Правильно подписанный сертификат можно указать с помощью набора параметров при запуске собранного приложения:
```
java -jar ./target/validations-0.1.jar \
--server.ssl.key-store=./correct_keystore.p12 \
--server.ssl.keyStoreType=PKCS12 \
--server.ssl.keyAlias=correctAlias \
--server.ssl.key-store-password=correctPassword
```
Или при запуске с помощью maven:
```
mvn clean spring-boot:run -Papp \
-Dserver.ssl.key-store=./correct_keystore.p12 \
-Dserver.ssl.keyStoreType=PKCS12 \
-Dserver.ssl.keyAlias=correctAlias \
-Dserver.ssl.key-store-password=correctPassword
```
