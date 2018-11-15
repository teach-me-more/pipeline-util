#!/usr/bin/env groovy
def call() {
	echo 'Compiling source code'
	mavenInfo = readMavenPom file:''
	deploymentUnitName = mavenInfo.artifactId
	version = mavenInfo.version
	config = readProperties file:'pipeline/config.properties'
	echo "Deployment unit name = ${deploymentUnitName}"

	withMaven(maven : 'Maven_3.5.4'){ sh 'mvn clean compile' }
}