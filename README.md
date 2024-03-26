### Compile error:
1) File -> invalidate caches -> invalidate and restart
2) run `./gradlew clean`
3) build
   or
4) task("testClasses").doLast {
   println("This is a dummy testClasses task")
   }
   or
5) task("testClasses")
   or
6) run `./gradlew :composeApp:run`'
7) run `./gradlew --stop`'