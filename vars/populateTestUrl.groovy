#!/usr/bin/env groovy
def String call(deploymentUnitName) {
def elbJson = sh (script: 'aws elb describe-load-balancers --load-balancer-name GIAMWebLoadBalance',returnStdout: true)
elbJson = elbJson.substring(elbJson.indexOf("\"DNSName\":"),elbJson.length())
elbJson = elbJson.substring(0,elbJson.indexOf(","))
elbJson = elbJson.replace("\"DNSName\": ","http://")
elbJson = elbJson.replace("\"","")
Properties props = new Properties()
File propsFile = new File('pipeline/config.properties')
props.load(propsFile.newDataInputStream())
props.setProperty('TEST_SERVER_BASE_URL', elbJson.toString())
props.store(propsFile.newWriter(), null)
sleep time: 1, unit: 'MINUTES'
config = readProperties file:'pipeline/config.properties' 
return props.getProperty('TEST_SERVER_BASE_URL')
}