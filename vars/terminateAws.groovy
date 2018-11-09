#!/usr/bin/env groovy
def call(deploymentUnitName,environment) {
                 def stackExists
                 def currentUnit="${deploymentUnitName}-${environment}"
                 def stackName ="${currentUnit}-stack"
                 
                   try{
                 		stackExists = sh (script: "aws cloudformation describe-stacks --stack-name ${stackName}",returnStdout: true)
					}catch(Exception e){
					}

                if(!stackExists) {
                echo "There is no existing running environment with - ${stackName}"
                }else{
                try{
                echo "Tearing off an existing environment - ${stackName}"
                }catch(Exception e){
                		echo 'Updates to existing stack failed !'
                }
                }
}