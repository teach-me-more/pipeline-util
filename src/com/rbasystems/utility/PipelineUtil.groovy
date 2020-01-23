package com.rbasystems.utility;

import groovy.util.XmlSlurper;
import groovy.json.JsonSlurper;

class PipelineUtil {
	static String versionList(productName) {
		println "Extracting version information for "+productName;
		def XmlSlurper xmlSlurper=new XmlSlurper();
		def jsonSlurper = new JsonSlurper();
				
				def nexusData = xmlSlurper.parse("https://repository.apache.org/service/local/lucene/search?g=commons-logging&a="+productName);
				def proxyVersions="Select Version";
				nexusData.data.artifact.each { item ->
					def versionList=""+item.artifactId+"-"+ item.version;
					println versionList;
					proxyVersions=proxyVersions+","+versionList;
				}
				println "Property: $proxyVersions";
				
				return proxyVersions;
	}
}
