#!/usr/bin/env groovy
def  call(deploymentUnitName) {
echo 'Deploying System to PROD environment'
 deployToAws(deploymentUnitName,"PROD")
  def prodUrl = populateTestUrl(deploymentUnitName,"PROD")
 echo "Production System is available at url = ${prodUrl}"

echo 'Deployment Completed successfully !'
}