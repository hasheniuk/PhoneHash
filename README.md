Application use embedded container and embedded key-value storage. 
So, there is no need to install something separately.

Java version - 8

How to start.
1. Open console.
2. Navigate to PhoneHash directory.
3. Execute mvn clean install
4. Run java -jar target/phone-hash.jar

Alternatively, just run main method of com.hash.phone.Main class

Api
GET hash/{phone}
GET phone/{hash}