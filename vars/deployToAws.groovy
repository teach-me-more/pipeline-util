#!/usr/bin/env groovy
def call() {
                 def stackExists
                   try{
                 		stackExists = sh (script: 'aws cloudformation describe-stacks --stack-name aws-code-deploy-${Environment}',returnStdout: true)
					}catch(Exception e){
					}

                if(!stackExists) {
                echo "Creating a new stack"
                sh 'aws cloudformation create-stack --stack-name aws-code-deploy-${Environment} --template-url ${TemplateName} --capabilities CAPABILITY_IAM --parameters ParameterKey=MaxNoOfInstances,ParameterValue=${MaxServers} ParameterKey=MinNoOfInstances,ParameterValue=${MinServers}'
                sh 'aws cloudformation wait stack-create-complete --stack-name aws-code-deploy-${Environment}'
                }else{
                try{
                echo "Updating an existing stack"
                sh 'aws cloudformation update-stack --stack-name aws-code-deploy-${Environment} --template-url ${TemplateName} --capabilities CAPABILITY_IAM --parameters ParameterKey=MaxNoOfInstances,ParameterValue=${MaxServers} ParameterKey=MinNoOfInstances,ParameterValue=${MinServers}'
                sh 'aws cloudformation wait stack-update-complete --stack-name aws-code-deploy-${Environment}'
                }catch(Exception e){
                		echo 'Updates to existing stack failed !'
                }
                }
}