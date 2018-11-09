#!/usr/bin/env groovy
def call(deploymentUnitName) {
                 def stackExists
                   try{
                 		stackExists = sh (script: "aws cloudformation describe-stacks --stack-name ${deploymentUnitName}",returnStdout: true)
					}catch(Exception e){
					}

                if(!stackExists) {
                echo "Creating a new stack"
                sh "aws cloudformation create-stack --stack-name ${deploymentUnitName} --template-url ${TemplateName} --capabilities CAPABILITY_IAM --parameters ParameterKey=MaxNoOfInstances,ParameterValue=${MaxServers} ParameterKey=MinNoOfInstances,ParameterValue=${MinServers}"
                sh "aws cloudformation wait stack-create-complete --stack-name ${deploymentUnitName}"
                }else{
                try{
                echo "Updating an existing stack"
                sh "aws cloudformation update-stack --stack-name ${deploymentUnitName} --template-url ${TemplateName} --capabilities CAPABILITY_IAM --parameters ParameterKey=MaxNoOfInstances,ParameterValue=${MaxServers} ParameterKey=MinNoOfInstances,ParameterValue=${MinServers}"
                sh "aws cloudformation wait stack-update-complete --stack-name ${deploymentUnitName}"
                }catch(Exception e){
                		echo 'Updates to existing stack failed !'
                }
                }
}