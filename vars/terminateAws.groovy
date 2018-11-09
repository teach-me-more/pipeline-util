#!/usr/bin/env groovy
def call(deploymentUnitName,environment) {
                 def stackExists
                 def currentUnit="${deploymentUnitName}-${environment}"
                 def stackName ="${currentUnit}-stack"
                 
                   try{
                 		stackExists = sh (script: "aws cloudformation describe-stacks --stack-name ${stackName}",returnStdout: true)
					}catch(Exception e){
						echo "warning in terminating"
					}

                if(!stackExists) {
                echo "There is no existing running environment with - ${stackName}"
                }else{
                try{
                echo "Tearing off an existing environment - ${stackName}"
                sh "aws cloudformation delete-stack --stack-name ${stackName}"
                }catch(Exception e){
                		echo 'Updates to existing stack failed !'
                }
                }
}