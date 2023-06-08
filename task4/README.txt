win: (doesnt work)
javac -encoding UTF-8 -h . .\Main.java
g++ -I "C:\Program Files\Java\jdk-17.0.2\include" -I "C:\Program Files\Java\jdk-17.0.2\include\win32" Main.cpp -o Main.o

wsl:
javac -h . Main.java
g++ -c -fPIC -I /usr/lib/jvm/java-17-openjdk-amd64/include/ -I /usr/lib/jvm/java-17-openjdk-amd64/include/linux/ Main.cpp -o Main.o
g++ -shared -fPIC -o libnative.so Main.o -lc
java -cp . -Djava.library.path=/mnt/c/Users/MarkB/projects/ProjectsIdea/optimization_java/task4/src/main/java -Xmx500m Main
