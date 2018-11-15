#!/usr/bin/env groovy
def call() {
	echo 'Building artifact..'
	withMaven(maven : 'Maven_3.5.4'){
		sh 'mvn package -DskipTests=true'
	}
				//	sh 'aws s3 sync target/ s3://amar-deep-singh  --exclude "*.*"  --include "aws-code-deploy-0.1.0.jar"'
}