#!/usr/bin/env groovy
import com.rbasystems.helper.PipelineHelper;
def call(productName) {
	
def versions=PipelineHelper.versionList("commons-logging");

println "version infor in extractVersionList $versions";
input message: 'Please select a version for deployment', parameters: [extendedChoice(description: '', multiSelectDelimiter: ',', name: 'Version', saveJSONParameterToFile: false, type: 'PT_SINGLE_SELECT', value: versions, visibleItemCount: 2)], submitter: 'admin', submitterParameter: 'selectedVersion'

// def selectedProperty = input (message: 'Select Version', parameters: [choice(choices: proxyVersions, description: 'Allow selecting a version of artifact', name: 'Please select a version of Proxy to deploy')], submitter: 'admin', submitterParameter: 'selectedVersion');
  // def selectedProperty = input( id: 'userInput', message: 'Choose properties file', parameters: [ [$class: 'ChoiceParameterDefinition', choices: proxyVersions, description: 'Properties', name: 'prop'] ])
    
    println "Property: $selectedProperty"
}