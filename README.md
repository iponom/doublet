# doublet
Command line utility for finding files with the same content in a selected directory.

### How to execute (Windows)
```
mvn package
java -jar target\doublet-1.0.jar C:\some\directory
```
or
```
mvn spring-boot:run -Drun.arguments="C:/some/directory"
```

### Implementation notes

A brute-force implementations gives us factorial order-of-growth in the worst case.
We can calculate some fast hash and group files by it. This calculation gives linear order-of-growth. 
But after that we need to compare files in every group.
For selecting the best solution I made some micro-benchmarks before implementation (see HashFunctionsBenchmark). 

### Performance settings

#### Garbage collector

* Switched on gc log `-verbose:gc -Xloggc:logs/gc.log`
* Used GCViewer (https://github.com/chewiebug/GCViewer) and grep to analyze logs
* Switched between CMS `-XX:+UseConcMarkSweepGC`  and parallel `-XX:+UseParallelGC` gc
* Changed `-Xms` and `-Xmx`

Total time was almost the same but CMS used less memory. Left default gc settings

#### ForkJoinPool thread count

see property doublet.parallelism

I will have the best performance for my computer (4 core) if  doublet.parallelism is 8.

#### Just-in-Time settings

TODO