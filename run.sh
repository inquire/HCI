#!/bin/bash

javac */*.java
java hci.ImageLabeller ./images/Tutorial.jpg
rm */*.class

