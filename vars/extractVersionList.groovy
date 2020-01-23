#!/usr/bin/env groovy
import com.rbasystems.utility.PipelineUtil;
import hudson.model.ParameterDefinition;
//import groovy.json.JsonGenerator;
import groovy.json.*;
def call(config) {
	def repoUrl=config["REPO_ROOT_URL"];
	def groupId=config["GROUP_ID"];
	def  componentListStr=config["COMPONENT_LIST"];
	def componentList=componentListStr.split(",");
	def choiceList = new ArrayList();
	componentList.each{ component ->
		println "loading version information for groupId=$groupId & artifact Id=$component from $repoUrl";
		def versions=PipelineUtil.versionList(repoUrl,groupId,component);
		def firstChoice=extendedChoice(description: 'Select appropiate '+component +' version for deployment , leave empty if not part of release.', multiSelectDelimiter: ',', name: component,  type: 'PT_SINGLE_SELECT', value: versions);
		choiceList.add(firstChoice);
		}
	def deploymentSelections = 	input(message: 'Please select component version for release package.', parameters: choiceList, submitter: 'admin', submitterParameter: 'approver');
		
	println "Input submitted by  and selection=$deploymentSelections "
	
	//println new JsonBuilder(deploymentSelections).toPrettyString();
	
	def generator = new JsonGenerator.Options().excludeNulls()
.dateFormat('yyyy@MM')
	.excludeFieldsByName('approver')
	.excludeFieldsByType(URL)
	.build();
	def jsonVal= generator.toJson(deploymentSelections);
	
	def json = new groovy.json.JsonBuilder()
	json "info": deploymentSelections
	def file = new File("$WORKSPACE/release.json")
	file.write(groovy.json.JsonOutput.prettyPrint(json.toString()))
//	writeJSON file: 'output.json', json: json, pretty: 4
	
//input message: 'Please select a version for deployment', parameters: choiceList, submitter: 'admin', submitterParameter: 'selectedVersion'
//input message: 'Please select a version for deployment', parameters: [extendedChoice(description: '', multiSelectDelimiter: ',', name: 'Version', saveJSONParameterToFile: false, type: 'PT_SINGLE_SELECT', value: versions, visibleItemCount: 2)], submitter: 'admin', submitterParameter: 'selectedVersion'

// def selectedProperty = input (message: 'Select Version', parameters: [choice(choices: proxyVersions, description: 'Allow selecting a version of artifact', name: 'Please select a version of Proxy to deploy')], submitter: 'admin', submitterParameter: 'selectedVersion');
  // def selectedProperty = input( id: 'userInput', message: 'Choose properties file', parameters: [ [$class: 'ChoiceParameterDefinition', choices: proxyVersions, description: 'Properties', name: 'prop'] ])
    
}