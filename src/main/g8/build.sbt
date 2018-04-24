name := "elastic-search-rest-client"

version := "0.1"

scalaVersion := "2.12.4"

val awsJavaCoreSDK = "com.amazonaws" % "aws-java-sdk-core" % "1.11.256"
val awsSigningRequestInterceptor = "vc.inreach.aws" % "aws-signing-request-interceptor" % "0.0.20"
val elasticSearch = "org.elasticsearch" % "elasticsearch" % "6.2.4"
val elasticSearchClient = "org.elasticsearch.client" % "elasticsearch-rest-high-level-client" % "6.2.4"
val play = "com.typesafe.play" %% "play" % "2.6.11"
val log4jCore = "org.apache.logging.log4j" % "log4j-core" % "2.11.0"
val slf4j = "org.slf4j" % "slf4j-api" % "1.7.25"
val slf4jLog4j = "org.slf4j" % "slf4j-log4j12" % "1.7.5"
val slf4jSimple = "org.slf4j" % "slf4j-simple" % "1.7.25"
val scalaTest = "org.scalatest" %% "scalatest" % "3.0.1"
val mockito = "org.mockito" % "mockito-core" % "2.10.0"

libraryDependencies ++= Seq(
  elasticSearch,
  elasticSearchClient,
  play,
  awsJavaCoreSDK,
  awsSigningRequestInterceptor,
  slf4jLog4j,
  slf4jSimple,
  log4jCore,
  scalaTest,
  mockito
)