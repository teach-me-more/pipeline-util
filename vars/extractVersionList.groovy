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
	
	def json = new groovy.json.JsonBuilder()
	json "info": deploymentSelections
	def file = new File("$WORKSPACE/release.json")
	file.write(groovy.json.JsonOutput.prettyPrint(json.toString()))
}