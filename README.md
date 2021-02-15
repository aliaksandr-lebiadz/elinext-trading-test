# Elinext trading assignment

### Startup instructions:
- Set properties api.key and api.secret in application.properties (BSDEX_API_KEY and BSDEX_API_SECRET accordingly)
- Execute mvn clean install in the root folder
- Move to the target folder under the root and execute java -jar trading-test-1.0.jar
- Open http://localhost:8080 in the browser

### How it works:
After the startup the application consuming balances from external API and logs it into console.
After that connection to the socket is establishing and client socket on the front page subscribes to the socket on Java.
All changes are being displayed on the page in the live mode.

### Used frameworks and libraries:
- Spring Boot (Web, WebSocket)
- Jackson
- Apache Commons
- JQuery for UI