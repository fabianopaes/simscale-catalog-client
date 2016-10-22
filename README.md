[![Build Status](https://travis-ci.org/fabianopaes/simscale-catalog-client.svg?branch=master)](https://travis-ci.org/fabianopaes/simscale-catalog-client)

# SimScale-Catalog-Client

## Prerequisites

You will need the following things properly installed on your own machine.

* [Java 8](https://www.java.com)

## Installation

Used [Gradle](http://www.gradle.org), a cross-platform build automation tool.
If you prefer [install Gradle](http://www.gradle.org/installation) or use a [Gradle wrapper](http://www.gradle.org/docs/current/userguide/gradle_wrapper.html) inside this project.

* `git clone git@github.com:fabianopaes/simscale-catalog-client.git` this repository
* change into the new directory `simscale-catalog-client`

### Build project

If you do not want to get Gradle installed (if you do not have yet), just run the follow commands

```
chmod +x gradlew
```

```
./gradlew clean build
```

```
./gradlew run
```

However if you already have Gradle installed, just execute

```
gradle clean build
```

### Running the client application

To run the simscale-catalog-client you have to execute the follow command ( and provide all configuration asked)...

```
java -jar build/libs/simscale-catalog-client-1.0.0.jar
```

