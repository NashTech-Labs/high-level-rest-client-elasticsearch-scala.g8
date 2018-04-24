package com.knoldus.elasticsearch.service

import java.time.{LocalDateTime, ZoneOffset}

import com.amazonaws.auth.{AWSCredentialsProvider, AWSStaticCredentialsProvider, BasicAWSCredentials}
import com.google.common.base.Supplier
import com.knoldus.elasticsearch.util.ConfigManager
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder
import org.apache.http.{HttpHost, HttpRequestInterceptor}
import org.elasticsearch.client.{RestClient, RestHighLevelClient}
import vc.inreach.aws.request.{AWSSigner, AWSSigningRequestInterceptor}

object HighLevelRestClient {

  val ES_HOST: String = ConfigManager.config.getString("elastic-search-host")
  val SERVICE_NAME: String = ConfigManager.config.getString("elastic-search-service")
  val REGION: String = ConfigManager.config.getString("elastic-search-service-region")
  val AWS_ACCESS_KEY_ID: String = ConfigManager.config.getString("aws-access-id")
  val AWS_SECRET_ACCESS_KEY: String = ConfigManager.config.getString("aws-access-key")
  val AWS_ES_ENDPOINT: String = ConfigManager.config.getString("aws-elastic-search-endpoint")
  val AWS_HOST: String = ConfigManager.config.getString("aws-elastic-search-host")

  /**
    * Creates RestHighLevelClient given the low level RestClientBuilder
    * and sets the hosts that the client will send requests to.
    */
  lazy val client: RestHighLevelClient = if (ES_HOST.equalsIgnoreCase("aws")) {
    new RestHighLevelClient(
      RestClient.builder(
        new HttpHost(AWS_HOST, 80))
        .setHttpClientConfigCallback(
          (httpClientBuilder: HttpAsyncClientBuilder) =>
            httpClientBuilder.addInterceptorLast(performSigningSteps)))
  } else {
    new RestHighLevelClient(
      RestClient.builder(new HttpHost("localhost", 9200, "http")))
  }

  private def performSigningSteps: HttpRequestInterceptor = {
    val awsCredentialsProvider: AWSCredentialsProvider = new AWSStaticCredentialsProvider(
      new BasicAWSCredentials(AWS_ACCESS_KEY_ID, AWS_SECRET_ACCESS_KEY))
    val awsSigner = new AWSSigner(awsCredentialsProvider, REGION, SERVICE_NAME, clock)
    new AWSSigningRequestInterceptor(awsSigner)
  }

  final val clock = new Supplier[LocalDateTime] {
    override def get(): LocalDateTime = LocalDateTime.now(ZoneOffset.UTC)
  }

}
