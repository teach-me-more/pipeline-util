#!/usr/bin/env groovy
def String call(deploymentUnitName,environment) {
def elbName="${deploymentUnitName}-${environment}-elb"	
def propertyName;

if("Dev"=="${environment}") {
	propertyName="TEST_SERVER_BASE_URL"
}else if("SIT"=="${environment}") {
	propertyName="SIT_SERVER_BASE_URL"
}else if("UAT"=="${environment}") {
	propertyName="UAT_SERVER_BASE_URL"
}else if("PROD"=="${environment}") {
	propertyName="PROD_SERVER_BASE_URL"
	
}
echo "propertyName== ${propertyName}"
def elbJson = sh (script: "aws elb describe-load-balancers --load-balancer-name ${elbName}",returnStdout: true)
elbJson = elbJson.substring(elbJson.indexOf("\"DNSName\":"),elbJson.length())
elbJson = elbJson.substring(0,elbJson.indexOf(","))
elbJson = elbJson.replace("\"DNSName\": ","http://")
elbJson = elbJson.replace("\"","")
//Properties props = new Properties()
//File propsFile = new File('pipeline/config.properties')
//props.load(propsFile.newDataInputStream())
//props.setProperty(propertyName, elbJson.toString())
//props.store(propsFile.newWriter(), null)
//props.load(propsFile.newDataInputStream())

//println(props.getProperty(propertyName)) 
//config = readProperties file:'pipeline/config.properties' 
return elbJson
}