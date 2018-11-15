#!/usr/bin/env groovy
def call(deploymentUnitName) {
TEST_URL= populateTestUrl(deploymentUnitName,"Dev");
echo "Application deployed with url - ${TEST_URL}"
				
def TEST_RESULT
def expectedResult = 'Hello World !'
TEST_RESULT = sh returnStdout: true, script: "curl $TEST_URL"
if("${expectedResult}"=="${TEST_RESULT}"){
 echo "Build deployed successfully ! test result== ${TEST_RESULT} "
 sh "mvn verify -DTEST_SERVER_BASE_URL=${TEST_URL}"
}else{
 echo "Build deployment failed  !"
 error("Application Sanity check failed")
}

}