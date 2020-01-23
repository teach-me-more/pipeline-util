#!/usr/bin/env groovy
import com.rbasystems.utility.PipelineUtil;
def call(config) {
	def repoUrl=config["REPO_ROOT_URL"];
	def groupId=config["GROUP_ID"];
	def  componentListStr=config["COMPONENT_LIST"];
	def componentList=componentListStr.split(",");
	def choiceList = new ArrayList();
	
	componentList.each{ component ->
		println "loading version information for groupId=$groupId & artifact Id=$component from $repoUrl";
		def versions=PipelineUtil.versionList(repoUrl,groupId,component);
		 def selectedProperty = input (message: 'Select Version', parameters: [choice(choices: versions, description: 'Allow selecting a version of artifact', name: 'Please select a version of $component to deploy')], submitter: 'admin', submitterParameter: 'selectedVersion');
		
		//def componentVersion = " extendedChoice(description: '', multiSelectDelimiter: ',', name:$component, saveJSONParameterToFile: false, type: 'PT_SINGLE_SELECT', value: $versions, visibleItemCount: 2)";
		choiceList.add(componentVersion);
		}
	

println "version infor in extractVersionList $choiceList";

input message: 'Please select a version for deployment', parameters: choiceList, submitter: 'admin', submitterParameter: 'selectedVersion'
//input message: 'Please select a version for deployment', parameters: [extendedChoice(description: '', multiSelectDelimiter: ',', name: 'Version', saveJSONParameterToFile: false, type: 'PT_SINGLE_SELECT', value: versions, visibleItemCount: 2)], submitter: 'admin', submitterParameter: 'selectedVersion'

// def selectedProperty = input (message: 'Select Version', parameters: [choice(choices: proxyVersions, description: 'Allow selecting a version of artifact', name: 'Please select a version of Proxy to deploy')], submitter: 'admin', submitterParameter: 'selectedVersion');
  // def selectedProperty = input( id: 'userInput', message: 'Choose properties file', parameters: [ [$class: 'ChoiceParameterDefinition', choices: proxyVersions, description: 'Properties', name: 'prop'] ])
    
    println "Property: $selectedProperty"
}