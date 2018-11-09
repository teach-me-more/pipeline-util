#!/usr/bin/env groovy
def  call(deploymentUnitName) {
echo 'Deploying System to UAT environment'
            deployToAws(deploymentUnitName,"UAT")
              def uatUrl = populateTestUrl(deploymentUnitName,"UAT")
             echo "UAT System is available at url = ${uatUrl}"
            input message: 'Click to Deploy in Production Environment', ok: 'Deploy To Production'
}