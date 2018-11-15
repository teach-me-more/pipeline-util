#!/usr/bin/env groovy
def call(deploymentUnitName,environment) {

	def stackExists
	def currentUnit="${deploymentUnitName}-${environment}"
	def stackName ="${currentUnit}-stack"
	echo "Starting application with stack id == ${stackName}"

	try{
		stackExists = sh (script: "aws cloudformation describe-stacks --stack-name ${stackName}",returnStdout: true)
	}catch(Exception e){
	}

	if(!stackExists) {
		echo "Creating a new stack"
		sh "aws cloudformation create-stack --stack-name ${stackName} --template-url ${TemplateName} --capabilities CAPABILITY_IAM --parameters ParameterKey=deploymentUnit,ParameterValue=${currentUnit} ParameterKey=MaxNoOfInstances,ParameterValue=${MaxServers} ParameterKey=MinNoOfInstances,ParameterValue=${MinServers}"
		sh "aws cloudformation wait stack-create-complete --stack-name ${stackName}"
	}else{
		try{
			echo "Updating an existing stack"
			sh "aws cloudformation update-stack --stack-name ${stackName} --template-url ${TemplateName} --capabilities CAPABILITY_IAM --parameters ParameterKey=deploymentUnit,ParameterValue=${currentUnit} ParameterKey=MaxNoOfInstances,ParameterValue=${MaxServers} ParameterKey=MinNoOfInstances,ParameterValue=${MinServers}"
			sh "aws cloudformation wait stack-update-complete --stack-name ${stackName}"
		}catch(Exception e){
			echo 'Updates to existing stack failed !'
		}
	}
}