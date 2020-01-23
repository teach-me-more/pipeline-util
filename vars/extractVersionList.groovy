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
       "schema":{
  "title": "Applications",
  "type": "array",
  "format":"tabs",
  "items": {
	 "title": "Application",
	 "headerTemplate": "{{self.name}}",
	 "type": "object",
	 "properties": {
		"name" : {
			 "title": "application",
			 "type": "string",
			 "readOnly": "true"

		 },
		 "environments": {
			 "title": "Environments",
			 "type": "array",
			 "format":"tabs",
			 "items": {
				 "title": "Environment",
				 "headerTemplate": "{{self.name}}",
				 "type": "object",
				 "properties": {
					"name" : {
						 "title": "environment",
						 "type": "string",
						 "readOnly": "true"

					 },
					 "properties": {
						 "type": "array",
						 "format": "table",
						 "items": {
							 "type": "object",
							  "properties": {
								  "name" : {
									  "type": "string",
									  "readOnly": "true"
								  },
								  "value" : {
									  "type": "string"
								  }
							  }
						 }
					 },
					 "servers": {
						 "title": "Servers",
						 "type": "array",
						 "format":"tabs",
						 "items": {
							 "title": "Server",
							 "headerTemplate": "{{self.name}}",
							 "type": "object",

							 "properties": {
								"name" : {
									 "title": "server",
									 "type": "string",
									 "readOnly": "true"

								 },
								 "properties": {
									 "type": "array",
									 "format": "table",
									 "items": {
										 "type": "object",
										  "properties": {
											  "name" : {
												  "type": "string",
												  "readOnly": "true"
											  },
											  "value" : {
												  "type": "string"
											  },
											  "override": {
												"type": "boolean"
											  }
										  }
									 }
								 }
							 }
						 }
					 }
				 }
			 }
		 }
	 }
  }
},
       startval: [
  {
    "name": "agent",
    "environments": [
      {
        "name": "dev1",
		"properties": [
		  {
			"name": "database_url",
			"value": "dev1_url"
		  },
		  {
			"name": "database_password",
			"value": "dev1_password"
		  }
		],
        "servers": [
			{
				"name": "agt11",
				"properties": [
				  {
					"name": "database_url",
					"value": "agt11_url",
					"override": "true"
				  },
				  {
					"name": "database_password",
					"value": "agt11_password",
					"override": "true"
				  }
				]
			},
			{
				"name": "agt12",
				"properties": [
				  {
					"name": "database_url",
					"value": "agt12_url",
					"override": "true"
				  },
				  {
					"name": "database_password",
					"value": "agt12_password",
					"override": "true"
				  }
				]
			}
		]
      },
	  {
        "name": "devprod",
		"properties": [
		  {
			"name": "database_url",
			"value": "devprod_url"
		  },
		  {
			"name": "database_password",
			"value": "devprod_password"
		  }
		],
        "servers": [
			{
				"name": "agt11",
				"properties": [
				  {
					"name": "database_url",
					"value": "agt11_prod_url",
					"override": "true"
				  },
				  {
					"name": "database_password",
					"value": "agt11_prod_password",
					"override": "true"
				  }
				]
			},
			{
				"name": "agt12",
				"properties": [
				  {
					"name": "database_url",
					"value": "agt12_prod_url",
					"override": "true"
				  },
				  {
					"name": "database_password",
					"value": "agt12_prod_password",
					"override": "true"
				  }
				]
			}
		]
      }
    ]
  },
  {
    "name": "consumer",
    "environments": [
      {
        "name": "dev1",
		"properties": [
		  {
			"name": "database_url",
			"value": "dev1_url"
		  },
		  {
			"name": "database_password",
			"value": "dev1_password"
		  }
		],
        "servers": [
			{
				"name": "app11",
				"properties": [
				  {
					"name": "database_url",
					"value": "app11_url",
					"override": "true"
				  },
				  {
					"name": "database_password",
					"value": "app11_password",
					"override": "true"
				  }
				]
			},
			{
				"name": "app12",
				"properties": [
				  {
					"name": "database_url",
					"value": "app12_url",
					"override": "true"
				  },
				  {
					"name": "database_password",
					"value": "app12_password",
					"override": "true"
				  }
				]
			}
		]
      },
	  {
        "name": "devprod",
		"properties": [
		  {
			"name": "database_url",
			"value": "devprod_url"
		  },
		  {
			"name": "database_password",
			"value": "devprod_password"
		  }
		],
        "servers": [
			{
				"name": "app11",
				"properties": [
				  {
					"name": "database_url",
					"value": "agt11_prod_url",
					"override": "true"
				  },
				  {
					"name": "database_password",
					"value": "app11_prod_password",
					"override": "true"
				  }
				]
			},
			{
				"name": "agt12",
				"properties": [
				  {
					"name": "database_url",
					"value": "app12_prod_url",
					"override": "true"
				  },
				  {
					"name": "database_password",
					"value": "app12_prod_password",
					"override": "true"
				  }
				]
			}
		]
      }
    ]
  }
]
}

}/);

return jsonEditorOptions;''', multiSelectDelimiter: ',', name: '', quoteValue: false, saveJSONParameterToFile: false, type: 'PT_JSON', visibleItemCount: 5)], submitter: 'admin', submitterParameter: 'selectedBy'
		
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