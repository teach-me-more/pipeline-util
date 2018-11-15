#!/usr/bin/env groovy
def call() {

	mavenInfo = readMavenPom file:''
	deploymentUnitName = mavenInfo.artifactId
	version = mavenInfo.version
	config = readProperties file:'pipeline/config.properties'
	echo "Deployment unit name = ${deploymentUnitName}"

	withMaven(maven : 'Maven_3.5.4'){ sh 'mvn clean compile' }
}