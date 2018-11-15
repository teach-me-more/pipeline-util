#!/usr/bin/env groovy
def call() {
	echo 'Analyzing your source code for defects'
	withMaven(maven : 'Maven_3.5.4'){ sh 'mvn sonar:sonar  -Dsonar.host.url=http://localhost:9000 -Dsonar.login=ecb869e957dd4b880a6e46ddfe474a2373b47332' }

}