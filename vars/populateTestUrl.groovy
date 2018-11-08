#!/usr/bin/env groovy
def String call(elbJson) {
elbJson = elbJson.substring(elbJson.indexOf("\"DNSName\":"),elbJson.length())
elbJson = elbJson.substring(0,elbJson.indexOf(","))
elbJson = elbJson.replace("\"DNSName\": ","http://")
elbJson = elbJson.replace("\"","")
Properties props = new Properties()
File propsFile = new File('pipeline/config.properties')
props.load(propsFile.newDataInputStream())
props.setProperty('TEST_SERVER_BASE_URL', elbJson.toString())
props.store(propsFile.newWriter(), null)
props.load(propsFile.newDataInputStream())

return elbJson
}