package com.rbasystems.utility;

import groovy.util.XmlSlurper;
import groovy.json.JsonSlurper;

class PipelineUtil {
	static String versionList(repoUrl,groupId,artifactId) {
		println "Extracting version information for "+artifactId;
		def XmlSlurper xmlSlurper=new XmlSlurper();
		def jsonSlurper = new JsonSlurper();
				
				def nexusData = xmlSlurper.parse(repoUrl+groupId+artifactId);
				def versions="Select Version";
				nexusData.data.artifact.each { item ->
					def versionList=""+item.artifactId+"-"+ item.version;
					versions=versions+","+versionList;
				}				
				return versions;
	}
}
