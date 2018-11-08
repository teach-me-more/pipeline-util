#!/usr/bin/env groovy
def call(args) {
      echo "Global liberary is called -${args}"
      Properties props = new Properties()
File propsFile = new File('pipeline/config.properties')
props.load(propsFile.newDataInputStream())
props.setProperty('TEST_SERVER_BASE_URL', args.toString())
props.store(propsFile.newWriter(), null)
}