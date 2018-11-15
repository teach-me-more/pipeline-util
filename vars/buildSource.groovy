#!/usr/bin/env groovy
def call() {
	echo 'Compiling source code'

	withMaven(maven : 'Maven_3.5.4'){ sh 'mvn clean compile' }
}