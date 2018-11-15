#!/usr/bin/env groovy
def call() {
					sh 'aws s3 sync target/ s3://amar-deep-singh  --exclude "*.*"  --include "aws-code-deploy-0.1.0.jar"'
}