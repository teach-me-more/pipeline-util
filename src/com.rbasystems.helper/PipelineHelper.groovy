package com.rbasystems.groovy
import groovy.util.XmlSlurper;
import groovy.json.JsonSlurper;

class PipelineHelper {
	static String versionList(productName) {
		println "Hello World";
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
				
				return productName;
	}
}
