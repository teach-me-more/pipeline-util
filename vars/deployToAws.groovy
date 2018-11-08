#!/usr/bin/env groovy
def call() {
                 def stackExists
                   try{
                 stackExists = sh (script: 'aws cloudformation describe-stacks --stack-name aws-code-deploy-${Environment}',returnStdout: true)
}catch(Exception e){
    echo 'Exception occured that means stack does not exist'
}
    echo 'Check build create stack'

                if(!stackExists) {
                sh 'aws cloudformation create-stack --stack-name aws-code-deploy-${Environment} --template-url ${TemplateName} --capabilities CAPABILITY_IAM --parameters ParameterKey=MaxNoOfInstances,ParameterValue=${MaxServers} ParameterKey=MinNoOfInstances,ParameterValue=${MinServers}'
                sh 'aws cloudformation wait stack-create-complete --stack-name aws-code-deploy-${Environment}'
                }else{
                try{
                sh 'aws cloudformation update-stack --stack-name aws-code-deploy-${Environment} --template-url ${TemplateName} --capabilities CAPABILITY_IAM --parameters ParameterKey=MaxNoOfInstances,ParameterValue=${MaxServers} ParameterKey=MinNoOfInstances,ParameterValue=${MinServers}'
                sh 'aws cloudformation wait stack-update-complete --stack-name aws-code-deploy-${Environment}'
                }catch(Exception e){
                echo 'No Need to make updates so skipping'
                }
                }
}