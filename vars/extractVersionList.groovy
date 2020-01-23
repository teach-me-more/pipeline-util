#!/usr/bin/env groovy
import com.rbasystems.utility.PipelineUtil;
def call(config) {
	def repoUrl=config["REPO_ROOT_URL"];
	def groupId=config["GROUP_ID"];
	def  componentListStr=config["COMPONENT_LIST"];
	def componentList=componentListStr.split(",");
	def choiceList = new ArrayList();
	input message: 'Please do something ', ok: 'Hey', parameters: [extendedChoice(bindings: '', description: '', groovyClasspath: '', groovyScript: '''import org.boon.Boon;

def jsonEditorOptions = Boon.fromJson(/{
		disable_edit_json: true,
        disable_properties: true,
        no_additional_properties: true,
        disable_collapse: true,
        disable_array_add: true,
        disable_array_delete: true,
        disable_array_reorder: true,
        theme: "bootstrap2",
        iconlib:"fontawesome4",
		schema: {
		  "type": "object",
		  "title": "Name",
		  "properties": {
			"first_name": {
			  "type": "string",
			  "propertyOrder" : 1
			},
			"last_name": {
			  "type": "string",
			  "propertyOrder" : 2
			},
			"full_name": {
			  "type": "string",
			  "propertyOrder" : 3,
			  "template": "{{fname}} {{lname}}",
			  "watch": {
				"fname": "first_name",
				"lname": "last_name"
			  }
			}
		  }
		},
		startval: {
			"first_name" : "John",
			"last_name" : "Doe",
			"full_name" : "John Doe"
		}
}/);''', multiSelectDelimiter: ',', name: '', quoteValue: false, saveJSONParameterToFile: false, type: 'PT_JSON', visibleItemCount: 5)], submitter: 'admin', submitterParameter: 'selectedBy'
		
	componentList.each{ component ->
		println "loading version information for groupId=$groupId & artifact Id=$component from $repoUrl";
		def versions=PipelineUtil.versionList(repoUrl,groupId,component);
		
		def componentVersion = " extendedChoice(description: '', multiSelectDelimiter: ',', name:$component, saveJSONParameterToFile: false, type: 'PT_SINGLE_SELECT', value: $versions, visibleItemCount: 2)";
		choiceList.add(componentVersion);
		}	

println "version infor in extractVersionList $choiceList";

input message: 'Please select a version for deployment', parameters: choiceList, submitter: 'admin', submitterParameter: 'selectedVersion'
//input message: 'Please select a version for deployment', parameters: [extendedChoice(description: '', multiSelectDelimiter: ',', name: 'Version', saveJSONParameterToFile: false, type: 'PT_SINGLE_SELECT', value: versions, visibleItemCount: 2)], submitter: 'admin', submitterParameter: 'selectedVersion'

// def selectedProperty = input (message: 'Select Version', parameters: [choice(choices: proxyVersions, description: 'Allow selecting a version of artifact', name: 'Please select a version of Proxy to deploy')], submitter: 'admin', submitterParameter: 'selectedVersion');
  // def selectedProperty = input( id: 'userInput', message: 'Choose properties file', parameters: [ [$class: 'ChoiceParameterDefinition', choices: proxyVersions, description: 'Properties', name: 'prop'] ])
    
    println "Property: $selectedProperty"
}