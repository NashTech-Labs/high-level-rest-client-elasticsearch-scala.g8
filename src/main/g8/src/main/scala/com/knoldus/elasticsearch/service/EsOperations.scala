package com.knoldus.elasticsearch.service

import com.knoldus.elasticsearch.models.request.{ESDocument, UpdateRequestDetails}
import org.elasticsearch.action.DocWriteResponse
import org.elasticsearch.action.delete.DeleteRequest
import org.elasticsearch.action.index.IndexRequest
import org.elasticsearch.action.search.SearchRequest
import org.elasticsearch.action.update.UpdateRequest
import org.elasticsearch.client.RestHighLevelClient
import org.elasticsearch.common.xcontent.{XContentFactory, XContentType}
import org.elasticsearch.index.query.QueryBuilders
import org.elasticsearch.search.builder.SearchSourceBuilder
import org.slf4j.{Logger, LoggerFactory}
import play.api.libs.json.Json

/**
  * EsOperations trait handles CRUD operations and executes it via RestHighLevelClient.
  */
trait EsOperations {

  val LOGGER: Logger = LoggerFactory.getLogger(classOf[EsOperations])

  val client: RestHighLevelClient = HighLevelRestClient.client

  /**
    * Inserting data in elastic search.
    *
    * @param eSDocument EsDocument containing Index name, type, document Id and source.
    * @return true for inserted or updated, false otherwise.
    */
  def insert[A](eSDocument: ESDocument[A])(implicit tjs: play.api.libs.json.Writes[A]): Boolean = {
    val request = new IndexRequest(eSDocument.index, eSDocument.docType, eSDocument.docId)
    val jsonString = Json.stringify(Json.toJson(eSDocument.obj))
    request.source(jsonString, XContentType.JSON)

    val indexResponse = client.index(request)

    LOGGER.info("Received Index Response " + indexResponse)
    LOGGER.info("Index Response Result -- " + indexResponse.getResult)

    if (indexResponse.getResult.equals(DocWriteResponse.Result.CREATED)) {
      LOGGER.info("Data inserted!")
      true
    } else if (indexResponse.getResult.equals(DocWriteResponse.Result.UPDATED)) {
      LOGGER.info(s"Document for id ${eSDocument.docId} is present, hence it's details are updated!")
      true
    } else {
      LOGGER.info("Data cannot be inserted!")
      false
    }
  }

  /**
    * For updating document for a particular id by
    * passing in the field name that needs to be updated.
    *
    * @param updateRequestDetails Update Request Details containing index name, type, id whose fields need to be
    *                             updated, field name and the value to be changed.
    * @return true for updated, false otherwise.
    */
  def update(updateRequestDetails: UpdateRequestDetails): Boolean = {
    val builder = XContentFactory.jsonBuilder
    builder.startObject
    builder.field(updateRequestDetails.fieldName.toLowerCase(), updateRequestDetails.value)
    builder.endObject

    val updateRequest = new UpdateRequest(updateRequestDetails.index, updateRequestDetails.docType,
      updateRequestDetails.docId).doc(builder)
    val updateResponse = client.update(updateRequest)
    LOGGER.info(s"Update Response $updateResponse")
    LOGGER.info(s"Update Response Result -- ${updateResponse.getResult}")

    if (updateResponse.getResult.equals(DocWriteResponse.Result.UPDATED)) {
      LOGGER.info(s"User with id ${updateRequestDetails.docId} updated successfully!")
      true
    } else {
      LOGGER.info(s"Error while updating user with id ${updateRequestDetails.docId}")
      false
    }
  }

  /**
    * For deleting document for a particular id.
    *
    * @param index   Index name
    * @param docType Type name
    * @param docId   Id for the user you want to delete.
    * @return true for deleted, false otherwise.
    */
  def delete(index: String, docType: String, docId: String): Boolean = {
    val deleteRequest = new DeleteRequest(index, docType, docId)
    val deleteResponse = client.delete(deleteRequest)
    LOGGER.info(s"Delete Response $deleteResponse")
      LOGGER.info(s"Delete Response Result -- ${deleteResponse.getResult}")

    if (deleteResponse.getResult.equals(DocWriteResponse.Result.DELETED)) {
      LOGGER.info(s"Document for id $docId deleted")
      true
    } else {
    LOGGER.info(s"Document for id $docId is not present, hence can't be deleted")
    false
  }
}

/**
  * For searching all the documents in the elastic-search.
  *
  * @param index   Index name
  * @param docType document type
  * @return List of documents.
  */
def searchAll[A](index: String, docType: String)(implicit tjs: play.api.libs.json.Reads[A]): List[A] = {
  val searchRequest = new SearchRequest().indices(index).types(docType)
  val searchSourceBuilder = new SearchSourceBuilder
  searchSourceBuilder.query(QueryBuilders.matchAllQuery())
  searchRequest.source(searchSourceBuilder)

  val searchResponse = client.search(searchRequest)
  val hits = searchResponse.getHits.getHits
  val userList = hits.map(hit => Json.parse(hit.getSourceAsString).as[A]).toList
  LOGGER.info(s"Search Query Result $userList")
  userList
}

}

